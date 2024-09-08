package com.usermanagement.services.serviceImpl;

import com.usermanagement.common.RequestWrapper;
import com.usermanagement.common.ResponseWrapper;
import com.usermanagement.dto.RoleDTO;
import com.usermanagement.entity.RolesEntity;
import com.usermanagement.repository.RolesRepository;
import com.usermanagement.services.service.RoleService;
import com.usermanagement.util.CommonUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.usermanagement.util.CommonUtils.responseTime;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RoleServiceImpl implements RoleService {


    private final RolesRepository rolesRepository;
    private final CommonUtils commonUtils;

    @Override
    public ResponseWrapper<RoleDTO> save(RequestWrapper<RoleDTO> requestWrapper) {
        final RoleDTO rolesEntity = this.commonUtils.convertTo(this.rolesRepository.save(this.commonUtils.convertTo(requestWrapper.getRequest(), RolesEntity.class)), RoleDTO.class);
        return ResponseWrapper.<RoleDTO>builder()
                .response(rolesEntity)
                .status(HttpStatus.CREATED.value())
                .message("Role created")
                .responsetime(responseTime())
                .build();
    }

    @Override
    public ResponseWrapper<RoleDTO> findById(RequestWrapper<Long> requestWrapper) {
        final RoleDTO findRole = this.commonUtils.convertTo(this.rolesRepository.findById(requestWrapper.getRequest()).orElseThrow(() -> new NullPointerException("Role not found")), RoleDTO.class);
        return ResponseWrapper.<RoleDTO>builder()
                .response(findRole)
                .status(HttpStatus.OK.value())
                .message("Find Role successful")
                .responsetime(responseTime())
                .build();
    }

    @Override
    public ResponseWrapper<List<RoleDTO>> findAll() {
        final List<RoleDTO> findAllRoles = this.rolesRepository.findAll().stream().map(m -> this.commonUtils.convertTo(m, RoleDTO.class)).toList();
        return ResponseWrapper.<List<RoleDTO>>builder()
                .response(findAllRoles)
                .message("Find all roles successful")
                .status(HttpStatus.OK.value())
                .responsetime(responseTime())
                .build();
    }

    @Override
    public ResponseWrapper<RoleDTO> update(RequestWrapper<RoleDTO> requestWrapper) {
        final RoleDTO updateEntity = this.commonUtils.convertTo(this.rolesRepository.save(this.commonUtils.convertTo(requestWrapper.getRequest(), RolesEntity.class)), RoleDTO.class);
        return ResponseWrapper.<RoleDTO>builder()
                .response(updateEntity)
                .message("Role updated")
                .status(HttpStatus.ACCEPTED.value())
                .responsetime(responseTime())
                .build();
    }

    @Override
    public ResponseWrapper<RoleDTO> deleteById(RequestWrapper<Long> requestWrapper) {
        this.rolesRepository.deleteById(requestWrapper.getRequest());
        return ResponseWrapper.<RoleDTO>builder()
                .message("Role deleted")
                .status(HttpStatus.OK.value())
                .responsetime(responseTime())
                .build();
    }
}
