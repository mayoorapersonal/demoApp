

CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255),
  PRIMARY KEY (`id`)
);

ALTER table student add column deptId int(11) AFTER lastName;

ALTER TABLE student ADD FOREIGN KEY (deptId) REFERENCES department(id) ON DELETE CASCADE ON UPDATE CASCADE;

