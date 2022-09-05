package com.shuaicai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaicai.constants.SystemConstants;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.Comment;
import com.shuaicai.domain.entity.User;
import com.shuaicai.domain.vo.CommentVo;
import com.shuaicai.domain.vo.PageVo;
import com.shuaicai.enums.AppHttpCodeEnum;
import com.shuaicai.exception.SystemException;
import com.shuaicai.mapper.CommentMapper;
import com.shuaicai.service.CommentService;
import com.shuaicai.service.UserService;
import com.shuaicai.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-08-21 23:03:56
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    public UserService userService;

    //查看评论以及二级评论
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应的根评论

        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断..如果commentType为0才去查id，友链不需要文章id
        lambdaQueryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论 rootId为-1
        lambdaQueryWrapper.eq(Comment::getRootId, SystemConstants.Comment_STATUS_NORMAL);

        //评论类型
        lambdaQueryWrapper.eq(Comment::getType,commentType);

        //分页
        Page<Comment> commentPage = new Page<>(pageNum, pageSize);
        page(commentPage,lambdaQueryWrapper);

        //封装
        List<CommentVo> commentVoList = toCommentVoList(commentPage.getRecords());

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        commentVoList.stream()
                .forEach(commentVo -> commentVo.setChildren(getChildren(commentVo.getId())));
//        commentVoList.stream()
//                .forEach(new Consumer<CommentVo>() {
//                    @Override
//                    public void accept(CommentVo commentVo) {
//                        List<CommentVo> children= getChildren(commentVo.getId());
//                        commentVo.setChildren(children);
//                    }
//                });

        return ResponseResult.okResult(new PageVo(commentVoList, commentPage.getTotal()));
    }
    //评论以及回复评论
    @Override
    public ResponseResult addComment(Comment comment) {

        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);

        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getRootId,id);
        lambdaQueryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment> list = list(lambdaQueryWrapper);
        //转成CommentVoList返回
        return toCommentVoList(list);
    }

    public List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        commentVoList.stream()
                .forEach(commentVo -> {
                    //通过creatyBy查询用户的昵称并赋值
                    String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
                    commentVo.setUsername(nickName);
                    //通过toCommentUserId查询用户的昵称并赋值
                    //如果toCommentUserId不为-1才进行查询
                    if (commentVo.getToCommentUserId()!=-1){
                        String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                        commentVo.setToCommentUserName(toCommentUserName);
                    }
                });
        return commentVoList;
    }

}

