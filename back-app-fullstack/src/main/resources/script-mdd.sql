CREATE TABLE `TOPICS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(50),
  `description` VARCHAR(2000),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `POSTS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(50),
  `author` VARCHAR(50),
  `content` VARCHAR(2000),
  `date` TIMESTAMP,
  `topic_id` int,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
ALTER TABLE `POSTS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`id`);

CREATE TABLE `USERS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(40),
  `email` VARCHAR(255),
  `password` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `COMMENTS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `date` TIMESTAMP,
  `content` VARCHAR(2000),
  `post_id` int,
  `user_id` int,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`post_id`) REFERENCES `POSTS` (`id`);
ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);

CREATE TABLE `SUBSCRIPTIONS` (
  `user_id` INT, 
  `topic_id` INT
);
ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`id`);

INSERT INTO TOPICS (title, description)
VALUES ('Java fundamentals', 'all basics of Java world'),
       ('Angular fundamentals', 'all basics of Angular world');

INSERT INTO POSTS (title, author, content, date, topic_id)
VALUES ('Design pattern', 'Romain', 'Design patterns in Java are reusable solutions to common software design problems. They provide best practices and templates for structuring and organizing code to achieve maintainability, scalability, and flexibility. Java is a versatile language and design patterns can be applied to various aspects of software development.', '2023-11-10', 1),
       ('The Observable Pattern', 'Jean', 'The Observable Pattern is a behavioral design pattern that defines a one-to-many dependency between objects. In this pattern, one object (known as the subject or observable) maintains a list of its dependents (observers) and notifies them of any state changes, typically by calling one of their methods. The observers can then react to the state changes of the subject.', '2023-11-10', 2);

INSERT INTO USERS (name, email, password)
VALUES ('user', 'user@test.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq'); 

INSERT INTO COMMENTS (date, content, post_id, user_id)
VALUES ('2023-12-10', 'Thank you for this post!', 1, 1),
       ('2023-12-10', 'Interesting post!', 2, 1);

INSERT INTO SUBSCRIPTIONS (user_id, topic_id)
VALUES (1, 2),
       (1, 1);