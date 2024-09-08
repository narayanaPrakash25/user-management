package com.usermanagement.dto;

import lombok.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -1446094725635755678L;

    private String accessToken;
    private String refreshToken;
    private Long tokenValid;
}
