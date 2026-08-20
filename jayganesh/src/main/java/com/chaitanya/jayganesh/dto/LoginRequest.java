package com.chaitanya.jayganesh.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class LoginRequest
{
    private String email;
    private String password;
}
