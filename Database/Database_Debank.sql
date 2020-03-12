create database Getbank
;
use Getbank
;
CREATE TABLE PERSONEELSGEGEVENS(
PERSONEELSID SMALLINT NOT NULL,
VOORNAAM CHAR(35) NOT NULL,
ACHTERNAAM CHAR(35) NOT NULL,
GEBOORTEDATUM DATE NOT NULL,
ADRES CHAR(50) NOT NULL,
WOONPLAATS CHAR(35) NOT NULL,
POSTCODE CHAR(7) NOT NULL,
GESLACHT CHAR(1) NOT NULL,
TELEFOONNR INTEGER NOT NULL,
EMAILADRES CHAR(50) NOT NULL,
PRIMARY KEY (PERSONEELSID) )
;
CREATE TABLE KLANTGEGEVENS(
KLANTID SMALLINT NOT NULL,
REKENINGNUMMER CHAR(18) NOT NULL,
VOORNAAM CHAR(35) NOT NULL,
ACHTERNAAM CHAR(35) NOT NULL,
GEBOORTEDATUM DATE NOT NULL,
ADRES CHAR(50) NOT NULL,
WOONPLAATS CHAR(35) NOT NULL,
POSTCODE CHAR(7) NOT NULL,
GESLACHT CHAR(1) NOT NULL,
TELEFOONNR INTEGER NOT NULL,
EMAILADRES CHAR(50) NOT NULL,
PRIMARY KEY (KLANTID) )
;
CREATE TABLE REKENINGEN(
KLANTID SMALLINT NOT NULL,
REKENINGNUMMER CHAR(18) NOT NULL,
KAARTSOORT CHAR(15) NOT NULL,
PRIMARY KEY (REKENINGNUMMER),
FOREIGN KEY (KLANTID) REFERENCES KLANTGEGEVENS(KLANTID) )
;
CREATE TABLE SALDOS(
REKENINGNUMMER CHAR(18) NOT NULL,
SALDO INTEGER NOT NULL,
PRIMARY KEY(REKENINGNUMMER),
FOREIGN KEY(REKENINGNUMMER) REFERENCES REKENINGEN(REKENINGNUMMER))
;
CREATE TABLE BANKPAS(
KAARTNUMMER INTEGER NOT NULL,
REKENINGNUMMER CHAR(18) NOT NULL,
PINCODE SMALLINT NOT NULL,
PRIMARY KEY(KAARTNUMMER),
FOREIGN KEY(REKENINGNUMMER) REFERENCES REKENINGEN(REKENINGNUMMER))
;

