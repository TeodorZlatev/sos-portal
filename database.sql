-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sos
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `block`
--

DROP TABLE IF EXISTS `block`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `block` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(45) NOT NULL,
  `host_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `block`
--

LOCK TABLES `block` WRITE;
/*!40000 ALTER TABLE `block` DISABLE KEYS */;
INSERT INTO `block` VALUES (1,'2',1),(2,'3',9);
/*!40000 ALTER TABLE `block` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credentials`
--

DROP TABLE IF EXISTS `credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credentials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials`
--

LOCK TABLES `credentials` WRITE;
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `credentials` VALUES (1,2,'1'),(2,1,'2'),(3,11,'3'),(4,10,'4'),(5,8,'12'),(6,5,'12345'),(7,9,'1231231230');
/*!40000 ALTER TABLE `credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `night_tax`
--

DROP TABLE IF EXISTS `night_tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `night_tax` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `guest_name` varchar(45) NOT NULL,
  `date` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `status` varchar(45) NOT NULL,
  `date_paid` varchar(45) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `night_tax`
--

LOCK TABLES `night_tax` WRITE;
/*!40000 ALTER TABLE `night_tax` DISABLE KEYS */;
INSERT INTO `night_tax` VALUES (1,2,1,'Виолина','2017-04-13 00:00:00',1,'PAID','2017-04-15 19:00:19','2017-04-14 19:10:19'),(2,2,1,'Виолина','2017-04-13 00:00:00',1,'UNPAID',NULL,'2017-04-14 19:00:30'),(3,2,1,'Виолина','2017-04-13 00:00:00',1,'UNPAID',NULL,'2017-04-14 19:00:42'),(4,4,3,'Иван','2017-11-11 00:00:00',1,'UNPAID',NULL,'2017-04-14 19:04:52'),(5,4,3,'Георги','2016-02-02 00:00:00',1,'UNPAID',NULL,'2017-04-14 19:46:45'),(6,3,1,'Миро','2017-04-13 00:00:00',1,'UNPAID',NULL,'2017-04-21 09:06:08'),(8,6,4,'Стоян','2017-05-17 00:00:00',1,'UNPAID',NULL,'2017-05-06 15:05:39'),(9,7,37,'Георги','2017-05-05 00:00:00',1,'UNPAID',NULL,'2017-05-06 23:07:33'),(20,3,1,'Стоян','2017-05-09 00:00:00',1,'UNPAID',NULL,'2017-05-07 17:45:11'),(21,2,1,'Виолина','2017-06-01 00:00:00',1,'UNPAID',NULL,'2017-06-04 10:40:30'),(22,2,1,'Виолина','2017-06-02 00:00:00',1,'UNPAID',NULL,'2017-06-04 10:43:02'),(23,2,1,'Виолина','2017-06-03 00:00:00',1,'UNPAID',NULL,'2017-06-04 10:45:23'),(24,2,1,'Виолина','2017-06-04 00:00:00',1,'UNPAID',NULL,'2017-06-04 10:48:33'),(25,2,1,'Виолина','2017-05-31 00:00:00',1,'UNPAID',NULL,'2017-06-04 10:53:24'),(26,3,1,'Мирослав','2017-06-01 00:00:00',1,'UNPAID',NULL,'2017-06-04 10:57:19'),(27,3,1,'Христиан','2017-06-02 00:00:00',1,'UNPAID',NULL,'2017-06-04 11:02:00'),(29,5,4,'Явор','2017-06-07 00:00:00',1,'PAID','2017-06-14 17:04:31','2017-06-11 17:04:31'),(30,12,23,'Стоян','2017-06-20 00:00:00',1,'UNPAID',NULL,'2017-06-21 22:41:07'),(31,8,5,'Стоян','2017-06-20 00:00:00',9,'UNPAID',NULL,'2017-06-21 22:41:07'),(32,3,1,'Мирослав Стратиев','2017-06-16 00:00:00',1,'UNPAID',NULL,'2017-06-24 01:54:58'),(33,4,3,'Венелин Иванов','2017-06-22 00:00:00',1,'UNPAID',NULL,'2017-06-24 02:49:52'),(34,8,5,'Иван Иванов','2017-06-13 00:00:00',1,'UNPAID',NULL,'2017-06-24 02:50:36');
/*!40000 ALTER TABLE `night_tax` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_INHABITED'),(2,'ROLE_HOST'),(3,'ROLE_CASHIER'),(4,'ROLE_ADMINISTRATOR');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(45) NOT NULL,
  `block_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'229',1),(3,'701',1),(4,'105',1),(5,'105',2),(6,'200',1),(7,'201',1),(8,'202',1),(9,'203',1),(10,'204',1),(11,'205',1),(12,'206',1),(13,'207',1),(14,'208',1),(15,'209',1),(16,'210',1),(17,'211',1),(18,'212',1),(19,'213',1),(20,'214',1),(21,'215',1),(22,'216',1),(23,'217',1),(24,'218',1),(25,'219',1),(26,'220',1),(27,'221',1),(28,'222',1),(29,'223',1),(30,'224',1),(31,'225',1),(32,'226',1),(33,'227',1),(34,'228',1),(35,'230',1),(37,'710',1);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `personal_number` varchar(10) NOT NULL,
  `email` varchar(45) NOT NULL,
  `role_id` int(11) NOT NULL,
  `activated` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Любка','2','tee0@abv.bg',2,0),(2,'Теодор Златев','1','tee0@abv.bg',1,0),(3,'Йордан Калъчев','123','tee0@abv.bg',1,0),(4,'Пламен Койнов','1234','tee0@abv.bg',1,0),(5,'Борислав Петровски','12345','tee0@abv.bg',1,0),(6,'Златко Стоянов','123456','tee0@abv.bg',1,0),(7,'Петър Лазаров','1234567','tee0@abv.bg',1,0),(8,'иван','12','tee0@abv.bg',1,0),(9,'Стоян Иванов Иванов','1231231230','tee0@abv.bg',2,0),(10,'Админ','4','tee0@abv.bg',4,0),(11,'Касиер','3','tee0@abv.bg',3,0),(12,'Иван Иванов Иванов','2222222222','tee0@abv.bg',1,0),(13,'Тест ','9999999999','tee0@abv.bg',1,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_room`
--

DROP TABLE IF EXISTS `user_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_room`
--

LOCK TABLES `user_room` WRITE;
/*!40000 ALTER TABLE `user_room` DISABLE KEYS */;
INSERT INTO `user_room` VALUES (1,2,1),(2,3,1),(3,4,3),(4,5,4),(5,6,4),(6,7,37),(7,8,5),(8,9,9),(9,12,23),(10,13,23);
/*!40000 ALTER TABLE `user_room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-24 14:13:10
