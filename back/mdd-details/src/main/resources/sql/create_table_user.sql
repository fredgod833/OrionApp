CREATE TABLE `users` (
                         `user_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                         `email` VARCHAR(50) NOT NULL,
                         `username` VARCHAR(50) NOT NULL,
                         `password` VARCHAR(80) NOT NULL,
                         `created_at` DATETIME NOT NULL,
                         `updated_at` DATETIME NOT NULL
);