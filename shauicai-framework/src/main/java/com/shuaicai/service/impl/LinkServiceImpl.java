package com.shuaicai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaicai.constants.SystemConstants;
import com.shuaicai.domain.ResponseResult;
import com.shuaicai.domain.entity.Link;
import com.shuaicai.domain.vo.LinkVo;
import com.shuaicai.mapper.LinkMapper;
import com.shuaicai.service.LinkService;
import com.shuaicai.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Link)表服务实现类
 *
 * @author makejava
 * @since 2022-08-21 13:23:54
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {



    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.eq(Link::getStatus, SystemConstants.lINK_STATUS_NORMAL);
        List<Link> links = list(linkLambdaQueryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }
}

