--
-- Name: area_of_concern; Type: TABLE; Schema: public; 
--
CREATE TABLE area_of_concern (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  uuid               UUID                        NOT NULL,
  name               CHARACTER VARYING(255)      NOT NULL
);

CREATE TABLE assessment_type (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  uuid               UUID                        NOT NULL,
  name               CHARACTER VARYING(255)      NOT NULL
);

--
-- Name: checklist; Type: TABLE; Schema: public; 
--
CREATE TABLE checklist (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  uuid               UUID                        NOT NULL,
  name               CHARACTER VARYING(255)      NOT NULL,
  department_id      INT                      NOT NULL,
  assessment_type_id INT                      NOT NULL
);

--
-- Name: checklist_area_of_concern; Type: TABLE; Schema: public; 
--
CREATE TABLE checklist_area_of_concern (
  area_of_concern_id INT NOT NULL,
  checklist_id       INT NOT NULL
);

--
-- Name: department; Type: TABLE; Schema: public; 
--
CREATE TABLE department (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  uuid               UUID                        NOT NULL,
  name               CHARACTER VARYING(255)      NOT NULL
);

--
-- Name: district; Type: TABLE; Schema: public; 
--
CREATE TABLE district (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  uuid               UUID                        NOT NULL,
  name               CHARACTER VARYING(255)      NOT NULL,
  state_id           INT                      NOT NULL
);

--
-- Name: facility; Type: TABLE; Schema: public; 
--
CREATE TABLE facility (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  uuid               UUID                        NOT NULL,
  name               CHARACTER VARYING(255)      NOT NULL,
  district_id        INT                      NOT NULL,
  facility_type_id   INT                      NOT NULL
);

--
-- Name: facility_type; Type: TABLE; Schema: public; 
--
CREATE TABLE facility_type (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  uuid               UUID                        NOT NULL,
  name               CHARACTER VARYING(255)      NOT NULL
);

--
-- Name: region; Type: TABLE; Schema: public; 
--
CREATE TABLE region (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  uuid               UUID                        NOT NULL,
  name               CHARACTER VARYING(255)      NOT NULL
);

--
-- Name: state; Type: TABLE; Schema: public; 
--
CREATE TABLE state (
  id                 SERIAL PRIMARY KEY,
  created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  uuid               UUID                        NOT NULL,
  name               CHARACTER VARYING(255)      NOT NULL,
  region_id          INT                      NOT NULL
);

ALTER TABLE ONLY department
  ADD CONSTRAINT uk_1t68827l97cwyxo9r1u6t4p7d UNIQUE (name);

--
-- Name: uk_2g0hi7w44i4sjkffh61pusaav; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY state
  ADD CONSTRAINT uk_2g0hi7w44i4sjkffh61pusaav UNIQUE (name);

--
-- Name: uk_2lo2qmsw5m8u266519tw71b0b; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY checklist
  ADD CONSTRAINT uk_2lo2qmsw5m8u266519tw71b0b UNIQUE (uuid);

--
-- Name: uk_360yo866iu1pqirqt9j61lvow; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY area_of_concern
  ADD CONSTRAINT uk_360yo866iu1pqirqt9j61lvow UNIQUE (name);

--
-- Name: uk_5og4o3o9u1vyaxabyhh3yhjm8; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY area_of_concern
  ADD CONSTRAINT uk_5og4o3o9u1vyaxabyhh3yhjm8 UNIQUE (uuid);

--
-- Name: uk_aa2ge00tn3kk9f19b8tc4hfvq; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY facility
  ADD CONSTRAINT uk_aa2ge00tn3kk9f19b8tc4hfvq UNIQUE (uuid);

--
-- Name: uk_ak6osygs4n1filjbae5s5dhgq; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY district
  ADD CONSTRAINT uk_ak6osygs4n1filjbae5s5dhgq UNIQUE (uuid);

--
-- Name: uk_ck4gpjfb8uop343jorys81i47; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY facility_type
  ADD CONSTRAINT uk_ck4gpjfb8uop343jorys81i47 UNIQUE (uuid);

--
-- Name: uk_d2o6ftcro2k532kvg8rp5k6qc; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY facility_type
  ADD CONSTRAINT uk_d2o6ftcro2k532kvg8rp5k6qc UNIQUE (name);

--
-- Name: uk_gltd99xeyhfx4wy821x8oog29; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY state
  ADD CONSTRAINT uk_gltd99xeyhfx4wy821x8oog29 UNIQUE (uuid);

--
-- Name: uk_ixr2itih2n9q41fv3qx6mbkrp; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY region
  ADD CONSTRAINT uk_ixr2itih2n9q41fv3qx6mbkrp UNIQUE (name);

--
-- Name: uk_nga81ppkv08rjp4mj3bx2a429; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY region
  ADD CONSTRAINT uk_nga81ppkv08rjp4mj3bx2a429 UNIQUE (uuid);

--
-- Name: uk_rkm3g5aw9qjrjmd0ty9lgibyn; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY department
  ADD CONSTRAINT uk_rkm3g5aw9qjrjmd0ty9lgibyn UNIQUE (uuid);

--
-- Name: uk_t65fvipifp5n0q3ab1fsclp0w; Type: CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY checklist
  ADD CONSTRAINT uk_t65fvipifp5n0q3ab1fsclp0w UNIQUE (name);

--
-- Name: fk2nekqjq1efy8cw88m8buhop0g; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY checklist_area_of_concern
  ADD CONSTRAINT fk2nekqjq1efy8cw88m8buhop0g FOREIGN KEY (checklist_id) REFERENCES checklist (id);

--
-- Name: fk6ggu3tu365ouitrqo1p38nbcv; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY state
  ADD CONSTRAINT fk6ggu3tu365ouitrqo1p38nbcv FOREIGN KEY (region_id) REFERENCES region (id);

--
-- Name: fk9q0dmy9dgad2d0ewohftj87cn; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY district
  ADD CONSTRAINT fk9q0dmy9dgad2d0ewohftj87cn FOREIGN KEY (state_id) REFERENCES state (id);


ALTER TABLE ONLY facility
  ADD CONSTRAINT fkigr2fb4t1jy9niygn2v1f9jbv FOREIGN KEY (facility_type_id) REFERENCES facility_type (id);

--
-- Name: fkodc5junfx6p0hgfu2psc96130; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY facility
  ADD CONSTRAINT fkodc5junfx6p0hgfu2psc96130 FOREIGN KEY (district_id) REFERENCES district (id);

--
-- Name: fkoixamrtiqflv8hk273pb0quyg; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY checklist
  ADD CONSTRAINT fkoixamrtiqflv8hk273pb0quyg FOREIGN KEY (department_id) REFERENCES department (id);

--
-- Name: fks15er2kj6ieh7a28uwrgye1qs; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY checklist
  ADD CONSTRAINT fks15er2kj6ieh7a28uwrgye1qs FOREIGN KEY (assessment_type_id) REFERENCES assessment_type (id);

--
-- Name: fksbu6vuvxcq9hqkrxpcyyad0h1; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY checklist_area_of_concern
  ADD CONSTRAINT fksbu6vuvxcq9hqkrxpcyyad0h1 FOREIGN KEY (area_of_concern_id) REFERENCES area_of_concern (id);


ALTER TABLE ONLY assessment_type
  ADD CONSTRAINT uk_1t68827l97cwyxo9r1u6t4p7m UNIQUE (name);