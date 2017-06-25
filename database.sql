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
  `host_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `host_block_idx` (`host_id`),
  CONSTRAINT `host_block` FOREIGN KEY (`host_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `block`
--

LOCK TABLES `block` WRITE;
/*!40000 ALTER TABLE `block` DISABLE KEYS */;
INSERT INTO `block` VALUES (1,'2',3),(2,'3',6),(3,'4',7),(4,'51А',8);
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
  `password` varchar(129) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_credentials_idx` (`user_id`),
  CONSTRAINT `user_credentials` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials`
--

LOCK TABLES `credentials` WRITE;
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `credentials` VALUES (1,1,'5aa62787f3302adb183911b7a7c9e61e3569706e806973ac0cfcafe5b8b49cd8ec5a523c91dd1c0f1e06fd7888ed4c5fab1462dd60a3c4f8e078e721757fe07c'),(2,2,'0ede7934a345eadfa811fb5e7fe42202631641f2dfc35772da213214ce11f3dc7136e2344b990d8cfe6908c4c04fda35d24e6d5e4782b2f96e4fd5ab48cb50ba'),(3,3,'3d1a7f4b9da8a0f40c7c6d738c5da75bdb011a98afb6abc7ca0bacf4cd058e8dad0c7d1c0de74b2846ef5bbadd8a3060a129fa2768002f8b5bcff641b0ed2ffa'),(4,4,'1185c62cb95556c7417ba47a00878d125deb39f7a46da0c3b32b453581b4110a8d5a47caf50fe5ad355110a62beaef4e52af512d3b68c8ac9c32ed1d63320cd2'),(5,5,'84de751752fce7babdebe358d97b9c538c8958cac88398d5d0716375ab73d4276d596a2f099c6379f2a43922b2195bf6ef7014d9769a0da6b84a83982d5133a2'),(6,6,'578422d35c3a058bc641936d1283c896c5745a48aae426bb85d364727f8f43a3ff979deef95c99fe4a11b16abfebe761102de69e5c10db041d40e8747dda0cb2'),(7,7,'2b9099ae19555e7b4618b628345934b75b62c85636cb174f22c91157e5ae3147c767c6ebd66c5367a9b7e89f54cad6fb18dc7897b342a5971f8cf2f1ea9df7fc'),(8,8,'daac5764ee868e7fc4f7f7128d48e9252033e00fabbfe119870b06037687931be91c396d492109304c025a279bd6440f686b24ba17656b0e311e4b9d90afe696'),(9,9,'efa2daaed0e83a958720513e763203829cc81111b0a35e72d5cb4b8fbcaa9c0c0623019364d2760ee26ec4402ee25fb72132ca9e16873fc5a724472315589621');
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
  PRIMARY KEY (`id`),
  KEY `user_idx` (`user_id`),
  KEY `creator_idx` (`created_by`),
  KEY `room_idx` (`room_id`),
  CONSTRAINT `creator_nighttax` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `room_nighttax` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_nighttax` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `night_tax`
--

LOCK TABLES `night_tax` WRITE;
/*!40000 ALTER TABLE `night_tax` DISABLE KEYS */;
INSERT INTO `night_tax` VALUES (1,9,7,'Георги','2017-06-25 00:00:00',8,'UNPAID',NULL,'2017-06-25 18:02:28');
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
  PRIMARY KEY (`id`),
  KEY `block_idx` (`block_id`),
  CONSTRAINT `block_room` FOREIGN KEY (`block_id`) REFERENCES `block` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'229',1),(2,'105',1),(3,'701',1),(4,'301',2),(5,'305',2),(6,'602',2),(7,'405',4),(8,'525',4),(9,'526',4);
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
  PRIMARY KEY (`id`),
  KEY `role_idx` (`role_id`),
  CONSTRAINT `role_user` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Администратор 1','4','tee0@abv.bg',4,0),(2,'Домакин блок 2','3','tee0@abv.bg',3,0),(3,'Касиер 1','2','tee0@abv.bg',2,0),(4,'Теодор Златев','1','tee0@abv.bg',1,0),(5,'Йордан Калъчев','9309011212','tee0@abv.bg',1,0),(6,'Домакин трети','1231231230','tee0@abv.bg',2,0),(7,'Домакин четвърти','1231231232','tee0@abv.bg',2,0),(8,'Домакин петЕдноА','5555555555','tee0@abv.bg',2,0),(9,'Стоян Стоянов','1231231239','tee0@abv.bg',1,0);
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
  PRIMARY KEY (`id`),
  KEY `user_idx` (`user_id`),
  KEY `room_idx` (`room_id`),
  CONSTRAINT `room_userroom` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_userroom` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_room`
--

LOCK TABLES `user_room` WRITE;
/*!40000 ALTER TABLE `user_room` DISABLE KEYS */;
INSERT INTO `user_room` VALUES (1,4,1),(2,5,1),(3,9,7);
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

-- Dump completed on 2017-06-25 18:39:01
