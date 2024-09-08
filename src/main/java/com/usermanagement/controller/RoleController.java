package com.usermanagement.controller;

import com.usermanagement.common.RequestWrapper;
import com.usermanagement.common.ResponseWrapper;
import com.usermanagement.dto.RoleDTO;
import com.usermanagement.services.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/role")
@Slf4j
public class RoleController {

    private final RoleService roleService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<RoleDTO>> findRoleById(@RequestParam("id") final Long id) {
        return ResponseEntity.ok(this.roleService.findById(RequestWrapper.<Long>builder().request(id).build()));
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<RoleDTO>>> findRoleAll() {
        return ResponseEntity.ok(this.roleService.findAll());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<RoleDTO>> createRole(@RequestBody final RequestWrapper<RoleDTO> requestWrapper, HttpServletRequest req) {
        return ResponseEntity.created(URI.create(req.getContextPath())).body(this.roleService.save(requestWrapper));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<RoleDTO>> updateRole(@RequestBody final RequestWrapper<RoleDTO> requestWrapper) {
        return ResponseEntity.accepted().body(this.roleService.update(requestWrapper));
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<RoleDTO>> deleteRoleById(@RequestParam("id") final Long id) {
        return ResponseEntity.ok(this.roleService.deleteById(RequestWrapper.<Long>builder().request(id).build()));
    }
}
