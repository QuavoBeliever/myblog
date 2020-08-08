package com.lzy.service.impl;

import com.lzy.pojo.Question;
import com.lzy.mapper.QuestionMapper;
import com.lzy.service.QuestionService;
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
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
