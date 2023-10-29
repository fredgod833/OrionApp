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

-- Utilisateurs supplémentaires
INSERT INTO `users` (`username`, `email`, `password`) VALUES
('emily', 'emily@example.com', 'emilypasswordhash'),
('michael', 'michael@example.com', 'michaelpasswordhash'),
('sophia', 'sophia@example.com', 'sophiapasswordhash'),
('ethan', 'ethan@example.com', 'ethanpasswordhash'),
('olivia', 'olivia@example.com', 'oliviapasswordhash'),
('david', 'david@example.com', 'davidpasswordhash'),
('isabella', 'isabella@example.com', 'isabellapasswordhash'),
('james', 'james@example.com', 'jamespasswordhash');

-- Sujets/Thèmes supplémentaires
INSERT INTO `topics` (`name`, `description`) VALUES
('Intelligence Artificielle', 'Développements et discussions sur l\'IA'),
('Développement Web', 'Tout sur le développement de sites et d\'applications web'),
('Cybersécurité', 'Protégez-vous contre les menaces en ligne'),
('Jeux vidéos', 'Discussions sur les derniers jeux et le développement de jeux'),
('Hardware', 'Discussions sur les composants et les nouveautés hardware'),
('Logiciels', 'Critiques et conseils sur les logiciels'),
('Technologie mobile', 'Nouveautés et discussions sur la technologie mobile');

-- Articles/Posts supplémentaires
INSERT INTO `posts` (`user_id`, `topic_id`, `title`, `content`) VALUES
(3, 3, 'Fondamentaux de l\'IA', 'Introduction à l\'intelligence artificielle...'),
(4, 4, 'Construire un site web', 'Étapes pour construire un site web attrayant...'),
(5, 5, 'Importance de la cybersécurité', 'Pourquoi vous devriez vous soucier de la cybersécurité...'),
(6, 1, 'Nouvelle console de jeu', 'Analyse de la nouvelle console...'),
(7, 6, 'Meilleurs logiciels de montage vidéo', 'Logiciels recommandés pour le montage vidéo...'),
(8, 7, 'Tendances actuelles en matière de smartphones', 'Ce qui est nouveau dans le monde des smartphones...'),
(2, 3, 'Comprendre le Machine Learning', 'Fondamentaux du Machine Learning...'),
(1, 4, 'Sécurité dans le développement web', 'Meilleures pratiques pour un web sûr...');

-- Abonnements supplémentaires
INSERT INTO `subscriptions` (`user_id`, `topic_id`) VALUES
(3, 3),
(4, 4),
(5, 1),
(6, 2),
(7, 5),
(8, 6),
(2, 7),
(1, 5),
(4, 1),
(3, 6);

-- Commentaires supplémentaires
INSERT INTO `comments` (`post_id`, `user_id`, `content`) VALUES
(3, 4, 'Article très informatif sur l\'IA.'),
(4, 3, 'Merci pour les conseils sur le développement web!'),
(5, 7, 'La cybersécurité est en effet cruciale.'),
(6, 5, 'Impatient d\'essayer cette nouvelle console.'),
(7, 8, 'J\'aime ces logiciels de montage vidéo.'),
(8, 6, 'Super article sur les tendances mobiles!'),
(3, 1, 'L\'IA et le Machine Learning sont fascinants.'),
(4, 2, 'La sécurité web est si importante!');
