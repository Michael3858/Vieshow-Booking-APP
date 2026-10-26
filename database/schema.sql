-- VIESHOW classroom booking-system schema; original records are not included.
CREATE DATABASE IF NOT EXISTS `booking_system` DEFAULT CHARACTER SET utf8mb4;
USE `booking_system`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `auditorium` (
  `Auditorium_ID` varchar(100) NOT NULL,
  `Theater_ID` varchar(100) NOT NULL,
  `Auditorium_Name` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `booking_meals` (
  `Ticket_ID` varchar(100) NOT NULL,
  `Meal_ID` varchar(100) NOT NULL,
  `Num` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `booking_record` (
  `Ticket_ID` varchar(100) NOT NULL,
  `User_Email` varchar(100) NOT NULL,
  `Screenings_ID` varchar(100) NOT NULL,
  `Booking_Payment` varchar(1000) NOT NULL,
  `Booking_Time` varchar(1000) NOT NULL,
  `Total_Price` varchar(1000) NOT NULL,
  `Serial_Number` varchar(1000) NOT NULL,
  `State` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `booking_seat` (
  `Ticket_ID` varchar(100) NOT NULL,
  `Seat` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `booking_ticket_type` (
  `Ticket_ID` varchar(100) NOT NULL,
  `Type_ID` varchar(100) NOT NULL,
  `Num` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `meals` (
  `Meal_ID` varchar(100) NOT NULL,
  `Meal_Classify` varchar(1000) NOT NULL,
  `Meal_Name` varchar(1000) NOT NULL,
  `Meal_Price` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `movie` (
  `Movie_ID` varchar(100) NOT NULL,
  `Movie_Series` varchar(1000) NOT NULL,
  `Movie_Genre` varchar(1000) NOT NULL,
  `Movie_Name` varchar(1000) NOT NULL,
  `Movie_Showtime` varchar(1000) NOT NULL,
  `Movie_Duration` varchar(1000) NOT NULL,
  `Movie_Actor` varchar(1000) NOT NULL,
  `Movie_Director` varchar(1000) NOT NULL,
  `Movie_Introduction` varchar(1000) NOT NULL,
  `Movie_EngName` varchar(1000) NOT NULL,
  `Pic_URL` varchar(1000) NOT NULL,
  `Trailer_URL` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `movie_ticket_type` (
  `Movie_ID` varchar(100) NOT NULL,
  `Type_ID` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `screenings` (
  `Screenings_ID` varchar(100) NOT NULL,
  `Movie_ID` varchar(100) NOT NULL,
  `Auditorium_ID` varchar(100) NOT NULL,
  `Screenings_Time` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `theater` (
  `Theater_ID` varchar(100) NOT NULL,
  `Theater_Name` varchar(1000) NOT NULL,
  `Theater_EngName` varchar(1000) NOT NULL,
  `Theater_Intro` varchar(1000) NOT NULL,
  `Theater_Address` varchar(1000) NOT NULL,
  `Theater_PhoneNum` varchar(1000) NOT NULL,
  `Theater_Transport` varchar(1000) NOT NULL,
  `Theater_Accessibility` varchar(1000) NOT NULL,
  `Theater_Seat` varchar(1000) NOT NULL,
  `Pic_URL` varchar(1000) NOT NULL,
  `Map_URL` varchar(1000) NOT NULL,
  `SeatPic_URL` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `theater_meals` (
  `Theater_ID` varchar(100) NOT NULL,
  `Meal_ID` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ticket_type` (
  `Type_ID` varchar(100) NOT NULL,
  `Type_Classify` varchar(1000) NOT NULL,
  `Type_Name` varchar(1000) NOT NULL,
  `Type_Price` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (
  `User_Email` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `User_IC` varchar(100) NOT NULL,
  `User_ID` varchar(100) NOT NULL,
  `User_Name` varchar(100) NOT NULL,
  `User_PhoneNum` varchar(100) NOT NULL,
  `User_Birthday` varchar(100) NOT NULL,
  `User_Gender` varchar(100) NOT NULL,
  `User_Money` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `auditorium`
  ADD PRIMARY KEY (`Auditorium_ID`),
  ADD KEY `Theater_ID` (`Theater_ID`);

ALTER TABLE `booking_meals`
  ADD PRIMARY KEY (`Ticket_ID`,`Meal_ID`),
  ADD KEY `Meal_ID` (`Meal_ID`);

ALTER TABLE `booking_record`
  ADD PRIMARY KEY (`Ticket_ID`),
  ADD KEY `booking_record_ibfk_1` (`User_Email`),
  ADD KEY `booking_record_ibfk_2` (`Screenings_ID`);

ALTER TABLE `booking_seat`
  ADD PRIMARY KEY (`Ticket_ID`,`Seat`),
  ADD KEY `Seat_ID` (`Seat`);

ALTER TABLE `booking_ticket_type`
  ADD PRIMARY KEY (`Ticket_ID`,`Type_ID`),
  ADD KEY `Type_ID` (`Type_ID`);

ALTER TABLE `meals`
  ADD PRIMARY KEY (`Meal_ID`);

ALTER TABLE `movie`
  ADD PRIMARY KEY (`Movie_ID`);

ALTER TABLE `movie_ticket_type`
  ADD PRIMARY KEY (`Movie_ID`,`Type_ID`),
  ADD KEY `Type_ID` (`Type_ID`);

ALTER TABLE `screenings`
  ADD PRIMARY KEY (`Screenings_ID`),
  ADD KEY `screenings_ibfk_2` (`Auditorium_ID`),
  ADD KEY `screenings_ibfk_1` (`Movie_ID`);

ALTER TABLE `theater`
  ADD PRIMARY KEY (`Theater_ID`);

ALTER TABLE `theater_meals`
  ADD PRIMARY KEY (`Theater_ID`,`Meal_ID`),
  ADD KEY `Meal_ID` (`Meal_ID`);

ALTER TABLE `ticket_type`
  ADD PRIMARY KEY (`Type_ID`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`User_Email`);

ALTER TABLE `auditorium`
  ADD CONSTRAINT `auditorium_ibfk_1` FOREIGN KEY (`Theater_ID`) REFERENCES `theater` (`Theater_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `booking_meals`
  ADD CONSTRAINT `booking_meals_ibfk_2` FOREIGN KEY (`Ticket_ID`) REFERENCES `booking_record` (`Ticket_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_meals_ibfk_3` FOREIGN KEY (`Meal_ID`) REFERENCES `meals` (`Meal_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `booking_record`
  ADD CONSTRAINT `booking_record_ibfk_1` FOREIGN KEY (`User_Email`) REFERENCES `user` (`User_Email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_record_ibfk_2` FOREIGN KEY (`Screenings_ID`) REFERENCES `screenings` (`Screenings_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `booking_seat`
  ADD CONSTRAINT `booking_seat_ibfk_1` FOREIGN KEY (`Ticket_ID`) REFERENCES `booking_record` (`Ticket_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `booking_ticket_type`
  ADD CONSTRAINT `booking_ticket_type_ibfk_1` FOREIGN KEY (`Ticket_ID`) REFERENCES `booking_record` (`Ticket_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `booking_ticket_type_ibfk_2` FOREIGN KEY (`Type_ID`) REFERENCES `ticket_type` (`Type_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `movie_ticket_type`
  ADD CONSTRAINT `movie_ticket_type_ibfk_1` FOREIGN KEY (`Movie_ID`) REFERENCES `movie` (`Movie_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `movie_ticket_type_ibfk_2` FOREIGN KEY (`Type_ID`) REFERENCES `ticket_type` (`Type_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `screenings`
  ADD CONSTRAINT `screenings_ibfk_2` FOREIGN KEY (`Auditorium_ID`) REFERENCES `auditorium` (`Auditorium_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `screenings_ibfk_3` FOREIGN KEY (`Movie_ID`) REFERENCES `movie` (`Movie_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `theater_meals`
  ADD CONSTRAINT `theater_meals_ibfk_1` FOREIGN KEY (`Meal_ID`) REFERENCES `meals` (`Meal_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `theater_meals_ibfk_2` FOREIGN KEY (`Theater_ID`) REFERENCES `theater` (`Theater_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

SET FOREIGN_KEY_CHECKS = 1;
