package com.usermanagement.controller;

import com.usermanagement.common.RequestWrapper;
import com.usermanagement.common.ResponseWrapper;
import com.usermanagement.dto.JwtTokenRequest;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.security.JwtService;
import com.usermanagement.services.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final JwtService jwtService;

    private final UserService userService;

    @GetMapping(path = "/login", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<?>> authenticate(@RequestParam final Map<String, String> request) {
        return ResponseEntity.ok(ResponseWrapper.builder().response(this.jwtService.generateToken(JwtTokenRequest.builder().username(request.get("username")).password(request.get("password")).build())).build());
    }

    @PostMapping(path = "/register", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<UserDTO>> createUser(@RequestBody final RequestWrapper<UserDTO> requestWrapper, HttpServletRequest req) {
        return ResponseEntity.created(URI.create(req.getContextPath())).body(this.userService.save(requestWrapper));
    }


}
