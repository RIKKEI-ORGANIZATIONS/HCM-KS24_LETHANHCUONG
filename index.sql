
DROP DATABASE IF EXISTS bus_ticket_db;
CREATE DATABASE bus_ticket_db;
USE bus_ticket_db;

-- =====================================================
-- USERS
-- =====================================================

CREATE TABLE app_users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,

    role VARCHAR(30),

    full_name VARCHAR(255),
    phone VARCHAR(30),
    email VARCHAR(255),
    address VARCHAR(255)
);

-- =====================================================
-- LOCATIONS
-- =====================================================

CREATE TABLE locations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);

-- =====================================================
-- ROUTES
-- =====================================================

CREATE TABLE routes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    from_location_id BIGINT,
    to_location_id BIGINT,

    distance DOUBLE,

    CONSTRAINT fk_route_from_location
        FOREIGN KEY (from_location_id)
        REFERENCES locations(id),

    CONSTRAINT fk_route_to_location
        FOREIGN KEY (to_location_id)
        REFERENCES locations(id)
);

-- =====================================================
-- BUSES
-- =====================================================

CREATE TABLE buses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    license_plate VARCHAR(100) UNIQUE,

    bus_type VARCHAR(100),

    total_seats INT,

    driver_name VARCHAR(255)
);

-- =====================================================
-- TRIPS
-- =====================================================

CREATE TABLE trips (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    route_id BIGINT,
    bus_id BIGINT,

    departure_time DATETIME,

    price DOUBLE,

    CONSTRAINT fk_trip_route
        FOREIGN KEY (route_id)
        REFERENCES routes(id),

    CONSTRAINT fk_trip_bus
        FOREIGN KEY (bus_id)
        REFERENCES buses(id)
);

-- =====================================================
-- SEATS
-- =====================================================

CREATE TABLE seats (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    trip_id BIGINT,

    seat_number VARCHAR(20),

    status VARCHAR(30),

    CONSTRAINT fk_seat_trip
        FOREIGN KEY (trip_id)
        REFERENCES trips(id)
);

-- =====================================================
-- TICKETS
-- =====================================================

CREATE TABLE tickets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    ticket_code VARCHAR(100) UNIQUE,

    customer_name VARCHAR(255),
    phone VARCHAR(30),
    email VARCHAR(255),

    trip_id BIGINT,
    seat_id BIGINT,

    total_price DOUBLE,

    status VARCHAR(30),

    booking_time DATETIME,

    expired_at DATETIME,

    CONSTRAINT fk_ticket_trip
        FOREIGN KEY (trip_id)
        REFERENCES trips(id),

    CONSTRAINT fk_ticket_seat
        FOREIGN KEY (seat_id)
        REFERENCES seats(id)
);

-- =====================================================
-- INSERT LOCATIONS
-- =====================================================

INSERT INTO locations(name)
VALUES
('Ho Chi Minh'),
('Da Nang'),
('Ha Noi');

-- =====================================================
-- INSERT ROUTES
-- =====================================================

INSERT INTO routes(from_location_id, to_location_id, distance)
VALUES
(1, 2, 950),
(2, 3, 760);

-- =====================================================
-- INSERT BUSES
-- =====================================================

INSERT INTO buses(license_plate, bus_type, total_seats, driver_name)
VALUES
('51B-12345', '45 chỗ', 10, 'Nguyen Van A'),
('43B-67890', '29 chỗ', 10, 'Tran Van B');

-- =====================================================
-- INSERT TRIPS
-- =====================================================

INSERT INTO trips(route_id, bus_id, departure_time, price)
VALUES
(1, 1, '2026-06-01 08:00:00', 250000),
(2, 2, '2026-06-01 14:00:00', 300000);

-- =====================================================
-- INSERT SEATS
-- =====================================================

INSERT INTO seats(trip_id, seat_number, status)
VALUES
(1, '01', 'PENDING'),
(1, '02', 'PENDING'),
(1, '03', 'AVAILABLE'),
(1, '04', 'AVAILABLE'),
(1, '05', 'AVAILABLE'),
(1, '06', 'AVAILABLE'),
(1, '07', 'AVAILABLE'),
(1, '08', 'AVAILABLE'),
(1, '09', 'AVAILABLE'),
(1, '10', 'AVAILABLE');

INSERT INTO seats(trip_id, seat_number, status)
VALUES
(2, '01', 'BOOKED'),
(2, '02', 'AVAILABLE'),
(2, '03', 'AVAILABLE'),
(2, '04', 'AVAILABLE'),
(2, '05', 'AVAILABLE'),
(2, '06', 'AVAILABLE'),
(2, '07', 'AVAILABLE'),
(2, '08', 'AVAILABLE'),
(2, '09', 'AVAILABLE'),
(2, '10', 'AVAILABLE');

-- =====================================================
-- INSERT USERS
-- password:
-- admin123
-- staff123
-- pass123
-- =====================================================

INSERT INTO app_users
(
    username,
    password,
    role,
    full_name,
    phone,
    email
)
VALUES
(
    'admin',
    '$2a$10$/D1zWMo0xVrXR6YEiWANg.PlrnGHQRXPIjPZQ75CZxnjs5J202Ysa',
    'ROLE_ADMIN',
    'Admin System',
    '0900000001',
    'admin@bus.com'
),
(
    'staff',
    '$2a$10$vE0RZmSKpfNovFVdVjAGueLBgCkZ/SYlD4/7OYqlJt1nzLlZv7yM2',
    'ROLE_STAFF',
    'Staff System',
    '0900000002',
    'staff@bus.com'
),
(
    'passenger',
    '$2a$10$7hUkwZYq28gn5/KWNPT90epU0mtOym/yEkSddndrVpkoc0bVmuJQO',
    'ROLE_PASSENGER',
    'Passenger Demo',
    '0900000003',
    'passenger@bus.com'
);

-- =====================================================
-- INSERT SAMPLE TICKETS
-- =====================================================

INSERT INTO tickets
(
    ticket_code,
    customer_name,
    phone,
    email,
    trip_id,
    seat_id,
    total_price,
    status,
    booking_time,
    expired_at
)
VALUES
(
    'TK001',
    'Passenger Demo',
    '0900000003',
    'passenger@bus.com',
    1,
    1,
    250000,
    'PENDING',
    NOW(),
    DATE_ADD(NOW(), INTERVAL 15 MINUTE)
),
(
    'TK002',
    'Passenger Demo',
    '0900000003',
    'passenger@bus.com',
    1,
    2,
    250000,
    'PENDING',
    NOW(),
    DATE_ADD(NOW(), INTERVAL 15 MINUTE)
),
(
    'TK003',
    'Passenger Demo',
    '0900000003',
    'passenger@bus.com',
    2,
    11,
    300000,
    'PAID',
    NOW(),
    NULL
);

-- =====================================================
-- CHECK DATA
-- =====================================================

SELECT * FROM app_users;
SELECT * FROM locations;
SELECT * FROM routes;
SELECT * FROM buses;
SELECT * FROM trips;
SELECT * FROM seats;
SELECT * FROM tickets;

UPDATE app_users SET password = '$2a$10$hbNUDAghpgat3IKH7TVGM.5Ik84B3DX03hgBXkH/XfKYvie8U7NI6' WHERE username = 'admin';
UPDATE app_users SET password = '$2a$10$WoMn3WaXDzVxowDkixA8UOw7FEFUOMkrA7rLF5VRhY66BbXI3nGKe' WHERE username = 'staff';
UPDATE app_users SET password = '$2a$10$LEk8FfwOJTXeHaJOVlvVPeuYDKgOeKAaEWOibM9Iq1QwuUm7OZmky' WHERE username = 'passenger';