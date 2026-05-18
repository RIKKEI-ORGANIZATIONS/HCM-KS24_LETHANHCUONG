package com.busticketpro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = "Thiếu tripId")
    private Long tripId;

    @NotNull(message = "Chọn ghế")
    private Long seatId;

    @NotBlank(message = "Họ tên không được trống")
    private String customerName;

    @NotBlank(message = "SĐT không được trống")
    private String phone;

    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không đúng định dạng")
    private String email;
}