package com.busticketpro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "buses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Biển số không được trống")
    @Column(unique = true)
    private String licensePlate;

    @NotBlank(message = "Loại xe không được trống")
    private String busType;

    @NotNull(message = "Số ghế không được trống")
    @Min(value = 1, message = "Số ghế phải >= 1")
    private Integer totalSeats;

    @NotBlank(message = "Tên tài xế không được trống")
    private String driverName;
}