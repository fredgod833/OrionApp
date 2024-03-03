CREATE TABLE `subscription` (
                                 `subscriptions_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 `subject_id` BIGINT,
                                 `user_id` BIGINT,
                                 CONSTRAINT `fk_subscriptions_subjects` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`subject_id`),
                                 CONSTRAINT `fk_subscriptions_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);