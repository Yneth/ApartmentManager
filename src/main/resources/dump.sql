--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: apartment_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE apartment_types (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE apartment_types OWNER TO postgres;

--
-- Name: apartment_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE apartment_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE apartment_types_id_seq OWNER TO postgres;

--
-- Name: apartment_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE apartment_types_id_seq OWNED BY apartment_types.id;


--
-- Name: apartments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE apartments (
    id integer NOT NULL,
    room_count integer NOT NULL,
    price numeric NOT NULL,
    apartment_type_id integer,
    name character varying(50)
);


ALTER TABLE apartments OWNER TO postgres;

--
-- Name: apartments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE apartments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE apartments_id_seq OWNER TO postgres;

--
-- Name: apartments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE apartments_id_seq OWNED BY apartments.id;


--
-- Name: authorities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE authorities (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE authorities OWNER TO postgres;

--
-- Name: authorities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE authorities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE authorities_id_seq OWNER TO postgres;

--
-- Name: authorities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE authorities_id_seq OWNED BY authorities.id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE orders (
    id integer NOT NULL,
    apartment_id integer,
    request_id integer,
    price numeric NOT NULL,
    payed boolean NOT NULL
);


ALTER TABLE orders OWNER TO postgres;

--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE orders_id_seq OWNER TO postgres;

--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE orders_id_seq OWNED BY orders.id;


--
-- Name: requests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE requests (
    id integer NOT NULL,
    user_id integer,
    from_date timestamp without time zone NOT NULL,
    to_date timestamp without time zone NOT NULL,
    room_count integer NOT NULL,
    apartment_type_id integer,
    status_id integer NOT NULL,
    status_comment character varying(200)
);


ALTER TABLE requests OWNER TO postgres;

--
-- Name: requests_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE requests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE requests_id_seq OWNER TO postgres;

--
-- Name: requests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE requests_id_seq OWNED BY requests.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    id integer NOT NULL,
    first_name character varying(20),
    last_name character varying(20),
    login character varying(40) NOT NULL,
    password character varying(60) NOT NULL,
    authority_id integer
);


ALTER TABLE users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apartment_types ALTER COLUMN id SET DEFAULT nextval('apartment_types_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apartments ALTER COLUMN id SET DEFAULT nextval('apartments_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY authorities ALTER COLUMN id SET DEFAULT nextval('authorities_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orders ALTER COLUMN id SET DEFAULT nextval('orders_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requests ALTER COLUMN id SET DEFAULT nextval('requests_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: apartment_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY apartment_types (id, name) FROM stdin;
13	business
14	lux
15	premium
16	classic
\.


--
-- Name: apartment_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('apartment_types_id_seq', 16, true);


--
-- Data for Name: apartments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY apartments (id, room_count, price, apartment_type_id, name) FROM stdin;
20	1	100	13	Test
21	1	200	13	asd
\.


--
-- Name: apartments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('apartments_id_seq', 21, true);


--
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY authorities (id, name) FROM stdin;
10	ADMIN
11	USER
12	SUPERSU
\.


--
-- Name: authorities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('authorities_id_seq', 14, true);


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY orders (id, apartment_id, request_id, price, payed) FROM stdin;
5	20	18	400	t
6	21	20	600	t
\.


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('orders_id_seq', 6, true);


--
-- Data for Name: requests; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY requests (id, user_id, from_date, to_date, room_count, apartment_type_id, status_id, status_comment) FROM stdin;
17	36	2017-10-10 10:10:00	2017-10-15 10:10:00	1	13	1	\N
18	36	2017-10-13 10:10:00	2017-10-17 10:11:00	1	13	1	\N
20	36	2017-10-15 10:10:00	2017-10-18 10:10:00	1	13	1	\N
21	36	2017-10-10 10:01:00	2018-10-10 10:10:00	1	13	0	\N
\.


--
-- Name: requests_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('requests_id_seq', 21, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (id, first_name, last_name, login, password, authority_id) FROM stdin;
37	Alex	Amirov	amirov	$2a$10$M1GyzmLngSp3O4bJathINu4RPtmVOnBZnP4P6zfXeeG3ulPS97.Z6	11
34	\N	\N	supersu	$2a$10$XcFFov1NwyOL5V/IRIz3VuikhUfxJMuYvfXrqTEpP5v9dnpjl//d6	12
35	\N	\N	admin	$2a$10$Svx9v3L.5W8WDVWkw88YDu/lBk3q5D4XJOVhDDa8uwi7SLsY9b4km	10
36	test42	test42	test	$2a$10$xbpWVdu6MoqYFNdgv6zzROdx/iopN6dilmigi/o2aoaAMmbW8a/7a	11
38	testset	testset	testset	$2a$10$YpKrdoK/OdS5WTApeF0oc.bIHLhOOmlYFRhJ3Q1DO8/QVCeKOfkKq	11
39			admin1	$2a$10$0UEb5zuH1W8fHEbEpIVIqu2TKOtaWSc5RxZyJwVNN/MkOQTBLddES	10
40			admin2	$2a$10$t.nNdrUZ5d3JWzBzOTnTauA7UokILNn18x4wNWNgxlVZ36LGFEgZK	10
41			admin3	$2a$10$oDwpqTIvfqhVjSZXbx.TteF/eLhsWMUtriFx3hHCm0gKSneNxBNNm	10
42			admin4	$2a$10$Yfv2C3gAvZFEj5tlJcg/f.0ark6vOkQDGPQiUj6OQlQgYysMFpc0W	10
43			admin5	$2a$10$.0L3zsnabLuDQkK6qnTStuXmo/xUzVXsRKYr2dBLSd8YVQifArvoe	10
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 43, true);


--
-- Name: apartment_types_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apartment_types
    ADD CONSTRAINT apartment_types_name_key UNIQUE (name);


--
-- Name: apartment_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apartment_types
    ADD CONSTRAINT apartment_types_pkey PRIMARY KEY (id);


--
-- Name: apartments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apartments
    ADD CONSTRAINT apartments_pkey PRIMARY KEY (id);


--
-- Name: authorities_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY authorities
    ADD CONSTRAINT authorities_name_key UNIQUE (name);


--
-- Name: authorities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY authorities
    ADD CONSTRAINT authorities_pkey PRIMARY KEY (id);


--
-- Name: orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: requests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requests
    ADD CONSTRAINT requests_pkey PRIMARY KEY (id);


--
-- Name: users_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_login_key UNIQUE (login);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: apartments_apartment_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY apartments
    ADD CONSTRAINT apartments_apartment_type_id_fkey FOREIGN KEY (apartment_type_id) REFERENCES apartment_types(id);


--
-- Name: orders_apartment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_apartment_id_fkey FOREIGN KEY (apartment_id) REFERENCES apartments(id);


--
-- Name: orders_request_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_request_id_fkey FOREIGN KEY (request_id) REFERENCES requests(id);


--
-- Name: requests_apartment_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requests
    ADD CONSTRAINT requests_apartment_type_id_fkey FOREIGN KEY (apartment_type_id) REFERENCES apartment_types(id);


--
-- Name: requests_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY requests
    ADD CONSTRAINT requests_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: users_authority_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_authority_id_fkey FOREIGN KEY (authority_id) REFERENCES authorities(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

