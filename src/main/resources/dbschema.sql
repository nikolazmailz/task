DROP TABLE IF EXISTS DIVISIONS;

CREATE TABLE IF NOT EXISTS DIVISIONS (
    ID INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL  PRIMARY KEY,
    DepCode VARCHAR(20) NOT NULL,
    DepJob VARCHAR(100) NOT NULL,
    Description VARCHAR(255) NOT NULL
);
