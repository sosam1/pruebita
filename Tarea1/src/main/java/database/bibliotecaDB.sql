-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3307
-- Tiempo de generación: 22-08-2025 a las 16:00:17
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `biblioteca_comunitaria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

-- Tabla base: Usuario
CREATE TABLE usuario (
  correo VARCHAR(100) NOT NULL PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  pass_word VARCHAR(100) NOT NULL
) ;

-- Tabla hija: Lector
CREATE TABLE lector (
  correo VARCHAR(100) NOT NULL PRIMARY KEY,
  fechaIngreso DATE NOT NULL,
  estadoUsuario ENUM('ACTIVO','SUSPENDIDO') NOT NULL,
  zona ENUM('BIBLIOTECA_CENTRAL','SUCURSAL_ESTE','SUCURSAL_OESTE','BIBLIOTECA_INFANTIL','ARCHIVO_GENERAL') NOT NULL,
  direccion VARCHAR(255) NOT NULL,
  FOREIGN KEY (correo) REFERENCES usuario(correo)
) ;

-- Tabla hija: Bibliotecario
CREATE TABLE bibliotecario (
  correo VARCHAR(100) NOT NULL PRIMARY KEY,
  idEmp INT NOT NULL UNIQUE AUTO_INCREMENT,
  FOREIGN KEY (correo) REFERENCES usuario(correo)
) ;

-- Tabla: Material
CREATE TABLE material (
  idMaterial INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  fechaRegistro DATE NOT NULL
) ;

-- Tabla hija: Libro
CREATE TABLE libro (
  idMaterial INT NOT NULL PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  cantPag INT NOT NULL,
  FOREIGN KEY (idMaterial) REFERENCES material(idMaterial)
) ;

-- Tabla hija: ArticuloEspecial
CREATE TABLE articuloEspecial (
  idMaterial INT NOT NULL PRIMARY KEY,
  descripcion VARCHAR(255) NOT NULL,
  peso FLOAT NOT NULL,
  dimFisica VARCHAR(100),
  FOREIGN KEY (idMaterial) REFERENCES material(idMaterial)
) ;

-- Tabla: Prestamo
CREATE TABLE prestamo (
  idPrestamo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  fechaSoli DATE NOT NULL,
  fechaDev DATE,
  estadoPres ENUM('PENDIENTE','EN_CURSO','DEVUELTO') NOT NULL,
  correoL VARCHAR(100) NOT NULL,
  idMaterial INT NOT NULL,
  correoB VARCHAR(100) NOT NULL,
  FOREIGN KEY (correoL) REFERENCES lector(correo),
  FOREIGN KEY (idMaterial) REFERENCES material(idMaterial),
  FOREIGN KEY (correoB) REFERENCES bibliotecario(correo)
) ;
