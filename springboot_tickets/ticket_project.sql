DROP DATABASE IF EXISTS  ticket_project;
CREATE DATABASE ticket_project
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

use ticket_project;

DROP TABLE if EXISTS flights;
CREATE TABLE flights(
	flight_id VARCHAR(100) NOT null,
	airline VARCHAR(100) DEFAULT null,
	airtype VARCHAR(100) DEFAULT null,
	start_time TIME DEFAULT null,
	end_time TIME DEFAULT null,
	start_city VARCHAR(100) DEFAULT null,
	end_city VARCHAR(100) DEFAULT null,
	start_airport VARCHAR(100) DEFAULT null,
	end_airport VARCHAR(100) DEFAULT null,
	PRIMARY KEY(flight_id)
);

DROP TABLE IF EXISTS schedules;
CREATE TABLE schedules(
	flight_id VARCHAR(100) not null,
	start_date DATE,
	nums int DEFAULT 0,
	price DOUBLE DEFAULT 0,
	punctuality bool DEFAULT TRUE,
	PRIMARY KEY(flight_id , start_date ),
	FOREIGN KEY(flight_id) REFERENCES flights(flight_id)
);

DROP TABLE IF EXISTS user_type;
CREATE TABLE user_type(
    type VARCHAR(100) not null,
    PRIMARY KEY( type )
);

DROP TABLE IF EXISTS users;
CREATE TABLE users(
	user_id VARCHAR(100) not null,
	user_name VARCHAR(100) not null,
	user_password VARCHAR(100) not null,
	balance DOUBLE DEFAULT 0,
	type VARCHAR(100) not null,
	PRIMARY KEY( user_id ),
	FOREIGN KEY( type ) REFERENCES user_type(type)
);

DROP TABLE IF EXISTS passengers;
CREATE TABLE passengers(
	passenger_id VARCHAR(100) not NULL,
	passenger_name VARCHAR(100) not null,
	user_id VARCHAR(100) not null,
	PRIMARY KEY( passenger_id ),
	FOREIGN KEY( user_id ) REFERENCES users( user_id ),
	INDEX(user_id)
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
	order_id INTEGER not NULL AUTO_INCREMENT,
	user_id VARCHAR(100) not NULL,
	passenger_id VARCHAR(100) not null,
	flight_id VARCHAR(100) not null,
	start_date DATE,
	amount int,
	update_time DATETIME,
	is_pay bool DEFAULT FALSE,
	PRIMARY KEY( order_id ),
	FOREIGN KEY( user_id ) REFERENCES users( user_id ),
	FOREIGN KEY( passenger_id ) REFERENCES passengers( passenger_id ),
	FOREIGN KEY( flight_id ) REFERENCES flights( flight_id ),
	INDEX(user_id)
);

DROP TABLE IF EXISTS messages;
CREATE TABLE messages(
    message_id INTEGER not NULL AUTO_INCREMENT,
	user_id VARCHAR(100) not NULL,
	msg VARCHAR(1000),
	update_time DATETIME,
	PRIMARY KEY( message_id )
);


DROP VIEW IF EXISTS flightsandschedules;
CREATE VIEW flightsandschedules
(
     flight_id , airline , airtype , start_time , end_time , start_city , end_city , start_airport , end_airport , start_date , nums , price , punctuality
)
AS SELECT
flight_id , airline , airtype , start_time , end_time , start_city , end_city , start_airport , end_airport , start_date , nums , price , punctuality
FROM (flights NATURAL JOIN schedules);


INSERT INTO flights VALUES
('CZ8882','南方航空','空客321','07:50:00','10:15:00','上海','北京','上海浦东T2','北京大兴'),
('MU6734','东方航空','波音738','15:45:00','17:30:00','武汉','广州','武汉天河T3','广州白云T1'),
('3U8920','四川航空','A321', '06:05:00', '11:05:00','杭州', '成都','杭州萧山T4','成都双流T2'),
('MU2767','东方航空','空客319','19:25:00','21:35:00','南京','西安','南京禄口T2','西安咸阳T3'),
('CA8685','中国民航','空客320','17:00:00','19:25:00','上海','北京','上海浦东T2','北京大兴'),
('MU5099','东方航空','空客330','07:00:00','09:15:00','上海','北京','上海虹桥T2','北京首都T2'),
('KN2959','中国联合航空','波音737','08:10:00','10:35:00','上海','北京','上海浦东T1','北京大兴'),
('MU5153','东方航空','空客321','09:30:00','12:00:00','上海','北京','上海虹桥T2','北京首都T2');



INSERT INTO user_type VALUES
('admin'),
('user');

INSERT INTO schedules VALUES
('CA8685','2024-09-01',1000,500,true),
('MU5099','2024-09-01',1000,500,true),
('KN2959','2024-09-01',1000,500,true),
('3U8920','2024-09-01',1000,500,true),
('3U8920','2024-09-02',1020,500,true),
('3U8920','2024-09-03',1100,500,true),
('3U8920','2024-09-04',2000,500,true),
('3U8920','2024-09-05',1340,500,true),
('3U8920','2024-09-06',1000,500,true),
('3U8920','2024-09-07',1000,500,false),
('3U8920','2024-09-08',1000,500,true),
('3U8920','2024-09-09',10430,500,true),
('3U8920','2024-09-10',1060,500,true),
('CZ8882','2024-09-01',2000,700,true),
('CZ8882','2024-09-02',2000,700,true),
('CZ8882','2024-09-03',2000,700,true),
('CZ8882','2024-09-04',230,700,true),
('CZ8882','2024-09-05',20440,700,true),
('CZ8882','2024-09-06',2040,700,true),
('CZ8882','2024-09-07',2020,700,true),
('CZ8882','2024-09-08',2100,700,true),
('MU5153','2024-09-01',1000,600,true),
('MF8528','2024-09-06',1200,800,true),
('MF8109','2024-09-06',1200,800,true),
('CZ4447','2024-09-08',1200,580,true),
('HU7603','2024-09-08',12000,550,true),
('CA8685','2024-10-01',1000,500,true),
('MU5099','2024-10-01',1000,500,true),
('KN2959','2024-10-01',1000,500,true),
('3U8920','2024-10-01',1000,500,true),
('3U8920','2024-10-02',1020,500,true),
('3U8920','2024-10-03',1100,500,true),
('3U8920','2024-10-04',2000,500,true),
('3U8920','2024-10-05',1340,500,true),
('3U8920','2024-10-06',1000,500,true),
('3U8920','2024-10-07',1000,500,false),
('3U8920','2024-10-08',1000,500,true),
('3U8920','2024-10-09',10430,500,true),
('3U8920','2024-10-10',1060,500,true),
('CZ8882','2024-10-01',2000,700,true),
('CZ8882','2024-10-02',2000,700,true),
('CZ8882','2024-10-03',2000,700,true),
('CZ8882','2024-10-04',230,700,true),
('CZ8882','2024-10-05',20440,700,true),
('CZ8882','2024-10-06',2040,700,true),
('CZ8882','2024-10-07',2020,700,true),
('CZ8882','2024-10-08',2100,700,true),
('MU5153','2024-10-01',1000,600,true),
('MF8528','2024-10-06',1200,800,true),
('MF8109','2024-10-06',1200,800,true),
('CZ4447','2024-10-08',1200,580,true),
('HU7603','2024-10-08',12000,550,true);



INSERT INTO passengers
VALUES
('371421200501305975','姚荣浩','17667399193');

INSERT INTO orders
VALUES
(null,'17667399193','371421200501305975','CZ8882','2024-09-15',1000,'2024-08-15 11:45:04',false);



insert into messages values(0,"17667399193","这是我的第一条消息","2024-09-02 10:00:00");
insert into messages values(0,"17667399193",
"这是我的第二条消息，很长长----------------------------------------------------------------------------------------------------------------------------------------------------",
"2024-09-02 11:00:00");
