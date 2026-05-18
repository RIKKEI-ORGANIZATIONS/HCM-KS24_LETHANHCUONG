package com.busticketpro.runner;

import com.busticketpro.entity.*;
import com.busticketpro.enums.*;
import com.busticketpro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final LocationRepository locationRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {

        if (userRepository.count() == 0) {
            AppUser admin = new AppUser();
            admin.setUsername("templates/admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ROLE_ADMIN);
            admin.setFullName("Admin System");
            admin.setPhone("0900000001");
            admin.setEmail("admin@bus.com");
            userRepository.save(admin);

            AppUser staff = new AppUser();
            staff.setUsername("staff");
            staff.setPassword(passwordEncoder.encode("staff123"));
            staff.setRole(Role.ROLE_STAFF);
            staff.setFullName("Staff System");
            staff.setPhone("0900000002");
            staff.setEmail("staff@bus.com");
            userRepository.save(staff);

            AppUser passenger = new AppUser();
            passenger.setUsername("templates/passenger");
            passenger.setPassword(passwordEncoder.encode("pass123"));
            passenger.setRole(Role.ROLE_PASSENGER);
            passenger.setFullName("Passenger Demo");
            passenger.setPhone("0900000003");
            passenger.setEmail("passenger@bus.com");
            userRepository.save(passenger);
        }

        if (locationRepository.count() == 0) {
            Location hcm = new Location(null, "Ho Chi Minh");
            Location daNang = new Location(null, "Da Nang");
            Location haNoi = new Location(null, "Ha Noi");
            locationRepository.save(hcm);
            locationRepository.save(daNang);
            locationRepository.save(haNoi);
        }

        if (routeRepository.count() == 0) {
            Location hcm = locationRepository.findAll().get(0);
            Location daNang = locationRepository.findAll().get(1);
            Location haNoi = locationRepository.findAll().get(2);

            routeRepository.save(new Route(null, hcm, daNang, 950.0));
            routeRepository.save(new Route(null, daNang, haNoi, 760.0));
        }

        if (busRepository.count() == 0) {
            busRepository.save(new Bus(null, "51B-12345", "45 chỗ", 10, "Nguyen Van A"));
            busRepository.save(new Bus(null, "43B-67890", "29 chỗ", 10, "Tran Van B"));
        }

        if (tripRepository.count() == 0) {
            Route route1 = routeRepository.findAll().get(0);
            Route route2 = routeRepository.findAll().get(1);
            Bus bus1 = busRepository.findAll().get(0);
            Bus bus2 = busRepository.findAll().get(1);

            Trip trip1 = new Trip();
            trip1.setRoute(route1);
            trip1.setBus(bus1);
            trip1.setDepartureTime(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0));
            trip1.setPrice(250000.0);
            tripRepository.save(trip1);

            Trip trip2 = new Trip();
            trip2.setRoute(route2);
            trip2.setBus(bus2);
            trip2.setDepartureTime(LocalDateTime.now().plusDays(1).withHour(14).withMinute(0).withSecond(0).withNano(0));
            trip2.setPrice(300000.0);
            tripRepository.save(trip2);
        }

        if (seatRepository.count() == 0) {
            for (Trip trip : tripRepository.findAll()) {
                int totalSeats = trip.getBus().getTotalSeats();
                for (int i = 1; i <= totalSeats; i++) {
                    Seat seat = new Seat();
                    seat.setTrip(trip);
                    seat.setSeatNumber(String.format("%02d", i));
                    seat.setStatus(SeatStatus.AVAILABLE);
                    seatRepository.save(seat);
                }
            }
        }
    }
}