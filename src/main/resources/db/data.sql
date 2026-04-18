/* ===========================
   TEAMS (2024/25)
   =========================== */
INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Red Bull Racing','Christian Horner','Milton Keynes, UK','Honda RBPT','https://logos-world.net/wp-content/uploads/2020/05/Red-Bull-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Ferrari','Frédéric Vasseur','Maranello, IT','Ferrari','https://1000logos.net/wp-content/uploads/2018/03/Ferrari-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Mercedes','Toto Wolff','Brackley, UK','Mercedes','https://1000logos.net/wp-content/uploads/2016/10/Mercedes-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('McLaren','Andrea Stella','Woking, UK','Mercedes','https://1000logos.net/wp-content/uploads/2017/02/McLaren-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Aston Martin','Mike Krack','Silverstone, UK','Mercedes','https://1000logos.net/wp-content/uploads/2017/05/Aston-Martin-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Alpine','Bruno Famin','Enstone, UK / Viry, FR','Renault','https://1000logos.net/wp-content/uploads/2018/04/Renault-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Williams','James Vowles','Grove, UK','Mercedes','https://1000logos.net/wp-content/uploads/2017/05/Williams-logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Sauber (Stake)','Alessandro Alunni Bravi','Hinwil, CH','Ferrari','https://1000logos.net/wp-content/uploads/2016/10/Sauber-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('RB (Visa Cash App RB)','Laurent Mekies','Faenza, IT','Honda RBPT','https://1000logos.net/wp-content/uploads/2020/09/AlphaTauri-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Haas','Ayao Komatsu','Kannapolis, USA','Ferrari','https://1000logos.net/wp-content/uploads/2017/03/HAAS-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

/* ===========================
   DRIVERS
   =========================== */
INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Max','Verstappen','VER',1,'Dutch','Red Bull Racing','https://media.formula1.com/d_driver_f1_2023/Verstappen.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Sergio','Perez','PER',11,'Mexican','Red Bull Racing','https://media.formula1.com/d_driver_f1_2023/Perez.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Charles','Leclerc','LEC',16,'Monegasque','Ferrari','https://media.formula1.com/d_driver_f1_2023/Leclerc.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Carlos','Sainz','SAI',55,'Spanish','Ferrari','https://media.formula1.com/d_driver_f1_2023/Sainz.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Lewis','Hamilton','HAM',44,'British','Mercedes','https://media.formula1.com/d_driver_f1_2023/Hamilton.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('George','Russell','RUS',63,'British','Mercedes','https://media.formula1.com/d_driver_f1_2023/Russell.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Lando','Norris','NOR',4,'British','McLaren','https://media.formula1.com/d_driver_f1_2023/Norris.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Oscar','Piastri','PIA',81,'Australian','McLaren','https://media.formula1.com/d_driver_f1_2023/Piastri.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Fernando','Alonso','ALO',14,'Spanish','Aston Martin','https://media.formula1.com/d_driver_f1_2023/Alonso.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Lance','Stroll','STR',18,'Canadian','Aston Martin','https://media.formula1.com/d_driver_f1_2023/Stroll.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

/* (… continue with rest of drivers as in your file …) */

/* ===========================
   CIRCUITS (24 full set)
   =========================== */
-- (use the cleaned 24 circuit INSERTs from your last paste — I won’t repeat all here due to length)

/* ===========================
   DRIVER STATS (sample 2022–24)
   =========================== */
DELETE FROM driver_stats;

-- Verstappen
INSERT INTO driver_stats (driver_id, season, team, races, wins, poles, podiums, points, position)
SELECT id, 2022, 'Red Bull Racing', 22, 15, 7, 17, 454, 1 FROM drivers WHERE code='VER';
INSERT INTO driver_stats (driver_id, season, team, races, wins, poles, podiums, points, position)
SELECT id, 2023, 'Red Bull Racing', 22, 19, 12, 21, 575, 1 FROM drivers WHERE code='VER';
INSERT INTO driver_stats (driver_id, season, team, races, wins, poles, podiums, points, position)
SELECT id, 2024, 'Red Bull Racing', 22, 18, 12, 20, 560, 1 FROM drivers WHERE code='VER';

/* (… continue for Perez, Leclerc, Norris, Piastri, Hamilton, Russell, Alonso etc …) */
/* ===========================
   TEAMS (2024/25)
   =========================== */
INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Red Bull Racing','Christian Horner','Milton Keynes, UK','Honda RBPT','https://logos-world.net/wp-content/uploads/2020/05/Red-Bull-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Ferrari','Frédéric Vasseur','Maranello, IT','Ferrari','https://1000logos.net/wp-content/uploads/2018/03/Ferrari-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Mercedes','Toto Wolff','Brackley, UK','Mercedes','https://1000logos.net/wp-content/uploads/2016/10/Mercedes-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('McLaren','Andrea Stella','Woking, UK','Mercedes','https://1000logos.net/wp-content/uploads/2017/02/McLaren-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Aston Martin','Mike Krack','Silverstone, UK','Mercedes','https://1000logos.net/wp-content/uploads/2017/05/Aston-Martin-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Alpine','Bruno Famin','Enstone, UK / Viry, FR','Renault','https://1000logos.net/wp-content/uploads/2018/04/Renault-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Williams','James Vowles','Grove, UK','Mercedes','https://1000logos.net/wp-content/uploads/2017/05/Williams-logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Sauber (Stake)','Alessandro Alunni Bravi','Hinwil, CH','Ferrari','https://1000logos.net/wp-content/uploads/2016/10/Sauber-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('RB (Visa Cash App RB)','Laurent Mekies','Faenza, IT','Honda RBPT','https://1000logos.net/wp-content/uploads/2020/09/AlphaTauri-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO teams (name, principal, base, power_unit, image_url) VALUES
('Haas','Ayao Komatsu','Kannapolis, USA','Ferrari','https://1000logos.net/wp-content/uploads/2017/03/HAAS-Logo.png')
ON DUPLICATE KEY UPDATE name=VALUES(name);

/* ===========================
   DRIVERS
   =========================== */
INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Max','Verstappen','VER',1,'Dutch','Red Bull Racing','https://media.formula1.com/d_driver_f1_2023/Verstappen.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Sergio','Perez','PER',11,'Mexican','Red Bull Racing','https://media.formula1.com/d_driver_f1_2023/Perez.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Charles','Leclerc','LEC',16,'Monegasque','Ferrari','https://media.formula1.com/d_driver_f1_2023/Leclerc.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Carlos','Sainz','SAI',55,'Spanish','Ferrari','https://media.formula1.com/d_driver_f1_2023/Sainz.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Lewis','Hamilton','HAM',44,'British','Mercedes','https://media.formula1.com/d_driver_f1_2023/Hamilton.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('George','Russell','RUS',63,'British','Mercedes','https://media.formula1.com/d_driver_f1_2023/Russell.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Lando','Norris','NOR',4,'British','McLaren','https://media.formula1.com/d_driver_f1_2023/Norris.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Oscar','Piastri','PIA',81,'Australian','McLaren','https://media.formula1.com/d_driver_f1_2023/Piastri.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Fernando','Alonso','ALO',14,'Spanish','Aston Martin','https://media.formula1.com/d_driver_f1_2023/Alonso.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

INSERT INTO drivers(first_name,last_name,code,number,nationality,team_name,image_url) VALUES
('Lance','Stroll','STR',18,'Canadian','Aston Martin','https://media.formula1.com/d_driver_f1_2023/Stroll.png')
ON DUPLICATE KEY UPDATE code=VALUES(code);

/* (… continue with rest of drivers as in your file …) */

/* ===========================
   CIRCUITS (24 full set)
   =========================== */
-- (use the cleaned 24 circuit INSERTs from your last paste — I won’t repeat all here due to length)

/* ===========================
   DRIVER STATS (sample 2022–24)
   =========================== */
DELETE FROM driver_stats;

-- Verstappen
INSERT INTO driver_stats (driver_id, season, team, races, wins, poles, podiums, points, position)
SELECT id, 2022, 'Red Bull Racing', 22, 15, 7, 17, 454, 1 FROM drivers WHERE code='VER';
INSERT INTO driver_stats (driver_id, season, team, races, wins, poles, podiums, points, position)
SELECT id, 2023, 'Red Bull Racing', 22, 19, 12, 21, 575, 1 FROM drivers WHERE code='VER';
INSERT INTO driver_stats (driver_id, season, team, races, wins, poles, podiums, points, position)
SELECT id, 2024, 'Red Bull Racing', 22, 18, 12, 20, 560, 1 FROM drivers WHERE code='VER';

/* (… continue for Perez, Leclerc, Norris, Piastri, Hamilton, Russell, Alonso etc …) */
