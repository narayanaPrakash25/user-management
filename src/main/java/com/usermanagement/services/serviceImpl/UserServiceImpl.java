package com.usermanagement.services.serviceImpl;

import com.usermanagement.common.RequestWrapper;
import com.usermanagement.common.ResponseWrapper;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.UserEntity;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.services.service.UserService;
import com.usermanagement.util.CommonUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static com.usermanagement.util.CommonUtils.isNullThrow;
import static com.usermanagement.util.CommonUtils.responseTime;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommonUtils commonUtils;


    @Override
    public Optional<UserEntity> findUserByUsername(String username) {
        isNullThrow(username, () -> new NullPointerException("User name shouldn't be null"));
        return this.userRepository.findUserEntityByEmail(username);
    }

    @Override
    public ResponseWrapper<UserDTO> save(RequestWrapper<UserDTO> requestWrapper) {
        UserEntity userEntity = this.commonUtils.convertTo(requestWrapper.getRequest(), UserEntity.class);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        if (this.isEmailAlreadyPresent(userEntity.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        final UserDTO res = this.commonUtils.convertTo(this.userRepository.save(userEntity), UserDTO.class);

        return ResponseWrapper.<UserDTO>builder()
                .response(res)
                .message("User created")
                .responsetime(responseTime())
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public ResponseWrapper<UserDTO> findById(RequestWrapper<Long> requestWrapper) {
        final UserDTO findUser = this.commonUtils.convertTo(this.userRepository.findById(requestWrapper.getRequest()).orElseThrow(() -> new NullPointerException("User not found")), UserDTO.class);
        return ResponseWrapper.<UserDTO>builder()
                .response(findUser)
                .status(HttpStatus.OK.value())
                .responsetime(responseTime())
                .message("Find user successful")
                .build();
    }

    @Override
    public ResponseWrapper<List<UserDTO>> findAll() {
        final List<UserDTO> findAllUser = this.userRepository.findAll().stream().map(m -> this.commonUtils.convertTo(m, UserDTO.class)).toList();
        return ResponseWrapper.<List<UserDTO>>builder()
                .response(findAllUser)
                .message("Find all user successful")
                .responsetime(responseTime())
                .status(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ResponseWrapper<UserDTO> update(RequestWrapper<UserDTO> requestWrapper) {
        final UserEntity updateUser = this.userRepository.save(this.commonUtils.convertTo(requestWrapper.getRequest(), UserEntity.class));
        return ResponseWrapper.<UserDTO>builder()
                .response(this.commonUtils.convertTo(updateUser, UserDTO.class))
                .message("User update")
                .responsetime(responseTime())
                .status(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ResponseWrapper<UserDTO> deleteById(RequestWrapper<Long> requestWrapper) {
        this.userRepository.deleteById(requestWrapper.getRequest());
        return ResponseWrapper.<UserDTO>builder()
                .status(HttpStatus.OK.value())
                .responsetime(responseTime())
                .message("User deleted")
                .build();
    }

    @Override
    public boolean isEmailAlreadyPresent(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public ResponseWrapper<UserDTO> deleteUserByEmail(String email) {
        this.userRepository.deleteByEmail(email);
        return ResponseWrapper.<UserDTO>builder()
                .status(HttpStatus.OK.value())
                .responsetime(responseTime())
                .message("User deleted successful")
                .build();
    }
}
