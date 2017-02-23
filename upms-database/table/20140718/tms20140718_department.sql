/*
SQLyog Enterprise - MySQL GUI v6.11
MySQL - 5.5.27 : Database - tms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `tms`;

USE `tms`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `security_department` */

DROP TABLE IF EXISTS `security_department`;

CREATE TABLE `security_department` (
  `department_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(80) DEFAULT NULL,
  `department_code` varchar(80) NOT NULL,
  `department_parent_id` bigint(11) DEFAULT NULL,
  `department_describe` varchar(200) DEFAULT NULL,
  `department_rank` bigint(1) NOT NULL,
  PRIMARY KEY (`department_id`,`department_code`),
  UNIQUE KEY `department_id` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `security_department` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
