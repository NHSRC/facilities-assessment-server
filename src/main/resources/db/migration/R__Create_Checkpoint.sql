CREATE OR REPLACE FUNCTION create_checkpoint(
  checkpoint_name VARCHAR(1024),
  chp_uuid        VARCHAR(1024),
  me_id           BIGINT,
  ch_id           BIGINT,
  mov             VARCHAR(1024) DEFAULT NULL,
  ob              BOOLEAN DEFAULT FALSE,
  si              BOOLEAN DEFAULT FALSE,
  pi              BOOLEAN DEFAULT FALSE,
  rr              BOOLEAN DEFAULT FALSE)
  RETURNS BIGINT AS $$
DECLARE checkpoint_id BIGINT;
BEGIN
  INSERT INTO checkpoint (name, means_of_verification, measurable_element_id, checklist_id, am_observation, am_staff_interview, am_patient_interview, am_record_review, uuid)
  VALUES (checkpoint_name, mov, me_id, ch_id, ob, si, pi, rr, chp_uuid :: UUID)
  RETURNING id
    INTO checkpoint_id;

  RAISE NOTICE 'Created Checkpoint with id as: %, name: %', checkpoint_id, checkpoint_name;

  RETURN checkpoint_id;
END;
$$ LANGUAGE plpgsql;