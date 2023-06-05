
CREATE TABLE IF NOT exists ATTACHMENT
(id int(11) not null AUTO_INCREMENT,
name varchar(256) not null,
attachmentType varchar(100) not null,
size double,
attachment longblob not null,
 PRIMARY KEY (`id`)
);