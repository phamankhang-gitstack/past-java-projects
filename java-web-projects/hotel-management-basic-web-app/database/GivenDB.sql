-- Create Database
CREATE DATABASE HotelManagement;
GO

USE HotelManagement;
GO

-- ==========================
-- 1. Guest Table
-- ==========================
CREATE TABLE GUEST (
    GuestID INT IDENTITY(1,1) PRIMARY KEY,
    FullName NVARCHAR(100) NOT NULL,
    Phone NVARCHAR(20) UNIQUE,
    Email NVARCHAR(100) UNIQUE,
    Address NVARCHAR(200),
    IDNumber NVARCHAR(50),   -- Passport/ID Card
    DateOfBirth DATE
);

-- ==========================
-- 2. Room Type Table
-- ==========================
CREATE TABLE ROOM_TYPE (
    RoomTypeID INT IDENTITY(1,1) PRIMARY KEY,
    TypeName NVARCHAR(50) NOT NULL,
    Capacity INT NOT NULL CHECK (Capacity > 0),
    PricePerNight DECIMAL(10,2) NOT NULL CHECK (PricePerNight >= 0)
);

-- ==========================
-- 3. Room Table
-- ==========================
CREATE TABLE ROOM (
    RoomID INT IDENTITY(1,1) PRIMARY KEY,
    RoomNumber NVARCHAR(10) UNIQUE NOT NULL,
    RoomTypeID INT NOT NULL,
    Status NVARCHAR(20) CHECK (Status IN ('Available', 'Occupied', 'Dirty', 'Maintenance')),
    FOREIGN KEY (RoomTypeID) REFERENCES ROOM_TYPE(RoomTypeID)
);

-- ==========================
-- 4. Booking Table
-- ==========================
CREATE TABLE BOOKING (
    BookingID INT IDENTITY(1,1) PRIMARY KEY,
    GuestID INT NOT NULL,
    RoomID INT NOT NULL,
    CheckInDate DATE NOT NULL,
    CheckOutDate DATE NOT NULL,
    BookingDate DATE DEFAULT GETDATE(),
    Status NVARCHAR(20) CHECK (Status IN ('Reserved', 'Checked-in', 'Checked-out', 'Canceled')),
    FOREIGN KEY (GuestID) REFERENCES GUEST(GuestID),
    FOREIGN KEY (RoomID) REFERENCES ROOM(RoomID),
    CHECK (CheckOutDate > CheckInDate)
);

-- ==========================
-- 5. Service Table
-- ==========================
CREATE TABLE SERVICE (
    ServiceID INT IDENTITY(1,1) PRIMARY KEY,
    ServiceName NVARCHAR(100) NOT NULL,
    ServiceType NVARCHAR(50),  -- e.g., Food, Laundry, Spa
    Price DECIMAL(10,2) NOT NULL CHECK (Price >= 0)
);

-- ==========================
-- 6. Booking_Service Table
-- ==========================
CREATE TABLE BOOKING_SERVICE (
    Booking_Service_ID int identity(1,1) primary key,
    BookingID INT NOT NULL,
    ServiceID INT NOT NULL,
    Quantity INT DEFAULT 1 CHECK (Quantity > 0),
    ServiceDate DATE DEFAULT GETDATE(),   
    FOREIGN KEY (BookingID) REFERENCES BOOKING(BookingID),
    FOREIGN KEY (ServiceID) REFERENCES SERVICE(ServiceID)
);

-- ==========================
-- 7. Invoice Table
-- ==========================
CREATE TABLE INVOICE (
    InvoiceID INT IDENTITY(1,1) PRIMARY KEY,
    BookingID INT NOT NULL UNIQUE,
    IssueDate DATE DEFAULT GETDATE(),
    TotalAmount DECIMAL(12,2) NOT NULL CHECK (TotalAmount >= 0),
    Status NVARCHAR(20) CHECK (Status IN ('Unpaid', 'Paid', 'Canceled')),
    FOREIGN KEY (BookingID) REFERENCES BOOKING(BookingID)
);

-- ==========================
-- 8. Payment Table
-- ==========================
CREATE TABLE PAYMENT (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    BookingID INT NOT NULL,
    PaymentDate DATE DEFAULT GETDATE(),
    Amount DECIMAL(12,2) NOT NULL CHECK (Amount >= 0),
    PaymentMethod NVARCHAR(50) CHECK (PaymentMethod IN ('Cash', 'Credit Card', 'Debit Card', 'Online')),
    Status NVARCHAR(20) CHECK (Status IN ('Pending', 'Completed', 'Failed')),
    FOREIGN KEY (BookingID) REFERENCES BOOKING(BookingID)
);

-- ==========================
-- 9. Staff Table
-- ==========================
CREATE TABLE STAFF (
    StaffID INT IDENTITY(1,1) PRIMARY KEY,
    FullName NVARCHAR(100) NOT NULL,
    Role NVARCHAR(50) CHECK (Role IN ('Receptionist', 'Manager', 'Housekeeping', 'ServiceStaff', 'Admin')),
    Username NVARCHAR(50) UNIQUE NOT NULL,
    PasswordHash NVARCHAR(255) NOT NULL, -- store hashed password
    Phone NVARCHAR(20),
    Email NVARCHAR(100)
);


-- ==========================
-- Sample Data (Optional)
-- ==========================
INSERT INTO ROOM_TYPE (TypeName, Capacity, PricePerNight)
VALUES 
('Single', 1, 50.00),
('Double', 2, 80.00),
('Suite', 4, 150.00);

INSERT INTO ROOM (RoomNumber, RoomTypeID, Status)
VALUES 
('101', 1, 'Available'),
('102', 2, 'Available'),
('201', 3, 'Available');

INSERT INTO SERVICE (ServiceName, ServiceType, Price)
VALUES
('Breakfast', 'Food', 10.00),
('Laundry', 'Laundry', 5.00),
('Spa Massage', 'Spa', 30.00);
