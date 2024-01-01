-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 25, 2023 at 12:46 PM
-- Server version: 5.7.11
-- PHP Version: 5.6.18



--
-- Database: `bananawhatsappdb`
--

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id`, `activo`, `alta`, `email`, `nombre`) VALUES
(26, b'1', '2024-01-01', 'elimino@e.com', 'Elimino'),
(27, b'1', '2024-01-01', 'borro@b.com', 'Borro'),
(28, b'1', '2024-01-01', 'valido@v.com', 'Valido');

--
-- Dumping data for table `mensaje`
--

INSERT INTO `mensaje` (`id`, `cuerpo`, `fecha`, `from_user`, `to_user`) VALUES
(22, 'Feliz Navidad', '2024-01-01', 26, 27),
(23, 'Felices fiestas', '2024-01-01', 27, 26),
(24, 'Feliz año', '2024-01-01', 27, 26),
(25, 'bah humbug!', '2024-01-01', 28, 27);



