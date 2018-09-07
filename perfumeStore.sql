/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.41-log : Database - perfume
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`perfume` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `perfume`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `ADDRESS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SEND_PLACE` varchar(255) NOT NULL,
  `SEND_MAN` varchar(255) NOT NULL,
  `SEND_PHONE` varchar(255) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ADDRESS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `address` */

insert  into `address`(`ADDRESS_ID`,`SEND_PLACE`,`SEND_MAN`,`SEND_PHONE`,`USER_ID`) values (1,'Unimelb','Jack','12345678',1);

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `CART_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) NOT NULL DEFAULT '0',
  `SALE_COUNT` int(11) NOT NULL DEFAULT '0',
  `USER_ID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CART_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `cart` */

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `ORDER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ORDER_NUM` varchar(255) NOT NULL DEFAULT '',
  `ORDER_TIME` datetime NOT NULL,
  `ORDER_STATUS` int(1) NOT NULL DEFAULT '0' COMMENT '0-->order created//1-->paid//2-->delivering//3-->done',
  `NOTE` varchar(100) DEFAULT '',
  `USER_ID` int(11) NOT NULL DEFAULT '0',
  `SEND_PLACE` varchar(255) NOT NULL DEFAULT '',
  `SEND_MAN` varchar(255) NOT NULL DEFAULT '',
  `SEND_PHONE` varchar(255) NOT NULL DEFAULT '',
  `PRODUCT_ID` int(11) NOT NULL DEFAULT '0',
  `PRODUCT_NAME` varchar(255) NOT NULL DEFAULT '',
  `PRODUCT_PRICE` double(11,2) NOT NULL DEFAULT '0.00',
  `SALE_COUNT` int(11) NOT NULL DEFAULT '0',
  `VISIBLE` int(1) NOT NULL DEFAULT '1' COMMENT '0-->deleted by user//1-->not deleted',
  PRIMARY KEY (`ORDER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`ORDER_ID`,`ORDER_NUM`,`ORDER_TIME`,`ORDER_STATUS`,`NOTE`,`USER_ID`,`SEND_PLACE`,`SEND_MAN`,`SEND_PHONE`,`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`,`SALE_COUNT`,`VISIBLE`) values (1,'15359833327434151','2018-09-04 00:02:13',1,'',1,'Unimelb','Jack','12345678',1,'%Lancome Poeme Eau de Parfum 100ml Vapouriser%',90.00,1,1),(2,'15359833327434151','2018-09-04 00:02:13',1,'',1,'Unimelb','Jack','12345678',2,'%Calvin Klein Euphoria for Men Eau de Toilette 100ml Spray%',35.00,3,1),(3,'153598412456429371','2018-09-04 00:15:25',0,'',1,'Unimelb','Jack','12345678',1,'%Lancome Poeme Eau de Parfum 100ml Vapouriser%',90.00,1,1),(4,'153598412456429371','2018-09-04 00:15:25',0,'',1,'Unimelb','Jack','12345678',3,'%Giorgio Armani SI Eau De Parfum 100ml%',120.00,5,1);

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_NAME` varchar(255) NOT NULL DEFAULT '',
  `PRODUCT_PRICE` double(11,2) NOT NULL DEFAULT '0.00',
  `PRODUCT_DESC` varchar(255) DEFAULT '',
  `PRODUCT_IMAGE_PATH` varchar(255) DEFAULT '',
  `STORE_NUM` int(11) NOT NULL DEFAULT '0',
  `PRODUCT_STATUS` int(1) DEFAULT '1' COMMENT '0-->delete/1-->active',
  PRIMARY KEY (`PRODUCT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`,`PRODUCT_DESC`,`PRODUCT_IMAGE_PATH`,`STORE_NUM`,`PRODUCT_STATUS`) values (1,'Lancome Poeme Eau de Parfum 100ml Vapouriser',90.00,'Lancome Poeme Eau de Parfum 100ml Vapouriser','upload/64a34e68-d96f-412b-b82c-40a232c85d7d.jpg',998,1),(2,'Calvin Klein Euphoria for Men Eau de Toilette 100ml Spray',35.00,'Calvin Klein Euphoria for Men Eau de Toilette 100ml Spray','upload/293bf758-692e-4241-9e12-ceaa3392b69f.jpg',1834,1),(3,'Giorgio Armani SI Eau De Parfum 100ml',120.00,'Giorgio Armani SI Eau De Parfum 100ml','upload/f4af5e26-ad2d-4321-b9a1-94a0b18ee28a.jpg',8480,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `USER_STATUS` int(1) NOT NULL DEFAULT '1' COMMENT 'default 1：active，0：inactive',
  `TRUENAME` varchar(255) DEFAULT '',
  `PHONE` varchar(255) DEFAULT '',
  `ADDRESS` varchar(255) DEFAULT '',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`USER_ID`,`USERNAME`,`PASSWORD`,`USER_STATUS`,`TRUENAME`,`PHONE`,`ADDRESS`) values (1,'aaa','aaa',1,'Tom','123456','melbourne');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
