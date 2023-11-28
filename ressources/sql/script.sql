-- Table des utilisateurs
CREATE TABLE `users` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(100) UNIQUE NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `auth_level` INTEGER NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table des sujets/thèmes
CREATE TABLE `topics` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(100) NOT NULL,
  `content` TEXT,
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
  `topic_id` INT NOT NULL,
  `subscribed_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

INSERT INTO `users` (`email`, `name`, `password`, `auth_level`)
VALUES
('john.doe@example.com', 'John Doe', 'mot_de_passe1', 1),
('jane.smith@example.com', 'Jane Smith', 'mot_de_passe2', 2),
('alex.jones@example.com', 'Alex Jones', 'mot_de_passe3', 1),
('sam.jones@example.com', 'Sam Jones', 'mot_de_passe4', 2),
('emily.white@example.com', 'Emily White', 'mot_de_passe5', 1),
('martin.dupont@example.com', 'Martin Dupont', 'mot_de_passe6', 1),
('claire.lemieux@example.com', 'Claire Lemieux', 'mot_de_passe7', 2),
('pierre.leroux@example.com', 'Pierre Leroux', 'mot_de_passe8', 1),
('sophie.gagnon@example.com', 'Sophie Gagnon', 'mot_de_passe9', 2),
('nicolas.tremblay@example.com', 'Nicolas Tremblay', 'mot_de_passe10', 1);

INSERT INTO `topics` (`title`, `content`)
VALUES
('Langages de Programmation', 'Discussions sur les langages de programmation populaires'),
('Frameworks Web', 'Partagez vos expériences avec les frameworks web'),
('Développement Front-end', 'Conseils et astuces pour le développement front-end'),
('Sécurité Informatique', 'Exploration des meilleures pratiques de sécurité informatique'),
('Intelligence Artificielle', 'Dernières avancées et discussions sur les IA'),
('DevOps', 'Intégration continue, déploiement continu et plus encore'),
('Applications Mobiles', 'Développement applications mobiles et tendances actuelles'),
('Big Data', 'Gestion et analyse de grandes quantités de données'),
('Blockchain', 'Technologie de blockchain et crypto-monnaies'),
('IoT', 'Internet des objets et développement des appareils connectés');

INSERT INTO `posts` (`user_id`, `topic_id`, `title`, `content`)
VALUES
(1, 1, 'Les Nouveautés de Python 4', 'Découvrez les nouvelles fonctionnalités de Python 4'),
(2, 2, 'Comparaison entre React et Vue.js', 'Quel framework JavaScript choisir ?'),
(3, 3, 'Les Meilleures Pratiques CSS', 'Optimisez votre code CSS pour des performances maximales'),
(4, 4, 'Sécurité Web : Protégez vos Applications', 'Conseils pour assurer la sécurité de vos applications web'),
(5, 5, 'Éthique sur les IA', 'Réflexions sur les implications éthiques des intelligences artificielle'),
(6, 6, 'Introduction à Jenkins', 'Automatisez vos processus de développement avec Jenkins'),
(7, 7, 'Développement des Applications Android', 'Conseils pour créer des applications Android performantes'),
(8, 8, 'Apache Hadoop et le Traitement des Données', 'Explorez la gestion de données à grande échelle avec Apache Hadoop'),
(9, 9, 'Comprendre la Technologie Blockchain', 'Principes de base de la blockchain et de la technologie des contrats intelligents'),
(10, 10, 'IoT : Tendances et Défis', 'Découvrez les tendances et les défis actuels');


INSERT INTO `subscriptions` (`user_id`, `topic_id`)
VALUES
(1, 2),
(2, 1),
(3, 1),
(4, 3),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

INSERT INTO `comments` (`post_id`, `user_id`, `content`)
VALUES
(2, 1, 'Apres avoir utilisé React dans plusieurs projets, mais Vue.js semble intéressant.'),
(2, 3, 'Vue.js a une courbe apprentissage plus douce que React.'),
(2, 5, 'Comparaison très utile, merci !'),
(2, 7, 'React reste mon choix préféré.'),
(2, 8, 'Est-ce performant dans des projets de grande envergure ?'),
(3, 2, 'Des conseils CSS toujours utiles, merci !'),
(3, 4, 'Jai parfois du mal avec les sélecteurs CSS, des conseils là-dessus ?'),
(3, 6, 'La gestion des styles est cruciale pour des performances optimales.'),
(3, 8, 'Quels sont vos outils préférés pour optimiser le CSS ?'),
(3, 10, 'Merci pour ces conseils, cela va beaucoup aider dans mon projet actuel.');
