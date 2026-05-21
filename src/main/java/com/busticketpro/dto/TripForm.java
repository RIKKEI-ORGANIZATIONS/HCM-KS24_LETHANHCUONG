package com.busticketpro.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TripForm {

    @NotNull(message = "Vui lòng chọn tuyến đường")
    private Long routeId;

    @NotNull(message = "Vui lòng chọn xe")
    private Long busId;

    @NotNull(message = "Vui lòng chọn giờ khởi hành")
    @Future(message = "Giờ khởi hành phải ở tương lai")
    private LocalDateTime departureTime;

    @NotNull(message = "Vui lòng nhập giá vé")
    @Min(value = 1000, message = "Giá vé không hợp lệ")
    private Double price;
}