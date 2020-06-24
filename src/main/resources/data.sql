-- MySQL dump 10.13  Distrib 5.7.30, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: splendor_database
-- ------------------------------------------------------
-- Server version	5.7.30-0ubuntu0.18.04.1

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
-- Table structure for table `card_cost`
--

DROP TABLE IF EXISTS `card_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card_cost` (
  `card_card_id` bigint(20) NOT NULL,
  `cost` int(11) DEFAULT NULL,
  `token_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`card_card_id`,`token_type`),
  CONSTRAINT `FKq7ttjnqugxdu2oe2vq2vbx2va` FOREIGN KEY (`card_card_id`) REFERENCES `cards` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card_cost`
--

LOCK TABLES `card_cost` WRITE;
/*!40000 ALTER TABLE `card_cost` DISABLE KEYS */;
INSERT INTO `card_cost` VALUES (1,1,'DIAMOND'),(1,1,'EMERALD'),(1,0,'ONYX'),(1,1,'RUBY'),(1,1,'SAPPHIRE'),(2,1,'DIAMOND'),(2,1,'EMERALD'),(2,0,'ONYX'),(2,1,'RUBY'),(2,2,'SAPPHIRE'),(3,2,'DIAMOND'),(3,0,'EMERALD'),(3,0,'ONYX'),(3,1,'RUBY'),(3,2,'SAPPHIRE'),(4,0,'DIAMOND'),(4,1,'EMERALD'),(4,1,'ONYX'),(4,3,'RUBY'),(4,0,'SAPPHIRE'),(5,0,'DIAMOND'),(5,2,'EMERALD'),(5,0,'ONYX'),(5,1,'RUBY'),(5,0,'SAPPHIRE'),(6,2,'DIAMOND'),(6,2,'EMERALD'),(6,0,'ONYX'),(6,0,'RUBY'),(6,0,'SAPPHIRE'),(7,0,'DIAMOND'),(7,3,'EMERALD'),(7,0,'ONYX'),(7,0,'RUBY'),(7,0,'SAPPHIRE'),(8,0,'DIAMOND'),(8,0,'EMERALD'),(8,0,'ONYX'),(8,0,'RUBY'),(8,4,'SAPPHIRE'),(9,1,'DIAMOND'),(9,1,'EMERALD'),(9,1,'ONYX'),(9,1,'RUBY'),(9,0,'SAPPHIRE'),(10,1,'DIAMOND'),(10,1,'EMERALD'),(10,1,'ONYX'),(10,2,'RUBY'),(10,0,'SAPPHIRE'),(11,1,'DIAMOND'),(11,2,'EMERALD'),(11,0,'ONYX'),(11,2,'RUBY'),(11,0,'SAPPHIRE'),(12,0,'DIAMOND'),(12,3,'EMERALD'),(12,0,'ONYX'),(12,1,'RUBY'),(12,1,'SAPPHIRE'),(13,1,'DIAMOND'),(13,0,'EMERALD'),(13,2,'ONYX'),(13,0,'RUBY'),(13,0,'SAPPHIRE'),(14,0,'DIAMOND'),(14,2,'EMERALD'),(14,2,'ONYX'),(14,0,'RUBY'),(14,0,'SAPPHIRE'),(15,0,'DIAMOND'),(15,0,'EMERALD'),(15,3,'ONYX'),(15,0,'RUBY'),(15,0,'SAPPHIRE'),(16,0,'DIAMOND'),(16,0,'EMERALD'),(16,0,'ONYX'),(16,4,'RUBY'),(16,0,'SAPPHIRE'),(17,0,'DIAMOND'),(17,1,'EMERALD'),(17,1,'ONYX'),(17,1,'RUBY'),(17,1,'SAPPHIRE'),(18,0,'DIAMOND'),(18,2,'EMERALD'),(18,1,'ONYX'),(18,1,'RUBY'),(18,1,'SAPPHIRE'),(19,0,'DIAMOND'),(19,2,'EMERALD'),(19,1,'ONYX'),(19,0,'RUBY'),(19,2,'SAPPHIRE'),(20,3,'DIAMOND'),(20,0,'EMERALD'),(20,1,'ONYX'),(20,0,'RUBY'),(20,1,'SAPPHIRE'),(21,0,'DIAMOND'),(21,0,'EMERALD'),(21,1,'ONYX'),(21,2,'RUBY'),(21,0,'SAPPHIRE'),(22,0,'DIAMOND'),(22,0,'EMERALD'),(22,2,'ONYX'),(22,0,'RUBY'),(22,2,'SAPPHIRE'),(23,0,'DIAMOND'),(23,0,'EMERALD'),(23,0,'ONYX'),(23,0,'RUBY'),(23,3,'SAPPHIRE'),(24,0,'DIAMOND'),(24,4,'EMERALD'),(24,0,'ONYX'),(24,0,'RUBY'),(24,0,'SAPPHIRE'),(25,1,'DIAMOND'),(25,0,'EMERALD'),(25,1,'ONYX'),(25,1,'RUBY'),(25,1,'SAPPHIRE'),(26,1,'DIAMOND'),(26,0,'EMERALD'),(26,2,'ONYX'),(26,1,'RUBY'),(26,1,'SAPPHIRE'),(27,0,'DIAMOND'),(27,0,'EMERALD'),(27,2,'ONYX'),(27,2,'RUBY'),(27,1,'SAPPHIRE'),(28,1,'DIAMOND'),(28,1,'EMERALD'),(28,0,'ONYX'),(28,0,'RUBY'),(28,3,'SAPPHIRE'),(29,2,'DIAMOND'),(29,0,'EMERALD'),(29,0,'ONYX'),(29,0,'RUBY'),(29,1,'SAPPHIRE'),(30,0,'DIAMOND'),(30,0,'EMERALD'),(30,0,'ONYX'),(30,2,'RUBY'),(30,2,'SAPPHIRE'),(31,0,'DIAMOND'),(31,0,'EMERALD'),(31,0,'ONYX'),(31,3,'RUBY'),(31,0,'SAPPHIRE'),(32,0,'DIAMOND'),(32,0,'EMERALD'),(32,4,'ONYX'),(32,0,'RUBY'),(32,0,'SAPPHIRE'),(33,1,'DIAMOND'),(33,1,'EMERALD'),(33,1,'ONYX'),(33,0,'RUBY'),(33,1,'SAPPHIRE'),(34,2,'DIAMOND'),(34,1,'EMERALD'),(34,1,'ONYX'),(34,0,'RUBY'),(34,1,'SAPPHIRE'),(35,2,'DIAMOND'),(35,1,'EMERALD'),(35,2,'ONYX'),(35,0,'RUBY'),(35,0,'SAPPHIRE'),(36,1,'DIAMOND'),(36,0,'EMERALD'),(36,3,'ONYX'),(36,1,'RUBY'),(36,0,'SAPPHIRE'),(37,0,'DIAMOND'),(37,1,'EMERALD'),(37,0,'ONYX'),(37,0,'RUBY'),(37,2,'SAPPHIRE'),(38,2,'DIAMOND'),(38,0,'EMERALD'),(38,0,'ONYX'),(38,2,'RUBY'),(38,0,'SAPPHIRE'),(39,3,'DIAMOND'),(39,0,'EMERALD'),(39,0,'ONYX'),(39,0,'RUBY'),(39,0,'SAPPHIRE'),(40,4,'DIAMOND'),(40,0,'EMERALD'),(40,0,'ONYX'),(40,0,'RUBY'),(40,0,'SAPPHIRE'),(41,3,'DIAMOND'),(41,2,'EMERALD'),(41,0,'ONYX'),(41,0,'RUBY'),(41,2,'SAPPHIRE'),(42,3,'DIAMOND'),(42,3,'EMERALD'),(42,2,'ONYX'),(42,0,'RUBY'),(42,0,'SAPPHIRE'),(43,0,'DIAMOND'),(43,4,'EMERALD'),(43,0,'ONYX'),(43,2,'RUBY'),(43,1,'SAPPHIRE'),(44,0,'DIAMOND'),(44,5,'EMERALD'),(44,0,'ONYX'),(44,3,'RUBY'),(44,0,'SAPPHIRE'),(45,5,'DIAMOND'),(45,0,'EMERALD'),(45,0,'ONYX'),(45,0,'RUBY'),(45,0,'SAPPHIRE'),(46,0,'DIAMOND'),(46,0,'EMERALD'),(46,6,'ONYX'),(46,0,'RUBY'),(46,0,'SAPPHIRE'),(47,0,'DIAMOND'),(47,2,'EMERALD'),(47,0,'ONYX'),(47,3,'RUBY'),(47,2,'SAPPHIRE'),(48,0,'DIAMOND'),(48,3,'EMERALD'),(48,3,'ONYX'),(48,0,'RUBY'),(48,2,'SAPPHIRE'),(49,5,'DIAMOND'),(49,0,'EMERALD'),(49,0,'ONYX'),(49,0,'RUBY'),(49,3,'SAPPHIRE'),(50,2,'DIAMOND'),(50,0,'EMERALD'),(50,4,'ONYX'),(50,1,'RUBY'),(50,0,'SAPPHIRE'),(51,0,'DIAMOND'),(51,0,'EMERALD'),(51,0,'ONYX'),(51,0,'RUBY'),(51,5,'SAPPHIRE'),(52,0,'DIAMOND'),(52,0,'EMERALD'),(52,0,'ONYX'),(52,0,'RUBY'),(52,6,'SAPPHIRE'),(53,0,'DIAMOND'),(53,3,'EMERALD'),(53,2,'ONYX'),(53,2,'RUBY'),(53,0,'SAPPHIRE'),(54,2,'DIAMOND'),(54,0,'EMERALD'),(54,0,'ONYX'),(54,3,'RUBY'),(54,3,'SAPPHIRE'),(55,0,'DIAMOND'),(55,1,'EMERALD'),(55,2,'ONYX'),(55,4,'RUBY'),(55,0,'SAPPHIRE'),(56,0,'DIAMOND'),(56,0,'EMERALD'),(56,3,'ONYX'),(56,5,'RUBY'),(56,0,'SAPPHIRE'),(57,0,'DIAMOND'),(57,0,'EMERALD'),(57,0,'ONYX'),(57,5,'RUBY'),(57,0,'SAPPHIRE'),(58,6,'DIAMOND'),(58,0,'EMERALD'),(58,0,'ONYX'),(58,0,'RUBY'),(58,0,'SAPPHIRE'),(59,3,'DIAMOND'),(59,2,'EMERALD'),(59,0,'ONYX'),(59,3,'RUBY'),(59,0,'SAPPHIRE'),(60,2,'DIAMOND'),(60,0,'EMERALD'),(60,2,'ONYX'),(60,0,'RUBY'),(60,3,'SAPPHIRE'),(61,4,'DIAMOND'),(61,0,'EMERALD'),(61,1,'ONYX'),(61,0,'RUBY'),(61,2,'SAPPHIRE'),(62,0,'DIAMOND'),(62,3,'EMERALD'),(62,0,'ONYX'),(62,0,'RUBY'),(62,5,'SAPPHIRE'),(63,0,'DIAMOND'),(63,5,'EMERALD'),(63,0,'ONYX'),(63,0,'RUBY'),(63,0,'SAPPHIRE'),(64,0,'DIAMOND'),(64,6,'EMERALD'),(64,0,'ONYX'),(64,0,'RUBY'),(64,0,'SAPPHIRE'),(65,2,'DIAMOND'),(65,0,'EMERALD'),(65,3,'ONYX'),(65,2,'RUBY'),(65,0,'SAPPHIRE'),(66,0,'DIAMOND'),(66,0,'EMERALD'),(66,3,'ONYX'),(66,2,'RUBY'),(66,3,'SAPPHIRE'),(67,1,'DIAMOND'),(67,2,'EMERALD'),(67,0,'ONYX'),(67,0,'RUBY'),(67,4,'SAPPHIRE'),(68,3,'DIAMOND'),(68,0,'EMERALD'),(68,5,'ONYX'),(68,0,'RUBY'),(68,0,'SAPPHIRE'),(69,0,'DIAMOND'),(69,0,'EMERALD'),(69,5,'ONYX'),(69,0,'RUBY'),(69,0,'SAPPHIRE'),(70,0,'DIAMOND'),(70,0,'EMERALD'),(70,0,'ONYX'),(70,6,'RUBY'),(70,0,'SAPPHIRE'),(71,3,'DIAMOND'),(71,5,'EMERALD'),(71,0,'ONYX'),(71,3,'RUBY'),(71,3,'SAPPHIRE'),(72,0,'DIAMOND'),(72,0,'EMERALD'),(72,0,'ONYX'),(72,7,'RUBY'),(72,0,'SAPPHIRE'),(73,0,'DIAMOND'),(73,3,'EMERALD'),(73,3,'ONYX'),(73,6,'RUBY'),(73,0,'SAPPHIRE'),(74,0,'DIAMOND'),(74,0,'EMERALD'),(74,3,'ONYX'),(74,7,'RUBY'),(74,0,'SAPPHIRE'),(75,3,'DIAMOND'),(75,3,'EMERALD'),(75,5,'ONYX'),(75,3,'RUBY'),(75,0,'SAPPHIRE'),(76,7,'DIAMOND'),(76,0,'EMERALD'),(76,0,'ONYX'),(76,0,'RUBY'),(76,0,'SAPPHIRE'),(77,6,'DIAMOND'),(77,0,'EMERALD'),(77,3,'ONYX'),(77,0,'RUBY'),(77,3,'SAPPHIRE'),(78,7,'DIAMOND'),(78,0,'EMERALD'),(78,0,'ONYX'),(78,0,'RUBY'),(78,3,'SAPPHIRE'),(79,0,'DIAMOND'),(79,3,'EMERALD'),(79,3,'ONYX'),(79,5,'RUBY'),(79,3,'SAPPHIRE'),(80,0,'DIAMOND'),(80,0,'EMERALD'),(80,7,'ONYX'),(80,0,'RUBY'),(80,0,'SAPPHIRE'),(81,3,'DIAMOND'),(81,0,'EMERALD'),(81,6,'ONYX'),(81,3,'RUBY'),(81,0,'SAPPHIRE'),(82,3,'DIAMOND'),(82,0,'EMERALD'),(82,7,'ONYX'),(82,0,'RUBY'),(82,0,'SAPPHIRE'),(83,5,'DIAMOND'),(83,0,'EMERALD'),(83,3,'ONYX'),(83,3,'RUBY'),(83,3,'SAPPHIRE'),(84,0,'DIAMOND'),(84,0,'EMERALD'),(84,0,'ONYX'),(84,0,'RUBY'),(84,7,'SAPPHIRE'),(85,3,'DIAMOND'),(85,3,'EMERALD'),(85,0,'ONYX'),(85,0,'RUBY'),(85,6,'SAPPHIRE'),(86,0,'DIAMOND'),(86,3,'EMERALD'),(86,0,'ONYX'),(86,0,'RUBY'),(86,7,'SAPPHIRE'),(87,3,'DIAMOND'),(87,3,'EMERALD'),(87,3,'ONYX'),(87,0,'RUBY'),(87,5,'SAPPHIRE'),(88,0,'DIAMOND'),(88,7,'EMERALD'),(88,0,'ONYX'),(88,0,'RUBY'),(88,0,'SAPPHIRE'),(89,0,'DIAMOND'),(89,6,'EMERALD'),(89,0,'ONYX'),(89,3,'RUBY'),(89,3,'SAPPHIRE'),(90,0,'DIAMOND'),(90,7,'EMERALD'),(90,0,'ONYX'),(90,3,'RUBY'),(90,0,'SAPPHIRE'),(91,100,'SAPPHIRE');
/*!40000 ALTER TABLE `card_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cards`
--

DROP TABLE IF EXISTS `cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cards` (
  `card_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `graphic` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  `produces` int(11) DEFAULT NULL,
  PRIMARY KEY (`card_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cards`
--

LOCK TABLES `cards` WRITE;
/*!40000 ALTER TABLE `cards` DISABLE KEYS */;
INSERT INTO `cards` VALUES (1,'assets/card-1.png',1,0,4),(2,'assets/card-2.png',1,0,4),(3,'assets/card-3.png',1,0,4),(4,'assets/card-4.png',1,0,4),(5,'assets/card-5.png',1,0,4),(6,'assets/card-6.png',1,0,4),(7,'assets/card-7.png',1,0,4),(8,'assets/card-8.png',1,1,4),(9,'assets/card-9.png',1,0,3),(10,'assets/card-10.png',1,0,3),(11,'assets/card-11.png',1,0,3),(12,'assets/card-12.png',1,0,3),(13,'assets/card-13.png',1,0,3),(14,'assets/card-14.png',1,0,3),(15,'assets/card-15.png',1,0,3),(16,'assets/card-16.png',1,1,3),(17,'assets/card-17.png',1,0,0),(18,'assets/card-18.png',1,0,0),(19,'assets/card-19.png',1,0,0),(20,'assets/card-20.png',1,0,0),(21,'assets/card-21.png',1,0,0),(22,'assets/card-22.png',1,0,0),(23,'assets/card-23.png',1,0,0),(24,'assets/card-24.png',1,1,0),(25,'assets/card-25.png',1,0,1),(26,'assets/card-26.png',1,0,1),(27,'assets/card-27.png',1,0,1),(28,'assets/card-28.png',1,0,1),(29,'assets/card-29.png',1,0,1),(30,'assets/card-30.png',1,0,1),(31,'assets/card-31.png',1,0,1),(32,'assets/card-32.png',1,1,1),(33,'assets/card-33.png',1,0,2),(34,'assets/card-34.png',1,0,2),(35,'assets/card-35.png',1,0,2),(36,'assets/card-36.png',1,0,2),(37,'assets/card-37.png',1,0,2),(38,'assets/card-38.png',1,0,2),(39,'assets/card-39.png',1,0,2),(40,'assets/card-40.png',1,1,2),(41,'assets/card-41.png',2,1,4),(42,'assets/card-42.png',2,1,4),(43,'assets/card-43.png',2,2,4),(44,'assets/card-44.png',2,2,4),(45,'assets/card-45.png',2,2,4),(46,'assets/card-46.png',2,3,4),(47,'assets/card-47.png',2,1,3),(48,'assets/card-48.png',2,1,3),(49,'assets/card-49.png',2,2,3),(50,'assets/card-50.png',2,2,3),(51,'assets/card-51.png',2,2,3),(52,'assets/card-52.png',2,3,3),(53,'assets/card-53.png',2,1,0),(54,'assets/card-54.png',2,1,0),(55,'assets/card-55.png',2,2,0),(56,'assets/card-56.png',2,2,0),(57,'assets/card-57.png',2,2,0),(58,'assets/card-58.png',2,3,0),(59,'assets/card-59.png',2,1,1),(60,'assets/card-60.png',2,1,1),(61,'assets/card-61.png',2,2,1),(62,'assets/card-62.png',2,2,1),(63,'assets/card-63.png',2,2,1),(64,'assets/card-64.png',2,3,1),(65,'assets/card-65.png',2,1,2),(66,'assets/card-66.png',2,1,2),(67,'assets/card-67.png',2,2,2),(68,'assets/card-68.png',2,2,2),(69,'assets/card-69.png',2,2,2),(70,'assets/card-70.png',2,3,2),(71,'assets/card-71.png',3,3,4),(72,'assets/card-72.png',3,4,4),(73,'assets/card-73.png',3,4,4),(74,'assets/card-74.png',3,5,4),(75,'assets/card-75.png',3,3,3),(76,'assets/card-76.png',3,4,3),(77,'assets/card-77.png',3,4,3),(78,'assets/card-78.png',3,5,3),(79,'assets/card-79.png',3,3,0),(80,'assets/card-80.png',3,4,0),(81,'assets/card-81.png',3,4,0),(82,'assets/card-82.png',3,5,0),(83,'assets/card-83.png',3,3,1),(84,'assets/card-84.png',3,4,1),(85,'assets/card-85.png',3,4,1),(86,'assets/card-86.png',3,5,1),(87,'assets/card-87.png',3,3,2),(88,'assets/card-88.png',3,4,2),(89,'assets/card-89.png',3,4,2),(90,'assets/card-90.png',3,5,2),(91,'assets/back-1.png',0,0,0);
/*!40000 ALTER TABLE `cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_state_tokens_on_table`
--

DROP TABLE IF EXISTS `game_state_tokens_on_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game_state_tokens_on_table` (
  `game_state_game_id` bigint(20) NOT NULL,
  `tokens_on_table` int(11) DEFAULT NULL,
  `token_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`game_state_game_id`,`token_type`),
  CONSTRAINT `FK4e5mk2o4av2wxppk25sssfr7h` FOREIGN KEY (`game_state_game_id`) REFERENCES `games` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_state_tokens_on_table`
--

LOCK TABLES `game_state_tokens_on_table` WRITE;
/*!40000 ALTER TABLE `game_state_tokens_on_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `game_state_tokens_on_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games` (
  `game_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_player_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games_cards`
--

DROP TABLE IF EXISTS `games_cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games_cards` (
  `game_state_game_id` bigint(20) NOT NULL,
  `cards_card_id` bigint(20) NOT NULL,
  KEY `FKbwf6mfwd92fogey3nnqbv3h90` (`cards_card_id`),
  KEY `FKas0b3heo7grp4at3ejsbk8ve9` (`game_state_game_id`),
  CONSTRAINT `FKas0b3heo7grp4at3ejsbk8ve9` FOREIGN KEY (`game_state_game_id`) REFERENCES `games` (`game_id`),
  CONSTRAINT `FKbwf6mfwd92fogey3nnqbv3h90` FOREIGN KEY (`cards_card_id`) REFERENCES `cards` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games_cards`
--

LOCK TABLES `games_cards` WRITE;
/*!40000 ALTER TABLE `games_cards` DISABLE KEYS */;
/*!40000 ALTER TABLE `games_cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games_cards_on_table`
--

DROP TABLE IF EXISTS `games_cards_on_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games_cards_on_table` (
  `game_state_game_id` bigint(20) NOT NULL,
  `cards_on_table_card_id` bigint(20) NOT NULL,
  KEY `FKqbkscw09fu1y92103sut4cexb` (`cards_on_table_card_id`),
  KEY `FK906axd1knsl9nofwefgs8wp8q` (`game_state_game_id`),
  CONSTRAINT `FK906axd1knsl9nofwefgs8wp8q` FOREIGN KEY (`game_state_game_id`) REFERENCES `games` (`game_id`),
  CONSTRAINT `FKqbkscw09fu1y92103sut4cexb` FOREIGN KEY (`cards_on_table_card_id`) REFERENCES `cards` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games_cards_on_table`
--

LOCK TABLES `games_cards_on_table` WRITE;
/*!40000 ALTER TABLE `games_cards_on_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `games_cards_on_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games_nobles`
--

DROP TABLE IF EXISTS `games_nobles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games_nobles` (
  `game_state_game_id` bigint(20) NOT NULL,
  `nobles_noble_id` bigint(20) NOT NULL,
  KEY `FK80y26bbu647s6up6kuxreuax8` (`nobles_noble_id`),
  KEY `FKjsg6tntwtuk5c9fm19xd5dkfu` (`game_state_game_id`),
  CONSTRAINT `FK80y26bbu647s6up6kuxreuax8` FOREIGN KEY (`nobles_noble_id`) REFERENCES `nobles` (`noble_id`),
  CONSTRAINT `FKjsg6tntwtuk5c9fm19xd5dkfu` FOREIGN KEY (`game_state_game_id`) REFERENCES `games` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games_nobles`
--

LOCK TABLES `games_nobles` WRITE;
/*!40000 ALTER TABLE `games_nobles` DISABLE KEYS */;
/*!40000 ALTER TABLE `games_nobles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games_players`
--

DROP TABLE IF EXISTS `games_players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games_players` (
  `game_state_game_id` bigint(20) NOT NULL,
  `players_player_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_9s29w26y7fsc2h0lm7f37vl5r` (`players_player_id`),
  KEY `FKjnnj5a0de7mu74inllpppupf3` (`game_state_game_id`),
  CONSTRAINT `FKjnnj5a0de7mu74inllpppupf3` FOREIGN KEY (`game_state_game_id`) REFERENCES `games` (`game_id`),
  CONSTRAINT `FKt7ujp492e2fd6ax0xhwjk552k` FOREIGN KEY (`players_player_id`) REFERENCES `players` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games_players`
--

LOCK TABLES `games_players` WRITE;
/*!40000 ALTER TABLE `games_players` DISABLE KEYS */;
/*!40000 ALTER TABLE `games_players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games_user_list`
--

DROP TABLE IF EXISTS `games_user_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games_user_list` (
  `game_state_game_id` bigint(20) NOT NULL,
  `user_list_user_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_r0p5u0ewr9grxr3u5epbd1bj5` (`user_list_user_id`),
  KEY `FKt2rh99ralxkf90lj2b988l99g` (`game_state_game_id`),
  CONSTRAINT `FKplmmlpegogos2yb46yu1ou9g1` FOREIGN KEY (`user_list_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKt2rh99ralxkf90lj2b988l99g` FOREIGN KEY (`game_state_game_id`) REFERENCES `games` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games_user_list`
--

LOCK TABLES `games_user_list` WRITE;
/*!40000 ALTER TABLE `games_user_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `games_user_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `noble_card_combination`
--

DROP TABLE IF EXISTS `noble_card_combination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `noble_card_combination` (
  `noble_noble_id` bigint(20) NOT NULL,
  `card_combination` int(11) DEFAULT NULL,
  `token_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`noble_noble_id`,`token_type`),
  CONSTRAINT `FKc0baqf2wbavsibsra2e2x15jp` FOREIGN KEY (`noble_noble_id`) REFERENCES `nobles` (`noble_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `noble_card_combination`
--

LOCK TABLES `noble_card_combination` WRITE;
/*!40000 ALTER TABLE `noble_card_combination` DISABLE KEYS */;
INSERT INTO `noble_card_combination` VALUES (1,4,'EMERALD'),(1,4,'RUBY'),(2,3,'DIAMOND'),(2,3,'ONYX'),(2,3,'RUBY'),(3,4,'DIAMOND'),(3,4,'SAPPHIRE'),(4,4,'DIAMOND'),(4,4,'ONYX'),(5,4,'EMERALD'),(5,4,'SAPPHIRE'),(6,3,'EMERALD'),(6,3,'RUBY'),(6,3,'SAPPHIRE'),(7,3,'DIAMOND'),(7,3,'EMERALD'),(7,3,'SAPPHIRE'),(8,4,'ONYX'),(8,4,'RUBY'),(9,3,'DIAMOND'),(9,3,'ONYX'),(9,3,'SAPPHIRE'),(10,3,'EMERALD'),(10,3,'ONYX'),(10,3,'RUBY');
/*!40000 ALTER TABLE `noble_card_combination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nobles`
--

DROP TABLE IF EXISTS `nobles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nobles` (
  `noble_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `graphic` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  PRIMARY KEY (`noble_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nobles`
--

LOCK TABLES `nobles` WRITE;
/*!40000 ALTER TABLE `nobles` DISABLE KEYS */;
INSERT INTO `nobles` VALUES (1,'assets/splendor_nobles-1.png',3),(2,'assets/splendor_nobles-2.png',3),(3,'assets/splendor_nobles-3.png',3),(4,'assets/splendor_nobles-4.png',3),(5,'assets/splendor_nobles-5.png',3),(6,'assets/splendor_nobles-6.png',3),(7,'assets/splendor_nobles-7.png',3),(8,'assets/splendor_nobles-8.png',3),(9,'assets/splendor_nobles-9.png',3),(10,'assets/splendor_nobles-10.png',3);
/*!40000 ALTER TABLE `nobles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_player_tokens`
--

DROP TABLE IF EXISTS `player_player_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_player_tokens` (
  `player_player_id` bigint(20) NOT NULL,
  `player_tokens` int(11) DEFAULT NULL,
  `token_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`player_player_id`,`token_type`),
  CONSTRAINT `FK40ot858my5gtmuc1lbdnuocja` FOREIGN KEY (`player_player_id`) REFERENCES `players` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_player_tokens`
--

LOCK TABLES `player_player_tokens` WRITE;
/*!40000 ALTER TABLE `player_player_tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_player_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `players` (
  `player_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `has_seen_results` bit(1) DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  `user_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`player_id`),
  KEY `FKl0qdcccaixpcxauqq9x8hs0yd` (`user_user_id`),
  CONSTRAINT `FKl0qdcccaixpcxauqq9x8hs0yd` FOREIGN KEY (`user_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players_cards`
--

DROP TABLE IF EXISTS `players_cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `players_cards` (
  `player_player_id` bigint(20) NOT NULL,
  `cards_card_id` bigint(20) NOT NULL,
  PRIMARY KEY (`player_player_id`,`cards_card_id`),
  UNIQUE KEY `UK_gg6j9qk2cvgj2c6b1oledigue` (`cards_card_id`),
  CONSTRAINT `FKg5g1n9m7eewbcfkdoom4gus0g` FOREIGN KEY (`cards_card_id`) REFERENCES `cards` (`card_id`),
  CONSTRAINT `FKgpa71k4y80yk7ouproadoye2u` FOREIGN KEY (`player_player_id`) REFERENCES `players` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players_cards`
--

LOCK TABLES `players_cards` WRITE;
/*!40000 ALTER TABLE `players_cards` DISABLE KEYS */;
/*!40000 ALTER TABLE `players_cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players_cards_in_hand`
--

DROP TABLE IF EXISTS `players_cards_in_hand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `players_cards_in_hand` (
  `player_player_id` bigint(20) NOT NULL,
  `cards_in_hand_card_id` bigint(20) NOT NULL,
  PRIMARY KEY (`player_player_id`,`cards_in_hand_card_id`),
  UNIQUE KEY `UK_dr41i7ieb1go4l6m9uhpa029` (`cards_in_hand_card_id`),
  CONSTRAINT `FK6rb5eu8u1ffswboa8hk0ikj1q` FOREIGN KEY (`player_player_id`) REFERENCES `players` (`player_id`),
  CONSTRAINT `FKpuq15re9fv88yq6s30lt632ot` FOREIGN KEY (`cards_in_hand_card_id`) REFERENCES `cards` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players_cards_in_hand`
--

LOCK TABLES `players_cards_in_hand` WRITE;
/*!40000 ALTER TABLE `players_cards_in_hand` DISABLE KEYS */;
/*!40000 ALTER TABLE `players_cards_in_hand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players_nobles`
--

DROP TABLE IF EXISTS `players_nobles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `players_nobles` (
  `player_player_id` bigint(20) NOT NULL,
  `nobles_noble_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_sp8i4bqvhl5roxvlxsq2ogwsn` (`nobles_noble_id`),
  KEY `FK3ai5uo9o8bcvqooyj0afb08qu` (`player_player_id`),
  CONSTRAINT `FK3ai5uo9o8bcvqooyj0afb08qu` FOREIGN KEY (`player_player_id`) REFERENCES `players` (`player_id`),
  CONSTRAINT `FKc01558jtpbvgm455ovqjohfse` FOREIGN KEY (`nobles_noble_id`) REFERENCES `nobles` (`noble_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players_nobles`
--

LOCK TABLES `players_nobles` WRITE;
/*!40000 ALTER TABLE `players_nobles` DISABLE KEYS */;
/*!40000 ALTER TABLE `players_nobles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_currently_interacting_users`
--

DROP TABLE IF EXISTS `user_currently_interacting_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_currently_interacting_users` (
  `user_user_id` bigint(20) NOT NULL,
  `currently_interacting_users` bigint(20) DEFAULT NULL,
  KEY `FKjy58kcuk86tr9a2xsh1311myd` (`user_user_id`),
  CONSTRAINT `FKjy58kcuk86tr9a2xsh1311myd` FOREIGN KEY (`user_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_currently_interacting_users`
--

LOCK TABLES `user_currently_interacting_users` WRITE;
/*!40000 ALTER TABLE `user_currently_interacting_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_currently_interacting_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKt7e7djp752sqn6w22i6ocqy6q` (`role_id`),
  CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKt7e7djp752sqn6w22i6ocqy6q` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` int(11) NOT NULL,
  `last_online` datetime(6) DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_name` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `game_state_game_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_k8d0f2n7n88w1a16yhua64onx` (`user_name`),
  KEY `FKgeokuk4i6l3byukbhxhoq8m4o` (`game_state_game_id`),
  CONSTRAINT `FKgeokuk4i6l3byukbhxhoq8m4o` FOREIGN KEY (`game_state_game_id`) REFERENCES `games` (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;INSERT INTO roles (role) VALUES ('ROLE_USER');
INSERT INTO roles (role) VALUES ('ROLE_ADMIN');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,4,'assets/card-1.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,4,'assets/card-2.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,4,'assets/card-3.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,4,'assets/card-4.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,4,'assets/card-5.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,4,'assets/card-6.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,4,'assets/card-7.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,1,4,'assets/card-8.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,3,'assets/card-9.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,3,'assets/card-10.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,3,'assets/card-11.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,3,'assets/card-12.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,3,'assets/card-13.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,3,'assets/card-14.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,3,'assets/card-15.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,1,3,'assets/card-16.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,0,'assets/card-17.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,0,'assets/card-18.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,0,'assets/card-19.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,0,'assets/card-20.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,0,'assets/card-21.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,0,'assets/card-22.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,0,'assets/card-23.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,1,0,'assets/card-24.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,1,'assets/card-25.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,1,'assets/card-26.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,1,'assets/card-27.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,1,'assets/card-28.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,1,'assets/card-29.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,1,'assets/card-30.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,1,'assets/card-31.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,1,1,'assets/card-32.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,2,'assets/card-33.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,2,'assets/card-34.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,2,'assets/card-35.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,2,'assets/card-36.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,2,'assets/card-37.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,2,'assets/card-38.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,0,2,'assets/card-39.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (1,1,2,'assets/card-40.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,4,'assets/card-41.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,4,'assets/card-42.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,4,'assets/card-43.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,4,'assets/card-44.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,4,'assets/card-45.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,3,4,'assets/card-46.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,3,'assets/card-47.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,3,'assets/card-48.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,3,'assets/card-49.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,3,'assets/card-50.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,3,'assets/card-51.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,3,3,'assets/card-52.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,0,'assets/card-53.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,0,'assets/card-54.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,0,'assets/card-55.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,0,'assets/card-56.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,0,'assets/card-57.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,3,0,'assets/card-58.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,1,'assets/card-59.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,1,'assets/card-60.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,1,'assets/card-61.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,1,'assets/card-62.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,1,'assets/card-63.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,3,1,'assets/card-64.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,2,'assets/card-65.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,1,2,'assets/card-66.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,2,'assets/card-67.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,2,'assets/card-68.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,2,2,'assets/card-69.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (2,3,2,'assets/card-70.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,3,4,'assets/card-71.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,4,'assets/card-72.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,4,'assets/card-73.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,5,4,'assets/card-74.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,3,3,'assets/card-75.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,3,'assets/card-76.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,3,'assets/card-77.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,5,3,'assets/card-78.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,3,0,'assets/card-79.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,0,'assets/card-80.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,0,'assets/card-81.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,5,0,'assets/card-82.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,3,1,'assets/card-83.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,1,'assets/card-84.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,1,'assets/card-85.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,5,1,'assets/card-86.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,3,2,'assets/card-87.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,2,'assets/card-88.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,4,2,'assets/card-89.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (3,5,2,'assets/card-90.png');
INSERT INTO cards (level, points, produces, graphic) VALUES (0,0,0,'assets/back-1.png');

/*!40000 ALTER TABLE `card_cost` DISABLE KEYS */;
INSERT INTO `card_cost` VALUES (1,1,'DIAMOND'),(1,1,'EMERALD'),(1,0,'ONYX'),(1,1,'RUBY'),(1,1,'SAPPHIRE'),(2,1,'DIAMOND'),(2,1,'EMERALD'),(2,0,'ONYX'),(2,1,'RUBY'),(2,2,'SAPPHIRE'),(3,2,'DIAMOND'),(3,0,'EMERALD'),(3,0,'ONYX'),(3,1,'RUBY'),(3,2,'SAPPHIRE'),(4,0,'DIAMOND'),(4,1,'EMERALD'),(4,1,'ONYX'),(4,3,'RUBY'),(4,0,'SAPPHIRE'),(5,0,'DIAMOND'),(5,2,'EMERALD'),(5,0,'ONYX'),(5,1,'RUBY'),(5,0,'SAPPHIRE'),(6,2,'DIAMOND'),(6,2,'EMERALD'),(6,0,'ONYX'),(6,0,'RUBY'),(6,0,'SAPPHIRE'),(7,0,'DIAMOND'),(7,3,'EMERALD'),(7,0,'ONYX'),(7,0,'RUBY'),(7,0,'SAPPHIRE'),(8,0,'DIAMOND'),(8,0,'EMERALD'),(8,0,'ONYX'),(8,0,'RUBY'),(8,4,'SAPPHIRE'),(9,1,'DIAMOND'),(9,1,'EMERALD'),(9,1,'ONYX'),(9,1,'RUBY'),(9,0,'SAPPHIRE'),(10,1,'DIAMOND'),(10,1,'EMERALD'),(10,1,'ONYX'),(10,2,'RUBY'),(10,0,'SAPPHIRE'),(11,1,'DIAMOND'),(11,2,'EMERALD'),(11,0,'ONYX'),(11,2,'RUBY'),(11,0,'SAPPHIRE'),(12,0,'DIAMOND'),(12,3,'EMERALD'),(12,0,'ONYX'),(12,1,'RUBY'),(12,1,'SAPPHIRE'),(13,1,'DIAMOND'),(13,0,'EMERALD'),(13,2,'ONYX'),(13,0,'RUBY'),(13,0,'SAPPHIRE'),(14,0,'DIAMOND'),(14,2,'EMERALD'),(14,2,'ONYX'),(14,0,'RUBY'),(14,0,'SAPPHIRE'),(15,0,'DIAMOND'),(15,0,'EMERALD'),(15,3,'ONYX'),(15,0,'RUBY'),(15,0,'SAPPHIRE'),(16,0,'DIAMOND'),(16,0,'EMERALD'),(16,0,'ONYX'),(16,4,'RUBY'),(16,0,'SAPPHIRE'),(17,0,'DIAMOND'),(17,1,'EMERALD'),(17,1,'ONYX'),(17,1,'RUBY'),(17,1,'SAPPHIRE'),(18,0,'DIAMOND'),(18,2,'EMERALD'),(18,1,'ONYX'),(18,1,'RUBY'),(18,1,'SAPPHIRE'),(19,0,'DIAMOND'),(19,2,'EMERALD'),(19,1,'ONYX'),(19,0,'RUBY'),(19,2,'SAPPHIRE'),(20,3,'DIAMOND'),(20,0,'EMERALD'),(20,1,'ONYX'),(20,0,'RUBY'),(20,1,'SAPPHIRE'),(21,0,'DIAMOND'),(21,0,'EMERALD'),(21,1,'ONYX'),(21,2,'RUBY'),(21,0,'SAPPHIRE'),(22,0,'DIAMOND'),(22,0,'EMERALD'),(22,2,'ONYX'),(22,0,'RUBY'),(22,2,'SAPPHIRE'),(23,0,'DIAMOND'),(23,0,'EMERALD'),(23,0,'ONYX'),(23,0,'RUBY'),(23,3,'SAPPHIRE'),(24,0,'DIAMOND'),(24,4,'EMERALD'),(24,0,'ONYX'),(24,0,'RUBY'),(24,0,'SAPPHIRE'),(25,1,'DIAMOND'),(25,0,'EMERALD'),(25,1,'ONYX'),(25,1,'RUBY'),(25,1,'SAPPHIRE'),(26,1,'DIAMOND'),(26,0,'EMERALD'),(26,2,'ONYX'),(26,1,'RUBY'),(26,1,'SAPPHIRE'),(27,0,'DIAMOND'),(27,0,'EMERALD'),(27,2,'ONYX'),(27,2,'RUBY'),(27,1,'SAPPHIRE'),(28,1,'DIAMOND'),(28,1,'EMERALD'),(28,0,'ONYX'),(28,0,'RUBY'),(28,3,'SAPPHIRE'),(29,2,'DIAMOND'),(29,0,'EMERALD'),(29,0,'ONYX'),(29,0,'RUBY'),(29,1,'SAPPHIRE'),(30,0,'DIAMOND'),(30,0,'EMERALD'),(30,0,'ONYX'),(30,2,'RUBY'),(30,2,'SAPPHIRE'),(31,0,'DIAMOND'),(31,0,'EMERALD'),(31,0,'ONYX'),(31,3,'RUBY'),(31,0,'SAPPHIRE'),(32,0,'DIAMOND'),(32,0,'EMERALD'),(32,4,'ONYX'),(32,0,'RUBY'),(32,0,'SAPPHIRE'),(33,1,'DIAMOND'),(33,1,'EMERALD'),(33,1,'ONYX'),(33,0,'RUBY'),(33,1,'SAPPHIRE'),(34,2,'DIAMOND'),(34,1,'EMERALD'),(34,1,'ONYX'),(34,0,'RUBY'),(34,1,'SAPPHIRE'),(35,2,'DIAMOND'),(35,1,'EMERALD'),(35,2,'ONYX'),(35,0,'RUBY'),(35,0,'SAPPHIRE'),(36,1,'DIAMOND'),(36,0,'EMERALD'),(36,3,'ONYX'),(36,1,'RUBY'),(36,0,'SAPPHIRE'),(37,0,'DIAMOND'),(37,1,'EMERALD'),(37,0,'ONYX'),(37,0,'RUBY'),(37,2,'SAPPHIRE'),(38,2,'DIAMOND'),(38,0,'EMERALD'),(38,0,'ONYX'),(38,2,'RUBY'),(38,0,'SAPPHIRE'),(39,3,'DIAMOND'),(39,0,'EMERALD'),(39,0,'ONYX'),(39,0,'RUBY'),(39,0,'SAPPHIRE'),(40,4,'DIAMOND'),(40,0,'EMERALD'),(40,0,'ONYX'),(40,0,'RUBY'),(40,0,'SAPPHIRE'),(41,3,'DIAMOND'),(41,2,'EMERALD'),(41,0,'ONYX'),(41,0,'RUBY'),(41,2,'SAPPHIRE'),(42,3,'DIAMOND'),(42,3,'EMERALD'),(42,2,'ONYX'),(42,0,'RUBY'),(42,0,'SAPPHIRE'),(43,0,'DIAMOND'),(43,4,'EMERALD'),(43,0,'ONYX'),(43,2,'RUBY'),(43,1,'SAPPHIRE'),(44,0,'DIAMOND'),(44,5,'EMERALD'),(44,0,'ONYX'),(44,3,'RUBY'),(44,0,'SAPPHIRE'),(45,5,'DIAMOND'),(45,0,'EMERALD'),(45,0,'ONYX'),(45,0,'RUBY'),(45,0,'SAPPHIRE'),(46,0,'DIAMOND'),(46,0,'EMERALD'),(46,6,'ONYX'),(46,0,'RUBY'),(46,0,'SAPPHIRE'),(47,0,'DIAMOND'),(47,2,'EMERALD'),(47,0,'ONYX'),(47,3,'RUBY'),(47,2,'SAPPHIRE'),(48,0,'DIAMOND'),(48,3,'EMERALD'),(48,3,'ONYX'),(48,0,'RUBY'),(48,2,'SAPPHIRE'),(49,5,'DIAMOND'),(49,0,'EMERALD'),(49,0,'ONYX'),(49,0,'RUBY'),(49,3,'SAPPHIRE'),(50,2,'DIAMOND'),(50,0,'EMERALD'),(50,4,'ONYX'),(50,1,'RUBY'),(50,0,'SAPPHIRE'),(51,0,'DIAMOND'),(51,0,'EMERALD'),(51,0,'ONYX'),(51,0,'RUBY'),(51,5,'SAPPHIRE'),(52,0,'DIAMOND'),(52,0,'EMERALD'),(52,0,'ONYX'),(52,0,'RUBY'),(52,6,'SAPPHIRE'),(53,0,'DIAMOND'),(53,3,'EMERALD'),(53,2,'ONYX'),(53,2,'RUBY'),(53,0,'SAPPHIRE'),(54,2,'DIAMOND'),(54,0,'EMERALD'),(54,0,'ONYX'),(54,3,'RUBY'),(54,3,'SAPPHIRE'),(55,0,'DIAMOND'),(55,1,'EMERALD'),(55,2,'ONYX'),(55,4,'RUBY'),(55,0,'SAPPHIRE'),(56,0,'DIAMOND'),(56,0,'EMERALD'),(56,3,'ONYX'),(56,5,'RUBY'),(56,0,'SAPPHIRE'),(57,0,'DIAMOND'),(57,0,'EMERALD'),(57,0,'ONYX'),(57,5,'RUBY'),(57,0,'SAPPHIRE'),(58,6,'DIAMOND'),(58,0,'EMERALD'),(58,0,'ONYX'),(58,0,'RUBY'),(58,0,'SAPPHIRE'),(59,3,'DIAMOND'),(59,2,'EMERALD'),(59,0,'ONYX'),(59,3,'RUBY'),(59,0,'SAPPHIRE'),(60,2,'DIAMOND'),(60,0,'EMERALD'),(60,2,'ONYX'),(60,0,'RUBY'),(60,3,'SAPPHIRE'),(61,4,'DIAMOND'),(61,0,'EMERALD'),(61,1,'ONYX'),(61,0,'RUBY'),(61,2,'SAPPHIRE'),(62,0,'DIAMOND'),(62,3,'EMERALD'),(62,0,'ONYX'),(62,0,'RUBY'),(62,5,'SAPPHIRE'),(63,0,'DIAMOND'),(63,5,'EMERALD'),(63,0,'ONYX'),(63,0,'RUBY'),(63,0,'SAPPHIRE'),(64,0,'DIAMOND'),(64,6,'EMERALD'),(64,0,'ONYX'),(64,0,'RUBY'),(64,0,'SAPPHIRE'),(65,2,'DIAMOND'),(65,0,'EMERALD'),(65,3,'ONYX'),(65,2,'RUBY'),(65,0,'SAPPHIRE'),(66,0,'DIAMOND'),(66,0,'EMERALD'),(66,3,'ONYX'),(66,2,'RUBY'),(66,3,'SAPPHIRE'),(67,1,'DIAMOND'),(67,2,'EMERALD'),(67,0,'ONYX'),(67,0,'RUBY'),(67,4,'SAPPHIRE'),(68,3,'DIAMOND'),(68,0,'EMERALD'),(68,5,'ONYX'),(68,0,'RUBY'),(68,0,'SAPPHIRE'),(69,0,'DIAMOND'),(69,0,'EMERALD'),(69,5,'ONYX'),(69,0,'RUBY'),(69,0,'SAPPHIRE'),(70,0,'DIAMOND'),(70,0,'EMERALD'),(70,0,'ONYX'),(70,6,'RUBY'),(70,0,'SAPPHIRE'),(71,3,'DIAMOND'),(71,5,'EMERALD'),(71,0,'ONYX'),(71,3,'RUBY'),(71,3,'SAPPHIRE'),(72,0,'DIAMOND'),(72,0,'EMERALD'),(72,0,'ONYX'),(72,7,'RUBY'),(72,0,'SAPPHIRE'),(73,0,'DIAMOND'),(73,3,'EMERALD'),(73,3,'ONYX'),(73,6,'RUBY'),(73,0,'SAPPHIRE'),(74,0,'DIAMOND'),(74,0,'EMERALD'),(74,3,'ONYX'),(74,7,'RUBY'),(74,0,'SAPPHIRE'),(75,3,'DIAMOND'),(75,3,'EMERALD'),(75,5,'ONYX'),(75,3,'RUBY'),(75,0,'SAPPHIRE'),(76,7,'DIAMOND'),(76,0,'EMERALD'),(76,0,'ONYX'),(76,0,'RUBY'),(76,0,'SAPPHIRE'),(77,6,'DIAMOND'),(77,0,'EMERALD'),(77,3,'ONYX'),(77,0,'RUBY'),(77,3,'SAPPHIRE'),(78,7,'DIAMOND'),(78,0,'EMERALD'),(78,0,'ONYX'),(78,0,'RUBY'),(78,3,'SAPPHIRE'),(79,0,'DIAMOND'),(79,3,'EMERALD'),(79,3,'ONYX'),(79,5,'RUBY'),(79,3,'SAPPHIRE'),(80,0,'DIAMOND'),(80,0,'EMERALD'),(80,7,'ONYX'),(80,0,'RUBY'),(80,0,'SAPPHIRE'),(81,3,'DIAMOND'),(81,0,'EMERALD'),(81,6,'ONYX'),(81,3,'RUBY'),(81,0,'SAPPHIRE'),(82,3,'DIAMOND'),(82,0,'EMERALD'),(82,7,'ONYX'),(82,0,'RUBY'),(82,0,'SAPPHIRE'),(83,5,'DIAMOND'),(83,0,'EMERALD'),(83,3,'ONYX'),(83,3,'RUBY'),(83,3,'SAPPHIRE'),(84,0,'DIAMOND'),(84,0,'EMERALD'),(84,0,'ONYX'),(84,0,'RUBY'),(84,7,'SAPPHIRE'),(85,3,'DIAMOND'),(85,3,'EMERALD'),(85,0,'ONYX'),(85,0,'RUBY'),(85,6,'SAPPHIRE'),(86,0,'DIAMOND'),(86,3,'EMERALD'),(86,0,'ONYX'),(86,0,'RUBY'),(86,7,'SAPPHIRE'),(87,3,'DIAMOND'),(87,3,'EMERALD'),(87,3,'ONYX'),(87,0,'RUBY'),(87,5,'SAPPHIRE'),(88,0,'DIAMOND'),(88,7,'EMERALD'),(88,0,'ONYX'),(88,0,'RUBY'),(88,0,'SAPPHIRE'),(89,0,'DIAMOND'),(89,6,'EMERALD'),(89,0,'ONYX'),(89,3,'RUBY'),(89,3,'SAPPHIRE'),(90,0,'DIAMOND'),(90,7,'EMERALD'),(90,0,'ONYX'),(90,3,'RUBY'),(90,0,'SAPPHIRE'),(91,100,'SAPPHIRE');
/*!40000 ALTER TABLE `card_cost` ENABLE KEYS */;

/*!40000 ALTER TABLE `nobles` DISABLE KEYS */;
INSERT INTO `nobles` VALUES (1,'assets/splendor_nobles-1.png',3),(2,'assets/splendor_nobles-2.png',3),(3,'assets/splendor_nobles-3.png',3),(4,'assets/splendor_nobles-4.png',3),(5,'assets/splendor_nobles-5.png',3),(6,'assets/splendor_nobles-6.png',3),(7,'assets/splendor_nobles-7.png',3),(8,'assets/splendor_nobles-8.png',3),(9,'assets/splendor_nobles-9.png',3),(10,'assets/splendor_nobles-10.png',3);
/*!40000 ALTER TABLE `nobles` ENABLE KEYS */;
/*!40000 ALTER TABLE `noble_card_combination` DISABLE KEYS */;
INSERT INTO `noble_card_combination` VALUES (1,4,'EMERALD'),(1,4,'RUBY'),(2,3,'DIAMOND'),(2,3,'ONYX'),(2,3,'RUBY'),(3,4,'DIAMOND'),(3,4,'SAPPHIRE'),(4,4,'DIAMOND'),(4,4,'ONYX'),(5,4,'EMERALD'),(5,4,'SAPPHIRE'),(6,3,'EMERALD'),(6,3,'RUBY'),(6,3,'SAPPHIRE'),(7,3,'DIAMOND'),(7,3,'EMERALD'),(7,3,'SAPPHIRE'),(8,4,'ONYX'),(8,4,'RUBY'),(9,3,'DIAMOND'),(9,3,'ONYX'),(9,3,'SAPPHIRE'),(10,3,'EMERALD'),(10,3,'ONYX'),(10,3,'RUBY');
/*!40000 ALTER TABLE `noble_card_combination` ENABLE KEYS */;
INSERT INTO `users` VALUES (1,1,NULL,'rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr','logged_out','init',NULL);
INSERT INTO `users` VALUES (2,1,NULL,'rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr','logged_out','endGame',NULL);