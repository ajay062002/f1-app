USE f1db;

-- TEAMS (10)
INSERT INTO team (id,name,principal,base,power_unit,logo_url,banner_url) VALUES
(1,'Red Bull Racing','Christian Horner','Milton Keynes, UK','Honda RBPT','https://1000logos.net/wp-content/uploads/2020/05/Red-Bull-Logo.png','https://picsum.photos/seed/rb-banner/1200/300'),
(2,'Ferrari','Frédéric Vasseur','Maranello, IT','Ferrari','https://1000logos.net/wp-content/uploads/2018/03/Ferrari-Logo.png','https://picsum.photos/seed/ferrari-banner/1200/300'),
(3,'Mercedes','Toto Wolff','Brackley, UK','Mercedes','https://1000logos.net/wp-content/uploads/2016/10/Mercedes-Logo.png','https://picsum.photos/seed/merc-banner/1200/300'),
(4,'McLaren','Andrea Stella','Woking, UK','Mercedes','https://1000logos.net/wp-content/uploads/2017/05/McLaren-logo.png','https://picsum.photos/seed/mcl-banner/1200/300'),
(5,'Aston Martin','Mike Krack','Silverstone, UK','Mercedes','https://1000logos.net/wp-content/uploads/2018/12/Aston-Martin-Logo.png','https://picsum.photos/seed/am-banner/1200/300'),
(6,'Alpine','Bruno Famin','Enstone, UK / Viry, FR','Renault','https://1000logos.net/wp-content/uploads/2021/07/Alpine-Logo.png','https://picsum.photos/seed/alp-banner/1200/300'),
(7,'Williams','James Vowles','Grove, UK','Mercedes','https://1000logos.net/wp-content/uploads/2020/04/Williams-Logo.png','https://picsum.photos/seed/wil-banner/1200/300'),
(8,'Sauber (Stake)','Alessandro Alunni Bravi','Hinwil, CH','Ferrari','https://1000logos.net/wp-content/uploads/2020/03/Alfa-Romeo-Logo.png','https://picsum.photos/seed/sau-banner/1200/300'),
(9,'RB (Visa Cash App RB)','Laurent Mekies','Faenza, IT','Honda RBPT','https://1000logos.net/wp-content/uploads/2023/08/VCARB-Logo.png','https://picsum.photos/seed/rbfaenza-banner/1200/300'),
(10,'Haas','Ayao Komatsu','Kannapolis, US','Ferrari','https://1000logos.net/wp-content/uploads/2019/08/Haas-Logo.png','https://picsum.photos/seed/haas-banner/1200/300')
ON DUPLICATE KEY UPDATE name=VALUES(name), principal=VALUES(principal), base=VALUES(base), power_unit=VALUES(power_unit), logo_url=VALUES(logo_url), banner_url=VALUES(banner_url);

-- DRIVERS (20)  ids = race numbers for convenience where unique; keep exact ids used in JSON
INSERT INTO driver (id,name,code,number,nationality,team_id,image_url,bio,stats_wins,stats_podiums,stats_poles,stats_championships,stats_points,stats_starts) VALUES
(33,'Max Verstappen','VER',1,'Dutch',1,'https://picsum.photos/seed/max/800/600','Dominant modern-era champion',61,102,40,3,2700,200),
(11,'Sergio Perez','PER',11,'Mexican',1,'https://picsum.photos/seed/perez/800/600','Tyre whisperer and late-race specialist',6,40,3,0,1600,270),
(16,'Charles Leclerc','LEC',16,'Monegasque',2,'https://picsum.photos/seed/leclerc/800/600','Qualifying king with raw speed',6,40,24,0,1200,130),
(55,'Carlos Sainz','SAI',55,'Spanish',2,'https://picsum.photos/seed/sainz/800/600','Smooth operator',4,25,5,0,1100,190),
(44,'Lewis Hamilton','HAM',44,'British',3,'https://picsum.photos/seed/ham/800/600','Record-breaking great',103,198,104,7,4600,340),
(63,'George Russell','RUS',63,'British',3,'https://picsum.photos/seed/russell/800/600','Methodical and fast',2,12,3,0,500,110),
(4,'Lando Norris','NOR',4,'British',4,'https://picsum.photos/seed/lnor/800/600','Blisteringly quick',2,20,2,0,900,120),
(81,'Oscar Piastri','PIA',81,'Australian',4,'https://picsum.photos/seed/piastri/800/600','Calm and clinical',1,8,1,0,300,50),
(14,'Fernando Alonso','ALO',14,'Spanish',5,'https://picsum.photos/seed/alonso/800/600','Two-time champion',32,106,22,2,2300,390),
(18,'Lance Stroll','STR',18,'Canadian',5,'https://picsum.photos/seed/stroll/800/600','Podium finisher',0,3,1,0,250,150),
(31,'Esteban Ocon','OCO',31,'French',6,'https://picsum.photos/seed/ocon/800/600','Measured racer',1,3,0,0,400,150),
(10,'Pierre Gasly','GAS',10,'French',6,'https://picsum.photos/seed/gasly/800/600','Composed and quick',1,4,0,0,420,150),
(23,'Alex Albon','ALB',23,'Thai',7,'https://picsum.photos/seed/albon/800/600','Qualifying specialist',0,2,0,0,250,110),
(2,'Logan Sargeant','SAR',2,'American',7,'https://picsum.photos/seed/sargeant/800/600','Young American talent',0,0,0,0,2,40),
(77,'Valtteri Bottas','BOT',77,'Finnish',8,'https://picsum.photos/seed/bottas/800/600','Race-winning pace',10,67,20,0,1800,230),
(24,'Zhou Guanyu','ZHO',24,'Chinese',8,'https://picsum.photos/seed/zhou/800/600','China’s first full-time F1 driver',0,0,0,0,12,70),
(22,'Yuki Tsunoda','TSU',22,'Japanese',9,'https://picsum.photos/seed/tsu/800/600','Explosive pace',0,0,0,0,80,100),
(3,'Daniel Ricciardo','RIC',3,'Australian',9,'https://picsum.photos/seed/ric/800/600','Late-braking master',8,32,3,0,1320,240),
(20,'Kevin Magnussen','MAG',20,'Danish',10,'https://picsum.photos/seed/mag/800/600','All-action racer',0,1,1,0,210,170),
(27,'Nico Hülkenberg','HUL',27,'German',10,'https://picsum.photos/seed/hulk/800/600','Qualifying ace',0,2,1,0,550,210)
ON DUPLICATE KEY UPDATE name=VALUES(name), team_id=VALUES(team_id), image_url=VALUES(image_url), bio=VALUES(bio),
stats_wins=VALUES(stats_wins), stats_podiums=VALUES(stats_podiums), stats_poles=VALUES(stats_poles),
stats_championships=VALUES(stats_championships), stats_points=VALUES(stats_points), stats_starts=VALUES(stats_starts);

-- TEAM LINEUPS
REPLACE INTO team_lineup (team_id, driver1_id, driver2_id) VALUES
(1,33,11),(2,16,55),(3,44,63),(4,4,81),(5,14,18),
(6,31,10),(7,23,2),(8,77,24),(9,22,3),(10,20,27);

-- CIRCUITS (24)  — simplified hero images and SVG paths
REPLACE INTO circuit (id,name,city,country,laps,length_km,race_km,hero_url,track_path) VALUES
(1,'Bahrain International Circuit','Sakhir','Bahrain',57,5.412,308.238,'https://picsum.photos/seed/bahrain/1200/320','M120,300 C90,260 80,220 110,190 C220,80 520,60 640,180 C700,240 650,320 530,330 C360,345 260,330 180,320 C160,318 140,310 120,300'),
(2,'Jeddah Corniche Circuit','Jeddah','Saudi Arabia',50,6.174,308.450,'https://picsum.photos/seed/jeddah/1200/320','M100,320 C180,330 240,330 300,300 C380,260 450,250 520,270 C600,300 660,320 720,310 C760,304 760,280 700,260 C600,225 520,210 400,230 C320,244 240,265 160,280 C120,290 100,300 100,320'),
(3,'Albert Park','Melbourne','Australia',58,5.278,306.124,'https://picsum.photos/seed/melbourne/1200/320','M120,320 C210,340 430,340 520,300 C620,255 640,220 600,200 C560,182 520,190 460,210 C360,245 260,250 180,240 C130,232 120,260 120,320'),
(4,'Suzuka','Suzuka','Japan',53,5.807,307.471,'https://picsum.photos/seed/suzuka/1200/320','M130,330 C180,320 240,290 280,260 C320,230 360,230 400,260 C440,290 510,305 560,290 C640,265 660,240 620,220 C540,180 470,170 380,180 C280,190 200,230 160,260 C140,275 130,300 130,330'),
(5,'Shanghai International Circuit','Shanghai','China',56,5.451,305.066,'https://picsum.photos/seed/shanghai/1200/320','M100,330 C180,330 260,320 320,300 C380,280 420,250 480,250 C560,250 620,290 680,300 C720,306 740,290 720,270 C660,210 520,180 400,190 C260,202 160,240 120,270 C100,286 100,305 100,330'),
(6,'Miami International Autodrome','Miami','USA',57,5.412,308.326,'https://picsum.photos/seed/miami/1200/320','M120,330 C180,330 280,330 360,300 C430,272 520,260 620,260 C700,260 740,280 720,300 C680,340 600,350 520,340 C350,320 240,320 120,330'),
(7,'Imola','Imola','Italy',63,4.909,309.049,'https://picsum.photos/seed/imola/1200/320','M120,320 C200,330 300,320 360,300 C420,282 460,250 520,240 C600,228 680,250 700,280 C720,308 680,330 600,330 C520,330 420,330 120,320'),
(8,'Monaco','Monte Carlo','Monaco',78,3.337,260.286,'https://picsum.photos/seed/monaco/1200/320','M120,320 C180,320 240,310 300,300 C360,290 420,290 480,300 C540,310 600,330 660,330 C700,330 720,320 700,300 C660,260 600,240 520,240 C440,240 360,260 300,270 C220,285 160,300 120,320'),
(9,'Circuit Gilles Villeneuve','Montreal','Canada',70,4.361,305.270,'https://picsum.photos/seed/montreal/1200/320','M120,320 C220,330 380,330 460,310 C520,295 560,270 620,260 C700,248 740,270 700,300 C660,330 560,340 460,340 C320,340 200,330 120,320'),
(10,'Circuit de Barcelona‑Catalunya','Barcelona','Spain',66,4.657,307.236,'https://picsum.photos/seed/barcelona/1200/320','M120,320 C220,330 300,310 380,300 C460,290 540,300 620,320 C680,336 740,330 720,300 C680,240 520,220 380,230 C260,238 160,270 120,300 C110,308 112,316 120,320'),
(11,'Red Bull Ring','Spielberg','Austria',71,4.318,306.452,'https://picsum.photos/seed/rbr/1200/320','M120,320 C180,330 300,330 420,320 C520,312 600,300 680,300 C740,300 740,280 700,270 C620,250 520,240 420,250 C320,260 220,285 120,320'),
(12,'Silverstone','Silverstone','UK',52,5.891,306.198,'https://picsum.photos/seed/silverstone/1200/320','M120,320 C220,330 340,320 440,300 C540,280 640,280 700,300 C740,315 720,340 640,340 C520,340 340,340 120,320'),
(13,'Hungaroring','Budapest','Hungary',70,4.381,306.630,'https://picsum.photos/seed/hungaroring/1200/320','M120,320 C200,335 280,330 360,310 C440,290 520,270 600,280 C680,290 720,315 700,330 C660,360 520,350 360,340 C220,330 160,325 120,320'),
(14,'Spa‑Francorchamps','Spa','Belgium',44,7.004,308.052,'https://picsum.photos/seed/spa/1200/320','M120,320 C220,310 300,270 380,250 C480,226 600,240 660,270 C720,300 700,330 620,340 C520,352 380,340 120,320'),
(15,'Zandvoort','Zandvoort','Netherlands',72,4.259,306.587,'https://picsum.photos/seed/zandvoort/1200/320','M120,320 C200,335 280,340 360,320 C440,300 520,260 600,260 C680,260 720,300 700,320 C660,360 520,360 360,340 C220,325 160,322 120,320'),
(16,'Monza','Monza','Italy',53,5.793,306.720,'https://picsum.photos/seed/monza/1200/320','M120,320 C220,330 420,330 520,300 C640,260 700,250 720,270 C740,290 720,320 640,330 C540,342 320,342 120,320'),
(17,'Baku City Circuit','Baku','Azerbaijan',51,6.003,306.049,'https://picsum.photos/seed/baku/1200/320','M120,320 C180,320 260,310 340,300 C420,290 540,290 620,300 C720,312 740,340 700,340 C580,340 360,340 120,320'),
(18,'Marina Bay','Singapore','Singapore',62,4.940,306.143,'https://picsum.photos/seed/singapore/1200/320','M120,320 C220,340 380,340 520,320 C600,308 660,290 700,300 C740,312 720,340 640,350 C480,368 260,350 120,320'),
(19,'Circuit of the Americas','Austin','USA',56,5.513,308.405,'https://picsum.photos/seed/cota/1200/320','M120,320 C220,330 340,320 420,300 C520,276 620,270 700,290 C740,301 740,330 700,340 C600,360 420,350 240,335 C180,330 140,325 120,320'),
(20,'Hermanos Rodríguez','Mexico City','Mexico',71,4.304,305.354,'https://picsum.photos/seed/mexico/1200/320','M120,320 C180,330 260,330 340,320 C420,310 500,290 580,290 C640,290 700,300 720,310 C740,320 720,340 640,345 C520,352 320,340 120,320'),
(21,'Interlagos','São Paulo','Brazil',71,4.309,305.879,'https://picsum.photos/seed/interlagos/1200/320','M120,320 C220,340 420,330 520,300 C600,276 640,260 700,270 C740,278 740,310 700,330 C640,360 520,360 340,345 C220,335 160,325 120,320'),
(22,'Las Vegas Strip Circuit','Las Vegas','USA',50,6.201,310.050,'https://picsum.photos/seed/vegas/1200/320','M120,320 C200,330 300,330 420,320 C520,310 620,300 700,300 C740,300 740,330 700,340 C600,360 380,350 120,320'),
(23,'Lusail','Doha','Qatar',57,5.419,308.611,'https://picsum.photos/seed/qatar/1200/320','M120,320 C220,330 360,320 440,300 C520,280 620,270 680,280 C720,288 740,320 700,340 C640,368 480,360 240,335 C180,330 140,325 120,320'),
(24,'Yas Marina','Abu Dhabi','UAE',58,5.281,306.183,'https://picsum.photos/seed/yas/1200/320','M120,320 C220,332 420,332 520,300 C620,268 700,260 720,280 C740,300 720,330 640,345 C520,364 320,352 120,320');

-- STANDINGS (2024) — drivers
DELETE FROM standings_driver WHERE season=2024;
INSERT INTO standings_driver (season,position,driver_name,team_name,points,wins) VALUES
(2024,1,'Max Verstappen','Red Bull Racing',560,18),
(2024,2,'Lando Norris','McLaren',330,2),
(2024,3,'Charles Leclerc','Ferrari',320,3),
(2024,4,'Oscar Piastri','McLaren',295,1),
(2024,5,'Sergio Perez','Red Bull Racing',280,1),
(2024,6,'Carlos Sainz','Ferrari',265,2),
(2024,7,'Lewis Hamilton','Mercedes',230,1),
(2024,8,'George Russell','Mercedes',210,1),
(2024,9,'Fernando Alonso','Aston Martin',190,0),
(2024,10,'Yuki Tsunoda','RB',85,0),
(2024,11,'Esteban Ocon','Alpine',70,1),
(2024,12,'Pierre Gasly','Alpine',64,0),
(2024,13,'Alex Albon','Williams',28,0),
(2024,14,'Valtteri Bottas','Sauber (Stake)',16,0),
(2024,15,'Kevin Magnussen','Haas',12,0),
(2024,16,'Nico Hülkenberg','Haas',10,0),
(2024,17,'Lance Stroll','Aston Martin',9,0),
(2024,18,'Daniel Ricciardo','RB',8,0),
(2024,19,'Zhou Guanyu','Sauber (Stake)',6,0),
(2024,20,'Logan Sargeant','Williams',2,0);

-- STANDINGS (2024) — constructors
DELETE FROM standings_constructor WHERE season=2024;
INSERT INTO standings_constructor (season,position,constructor_name,points,wins) VALUES
(2024,1,'Red Bull Racing',840,19),
(2024,2,'McLaren',640,3),
(2024,3,'Ferrari',620,5),
(2024,4,'Mercedes',450,2),
(2024,5,'Aston Martin',199,0),
(2024,6,'Alpine',134,1),
(2024,7,'RB',93,0),
(2024,8,'Williams',30,0),
(2024,9,'Haas',22,0),
(2024,10,'Sauber (Stake)',22,0);
