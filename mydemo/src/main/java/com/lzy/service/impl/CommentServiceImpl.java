package com.lzy.service.impl;

import com.lzy.pojo.Comment;
import com.lzy.mapper.CommentMapper;
import com.lzy.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
