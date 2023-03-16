# BusManagementSystem

## Steps to use

1. Open sql workbench
2. Run the following query

create database project;\
use project;\
CREATE TABLE `user` (\
  `id` bigint(10) NOT NULL AUTO_INCREMENT,\
  `username` varchar(50) NOT NULL,\
  `password` char(80) NOT NULL,\
  `role` varchar(15) NOT NULL,\
  PRIMARY KEY (`id`)\
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;\

<!-- fun123 -->
INSERT INTO `user` (username,password,role)\
VALUES \
('anupam','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','ROLE_ADMIN');


3. Run the application
4. Register new employees and try login with the new data
