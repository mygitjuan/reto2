-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 06-01-2024 a las 19:18:37
-- Versión del servidor: 5.7.11
-- Versión de PHP: 5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bananawhatsappdb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `activo` bit(1) NOT NULL,
  `alta` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `activo`, `alta`, `email`, `nombre`) VALUES
(1, b'1', '2023-11-25', 'juana@j.com', 'Juana'),
(2, b'1', '2023-11-25', 'luis@l.com', 'Luis'),
(3, b'1', '2024-01-01', 'elimino@e.com', 'Elimino'),
(4, b'1', '2024-01-01', 'borro@b.com', 'Borro'),
(15, b'1', '2023-12-30', 'persistencia@email.com', 'Persistencia'),
(16, b'1', '2023-12-30', 'servicio@email.com', 'Servicio'),
(17, b'1', '2023-12-30', 'controlador@email.com', 'Controlador'),
(18, b'1', '2023-12-31', 'persistencia2@email.com', 'Persistencia2'),
(19, b'0', '2023-12-31', 'servicio2@email.com', 'Servicio2'),
(20, b'1', '2023-12-31', 'controlador2@email.com', 'Controlador2'),
(21, b'1', '2023-12-31', 'persistencia3@email.com', 'Persistencia3'),
(22, b'1', '2023-12-31', 'servicio3@email.com', 'Servicio3'),
(23, b'1', '2023-12-31', 'controlador3@email.com', 'Controlador3'),
(24, b'1', '2024-01-01', 'elimino@e.com', 'Elimino'),
(25, b'1', '2024-01-01', 'borro@b.com', 'Borro'),
(26, b'1', '2024-01-01', 'elimino@e.com', 'Elimino'),
(28, b'1', '2024-01-01', 'valido@v.com', 'Valido'),
(29, b'1', '2024-01-01', 'mariner@o.com', 'Mariner'),
(32, b'1', '2024-01-01', 'mariner#o.com', 'Mariner'),
(34, b'1', '2024-01-01', 'dvana@d.com', 'Dvana'),
(35, b'1', '2024-01-01', 'mariner@o.com', 'Mariner'),
(37, b'1', '2024-01-01', 'dvana@d.com', 'Dvana'),
(38, b'1', '2024-01-01', 'mariner@o.com', 'Mariner'),
(40, b'1', '2024-01-01', 'dvana@d.com', 'Dvana'),
(41, b'1', '2024-01-01', 'cisne@c.com', 'Cisne'),
(43, b'1', '2024-01-01', 'dvana@d.com', 'Dvana');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
