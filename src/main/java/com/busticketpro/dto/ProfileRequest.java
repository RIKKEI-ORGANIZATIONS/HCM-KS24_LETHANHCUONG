package com.busticketpro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {

    @NotBlank(message = "Họ tên không được trống")
    private String fullName;

    @NotBlank(message = "SĐT không được trống")
    private String phone;

    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    private String address;
}