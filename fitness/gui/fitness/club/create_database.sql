
CREATE DATABASE fitness_club;


\c fitness_club


CREATE TABLE trainers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL
);


CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100)
);


CREATE TABLE trainings (
    id SERIAL PRIMARY KEY,
    trainer_id INTEGER REFERENCES trainers(id),
    client_id INTEGER REFERENCES clients(id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    type VARCHAR(50) NOT NULL,
    CONSTRAINT time_check CHECK (
        EXTRACT(HOUR FROM start_time) >= 9 AND 
        EXTRACT(HOUR FROM end_time) <= 22
    )
); 