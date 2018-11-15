SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema scheduling
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `scheduling` ;

-- -----------------------------------------------------
-- Schema scheduling
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scheduling` DEFAULT CHARACTER SET utf8 ;
USE `scheduling` ;

-- -----------------------------------------------------
-- Table `scheduling`.`Student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Student` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Student` (
  `userName` VARCHAR(45) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `isActive` TINYINT NULL,
  PRIMARY KEY (`userName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduling`.`Building`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Building` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Building` (
  `ID` VARCHAR(4) NOT NULL,
  `fullName` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `longitude` DECIMAL(10,5) NULL,
  `latitude` DECIMAL(10,5) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduling`.`Course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Course` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Course` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `school` VARCHAR(45) NULL,
  `major` VARCHAR(45) NULL,
  `number` VARCHAR(45) NULL,
  `units` FLOAT NULL,
  `name` VARCHAR(100) NULL,
  `description` TEXT NULL,
  `semester` INT(11) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
COMMENT = '	';


-- -----------------------------------------------------
-- Table `scheduling`.`Lecture_Sections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Lecture_Sections` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Lecture_Sections` (
  `sectionID` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NULL,
  `start_time` VARCHAR(45) NULL,
  `end_time` VARCHAR(45) NULL,
  `day` VARCHAR(45) NULL,
  `instructor` VARCHAR(45) NULL,
  `numRegistered` INT(4) NULL,
  `classCapacity` INT(4) NULL,
  `Building_ID` VARCHAR(4) NULL,
  `Course_ID` INT(11) NOT NULL,
  PRIMARY KEY (`sectionID`),
  INDEX `fk_Section_Building1_idx` (`Building_ID` ASC) VISIBLE,
  INDEX `fk_Section_Course1_idx` (`Course_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Section_Building1`
    FOREIGN KEY (`Building_ID`)
    REFERENCES `scheduling`.`Building` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Section_Course1`
    FOREIGN KEY (`Course_ID`)
    REFERENCES `scheduling`.`Course` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduling`.`Schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Schedule` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Schedule` (
  `studentUserName` VARCHAR(45) NOT NULL,
  `sectionID1` VARCHAR(45) NULL,
  `sectionID2` VARCHAR(45) NULL,
  `sectionID3` VARCHAR(45) NULL,
  `sectionID4` VARCHAR(45) NULL,
  `sectionID5` VARCHAR(45) NULL,
  `sectionID6` VARCHAR(45) NULL,
  `sectionID7` VARCHAR(45) NULL,
  `sectionID8` VARCHAR(45) NULL,
  `sectionID9` VARCHAR(45) NULL,
  `sectionID10` VARCHAR(45) NULL,
  PRIMARY KEY (`studentUserName`),
  INDEX `fk_Schedule_Student1_idx` (`studentUserName` ASC) VISIBLE,
  CONSTRAINT `fk_Schedule_Student1`
    FOREIGN KEY (`studentUserName`)
    REFERENCES `scheduling`.`Student` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduling`.`Lab_Sections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Lab_Sections` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Lab_Sections` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `sectionID` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NULL,
  `start_time` VARCHAR(45) NULL,
  `end_time` VARCHAR(45) NULL,
  `day` VARCHAR(45) NULL,
  `instructor` VARCHAR(45) NULL,
  `numRegistered` INT(4) NULL,
  `classCapacity` INT(4) NULL,
  `Building_ID` VARCHAR(4) NOT NULL,
  `Course_ID` INT(11) NOT NULL,
  `Lecture_SectionID` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Section_Building1_idx` (`Building_ID` ASC) VISIBLE,
  INDEX `fk_Section_Course1_idx` (`Course_ID` ASC) VISIBLE,
  INDEX `fk_Lab_Sections_Lecture_Sections1_idx` (`Lecture_SectionID` ASC) VISIBLE,
  CONSTRAINT `fk_Section_Building10`
    FOREIGN KEY (`Building_ID`)
    REFERENCES `scheduling`.`Building` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Section_Course10`
    FOREIGN KEY (`Course_ID`)
    REFERENCES `scheduling`.`Course` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Lab_Sections_Lecture_Sections1`
    FOREIGN KEY (`Lecture_SectionID`)
    REFERENCES `scheduling`.`Lecture_Sections` (`sectionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduling`.`Discussion_Sections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Discussion_Sections` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Discussion_Sections` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `sectionID` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NULL,
  `start_time` VARCHAR(45) NULL,
  `end_time` VARCHAR(45) NULL,
  `day` VARCHAR(45) NULL,
  `instructor` VARCHAR(45) NULL,
  `numRegistered` INT(4) NULL,
  `classCapacity` INT(4) NULL,
  `Building_ID` VARCHAR(4) NOT NULL,
  `Course_ID` INT(11) NOT NULL,
  `Lecture_SectionID` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Section_Building1_idx` (`Building_ID` ASC) VISIBLE,
  INDEX `fk_Section_Course1_idx` (`Course_ID` ASC) VISIBLE,
  INDEX `fk_Disscussion_Sections_Lecture_Sections1_idx` (`Lecture_SectionID` ASC) VISIBLE,
  CONSTRAINT `fk_Section_Building11`
    FOREIGN KEY (`Building_ID`)
    REFERENCES `scheduling`.`Building` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Section_Course11`
    FOREIGN KEY (`Course_ID`)
    REFERENCES `scheduling`.`Course` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Disscussion_Sections_Lecture_Sections1`
    FOREIGN KEY (`Lecture_SectionID`)
    REFERENCES `scheduling`.`Lecture_Sections` (`sectionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduling`.`Quiz_Sections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Quiz_Sections` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Quiz_Sections` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `sectionID` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NULL,
  `start_time` VARCHAR(45) NULL,
  `end_time` VARCHAR(45) NULL,
  `day` VARCHAR(45) NULL,
  `instructor` VARCHAR(45) NULL,
  `numRegistered` INT(4) NULL,
  `classCapacity` INT(4) NULL,
  `Building_ID` VARCHAR(4) NOT NULL,
  `Course_ID` INT(11) NOT NULL,
  `Lecture_SectionID` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Section_Building1_idx` (`Building_ID` ASC) VISIBLE,
  INDEX `fk_Section_Course1_idx` (`Course_ID` ASC) VISIBLE,
  INDEX `fk_Quiz_Sections_Lecture_Sections1_idx` (`Lecture_SectionID` ASC) VISIBLE,
  CONSTRAINT `fk_Section_Building12`
    FOREIGN KEY (`Building_ID`)
    REFERENCES `scheduling`.`Building` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Section_Course12`
    FOREIGN KEY (`Course_ID`)
    REFERENCES `scheduling`.`Course` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Quiz_Sections_Lecture_Sections1`
    FOREIGN KEY (`Lecture_SectionID`)
    REFERENCES `scheduling`.`Lecture_Sections` (`sectionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Student`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Student` (`userName`, `password`, `firstName`, `lastName`, `isActive`) 
	VALUES ('seansyed', 'root', 'Sean', 'Syed', 1);
INSERT INTO `scheduling`.`Student` (`userName`, `password`, `firstName`, `lastName`, `isActive`) 
	VALUES ('gaoxing', 'root', 'Xing', 'Gao', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Building`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) 
	VALUES ('GFS', 'Grace Ford Salvatori Hall', '999 W 36th St, Los Angeles, CA 90089', -118.2880020142, 34.0213356018);
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) 
	VALUES ('MHP', 'Mudd Hall', '3709 Trousdale Parkway, Los Angeles, CA 90089', -118.2868652344, 34.0188293457);
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) 
	VALUES ('VKC', 'Von KleinSmid Center', '3518 Trousdale Parkway, Los Angeles, CA 90089', -118.2839584351, 34.0212898254);
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) 
	VALUES ('NA', '', '', NULL, NULL);
INSERT INTO 'scheduling'.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) 
	VALUES ('SAL','Computer Center at Salvatori Computer Scie...','',-118.28956,34.01970);
INSERT INTO 'scheduling'.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) 
	VALUES ('VHE','Vivian Hall','',-118.28819,34.02016);
INSERT INTO 'scheduling'.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) 
	VALUES ('SGM','Seeley G. Mudd Building','',-118.28918,34.02110);
INSERT INTO 'scheduling'.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`)
	VALUES ('SLH','Stauffer Science Lecture Hall','',-118.28748,34.01968);
COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Course`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
-- CSCI Courses
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1001, 'Viterbi', 'CSCI', '103L', 4.0, 'Introduction to Programming', 'Basic datatypes, assignments, control statements (if, switch, for, while), input/output (printf, scanf, cin, cout), functions, arrays, structures, recursion, dynamic memory, file handling. Programming in C/C++.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1002, 'Viterbi', 'CSCI', '104L', 4.0, 'Data Structures and Object Oriented Design', 'Introduces the student to standard data structures (linear structures such as linked lists, (balanced) trees, priority queues, and hashtables), using the C++ programming language.Prerequisite: CSCI 103. Corequisite: CSCI 170.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1003, 'Viterbi', 'CSCI', '109', 2.0, 'Introduction to Computer Science', 'An introduction to, and overview of, Computer Science; both as a discipline and a body of knowledge.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1004, 'Viterbi', 'CSCI', '170', 4.0, 'Discrete Methods in Computer Science', 'Sets, functions, series. Big-O notation and algorithm analysis. Propositional and first-order logic. Counting and discrete probability. Graphs and basic graph algorithms. Basic number theory.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1005, 'Viterbi', 'CSCI', '201', 4.0, 'Principles of Software Development', 'Object-oriented paradigm for programming-in-the-large in Java; writing sophisticated concurrent applications with animation and graphic user interfaces; using professional tools on team project. Prerequisite: CSCI 104L.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1006, 'Viterbi', 'CSCI', '270', 4.0, 'Introduction to Algorithms and Theory of Computing', 'Algorithm analysis. Greedy algorithms, divide and conquer, dynamic programming, graph algorithms. NP-completeness and basic recursion theory and undecidability. Sorting lower bounds. Number-theory based cryptography.', 1);

-- EE Courses
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1101, 'Viterbi', 'EE', '109L', 4.0, 'Introduction to Embedded Systems', 'Information representations, embedded programming, digital and serial I/O, analog-to-digital conversion, and interrupt mechanisms. Elementary analog, logic, and state-machine design.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1102, 'Viterbi', 'EE', '202L', 4.0, 'Linear Circuts', 'Lumped circuit elements; network equations; zero-input and zero-state responses; sinusoidal steady-state analysis; impedance;resonance; network functions; power concepts; transformers; Laplace transforms.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1103, 'Viterbi', 'EE', '250L', 4.0, 'Distributed Systems for the Internet of Things', 'Introduction to hardware, operating systems, signal processing and control, network protocols, mobile applications, databases, cloud computing, machine learning, and security for the Internet of Things.', 1);
	
-- WRIT Courses
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (2001, 'Dornsife', 'WRIT', '120', 4.0, 'Introduction to College Writing', 'Intensive instruction and practice in the writing process. Focuses upon the formal conventions and conceptual expectations of college writing, with emphasis upon the grammatical, stylistic, and rhetorical techniques required in successful writing. Graded CR/NC. Limited to and required of students who score below specified level on the USC Writing Examination.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (2002, 'Dornsife', 'WRIT', '150', 4.0, 'Writing and Critical Reasoning', 'Academic writing, emphasizing analysis and argumentation, rhetorical judgment, critical reasoning, creative insight, the careful use of evidence, ethical perspectives, logical organization, stylistic and grammatical fluency. Duplicates credit in WRIT 130 and WRIT 140.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (2003, 'Dornsife', 'WRIT', '340', 4.0, 'Advanced Writing', 'Instruction in writing for various audiences on topics related to a student\'s professional or disciplinary interests, with some emphasis on issues of broad public concern. Prerequisite: WRIT 130 or WRIT 150.', 1);
	
COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Lecture_Sections`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;

--INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
--	VALUES ('', 'Lecture', '', '', '', '', 0, , '', );	

------------------- CSCI -------------------------------
-- 103L	FILLER
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('NA5', 'Lecture', '', '', '', '', 0, 80, 'NA', 1001);	

-- 104L
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('3000', 'Lecture', '8:00', '8:50', 'MWF', 'Mark Redekopp', 0, 100, 'GFS', 1002);	 		--ROOM
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('3001', 'Lecture', '12:00', '12:50', 'MWF', 'Andrew Goodney', 0, 100, 'GFS', 1002);		--ROOM
	
-- 109	FILLER
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('', 'Lecture', '', '', '', '', 0, , 'NA', 1003);	

-- 170
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('3005', 'Lecture', '12:00', '13:20', 'MW', 'Michael Shindler', 0, 100, 'MHP', 1004);		--ROOM
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('3006', 'Lecture', '14:00', '15:20', 'TH', 'Michael Shindler', 0, 100, 'MHP', 1004);		--ROOM
	
-- 201	FILLER
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('NA6', 'Lecture', '', '', '', '', 0, , 'NA', 1005);	

-- 270	FILLER
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('NA7', 'Lecture', '', '', '', '', 0, , 'NA', 1006);	

------------------- EE ---------------------------------
-- 109
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('3100', 'Lecture', '12:00', '12:50', 'TH', '', 0, 45, 'VHE', 1101);					--ROOM

-- 202	FILLER
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('NA3', 'Lecture', '', '', '', '', 0, , 'NA', 1102);	

-- 250	FILLER
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('NA4', 'Lecture', '', '', '', '', 0, , 'NA', 1103);	


------------------- WRIT -------------------------------
-- 120	FILLER
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('NA0', 'Lecture', '', '', '', '', 0, , 'NA', 2001);	

-- 150
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('6000', 'Lecture', '10:00', '11:20', 'TH', 'Jessi Johnson', 0, 15, 'VKC', 2002);		

-- 340 	FILLER
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('NA1', 'Lecture', '', '', '', '', 0, , 'NA', 2003);


COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Discussion_Sections`
-- -----------------------------------------------------


START TRANSACTION;
USE `scheduling`;

--INSERT INTO `scheduling`.`Discussion_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
--	VALUES ('', '', '', '', '', NULL, , , '', , '');


------------------- 170 --------------------------------
INSERT INTO `scheduling`.`Discussion_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3030', 'Discussion', '13:00', '15:50', 'F', NULL, 0, 100, 'SLH', 1004, '3005');		
INSERT INTO `scheduling`.`Discussion_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3030', 'Discussion', '13:00', '15:50', 'F', NULL, 0, 100, 'SLH', 1004, '3006');		

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Lab_Sections`
-- -----------------------------------------------------


START TRANSACTION;
USE `scheduling`;

--INSERT INTO `scheduling`.`Lab_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
--	VALUES ('', '', '', '', '', NULL, , , '', , '');

------------------- CSCI --------------------------------
-- 104
INSERT INTO `scheduling`.`Lab_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3010', 'Lab', '15:00', '16:50', 'T', '', 0, 100, 'SAL', 1002, '3000');				
INSERT INTO `scheduling`.`Lab_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3010', 'Lab', '15:00', '16:50', 'T', '', 0, 100, 'SAL', 1002, '3001');				
	
INSERT INTO `scheduling`.`Lab_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3011', 'Lab', '15:00', '16:50', 'T', '', 0, 100, 'SAL', 1002, '3000');				
INSERT INTO `scheduling`.`Lab_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3011', 'Lab', '15:00', '16:50', 'T', '', 0, 100, 'SAL', 1002, '3001');				

	
------------------- EE ----------------------------------
-- 109 
INSERT INTO `scheduling`.`Lab_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3110', 'Lab', '14:00', '15:50', 'W', '', 0, 100, 'VHE', 1101, '3100');


COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Quiz_Sections`
-- -----------------------------------------------------

START TRANSACTION;
USE `scheduling`;

--INSERT INTO `scheduling`.`Quiz_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
--	VALUES ('', '', '', '', '', NULL, , , '', , '');

------------------- CSCI --------------------------------
-- 170
INSERT INTO `scheduling`.`Quiz_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3020', 'Quiz', '19:00', '20:50', 'H', '', 0, 100, 'SGM', 1004, '3005');
INSERT INTO `scheduling`.`Quiz_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3020', 'Quiz', '19:00', '20:50', 'H', '', 0, 100, 'SGM', 1004, '3006');

	
------------------- EE ----------------------------------
-- 109 
INSERT INTO `scheduling`.`Quiz_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) 
	VALUES ('3120', 'Quiz', '19:00', '20:50', 'W', '', 0, 100, 'SGM', 1101, '3100');				


COMMIT;




-- -----------------------------------------------------
-- Data for table `scheduling`.`Schedule`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Schedule` (`studentUserName`, `sectionID1`, `sectionID2`, `sectionID3`, `sectionID4`, `sectionID5`, `sectionID6`, `sectionID7`, `sectionID8`, `sectionID9`, `sectionID10`) 
	VALUES ('seansyed', '3000', '3011', '3005', '3020', '3030', NULL, NULL, NULL, NULL, NULL);

COMMIT;

