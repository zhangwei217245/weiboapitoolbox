SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `weibotoolbox` ;
CREATE SCHEMA IF NOT EXISTS `weibotoolbox` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `weibotoolbox` ;

-- -----------------------------------------------------
-- Table `loopconfig`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `loopconfig` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `loopconfig` (
  `numconfigid` INT NOT NULL AUTO_INCREMENT ,
  `filename` VARCHAR(100) NOT NULL ,
  `datupdated` TIMESTAMP NOT NULL ,
  `numispublic` INT NOT NULL DEFAULT 0 ,
  `numownerid` INT NULL ,
  PRIMARY KEY (`numconfigid`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `idx_dateupdated` ON `loopconfig` (`datupdated` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_ownerid` ON `loopconfig` (`numownerid` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `tgroup`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tgroup` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `tgroup` (
  `numgroupid` INT NOT NULL AUTO_INCREMENT ,
  `vc2groupname` VARCHAR(50) NULL ,
  PRIMARY KEY (`numgroupid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `tuser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tuser` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `tuser` (
  `numuserid` INT NOT NULL AUTO_INCREMENT ,
  `vc2nickname` VARCHAR(50) NULL ,
  `vc2email` VARCHAR(200) NOT NULL ,
  `vc2password` VARCHAR(50) NOT NULL ,
  `is_active` INT NOT NULL DEFAULT 1 ,
  `is_super` INT NOT NULL DEFAULT 0 ,
  `datlastlogin` DATE NULL ,
  `datjoined` DATE NULL ,
  `numgroupid` INT NOT NULL ,
  PRIMARY KEY (`numuserid`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `vc2email_UNIQUE` ON `tuser` (`vc2email` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `tright`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tright` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `tright` (
  `numrightid` INT NOT NULL AUTO_INCREMENT ,
  `vc2rightname` VARCHAR(50) NOT NULL ,
  `vc2path` VARCHAR(300) NOT NULL ,
  `numgroupid` INT NOT NULL ,
  PRIMARY KEY (`numrightid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `tmenu`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tmenu` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `tmenu` (
  `nummenuid` INT NOT NULL AUTO_INCREMENT ,
  `vc2label` VARCHAR(50) NOT NULL ,
  `vc2imgurl` VARCHAR(200) NULL ,
  `vc2href` VARCHAR(200) NULL ,
  `numpmenuid` INT NULL ,
  `numgroupid` INT NOT NULL ,
  PRIMARY KEY (`nummenuid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `tbuttons`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbuttons` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `tbuttons` (
  `numbuttonid` INT NOT NULL AUTO_INCREMENT ,
  `vc2label` VARCHAR(50) NULL ,
  `vc2imgurl` VARCHAR(200) NULL ,
  `vc2href` VARCHAR(200) NULL ,
  `numgroupid` INT NOT NULL ,
  PRIMARY KEY (`numbuttonid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `apiversion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `apiversion` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `apiversion` (
  `numversionid` INT NOT NULL AUTO_INCREMENT ,
  `vc2value` VARCHAR(20) NOT NULL ,
  `numstatus` INT NOT NULL ,
  `datcreatedate` TIMESTAMP NOT NULL ,
  `numcreateuid` INT NOT NULL ,
  `datlastmodify` TIMESTAMP NULL ,
  `nummodifyuid` INT NULL ,
  PRIMARY KEY (`numversionid`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `idx_status` ON `apiversion` (`numstatus` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_value` ON `apiversion` (`vc2value` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `apiclass`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `apiclass` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `apiclass` (
  `numclassid` INT NOT NULL AUTO_INCREMENT ,
  `vc2identity` VARCHAR(50) NOT NULL ,
  `numindex` INT NOT NULL ,
  `vc2desc` VARCHAR(500) NOT NULL ,
  `numstatus` INT NOT NULL ,
  `numversionid` INT NULL ,
  `datcreate` TIMESTAMP NOT NULL ,
  `numcreateuid` INT NOT NULL ,
  `datlastmodify` TIMESTAMP NULL ,
  `nummodifyuid` INT NULL ,
  PRIMARY KEY (`numclassid`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `vc2identity_UNIQUE` ON `apiclass` (`vc2identity` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_status` ON `apiclass` (`numstatus` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_index` ON `apiclass` (`numindex` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_identity` ON `apiclass` (`vc2identity` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `apicatagory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `apicatagory` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `apicatagory` (
  `numcataid` INT NOT NULL AUTO_INCREMENT ,
  `vc2identity` VARCHAR(50) NOT NULL ,
  `numindex` INT NOT NULL ,
  `vc2desc` VARCHAR(500) NOT NULL ,
  `numstatus` INT NOT NULL ,
  `numclassid` INT NULL ,
  `datcreate` TIMESTAMP NOT NULL ,
  `numcreateuid` INT NOT NULL ,
  `datlastmodify` TIMESTAMP NULL ,
  `nummodifyuid` INT NULL ,
  PRIMARY KEY (`numcataid`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `vc2identity_UNIQUE` ON `apicatagory` (`vc2identity` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_status` ON `apicatagory` (`numstatus` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `apidef`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `apidef` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `apidef` (
  `numapiid` INT NOT NULL AUTO_INCREMENT ,
  `vc2identity` VARCHAR(50) NOT NULL ,
  `numindex` INT NOT NULL ,
  `vc2brief` VARCHAR(100) NOT NULL ,
  `vc2url` VARCHAR(100) NOT NULL ,
  `vc2desc` VARCHAR(1000) NULL ,
  `numisneedauth` INT NOT NULL ,
  `numisratelimited` INT NOT NULL ,
  `vc2enctype` VARCHAR(100) NOT NULL ,
  `numstatus` INT NOT NULL ,
  `numcataid` INT NULL ,
  `datcreate` TIMESTAMP NOT NULL ,
  `numcreateuid` INT NOT NULL ,
  `datelastmodify` TIMESTAMP NULL ,
  `nummodifyuid` INT NULL ,
  PRIMARY KEY (`numapiid`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `idx_status` ON `apidef` (`numstatus` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `apiparams`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `apiparams` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `apiparams` (
  `numparamid` INT NOT NULL AUTO_INCREMENT ,
  `vc2paramname` VARCHAR(50) NOT NULL ,
  `numrequired` INT NOT NULL ,
  `numisrest` INT NOT NULL ,
  `vc2paramdesc` VARCHAR(1000) NOT NULL ,
  `numapiid` INT NOT NULL ,
  PRIMARY KEY (`numparamid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `apimethodref`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `apimethodref` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `apimethodref` (
  `nummethodrefid` INT NOT NULL AUTO_INCREMENT ,
  `vc2method` VARCHAR(20) NOT NULL ,
  `nummethodcode` INT NOT NULL ,
  `numapiid` INT NOT NULL ,
  PRIMARY KEY (`nummethodrefid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `apiformatsref`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `apiformatsref` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `apiformatsref` (
  `numformatrefid` INT NOT NULL AUTO_INCREMENT ,
  `vc2format` VARCHAR(20) NOT NULL ,
  `numformatcode` INT NOT NULL ,
  `numapiid` INT NOT NULL ,
  PRIMARY KEY (`numformatrefid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `loopcases`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `loopcases` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `loopcases` (
  `numloopcaseid` INT NOT NULL AUTO_INCREMENT ,
  `vc2format` VARCHAR(20) NOT NULL ,
  `vc2priority` VARCHAR(10) NOT NULL ,
  `vc2desc` VARCHAR(400) NULL ,
  `numconfigid` INT NULL ,
  PRIMARY KEY (`numloopcaseid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `loopaccount`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `loopaccount` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `loopaccount` (
  `numaccountid` INT NOT NULL ,
  `vc2label` VARCHAR(50) NULL ,
  `vc2accountuid` VARCHAR(50) NULL ,
  `vc2screen_name` VARCHAR(100) NULL ,
  `vc2loginname` VARCHAR(200) NULL ,
  `vc2password` VARCHAR(100) NULL ,
  `vc2oauthtoken` VARCHAR(50) NULL ,
  `vc2oauthsecret` VARCHAR(50) NULL ,
  PRIMARY KEY (`numaccountid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `looprequest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `looprequest` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `looprequest` (
  `numrequestid` INT NOT NULL ,
  `vc2classname` VARCHAR(200) NULL DEFAULT 'com.sina.weibo.looptest.request.CommonLoopRequest' ,
  `numdelayedMills` INT NULL DEFAULT 3000 ,
  `vc2requestdesc` VARCHAR(200) NULL ,
  `vc2url` VARCHAR(50) NULL ,
  `vc2method` VARCHAR(10) NULL ,
  `vc2authtype` VARCHAR(10) NULL ,
  `numaccountid` INT NULL ,
  `vc2enctype` VARCHAR(100) NULL ,
  `numloopcaseid` INT NOT NULL ,
  PRIMARY KEY (`numrequestid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `reqparam`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reqparam` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `reqparam` (
  `numrequestid` INT NOT NULL ,
  `vc2paramname` VARCHAR(100) NOT NULL ,
  `vc2paramvalue` VARCHAR(100) NOT NULL ,
  `numreqparamid` BIGINT NOT NULL ,
  PRIMARY KEY (`numreqparamid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `reqvaluekept`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reqvaluekept` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `reqvaluekept` (
  `numrequestid` INT NOT NULL ,
  `vc2sessionkey` VARCHAR(50) NOT NULL ,
  `vc2valuepath` VARCHAR(50) NOT NULL ,
  `numreqvaluekeptid` BIGINT NOT NULL ,
  PRIMARY KEY (`numreqvaluekeptid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `reqassert`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reqassert` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `reqassert` (
  `numrequestid` INT NOT NULL ,
  `vc2path` VARCHAR(100) NOT NULL ,
  `vc2pathtype` VARCHAR(1) NOT NULL ,
  `vc2relation` VARCHAR(10) NOT NULL ,
  `vc2expvalueexp` VARCHAR(100) NOT NULL ,
  `vc2errmsg` VARCHAR(100) NOT NULL ,
  `numreqassertid` BIGINT NOT NULL ,
  PRIMARY KEY (`numreqassertid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `globalparam`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `globalparam` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `globalparam` (
  `vc2paramname` VARCHAR(100) NOT NULL ,
  `vc2paramvalue` VARCHAR(500) NOT NULL ,
  `numconfigid` INT NOT NULL ,
  `numgloblalparamid` BIGINT NOT NULL ,
  PRIMARY KEY (`numgloblalparamid`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `sqllog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sqllog` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `sqllog` (
  `vc2sqlquery` VARCHAR(5000) NOT NULL ,
  `datcreate` TIMESTAMP NOT NULL ,
  `numuserid` INT NOT NULL )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `idx_createdate` ON `sqllog` (`datcreate` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `loopexcutelog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `loopexcutelog` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `loopexcutelog` (
  `datexcute` TIMESTAMP NOT NULL ,
  `numrequestid` INT NOT NULL ,
  `vc2testresult` VARCHAR(100) NOT NULL ,
  `vc2testuri` VARCHAR(200) NOT NULL ,
  `numduration` INT NOT NULL ,
  `numpredelayed` INT NOT NULL ,
  `numretrycount` INT NOT NULL ,
  `numstatuscode` INT NOT NULL ,
  `blobrequest` BLOB NULL ,
  `blobresponse` BLOB NULL ,
  `numexecutelogid` BIGINT NOT NULL ,
  PRIMARY KEY (`numexecutelogid`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `idx_excutedate` ON `loopexcutelog` (`datexcute` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_statuscode` ON `loopexcutelog` (`numstatuscode` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_result` ON `loopexcutelog` (`vc2testresult` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_duration` ON `loopexcutelog` (`numduration` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_uri` ON `loopexcutelog` (`vc2testuri` ASC) ;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
