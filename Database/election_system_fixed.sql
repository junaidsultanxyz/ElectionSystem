-- phpMyAdmin SQL Dump (Fixed for Import)
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 11, 2025 at 12:00 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12
-- 
-- Fixed Version: Removed DEFINER clauses and improved compatibility

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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

DELIMITER $$

--
-- Procedures (DEFINER clauses removed for compatibility)
--

CREATE PROCEDURE `cast_vote` (IN `v_election_id` INT, IN `v_vote_time` TIME, IN `v_cnic` VARCHAR(13), IN `v_party_code` VARCHAR(5), IN `v_vote_type` ENUM('NA','PA'))
BEGIN
    INSERT INTO vote (election_id, vote_time, cnic, party_code, vote_type)
    VALUES (v_election_id, v_vote_time, v_cnic, v_party_code, v_vote_type);
END$$

CREATE PROCEDURE `country_result` ()
BEGIN
    SELECT 
        p.name AS party_name,
        p.code AS party_code,
        prov.name AS province_name,
        v.vote_type,
        COUNT(*) AS total_votes
    FROM vote v
    JOIN party p ON v.party_code = p.code
    JOIN voter vr ON v.cnic = vr.cnic
    JOIN division d ON vr.division_id = d.id
    JOIN city c ON d.city_id = c.id
    JOIN province prov ON c.province_code = prov.code
    GROUP BY p.name, p.code, prov.name, v.vote_type
    ORDER BY prov.name, p.name, v.vote_type;
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

CREATE PROCEDURE `delete_election` (IN `p_id` INT)
BEGIN
    DELETE FROM election WHERE id = p_id;
    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Election ID not found.';
    END IF;
END$$

CREATE PROCEDURE `delete_party` (IN `p_code` VARCHAR(5))
BEGIN
    DELETE FROM party WHERE code = p_code;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Party code not found. Deletion failed.';
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

CREATE PROCEDURE `getMnaVoteDetails` (IN `v_cnic` VARCHAR(13))
BEGIN
    SELECT 
        p.name AS party_name,
        p.code AS party_code,
        p.symbol,
        p.flag
    FROM vote v
    JOIN party p ON v.party_code = p.code
    WHERE v.vote_type = 'NA' AND v.cnic = v_cnic;
END$$

CREATE PROCEDURE `getMpaVoteDetails` (IN `v_cnic` VARCHAR(13))
BEGIN
    SELECT 
        p.name AS party_name,
        p.code AS party_code,
        p.symbol,
        p.flag
    FROM vote v
    JOIN party p ON v.party_code = p.code
    WHERE v.vote_type = 'PA' AND v.cnic = v_cnic;
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

CREATE PROCEDURE `getVoterDivision` (IN `v_cnic` VARCHAR(13), IN `v_password` VARCHAR(50))
BEGIN
    SELECT 
        CONCAT('NA-', division_id) AS division
    FROM voter
    WHERE cnic = v_cnic AND password = v_password;
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

CREATE PROCEDURE `mna_winners` ()
BEGIN
    CREATE TEMPORARY TABLE temp_party_votes AS
    SELECT 
        p.name AS party_name,
        p.code AS party_code,
        vr.division_id,
        COUNT(*) AS votes
    FROM vote v
    JOIN voter vr ON v.cnic = vr.cnic
    JOIN party p ON v.party_code = p.code
    WHERE v.vote_type = 'NA'
    GROUP BY p.code, p.name, vr.division_id;

    SELECT 
        tpv.party_name,
        tpv.party_code,
        CONCAT('NA-', tpv.division_id) AS division,
        tpv.votes
    FROM temp_party_votes tpv
    WHERE tpv.votes = (
        SELECT MAX(votes)
        FROM temp_party_votes tpv2
        WHERE tpv2.division_id = tpv.division_id
    )
    ORDER BY tpv.division_id;

    DROP TEMPORARY TABLE temp_party_votes;
END$$

CREATE PROCEDURE `mpa_winners` ()
BEGIN
    CREATE TEMPORARY TABLE temp_party_votes AS
    SELECT 
        p.name AS party_name,
        p.code AS party_code,
        vr.division_id,
        prov.code AS province_code,
        COUNT(*) AS votes
    FROM vote v
    JOIN voter vr ON v.cnic = vr.cnic
    JOIN division d ON vr.division_id = d.id
    JOIN city c ON d.city_id = c.id
    JOIN province prov ON c.province_code = prov.code
    JOIN party p ON v.party_code = p.code
    WHERE v.vote_type = 'PA'
    GROUP BY p.code, p.name, vr.division_id, prov.code;

    SELECT 
        tpv.party_name,
        tpv.party_code,
        CONCAT(tpv.province_code, '-', tpv.division_id) AS division,
        tpv.votes
    FROM temp_party_votes tpv
    WHERE tpv.votes = (
        SELECT MAX(votes)
        FROM temp_party_votes tpv2
        WHERE tpv2.division_id = tpv.division_id
    )
    ORDER BY tpv.division_id;

    DROP TEMPORARY TABLE temp_party_votes;
END$$

CREATE PROCEDURE `search_country_result` (IN `keyword` VARCHAR(100))
BEGIN
    SELECT 
        p.name AS party_name,
        p.code AS party_code,
        prov.name AS province_name,
        v.vote_type,
        COUNT(*) AS total_votes
    FROM vote v
    JOIN party p ON v.party_code = p.code
    JOIN voter vr ON v.cnic = vr.cnic
    JOIN division d ON vr.division_id = d.id
    JOIN city c ON d.city_id = c.id
    JOIN province prov ON c.province_code = prov.code
    WHERE
        p.name LIKE CONCAT('%', keyword, '%') OR
        p.code LIKE CONCAT('%', keyword, '%') OR
        prov.name LIKE CONCAT('%', keyword, '%') OR
        v.vote_type LIKE CONCAT('%', keyword, '%')
    GROUP BY p.name, p.code, prov.name, v.vote_type
    ORDER BY prov.name, p.name, v.vote_type;
END$$

CREATE PROCEDURE `search_current_votes` (IN `keyword` VARCHAR(100))
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
    WHERE 
        vr.name LIKE CONCAT('%', keyword, '%') OR
        vr.cnic LIKE CONCAT('%', keyword, '%') OR
        v.party_code LIKE CONCAT('%', keyword, '%') OR
        v.vote_type LIKE CONCAT('%', keyword, '%') OR
        CONCAT('NA-', vr.division_id) LIKE CONCAT('%', keyword, '%') OR
        CONCAT(p.code, '-', vr.division_id) LIKE CONCAT('%', keyword, '%') OR
        DATE_FORMAT(v.vote_time, '%Y-%m-%d %H:%i:%s') LIKE CONCAT('%', keyword, '%')
    ORDER BY v.vote_time DESC;
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

CREATE PROCEDURE `search_mna_winners` (IN `keyword` VARCHAR(100))
BEGIN
    CREATE TEMPORARY TABLE temp_party_votes AS
    SELECT 
        p.name AS party_name,
        p.code AS party_code,
        vr.division_id,
        COUNT(*) AS votes
    FROM vote v
    JOIN voter vr ON v.cnic = vr.cnic
    JOIN party p ON v.party_code = p.code
    WHERE v.vote_type = 'NA'
    GROUP BY p.code, p.name, vr.division_id;

    SELECT 
        tpv.party_name,
        tpv.party_code,
        CONCAT('NA-', tpv.division_id) AS division,
        tpv.votes
    FROM temp_party_votes tpv
    WHERE tpv.votes = (
        SELECT MAX(votes)
        FROM temp_party_votes tpv2
        WHERE tpv2.division_id = tpv.division_id
    )
    AND (
        tpv.party_name LIKE CONCAT('%', keyword, '%') OR
        tpv.party_code LIKE CONCAT('%', keyword, '%') OR
        CONCAT('NA-', tpv.division_id) LIKE CONCAT('%', keyword, '%') OR
        CAST(tpv.votes AS CHAR) LIKE CONCAT('%', keyword, '%')
    )
    ORDER BY tpv.division_id;

    DROP TEMPORARY TABLE temp_party_votes;
END$$

CREATE PROCEDURE `search_mpa_winners` (IN `keyword` VARCHAR(100))
BEGIN
    CREATE TEMPORARY TABLE temp_party_votes AS
    SELECT 
        p.name AS party_name,
        p.code AS party_code,
        vr.division_id,
        prov.code AS province_code,
        COUNT(*) AS votes
    FROM vote v
    JOIN voter vr ON v.cnic = vr.cnic
    JOIN division d ON vr.division_id = d.id
    JOIN city c ON d.city_id = c.id
    JOIN province prov ON c.province_code = prov.code
    JOIN party p ON v.party_code = p.code
    WHERE v.vote_type = 'PA'
    GROUP BY p.code, p.name, vr.division_id, prov.code;

    SELECT 
        tpv.party_name,
        tpv.party_code,
        CONCAT(tpv.province_code, '-', tpv.division_id) AS division,
        tpv.votes
    FROM temp_party_votes tpv
    WHERE tpv.votes = (
        SELECT MAX(votes)
        FROM temp_party_votes tpv2
        WHERE tpv2.division_id = tpv.division_id
    )
    AND (
        tpv.party_name LIKE CONCAT('%', keyword, '%') OR
        tpv.party_code LIKE CONCAT('%', keyword, '%') OR
        CONCAT(tpv.province_code, '-', tpv.division_id) LIKE CONCAT('%', keyword, '%') OR
        CAST(tpv.votes AS CHAR) LIKE CONCAT('%', keyword, '%')
    )
    ORDER BY tpv.division_id;

    DROP TEMPORARY TABLE temp_party_votes;
END$$

CREATE PROCEDURE `search_total_voters` (IN `keyword` VARCHAR(100))
BEGIN
    SELECT 
        vr.cnic,
        vr.name,
        vr.age,
        vr.division_id AS division
    FROM voter vr
    JOIN division d ON vr.division_id = d.id
    JOIN city c ON d.city_id = c.id
    JOIN province p ON c.province_code = p.code
    WHERE 
        vr.cnic LIKE CONCAT('%', keyword, '%') OR
        vr.name LIKE CONCAT('%', keyword, '%') OR
        CAST(vr.age AS CHAR) LIKE CONCAT('%', keyword, '%') OR
        CAST(vr.division_id AS CHAR) LIKE CONCAT('%', keyword, '%') OR
        c.name LIKE CONCAT('%', keyword, '%') OR
        p.name LIKE CONCAT('%', keyword, '%')
    ORDER BY vr.name;
END$$

CREATE PROCEDURE `update_election` (IN `p_id` INT, IN `p_name` VARCHAR(100), IN `p_starting_time` TIME, IN `p_ending_time` TIME, IN `p_starting_date` DATE, IN `p_ending_date` DATE)
BEGIN
    UPDATE election 
    SET name = p_name, 
        starting_time = p_starting_time, 
        ending_time = p_ending_time, 
        starting_date = p_starting_date, 
        ending_date = p_ending_date 
    WHERE id = p_id;
    
    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Election ID not found. Update failed.';
    END IF;
END$$

CREATE PROCEDURE `update_party` (IN `p_code` VARCHAR(5), IN `p_name` VARCHAR(100), IN `p_symbol` TEXT, IN `p_flag` TEXT)
BEGIN
    UPDATE party 
    SET name = p_name, symbol = p_symbol, flag = p_flag 
    WHERE code = p_code;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Party code not found. Update failed.';
    END IF;
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

CREATE PROCEDURE `your_area_result` (IN `_division_id` INT)
BEGIN
    SELECT 
        p.code AS party_code,
        p.flag,
        COUNT(*) AS total_votes
    FROM vote v
    JOIN voter vr ON v.cnic = vr.cnic
    JOIN party p ON v.party_code = p.code
    WHERE vr.division_id = _division_id
    GROUP BY p.code, p.flag
    ORDER BY total_votes DESC;
END$$

DELIMITER ;
