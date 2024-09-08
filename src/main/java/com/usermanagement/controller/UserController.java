package com.usermanagement.controller;

import com.usermanagement.common.RequestWrapper;
import com.usermanagement.common.ResponseWrapper;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.services.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<UserDTO>> findById(@RequestParam("id") final Long id) {
        return ResponseEntity.ok(this.userService.findById(RequestWrapper.<Long>builder().request(id).build()));
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<UserDTO>>> findAllUser() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<UserDTO>> createUser(@RequestBody final RequestWrapper<UserDTO> requestWrapper, HttpServletRequest req) {
        return ResponseEntity.created(URI.create(req.getContextPath())).body(this.userService.save(requestWrapper));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<UserDTO>> updateUser(@RequestBody final RequestWrapper<UserDTO> requestWrapper) {
        return ResponseEntity.accepted().body(this.userService.update(requestWrapper));
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<UserDTO>> deleteByEmail(@RequestParam("email") final String email) {
        return ResponseEntity.ok(this.userService.deleteUserByEmail(email));
    }

}
