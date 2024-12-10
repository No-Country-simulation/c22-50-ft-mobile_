-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 10-12-2024 a las 18:19:35
-- Versión del servidor: 8.3.0
-- Versión de PHP: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `crowdfundingdb`
--

DELIMITER $$
--
-- Procedimientos
--
DROP PROCEDURE IF EXISTS `generarInversion`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `generarInversion` (IN `inversorId` INT, IN `crowdId` INT, IN `monto` DECIMAL)   BEGIN
    
    START TRANSACTION;
    	IF(NOT EXISTS (SELECT fondos_disponibles
                   	   FROM inversor
                   	   WHERE usuario_id = inversorId
                       AND fondos_disponibles >= monto)
          )
        THEN
        	ROLLBACK;
        END IF;
        
        IF(EXISTS (SELECT * 
                   FROM crowdfunding
                   WHERE id = crowdId
                   AND estado = 'Aprobado')
          )
        THEN
        	UPDATE inversor 
            SET fondos_disponibles = fondos_disponibles - monto
            WHERE usuario_id = inversorId;
            
            UPDATE crowdfunding
            SET monto_recaudado = monto_recaudado + monto
            WHERE id = crowdId;
            
            INSERT INTO inversion(
            	`monto`,
                fecha_inversion,
                inversor_id,
                crowdfunding_id
            ) VALUES (
                monto,
                CURRENT_DATE,
                inversorId,
                crowdId
            );
        ELSE
        	ROLLBACK;
        END IF;
        
    COMMIT;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

DROP TABLE IF EXISTS `administrador`;
CREATE TABLE IF NOT EXISTS `administrador` (
  `id` int NOT NULL,
  `permisos` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `crowdfunding`
--

DROP TABLE IF EXISTS `crowdfunding`;
CREATE TABLE IF NOT EXISTS `crowdfunding` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) NOT NULL,
  `descripcion` text,
  `monto_objetivo` decimal(15,2) NOT NULL,
  `monto_recaudado` decimal(15,2) DEFAULT '0.00',
  `estado` enum('Pendiente','Aprobado','Finalizado') NOT NULL DEFAULT 'Pendiente',
  `fecha_creacion` date NOT NULL,
  `fecha_finalizacion` date NOT NULL,
  `emprendedor_id` int NOT NULL,
  `url_imagen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `emprendedor_id` (`emprendedor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `crowdfunding`
--

INSERT INTO `crowdfunding` (`id`, `titulo`, `descripcion`, `monto_objetivo`, `monto_recaudado`, `estado`, `fecha_creacion`, `fecha_finalizacion`, `emprendedor_id`, `url_imagen`) VALUES
(1, 'Unidos para el Cambio: Tu Apoyo, Nuestro Futuro', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.', 10000.99, 5134.00, 'Aprobado', '2024-12-02', '2024-12-31', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsgOjj_ELuqils_hP1As_8ksCFsMyH8QlblQ&s'),
(2, 'Transformando Vidas, Creando Esperanza: contra el hambre infantil', 'Phasellus quis ligula non tortor pellentesque suscipit in malesuada nibh. Ut sed accumsan sapien, nec blandit eros. Morbi mollis massa eu rutrum hendrerit. Morbi vel consectetur nisl. Aliquam eu suscipit ante, et efficitur ipsum. Suspendisse ac turpis dignissim, lobortis neque sed, bibendum ipsum. Nullam sit amet lorem congue, mollis arcu a, consectetur tortor. Maecenas sodales condimentum viverra. Pellentesque sed nibh fringilla, iaculis massa rutrum, sollicitudin elit. Integer accumsan bibendum nunc, vel tempus enim placerat at. Vestibulum maximus mi orci, hendrerit sodales sapien sollicitudin hendrerit. Sed venenatis sit amet enim et efficitur.\r\n\r\nNam cursus in enim at fermentum. Curabitur finibus sem facilisis sollicitudin pretium. Proin imperdiet dui nisl, eget maximus urna convallis in. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean imperdiet nisi at ante sollicitudin mollis. Duis id arcu interdum, luctus justo non, elementum sapien. Integer pretium, nunc eget sollicitudin feugiat, lectus ligula pellentesque ligula, eget mattis ex lacus vel nibh. Aliquam molestie nisl est, eu lobortis mauris condimentum dignissim. Curabitur elit dui, semper sed turpis a, venenatis vestibulum ex. Phasellus ornare risus dictum arcu posuere blandit. Maecenas semper hendrerit massa vitae convallis. Phasellus sit amet placerat metus, eget ultricies mauris. Vestibulum ante tortor, mattis ac dui viverra, blandit viverra elit. Pellentesque aliquam pulvinar ex, vitae luctus libero semper vel. Vestibulum facilisis maximus nisi eget gravida. Sed diam enim, gravida sit amet pretium at, tempor eu mi.', 150000.99, 134009.99, 'Aprobado', '2024-12-18', '2025-05-15', 1, 'https://www.soziable.es/sites/default/files/styles/wide/public/field/image/savethechidrenhambre_1.jpg?itok=Q-p5mnXY'),
(3, 'Eclipse: La Guerra de los Reinos - Videojuego indie', '\"Eclipse: La Guerra de los Reinos\" es un videojuego de acción y estrategia en un mundo de fantasía donde los reinos luchan por la supremacía. Un eclipse cósmico ha alterado el equilibrio de poder, desatando una guerra brutal entre facciones antiguas que buscan controlar la misteriosa energía del eclipse. Los jugadores deberán elegir su reino, formar alianzas, y liderar a su ejército en batallas épicas mientras exploran vastos paisajes llenos de secretos y criaturas míticas.\r\n\r\nCon mecánicas de combate dinámicas, decisiones estratégicas y un sistema de progresión único, \"Eclipse: La Guerra de los Reinos\" desafía a los jugadores a decidir el destino de un mundo al borde del colapso. ¿Podrás restaurar el equilibrio o sumergir a los reinos en la oscuridad eterna?', 13000.00, 740.11, 'Aprobado', '2024-06-20', '2024-12-31', 2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9uoeIlmtzAjkrTZwQG0BslGLK_XDhW9S6Hw&s'),
(4, 'Llanura silenciosa 2: Un videojuego sobre el sexo y la muerte', 'Es un juego de terror psicológico en el que te adentrarás en una mansión antigua y deteriorada, llena de secretos oscuros y criaturas que acechan en las sombras. Como protagonista, interpretarás a un individuo que, tras un misterioso accidente, se despierta en este lugar olvidado por el tiempo. A medida que exploras sus pasillos, descubrirás fragmentos de una historia perturbadora que conecta tu pasado con el horror que habita entre sus paredes.\r\n\r\nCon mecánicas de supervivencia, acertijos y una atmósfera tensa, \"La Casa del Olvido\" te pondrá a prueba en cada rincón, mientras intentas escapar antes de que las fuerzas oscuras te atrapen. No hay enemigos visibles, solo el eco de lo que fue. Cada decisión que tomes puede alterar tu destino, pero, ¿realmente podrás escapar de la casa… o ella escapará de ti?', 1000.00, 19.90, 'Aprobado', '2024-06-20', '2025-12-15', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5KMAhAtSHcovMVv4uyJKJT3hf-p9Lrijh7w&s');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `emprendedor`
--

DROP TABLE IF EXISTS `emprendedor`;
CREATE TABLE IF NOT EXISTS `emprendedor` (
  `usuario_id` int NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`usuario_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `emprendedor`
--

INSERT INTO `emprendedor` (`usuario_id`, `descripcion`) VALUES
(1, 'Donec convallis sem nec eros venenatis tempus. Vestibulum auctor nibh purus, in placerat leo pharetra vitae.'),
(2, 'Suspendisse pulvinar nec diam sit amet ullamcorper.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `etiqueta`
--

DROP TABLE IF EXISTS `etiqueta`;
CREATE TABLE IF NOT EXISTS `etiqueta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `crowdfunding_id` int NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `crowdfunding_id` (`crowdfunding_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `etiqueta`
--

INSERT INTO `etiqueta` (`id`, `crowdfunding_id`, `descripcion`) VALUES
(1, 1, 'Salud'),
(2, 2, 'Videojuegos'),
(3, 1, 'Sin fines de lucro'),
(4, 1, 'Gastronomía');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inversion`
--

DROP TABLE IF EXISTS `inversion`;
CREATE TABLE IF NOT EXISTS `inversion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `monto` decimal(15,2) NOT NULL,
  `fecha_inversion` date NOT NULL,
  `inversor_id` int NOT NULL,
  `crowdfunding_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `inversor_id` (`inversor_id`),
  KEY `crowdfunding_id` (`crowdfunding_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inversor`
--

DROP TABLE IF EXISTS `inversor`;
CREATE TABLE IF NOT EXISTS `inversor` (
  `usuario_id` int NOT NULL,
  `fondos_disponibles` decimal(15,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`usuario_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitud`
--

DROP TABLE IF EXISTS `solicitud`;
CREATE TABLE IF NOT EXISTS `solicitud` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` text NOT NULL,
  `estado` enum('Pendiente','Rechazada','Aprobada') NOT NULL DEFAULT 'Pendiente',
  `fecha_envio` date NOT NULL,
  `crowdfunding_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `crowdfunding_id` (`crowdfunding_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `solicitud`
--

INSERT INTO `solicitud` (`id`, `descripcion`, `estado`, `fecha_envio`, `crowdfunding_id`) VALUES
(1, 'hejdje', 'Pendiente', '2024-12-10', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contrasenia` varchar(255) NOT NULL,
  `imagen_perfil` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `correo`, `contrasenia`, `imagen_perfil`) VALUES
(1, 'Joel', 'joelfernandez.relg@gmail.com', 'root', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSW8q3zIgKDosEl9HK82LXIFYJO6AYmCRavLEFTH-ZSbhOC_h6omFc2TtlcDl1DBCSxcYU&usqp=CAU'),
(2, 'Mariangela', 'mariangela@hotmail.com', '12345', 'https://cdn-uploads-frankfurt2.starofservice.com/uploads/pj/thumbs-medium/starofservice_50290img20180721185739789.jpg'),
(3, 'Brisa', 'aguilera@outlook.net', 'abcde', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6QY-YNnuiNVBvwmNc8pHGJLjgzsZ4qdQu9Q&s');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
