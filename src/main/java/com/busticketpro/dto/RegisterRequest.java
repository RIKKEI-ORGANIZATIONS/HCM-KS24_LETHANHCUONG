package com.busticketpro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username không được trống")
    @Size(min = 4, max = 30, message = "Username từ 4 đến 30 ký tự")
    private String username;

    @NotBlank(message = "Password không được trống")
    @Size(min = 6, message = "Password tối thiểu 6 ký tự")
    private String password;

    @NotBlank(message = "Họ tên không được trống")
    private String fullName;

    @NotBlank(message = "SĐT không được trống")
    private String phone;

    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    private String address;
}