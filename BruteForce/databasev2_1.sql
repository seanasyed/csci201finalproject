

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
  `longitude` DECIMAL(10,5) NULL,
  `latitude` DECIMAL(10,5) NULL,
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
  `description` TEXT NULL,
  `semester` INT(11) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
COMMENT = '	';


-- -----------------------------------------------------
-- Table `scheduling`.`Section`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `scheduling`.`Section` ;

CREATE TABLE IF NOT EXISTS `scheduling`.`Section` (
  `sectionID` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NULL,
  `time` VARCHAR(45) NULL,
  `day` VARCHAR(45) NULL,
  `start_time` VARCHAR(45) NULL,
  `end_time` VARCHAR(45) NULL,
  `am_pm` VARCHAR(2) NULL,
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