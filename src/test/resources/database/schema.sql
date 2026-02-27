-- Drop tables if they already exist (clean start)
DROP TABLE IF EXISTS billing;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS room_types;
DROP TABLE IF EXISTS users;

-- ================= ROOM TYPES =================
CREATE TABLE room_types (
  room_type_id INT AUTO_INCREMENT PRIMARY KEY,
  type_name VARCHAR(50) NOT NULL UNIQUE
);

-- ================= ROOMS =================
CREATE TABLE rooms (
  room_id INT AUTO_INCREMENT PRIMARY KEY,
  room_number VARCHAR(10) NOT NULL UNIQUE,
  room_type_id INT NOT NULL,
  rate_per_night DECIMAL(10,2) NOT NULL,
  status ENUM('AVAILABLE','OCCUPIED') DEFAULT 'AVAILABLE',
  FOREIGN KEY (room_type_id) REFERENCES room_types(room_type_id)
);

-- ================= RESERVATIONS =================
CREATE TABLE reservations (
  reservation_id INT AUTO_INCREMENT PRIMARY KEY,
  reservation_number VARCHAR(20) NOT NULL UNIQUE,
  guest_name VARCHAR(100) NOT NULL,
  address VARCHAR(255) NOT NULL,
  contact_number VARCHAR(15) NOT NULL,
  contact_email VARCHAR(100) NOT NULL,
  room_id INT NOT NULL,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(20) DEFAULT 'CHECKED_IN',
  checked_out_at DATE NULL,
  FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);

-- ================= BILLING =================
CREATE TABLE billing (
  bill_id INT AUTO_INCREMENT PRIMARY KEY,
  reservation_number VARCHAR(50) NOT NULL,
  nights INT NOT NULL,
  price_per_night DECIMAL(10,2) NOT NULL,
  total_amount DECIMAL(12,2) NOT NULL,
  generated_at DATETIME NOT NULL
);

-- ================= USERS =================
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  role ENUM('ADMIN','STAFF') DEFAULT 'STAFF',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================= SEED DATA =================

INSERT INTO room_types (type_name) VALUES
('Standard'),
('Suite'),
('Luxury');

INSERT INTO rooms (room_number, room_type_id, rate_per_night, status) VALUES
('101', 1, 5000.00, 'AVAILABLE'),
('102', 2, 7000.00, 'AVAILABLE'),
('103', 3, 12000.00, 'AVAILABLE');

INSERT INTO users (username, password_hash, full_name, role) VALUES
('admin', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'System Administrator', 'ADMIN'),
('staff1', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'Staff Member', 'STAFF');

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