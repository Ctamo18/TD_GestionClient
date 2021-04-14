-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Jeu 22 Mars 2018 à 20:29
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `bdgsf`
--

CREATE DATABASE IF NOT EXISTS `bdgsf`;
USE `bdgsf`;

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`brou`@`localhost` PROCEDURE `curseurEx1` ()  BEGIN
 DECLARE fin BOOLEAN DEFAULT FALSE; 
 DECLARE cnom VARCHAR(20) ;
 DECLARE liste, liste1 VARCHAR(200);
 DECLARE ctel INT ;
 DECLARE curCl CURSOR FOR 
  SELECT nom, tel 
  FROM Client;
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = TRUE; 
 OPEN curCl;
 REPEAT
  FETCH curCl INTO cnom, ctel;
  SET liste1 = CONCAT_WS(' ', cnom, ctel) ;
  SET liste = CONCAT_WS(' ', liste, liste1,'\n') ;
 UNTIL fin END REPEAT; 
 CLOSE curCl;
 SELECT liste AS 'Contacts clients';
END$$

CREATE DEFINER=`brou`@`localhost` PROCEDURE `libProduit` (IN `numP` INT, OUT `design` VARCHAR(15))  BEGIN
   SELECT designation INTO design
    FROM Produit
    WHERE numProd = numP;
END$$

CREATE DEFINER=`brou`@`localhost` PROCEDURE `montantTPOFC` (IN `numC` INT, IN `numCo` INT, OUT `montant` INT)  BEGIN 
    DECLARE montant2 INT;
	DECLARE fin BOOLEAN DEFAULT False;
	DECLARE curM CURSOR FOR
	    SELECT qteCom * prixUnit as montantligne
		FROM commande co, produit pr, detailCom d
		where co.numCom = d.numCom
		and    d.numProd = pr.numProd
		and   co.numCl = numC
		and    co.numCom = numCo ;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = True;
	OPEN curM;
	
	CREATE TABLE resultat_montant(montantligne INT) ENGINE = INNODB ;
	
	REPEAT
	  FETCH curM INTO montant2;
	  INSERT INTO resultat_montant VALUES(montant2);
	  UNTIL fin END REPEAT;
	  CLOSE curM;
	  
	 SET montant=(SELECT sum(montantligne) FROM resultat_montant);
	  IF montant > 1000 THEN
	  SET montant = montant + montant*0.11 ;
	  END IF;
	  SELECT montant ;
	  
  END$$

--
-- Fonctions
--
CREATE DEFINER=`brou`@`localhost` FUNCTION `calcul` (`numP` INT) RETURNS INT(10) BEGIN
   DECLARE som INT default 0;
   DECLARE prixT INT default 0;
   
  
	SELECT  d.qteCom*Pr.prixUnit into prixT
	 FROM commande co, produit Pr, detailCom d
	 where co.numCom= d.numCom and 
	       d.numProd=Pr.numProd and 
		   co.numCl=1 and  co.numCom= 1 and 
		   Pr.numProd= numP;
	 SET som = som + prixT;
   
    RETURN som;
END$$

CREATE DEFINER=`brou`@`localhost` FUNCTION `libProduit` (`numP` INT) RETURNS VARCHAR(10) CHARSET latin1 BEGIN
   DECLARE design VARCHAR(10);
   SELECT designation INTO design
    FROM Produit
    WHERE numProd = numP;
    RETURN design;
END$$

CREATE DEFINER=`brou`@`localhost` FUNCTION `montantCmde` (`nCl` INT, `nCo` INT, `nPr` INT) RETURNS FLOAT BEGIN
   DECLARE montant FLOAT DEFAULT 0.0;
   SELECT qteCom*prixUnit INTO montant
    FROM Commande co, DetailCom d, Produit pr
    WHERE co.numCom = d.numCom AND 
	      d.numProd = pr.numProd AND
		  co.numCl= nCl AND co.numCom = nCo AND pr.numProd = nPr;
	
    RETURN montant;
END$$

CREATE DEFINER=`brou`@`localhost` FUNCTION `montantTPOFC` (`numC` INT, `numCo` INT) RETURNS INT(11) BEGIN 
    DECLARE montantCom INT;
	DECLARE fin BOOLEAN DEFAULT False;
	DECLARE curM CURSOR FOR
	    SELECT qteCom * prixUnit as montantligne
		FROM commande co, produit pr, detailCom d
		where co.numCom = d.numCom
		and    d.numProd = pr.numProd
		and   co.numCl = numC
		and    co.numCom = numCo ;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin = True;
	OPEN curM;
	
	
	
	REPEAT
	  FETCH curM INTO montantCom;
	  INSERT INTO resultat_montant VALUES(montantCom);
	UNTIL fin END REPEAT;
	CLOSE curM;
	  
	SET montantCom=(SELECT sum(montantligne) FROM resultat_montant);
	  
	RETURN montantCom ;
	 
  END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `numCl` int(11) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `adresse` varchar(30) DEFAULT NULL,
  `tel` int(11) DEFAULT NULL,
  `fax` int(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `photo` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`numCl`, `nom`, `adresse`, `tel`, `fax`, `email`, `photo`) VALUES
(1, 'Brou', 'Yakro', 30642020, 30642020, 'brou@yahoo.fr', 'brou.jpg'),
(2, 'Lago', 'Abidjan', 22343434, 22343434, NULL, 'lago.jpg'),
(3, 'Lokpo', 'Bouaké', 30636565, 30636565, NULL, 'lokpo.jpg'),
(4, 'Mado', 'Daloa', 34343434, 34343434, 'mado@gmail.com', 'mado.jpg'),
(5, 'Safi', 'Yakro', NULL, NULL, NULL, 'safisaf.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `numCom` int(11) NOT NULL,
  `dateCom` date NOT NULL,
  `numCl` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `commande`
--

INSERT INTO `commande` (`numCom`, `dateCom`, `numCl`) VALUES
(1, '2014-11-13', 1),
(2, '2014-05-12', 1),
(3, '2014-11-13', 1),
(4, '2015-05-15', 2),
(5, '2015-05-18', 2),
(6, '2015-11-18', 3),
(7, '2015-04-05', 3),
(8, '2016-05-06', 3),
(9, '2016-05-06', 4),
(10, '2016-05-12', 4);

-- --------------------------------------------------------

--
-- Structure de la table `detailcom`
--

CREATE TABLE `detailcom` (
  `numCom` int(11) NOT NULL,
  `numProd` int(11) NOT NULL,
  `numLigne` int(11) NOT NULL,
  `qteCom` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `detailcom`
--

INSERT INTO `detailcom` (`numCom`, `numProd`, `numLigne`, `qteCom`) VALUES
(1, 1, 1, 10),
(1, 2, 2, 4),
(2, 2, 3, 3),
(3, 1, 4, 2),
(3, 2, 5, 6),
(3, 3, 6, 3),
(4, 2, 7, 7),
(5, 2, 8, 3),
(5, 3, 9, 6),
(6, 2, 10, 1),
(7, 3, 11, 5),
(8, 2, 12, 8),
(9, 1, 13, 3),
(10, 2, 14, 4);

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `prenom` char(30) NOT NULL DEFAULT ''
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `personne`
--

INSERT INTO `personne` (`prenom`) VALUES
('Cyril'),
('Fabien'),
('Guillaume'),
('Laurence'),
('Loic'),
('Olivier'),
('Omar'),
('Pierre'),
('Romain'),
('Sandrine'),
('Sarah');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `numProd` int(11) NOT NULL,
  `designation` varchar(15) NOT NULL,
  `prixUnit` float DEFAULT NULL,
  `qteStock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `produit`
--

INSERT INTO `produit` (`numProd`, `designation`, `prixUnit`, `qteStock`) VALUES
(1, 'Savon', 300, 15),
(2, 'Eau Awa', 400, 29),
(3, 'Sardine', 450, 10),
(4, 'Assiette', 245, 27),
(5, 'Yaourt', 250, 50),
(6, 'Beurre', 1500, 85);

-- --------------------------------------------------------

--
-- Structure de la table `resultat_montant`
--

CREATE TABLE `resultat_montant` (
  `montantligne` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `resultat_montant`
--

INSERT INTO `resultat_montant` (`montantligne`) VALUES
(3000),
(1600),
(1600),
(3000),
(1600),
(1600);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`numCl`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`numCom`),
  ADD KEY `numCl` (`numCl`);

--
-- Index pour la table `detailcom`
--
ALTER TABLE `detailcom`
  ADD PRIMARY KEY (`numCom`,`numProd`),
  ADD UNIQUE KEY `numLigne` (`numLigne`),
  ADD KEY `DetailComProduitFK` (`numProd`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`prenom`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`numProd`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `detailcom`
--
ALTER TABLE `detailcom`
  MODIFY `numLigne` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `CommandeClientFK` FOREIGN KEY (`numCl`) REFERENCES `client` (`numCl`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `detailcom`
--
ALTER TABLE `detailcom`
  ADD CONSTRAINT `DetailComCommandeFK` FOREIGN KEY (`numCom`) REFERENCES `commande` (`numCom`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `DetailComProduitFK` FOREIGN KEY (`numProd`) REFERENCES `produit` (`numProd`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
