

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema scheduling
-- -----------------------------------------------------

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
  `password` VARCHAR(45) NOT NULL,
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
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduling`.`Course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Course` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Course` (
  `ID` INT(11) NOT NULL,
  `school` VARCHAR(45) NULL,
  `major` VARCHAR(45) NULL,
  `number` VARCHAR(45) NULL,
  `units` FLOAT NULL,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(256) NULL,
  `semester` INT(11) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduling`.`Section`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Section` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Section` (
  `sectionID` VARCHAR(45) NOT NULL,
  `session` VARCHAR(45) NULL,
  `type` VARCHAR(45) NULL,
  `time` VARCHAR(45) NULL,
  `day` VARCHAR(45) NULL,
  `instructor` VARCHAR(45) NULL,
  `numRegistered` INT(4) NULL,
  `classCapacity` INT(4) NULL,
  `Building_ID` VARCHAR(4) NOT NULL,
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
-- Table `scheduling`.`SectionDependencies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`SectionDependencies` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`SectionDependencies` (
  `sectionID` VARCHAR(45) NOT NULL,
  `linkedLab` VARCHAR(45) NULL,
  `linkedDisscussion` VARCHAR(45) NULL,
  `linkedQuiz` VARCHAR(45) NULL,
  PRIMARY KEY (`sectionID`),
  INDEX `fk_SectionDependencies_Section1_idx` (`sectionID` ASC) VISIBLE,
  CONSTRAINT `fk_SectionDependencies_Section1`
    FOREIGN KEY (`sectionID`)
    REFERENCES `scheduling`.`Section` (`sectionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheduling`.`Schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Schedule` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Schedule` (
  `ID` INT(11) NOT NULL,
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
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`) 
	VALUES ('GFS', 'Grace Ford Salvatori Hall', '999 W 36th St, Los Angeles, CA 90089');
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`) 
	VALUES ('MHP', 'Mudd Hall', '3709 Trousdale Parkway, Los Angeles, CA 90089');
INSERT INTO `scheduling`.`Building` (`ID`, `fullName`, `address`) 
	VALUES ('VKC', 'Von KleinSmid Center', '3518 Trousdale Parkway, Los Angeles, CA 90089');

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Course`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (1, 'Viterbi', 'CSCI', '102L', 2.0, 'Fundamentals of Computation', 'Fundamental concepts of algorithmic thinking as a primer to programming. Introduction to C++.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (2, 'Viterbi', 'CSCI', '103L', 4.0, 'Introduction to Programming', 'Basic datatypes, assignments, control statements (if, switch, for, while), input/output (printf, scanf, cin, cout), functions, arrays, structures, recursion, dynamic memory, file handling. Programming in C/C++.', 1);
INSERT INTO `scheduling`.`Course` (`ID`, `school`, `major`, `number`, `units`, `name`, `description`, `semester`) 
	VALUES (3, 'Viterbi', 'CSCI', '170', 4.0, 'Discrete Methods in Computer Science', 'Sets, functions, series. Big-O notation and algorithm analysis. Propositional and first-order logic. Counting and discrete probability. Graphs and basic graph algorithms. Basic number theory.', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Section`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Section` (`sectionID`, `session`, `type`, `time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('29908D', '001', 'Lecture', '9:00-9:50am', 'Mon, Wed', 'Mark Redekopp', 2, 50, 'GFS', 1);
INSERT INTO `scheduling`.`Section` (`sectionID`, `session`, `type`, `time`, `day`, `instructor`, `numRegistered`, `classCapacity`, `Building_ID`, `Course_ID`) 
	VALUES ('30225R', '001', 'Lab', '4:30-5:20pm', 'Tuesday', '', 0, 15, 'VKC', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`SectionDependencies`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`SectionDependencies` (`sectionID`, `linkedLab`, `linkedDisscussion`, `linkedQuiz`) 
	VALUES ('29908D', '30225R', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `scheduling`.`Schedule`
-- -----------------------------------------------------
START TRANSACTION;
USE `scheduling`;
INSERT INTO `scheduling`.`Schedule` (`ID`, `studentUserName`, `sectionID1`, `sectionID2`, `sectionID3`, `sectionID4`, `sectionID5`, `sectionID6`, `sectionID7`, `sectionID8`, `sectionID9`, `sectionID10`) 
	VALUES (1, 'seansyed', '29908D', '30225R', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

COMMIT;

