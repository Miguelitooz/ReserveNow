-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Creación del esquema
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `restaurants_booking`;
CREATE SCHEMA IF NOT EXISTS `restaurants_booking` DEFAULT CHARACTER SET utf8mb4;
USE `restaurants_booking`;

-- -----------------------------------------------------
-- Tabla de Usuarios
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` CHAR(60) NOT NULL,
  `email` VARCHAR(90) NOT NULL,
  `rol_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),  -- Eliminado `Roles_rol_id` de la clave primaria
  CONSTRAINT `fk_users_roles`
    FOREIGN KEY (`rol_id`)  -- Cambiado `Roles_rol_id` a `rol_id`
    REFERENCES `restaurants_booking`.`roles` (`rol_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Tabla de Restaurantes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurants` (
  `restaurant_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL UNIQUE,
  `address` VARCHAR(255) NOT NULL,
  `menu_details` TEXT,
  `price_range` VARCHAR(50),
  PRIMARY KEY (`restaurant_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Tabla de Mesas
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tables` (
  `table_id` INT NOT NULL AUTO_INCREMENT,
  `table_number` INT NOT NULL,
  `available` BOOLEAN NOT NULL,
  `restaurant_id` INT NOT NULL,
  `restaurant_name` VARCHAR(100) NOT NULL, 
  PRIMARY KEY (`table_id`),
  CONSTRAINT `fk_tables_restaurants` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- -----------------------------------------------------
-- Tabla de Roles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `roles` (
  `rol_id` INT NOT NULL AUTO_INCREMENT,
  `rol_name` VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY (`rol_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Tabla de Reservas
CREATE TABLE IF NOT EXISTS `reservations` (
  `reservation_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `restaurant_id` INT NOT NULL,
  `table_id` INT NOT NULL,
  `reservation_date` DATETIME NOT NULL,
  `num_guests` INT NOT NULL,
  `food_allergies` TEXT,
  `status` ENUM('pendiente', 'confirmada', 'cancelada') DEFAULT 'pendiente',
  `username` VARCHAR(50) NOT NULL,
  `restaurant_name` VARCHAR(100) NOT NULL, 
  `table_number` INT NOT NULL, 
  
  PRIMARY KEY (`reservation_id`),
  CONSTRAINT `fk_reservations_users`
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservations_restaurants`
    FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`restaurant_id`)
    ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservations_tables`
    FOREIGN KEY (`table_id`) REFERENCES `tables` (`table_id`)
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Tabla de Configuración de Usuario
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_settings` (
  `user_id` INT NOT NULL,
  `theme_preference` VARCHAR(50),
  `notifications_enabled` BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_settings_users`
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
