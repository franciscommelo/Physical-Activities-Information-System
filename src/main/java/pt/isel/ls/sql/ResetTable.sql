-- RESET DATABASE

-- drop tables
DROP TABLE IF EXISTS activity;
DROP TABLE IF EXISTS sport;
DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS users;

-- create tables
CREATE TABLE IF NOT EXISTS users(uid INT GENERATED ALWAYS AS IDENTITY UNIQUE, name VARCHAR(30) NOT NULL, email VARCHAR(30) UNIQUE NOT NULL);
CREATE TABLE IF NOT EXISTS sport(sid INT GENERATED ALWAYS AS IDENTITY UNIQUE, name VARCHAR(40) NOT NULL, description VARCHAR(40) );
CREATE TABLE IF NOT EXISTS route(rid INT GENERATED ALWAYS AS IDENTITY UNIQUE, startLocation VARCHAR(40), endLocation VARCHAR(40), distance DOUBLE PRECISION CHECK (distance > 0));
CREATE TABLE IF NOT EXISTS activity(aid INT GENERATED ALWAYS AS IDENTITY UNIQUE, "date" DATE NOT NULL, duration TIME NOT NULL, uid int REFERENCES USERS(uid), sid int REFERENCES SPORT(sid), rid int REFERENCES ROUTE(rid),deletedAt DATE);

-- insert date

INSERT INTO users (name , email) values('Francisco','francisco1@gmail.com'),('Manel','Man1el@gmail.com'),('Jose','Jose1o1@gmail.com');
INSERT INTO sport (name , description) values ('corrida','corrida desc'),('futebol','futebol desc'), ('tenis','tenis desc');
INSERT INTO route (startLocation , endLocation, distance) values ('Location 1','Location 2','12'), ('Location 10','Location 11','21'), ('Location 20','Location 21','41');
INSERT INTO activity (date , duration, uid,sid,rid) values ('2010-12-12','20:10:10.130300',1,1,1), ('2010-12-13','20:10:10.130300',1,2,2),('2010-12-14','20:10:10.130300',1,1,1);