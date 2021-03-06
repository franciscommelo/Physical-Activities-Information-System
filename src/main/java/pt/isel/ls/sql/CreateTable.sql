CREATE TABLE IF NOT EXISTS users
(
    uid INT GENERATED ALWAYS AS IDENTITY UNIQUE,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS sport
(
    sid INT GENERATED ALWAYS AS IDENTITY UNIQUE,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(40) 
);

CREATE TABLE IF NOT EXISTS route
(
    rid INT GENERATED ALWAYS AS IDENTITY UNIQUE,
    startLocation VARCHAR(40),
    endLocation VARCHAR(40),
    distance INT CHECK (distance > 0)
    );

CREATE TABLE IF NOT EXISTS activity
(
  aid INT GENERATED ALWAYS AS IDENTITY UNIQUE,
  "date" DATE NOT NULL, 
  duration TIME NOT NULL,
  uid int REFERENCES "user"(uid),
  sid int REFERENCES SPORT(sid),
  rid int REFERENCES ROUTE(rid),
  deletedAt DATE
);



