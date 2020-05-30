--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-05-30 13:21:36

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2833 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 16460)
-- Name: persons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persons (
    id integer NOT NULL,
    name character varying(255),
    surname character varying(255),
    brief text,
    bg text
);


ALTER TABLE public.persons OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16478)
-- Name: skills; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.skills (
    skill_id integer NOT NULL,
    id integer,
    type character varying(255),
    skill character varying(255)
);


ALTER TABLE public.skills OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16476)
-- Name: skills_skill_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.skills_skill_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.skills_skill_id_seq OWNER TO postgres;

--
-- TOC entry 2834 (class 0 OID 0)
-- Dependencies: 204
-- Name: skills_skill_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.skills_skill_id_seq OWNED BY public.skills.skill_id;


--
-- TOC entry 2694 (class 2604 OID 16481)
-- Name: skills skill_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skills ALTER COLUMN skill_id SET DEFAULT nextval('public.skills_skill_id_seq'::regclass);


--
-- TOC entry 2825 (class 0 OID 16460)
-- Dependencies: 203
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persons (id, name, surname, brief, bg) FROM stdin;
0	Сергей	Сливкин	Приветствую на моей странице! Я Сергей. Увлекаюсь... много чем. И одно из моих главных хобби - программирование. Мне нравится писать программы, приложения и скрипты, проектировать их, продумывать и оптимизировать. Что может быть лучше, чем заниматься тем, что интересно тебе и приносит пользу людям?	На скриптовых языках я пишу лет с 12-ти. Тогда это были различные моды, сюжетные и функциональные, для разных игр. Со временем я понял, что хочу превратить это увлечение в своё основное занятие. Мне нравится изучать, как всё устроено, нравится устраивать это самому. Нравится то ощущение, когда, наконец, после многих попыток справился с багом, устранил утечку памяти или просто реализовал желаемое и оно работает правильным образом, принося людям пользу и удовольствие.<br/>Java я выбрал по двум основным причинам: во-первых, у меня уже был опыт работы с ней, когда я писал моды для игр (да, для игр на Джаве). А во-вторых, именно Джава показалась мне наиболее гармоничным языком с точки зрения человека, желающего войти в мир ООП. В ней нет такого обилия концепций, как в С++, в котором, будучи новичком, можно надолго зарыться и уйти от первоначальной цели. Но в то же время, в Джаве есть всё необходимое, позволяющее реализовывать объектно-ориентированный дизайн и вести разработку всеми современными методами.<br/>В 2018 году я занялся Джавой основательно, моей первой книгой стала "Core Java" от Кея Хорстмана. Прочитав её, я решил закрепить свои навыки и сделал свой первый более-менее серьёзный проект - платформер на Swing.<br/>После этого я занялся EE стеком, поскольку расценил это направление как одно из наиболее востребованных и перспективных из тех, что интересны именно мне. Здесь моим путеводителем стала книга "Professional Java for Web Applications" Николаса Уильямса. Ну и, конечно, интернет.<br/>Осваивая технологии веб-разработки, в какой-то момент я рассудил, что моих знаний уже достаточно, чтобы начинать делать какой-нибудь свой тренировочный проект. Так и появилась на свет Такаба - движок для АИБ, мой наиболее крупный на данный момент проект.
\.


--
-- TOC entry 2827 (class 0 OID 16478)
-- Dependencies: 205
-- Data for Name: skills; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.skills (skill_id, id, type, skill) FROM stdin;
1	0	front	JavaScript
2	0	front	jQuery
3	0	back	Java
4	0	back	Servlet API
5	0	back	JSP
6	0	back	Spring Core
7	0	back	Spring MVC
8	0	back	SQL
9	0	back	Maven
11	0	back	JDBC
12	0	front	HTML
13	0	front	CSS
14	0	back	JSON API
10	0	other	вышивание крестиком (немножко)
\.


--
-- TOC entry 2835 (class 0 OID 0)
-- Dependencies: 204
-- Name: skills_skill_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.skills_skill_id_seq', 14, true);


--
-- TOC entry 2696 (class 2606 OID 16467)
-- Name: persons persons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);


--
-- TOC entry 2698 (class 2606 OID 16486)
-- Name: skills skills_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.skills
    ADD CONSTRAINT skills_pkey PRIMARY KEY (skill_id);


-- Completed on 2020-05-30 13:21:36

--
-- PostgreSQL database dump complete
--

