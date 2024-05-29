CREATE DATABASE  IF NOT EXISTS `MDD`;
USE `MDD`;

DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USERS` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(30) DEFAULT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `TOPIC`;
CREATE TABLE `TOPIC` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` varchar(400) NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `USER_TOPIC`;
CREATE TABLE `USER_TOPIC` (
  `user_id` int NOT NULL,
  `topic_id` int NOT NULL,
  `subscribe_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `user_id` (`user_id`),
  KEY `topic_id` (`topic_id`),
  CONSTRAINT `USERS_TOPICS_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`),
  CONSTRAINT `USERS_TOPICS_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `TOPIC` (`id`)
);

DROP TABLE IF EXISTS `POST`;
CREATE TABLE `POST` (
  `id` int NOT NULL AUTO_INCREMENT,
  `author_id` int NOT NULL,
  `topic_id` int NOT NULL,
  `title` varchar(60) NOT NULL,
  `content` text(30000) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `topic_id` (`topic_id`),
   CONSTRAINT `ARTICLE_TOPIC_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `TOPIC` (`id`)
);

DROP TABLE IF EXISTS `COMMENT`;
CREATE TABLE `COMMENT` (
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `content` text(400) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   KEY `post_id` (`post_id`),
   KEY `user_id` (`user_id`),
   CONSTRAINT `COMMENT_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`),
   CONSTRAINT `COMMENT_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `POST` (`id`)
);
