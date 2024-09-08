package com.usermanagement.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServerError implements Serializable {

    @Serial
    private static final long serialVersionUID = 3366854155426414757L;

    private String code;
    private Object error;
}
