-- MySQL dump 10.13  Distrib 5.5.38, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ST11429074
-- ------------------------------------------------------
-- Server version	5.5.38-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `one`
--

DROP TABLE IF EXISTS `one`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `one` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `grade1` int(11) NOT NULL,
  `grade2` int(11) NOT NULL,
  `grade3` int(11) NOT NULL,
  `gradeExam` int(11) NOT NULL,
  `gradeFinal` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `one`
--

LOCK TABLES `one` WRITE;
/*!40000 ALTER TABLE `one` DISABLE KEYS */;
INSERT INTO `one` VALUES (1,'Caleb',71,56,31,95,72),(2,'Miriam',22,11,26,1,10),(3,'Priscilla',22,68,71,94,77),(4,'Clay',57,60,94,73,73),(5,'Tricia',6,47,36,37,35),(6,'Harold',99,57,28,61,57),(7,'Sergio',75,31,23,67,51),(8,'Verna',43,5,5,22,17),(9,'Robyn',85,66,57,19,42),(10,'Carolyn',45,78,12,13,29),(11,'Cecilia',76,74,89,0,40),(12,'Robin',61,32,71,21,37),(13,'Roberto',14,95,50,89,74),(14,'Brent',46,8,32,96,60),(15,'Monique',49,98,40,76,70),(16,'Andy',99,0,50,70,54),(17,'Mathew',92,37,12,41,39),(18,'Mandy',0,57,40,58,48),(19,'Wilfred',17,35,72,9,27),(20,'Andrew',2,59,18,8,19),(21,'Ida',48,70,12,49,45),(22,'Bryan',80,89,26,19,40),(23,'Sherry',66,89,33,6,34),(24,'Irving',30,17,71,72,56),(25,'Shelia',57,38,46,36,40),(26,'Trevor',79,43,81,70,67),(27,'Gerardo',60,5,74,31,37),(28,'Dean',23,76,72,14,38),(29,'Tim',84,59,45,52,55),(30,'Opal',62,57,77,11,38),(31,'Eloise',45,1,41,6,15),(32,'Andres',69,83,74,16,46),(33,'Chester',78,53,32,64,56),(34,'Lynda',98,5,13,25,25),(35,'Rickey',86,87,32,39,51),(36,'Cameron',44,22,91,36,45),(37,'Abraham',29,20,98,54,53),(38,'Lindsay',57,62,92,61,67),(39,'Wendy',10,41,13,20,21),(40,'Seth',56,21,94,82,69),(41,'Lindsey',26,60,53,36,43),(42,'Carroll',21,54,33,28,33),(43,'Angela',79,47,41,32,41),(44,'Lena',74,25,0,29,26),(45,'Rolando',49,51,46,27,37),(46,'Antonia',60,10,76,59,52),(47,'Alex',99,83,89,94,91),(48,'Christina',26,34,4,84,52),(49,'Jacob',70,56,32,19,34),(50,'Corey',73,66,10,35,40),(51,'Jordan',86,8,80,2,27),(52,'Sylvester',16,37,2,65,41),(53,'Patricia',59,1,80,0,22),(54,'Ronnie',97,51,41,40,48),(55,'Marcella',41,35,47,11,26),(56,'Sarah',89,94,17,98,80),(57,'Christopher',52,17,38,38,35),(58,'Kim',9,19,71,20,28),(59,'Cathy',46,0,57,82,57),(60,'Shannon',94,94,25,15,40);
/*!40000 ALTER TABLE `one` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-06 14:26:19
