package com.usermanagement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -9154408970259809720L;
    private Long userId;
    private String userName;
    private String email;
    @JsonProperty(access = WRITE_ONLY)
    private String password;
    private String gender;
    private List<RoleDTO> roles;
}
