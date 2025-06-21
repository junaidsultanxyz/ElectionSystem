CREATE DATABASE election_system;
use election_system;

CREATE TABLE voter (
    cnic varchar(13) PRIMARY KEY,
    name varchar(50),
    age int NOT NULL,
    division_id INT NOT NULL,
    password varchar(50) NOT NULL
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
    FOREIGN KEY (city_id) REFERENCES city(id)
);

CREATE TABLE party (
    code VARCHAR(5) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL UNIQUE,
    symbol TEXT
);



CREATE TABLE vote (
    id INT PRIMARY KEY,
    cnic VARCHAR(13) NOT NULL,
    FOREIGN KEY (cnic) REFERENCES voter(cnic),
    party_code VARCHAR(5) NOT NULL,
    FOREIGN KEY (party_code) REFERENCES party(code),
    vote_type ENUM('NA', 'PA') NOT NULL 
);