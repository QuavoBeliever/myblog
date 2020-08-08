package com.lzy.service.impl;

import com.lzy.pojo.Blog;
import com.lzy.mapper.BlogMapper;
import com.lzy.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘子义
 * @since 2020-08-06
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
