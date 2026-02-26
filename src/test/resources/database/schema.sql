-- Drop tables if they already exist (clean start)
DROP TABLE IF EXISTS billing;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS room_types;
DROP TABLE IF EXISTS users;

-- Table for room categories and prices
CREATE TABLE room_types (
  room_type_id INT AUTO_INCREMENT PRIMARY KEY,  -- Unique room type ID
  type_name VARCHAR(50) NOT NULL UNIQUE,        -- Room type name
  rate_per_night DECIMAL(10,2) NOT NULL CHECK (rate_per_night > 0) -- Price per night
);

-- Table for hotel rooms
CREATE TABLE rooms (
  room_id INT AUTO_INCREMENT PRIMARY KEY,      -- Unique room ID
  room_number VARCHAR(10) NOT NULL UNIQUE,     -- Room number
  room_type_id INT NOT NULL,                   -- Related room type
  status ENUM('AVAILABLE','OCCUPIED') DEFAULT 'AVAILABLE', -- Room status
  FOREIGN KEY (room_type_id) REFERENCES room_types(room_type_id)
);

-- Table for guest reservations
CREATE TABLE reservations (
  reservation_id INT AUTO_INCREMENT PRIMARY KEY, -- Unique reservation ID
  reservation_number VARCHAR(20) NOT NULL UNIQUE, -- Reservation reference
  guest_name VARCHAR(100) NOT NULL,               -- Guest name
  address VARCHAR(255) NOT NULL,                  -- Guest address
  contact_number VARCHAR(15) NOT NULL,            -- Phone number
  contact_email VARCHAR(100) NOT NULL,            -- Email
  room_id INT NOT NULL,                           -- Assigned room
  check_in DATE NOT NULL,                         -- Check-in date
  check_out DATE NOT NULL,                        -- Check-out date
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Created time
  status VARCHAR(20) DEFAULT 'CHECKED_IN',        -- Reservation status
  checked_out_at DATE NULL,                       -- Checkout date
  FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);

-- Table for billing information
CREATE TABLE billing (
  bill_id INT AUTO_INCREMENT PRIMARY KEY, -- Unique bill ID
  reservation_number VARCHAR(50) NOT NULL, -- Reservation reference
  nights INT NOT NULL,                     -- Number of nights
  price_per_night DECIMAL(10,2) NOT NULL,  -- Nightly rate
  total_amount DECIMAL(12,2) NOT NULL,     -- Total bill
  generated_at DATETIME NOT NULL           -- Bill generated time
);

-- Table for system users
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY, -- Unique user ID
  username VARCHAR(50) NOT NULL UNIQUE,   -- Login username
  password_hash VARCHAR(255) NOT NULL,    -- Encrypted password
  full_name VARCHAR(100) NOT NULL,        -- User full name
  role ENUM('ADMIN','STAFF') DEFAULT 'STAFF', -- User role
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Account creation time
);

-- Insert room types
INSERT INTO room_types (type_name, rate_per_night) VALUES
('Standard', 5000.00),
('Suite', 7000.00),
('Luxury', 12000.00);

-- Insert rooms
INSERT INTO rooms (room_number, room_type_id, status) VALUES
('101', 1, 'AVAILABLE'),
('102', 2, 'AVAILABLE'),
('103', 3, 'AVAILABLE');

-- Insert system users
-- admin password = "12345" (SHA-256)
INSERT INTO users (username, password_hash, full_name, role) VALUES
('admin', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'System Administrator', 'ADMIN'),
('staff1', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'Staff Member', 'STAFF');

-- Insert test reservation
INSERT INTO reservations (
  reservation_number,
  guest_name,
  address,
  contact_number,
  contact_email,
  room_id,
  check_in,
  check_out,
  status
) VALUES (
  'TEST-RES-001',
  'CI Test Guest',
  'Test Address',
  '0710000000',
  'test@email.com',
  1,
  '2026-01-01',
  '2026-01-03',
  'CHECKED_OUT'
);

-- Insert billing record for testing revenue
INSERT INTO billing (
  reservation_number,
  nights,
  price_per_night,
  total_amount,
  generated_at
) VALUES (
  'TEST-RES-001',
  2,
  5000.00,
  10000.00,
  NOW()
);