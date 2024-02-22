CREATE TABLE `subscriptions` (
                                 `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 `subject_id` BIGINT,
                                 `user_id` BIGINT,
                                 CONSTRAINT `fk_subscription_user`
                                     FOREIGN KEY (`user_id`)
                                         REFERENCES `users` (`user_id`)
                                         ON DELETE CASCADE,
                                 CONSTRAINT `fk_subscription_subject`
                                     FOREIGN KEY (`subject_id`)
                                         REFERENCES `subjects` (`id`)
                                         ON DELETE CASCADE
);