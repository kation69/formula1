-- MySQL Script generated by MySQL Workbench
-- Mon Dec 20 10:34:18 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema formulaProject
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema formulaProject
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `formulaProject` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `formulaProject` ;

-- -----------------------------------------------------
-- Table `formulaProject`.`circuito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`circuito` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `ciudad` VARCHAR(45) NULL DEFAULT NULL,
  `pais` VARCHAR(45) NULL DEFAULT NULL,
  `trazado` VARCHAR(45) NULL DEFAULT NULL,
  `numVueltas` INT NULL DEFAULT NULL,
  `longitud` VARCHAR(45) NULL DEFAULT NULL,
  `curvasLentas` INT NULL DEFAULT NULL,
  `curvasMedias` INT NULL DEFAULT NULL,
  `curvasRapidas` INT NULL DEFAULT NULL,
  `circuitId` VARCHAR(45) NULL DEFAULT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `formulaProject`.`equipos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`equipos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `logo` VARCHAR(45) NULL DEFAULT NULL,
  `twitter` VARCHAR(45) NULL DEFAULT NULL,
  `fechaCreacion` DATE NULL DEFAULT NULL,
  `usuarioCreador` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `formulaProject`.`coche`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`coche` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `equipo_id` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `codigo` VARCHAR(45) NOT NULL,
  `ersCurvaRapida` FLOAT NOT NULL DEFAULT '0.1',
  `ersCurvaMedia` FLOAT NOT NULL DEFAULT '0.1',
  `ersCurvaLenta` FLOAT NOT NULL DEFAULT '0.1',
  `consumo` FLOAT NOT NULL DEFAULT '0.1',
  `imagen` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Coches_Equipo1_idx` (`equipo_id` ASC) VISIBLE,
  CONSTRAINT `fk_Coches_Equipo1`
    FOREIGN KEY (`equipo_id`)
    REFERENCES `formulaProject`.`equipos` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `formulaProject`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `rol` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `usuario` VARCHAR(45) NULL DEFAULT NULL,
  `verificada` TINYINT NULL DEFAULT '0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `formulaProject`.`miembros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`miembros` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `usuario_id` INT NOT NULL,
  `equipo_id` INT NOT NULL,
  PRIMARY KEY (`id`, `usuario_id`),
  INDEX `fk_miembros_usuarioId` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_miembros_usuarioId`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `formulaProject`.`usuarios` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;



-- -----------------------------------------------------
-- Table `formulaProject`.`noticias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`noticias` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `permaLink` VARCHAR(150) NULL DEFAULT NULL,
  `titulo` VARCHAR(255) NOT NULL,
  `imagen` VARCHAR(45) NULL DEFAULT NULL,
  `texto` VARCHAR(2000) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `formulaProject`.`pilotos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`pilotos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(45) NULL DEFAULT NULL,
  `siglas` VARCHAR(3) NULL DEFAULT NULL,
  `dorsal` INT NULL DEFAULT NULL,
  `foto` VARCHAR(45) NULL DEFAULT NULL,
  `pais` VARCHAR(45) NULL DEFAULT NULL,
  `twitter` VARCHAR(45) NULL DEFAULT NULL,
  `equipo_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Pilotos_Equipo_idx` (`equipo_id` ASC) VISIBLE,
  CONSTRAINT `fk_Pilotos_Equipo`
    FOREIGN KEY (`equipo_id`)
    REFERENCES `formulaProject`.`equipos` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `formulaProject`.`votacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`votacion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `permalink` VARCHAR(150) NOT NULL,
  `titulo` VARCHAR(255) NULL DEFAULT NULL,
  `descripcion` VARCHAR(2000) NULL DEFAULT NULL,
  `limite` DATETIME NULL DEFAULT NULL,
										  
										
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `formulaProject`.`votacionUsuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`votacionUsuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `votacion_id` INT NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `piloto` varchar(300) NOT NULL,
  PRIMARY KEY (`id`, `votacion_id`, `email`,`piloto`),
  INDEX `fk_votacionUsuario_votacion1_idx` (`votacion_id` ASC) VISIBLE,
  CONSTRAINT `fk_votacionUsuario_votacion1`
    FOREIGN KEY (`votacion_id`)
    REFERENCES `formulaProject`.`votacion` (`id`)
	ON DELETE NO ACTION
    ON UPDATE NO ACTION)
														
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `formulaProject`.`votacionPiloto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`votacionPiloto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `votacion_id` INT NOT NULL,
  `apellidos` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `votacion_id`, `apellidos`),
  INDEX `fk_votacionPiloto_votacion1_idx` (`votacion_id` ASC) VISIBLE,
  CONSTRAINT `fk_votacionPiloto_votacion1`
    FOREIGN KEY (`votacion_id`)
    REFERENCES `formulaProject`.`votacion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1						
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
-- -----------------------------------------------------
-- Table `formulaProject`.`pilotos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`pilotos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(45) NULL DEFAULT NULL,
  `siglas` VARCHAR(3) NULL DEFAULT NULL,
  `dorsal` INT NULL DEFAULT NULL,
  `foto` VARCHAR(45) NULL DEFAULT NULL,
  `pais` VARCHAR(45) NULL DEFAULT NULL,
  `twitter` VARCHAR(45) NULL DEFAULT NULL,
  `equipo_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Pilotos_Equipo_idx` (`equipo_id`),
  CONSTRAINT `fk_Pilotos_Equipo` FOREIGN KEY (`equipo_id`) REFERENCES `equipos` (`id`)
  )
												 
ENGINE = InnoDB
AUTO_INCREMENT = 1				  
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `formulaProject`.`miembros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `formulaProject`.`miembros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `equipo_id` int NOT NULL,
								 
  PRIMARY KEY (`id`,`usuario_id`),
  KEY `fk_miembros_usuarioId` (`usuario_id`),
  CONSTRAINT `fk_miembros_usuarioId` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
										
										   
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
