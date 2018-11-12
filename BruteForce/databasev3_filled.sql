
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

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
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`ID`),
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
  PRIMARY KEY (`sectionID`),
  INDEX `fk_Section_Building2_idx` (`Building_ID` ASC) VISIBLE,
  INDEX `fk_Section_Course10_idx` (`Course_ID` ASC) VISIBLE,
  INDEX `fk_Lab_Sections_Lecture_Sections1_idx` (`Lecture_SectionID` ASC) VISIBLE,
  CONSTRAINT `fk_Section_Building2`
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
  PRIMARY KEY (`sectionID`),
  INDEX `fk_Section_Building3_idx` (`Building_ID` ASC) VISIBLE,
  INDEX `fk_Section_Course11_idx` (`Course_ID` ASC) VISIBLE,
  INDEX `fk_Disscussion_Sections_Lecture_Sections1_idx` (`Lecture_SectionID` ASC) VISIBLE,
  CONSTRAINT `fk_Section_Building3`
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
  PRIMARY KEY (`sectionID`),
  INDEX `fk_Section_Building4_idx` (`Building_ID` ASC) VISIBLE,
  INDEX `fk_Section_Course12_idx` (`Course_ID` ASC) VISIBLE,
  INDEX `fk_Quiz_Sections_Lecture_Sections1_idx` (`Lecture_SectionID` ASC) VISIBLE,
  CONSTRAINT `fk_Section_Building4`
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
INSERT INTO `scheduling`.`Student` (`userName`, `password`, `firstName`, `lastName`, `isActive`) VALUES ('seansyed', 'root', 'Sean', 'Syed', 1);
INSERT INTO `scheduling`.`Student` (`userName`, `password`, `firstName`, `lastName`, `isActive`) VALUES ('gaoxing', 'root', 'Xing', 'Gao', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Building`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) VALUES ('GFS', 'Grace Ford Salvatori Hall', '999 W 36th St, Los Angeles, CA 90089', NULL, NULL);
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) VALUES ('MHP', 'Mudd Hall', '3709 Trousdale Parkway, Los Angeles, CA 90089', NULL, NULL);
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`, `longitude`, `latitude`) VALUES ('VKC', 'Von KleinSmid Center', '3518 Trousdale Parkway, Los Angeles, CA 90089', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Course`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) VALUES (1, 'Viterbi', 'CSCI', '102L', 2.0, 'Fundamentals of Computation', 'Fundamental concepts of algorithmic thinking as a primer to programming. Introduction to C++.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) VALUES (2, 'Viterbi', 'CSCI', '103L', 4.0, 'Introduction to Programming', 'Basic datatypes, assignments, control statements (if, switch, for, while), input/output (printf, scanf, cin, cout), functions, arrays, structures, recursion, dynamic memory, file handling. Programming in C/C++.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) VALUES (3, 'Viterbi', 'CSCI', '170', 4.0, 'Discrete Methods in Computer Science', 'Sets, functions, series. Big-O notation and algorithm analysis. Propositional and first-order logic. Counting and discrete probability. Graphs and basic graph algorithms. Basic number theory.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) VALUES (4, 'Viterbi', 'ENGR', '102', 2.0, 'Engineering Freshman Academy', 'Introduction to the profession of engineering. Ethical, political and societal consequences of engineering innovations and the impact of engineering on everyday life. Team projects and guest lectures. Open to freshmen only.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) VALUES (5, 'Dornsife', 'WRIT', '150', 4.0, 'Writing and Critical Reasoning--Thematic Approaches', 'Academic writing, emphasizing analysis and argumentation, rhetorical judgment, critical reasoning, creative insight, the careful use of evidence, ethical perspectives, logical organization, stylistic and grammatical fluency. Duplicates credit in WRIT 130 and WRIT 140.', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Lecture_Sections`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) VALUES ('1', 'Lecture', '9:00', '9:50', 'MWF', 'Mark Redekopp', 0, 50, 'GFS', 2);
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) VALUES ('2', 'Lecture', '10:00', '11:20', 'Tues, Thurs', NULL, 0, 50, NULL, 3);
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) VALUES ('3', 'Lecture', '9:00', '10:20', 'Mon, Wed', NULL, 0, 50, NULL, 4);
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) VALUES ('4', 'Lecture', '10:00', '11:50', 'Tues, Thurs', NULL, 0, 50, NULL, 2);
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) VALUES ('5', 'Lecture', '14:00', '16:00', 'Tues, Thurs', NULL, 0, 50, NULL, 3);
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) VALUES ('6', 'Lecture', '10:00', '11:50', 'Friday', NULL, 0, 50, NULL, 5);
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) VALUES ('7', 'Lecture', '13:00', '14:50', 'Mon, Wed', NULL, 0, 50, NULL, 2);
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) VALUES ('8', 'Lecture', '14:00', '15:50', 'Tues, Thurs', NULL, 0, 50, NULL, 3);
INSERT INTO `scheduling`.`Lecture_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) VALUES ('9', 'Lecture', '13:00', '14:50', 'Friday', NULL, 0, 50, NULL, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Schedule`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Schedule` (`ID`, `studentUserName`, `sectionID1`, `sectionID2`, `sectionID3`, `sectionID4`, `sectionID5`, `sectionID6`, `sectionID7`, `sectionID8`, `sectionID9`, `sectionID10`) VALUES (1, 'seansyed', '29908D', '30225R', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Lab_Sections`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Lab_Sections` (`sectionID`, `type`, `start_time`, `end_time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`, `Lecture_SectionID`) VALUES ('30225R', 'Lab', '16:30', '17:20', 'Tuesday', NULL, 0, 15, 'VKC', 1, '1');

COMMIT;

