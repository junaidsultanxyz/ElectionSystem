CREATE DATABASE election_system;
use election_system;

CREATE TABLE election (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    starting_time TIME NOT NULL,
    ending_time TIME NOT NULL,
    starting_date DATE NOT NULL,
    ending_date DATE NOT NULL
);

CREATE TABLE voter (
    cnic varchar(13) PRIMARY KEY,
    name varchar(50) NOT NULL,
    age int NOT NULL,182.191.151.45
    division_id INT NOT NULL,
    FOREIGN KEY (division_id) REFERENCES division(id),
    password varchar(50) NOT NULL
);

CREATE TABLE province (
    code VARCHAR(5) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE city (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    province_code VARCHAR(5) NOT NULL,
    FOREIGN KEY (province_code) REFERENCES province(code)
);

CREATE TABLE division (
    id INT PRIMARY KEY,
    city_id INT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city(id)
);

CREATE TABLE party (
    code VARCHAR(5) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL UNIQUE,
    symbol TEXT,
    flag TEXT
);


CREATE TABLE vote (
    id INT PRIMARY KEY AUTO_INCREMENT,
    election_id INT NOT NULL,
    FOREIGN KEY(election_id) REFERENCES election(id),
    cnic VARCHAR(13) NOT NULL,
    FOREIGN KEY (cnic) REFERENCES voter(cnic),
    party_code VARCHAR(5) NOT NULL,
    FOREIGN KEY (party_code) REFERENCES party(code),
    vote_type ENUM('NA', 'PA') NOT NULL,
    vote_time TIMESTAMP NOT NULL
);