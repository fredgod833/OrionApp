-- Création des tables

-- Table des utilisateurs
CREATE TABLE `users` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) UNIQUE NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table des sujets/thèmes
CREATE TABLE `topics` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table des articles/posts
CREATE TABLE `posts` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `user_id` INT NOT NULL,
  `topic_id` INT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table des abonnements
CREATE TABLE `subscriptions` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `user_id` INT NOT NULL,
  `topic_id` INT NOT NULL
);

-- Table des commentaires
CREATE TABLE `comments` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `post_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Ajout des contraintes de clé étrangère
ALTER TABLE `posts`
ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
ADD FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`);

ALTER TABLE `subscriptions`
ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
ADD FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`);

ALTER TABLE `comments`
ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

-- Insertion de données de test

-- Utilisateurs
INSERT INTO `users` (`username`, `email`, `password`) VALUES
('johndoe', 'john@example.com', 'johnpasswordhash'),
('janedoe', 'jane@example.com', 'janepasswordhash');

-- Sujets/Thèmes
INSERT INTO `topics` (`name`, `description`) VALUES
('Programmation', 'Tout sur la programmation'),
('Base de données', 'Discussions sur les systèmes de gestion de bases de données');

-- Articles/Posts
INSERT INTO `posts` (`user_id`, `topic_id`, `title`, `content`) VALUES
(1, 1, 'Apprendre Python', 'Contenu sur comment apprendre Python...'),
(2, 2, 'SQL pour les débutants', 'Guide détaillé sur le démarrage avec SQL...');

-- Abonnements
INSERT INTO `subscriptions` (`user_id`, `topic_id`) VALUES
(1, 1),
(2, 2);

-- Commentaires
INSERT INTO `comments` (`post_id`, `user_id`, `content`) VALUES
(1, 2, 'Super article sur Python !'),
(2, 1, 'J\'ai trouvé ce guide SQL très utile.');
