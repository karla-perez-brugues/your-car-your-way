SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- Table User
CREATE TABLE `user` (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'Primary Key (UUID)',
    `last_name` VARCHAR(255) NOT NULL COMMENT 'User last name',
    `first_name` VARCHAR(255) NOT NULL COMMENT 'User first name',
    `address` VARCHAR(500) COMMENT 'User address',
    `phone_number` VARCHAR(20) COMMENT 'User phone number',
    `email` VARCHAR(255) UNIQUE NOT NULL COMMENT 'User email, must be unique',
    `password_hash` VARCHAR(255) NOT NULL COMMENT 'Hashed password for security',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp of record creation',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp of last record update'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Table for user accounts';

-- Table Role
CREATE TABLE `role` (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'Primary Key (UUID)',
    `name` VARCHAR(100) UNIQUE NOT NULL COMMENT 'Unique name of the role (e.g., Admin, Client, Support)',
    `description` TEXT COMMENT 'Description of the role',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Table for defining user roles';

-- Join table between User and Role
CREATE TABLE `user_role` (
    `user_id` VARCHAR(36) NOT NULL COMMENT 'Foreign Key to User',
    `role_id` VARCHAR(36) NOT NULL COMMENT 'Foreign Key to Role',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`, `role_id`), -- Composite primary key for uniqueness of user-role pair
    CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE, -- If user is deleted, remove their roles
    CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role`(`id`) ON DELETE CASCADE -- If role is deleted, remove associated user roles
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Join table for many-to-many relationship between User and Role';

-- Table Agency
CREATE TABLE `agency` (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'Primary Key (UUID)',
    `name` VARCHAR(255) NOT NULL COMMENT 'Agency name',
    `address` VARCHAR(500) NOT NULL COMMENT 'Agency address',
    `phone_number` VARCHAR(20) COMMENT 'Agency phone number',
    `email` VARCHAR(255) COMMENT 'Agency email',
    `city` VARCHAR(255) NOT NULL COMMENT 'City where the agency is located',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Table for car rental agencies';

-- Table VehicleCategory
CREATE TABLE `vehicle_category` (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'Primary Key (UUID)',
    `acriss_code` VARCHAR(10) UNIQUE NOT NULL COMMENT 'Unique ACRISS code for the vehicle category',
    `name` VARCHAR(100) NOT NULL COMMENT 'Category name (e.g., Economy, SUV)',
    `description` TEXT COMMENT 'Description of the vehicle category',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Table for vehicle categories based on ACRISS standard';

-- Table Offer
CREATE TABLE `offer` (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'Primary Key (UUID)',
    `start_city` VARCHAR(255) NOT NULL COMMENT 'City where the rental starts',
    `end_city` VARCHAR(255) NOT NULL COMMENT 'City where the rental ends',
    `check_in` DATETIME NOT NULL COMMENT 'Date and time of rental start',
    `check_out` DATETIME NOT NULL COMMENT 'Date and time of rental end',
    `daily_price` DECIMAL(10, 2) NOT NULL COMMENT 'Daily rental price',
    `vehicle_category_id` VARCHAR(36) NOT NULL COMMENT 'Foreign Key to VehicleCategory',
    `start_agency_id` VARCHAR(36) NOT NULL COMMENT 'Foreign Key to Agency for pickup',
    `end_agency_id` VARCHAR(36) NOT NULL COMMENT 'Foreign Key to Agency for drop-off',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_offer_vehicle_category` FOREIGN KEY (`vehicle_category_id`) REFERENCES `vehicle_category`(`id`),
    CONSTRAINT `fk_offer_start_agency` FOREIGN KEY (`start_agency_id`) REFERENCES `agency`(`id`),
    CONSTRAINT `fk_offer_end_agency` FOREIGN KEY (`end_agency_id`) REFERENCES `agency`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Table for rental offers';

-- Table Booking
CREATE TABLE `booking` (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'Primary Key (UUID)',
    `user_id` VARCHAR(36) NOT NULL COMMENT 'Foreign Key to User',
    `offer_id` VARCHAR(36) NOT NULL COMMENT 'Foreign Key to Offer',
    `booking_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Date and time when the booking was made',
    `total_amount` DECIMAL(10, 2) NOT NULL COMMENT 'Total amount for the booking',
    `status` VARCHAR(50) NOT NULL COMMENT 'Booking status (e.g., Confirmed, Cancelled, Modified)',
    `update_date` DATETIME COMMENT 'Date and time of last booking update',
    `cancellation_date` DATETIME COMMENT 'Date and time of booking cancellation',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_booking_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT `fk_booking_offer` FOREIGN KEY (`offer_id`) REFERENCES `offer`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Table for user bookings';

-- Table Conversation (for messaging, 1 conversation per customer for now)
CREATE TABLE `conversation` (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'Primary Key (UUID)',
    `user_id` VARCHAR(36) UNIQUE NOT NULL COMMENT 'Foreign Key to User, unique as 1 user has 1 conversation',
    `status` VARCHAR(50) COMMENT 'Status of the conversation (e.g., Open, Closed, Pending)',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_conversation_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Table for user conversations with support';

-- Table Message (messages within a conversation)
CREATE TABLE `message` (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'Primary Key (UUID)',
    `conversation_id` VARCHAR(36) NOT NULL COMMENT 'Foreign Key to Conversation',
    `sender_id` VARCHAR(36) NOT NULL COMMENT 'ID of the sender (can be user or support agent)',
    `content` TEXT NOT NULL COMMENT 'Content of the message',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp of message creation',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_message_conversation` FOREIGN KEY (`conversation_id`) REFERENCES `conversation`(`id`)
    -- Potentially add a foreign key to a support_agent table if applicable
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Table for individual messages within a conversation';

-- Table Issue (User reported issues, linked to a booking)
CREATE TABLE `issue` (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'Primary Key (UUID)',
    `user_id` VARCHAR(36) NOT NULL COMMENT 'Foreign Key to User who reported the issue',
    `booking_id` VARCHAR(36) COMMENT 'Foreign Key to Booking, if the issue is related to a specific booking (optional)',
    `content` TEXT NOT NULL COMMENT 'Description of the issue',
    `status` VARCHAR(50) DEFAULT 'Open' COMMENT 'Status of the issue (e.g., Open, Resolved, Closed)',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_issue_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT `fk_issue_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT 'Table for user-reported issues';

SET FOREIGN_KEY_CHECKS = 1;

-- todo: check this file and adapt it to java (id to bigint not varchar)