DROP database IF EXISTS reservation;
#traiter les cas extremes , attention aux @ par exemple

create database reservation ;

-- Suppression des tables existantes pour un déploiement propre
DROP TABLE IF EXISTS Billet;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Horaire;
DROP TABLE IF EXISTS Utilisateur;
DROP TABLE IF EXISTS TypeService;

-- select * from reservation ;
-- Table TypeService : définit les types de services (Cinéma, Bus, Avion, etc.)
CREATE TABLE TypeService (
    id_type INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
) ;

-- Table Utilisateur : enregistre les utilisateurs du système
CREATE TABLE Utilisateur (
    id_utilisateur INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL
) ;
#insert into utilisateur (nom,email,mot_de_passe) values('','','') ;

-- select * from utilisateur ;

-- use reservations ;
-- Table Horaire : contient les créneaux horaires disponibles pour chaque service
CREATE TABLE Horaire (
    id_horaire INT AUTO_INCREMENT PRIMARY KEY,
    id_type INT NOT NULL,
    date DATE NOT NULL,
    heure TIME NOT NULL,
    details VARCHAR(255),
    FOREIGN KEY (id_type) REFERENCES TypeService(id_type)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ;

-- Table Reservation : stocke les réservations effectuées par les utilisateurs
CREATE TABLE Reservation (
    id_reservation INT AUTO_INCREMENT PRIMARY KEY,
    id_utilisateur INT NOT NULL,
    id_horaire INT NOT NULL,
    date_reservation datetime ,
    statut ENUM('confirmée', 'annulée', 'en attente') NOT NULL DEFAULT 'en attente',
    FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_horaire) REFERENCES Horaire(id_horaire)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ;


-- Table Billet : référence le billet PDF généré pour chaque réservation
CREATE TABLE Billet (
    id_billet INT AUTO_INCREMENT PRIMARY KEY,
    id_reservation INT NOT NULL,
    chemin_pdf VARCHAR(255) NOT NULL,
    date_generation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_reservation) REFERENCES Reservation(id_reservation)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ;

INSERT INTO TypeService (nom) VALUES
('Cinéma'),
('Bus'),
('Avion');

INSERT INTO Horaire (date, heure, id_type, details) VALUES
('2025-04-10', '14:00:00', 1, 'Film : Inception - Salle 2'),
('2025-04-10', '16:30:00', 1, 'Film : Le Parrain - Salle 1'),
('2025-04-10', '19:00:00', 1, 'Film : Interstellar - Salle 3'),
('2025-04-11', '11:00:00', 1, 'Film : Coco - Salle 1'),
('2025-04-11', '21:00:00', 1, 'Film : Joker - Salle 2'),

('2025-04-10', '08:00:00', 2, 'Bus Ligne 101 - Montréal à Ottawa'),
('2025-04-10', '10:30:00', 2, 'Bus Ligne 202 - Québec à Trois-Rivières'),
('2025-04-10', '13:00:00', 2, 'Bus Ligne 303 - Laval à Gatineau'),
('2025-04-11', '15:00:00', 2, 'Bus Ligne 404 - Sherbrooke à Montréal'),
('2025-04-11', '18:00:00', 2, 'Bus Ligne 505 - Longueuil à Québec'),

('2025-04-10', '06:45:00', 3, 'Vol AC720 - Montréal à Toronto'),
('2025-04-10', '09:15:00', 3, 'Vol WS305 - Ottawa à Vancouver'),
('2025-04-10', '12:00:00', 3, 'Vol TS110 - Québec à Cancun'),
('2025-04-10', '15:30:00', 3, 'Vol AC850 - Montréal à Paris'),
('2025-04-10', '20:00:00', 3, 'Vol WS710 - Ottawa à New York'),

('2025-04-11', '07:30:00', 3, 'Vol AC901 - Montréal à Calgary'),
('2025-04-11', '10:45:00', 3, 'Vol TS330 - Québec à Punta Cana'),
('2025-04-11', '13:20:00', 3, 'Vol AC300 - Ottawa à Halifax'),
('2025-04-11', '17:45:00', 3, 'Vol WS201 - Toronto à Vancouver'),
('2025-04-11', '22:00:00', 3, 'Vol AC100 - Montréal à Tokyo');
