-- MySQL dump 10.13  Distrib 5.7.15, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.15-log

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
-- Table structure for table `check_list`
--

DROP TABLE IF EXISTS `check_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_list` (
  `check_id` int(11) NOT NULL AUTO_INCREMENT,
  `check_type` int(11) DEFAULT NULL,
  `check_name` varchar(16) DEFAULT NULL,
  `check_detail` varchar(32) DEFAULT NULL,
  `check_result` int(11) DEFAULT NULL,
  PRIMARY KEY (`check_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_list`
--

LOCK TABLES `check_list` WRITE;
/*!40000 ALTER TABLE `check_list` DISABLE KEYS */;
INSERT INTO `check_list` VALUES (1,3,'45','sad',1),(2,12,'gg','asd',22),(3,12,'gg','asd',22),(4,12,'gg','asd',22),(5,12,'gg','asd',22),(6,12,'gg','asd',22);
/*!40000 ALTER TABLE `check_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_plan`
--

DROP TABLE IF EXISTS `check_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_plan` (
  `plan_id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(16) NOT NULL,
  `plan_cycle` int(11) NOT NULL,
  `plan_start_date` date NOT NULL,
  `plan_star_time` time NOT NULL,
  `plan_dead_time` time NOT NULL,
  `inspector_id` int(11) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  `plan_state` int(11) NOT NULL,
  `plan_note` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_plan`
--

LOCK TABLES `check_plan` WRITE;
/*!40000 ALTER TABLE `check_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `check_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_plan_detail`
--

DROP TABLE IF EXISTS `check_plan_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_plan_detail` (
  `cd_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cd_plan_id` int(10) unsigned DEFAULT NULL,
  `cd_eq_id` int(10) unsigned DEFAULT NULL,
  `cd_check_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`cd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_plan_detail`
--

LOCK TABLES `check_plan_detail` WRITE;
/*!40000 ALTER TABLE `check_plan_detail` DISABLE KEYS */;
INSERT INTO `check_plan_detail` VALUES (1,2,1,3),(2,11,213,666),(3,11,213,666),(4,11,213,666),(5,11,213,666);
/*!40000 ALTER TABLE `check_plan_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_result`
--

DROP TABLE IF EXISTS `check_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_result` (
  `result_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `eq_id` int(10) unsigned DEFAULT NULL,
  `eq_name` varchar(16) DEFAULT NULL,
  `check_name` varchar(16) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  `result_detail` json DEFAULT NULL,
  PRIMARY KEY (`result_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_result`
--

LOCK TABLES `check_result` WRITE;
/*!40000 ALTER TABLE `check_result` DISABLE KEYS */;
INSERT INTO `check_result` VALUES (1,123,'asd','zx',123,'[\"kk\"]'),(2,123,'asd','zx',123,'[\"kk\"]'),(3,123,'asd','zx',123,'[\"kk\"]'),(4,123,'asd','zx',123,'[\"kk\"]'),(5,123,'asd','zx',123,'[\"kk\"]');
/*!40000 ALTER TABLE `check_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_task`
--

DROP TABLE IF EXISTS `check_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_task` (
  `task_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `task_start_time` timestamp(6) NULL DEFAULT NULL,
  `task_dead_time` timestamp(6) NULL DEFAULT NULL,
  `task_state` int(11) DEFAULT NULL,
  `plan_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_task`
--

LOCK TABLES `check_task` WRITE;
/*!40000 ALTER TABLE `check_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `check_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eq_group`
--

DROP TABLE IF EXISTS `eq_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eq_group` (
  `group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_manager` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eq_group`
--

LOCK TABLES `eq_group` WRITE;
/*!40000 ALTER TABLE `eq_group` DISABLE KEYS */;
INSERT INTO `eq_group` VALUES (1,213123),(2,213123),(3,213123),(4,213123),(5,213123),(6,213123);
/*!40000 ALTER TABLE `eq_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eq_list`
--

DROP TABLE IF EXISTS `eq_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eq_list` (
  `eq_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `eq_name` varchar(16) NOT NULL,
  `eq_group` int(10) unsigned DEFAULT NULL,
  `eq_type` int(10) unsigned NOT NULL,
  `eq_state` tinyint(1) unsigned NOT NULL,
  `eq_manager` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`eq_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eq_list`
--

LOCK TABLES `eq_list` WRITE;
/*!40000 ALTER TABLE `eq_list` DISABLE KEYS */;
INSERT INTO `eq_list` VALUES (1,'ASD',21,12,4,213),(2,'2',NULL,3,2,NULL),(3,'2',NULL,3,2,NULL),(4,'shebei',1,2,1,4),(5,'shebei',1,2,1,4),(6,'shebei',1,2,1,4),(7,'shebei',1,2,1,4),(8,'shebei',1,2,1,4);
/*!40000 ALTER TABLE `eq_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eq_type`
--

DROP TABLE IF EXISTS `eq_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eq_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(16) NOT NULL,
  `type_check` json NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eq_type`
--

LOCK TABLES `eq_type` WRITE;
/*!40000 ALTER TABLE `eq_type` DISABLE KEYS */;
INSERT INTO `eq_type` VALUES (1,'asd','[1, 4, 6]'),(2,'test','{\"key\": 33}'),(3,'test','{\"key\": 33}'),(4,'test','[1, 2, 3]'),(5,'as','[1, 2, 3]'),(6,'as','[1, 2, 3]'),(7,'as','[1, 2, 3]'),(8,'as','[1, 2, 3]'),(9,'as','[1, 2, 3]'),(10,'as','[1, 2, 3]');
/*!40000 ALTER TABLE `eq_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repair_list`
--

DROP TABLE IF EXISTS `repair_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repair_list` (
  `repair_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `repair_eqid` int(10) unsigned NOT NULL,
  `repair_detail` varchar(128) NOT NULL,
  `repair_state` tinyint(1) NOT NULL,
  `repair_inspector_id` int(11) DEFAULT NULL,
  `report_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `m_got_time` timestamp(6) NULL DEFAULT NULL,
  `m_deal_time` timestamp(6) NULL DEFAULT NULL,
  `i_got_time` timestamp(6) NULL DEFAULT NULL,
  `i_arrive_time` timestamp(6) NULL DEFAULT NULL,
  `i_deal_time` timestamp(6) NULL DEFAULT NULL,
  `finish_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`repair_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair_list`
--

LOCK TABLES `repair_list` WRITE;
/*!40000 ALTER TABLE `repair_list` DISABLE KEYS */;
INSERT INTO `repair_list` VALUES (1,0,'rain',0,NULL,'2016-11-04 17:16:23.585050',NULL,NULL,NULL,NULL,NULL,NULL),(2,1,'umbrella',0,NULL,'2016-11-04 17:21:30.534607','1996-10-30 03:11:11.000000',NULL,NULL,NULL,NULL,NULL),(3,112,'kaiwanxiao',0,NULL,'2016-11-05 09:45:57.192245',NULL,NULL,NULL,NULL,NULL,'2016-11-05 11:07:31.315000'),(4,112,'kaiwanxiao',0,NULL,'2016-11-05 09:56:33.260626',NULL,NULL,NULL,NULL,NULL,NULL),(5,112,'kaiwanxiao',0,NULL,'2016-11-05 09:59:07.884470',NULL,NULL,NULL,NULL,NULL,NULL),(6,112,'kaiwanxiao',0,NULL,'2016-11-05 09:59:54.937161',NULL,NULL,NULL,NULL,NULL,NULL),(7,112,'kaiwanxiao',0,NULL,'2016-11-05 10:08:56.753152',NULL,NULL,NULL,NULL,NULL,NULL),(8,112,'kaiwanxiao',0,NULL,'2016-11-05 10:09:05.954678',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `repair_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_UNIQUE` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'aaaa'),(1,'kkkk'),(3,'lll');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `staff_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `staff_pwd` varchar(16) NOT NULL,
  `staff_job` varchar(6) NOT NULL,
  `staff_phone` char(11) NOT NULL,
  `staff_name` varchar(6) NOT NULL,
  `staff_role` int(10) unsigned NOT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100005 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (100000,'123','jj','11111111111','asd',3),(100001,'kkk','jj','11111111111','asd',3),(100002,'as','oo','12222111111','zxc',5),(100003,'123','jj','11111111111','asd',3),(100004,'123','jj','11111111111','asd',3);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id` int(10) unsigned NOT NULL,
  `json` json DEFAULT NULL,
  `dat` timestamp(4) NULL DEFAULT NULL,
  `bool` tinyint(1) DEFAULT NULL,
  `gg` varchar(16) DEFAULT NULL,
  `gaomei` int(11) DEFAULT NULL,
  `ti` time(4) DEFAULT NULL,
  `da` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'[666, 777, 999]',NULL,NULL,NULL,NULL,'11:48:12.0000','2016-11-05'),(2,'[666, 777, 999, {\"asd\": 1, \"bbb\": \"666\"}]','1996-10-30 03:12:13.0000',NULL,NULL,NULL,NULL,NULL),(3,'[666, 777, \"999\", {\"asd\": 1, \"bbb\": \"666\"}]',NULL,NULL,NULL,NULL,NULL,NULL),(4,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,NULL,NULL,1,NULL,NULL,NULL,NULL),(12,'[1, 2]',NULL,NULL,NULL,NULL,NULL,NULL),(13,'{\"key\": 11}',NULL,NULL,NULL,NULL,NULL,NULL),(14,'[\"a\", {\"key\": 11}]',NULL,NULL,NULL,NULL,NULL,NULL),(16,'[\"a\", {\"key\": 123}]',NULL,NULL,NULL,NULL,NULL,NULL),(23,'[\"a\", {\"key\": 1}]',NULL,NULL,NULL,NULL,NULL,NULL),(333,NULL,NULL,NULL,'7777',NULL,NULL,NULL),(444,NULL,NULL,NULL,'7777',NULL,NULL,NULL),(555,NULL,NULL,NULL,'7777',NULL,NULL,NULL),(777,NULL,NULL,NULL,'asdasd',NULL,NULL,NULL),(888,NULL,'1996-10-30 02:22:33.0000',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-05 20:41:11
