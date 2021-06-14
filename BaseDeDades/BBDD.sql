-- Creacio base de dades
DROP DATABASE IF EXISTS DB;
CREATE DATABASE DB;
USE DB;

DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON * . * TO 'admin'@'localhost';

-- Creacio de taules
DROP TABLE IF EXISTS Usuari CASCADE;
CREATE TABLE Usuari (
  codiAmic VARCHAR(9),
  password VARCHAR(255),
  email VARCHAR(255),
  nickname VARCHAR(255),
  key_bindings VARCHAR(25),
  PRIMARY KEY(codiAmic)
);

DROP TABLE IF EXISTS Canco CASCADE;
CREATE TABLE Canco (
  id_canco VARCHAR(255),
  public BOOLEAN,
  nom VARCHAR(255),
  durada INTEGER,
  creador VARCHAR(9),
  data_creacio TIME,
  PRIMARY KEY(id_canco)
);

DROP TABLE IF EXISTS Reprodueix CASCADE;
CREATE TABLE Reprodueix (
  id_reprodueix SERIAL,
  id_canco VARCHAR(255),
  data_reproduccio VARCHAR(255),
  temps_reproduccio INTEGER,
  PRIMARY KEY(id_reprodueix)
);


DROP TABLE IF EXISTS Amics CASCADE;
CREATE TABLE Amics (
  codiAmic1 VARCHAR(9),
  codiAmic2 VARCHAR(9),
  sender VARCHAR(9),
  pending BOOLEAN,
  PRIMARY KEY(codiAmic1, codiAmic2)
);

DROP TABLE IF EXISTS Nota CASCADE;
CREATE TABLE Nota (
  id_nota VARCHAR(255),
  nom VARCHAR(255),
  inici INTEGER,
  final INTEGER,
  PRIMARY KEY(id_nota)
);

DROP TABLE IF EXISTS cancoNota CASCADE;
CREATE TABLE cancoNota (
  id_canco VARCHAR(255),
  id_nota VARCHAR(255),
  PRIMARY KEY(id_canco, id_nota),
  FOREIGN KEY(id_canco) REFERENCES Canco(id_canco),
  FOREIGN KEY(id_nota) REFERENCES Nota(id_nota)
);

-- Mostra les taules
SELECT * FROM Usuari;
SELECT * FROM Canco;
SELECT * FROM Reprodueix;
SELECT * FROM Amics;
SELECT * FROM Nota;
SELECT * FROM cancoNota;

-- Creacio del usuari
INSERT INTO usuari(codiAmic,password,email,nickname)VALUES('123456788','1234','test@test.com','test');

-- Insert de les tres cançons publiques
INSERT INTO canco(id_canco,public,nom,creador)VALUES('Midi1',1,'Midi1','0');
INSERT INTO canco(id_canco,public,nom,creador)VALUES('Midi2',1,'Midi2','0');
INSERT INTO canco(id_canco,public,nom,creador)VALUES('Midi3',1,'Midi3','0');
