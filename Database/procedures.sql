use election_system;


-- ************************************************************
-- ELECTION
-- ************************************************************

DELIMITER $$
CREATE PROCEDURE insert_election (
    IN p_name VARCHAR(100),
    IN p_starting_time TIMESTAMP,
    IN p_ending_time TIMESTAMP
)
BEGIN
    INSERT INTO election (name, starting_time, ending_time)
    VALUES (p_name, p_starting_time, p_ending_time);
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE delete_election (
    IN p_id INT
)
BEGIN
    DELETE FROM election WHERE id = p_id;
END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE update_election (
    IN p_name VARCHAR(100),
    IN p_starting_time TIMESTAMP,
    IN p_ending_time TIMESTAMP
)
BEGIN
    UPDATE election
    SET name = p_name,
        starting_time = p_starting_time,
        ending_time = p_ending_time
    WHERE id = p_id;
END$$
DELIMITER ; 



DELIMITER $$
CREATE PROCEDURE search_election (
    IN p_keyword VARCHAR(100)
)
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



DELIMITER $$
CREATE PROCEDURE get_all_elections()
BEGIN
    SELECT * FROM election;
END$$
DELIMITER ;


-- ************************************************************
-- PARTY
-- ************************************************************

DELIMITER $$
CREATE PROCEDURE insert_party (
    IN p_code VARCHAR(5),
    IN p_name VARCHAR(100),
    IN p_symbol TEXT,
    IN p_flag TEXT
)
BEGIN
    INSERT INTO party (code, name, symbol, flag)
    VALUES (p_code, p_name, p_symbol, p_flag);
END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE delete_party (
    IN p_code VARCHAR(5)
)
BEGIN
    DELETE FROM party WHERE code = p_code;
END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE search_party (
    IN p_keyword VARCHAR(100)
)
BEGIN
    SELECT *
    FROM party
    WHERE code LIKE CONCAT('%', p_keyword, '%')
       OR name LIKE CONCAT('%', p_keyword, '%')
       OR symbol LIKE CONCAT('%', p_keyword, '%')
       OR flag LIKE CONCAT('%', p_keyword, '%');
END$$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE get_all_parties()
BEGIN
    SELECT * FROM party;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE update_party (
    IN p_code VARCHAR(5),
    IN p_symbol TEXT,
    IN p_flag TEXT
)
BEGIN
    UPDATE party
    SET symbol = p_symbol,
        flag = p_flag
    WHERE code = p_code;
END$$
DELIMITER ;


-- ************************************************************
-- RESULTS
-- ************************************************************

DELIMITER $$
CREATE PROCEDURE country_result()
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
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE current_votes()
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
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE mna_winners()
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
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE mpa_winners()
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
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE your_area_result(IN _division_id INT)
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


-- ************************************************************
-- VOTING
-- ************************************************************

DELIMITER $$
CREATE PROCEDURE cast_vote (
    IN v_election_id INT,
    IN v_vote_time TIMESTAMP,
    IN v_cnic VARCHAR(13),
    IN v_party_code VARCHAR(5),
    IN v_vote_type ENUM('NA', 'PA')
)
BEGIN
   INSERT INTO vote (election_id, vote_time, cnic, party_code, vote_type)
   VALUES (v_election_id, v_vote_time, v_cnic, v_party_code, v_vote_type);
END$$
DELIMITER ;


-- ************************************************************
-- AUTHENTICATION
-- ************************************************************

DELIMITER $$
CREATE PROCEDURE validate_voter_login (
    IN p_cnic VARCHAR(13),
    IN p_password VARCHAR(50),
    OUT p_found BOOLEAN,
    OUT p_name VARCHAR(50),
    OUT p_division_id INT
)
BEGIN
    DECLARE v_count INT;

    SELECT COUNT(*) INTO v_count
    FROM voter
    WHERE cnic = p_cnic AND password = p_password;

    IF v_count = 1 THEN
        SET p_found = TRUE;

        SELECT name, division_id
        INTO p_name, p_division_id
        FROM voter
        WHERE cnic = p_cnic;
    ELSE
        SET p_found = FALSE;
        SET p_name = NULL;
        SET p_division_id = NULL;
    END IF;
END$$
DELIMITER ;


-- ************************************************************
-- VOTER
-- ************************************************************

DELIMITER $$
CREATE PROCEDURE getTotalVoters()
BEGIN
    SELECT 
        vr.cnic,
        vr.name,
        vr.age,
        vr.division_id
    FROM voter vr
    JOIN division d ON vr.division_id = d.id
    JOIN city c ON d.city_id = c.id
    JOIN province p ON c.province_code = p.code;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE search_total_voters()
BEGIN
    SELECT 
        vr.cnic,
        vr.name,
        vr.age,
        CONCAT(p.code, '-', vr.division_id) AS division
    FROM voter vr
    JOIN division d ON vr.division_id = d.id
    JOIN city c ON d.city_id = c.id
    JOIN province p ON c.province_code = p.code
    WHERE
        vr.cnic LIKE CONCAT('%', keyword, '%') OR
        vr.name LIKE CONCAT('%', keyword, '%') OR
        CAST(vr.age AS CHAR) LIKE CONCAT('%', keyword, '%') OR
        CONCAT(p.code, '-', vr.division_id) LIKE CONCAT('%', keyword, '%');
END$$
DELIMITER ;

-- DELIMITER $$
-- CREATE PROCEDURE getTotalVotersWithDivision()
-- BEGIN
--     SELECT 
--         vr.cnic,
--         vr.name,
--         vr.age,
--         CONCAT(p.code, '-', vr.division_id) AS division
--     FROM voter vr
--     JOIN division d ON vr.division_id = d.id
--     JOIN city c ON d.city_id = c.id
--     JOIN province p ON c.province_code = p.code;
-- END$$
-- DELIMITER ;


DELIMITER $$
CREATE PROCEDURE getVoterDivision(
    IN v_cnic VARCHAR(13),
    IN v_password VARCHAR(50)
)
BEGIN
    SELECT 
        CONCAT('NA-', division_id) AS division
    FROM voter
    WHERE cnic = v_cnic AND password = v_password;
END$$
DELIMITER ;


-- ************************************************************
-- RESULTS
-- ************************************************************

DELIMITER $$
CREATE PROCEDURE getMnaVoteDetails(IN v_cnic VARCHAR(13))
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
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE getMpaVoteDetails(IN v_cnic VARCHAR(13))
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
DELIMITER ;