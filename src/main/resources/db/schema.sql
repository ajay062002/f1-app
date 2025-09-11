-- Drop & create schema
CREATE DATABASE IF NOT EXISTS f1db CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE f1db;

-- Users (for real backend auth later)
CREATE TABLE IF NOT EXISTS app_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255) UNIQUE NOT NULL,
  display_name VARCHAR(120) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,         -- store bcrypt/argon2 here (UI currently uses localStorage)
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Teams
CREATE TABLE IF NOT EXISTS team (
  id INT PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  principal VARCHAR(80) NOT NULL,
  base VARCHAR(120) NOT NULL,
  power_unit VARCHAR(80) NOT NULL,
  logo_url VARCHAR(500),
  banner_url VARCHAR(500)
);

-- Drivers
CREATE TABLE IF NOT EXISTS driver (
  id INT PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  code VARCHAR(3) NOT NULL,
  number INT NOT NULL,
  nationality VARCHAR(40) NOT NULL,
  team_id INT NOT NULL,
  image_url VARCHAR(500),
  bio TEXT,
  stats_wins INT DEFAULT 0,
  stats_podiums INT DEFAULT 0,
  stats_poles INT DEFAULT 0,
  stats_championships INT DEFAULT 0,
  stats_points INT DEFAULT 0,
  stats_starts INT DEFAULT 0,
  CONSTRAINT fk_driver_team FOREIGN KEY (team_id) REFERENCES team(id)
);

-- Team lineup (redundant helper table if you prefer)
CREATE TABLE IF NOT EXISTS team_lineup (
  team_id INT PRIMARY KEY,
  driver1_id INT NOT NULL,
  driver2_id INT NOT NULL,
  CONSTRAINT fk_lineup_team FOREIGN KEY (team_id) REFERENCES team(id),
  CONSTRAINT fk_lineup_d1 FOREIGN KEY (driver1_id) REFERENCES driver(id),
  CONSTRAINT fk_lineup_d2 FOREIGN KEY (driver2_id) REFERENCES driver(id)
);

-- Circuits
CREATE TABLE IF NOT EXISTS circuit (
  id INT PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  city VARCHAR(80) NOT NULL,
  country VARCHAR(80) NOT NULL,
  laps INT NOT NULL,
  length_km DECIMAL(6,3) NOT NULL,
  race_km DECIMAL(6,3) NOT NULL,
  hero_url VARCHAR(500),
  track_path TEXT
);

-- Standings
CREATE TABLE IF NOT EXISTS standings_driver (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  season INT NOT NULL,
  position INT NOT NULL,
  driver_name VARCHAR(80) NOT NULL,
  team_name VARCHAR(80) NOT NULL,
  points INT NOT NULL,
  wins INT NOT NULL
);

CREATE TABLE IF NOT EXISTS standings_constructor (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  season INT NOT NULL,
  position INT NOT NULL,
  constructor_name VARCHAR(80) NOT NULL,
  points INT NOT NULL,
  wins INT NOT NULL
);
