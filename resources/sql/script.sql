-- -----------------------------------------------------
-- `subject`
-- -----------------------------------------------------

CREATE TABLE `subject` (
  `id_subject` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(20) NULL,
  `description` VARCHAR(255) NULL,
  PRIMARY KEY (`id_subject`));


-- -----------------------------------------------------
-- `subscription`
-- -----------------------------------------------------

CREATE TABLE `subscription` (
  `id_subscription` INT NOT NULL AUTO_INCREMENT,
  `subject_id_subject` INT NOT NULL,
  PRIMARY KEY (`id_subscription`));

-- -----------------------------------------------------
-- Table `feed`
-- -----------------------------------------------------

CREATE TABLE `feed` (
  `id_feed` INT NOT NULL AUTO_INCREMENT,
  `subscription_id_subscription` INT NOT NULL,
  PRIMARY KEY (`id_feed`));

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------

CREATE TABLE `user` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `lastName` VARCHAR(20) NOT NULL,
  `email` VARCHAR(20) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `feed_id_feed` INT NOT NULL,
  PRIMARY KEY (`id_user`, `feed_id_feed`));

-- -----------------------------------------------------
-- Table `post`
-- -----------------------------------------------------

CREATE TABLE `post` (
  `id_post` INT NOT NULL,
  `title` VARCHAR(20) NULL,
  `date` DATETIME NULL,
  `author` VARCHAR(15) NULL,
  `content` TEXT(500) NULL,
  `comments` VARCHAR(45) NULL,
  `subject_id_subject` INT NOT NULL,
  PRIMARY KEY (`id_post`, `subject_id_subject`));
  

-- -----------------------------------------------------
-- INSERT DATA
-- -----------------------------------------------------

USE `mdd_test`;

INSERT INTO `subject`(`id_subject`, `title`, `description`)
VALUES(1, 'JavaScript', "JavaScript est un langage de programmation essentiel pour le développement web moderne. Il permet de créer des expériences web interactives et dynamiques, de concevoir des applications web complexes");

INSERT INTO `subscription`(`id_subscription`, `subject_id_subject`)
VALUES(1, 1);

INSERT INTO `feed`(`id_feed`, `subscription_id_subscription`)
VALUES(1, 1);

INSERT INTO `post`(`id_post`, `title`, `date`, `author`, `content`, `comments`, `subject_id_subject`)
VALUES(1, 'Les avantages de JavaScript', '2023-09-08T15:17:00', 'Marvin', "Les avantages de JavaScript sont nombreux et font de ce langage de programmation un élément incontournable dans le monde du développement web. Voici quelques-uns des principaux avantages de JavaScript :

Interactivité en temps réel : JavaScript permet de créer des pages web interactives où les éléments de la page peuvent réagir en temps réel aux actions de l'utilisateur. Cela crée des expériences utilisateur plus dynamiques et engageantes.

Exécution côté client : JavaScript s'exécute côté client, ce qui signifie que les calculs et les interactions sont traités par le navigateur de l'utilisateur, allégeant ainsi la charge du serveur et améliorant la réactivité des applications.

Manipulation du DOM : JavaScript permet de manipuler le Document Object Model (DOM), ce qui facilite l'ajout, la suppression et la modification d'éléments HTML et de styles sur une page web sans avoir à recharger la page.

Bibliothèques et frameworks : Il existe de nombreuses bibliothèques et frameworks JavaScript populaires, tels que React, Angular et Vue.js, qui simplifient le développement d'applications web complexes en offrant des composants réutilisables et une structure organisée.

Grande communauté et support : JavaScript bénéficie d'une immense communauté de développeurs, de forums de discussion actifs et de ressources en ligne abondantes. Cela facilite l'apprentissage et la résolution de problèmes.

Compatibilité multiplateforme : JavaScript est pris en charge par tous les principaux navigateurs web, garantissant une compatibilité élevée et une expérience utilisateur cohérente.

Applications côté serveur : Grâce à Node.js, JavaScript peut être utilisé côté serveur pour créer des applications web, des serveurs de chat, des API RESTful, etc., offrant ainsi une stack de développement complète.

Évolutivité : Les normes telles qu'ECMAScript (ES6 et versions ultérieures) apportent de nouvelles fonctionnalités et des améliorations de la syntaxe, permettant aux développeurs de créer un code plus propre, plus modulaire et plus maintenable.

Rapidité d'exécution : Les navigateurs modernes ont des moteurs JavaScript très performants qui rendent l'exécution du code JavaScript rapide et efficace, ce qui est essentiel pour des applications web réactives.

Intégration avec d'autres technologies : JavaScript peut être facilement intégré à d'autres technologies web, telles que HTML, CSS, AJAX, et des bases de données, pour créer des applications web complètes.

En résumé, JavaScript est un langage de programmation polyvalent et puissant qui offre une multitude d'avantages pour le développement web. Que ce soit pour créer des sites web interactifs, des applications web complexes, ou des applications côté serveur, JavaScript reste un choix incontournable pour les développeurs du monde entier. Sa popularité continue de croître, ce qui en fait un atout précieux pour ceux qui souhaitent s'aventurer dans le monde du développement web.",'',1);


INSERT INTO `user`(`id_user`, `username`, `lastName`, `email`, `password`, `feed_id_feed`)
VALUES(1, 'test', 'TEST', 'test@email.com', 'test!1234',1);
