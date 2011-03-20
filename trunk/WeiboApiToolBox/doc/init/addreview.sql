SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE  TABLE IF NOT EXISTS `apitoolbox`.`tdatastructreview` (
  `numreviewid` INT(11) NOT NULL AUTO_INCREMENT ,
  `vc2reviewmsg` VARCHAR(5000) NOT NULL ,
  `numshipped` INT(11) NOT NULL ,
  `numdatastructid` INT(11) NOT NULL ,
  `numreviewerid` INT(11) NOT NULL ,
  `datreviewtime` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`numreviewid`) ,
  INDEX `fk_tdatastructreview_tdatastruct1` (`numdatastructid` ASC) ,
  INDEX `fk_tdatastructreview_tuser1` (`numreviewerid` ASC) ,
  CONSTRAINT `fk_tdatastructreview_tdatastruct1`
    FOREIGN KEY (`numdatastructid` )
    REFERENCES `apitoolbox`.`tdatastruct` (`numdatastructid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tdatastructreview_tuser1`
    FOREIGN KEY (`numreviewerid` )
    REFERENCES `apitoolbox`.`tuser` (`numuserid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

CREATE  TABLE IF NOT EXISTS `apitoolbox`.`tspecreview` (
  `numreviewid` INT(11) NOT NULL AUTO_INCREMENT ,
  `vc2reviewmsg` VARCHAR(5000) NOT NULL ,
  `numshipped` INT(11) NOT NULL ,
  `numspecid` INT(11) NOT NULL ,
  `numreviewerid` INT(11) NOT NULL ,
  `datreviewtime` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`numreviewid`) ,
  INDEX `fk_tspecreview_tspec1` (`numspecid` ASC) ,
  INDEX `fk_tspecreview_tuser1` (`numreviewerid` ASC) ,
  CONSTRAINT `fk_tspecreview_tspec1`
    FOREIGN KEY (`numspecid` )
    REFERENCES `apitoolbox`.`tspec` (`numspecid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tspecreview_tuser1`
    FOREIGN KEY (`numreviewerid` )
    REFERENCES `apitoolbox`.`tuser` (`numuserid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

ALTER TABLE `apitoolbox`.`tdatastruct`
DROP INDEX `enable`
, ADD INDEX `enable` (`vc2desc` ASC) ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
