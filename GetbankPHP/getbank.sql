-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 16 apr 2020 om 21:57
-- Serverversie: 10.4.11-MariaDB
-- PHP-versie: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `getbank`
--
CREATE DATABASE IF NOT EXISTS `getbank` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `getbank`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `bankpas`
--

CREATE TABLE `bankpas` (
  `KAARTNUMMER` int(11) NOT NULL,
  `REKENINGNUMMER` char(18) NOT NULL,
  `PINCODE` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `klantgegevens`
--

CREATE TABLE `klantgegevens` (
  `KLANTID` smallint(6) NOT NULL,
  `REKENINGNUMMER` char(18) NOT NULL,
  `VOORNAAM` char(35) NOT NULL,
  `ACHTERNAAM` char(35) NOT NULL,
  `GEBOORTEDATUM` date NOT NULL,
  `ADRES` char(50) NOT NULL,
  `WOONPLAATS` char(35) NOT NULL,
  `POSTCODE` char(7) NOT NULL,
  `GESLACHT` char(1) NOT NULL,
  `TELEFOONNR` int(11) NOT NULL,
  `EMAILADRES` char(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Gegevens worden geëxporteerd voor tabel `klantgegevens`
--

INSERT INTO `klantgegevens` (`KLANTID`, `REKENINGNUMMER`, `VOORNAAM`, `ACHTERNAAM`, `GEBOORTEDATUM`, `ADRES`, `WOONPLAATS`, `POSTCODE`, `GESLACHT`, `TELEFOONNR`, `EMAILADRES`) VALUES
(0, '000000000', 'Blan', 'Co', '1900-01-01', 'straat', 'Stad', '0000AA', 'M', 2147483647, 'email@email.nl'),
(69, '000000069', 'Siks', 'Tinine', '1969-06-09', 'Standjesstraat', 'Pernis', '6969XD', 'F', 319060069, '6tinine@hotmail.com');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `personeelsgegevens`
--

CREATE TABLE `personeelsgegevens` (
  `PERSONEELSID` smallint(6) NOT NULL,
  `VOORNAAM` char(35) NOT NULL,
  `ACHTERNAAM` char(35) NOT NULL,
  `GEBOORTEDATUM` date NOT NULL,
  `ADRES` char(50) NOT NULL,
  `WOONPLAATS` char(35) NOT NULL,
  `POSTCODE` char(7) NOT NULL,
  `GESLACHT` char(1) NOT NULL,
  `TELEFOONNR` int(11) NOT NULL,
  `EMAILADRES` char(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `rekeningen`
--

CREATE TABLE `rekeningen` (
  `KLANTID` smallint(6) NOT NULL,
  `REKENINGNUMMER` char(18) NOT NULL,
  `KAARTSOORT` char(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Gegevens worden geëxporteerd voor tabel `rekeningen`
--

INSERT INTO `rekeningen` (`KLANTID`, `REKENINGNUMMER`, `KAARTSOORT`) VALUES
(0, '00000000', 'Debit'),
(69, '00000069', 'Credit');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `saldos`
--

CREATE TABLE `saldos` (
  `REKENINGNUMMER` char(18) NOT NULL,
  `SALDO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Gegevens worden geëxporteerd voor tabel `saldos`
--

INSERT INTO `saldos` (`REKENINGNUMMER`, `SALDO`) VALUES
('00000000', 0),
('00000069', 69);

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `bankpas`
--
ALTER TABLE `bankpas`
  ADD PRIMARY KEY (`KAARTNUMMER`),
  ADD KEY `REKENINGNUMMER` (`REKENINGNUMMER`);

--
-- Indexen voor tabel `klantgegevens`
--
ALTER TABLE `klantgegevens`
  ADD PRIMARY KEY (`KLANTID`);

--
-- Indexen voor tabel `personeelsgegevens`
--
ALTER TABLE `personeelsgegevens`
  ADD PRIMARY KEY (`PERSONEELSID`);

--
-- Indexen voor tabel `rekeningen`
--
ALTER TABLE `rekeningen`
  ADD PRIMARY KEY (`REKENINGNUMMER`),
  ADD KEY `KLANTID` (`KLANTID`);

--
-- Indexen voor tabel `saldos`
--
ALTER TABLE `saldos`
  ADD PRIMARY KEY (`REKENINGNUMMER`);

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `bankpas`
--
ALTER TABLE `bankpas`
  ADD CONSTRAINT `bankpas_ibfk_1` FOREIGN KEY (`REKENINGNUMMER`) REFERENCES `rekeningen` (`REKENINGNUMMER`);

--
-- Beperkingen voor tabel `rekeningen`
--
ALTER TABLE `rekeningen`
  ADD CONSTRAINT `rekeningen_ibfk_1` FOREIGN KEY (`KLANTID`) REFERENCES `klantgegevens` (`KLANTID`);

--
-- Beperkingen voor tabel `saldos`
--
ALTER TABLE `saldos`
  ADD CONSTRAINT `saldos_ibfk_1` FOREIGN KEY (`REKENINGNUMMER`) REFERENCES `rekeningen` (`REKENINGNUMMER`);
--
-- Database: `get_bank`
--
CREATE DATABASE IF NOT EXISTS `get_bank` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `get_bank`;
--
-- Database: `levart_final`
--
CREATE DATABASE IF NOT EXISTS `levart_final` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `levart_final`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `artikelen`
--

CREATE TABLE `artikelen` (
  `ARTIKELID` int(11) NOT NULL,
  `ARTIKELNAAM` varchar(100) NOT NULL,
  `KLEUR` varchar(50) DEFAULT NULL,
  `GEWICHT` smallint(6) DEFAULT NULL,
  `STAD` varchar(50) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `artikelen`
--

INSERT INTO `artikelen` (`ARTIKELID`, `ARTIKELNAAM`, `KLEUR`, `GEWICHT`, `STAD`) VALUES
(1, 'moer', 'rood', 12, 'Londen'),
(2, 'bout', 'groen', 17, 'Parijs'),
(3, 'schroef', 'blauw', 17, 'Rome'),
(4, 'schroef', 'rood', 14, 'Londen'),
(5, 'kamrad', 'blauw', 12, 'Parijs'),
(6, 'nok', 'groen', 19, 'Londen');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `leveranciers`
--

CREATE TABLE `leveranciers` (
  `LEVERANCIERID` int(11) NOT NULL,
  `NAAM` varchar(100) NOT NULL,
  `STATUS` tinyint(4) DEFAULT 10,
  `STAD` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `leveranciers`
--

INSERT INTO `leveranciers` (`LEVERANCIERID`, `NAAM`, `STATUS`, `STAD`) VALUES
(1, 'Smith', 20, 'Londen'),
(2, 'Chirac', 10, 'Parijs'),
(3, 'Boulanger', 30, 'Parijs'),
(4, 'Blair', 20, 'Londen'),
(5, 'Papandreou', 30, 'Athene');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `voorraad`
--

CREATE TABLE `voorraad` (
  `LEVERANCIERSID` int(11) DEFAULT NULL,
  `ARTIKELID` int(11) DEFAULT NULL,
  `AANTAL` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `voorraad`
--

INSERT INTO `voorraad` (`LEVERANCIERSID`, `ARTIKELID`, `AANTAL`) VALUES
(1, 1, 300),
(1, 2, 200),
(1, 3, 400),
(1, 4, 200),
(1, 5, 100),
(1, 6, 100),
(2, 1, 300),
(2, 2, 400),
(3, 3, 200),
(4, 4, 200),
(4, 5, 300),
(4, 6, 400);

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `artikelen`
--
ALTER TABLE `artikelen`
  ADD PRIMARY KEY (`ARTIKELID`);

--
-- Indexen voor tabel `leveranciers`
--
ALTER TABLE `leveranciers`
  ADD PRIMARY KEY (`LEVERANCIERID`);

--
-- Indexen voor tabel `voorraad`
--
ALTER TABLE `voorraad`
  ADD KEY `LEVERANCIERSID` (`LEVERANCIERSID`),
  ADD KEY `ARTIKELID` (`ARTIKELID`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `artikelen`
--
ALTER TABLE `artikelen`
  MODIFY `ARTIKELID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT voor een tabel `leveranciers`
--
ALTER TABLE `leveranciers`
  MODIFY `LEVERANCIERID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `voorraad`
--
ALTER TABLE `voorraad`
  ADD CONSTRAINT `ARTIKELID` FOREIGN KEY (`ARTIKELID`) REFERENCES `artikelen` (`ARTIKELID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `LEVERANCIERID` FOREIGN KEY (`LEVERANCIERSID`) REFERENCES `leveranciers` (`LEVERANCIERID`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Database: `phpmyadmin`
--
CREATE DATABASE IF NOT EXISTS `phpmyadmin` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `phpmyadmin`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__bookmark`
--

CREATE TABLE `pma__bookmark` (
  `id` int(10) UNSIGNED NOT NULL,
  `dbase` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `user` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `label` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `query` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__central_columns`
--

CREATE TABLE `pma__central_columns` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_type` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_length` text COLLATE utf8_bin DEFAULT NULL,
  `col_collation` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_isNull` tinyint(1) NOT NULL,
  `col_extra` varchar(255) COLLATE utf8_bin DEFAULT '',
  `col_default` text COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Central list of columns';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__column_info`
--

CREATE TABLE `pma__column_info` (
  `id` int(5) UNSIGNED NOT NULL,
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `column_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `comment` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `mimetype` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `transformation` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `transformation_options` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `input_transformation` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `input_transformation_options` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Column information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__designer_settings`
--

CREATE TABLE `pma__designer_settings` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `settings_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Settings related to Designer';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__export_templates`
--

CREATE TABLE `pma__export_templates` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `export_type` varchar(10) COLLATE utf8_bin NOT NULL,
  `template_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `template_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved export templates';

--
-- Gegevens worden geëxporteerd voor tabel `pma__export_templates`
--

INSERT INTO `pma__export_templates` (`id`, `username`, `export_type`, `template_name`, `template_data`) VALUES
(1, 'root', 'server', 'Template', '{\"quick_or_custom\":\"quick\",\"what\":\"sql\",\"db_select[]\":[\"getbank\",\"get_bank\",\"levart_final\",\"phpmyadmin\",\"tennis\",\"test\"],\"aliases_new\":\"\",\"output_format\":\"sendit\",\"filename_template\":\"@SERVER@\",\"remember_template\":\"on\",\"charset\":\"utf-8\",\"compression\":\"none\",\"maxsize\":\"\",\"codegen_structure_or_data\":\"data\",\"codegen_format\":\"0\",\"csv_separator\":\",\",\"csv_enclosed\":\"\\\"\",\"csv_escaped\":\"\\\"\",\"csv_terminated\":\"AUTO\",\"csv_null\":\"NULL\",\"csv_structure_or_data\":\"data\",\"excel_null\":\"NULL\",\"excel_columns\":\"something\",\"excel_edition\":\"win\",\"excel_structure_or_data\":\"data\",\"json_structure_or_data\":\"data\",\"json_unicode\":\"something\",\"latex_caption\":\"something\",\"latex_structure_or_data\":\"structure_and_data\",\"latex_structure_caption\":\"Structuur van de tabel @TABLE@\",\"latex_structure_continued_caption\":\"Structuur van de tabel @TABLE@ (vervolgd)\",\"latex_structure_label\":\"tab:@TABLE@-structure\",\"latex_relation\":\"something\",\"latex_comments\":\"something\",\"latex_mime\":\"something\",\"latex_columns\":\"something\",\"latex_data_caption\":\"Inhoud van tabel @TABLE@\",\"latex_data_continued_caption\":\"Inhoud van tabel @TABLE@ (vervolgd)\",\"latex_data_label\":\"tab:@TABLE@-data\",\"latex_null\":\"\\\\textit{NULL}\",\"mediawiki_structure_or_data\":\"data\",\"mediawiki_caption\":\"something\",\"mediawiki_headers\":\"something\",\"htmlword_structure_or_data\":\"structure_and_data\",\"htmlword_null\":\"NULL\",\"ods_null\":\"NULL\",\"ods_structure_or_data\":\"data\",\"odt_structure_or_data\":\"structure_and_data\",\"odt_relation\":\"something\",\"odt_comments\":\"something\",\"odt_mime\":\"something\",\"odt_columns\":\"something\",\"odt_null\":\"NULL\",\"pdf_report_title\":\"\",\"pdf_structure_or_data\":\"data\",\"phparray_structure_or_data\":\"data\",\"sql_include_comments\":\"something\",\"sql_header_comment\":\"\",\"sql_use_transaction\":\"something\",\"sql_compatibility\":\"NONE\",\"sql_structure_or_data\":\"structure_and_data\",\"sql_create_table\":\"something\",\"sql_auto_increment\":\"something\",\"sql_create_view\":\"something\",\"sql_create_trigger\":\"something\",\"sql_backquotes\":\"something\",\"sql_type\":\"INSERT\",\"sql_insert_syntax\":\"both\",\"sql_max_query_size\":\"50000\",\"sql_hex_for_binary\":\"something\",\"sql_utc_time\":\"something\",\"texytext_structure_or_data\":\"structure_and_data\",\"texytext_null\":\"NULL\",\"yaml_structure_or_data\":\"data\",\"\":null,\"as_separate_files\":null,\"csv_removeCRLF\":null,\"csv_columns\":null,\"excel_removeCRLF\":null,\"json_pretty_print\":null,\"htmlword_columns\":null,\"ods_columns\":null,\"sql_dates\":null,\"sql_relation\":null,\"sql_mime\":null,\"sql_disable_fk\":null,\"sql_views_as_tables\":null,\"sql_metadata\":null,\"sql_drop_database\":null,\"sql_drop_table\":null,\"sql_if_not_exists\":null,\"sql_view_current_user\":null,\"sql_or_replace_view\":null,\"sql_procedure_function\":null,\"sql_truncate\":null,\"sql_delayed\":null,\"sql_ignore\":null,\"texytext_columns\":null}');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__favorite`
--

CREATE TABLE `pma__favorite` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `tables` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Favorite tables';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__history`
--

CREATE TABLE `pma__history` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `db` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp(),
  `sqlquery` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SQL history for phpMyAdmin';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__navigationhiding`
--

CREATE TABLE `pma__navigationhiding` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `item_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `item_type` varchar(64) COLLATE utf8_bin NOT NULL,
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Hidden items of navigation tree';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__pdf_pages`
--

CREATE TABLE `pma__pdf_pages` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `page_nr` int(10) UNSIGNED NOT NULL,
  `page_descr` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='PDF relation pages for phpMyAdmin';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__recent`
--

CREATE TABLE `pma__recent` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `tables` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Recently accessed tables';

--
-- Gegevens worden geëxporteerd voor tabel `pma__recent`
--

INSERT INTO `pma__recent` (`username`, `tables`) VALUES
('root', '[{\"db\":\"getbank\",\"table\":\"klantgegevens\"},{\"db\":\"getbank\",\"table\":\"saldos\"},{\"db\":\"getbank\",\"table\":\"rekeningen\"},{\"db\":\"getbank\",\"table\":\"bankpas\"},{\"db\":\"getbank\",\"table\":\"personeelsgegevens\"},{\"db\":\"tennis\",\"table\":\"boetes\"},{\"db\":\"tennis\",\"table\":\"teams\"},{\"db\":\"tennis\",\"table\":\"wedstrijden\"},{\"db\":\"tennis\",\"table\":\"bestuursleden\"},{\"db\":\"tennis\",\"table\":\"spelers\"}]');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__relation`
--

CREATE TABLE `pma__relation` (
  `master_db` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `master_table` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `master_field` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `foreign_db` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `foreign_table` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `foreign_field` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Relation table';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__savedsearches`
--

CREATE TABLE `pma__savedsearches` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `search_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `search_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved searches';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__table_coords`
--

CREATE TABLE `pma__table_coords` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `pdf_page_number` int(11) NOT NULL DEFAULT 0,
  `x` float UNSIGNED NOT NULL DEFAULT 0,
  `y` float UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table coordinates for phpMyAdmin PDF output';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__table_info`
--

CREATE TABLE `pma__table_info` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `display_field` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__table_uiprefs`
--

CREATE TABLE `pma__table_uiprefs` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `prefs` text COLLATE utf8_bin NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Tables'' UI preferences';

--
-- Gegevens worden geëxporteerd voor tabel `pma__table_uiprefs`
--

INSERT INTO `pma__table_uiprefs` (`username`, `db_name`, `table_name`, `prefs`, `last_update`) VALUES
('root', 'levart_final', 'leveranciers', '{\"CREATE_TIME\":\"2020-02-10 12:06:07\",\"col_visib\":[1,1,1,1]}', '2020-02-19 12:18:22'),
('root', 'levart_final', 'voorraad', '{\"sorted_col\":\"`voorraad`.`LEVERANCIERSID` ASC\"}', '2020-03-05 10:35:27');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__tracking`
--

CREATE TABLE `pma__tracking` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `version` int(10) UNSIGNED NOT NULL,
  `date_created` datetime NOT NULL,
  `date_updated` datetime NOT NULL,
  `schema_snapshot` text COLLATE utf8_bin NOT NULL,
  `schema_sql` text COLLATE utf8_bin DEFAULT NULL,
  `data_sql` longtext COLLATE utf8_bin DEFAULT NULL,
  `tracking` set('UPDATE','REPLACE','INSERT','DELETE','TRUNCATE','CREATE DATABASE','ALTER DATABASE','DROP DATABASE','CREATE TABLE','ALTER TABLE','RENAME TABLE','DROP TABLE','CREATE INDEX','DROP INDEX','CREATE VIEW','ALTER VIEW','DROP VIEW') COLLATE utf8_bin DEFAULT NULL,
  `tracking_active` int(1) UNSIGNED NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Database changes tracking for phpMyAdmin';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__userconfig`
--

CREATE TABLE `pma__userconfig` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `config_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User preferences storage for phpMyAdmin';

--
-- Gegevens worden geëxporteerd voor tabel `pma__userconfig`
--

INSERT INTO `pma__userconfig` (`username`, `timevalue`, `config_data`) VALUES
('root', '2020-04-16 18:47:33', '{\"Console\\/Mode\":\"show\",\"lang\":\"nl\",\"Console\\/Height\":185,\"NavigationWidth\":273}');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__usergroups`
--

CREATE TABLE `pma__usergroups` (
  `usergroup` varchar(64) COLLATE utf8_bin NOT NULL,
  `tab` varchar(64) COLLATE utf8_bin NOT NULL,
  `allowed` enum('Y','N') COLLATE utf8_bin NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User groups with configured menu items';

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pma__users`
--

CREATE TABLE `pma__users` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `usergroup` varchar(64) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and their assignments to user groups';

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `pma__central_columns`
--
ALTER TABLE `pma__central_columns`
  ADD PRIMARY KEY (`db_name`,`col_name`);

--
-- Indexen voor tabel `pma__column_info`
--
ALTER TABLE `pma__column_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `db_name` (`db_name`,`table_name`,`column_name`);

--
-- Indexen voor tabel `pma__designer_settings`
--
ALTER TABLE `pma__designer_settings`
  ADD PRIMARY KEY (`username`);

--
-- Indexen voor tabel `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_user_type_template` (`username`,`export_type`,`template_name`);

--
-- Indexen voor tabel `pma__favorite`
--
ALTER TABLE `pma__favorite`
  ADD PRIMARY KEY (`username`);

--
-- Indexen voor tabel `pma__history`
--
ALTER TABLE `pma__history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`,`db`,`table`,`timevalue`);

--
-- Indexen voor tabel `pma__navigationhiding`
--
ALTER TABLE `pma__navigationhiding`
  ADD PRIMARY KEY (`username`,`item_name`,`item_type`,`db_name`,`table_name`);

--
-- Indexen voor tabel `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  ADD PRIMARY KEY (`page_nr`),
  ADD KEY `db_name` (`db_name`);

--
-- Indexen voor tabel `pma__recent`
--
ALTER TABLE `pma__recent`
  ADD PRIMARY KEY (`username`);

--
-- Indexen voor tabel `pma__relation`
--
ALTER TABLE `pma__relation`
  ADD PRIMARY KEY (`master_db`,`master_table`,`master_field`),
  ADD KEY `foreign_field` (`foreign_db`,`foreign_table`);

--
-- Indexen voor tabel `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_savedsearches_username_dbname` (`username`,`db_name`,`search_name`);

--
-- Indexen voor tabel `pma__table_coords`
--
ALTER TABLE `pma__table_coords`
  ADD PRIMARY KEY (`db_name`,`table_name`,`pdf_page_number`);

--
-- Indexen voor tabel `pma__table_info`
--
ALTER TABLE `pma__table_info`
  ADD PRIMARY KEY (`db_name`,`table_name`);

--
-- Indexen voor tabel `pma__table_uiprefs`
--
ALTER TABLE `pma__table_uiprefs`
  ADD PRIMARY KEY (`username`,`db_name`,`table_name`);

--
-- Indexen voor tabel `pma__tracking`
--
ALTER TABLE `pma__tracking`
  ADD PRIMARY KEY (`db_name`,`table_name`,`version`);

--
-- Indexen voor tabel `pma__userconfig`
--
ALTER TABLE `pma__userconfig`
  ADD PRIMARY KEY (`username`);

--
-- Indexen voor tabel `pma__usergroups`
--
ALTER TABLE `pma__usergroups`
  ADD PRIMARY KEY (`usergroup`,`tab`,`allowed`);

--
-- Indexen voor tabel `pma__users`
--
ALTER TABLE `pma__users`
  ADD PRIMARY KEY (`username`,`usergroup`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `pma__column_info`
--
ALTER TABLE `pma__column_info`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT voor een tabel `pma__history`
--
ALTER TABLE `pma__history`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  MODIFY `page_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- Database: `tennis`
--
CREATE DATABASE IF NOT EXISTS `tennis` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tennis`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `bestuursleden`
--

CREATE TABLE `bestuursleden` (
  `SPELERSNR` int(11) NOT NULL,
  `BEGIN_DATUM` date NOT NULL,
  `EIND_DATUM` date DEFAULT NULL,
  `FUNCTIE` char(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Gegevens worden geëxporteerd voor tabel `bestuursleden`
--

INSERT INTO `bestuursleden` (`SPELERSNR`, `BEGIN_DATUM`, `EIND_DATUM`, `FUNCTIE`) VALUES
(2, '1990-01-01', '1992-12-31', 'Voorzitter'),
(2, '1994-01-01', NULL, 'Lid'),
(6, '1990-01-01', '1990-12-31', 'Secretaris'),
(6, '1991-01-01', '1992-12-31', 'Lid'),
(6, '1992-01-01', '1993-12-31', 'Penningmeester'),
(6, '1993-01-01', NULL, 'Voorzitter'),
(8, '1990-01-01', '1990-12-31', 'Penningmeester'),
(8, '1991-01-01', '1991-12-31', 'Secretaris'),
(8, '1993-01-01', '1993-12-31', 'Lid'),
(8, '1994-01-01', NULL, 'Lid'),
(27, '1990-01-01', '1990-12-31', 'Lid'),
(27, '1991-01-01', '1991-12-31', 'Penningmeester'),
(27, '1993-01-01', '1993-12-31', 'Penningmeester'),
(57, '1992-01-01', '1992-12-31', 'Secretaris'),
(95, '1994-01-01', NULL, 'Penningmeester'),
(112, '1992-01-01', '1992-12-31', 'Lid'),
(112, '1994-01-01', NULL, 'Secretaris');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `boetes`
--

CREATE TABLE `boetes` (
  `BETALINGSNR` int(11) NOT NULL,
  `SPELERSNR` int(11) NOT NULL,
  `DATUM` date NOT NULL,
  `BEDRAG` decimal(7,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Gegevens worden geëxporteerd voor tabel `boetes`
--

INSERT INTO `boetes` (`BETALINGSNR`, `SPELERSNR`, `DATUM`, `BEDRAG`) VALUES
(1, 6, '1980-12-08', '100.00'),
(2, 44, '1981-05-05', '75.00'),
(3, 27, '1983-09-10', '100.00'),
(4, 104, '1984-12-08', '50.00'),
(5, 44, '1980-12-08', '25.00'),
(6, 8, '1980-12-08', '25.00'),
(7, 44, '1982-12-30', '30.00'),
(8, 27, '1984-11-12', '75.00');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `spelers`
--

CREATE TABLE `spelers` (
  `SPELERSNR` int(11) NOT NULL,
  `NAAM` char(15) NOT NULL,
  `VOORLETTERS` char(3) NOT NULL,
  `GEB_DATUM` date DEFAULT NULL,
  `GESLACHT` char(1) NOT NULL,
  `JAARTOE` smallint(6) NOT NULL,
  `STRAAT` varchar(30) NOT NULL,
  `HUISNR` char(4) DEFAULT NULL,
  `POSTCODE` char(6) DEFAULT NULL,
  `PLAATS` varchar(30) NOT NULL,
  `TELEFOON` char(13) DEFAULT NULL,
  `BONDSNR` char(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Gegevens worden geëxporteerd voor tabel `spelers`
--

INSERT INTO `spelers` (`SPELERSNR`, `NAAM`, `VOORLETTERS`, `GEB_DATUM`, `GESLACHT`, `JAARTOE`, `STRAAT`, `HUISNR`, `POSTCODE`, `PLAATS`, `TELEFOON`, `BONDSNR`) VALUES
(2, 'Elfring', 'R', '1948-09-01', 'M', 1975, 'Steden', '43', '3575NH', 'Den Haag', '070-237893', '2411'),
(6, 'Permentier', 'R', '1964-06-25', 'M', 1977, 'Hazensteinln', '80', '1234KK', 'Den Haag', '070-476537', '8467'),
(7, 'Wijers', 'GWS', '1963-05-11', 'M', 1981, 'Erasmusweg', '39', '9758VB', 'Den Haag', '070-347689', NULL),
(8, 'Niewenburg', 'B', '1962-07-08', 'V', 1980, 'Spoorlaan', '4', '6584WO', 'Rijswijk', '070-458458', '2983'),
(27, 'Cools', 'DD', '1964-12-28', 'V', 1983, 'Liespad', '804', '8457DK', 'Zoetermeer', '079-234857', '2513'),
(28, 'Cools', 'C', '1963-06-22', 'V', 1983, 'Oudegracht', '10', '1294QK', 'Leiden', '010-659599', NULL),
(39, 'Bischoff', 'D', '1956-10-29', 'M', 1980, 'Ericaplein', '78', '9629CD', 'Den Haag', '070-393435', NULL),
(44, 'Bakker, de', 'E', '1963-01-09', 'M', 1980, 'Lawaaistraat', '23', '4444LJ', 'Rijswijk', '070-368753', '1124'),
(57, 'Bohemen, van', 'M', '1971-08-17', 'M', 1985, 'Erasmusweg', '16', '4377CB', 'Den Haag', '070-473458', '6409'),
(83, 'Hofland', 'PK', '1956-11-11', 'M', 1982, 'Mariakade', '16a', '1812UP', 'Den Haag', '070-353548', '1608'),
(95, 'Meuleman', 'P', '1963-05-14', 'M', 1972, 'Hoofdweg', '33a', '5746OP', 'Voorburg', '070-867564', NULL),
(100, 'Permentier', 'P', '1963-02-28', 'M', 1979, 'Hazensteinln', '80', '6494SG', 'Den Haag', '070-494593', '6524'),
(104, 'Moerman', 'D', '1970-05-10', 'V', 1984, 'Stoutlaan', '65', '9437AO', 'Zoetermeer', '079-987571', '7060'),
(112, 'Baalen, van', 'IP', '1963-10-01', 'V', 1984, 'Vosseweg', '8', '6392LK', 'Rotterdam', '010-548745', '1319');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `teams`
--

CREATE TABLE `teams` (
  `TEAMNR` int(11) NOT NULL,
  `SPELERSNR` int(11) NOT NULL,
  `DIVISIE` char(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Gegevens worden geëxporteerd voor tabel `teams`
--

INSERT INTO `teams` (`TEAMNR`, `SPELERSNR`, `DIVISIE`) VALUES
(1, 6, 'ere'),
(2, 27, 'tweede');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `wedstrijden`
--

CREATE TABLE `wedstrijden` (
  `WEDSTRIJDNR` int(11) NOT NULL,
  `TEAMNR` int(11) NOT NULL,
  `SPELERSNR` int(11) NOT NULL,
  `GEWONNEN` smallint(6) NOT NULL,
  `VERLOREN` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Gegevens worden geëxporteerd voor tabel `wedstrijden`
--

INSERT INTO `wedstrijden` (`WEDSTRIJDNR`, `TEAMNR`, `SPELERSNR`, `GEWONNEN`, `VERLOREN`) VALUES
(1, 1, 6, 3, 1),
(2, 1, 6, 2, 3),
(3, 1, 6, 3, 0),
(4, 1, 44, 3, 2),
(5, 1, 83, 0, 3),
(6, 1, 2, 1, 3),
(7, 1, 57, 3, 0),
(8, 1, 8, 0, 3),
(9, 2, 27, 3, 2),
(10, 2, 104, 3, 2),
(11, 2, 112, 2, 3),
(12, 2, 112, 1, 3),
(13, 2, 8, 0, 3);

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `bestuursleden`
--
ALTER TABLE `bestuursleden`
  ADD PRIMARY KEY (`SPELERSNR`,`BEGIN_DATUM`);

--
-- Indexen voor tabel `boetes`
--
ALTER TABLE `boetes`
  ADD PRIMARY KEY (`BETALINGSNR`);

--
-- Indexen voor tabel `spelers`
--
ALTER TABLE `spelers`
  ADD PRIMARY KEY (`SPELERSNR`);

--
-- Indexen voor tabel `teams`
--
ALTER TABLE `teams`
  ADD PRIMARY KEY (`TEAMNR`);

--
-- Indexen voor tabel `wedstrijden`
--
ALTER TABLE `wedstrijden`
  ADD PRIMARY KEY (`WEDSTRIJDNR`);
--
-- Database: `test`
--
CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `test`;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
