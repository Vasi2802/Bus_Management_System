-- data for stop table

desc stop;
select * from stop;
select * from route;


insert into stop(name) values
('Belgharia'),
('Dunlop Bridge'),
('Sinthee More'),
('Chiria More'),
('Shyambazar'),
('Khanna More'),
('Ultadanga Main Rd'),
('Hudco More'),
('Salt Lake Karunamayee'),
('Wipro More'),
('Nalban'),
('NRI FinTech');

insert into stop(name) values
('Howrah Station'),
('Posta'),
('M G Road'),
('Sovabazar'),
('Hatibagan');

insert into stop(name) values
('Jadavpur'),
('Sulekha'),
('Santoshpur Lake'),
('Ajaynagar'),
('Ruby'),
('Science City'),
('Chingrighata'),
('Building More');

insert into stop(name) values
('Behala Chowrasta'),
('14 No.'),
('Taratala'),
('New Alipore'),
('Tollygunge Fari'),
('Rashbehari Avenue'),
('Deshapriya Park'),
('Gariahat'),
('Kashba');

insert into stop(name) values
('New Town Bus Stop'),
('Technopolis'),
('College More'),
('Webel More');


-- data for route table

desc route;
select * from route;

insert into route(active,start,end) values
('YES', 1, 12);

insert into route(active,start,end) values
('YES', 13, 12);

insert into route(active,start,end) values
('YES', 18, 12);

insert into route(active,start,end) values
('YES', 26, 12);

insert into route(active,start,end) values
('YES', 35, 12);


-- data for time table

desc time;
select * from time;

insert into time(route_id, stop_id, morning_arrival_time, evening_arrival_time) values
(1,1,'06:50','19:10'),
(1,2,'07:00','19:00'),
(1,3,'07:15','18:45'),
(1,4,'07:25','18:35'),
(1,5,'07:35','18:25'),
(1,6,'07:40','18:20'),
(1,7,'07:50','18:10'),
(1,8,'07:55','18:05'),
(1,9,'08:10','17:50'),
(1,10,'08:15','17:45'),
(1,11,'08:25','17:35'),
(1,12,'08:30','17:30');

insert into time(route_id, stop_id, morning_arrival_time, evening_arrival_time) values
(2,13,'06:45','19:15'),
(2,14,'07:05','18:55'),
(2,15,'07:20','18:40'),
(2,16,'07:30','18:30'),
(2,17,'07:35','18:25'),
(2,6,'07:40','18:20'),
(2,7,'07:50','18:10'),
(2,8,'07:55','18:05'),
(2,9,'08:10','17:50'),
(2,10,'08:15','17:45'),
(2,11,'08:25','17:35'),
(2,12,'08:30','17:30');

insert into time(route_id, stop_id, morning_arrival_time, evening_arrival_time) values
(3,18,'07:00','19:00'),
(3,19,'07:05','18:55'),
(3,20,'07:15','18:45'),
(3,21,'07:25','18:35'),
(3,22,'07:30','18:30'),
(3,23,'07:35','18:25'),
(3,24,'07:50','18:10'),
(3,25,'07:55','18:05'),
(3,9,'08:10','17:50'),
(3,10,'08:15','17:45'),
(3,11,'08:25','17:35'),
(3,12,'08:30','17:30');

insert into time(route_id, stop_id, morning_arrival_time, evening_arrival_time) values
(4,26,'06:10','19:50'),
(4,27,'06:20','19:40'),
(4,28,'06:25','19:35'),
(4,29,'06:35','19:25'),
(4,30,'06:50','19:10'),
(4,31,'07:00','19:00'),
(4,32,'07:05','18:55'),
(4,33,'07:10','18:50'),
(4,34,'07:25','18:35'),
(4,22,'07:30','18:30'),
(4,23,'07:35','18:25'),
(4,24,'07:50','18:10'),
(4,25,'07:55','18:05'),
(4,9,'08:10','17:50'),
(4,10,'08:15','17:45'),
(4,11,'08:25','17:35'),
(4,12,'08:30','17:30');

insert into time(route_id, stop_id, morning_arrival_time, evening_arrival_time) values
(5,35,'08:00','18:00'),
(5,36,'08:10','17:50'),
(5,37,'08:15','17:45'),
(5,38,'08:25','17:35'),
(5,12,'08:30','17:30');

-- data for user table

desc user;
select * from user;

insert into user(username, password, role) values
('driver1@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER'),
('driver2@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER'),
('driver3@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER'),
('driver4@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER'),
('driver5@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER'),
('driver6@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER'),
('driver7@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER'),
('driver8@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER'),
('driver9@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER'),
('driver10@driver.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_DRIVER');


insert into user(username, password, role) values
('subhashreem@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('anupama@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('shreyanshs@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('ankitag@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('achyutm@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('mohammadv@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('arijitse@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('prajeetg@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('shreyar@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('abhijits@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('roshans@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('anuragb@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('annweshak@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('suchetanc@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('anikets@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('swairikd@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('rajarshigd@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('subhs@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('satikkp@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('monidepag@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('pragyaj@nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE'),
('sagniks@trainee.nrifintech.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'ROLE_EMPLOYEE');




-- data for driver table

desc driver;
select * from driver;

insert into driver(driver_name, driver_contact, auth_id) values
('Anuj Sharma', '1234567890', 1),
('Akash Das', '45678910203', 2),
('Nakul Meheta', '03325545687521', 3),
('Prateek Sinha', '1234560009', 4),
('Satwik Mitra', '10003456789', 5),
('Sagnik Bose', '1230000789', 6),
('Farzan Ali', '1234500089', 7),
('Rohit Paul', '000456789', 8),
('Rahul Kumar', '9876543210', 9),
('Sahil Mullick', '120000000789', 10);

-- data for bus table

desc bus;
select * from bus;

insert into bus(start_time, total_seats, available_seats, bus_route, driver_id) values
('06:40', 20,20, 1, 1),
('06:40', 15,15, 1, 2),
('06:35', 25,25, 2, 3),
('06:35', 5,5, 2, 4),
('06:50', 20,20, 3, 5),
('06:50', 20,20, 3, 6),
('06:05', 25,25, 4, 7),
('06:05', 5,5, 4, 8),
('07:45', 20,20, 5, 9);







