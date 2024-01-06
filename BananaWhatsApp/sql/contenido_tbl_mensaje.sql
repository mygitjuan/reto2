-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 06-01-2024 a las 19:20:05
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
-- Estructura de tabla para la tabla `mensaje`
--

CREATE TABLE `mensaje` (
  `id` int(11) NOT NULL,
  `cuerpo` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `from_user` int(11) DEFAULT NULL,
  `to_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `mensaje`
--

INSERT INTO `mensaje` (`id`, `cuerpo`, `fecha`, `from_user`, `to_user`) VALUES
(1, 'Hola, qué tal?', '2023-11-25', 1, 2),
(2, 'Muy bien! y tu?', '2023-11-25', 2, 1),
(3, 'Bien también...', '2023-11-25', 1, 2),
(4, 'Chachi!', '2023-11-25', 2, 1),
(5, 'Está es la prueba 1', '2023-12-30', 1, 2),
(6, 'Mensaje de prueba de servicio', '2023-12-30', 1, 2),
(7, 'Mensaje de prueba de servicio', '2023-12-30', 1, 2),
(8, 'Está es la prueba repositorio', '2023-12-30', 1, 2),
(9, 'Mensaje de prueba de controller', '2023-12-30', 1, 2),
(10, 'Está es la prueba repositorio', '2023-12-31', 18, 19),
(11, 'Mensaje de prueba servicio', '2023-12-31', 18, 19),
(12, 'Mensaje de prueba de controller', '2023-12-31', 18, 19),
(13, 'Está es la prueba repositorio', '2023-12-31', 22, 23),
(14, 'Mensaje de prueba servicio', '2023-12-31', 22, 23),
(15, 'Mensaje de prueba de controller', '2023-12-31', 22, 23),
(16, 'Mensaje de prueba de controller', '2023-12-31', 22, 23),
(21, 'Mensaje de prueba de controller', '2023-12-31', 22, 21);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKe575t9q4ol4vdkb21f4tysbxe` (`from_user`),
  ADD KEY `FK76kstlgspgoe0kdekwggfvtd2` (`to_user`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD CONSTRAINT `FK76kstlgspgoe0kdekwggfvtd2` FOREIGN KEY (`to_user`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKe575t9q4ol4vdkb21f4tysbxe` FOREIGN KEY (`from_user`) REFERENCES `usuario` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
