-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 13 mars 2025 à 01:53
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `school`
--

-- --------------------------------------------------------

--
-- Structure de la table `addresses`
--

CREATE TABLE `addresses` (
  `ID` bigint(20) NOT NULL,
  `CITY` varchar(255) DEFAULT NULL,
  `STREET` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `addresses`
--

INSERT INTO `addresses` (`ID`, `CITY`, `STREET`, `zip_code`) VALUES
(3, 'Casablanca', '123 Rue Exemple', '20000'),
(10, 'Casablanca', '123 Rue Exemple', '20000'),
(11, 'Casablanca', '123 Rue Exemple', '20000'),
(12, 'Casablanca', '123 Rue Exemple', '20000'),
(13, 'Casablanca', '123 Rue Exemple', '20000');

-- --------------------------------------------------------

--
-- Structure de la table `courses`
--

CREATE TABLE `courses` (
  `ID` bigint(20) NOT NULL,
  `course_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `courses`
--

INSERT INTO `courses` (`ID`, `course_name`) VALUES
(6, 'Mathematics'),
(7, 'Mathematics'),
(8, 'Mathematics'),
(9, 'Mathematics'),
(10, 'Mathematics'),
(11, 'Physics'),
(12, 'Mathematics'),
(13, 'Mathematics'),
(14, 'Physics'),
(15, 'Mathematics'),
(16, 'Physics');

-- --------------------------------------------------------

--
-- Structure de la table `enrollments`
--

CREATE TABLE `enrollments` (
  `student_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `enrollments`
--

INSERT INTO `enrollments` (`student_id`, `course_id`) VALUES
(11, 11),
(11, 12),
(12, 11),
(12, 12),
(13, 13),
(13, 14),
(14, 13),
(14, 14),
(15, 15),
(15, 16),
(16, 15),
(16, 16);

-- --------------------------------------------------------

--
-- Structure de la table `modules`
--

CREATE TABLE `modules` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `modules`
--

INSERT INTO `modules` (`ID`, `NAME`) VALUES
(6, 'Module1'),
(7, 'Module1'),
(8, 'Module1'),
(9, 'Module1'),
(10, 'Module1'),
(11, 'Module de Mathématiques'),
(12, 'Module de Mathématiques'),
(13, 'Module de Mathématiques');

-- --------------------------------------------------------

--
-- Structure de la table `students`
--

CREATE TABLE `students` (
  `ID` bigint(20) NOT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `module_id` bigint(20) DEFAULT NULL,
  `address_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `students`
--

INSERT INTO `students` (`ID`, `EMAIL`, `NAME`, `module_id`, `address_id`) VALUES
(10, 'ghitaait@example.com', 'Ghita AIT', NULL, 10),
(11, 'amineben@example.com', 'Amine BEN', 11, 11),
(12, 'ghitaait@example.com', 'Ghita AIT', 11, 11),
(13, 'ghitaait@example.com', 'Ghita AIT', 12, 12),
(14, 'amineben@example.com', 'Amine BEN', 12, 12),
(15, 'ghitaait@example.com', 'Ghita AIT', 13, 13),
(16, 'amineben@example.com', 'Amine BEN', 13, 13);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `enrollments`
--
ALTER TABLE `enrollments`
  ADD PRIMARY KEY (`student_id`,`course_id`),
  ADD KEY `FK_enrollments_course_id` (`course_id`);

--
-- Index pour la table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_students_address_id` (`address_id`),
  ADD KEY `FK_students_module_id` (`module_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `courses`
--
ALTER TABLE `courses`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `modules`
--
ALTER TABLE `modules`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `students`
--
ALTER TABLE `students`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `enrollments`
--
ALTER TABLE `enrollments`
  ADD CONSTRAINT `FK_enrollments_course_id` FOREIGN KEY (`course_id`) REFERENCES `courses` (`ID`),
  ADD CONSTRAINT `FK_enrollments_student_id` FOREIGN KEY (`student_id`) REFERENCES `students` (`ID`);

--
-- Contraintes pour la table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `FK_students_address_id` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`ID`),
  ADD CONSTRAINT `FK_students_module_id` FOREIGN KEY (`module_id`) REFERENCES `modules` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
