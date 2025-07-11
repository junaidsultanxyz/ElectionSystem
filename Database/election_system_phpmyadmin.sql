-- phpMyAdmin SQL Dump (Optimized for Import)
-- Pakistan Election System Database
-- Fixed Version: Compatible with phpMyAdmin import
-- Generation Time: Jul 11, 2025

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `election_system`
--
CREATE DATABASE IF NOT EXISTS `election_system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `election_system`;

-- --------------------------------------------------------

--
-- Disable foreign key checks for import
--
SET FOREIGN_KEY_CHECKS = 0;

--
-- Drop existing procedures if they exist
--
DROP PROCEDURE IF EXISTS `cast_vote`;
DROP PROCEDURE IF EXISTS `country_result`;
DROP PROCEDURE IF EXISTS `current_votes`;
DROP PROCEDURE IF EXISTS `delete_election`;
DROP PROCEDURE IF EXISTS `delete_party`;
DROP PROCEDURE IF EXISTS `display_all_elections`;
DROP PROCEDURE IF EXISTS `display_all_parties`;
DROP PROCEDURE IF EXISTS `getMnaVoteDetails`;
DROP PROCEDURE IF EXISTS `getMpaVoteDetails`;
DROP PROCEDURE IF EXISTS `getTotalVoters`;
DROP PROCEDURE IF EXISTS `getVoterDivision`;
DROP PROCEDURE IF EXISTS `insert_election`;
DROP PROCEDURE IF EXISTS `insert_party`;
DROP PROCEDURE IF EXISTS `mna_winners`;
DROP PROCEDURE IF EXISTS `mpa_winners`;
DROP PROCEDURE IF EXISTS `search_country_result`;
DROP PROCEDURE IF EXISTS `search_current_votes`;
DROP PROCEDURE IF EXISTS `search_election`;
DROP PROCEDURE IF EXISTS `search_mna_winners`;
DROP PROCEDURE IF EXISTS `search_mpa_winners`;
DROP PROCEDURE IF EXISTS `update_election`;
DROP PROCEDURE IF EXISTS `update_party`;
DROP PROCEDURE IF EXISTS `validate_voter_login`;
DROP PROCEDURE IF EXISTS `your_area_result`;

--
-- Drop existing tables if they exist
--
DROP TABLE IF EXISTS `vote`;
DROP TABLE IF EXISTS `voter`;
DROP TABLE IF EXISTS `election`;
DROP TABLE IF EXISTS `party`;
DROP TABLE IF EXISTS `division`;
DROP TABLE IF EXISTS `city`;
DROP TABLE IF EXISTS `province`;

-- --------------------------------------------------------

--
-- Table structure for table `province`
--

CREATE TABLE `province` (
  `code` varchar(5) NOT NULL,
  `name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `province`
--

INSERT INTO `province` (`code`, `name`) VALUES
('KPK', 'Khyber Pakhtunkhwa'),
('PB', 'Balochistan'),
('PP', 'Punjab'),
('PS', 'Sindh');

-- --------------------------------------------------------

--
-- Table structure for table `city`
--

CREATE TABLE `city` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `province_code` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `city`
--

INSERT INTO `city` (`id`, `name`, `province_code`) VALUES
(1, 'Lahore', 'PP'),
(2, 'Faisalabad', 'PP'),
(3, 'Rawalpindi', 'PP'),
(4, 'Gujranwala', 'PP'),
(5, 'Multan', 'PP'),
(6, 'Bahawalpur', 'PP'),
(7, 'Sargodha', 'PP'),
(8, 'Sialkot', 'PP'),
(9, 'Sheikhupura', 'PP'),
(10, 'Rahim Yar Khan', 'PP'),
(11, 'Jhang', 'PP'),
(12, 'Dera Ghazi Khan', 'PP'),
(13, 'Gujrat', 'PP'),
(14, 'Sahiwal', 'PP'),
(15, 'Wah Cantonment', 'PP'),
(16, 'Karachi', 'PS'),
(17, 'Hyderabad', 'PS'),
(18, 'Sukkur', 'PS'),
(19, 'Larkana', 'PS'),
(20, 'Benazirabad', 'PS'),
(21, 'Mirpur Khas', 'PS'),
(22, 'Jacobabad', 'PS'),
(23, 'Shikarpur', 'PS'),
(24, 'Khairpur', 'PS'),
(25, 'Dadu', 'PS'),
(26, 'Tando Adam Khan', 'PS'),
(27, 'Tando Allahyar', 'PS'),
(28, 'Umerkot', 'PS'),
(29, 'Moro', 'PS'),
(30, 'Kotri', 'PS'),
(31, 'Badin', 'PS'),
(32, 'Quetta', 'PB'),
(33, 'Turbat', 'PB'),
(34, 'Khuzdar', 'PB'),
(35, 'Hub', 'PB'),
(36, 'Chaman', 'PB'),
(37, 'Dera Murad Jamali', 'PB'),
(38, 'Gwadar', 'PB'),
(39, 'Dera Allah Yar', 'PB'),
(40, 'Usta Mohammad', 'PB'),
(41, 'Sui', 'PB'),
(42, 'Sibi', 'PB'),
(43, 'Loralai', 'PB'),
(44, 'Nushki', 'PB'),
(45, 'Zhob', 'PB'),
(46, 'Kharan', 'PB'),
(47, 'Pasni', 'PB'),
(48, 'Dalbandin', 'PB'),
(49, 'Ziarat', 'PB'),
(50, 'Peshawar', 'KPK'),
(51, 'Mardan', 'KPK'),
(52, 'Mingora', 'KPK'),
(53, 'Kohat', 'KPK'),
(54, 'Dera Ismail Khan', 'KPK'),
(55, 'Abbottabad', 'KPK'),
(56, 'Mansehra', 'KPK'),
(57, 'Nowshera', 'KPK'),
(58, 'Swabi', 'KPK'),
(59, 'Batkhela', 'KPK'),
(60, 'Battagram', 'KPK'),
(61, 'Bannu', 'KPK'),
(62, 'Chitral', 'KPK'),
(63, 'Haripur', 'KPK'),
(64, 'Lakki Marwat', 'KPK'),
(65, 'Karak', 'KPK'),
(66, 'Timargara', 'KPK'),
(67, 'Tank', 'KPK');

-- --------------------------------------------------------

--
-- Note: Division data is extensive (350 entries)
-- Creating table structure and inserting first few entries as sample
--

CREATE TABLE `division` (
  `id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Sample division data (first 50 entries)
--

INSERT INTO `division` (`id`, `city_id`) VALUES
(1, 16), (2, 31), (3, 37), (4, 6), (5, 42), (6, 25), (7, 2), (8, 5), (9, 14), (10, 62),
(11, 1), (12, 61), (13, 66), (14, 22), (15, 46), (16, 42), (17, 51), (18, 61), (19, 19), (20, 19),
(21, 52), (22, 3), (23, 12), (24, 26), (25, 18), (26, 24), (27, 3), (28, 6), (29, 16), (30, 48),
(31, 33), (32, 36), (33, 60), (34, 32), (35, 30), (36, 11), (37, 54), (38, 25), (39, 28), (40, 4),
(41, 49), (42, 53), (43, 39), (44, 1), (45, 18), (46, 48), (47, 45), (48, 52), (49, 13), (50, 15);

-- --------------------------------------------------------

--
-- Table structure for table `election`
--

CREATE TABLE `election` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `starting_time` time NOT NULL,
  `ending_time` time NOT NULL,
  `starting_date` date NOT NULL,
  `ending_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `election`
--

INSERT INTO `election` (`id`, `name`, `starting_time`, `ending_time`, `starting_date`, `ending_date`) VALUES
(328123321, 'General Election 2025', '00:00:00', '00:00:00', '2025-07-05', '2025-07-12');

-- --------------------------------------------------------

--
-- Table structure for table `party`
--

CREATE TABLE `party` (
  `code` varchar(5) NOT NULL,
  `name` varchar(100) NOT NULL,
  `symbol` text DEFAULT NULL,
  `flag` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `party`
--

INSERT INTO `party` (`code`, `name`, `symbol`, `flag`) VALUES
('ANP', 'Awami National Party', 'images/symbols/anp.png', 'images/flags/anp_flag.png'),
('BNP', 'Balochistan National Party', 'images/symbols/bnp.png', 'images/flags/bnp_flag.png'),
('GDA', 'Grand Democratic Alliance', 'images/symbols/gda.png', 'images/flags/gda_flag.png'),
('JI', 'Jamaat-e-Islami', 'images/symbols/ji.png', 'images/flags/ji_flag.png'),
('JUIF', 'Jamiat Ulema-e-Islam (F)', 'images/symbols/juif.png', 'images/flags/juif_flag.png'),
('MQM', 'Muttahida Qaumi Movement', 'images/symbols/mqm.png', 'images/flags/mqm_flag.png'),
('PMLN', 'Pakistan Muslim League (N)', 'images/symbols/pmln.png', 'images/flags/pmln_flag.png'),
('PPP', 'Pakistan Peoples Party', 'images/symbols/ppp.png', 'images/flags/ppp_flag.png'),
('PTI', 'Pakistan Tehreek-e-Insaf', 'images/symbols/pti.png', 'images/flags/pti_flag.png'),
('TLP', 'Tehreek-e-Labbaik Pakistan', 'images/symbols/tlp.png', 'images/flags/tlp_flag.png');

-- --------------------------------------------------------

--
-- Table structure for table `voter`
--

CREATE TABLE `voter` (
  `cnic` varchar(13) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `division_id` int(11) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Sample voter data (first 10 entries)
--

INSERT INTO `voter` (`cnic`, `name`, `age`, `division_id`, `password`) VALUES
('3520200000001', 'Ahmed Hussain', 47, 204, 'o0S0eJkD'),
('3520200000002', 'Hina Idrees', 54, 130, 'pPQ8xGoPa'),
('3520200000003', 'Waleed Javed', 76, 165, 'eWktSPcu'),
('3520200000004', 'Nashit Shah', 33, 305, 'LpQXQJH'),
('3520200000005', 'Lubna Farooq', 52, 217, 'WiLGUgb'),
('4230100000001', 'Shehryar Habib', 32, 274, 'GEP9zfaA'),
('4230100000002', 'Maham Waqas', 68, 156, '4KKCi9'),
('4230100000003', 'Fahad Hanif', 52, 349, 'BUOFPsQ'),
('4230100000004', 'Sadia Masood', 31, 27, 'G9jTyRv'),
('4230100000005', 'Zeeshan Kazmi', 29, 146, 'Rd3l2CmW');

-- --------------------------------------------------------

--
-- Table structure for table `vote`
--

CREATE TABLE `vote` (
  `id` int(11) NOT NULL,
  `election_id` int(11) NOT NULL,
  `cnic` varchar(13) NOT NULL,
  `party_code` varchar(5) NOT NULL,
  `vote_type` enum('NA','PA') NOT NULL,
  `vote_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Sample vote data (first 10 entries)
--

INSERT INTO `vote` (`id`, `election_id`, `cnic`, `party_code`, `vote_type`, `vote_time`) VALUES
(22066, 328123321, '4230100000001', 'MQM', 'NA', '2025-07-10 19:52:23'),
(22067, 328123321, '4230100000001', 'TLP', 'PA', '2025-07-06 14:50:21'),
(22068, 328123321, '4230100000002', 'MQM', 'PA', '2025-07-11 07:12:37'),
(22069, 328123321, '4230100000003', 'JUIF', 'PA', '2025-07-08 01:31:06'),
(22070, 328123321, '4230100000004', 'PPP', 'NA', '2025-07-04 21:27:03'),
(22071, 328123321, '4230100000004', 'JUIF', 'PA', '2025-07-06 09:27:49'),
(22072, 328123321, '4230100000005', 'GDA', 'NA', '2025-07-09 10:03:13'),
(22073, 328123321, '4230100000005', 'GDA', 'PA', '2025-07-10 05:57:01'),
(22074, 328123321, '3520200000001', 'PMLN', 'NA', '2025-07-09 10:21:44'),
(22075, 328123321, '3520200000002', 'GDA', 'NA', '2025-07-06 03:26:59');

-- --------------------------------------------------------

--
-- Indexes for dumped tables
--

--
-- Indexes for table `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`id`),
  ADD KEY `province_code` (`province_code`);

--
-- Indexes for table `division`
--
ALTER TABLE `division`
  ADD PRIMARY KEY (`id`),
  ADD KEY `city_id` (`city_id`);

--
-- Indexes for table `election`
--
ALTER TABLE `election`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `party`
--
ALTER TABLE `party`
  ADD PRIMARY KEY (`code`),
  ADD UNIQUE KEY `code` (`code`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `province`
--
ALTER TABLE `province`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `vote`
--
ALTER TABLE `vote`
  ADD PRIMARY KEY (`id`),
  ADD KEY `election_id` (`election_id`),
  ADD KEY `cnic` (`cnic`),
  ADD KEY `party_code` (`party_code`);

--
-- Indexes for table `voter`
--
ALTER TABLE `voter`
  ADD PRIMARY KEY (`cnic`),
  ADD KEY `division_id` (`division_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `vote`
--
ALTER TABLE `vote`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24059;

-- --------------------------------------------------------

--
-- Stored Procedures (DEFINER removed for phpMyAdmin compatibility)
--

DELIMITER $$

CREATE PROCEDURE `cast_vote` (IN `v_election_id` INT, IN `v_vote_time` TIME, IN `v_cnic` VARCHAR(13), IN `v_party_code` VARCHAR(5), IN `v_vote_type` ENUM('NA','PA'))
BEGIN
    INSERT INTO vote (election_id, vote_time, cnic, party_code, vote_type)
    VALUES (v_election_id, v_vote_time, v_cnic, v_party_code, v_vote_type);
END$$

CREATE PROCEDURE `validate_voter_login` (IN `v_cnic` VARCHAR(13), IN `v_password` VARCHAR(50), OUT `found` BOOLEAN, OUT `voter_name` VARCHAR(50), OUT `division_id` INT)
BEGIN
    DECLARE voter_count INT DEFAULT 0;
    
    SELECT COUNT(*), name, division_id 
    INTO voter_count, voter_name, division_id
    FROM voter 
    WHERE cnic = v_cnic AND password = v_password;
    
    IF voter_count > 0 THEN
        SET found = TRUE;
    ELSE
        SET found = FALSE;
        SET voter_name = NULL;
        SET division_id = NULL;
    END IF;
END$$

CREATE PROCEDURE `display_all_elections` ()
BEGIN
    SELECT * FROM election;
END$$

CREATE PROCEDURE `display_all_parties` ()
BEGIN
    SELECT * FROM party;
END$$

CREATE PROCEDURE `current_votes` ()
BEGIN
    SELECT 
        vr.name AS voter_name,
        vr.cnic AS voter_cnic,
        v.party_code,
        CASE 
            WHEN v.vote_type = 'NA' THEN CONCAT('NA-', vr.division_id)
            WHEN v.vote_type = 'PA' THEN CONCAT(p.code, '-', vr.division_id)
        END AS division,
        v.vote_type,
        v.vote_time AS vote_time
    FROM vote v
    JOIN voter vr ON v.cnic = vr.cnic
    JOIN division d ON vr.division_id = d.id
    JOIN city c ON d.city_id = c.id
    JOIN province p ON c.province_code = p.code
    ORDER BY v.vote_time DESC;
END$$

CREATE PROCEDURE `getTotalVoters` ()
BEGIN
    SELECT 
        vr.cnic,
        vr.name,
        vr.age,
        vr.division_id,
        CONCAT('NA-', division_id) AS division
    FROM voter vr
    JOIN division d ON vr.division_id = d.id
    JOIN city c ON d.city_id = c.id
    JOIN province p ON c.province_code = p.code;
END$$

CREATE PROCEDURE `insert_election` (IN `p_id` INT, IN `p_name` VARCHAR(100), IN `p_starting_time` TIME, IN `p_ending_time` TIME, IN `p_starting_date` DATE, IN `p_ending_date` DATE)
BEGIN
    INSERT INTO election (id, name, starting_time, ending_time, starting_date, ending_date)
    VALUES (p_id, p_name, p_starting_time, p_ending_time, p_starting_date, p_ending_date);
END$$

CREATE PROCEDURE `insert_party` (IN `p_code` VARCHAR(5), IN `p_name` VARCHAR(100), IN `p_symbol` TEXT, IN `p_flag` TEXT)
BEGIN
    INSERT INTO party (code, name, symbol, flag)
    VALUES (p_code, p_name, p_symbol, p_flag);
END$$

CREATE PROCEDURE `update_election` (IN `p_id` INT, IN `p_name` VARCHAR(100), IN `p_starting_time` TIME, IN `p_ending_time` TIME, IN `p_starting_date` DATE, IN `p_ending_date` DATE)
BEGIN
    UPDATE election 
    SET name = p_name, starting_time = p_starting_time, ending_time = p_ending_time, 
        starting_date = p_starting_date, ending_date = p_ending_date
    WHERE id = p_id;
    
    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Election ID not found. Update failed.';
    END IF;
END$$

CREATE PROCEDURE `delete_election` (IN `p_id` INT)
BEGIN
    DELETE FROM election WHERE id = p_id;
    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Election ID not found.';
    END IF;
END$$

CREATE PROCEDURE `search_election` (IN `p_keyword` VARCHAR(100))
BEGIN
    SELECT *
    FROM election
    WHERE CAST(id AS CHAR) LIKE CONCAT('%', p_keyword, '%')
       OR name LIKE CONCAT('%', p_keyword, '%')
       OR CAST(starting_time AS CHAR) LIKE CONCAT('%', p_keyword, '%')
       OR CAST(ending_time AS CHAR) LIKE CONCAT('%', p_keyword, '%')
       OR CAST(starting_date AS CHAR) LIKE CONCAT('%', p_keyword, '%')
       OR CAST(ending_date AS CHAR) LIKE CONCAT('%', p_keyword, '%');
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Constraints for dumped tables (Re-enable foreign key checks)
--

SET FOREIGN_KEY_CHECKS = 1;

--
-- Constraints for table `city`
--
ALTER TABLE `city`
  ADD CONSTRAINT `city_ibfk_1` FOREIGN KEY (`province_code`) REFERENCES `province` (`code`);

--
-- Constraints for table `division`
--
ALTER TABLE `division`
  ADD CONSTRAINT `division_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`);

--
-- Constraints for table `vote`
--
ALTER TABLE `vote`
  ADD CONSTRAINT `vote_ibfk_1` FOREIGN KEY (`election_id`) REFERENCES `election` (`id`),
  ADD CONSTRAINT `vote_ibfk_2` FOREIGN KEY (`cnic`) REFERENCES `voter` (`cnic`),
  ADD CONSTRAINT `vote_ibfk_3` FOREIGN KEY (`party_code`) REFERENCES `party` (`code`);

--
-- Constraints for table `voter`
--
ALTER TABLE `voter`
  ADD CONSTRAINT `voter_ibfk_1` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
