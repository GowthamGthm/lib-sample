package com.library.management.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {


    private String token;
    private String username;
    private String fullName;
    private String email;




}