-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library_db
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `pub_date` date DEFAULT NULL,
  `rate` int DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `Locations_id` int NOT NULL,
  `type_id` int NOT NULL,
  `publisher_id` int NOT NULL,
  `author_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Locations_id` (`Locations_id`),
  KEY `fk_Books_Types1_idx` (`type_id`),
  KEY `fk_Books_Publishers1_idx` (`publisher_id`),
  KEY `fk_Books_Authors1_idx` (`author_id`),
  CONSTRAINT `fk_Books_Authors1` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_Books_Publishers1` FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`publisher_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_Books_Types1` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'살인자의 기억법','2013-07-25',5,1,100101,4,1,1),(2,'태백산맥','1987-10-01',4,1,100102,4,4,2),(3,'7년의 밤','2011-05-15',4,1,100303,4,2,3),(4,'사피엔스','2015-02-02',5,1,200304,3,5,4),(5,'무소의 뿔처럼 혼자서 가라','1994-02-02',4,1,200301,4,1,5),(6,'1984','1949-06-08',5,1,300706,4,2,6),(7,'안나 카레니나','1877-01-01',5,1,105007,3,2,7),(8,'인간 실격','1948-06-05',5,1,100608,3,8,8),(9,'반지의 제왕','1954-07-29',5,0,101109,4,7,9),(10,'위대한 개츠비','1925-04-10',4,0,110510,4,6,10),(11,'존재의 세가지 거짓말','1925-05-12',5,1,101510,4,6,11),(12,'채식주의자','2022-03-28',5,1,201715,1,4,12),(13,'문맹','2020-10-12',5,1,105706,1,6,11),(14,'소년이 온다','2014-05-19',5,1,141017,1,4,12),(15,'오만과 편견','1998-12-14',4,1,141049,1,5,13);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-03 10:00:14
