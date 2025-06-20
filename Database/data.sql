CREATE DATABASE election_system;
use election_system;

CREATE TABLE voter (
    cnic varchar(13) PRIMARY KEY,
    name varchar(50),
    age int,
    division_id INT NOT NULL,
    password varchar(50)
);

CREATE TABLE province (
    code VARCHAR(5) PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE city (
    id VARCHAR(5) PRIMARY KEY,
    name VARCHAR(100),
    province_code VARCHAR(5),
    FOREIGN KEY (province_code) REFERENCES province(code)
);

CREATE TABLE division (
    id INT PRIMARY KEY,
    city_id VARCHAR(5),
    FOREIGN KEY (city_id) REFERENCES city(id),
);

CREATE TABLE party (
    code VARCHAR(5) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL UNIQUE
);



CREATE TABLE vote (
    id INT PRIMARY KEY,
    cnic VARCHAR(13) NOT NULL,
    FOREIGN KEY (cnic) REFERENCES voter(cnic),
    party_code VARCHAR(5) NOT NULL,
    FOREIGN KEY (party_code) REFERENCES party(code),
    vote_type ENUM('NA', 'PA') NOT NULL;    
);



-- https://www.figma.com/proto/VQqUgIU9P9UPmPwsbEYTAu/tradeyourself-App-ref?node-id=167-1883&t=Wdd8aKCpUWXYtgG5-0&scaling=contain&content-scaling=fixed&page-id=68%3A2











NA (WHOLE COUNTRY)
1-250


PUNJAB (PP) (PROVINCE SPECIFIC)
1-100


SINDH (PS) (PROVINCE SPECIFIC)
1-80













