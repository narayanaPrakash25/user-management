package com.usermanagement.services.service;

import com.usermanagement.common.BaseService;
import com.usermanagement.common.ResponseWrapper;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.UserEntity;

import java.util.Optional;

public interface UserService extends BaseService<UserDTO,Long> {

    Optional<UserEntity> findUserByUsername(String username);

    boolean isEmailAlreadyPresent(String email);

    ResponseWrapper<UserDTO> deleteUserByEmail(String email);
}
