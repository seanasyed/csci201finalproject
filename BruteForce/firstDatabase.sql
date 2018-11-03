
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Student` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Student` (
  `userName` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `isActive` TINYINT NULL,
  PRIMARY KEY (`userName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Schedule` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Schedule` (
  `ID` INT(11) NOT NULL,
  `sectionID` VARCHAR(45) NOT NULL,
  `studentUserName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Schedule_Student_idx` (`studentUserName` ASC) VISIBLE,
  CONSTRAINT `fk_Schedule_Student`
    FOREIGN KEY (`studentUserName`)
    REFERENCES `mydb`.`Student` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Building`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Building` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Building` (
  `ID` VARCHAR(4) NOT NULL,
  `fullName` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Course` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Course` (
  `ID` INT(11) NOT NULL,
  `school` VARCHAR(45) NULL,
  `major` VARCHAR(45) NULL,
  `number` VARCHAR(45) NULL,
  `units` FLOAT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Section`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Section` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Section` (
  `sectionID` VARCHAR(45) NOT NULL,
  `session` VARCHAR(45) NULL,
  `type` VARCHAR(45) NULL,
  `time` VARCHAR(45) NULL,
  `day` VARCHAR(45) NULL,
  `instructor` VARCHAR(45) NULL,
  `Building_ID` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`sectionID`),
  INDEX `fk_Section_Building1_idx` (`Building_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Section_Building1`
    FOREIGN KEY (`Building_ID`)
    REFERENCES `mydb`.`Building` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`SectionDependencies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`SectionDependencies` ;

CREATE TABLE IF NOT EXISTS `mydb`.`SectionDependencies` (
  `sectionID` VARCHAR(45) NOT NULL,
  `linkedLab` VARCHAR(45) NULL,
  `linkedDisscussion` VARCHAR(45) NULL,
  `linkedQuiz` VARCHAR(45) NULL,
  PRIMARY KEY (`sectionID`),
  INDEX `fk_SectionDependencies_Section1_idx` (`sectionID` ASC) VISIBLE,
  CONSTRAINT `fk_SectionDependencies_Section1`
    FOREIGN KEY (`sectionID`)
    REFERENCES `mydb`.`Section` (`sectionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CourseInstance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`CourseInstance` ;

CREATE TABLE IF NOT EXISTS `mydb`.`CourseInstance` (
  `semester` INT(11) NOT NULL,
  `isActive` TINYINT NULL,
  `Course_ID` INT(11) NOT NULL,
  `lectureSection` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`semester`),
  INDEX `fk_CourseInstance_Course1_idx` (`Course_ID` ASC) VISIBLE,
  INDEX `fk_CourseInstance_SectionDependencies1_idx` (`lectureSection` ASC) VISIBLE,
  CONSTRAINT `fk_CourseInstance_Course1`
    FOREIGN KEY (`Course_ID`)
    REFERENCES `mydb`.`Course` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CourseInstance_SectionDependencies1`
    FOREIGN KEY (`lectureSection`)
    REFERENCES `mydb`.`SectionDependencies` (`sectionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
