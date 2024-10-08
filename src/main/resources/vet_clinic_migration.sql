-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: vet_clinic
-- ------------------------------------------------------
-- Server version	5.7.44-log


SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- Schema vet_clinic2
-- ----------------------------------------------------------------------------
DROP SCHEMA IF EXISTS `vet_clinic` ;
CREATE SCHEMA IF NOT EXISTS `vet_clinic` ;

--
-- Table structure for table `animal`
--

DROP TABLE IF EXISTS `vet_clinic`.`animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vet_clinic`.`animal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price_coeff` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animal`
--

LOCK TABLES `vet_clinic`.`animal` WRITE;
/*!40000 ALTER TABLE `vet_clinic`.`animal` DISABLE KEYS */;
INSERT INTO `vet_clinic`.`animal` VALUES (1,'Cat',1),(2,'Dog',1.2),(3,'Snake',1.5);
/*!40000 ALTER TABLE `vet_clinic`.`animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `vet_clinic`.`client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vet_clinic`.`client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `vet_clinic`.`client` WRITE;
/*!40000 ALTER TABLE `vet_clinic`.`client` DISABLE KEYS */;
INSERT INTO `vet_clinic`.`client` VALUES (1,'Alex','111'),(2,'Jessica','222'),(3,'John','333'),(7,'Oliver','44444');
/*!40000 ALTER TABLE `vet_clinic`.`client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `vet_clinic`.`order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vet_clinic`.`order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `animal_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `cost` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `vet_clinic`.`order` WRITE;
/*!40000 ALTER TABLE `vet_clinic`.`order` DISABLE KEYS */;
INSERT INTO `vet_clinic`.`order` VALUES (1,1,1,1,'2024-01-02 10:00:00',1,100),(2,1,2,1,'2024-01-02 10:00:00',1,150),(3,2,2,3,'2024-01-02 10:00:00',2,290);
/*!40000 ALTER TABLE `vet_clinic`.`order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `vet_clinic`.`service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vet_clinic`.`service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `vet_clinic`.`service` WRITE;
/*!40000 ALTER TABLE `vet_clinic`.`service` DISABLE KEYS */;
INSERT INTO `vet_clinic`.`service` VALUES (1,'Vaccination',500),(2,'Chipping',200),(3,'Castration',700),(4,'Clinical_examination',150);
/*!40000 ALTER TABLE `vet_clinic`.`service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_animal`
--

DROP TABLE IF EXISTS `vet_clinic`.`service_animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vet_clinic`.`service_animal` (
  `service_id` int(11) NOT NULL,
  `animal_id` int(11) NOT NULL,
  PRIMARY KEY (`service_id`,`animal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_animal`
--

LOCK TABLES `vet_clinic`.`service_animal` WRITE;
/*!40000 ALTER TABLE `vet_clinic`.`service_animal` DISABLE KEYS */;
INSERT INTO `vet_clinic`.`service_animal` VALUES (1,1),(1,2),(2,1),(2,2),(2,3),(3,1),(3,2);
/*!40000 ALTER TABLE `vet_clinic`.`service_animal` ENABLE KEYS */;
UNLOCK TABLES;


-- Dump completed on 2024-09-24 20:02:43
