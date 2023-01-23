

CREATE TABLE IF NOT EXISTS `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dob` datetime(6) DEFAULT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


ALTER TABLE `student` ADD COLUMN  `registerNo` int AFTER `lastName`;


CREATE TABLE `_user` (
  `id` integer NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) ,
  `lastName` varchar(255) ,
  `email` varchar(255) ,
  `password` varchar(255),
  PRIMARY KEY (`id`)
);

ALTER TABLE `_user` ADD COLUMN `role` VARCHAR(255) AFTER `password`;