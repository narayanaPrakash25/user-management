package com.usermanagement.dto;

import lombok.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8123822535869024869L;

    private String username;
    private String password;
}
