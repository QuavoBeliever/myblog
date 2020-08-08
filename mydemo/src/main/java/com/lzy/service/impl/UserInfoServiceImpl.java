package com.lzy.service.impl;

import com.lzy.pojo.UserInfo;
import com.lzy.mapper.UserInfoMapper;
import com.lzy.service.UserInfoService;
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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
