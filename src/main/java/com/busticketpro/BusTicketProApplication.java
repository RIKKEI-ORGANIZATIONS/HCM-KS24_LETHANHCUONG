package com.busticketpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BusTicketProApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println("admin123 => " + encoder.encode("admin123"));
//        System.out.println("staff123 => " + encoder.encode("staff123"));
//        System.out.println("pass123 => " + encoder.encode("pass123"));
        SpringApplication.run(BusTicketProApplication.class, args);
    }

}