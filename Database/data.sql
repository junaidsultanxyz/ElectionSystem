CREATE DATABASE election_system;
use election_system;

CREATE TABLE voter (
    cnic varchar(13) PRIMARY KEY,
    name varchar(50),
    age int,
    gender varchar(10),
    division varchar(100),
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
    province_code VARCHAR(5),
    FOREIGN KEY (province_code) REFERENCES province(code)
);

CREATE TABLE party (
    id INT PRIMARY KEY,
    name VARCHAR(100)
);



CREATE TABLE vote (
    id INT PRIMARY KEY,
    cnic VARCHAR(13),
    FOREIGN KEY (cnic) REFERENCES voter(cnic),
    party_id INT,
    FOREIGN KEY (party_id) REFERENCES party(id),
    division_id INT,
    FOREIGN KEY (division_id) REFERENCES division(id)
);