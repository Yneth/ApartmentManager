DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS apartments CASCADE;
DROP TABLE IF EXISTS apartment_types CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;

CREATE TABLE IF NOT EXISTS authorities
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    login VARCHAR(40) UNIQUE NOT NULL,
    password VARCHAR(40) NOT NULL,
    authority_id INTEGER REFERENCES authorities (id)
);

CREATE TABLE IF NOT EXISTS apartment_types
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS apartments
(
    id SERIAL PRIMARY KEY,
    room_count INTEGER NOT NULL,
	price NUMERIC NOT NULL,
    apartment_type_id INTEGER REFERENCES apartment_types (id)
);

CREATE TABLE IF NOT EXISTS requests (
    id SERIAL PRIMARY KEY,
	
	user_id INTEGER REFERENCES users (id),
	
	"from" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	"to" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	
    room_count INTEGER NOT NULL,
	apartment_type_id INTEGER REFERENCES apartment_types (id),
	
	status_id INTEGER NOT NULL,
	status_comment VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    apartment_id INTEGER REFERENCES apartments (id),
    request_id INTEGER REFERENCES requests (id),
    price NUMERIC NOT NULL,
    payed BOOLEAN NOT NULL
);

insert into apartment_types(id, name) values (default, 'business');
insert into apartment_types(id, name) values (default, 'lux');
insert into apartment_types(id, name) values (default, 'premium');
insert into apartment_types(id, name) values (default, 'classic');

insert into authorities(id, name) values (default, 'ADMIN');
insert into authorities(id, name) values (default, 'USER');

insert into users(id, first_name, last_name, login, password, authority_id) 
values(default, NULL, NULL, 'admin', 'admin', 1);
insert into users(id, first_name, last_name, login, password, authority_id) 
values(default, 'test', 'test', 'test', 'test', 2);