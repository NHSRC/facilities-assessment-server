--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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


--
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET search_path = public, pg_catalog;

--
-- Name: create_assessment_tool(character varying); Type: FUNCTION; Schema: public; Owner: nhsrc
--

CREATE FUNCTION create_assessment_tool(assessment_tool_name character varying) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
DECLARE assessment_tool_id BIGINT;
BEGIN
  INSERT INTO assessment_tool (name) VALUES (assessment_tool_name)
  RETURNING id
    INTO assessment_tool_id;
  RAISE NOTICE 'Created Assessment Tool with id as: %, name: %', assessment_tool_id, assessment_tool_name;

  RETURN assessment_tool_id;
END;
$$;


ALTER FUNCTION public.create_assessment_tool(assessment_tool_name character varying) OWNER TO nhsrc;

--
-- Name: create_district(character varying, bigint); Type: FUNCTION; Schema: public; Owner: nhsrc
--

CREATE FUNCTION create_district(district_name character varying, state_id bigint) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
DECLARE district_id BIGINT;
BEGIN
  INSERT INTO district (name, state_id) VALUES (district_name, state_id)
  RETURNING id
    INTO district_id;
  RAISE NOTICE 'Created District with district id as: %, name: %', district_id, district_name;

  RETURN district_id;
END;
$$;


ALTER FUNCTION public.create_district(district_name character varying, state_id bigint) OWNER TO nhsrc;

--
-- Name: create_facility(character varying, bigint, bigint); Type: FUNCTION; Schema: public; Owner: nhsrc
--

CREATE FUNCTION create_facility(facility_name character varying, district_id bigint, facility_type_id bigint) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
DECLARE facility_id BIGINT;
BEGIN
  INSERT INTO facility (name, district_id, facility_type_id) VALUES (facility_name, district_id, facility_type_id)
  RETURNING id
    INTO facility_id;
  RAISE NOTICE 'Created Facility with facility id as: %, name: %', facility_id, facility_name;

  RETURN facility_id;
END;
$$;


ALTER FUNCTION public.create_facility(facility_name character varying, district_id bigint, facility_type_id bigint) OWNER TO nhsrc;

--
-- Name: create_facility_type(character varying); Type: FUNCTION; Schema: public; Owner: nhsrc
--

CREATE FUNCTION create_facility_type(facility_type_name character varying) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
DECLARE facility_type_id BIGINT;
BEGIN
  INSERT INTO facility_type (name) VALUES (facility_type_name)
  RETURNING id
    INTO facility_type_id;
  RAISE NOTICE 'Created Facility Type with facility type id as: %, name: %', facility_type_id, facility_type_name;

  RETURN facility_type_id;
END;
$$;


ALTER FUNCTION public.create_facility_type(facility_type_name character varying) OWNER TO nhsrc;

--
-- Name: create_seed_test_data(); Type: FUNCTION; Schema: public; Owner: nhsrc
--

CREATE FUNCTION create_seed_test_data() RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE   state_id                 BIGINT;
  DECLARE district_id              BIGINT;
  DECLARE district_hospital_id     BIGINT;
  DECLARE community_hospital_id    BIGINT;
  DECLARE primary_health_center_id BIGINT;
  DECLARE facility_id              BIGINT;
  DECLARE assessment_tool_id              BIGINT;
BEGIN
  district_hospital_id = create_facility_type('District Hospital');
  community_hospital_id = create_facility_type('Community Hospital');
  primary_health_center_id = create_facility_type('Primary Health Center');
  state_id = create_state('Punjab');
  district_id = create_district('Jalandhar', state_id);
  facility_id = create_facility('National Kidney Hospitals', district_id, district_hospital_id);
  facility_id = create_facility('Kapil Hospital', district_id, community_hospital_id);
  facility_id = create_facility('Balaji Medicare Hospital', district_id, primary_health_center_id);
  district_id = create_district('Amritsar', state_id);
  facility_id = create_facility('Fortis Hospital', district_id, district_hospital_id);
  facility_id = create_facility('ESI Corp', district_id, community_hospital_id);
  facility_id = create_facility('Shoor Hospital', district_id, primary_health_center_id);
  state_id = create_state('Himachal Pradesh');
  district_id = create_district('Shimla', state_id);
  facility_id = create_facility('MY Hospital', district_id, district_hospital_id);
  facility_id = create_facility('Ramesh Medicare', district_id, primary_health_center_id);
  district_id = create_district('Kullu', state_id);
  facility_id = create_facility('Suresh Hospital', district_id, district_hospital_id);
  facility_id = create_facility('Good Care', district_id, community_hospital_id);
  facility_id = create_facility('Free Healthcare', district_id, primary_health_center_id);
  state_id = create_state('Karnataka');
  district_id = create_district('Bangalore', state_id);
  facility_id = create_facility('Manipal Hospital', district_id, district_hospital_id);
  facility_id = create_facility('ESI Hospital', district_id, community_hospital_id);
  facility_id = create_facility('Narayana Hospital', district_id, primary_health_center_id);
  district_id = create_district('Hubli', state_id);
  facility_id = create_facility('KIMS Hospital', district_id, district_hospital_id);
  facility_id = create_facility('NIMHANS', district_id, community_hospital_id);
  facility_id = create_facility('Iyengar Hospital', district_id, primary_health_center_id);
  state_id = create_state('Andhra Pradesh');
  district_id = create_district('Hyderabad', state_id);
  facility_id = create_facility('Good Medicare Hospital', district_id, district_hospital_id);
  assessment_tool_id = create_assessment_tool('District Hospital (DH)');
  assessment_tool_id = create_assessment_tool('Primary Health Center (PHC)');
  RETURN TRUE;
END;
$$;


ALTER FUNCTION public.create_seed_test_data() OWNER TO nhsrc;

--
-- Name: create_state(character varying); Type: FUNCTION; Schema: public; Owner: nhsrc
--

CREATE FUNCTION create_state(state_name character varying) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
DECLARE state_id BIGINT;
BEGIN
  INSERT INTO state (name) VALUES (state_name)
  RETURNING id
    INTO state_id;
  RAISE NOTICE 'Created State with state id as: %, name: %', state_id, state_name;

  RETURN state_id;
END;
$$;


ALTER FUNCTION public.create_state(state_name character varying) OWNER TO nhsrc;

--
-- Name: delete_seed_test_data(); Type: FUNCTION; Schema: public; Owner: nhsrc
--

CREATE FUNCTION delete_seed_test_data() RETURNS boolean
    LANGUAGE plpgsql
    AS $$
BEGIN
  DELETE FROM facility;
  DELETE FROM facility_type;
  DELETE FROM district;
  DELETE FROM state;
  RETURN TRUE;
END;
$$;


ALTER FUNCTION public.delete_seed_test_data() OWNER TO nhsrc;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: area_of_concern; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE area_of_concern (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL,
    reference character varying(255) NOT NULL
);


ALTER TABLE area_of_concern OWNER TO nhsrc;

--
-- Name: area_of_concern_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE area_of_concern_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE area_of_concern_id_seq OWNER TO nhsrc;

--
-- Name: area_of_concern_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE area_of_concern_id_seq OWNED BY area_of_concern.id;


--
-- Name: assessment_tool; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE assessment_tool (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE assessment_tool OWNER TO nhsrc;

--
-- Name: assessment_tool_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE assessment_tool_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE assessment_tool_id_seq OWNER TO nhsrc;

--
-- Name: assessment_tool_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE assessment_tool_id_seq OWNED BY assessment_tool.id;


--
-- Name: assessment_type; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE assessment_type (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE assessment_type OWNER TO nhsrc;

--
-- Name: assessment_type_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE assessment_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE assessment_type_id_seq OWNER TO nhsrc;

--
-- Name: assessment_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE assessment_type_id_seq OWNED BY assessment_type.id;


--
-- Name: checklist; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE checklist (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL,
    department_id integer NOT NULL,
    assessment_tool_id integer NOT NULL
);


ALTER TABLE checklist OWNER TO nhsrc;

--
-- Name: checklist_area_of_concern; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE checklist_area_of_concern (
    area_of_concern_id integer NOT NULL,
    checklist_id integer NOT NULL
);


ALTER TABLE checklist_area_of_concern OWNER TO nhsrc;

--
-- Name: checklist_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE checklist_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE checklist_id_seq OWNER TO nhsrc;

--
-- Name: checklist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE checklist_id_seq OWNED BY checklist.id;


--
-- Name: checkpoint; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE checkpoint (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL,
    means_of_verification character varying(1023),
    measurable_element_id integer NOT NULL,
    is_default boolean DEFAULT true NOT NULL,
    checklist_id integer NOT NULL,
    state_id integer,
    am_observation boolean DEFAULT false,
    am_staff_interview boolean DEFAULT false,
    am_patient_interview boolean DEFAULT false,
    am_record_review boolean DEFAULT false,
    reference character varying(255) NOT NULL
);


ALTER TABLE checkpoint OWNER TO nhsrc;

--
-- Name: checkpoint_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE checkpoint_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE checkpoint_id_seq OWNER TO nhsrc;

--
-- Name: checkpoint_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE checkpoint_id_seq OWNED BY checkpoint.id;


--
-- Name: checkpoint_score; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE checkpoint_score (
    id integer NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    facility_assessment_id integer NOT NULL,
    checkpoint_id integer NOT NULL,
    checklist_id integer NOT NULL,
    score integer NOT NULL,
    remarks text,
    CONSTRAINT checkpoint_score_score_check CHECK (((score >= 0) AND (score <= 2)))
);


ALTER TABLE checkpoint_score OWNER TO nhsrc;

--
-- Name: checkpoint_score_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE checkpoint_score_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE checkpoint_score_id_seq OWNER TO nhsrc;

--
-- Name: checkpoint_score_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE checkpoint_score_id_seq OWNED BY checkpoint_score.id;


--
-- Name: department; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE department (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE department OWNER TO nhsrc;

--
-- Name: department_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE department_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE department_id_seq OWNER TO nhsrc;

--
-- Name: department_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE department_id_seq OWNED BY department.id;


--
-- Name: district; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE district (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL,
    state_id integer NOT NULL
);


ALTER TABLE district OWNER TO nhsrc;

--
-- Name: district_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE district_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE district_id_seq OWNER TO nhsrc;

--
-- Name: district_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE district_id_seq OWNED BY district.id;


--
-- Name: facility; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE facility (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    name character varying(255) NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    district_id integer NOT NULL,
    facility_type_id integer NOT NULL
);


ALTER TABLE facility OWNER TO nhsrc;

--
-- Name: facility_assessment; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE facility_assessment (
    id integer NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    facility_id integer NOT NULL,
    assessment_tool_id integer NOT NULL,
    start_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    end_date timestamp without time zone,
    CONSTRAINT facility_assessment_check CHECK ((end_date >= start_date))
);


ALTER TABLE facility_assessment OWNER TO nhsrc;

--
-- Name: facility_assessment_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE facility_assessment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE facility_assessment_id_seq OWNER TO nhsrc;

--
-- Name: facility_assessment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE facility_assessment_id_seq OWNED BY facility_assessment.id;


--
-- Name: facility_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE facility_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE facility_id_seq OWNER TO nhsrc;

--
-- Name: facility_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE facility_id_seq OWNED BY facility.id;


--
-- Name: facility_type; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE facility_type (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE facility_type OWNER TO nhsrc;

--
-- Name: facility_type_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE facility_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE facility_type_id_seq OWNER TO nhsrc;

--
-- Name: facility_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE facility_type_id_seq OWNED BY facility_type.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO nhsrc;

--
-- Name: measurable_element; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE measurable_element (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL,
    reference character varying(255) NOT NULL,
    standard_id integer NOT NULL
);


ALTER TABLE measurable_element OWNER TO nhsrc;

--
-- Name: measurable_element_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE measurable_element_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE measurable_element_id_seq OWNER TO nhsrc;

--
-- Name: measurable_element_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE measurable_element_id_seq OWNED BY measurable_element.id;


--
-- Name: schema_version; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE schema_version (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE schema_version OWNER TO nhsrc;

--
-- Name: standard; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE standard (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL,
    reference character varying(255) NOT NULL,
    area_of_concern_id integer NOT NULL
);


ALTER TABLE standard OWNER TO nhsrc;

--
-- Name: standard_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE standard_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE standard_id_seq OWNER TO nhsrc;

--
-- Name: standard_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE standard_id_seq OWNED BY standard.id;


--
-- Name: state; Type: TABLE; Schema: public; Owner: nhsrc
--

CREATE TABLE state (
    id integer NOT NULL,
    created_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone DEFAULT (now())::timestamp without time zone NOT NULL,
    uuid uuid DEFAULT uuid_generate_v4() NOT NULL,
    inactive boolean DEFAULT false NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE state OWNER TO nhsrc;

--
-- Name: state_id_seq; Type: SEQUENCE; Schema: public; Owner: nhsrc
--

CREATE SEQUENCE state_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE state_id_seq OWNER TO nhsrc;

--
-- Name: state_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: nhsrc
--

ALTER SEQUENCE state_id_seq OWNED BY state.id;


--
-- Name: area_of_concern id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY area_of_concern ALTER COLUMN id SET DEFAULT nextval('area_of_concern_id_seq'::regclass);


--
-- Name: assessment_tool id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY assessment_tool ALTER COLUMN id SET DEFAULT nextval('assessment_tool_id_seq'::regclass);


--
-- Name: assessment_type id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY assessment_type ALTER COLUMN id SET DEFAULT nextval('assessment_type_id_seq'::regclass);


--
-- Name: checklist id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist ALTER COLUMN id SET DEFAULT nextval('checklist_id_seq'::regclass);


--
-- Name: checkpoint id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint ALTER COLUMN id SET DEFAULT nextval('checkpoint_id_seq'::regclass);


--
-- Name: checkpoint_score id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint_score ALTER COLUMN id SET DEFAULT nextval('checkpoint_score_id_seq'::regclass);


--
-- Name: department id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY department ALTER COLUMN id SET DEFAULT nextval('department_id_seq'::regclass);


--
-- Name: district id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY district ALTER COLUMN id SET DEFAULT nextval('district_id_seq'::regclass);


--
-- Name: facility id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility ALTER COLUMN id SET DEFAULT nextval('facility_id_seq'::regclass);


--
-- Name: facility_assessment id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_assessment ALTER COLUMN id SET DEFAULT nextval('facility_assessment_id_seq'::regclass);


--
-- Name: facility_type id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_type ALTER COLUMN id SET DEFAULT nextval('facility_type_id_seq'::regclass);


--
-- Name: measurable_element id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY measurable_element ALTER COLUMN id SET DEFAULT nextval('measurable_element_id_seq'::regclass);


--
-- Name: standard id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY standard ALTER COLUMN id SET DEFAULT nextval('standard_id_seq'::regclass);


--
-- Name: state id; Type: DEFAULT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY state ALTER COLUMN id SET DEFAULT nextval('state_id_seq'::regclass);


--
-- Data for Name: area_of_concern; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY area_of_concern (id, created_date, last_modified_date, uuid, inactive, name, reference) FROM stdin;
\.


--
-- Name: area_of_concern_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('area_of_concern_id_seq', 1, false);


--
-- Data for Name: assessment_tool; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY assessment_tool (id, created_date, last_modified_date, uuid, inactive, name) FROM stdin;
1	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	c76a1683-ea08-4472-a7b2-6588716501e5	f	District Hospital (DH)
2	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	d8de2716-cc30-4af7-9011-638e34077222	f	Primary Health Center (PHC)
\.


--
-- Name: assessment_tool_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('assessment_tool_id_seq', 2, true);


--
-- Data for Name: assessment_type; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY assessment_type (id, created_date, last_modified_date, uuid, inactive, name) FROM stdin;
\.


--
-- Name: assessment_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('assessment_type_id_seq', 1, false);


--
-- Data for Name: checklist; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY checklist (id, created_date, last_modified_date, uuid, inactive, name, department_id, assessment_tool_id) FROM stdin;
\.


--
-- Data for Name: checklist_area_of_concern; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY checklist_area_of_concern (area_of_concern_id, checklist_id) FROM stdin;
\.


--
-- Name: checklist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('checklist_id_seq', 1, false);


--
-- Data for Name: checkpoint; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY checkpoint (id, created_date, last_modified_date, uuid, inactive, name, means_of_verification, measurable_element_id, is_default, checklist_id, state_id, am_observation, am_staff_interview, am_patient_interview, am_record_review, reference) FROM stdin;
\.


--
-- Name: checkpoint_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('checkpoint_id_seq', 1, false);


--
-- Data for Name: checkpoint_score; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY checkpoint_score (id, uuid, created_date, last_modified_date, facility_assessment_id, checkpoint_id, checklist_id, score, remarks) FROM stdin;
\.


--
-- Name: checkpoint_score_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('checkpoint_score_id_seq', 1, false);


--
-- Data for Name: department; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY department (id, created_date, last_modified_date, uuid, inactive, name) FROM stdin;
\.


--
-- Name: department_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('department_id_seq', 1, false);


--
-- Data for Name: district; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY district (id, created_date, last_modified_date, uuid, inactive, name, state_id) FROM stdin;
1	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	e4bfb0ef-ca37-4e31-997a-d062244022c5	f	Jalandhar	1
2	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	4d2955d8-87d6-476c-aad1-8bcb6d89a4ab	f	Amritsar	1
3	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	05da120b-1cf9-43e2-a8b4-c26d99c9e1d3	f	Shimla	2
4	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	3336d4e7-f15a-4b17-afd6-3899a7741318	f	Kullu	2
5	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	1fc57f3d-20ee-4079-a1de-c6289fe5a73e	f	Bangalore	3
6	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	d54c25bc-2d7d-4114-ab29-11ade191e818	f	Hubli	3
7	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	05cd186e-eb68-4ad1-a027-db89eed82586	f	Hyderabad	4
\.


--
-- Name: district_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('district_id_seq', 7, true);


--
-- Data for Name: facility; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY facility (id, created_date, last_modified_date, uuid, name, inactive, district_id, facility_type_id) FROM stdin;
1	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	85f977df-60c5-456d-bb87-72188a0d57d4	National Kidney Hospitals	f	1	1
2	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	507267c2-0e57-472a-967d-5807fd7f4725	Kapil Hospital	f	1	2
3	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	110b46bc-829e-4175-ade7-47b6e3e44819	Balaji Medicare Hospital	f	1	3
4	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	b63d69ad-b0ce-4f71-8bff-1362d1b52954	Fortis Hospital	f	2	1
5	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	85b46524-f9e7-4d2d-af79-69c1cf4930aa	ESI Corp	f	2	2
6	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	3af845c2-792a-44dc-95f7-b3eb26a6b075	Shoor Hospital	f	2	3
7	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	e7a80ae7-7ae7-44bf-a364-0af0e8e3e607	MY Hospital	f	3	1
8	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	fc9a15e2-42d3-4580-9b38-a88c7899ac67	Ramesh Medicare	f	3	3
9	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	03389e1a-854e-4e78-a4a1-1cf259879512	Suresh Hospital	f	4	1
10	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	49a0e1e9-0d7d-4f79-93a4-d0e914794f72	Good Care	f	4	2
11	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	d41dba0a-af02-44a9-af50-d04fd417fc15	Free Healthcare	f	4	3
12	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	89e87b71-e81b-420a-bb2b-06ebad3c32ce	Manipal Hospital	f	5	1
13	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	66a15429-2eb7-4a4f-a6fd-c34a369ca973	ESI Hospital	f	5	2
14	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	4ea05d50-1b28-48b7-b112-435871e7b25e	Narayana Hospital	f	5	3
15	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	fac905ca-ae4b-41c4-af76-a27c86e7852d	KIMS Hospital	f	6	1
16	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	25b37bd3-1cd0-463a-8752-4aa6ea2e22d9	NIMHANS	f	6	2
17	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	a431ad97-8e67-4d0d-ba2f-69f8acd9eb35	Iyengar Hospital	f	6	3
18	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	b4d3a2ec-f17f-475c-bca1-b92d13bc469e	Good Medicare Hospital	f	7	1
\.


--
-- Data for Name: facility_assessment; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY facility_assessment (id, uuid, created_date, last_modified_date, facility_id, assessment_tool_id, start_date, end_date) FROM stdin;
\.


--
-- Name: facility_assessment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('facility_assessment_id_seq', 1, false);


--
-- Name: facility_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('facility_id_seq', 18, true);


--
-- Data for Name: facility_type; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY facility_type (id, created_date, last_modified_date, uuid, inactive, name) FROM stdin;
1	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	77cceb53-7d71-456c-a9ee-c870774707ad	f	District Hospital
2	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	c1837c18-5b63-4e3f-8e56-cb5d9bdf2e86	f	Community Hospital
3	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	be22ffe8-ce18-4aac-a50d-fa1ee9a93251	f	Primary Health Center
\.


--
-- Name: facility_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('facility_type_id_seq', 3, true);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- Data for Name: measurable_element; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY measurable_element (id, created_date, last_modified_date, uuid, inactive, name, reference, standard_id) FROM stdin;
\.


--
-- Name: measurable_element_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('measurable_element_id_seq', 1, false);


--
-- Data for Name: schema_version; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY schema_version (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	Init	SQL	V1__Init.sql	840078719	nhsrc	2017-04-07 12:57:31.815774	212	t
2	\N	Create Assessment Tool	SQL	R__Create_Assessment_Tool.sql	102882886	nhsrc	2017-04-07 12:57:32.117433	28	t
3	\N	Create District	SQL	R__Create_District.sql	1662687565	nhsrc	2017-04-07 12:57:32.165523	13	t
4	\N	Create Facility	SQL	R__Create_Facility.sql	-871397381	nhsrc	2017-04-07 12:57:32.208239	6	t
5	\N	Create Facility Type	SQL	R__Create_Facility_Type.sql	283600673	nhsrc	2017-04-07 12:57:32.241859	11	t
6	\N	Create State	SQL	R__Create_State.sql	-1860166640	nhsrc	2017-04-07 12:57:32.381549	8	t
\.


--
-- Data for Name: standard; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY standard (id, created_date, last_modified_date, uuid, inactive, name, reference, area_of_concern_id) FROM stdin;
\.


--
-- Name: standard_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('standard_id_seq', 1, false);


--
-- Data for Name: state; Type: TABLE DATA; Schema: public; Owner: nhsrc
--

COPY state (id, created_date, last_modified_date, uuid, inactive, name) FROM stdin;
1	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	35a75b21-0362-4220-8bcd-38b22332d9ce	f	Punjab
2	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	1bea358d-9c2f-4cb7-80a2-961157f3ef4d	f	Himachal Pradesh
3	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	7e58bbc2-bcd6-43fe-bb27-c326ebf33ba0	f	Karnataka
4	2017-04-07 12:59:23.159355	2017-04-07 12:59:23.159355	d40f0579-5970-45f4-bb91-503b8e21477d	f	Andhra Pradesh
\.


--
-- Name: state_id_seq; Type: SEQUENCE SET; Schema: public; Owner: nhsrc
--

SELECT pg_catalog.setval('state_id_seq', 4, true);


--
-- Name: area_of_concern area_of_concern_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY area_of_concern
    ADD CONSTRAINT area_of_concern_name_key UNIQUE (name);


--
-- Name: area_of_concern area_of_concern_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY area_of_concern
    ADD CONSTRAINT area_of_concern_pkey PRIMARY KEY (id);


--
-- Name: area_of_concern area_of_concern_reference_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY area_of_concern
    ADD CONSTRAINT area_of_concern_reference_key UNIQUE (reference);


--
-- Name: area_of_concern area_of_concern_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY area_of_concern
    ADD CONSTRAINT area_of_concern_uuid_key UNIQUE (uuid);


--
-- Name: assessment_tool assessment_tool_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY assessment_tool
    ADD CONSTRAINT assessment_tool_name_key UNIQUE (name);


--
-- Name: assessment_tool assessment_tool_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY assessment_tool
    ADD CONSTRAINT assessment_tool_pkey PRIMARY KEY (id);


--
-- Name: assessment_tool assessment_tool_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY assessment_tool
    ADD CONSTRAINT assessment_tool_uuid_key UNIQUE (uuid);


--
-- Name: assessment_type assessment_type_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY assessment_type
    ADD CONSTRAINT assessment_type_name_key UNIQUE (name);


--
-- Name: assessment_type assessment_type_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY assessment_type
    ADD CONSTRAINT assessment_type_pkey PRIMARY KEY (id);


--
-- Name: assessment_type assessment_type_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY assessment_type
    ADD CONSTRAINT assessment_type_uuid_key UNIQUE (uuid);


--
-- Name: checklist checklist_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist
    ADD CONSTRAINT checklist_name_key UNIQUE (name);


--
-- Name: checklist checklist_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist
    ADD CONSTRAINT checklist_pkey PRIMARY KEY (id);


--
-- Name: checklist checklist_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist
    ADD CONSTRAINT checklist_uuid_key UNIQUE (uuid);


--
-- Name: checkpoint checkpoint_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint
    ADD CONSTRAINT checkpoint_pkey PRIMARY KEY (id);


--
-- Name: checkpoint_score checkpoint_score_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint_score
    ADD CONSTRAINT checkpoint_score_pkey PRIMARY KEY (id);


--
-- Name: checkpoint_score checkpoint_score_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint_score
    ADD CONSTRAINT checkpoint_score_uuid_key UNIQUE (uuid);


--
-- Name: checkpoint checkpoint_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint
    ADD CONSTRAINT checkpoint_uuid_key UNIQUE (uuid);


--
-- Name: department department_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY department
    ADD CONSTRAINT department_name_key UNIQUE (name);


--
-- Name: department department_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY department
    ADD CONSTRAINT department_pkey PRIMARY KEY (id);


--
-- Name: department department_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY department
    ADD CONSTRAINT department_uuid_key UNIQUE (uuid);


--
-- Name: district district_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY district
    ADD CONSTRAINT district_pkey PRIMARY KEY (id);


--
-- Name: district district_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY district
    ADD CONSTRAINT district_uuid_key UNIQUE (uuid);


--
-- Name: facility_assessment facility_assessment_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_assessment
    ADD CONSTRAINT facility_assessment_pkey PRIMARY KEY (id);


--
-- Name: facility_assessment facility_assessment_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_assessment
    ADD CONSTRAINT facility_assessment_uuid_key UNIQUE (uuid);


--
-- Name: facility facility_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility
    ADD CONSTRAINT facility_name_key UNIQUE (name);


--
-- Name: facility facility_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility
    ADD CONSTRAINT facility_pkey PRIMARY KEY (id);


--
-- Name: facility_type facility_type_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_type
    ADD CONSTRAINT facility_type_name_key UNIQUE (name);


--
-- Name: facility_type facility_type_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_type
    ADD CONSTRAINT facility_type_pkey PRIMARY KEY (id);


--
-- Name: facility_type facility_type_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_type
    ADD CONSTRAINT facility_type_uuid_key UNIQUE (uuid);


--
-- Name: facility facility_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility
    ADD CONSTRAINT facility_uuid_key UNIQUE (uuid);


--
-- Name: measurable_element measurable_element_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY measurable_element
    ADD CONSTRAINT measurable_element_name_key UNIQUE (name);


--
-- Name: measurable_element measurable_element_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY measurable_element
    ADD CONSTRAINT measurable_element_pkey PRIMARY KEY (id);


--
-- Name: measurable_element measurable_element_reference_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY measurable_element
    ADD CONSTRAINT measurable_element_reference_key UNIQUE (reference);


--
-- Name: measurable_element measurable_element_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY measurable_element
    ADD CONSTRAINT measurable_element_uuid_key UNIQUE (uuid);


--
-- Name: schema_version schema_version_pk; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY schema_version
    ADD CONSTRAINT schema_version_pk PRIMARY KEY (installed_rank);


--
-- Name: standard standard_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY standard
    ADD CONSTRAINT standard_name_key UNIQUE (name);


--
-- Name: standard standard_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY standard
    ADD CONSTRAINT standard_pkey PRIMARY KEY (id);


--
-- Name: standard standard_reference_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY standard
    ADD CONSTRAINT standard_reference_key UNIQUE (reference);


--
-- Name: standard standard_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY standard
    ADD CONSTRAINT standard_uuid_key UNIQUE (uuid);


--
-- Name: state state_name_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY state
    ADD CONSTRAINT state_name_key UNIQUE (name);


--
-- Name: state state_pkey; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY state
    ADD CONSTRAINT state_pkey PRIMARY KEY (id);


--
-- Name: state state_uuid_key; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY state
    ADD CONSTRAINT state_uuid_key UNIQUE (uuid);


--
-- Name: checkpoint uk_rbmq0k6q99te330xf90fh8nhn; Type: CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint
    ADD CONSTRAINT uk_rbmq0k6q99te330xf90fh8nhn UNIQUE (reference);


--
-- Name: schema_version_s_idx; Type: INDEX; Schema: public; Owner: nhsrc
--

CREATE INDEX schema_version_s_idx ON schema_version USING btree (success);


--
-- Name: checklist_area_of_concern checklist_area_of_concern_area_of_concern_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist_area_of_concern
    ADD CONSTRAINT checklist_area_of_concern_area_of_concern_id_fkey FOREIGN KEY (area_of_concern_id) REFERENCES area_of_concern(id);


--
-- Name: checklist_area_of_concern checklist_area_of_concern_checklist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist_area_of_concern
    ADD CONSTRAINT checklist_area_of_concern_checklist_id_fkey FOREIGN KEY (checklist_id) REFERENCES checklist(id);


--
-- Name: checklist checklist_assessment_tool_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist
    ADD CONSTRAINT checklist_assessment_tool_id_fkey FOREIGN KEY (assessment_tool_id) REFERENCES assessment_tool(id);


--
-- Name: checklist checklist_department_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist
    ADD CONSTRAINT checklist_department_id_fkey FOREIGN KEY (department_id) REFERENCES department(id);


--
-- Name: checkpoint checkpoint_checklist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint
    ADD CONSTRAINT checkpoint_checklist_id_fkey FOREIGN KEY (checklist_id) REFERENCES checklist(id);


--
-- Name: checkpoint checkpoint_measurable_element_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint
    ADD CONSTRAINT checkpoint_measurable_element_id_fkey FOREIGN KEY (measurable_element_id) REFERENCES measurable_element(id);


--
-- Name: checkpoint_score checkpoint_score_checklist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint_score
    ADD CONSTRAINT checkpoint_score_checklist_id_fkey FOREIGN KEY (checklist_id) REFERENCES checklist(id);


--
-- Name: checkpoint_score checkpoint_score_checkpoint_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint_score
    ADD CONSTRAINT checkpoint_score_checkpoint_id_fkey FOREIGN KEY (checkpoint_id) REFERENCES checkpoint(id);


--
-- Name: checkpoint_score checkpoint_score_facility_assessment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint_score
    ADD CONSTRAINT checkpoint_score_facility_assessment_id_fkey FOREIGN KEY (facility_assessment_id) REFERENCES facility_assessment(id);


--
-- Name: checkpoint checkpoint_state_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint
    ADD CONSTRAINT checkpoint_state_id_fkey FOREIGN KEY (state_id) REFERENCES state(id);


--
-- Name: district district_state_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY district
    ADD CONSTRAINT district_state_id_fkey FOREIGN KEY (state_id) REFERENCES state(id);


--
-- Name: facility_assessment facility_assessment_assessment_tool_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_assessment
    ADD CONSTRAINT facility_assessment_assessment_tool_id_fkey FOREIGN KEY (assessment_tool_id) REFERENCES assessment_tool(id);


--
-- Name: facility_assessment facility_assessment_facility_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_assessment
    ADD CONSTRAINT facility_assessment_facility_id_fkey FOREIGN KEY (facility_id) REFERENCES facility(id);


--
-- Name: facility facility_district_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility
    ADD CONSTRAINT facility_district_id_fkey FOREIGN KEY (district_id) REFERENCES district(id);


--
-- Name: facility facility_facility_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility
    ADD CONSTRAINT facility_facility_type_id_fkey FOREIGN KEY (facility_type_id) REFERENCES facility_type(id);


--
-- Name: checklist_area_of_concern fk2nekqjq1efy8cw88m8buhop0g; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist_area_of_concern
    ADD CONSTRAINT fk2nekqjq1efy8cw88m8buhop0g FOREIGN KEY (checklist_id) REFERENCES checklist(id);


--
-- Name: district fk9q0dmy9dgad2d0ewohftj87cn; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY district
    ADD CONSTRAINT fk9q0dmy9dgad2d0ewohftj87cn FOREIGN KEY (state_id) REFERENCES state(id);


--
-- Name: measurable_element fkchw75hwbgdtd3cgcpupn1x6sl; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY measurable_element
    ADD CONSTRAINT fkchw75hwbgdtd3cgcpupn1x6sl FOREIGN KEY (standard_id) REFERENCES standard(id);


--
-- Name: checkpoint fkd190j4ew4magng8mp8b7o4em7; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint
    ADD CONSTRAINT fkd190j4ew4magng8mp8b7o4em7 FOREIGN KEY (checklist_id) REFERENCES checklist(id);


--
-- Name: facility_assessment fkerjal4cbxc47uin80r7vy2waq; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_assessment
    ADD CONSTRAINT fkerjal4cbxc47uin80r7vy2waq FOREIGN KEY (facility_id) REFERENCES facility(id);


--
-- Name: checkpoint fkhqnoo2bh9njf94l17duk597cj; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint
    ADD CONSTRAINT fkhqnoo2bh9njf94l17duk597cj FOREIGN KEY (measurable_element_id) REFERENCES measurable_element(id);


--
-- Name: facility fkigr2fb4t1jy9niygn2v1f9jbv; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility
    ADD CONSTRAINT fkigr2fb4t1jy9niygn2v1f9jbv FOREIGN KEY (facility_type_id) REFERENCES facility_type(id);


--
-- Name: checklist fkjpapanlv0gojoo3lp11y903na; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist
    ADD CONSTRAINT fkjpapanlv0gojoo3lp11y903na FOREIGN KEY (assessment_tool_id) REFERENCES assessment_tool(id);


--
-- Name: facility_assessment fkkhe0hck4up5hfakge4a5tyc75; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility_assessment
    ADD CONSTRAINT fkkhe0hck4up5hfakge4a5tyc75 FOREIGN KEY (assessment_tool_id) REFERENCES assessment_tool(id);


--
-- Name: checkpoint_score fkkjl9p6q4x5sutpl56csk034ql; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint_score
    ADD CONSTRAINT fkkjl9p6q4x5sutpl56csk034ql FOREIGN KEY (facility_assessment_id) REFERENCES facility_assessment(id);


--
-- Name: checkpoint_score fkloo28vdnbvmbh1k7p9acb8nvs; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint_score
    ADD CONSTRAINT fkloo28vdnbvmbh1k7p9acb8nvs FOREIGN KEY (checklist_id) REFERENCES checklist(id);


--
-- Name: checkpoint_score fkm3qe1wbdp518lw62ra860e37n; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checkpoint_score
    ADD CONSTRAINT fkm3qe1wbdp518lw62ra860e37n FOREIGN KEY (checkpoint_id) REFERENCES checkpoint(id);


--
-- Name: facility fkodc5junfx6p0hgfu2psc96130; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY facility
    ADD CONSTRAINT fkodc5junfx6p0hgfu2psc96130 FOREIGN KEY (district_id) REFERENCES district(id);


--
-- Name: checklist fkoixamrtiqflv8hk273pb0quyg; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist
    ADD CONSTRAINT fkoixamrtiqflv8hk273pb0quyg FOREIGN KEY (department_id) REFERENCES department(id);


--
-- Name: standard fkrwbwnck9ms8k8fd7lju4pgi65; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY standard
    ADD CONSTRAINT fkrwbwnck9ms8k8fd7lju4pgi65 FOREIGN KEY (area_of_concern_id) REFERENCES area_of_concern(id);


--
-- Name: checklist_area_of_concern fksbu6vuvxcq9hqkrxpcyyad0h1; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY checklist_area_of_concern
    ADD CONSTRAINT fksbu6vuvxcq9hqkrxpcyyad0h1 FOREIGN KEY (area_of_concern_id) REFERENCES area_of_concern(id);


--
-- Name: measurable_element measurable_element_standard_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY measurable_element
    ADD CONSTRAINT measurable_element_standard_id_fkey FOREIGN KEY (standard_id) REFERENCES standard(id);


--
-- Name: standard standard_area_of_concern_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: nhsrc
--

ALTER TABLE ONLY standard
    ADD CONSTRAINT standard_area_of_concern_id_fkey FOREIGN KEY (area_of_concern_id) REFERENCES area_of_concern(id);


--
-- PostgreSQL database dump complete
--

