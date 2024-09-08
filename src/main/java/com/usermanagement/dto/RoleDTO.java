package com.usermanagement.dto;


import lombok.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 9095949986125125465L;

    private Long roleId;
    private String role;
    private String description;
}
