package com.example.demo.service.user.mapper;

import com.example.demo.entities.UserPasswordChangeEntity;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.UserPasswordChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordChangeMapper {

    @Autowired
    private UserMapper userMapper;

    public UserPasswordChangeEntity mapToEntity(UserPasswordChangeEntity entity, UserPasswordChangeRequest request) {
        entity.setUserId(userMapper.mapToEntity(request.getUserRequest(), new UserEntity()));
        entity.setPasswordBefore(request.getPasswordBefore());
        entity.setPasswordAfter(request.getPasswordAfter());
        entity.setChangedAt(request.getChangedAt());

        return entity;
    }


}
