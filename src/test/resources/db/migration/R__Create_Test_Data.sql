CREATE OR REPLACE FUNCTION create_seed_test_data()
  RETURNS BOOLEAN AS $$
DECLARE   state_id                 BIGINT;
  DECLARE district_id              BIGINT;
  DECLARE district_hospital_id     BIGINT;
  DECLARE community_hospital_id    BIGINT;
  DECLARE primary_health_center_id BIGINT;
  DECLARE facility_id              BIGINT;
  DECLARE assessment_tool_id       BIGINT;
  DECLARE checklist_id             BIGINT;
  DECLARE me_id                    BIGINT;
  DECLARE checkpoint_id            BIGINT;
  DECLARE aoc_id                   BIGINT;
  DECLARE std_id                   BIGINT;
  DECLARE department_id            BIGINT;
BEGIN
  district_hospital_id = create_facility_type('District Hospital', '77cceb53-7d71-456c-a9ee-c870774707ad');
  community_hospital_id = create_facility_type('Community Hospital', 'c1837c18-5b63-4e3f-8e56-cb5d9bdf2e86');
  primary_health_center_id = create_facility_type('Primary Health Center', 'be22ffe8-ce18-4aac-a50d-fa1ee9a93251');
  state_id = create_state('Andhra Pradesh','d40f0579-5970-45f4-bb91-503b8e21477d');
  district_id = create_district('Hyderabad', state_id, '05cd186e-eb68-4ad1-a027-db89eed82586');
  facility_id = create_facility('Good Medicare Hospital', district_id, district_hospital_id, 'b4d3a2ec-f17f-475c-bca1-b92d13bc469e');
  state_id = create_state('Karnataka','7e58bbc2-bcd6-43fe-bb27-c326ebf33ba0');
  district_id = create_district('Hubli', state_id, 'd54c25bc-2d7d-4114-ab29-11ade191e818');
  facility_id = create_facility('KIMS Hospital', district_id, district_hospital_id, 'fac905ca-ae4b-41c4-af76-a27c86e7852d');
  facility_id = create_facility('NIMHANS', district_id, community_hospital_id, '25b37bd3-1cd0-463a-8752-4aa6ea2e22d9');
  facility_id = create_facility('Iyengar Hospital', district_id, primary_health_center_id, 'a431ad97-8e67-4d0d-ba2f-69f8acd9eb35');
  district_id = create_district('Bangalore', state_id, '1fc57f3d-20ee-4079-a1de-c6289fe5a73e');
  facility_id = create_facility('Manipal Hospital', district_id, district_hospital_id, '89e87b71-e81b-420a-bb2b-06ebad3c32ce');
  facility_id = create_facility('ESI Hospital', district_id, community_hospital_id, '66a15429-2eb7-4a4f-a6fd-c34a369ca973');
  facility_id = create_facility('Narayana Hospital', district_id, primary_health_center_id, '4ea05d50-1b28-48b7-b112-435871e7b25e');
  state_id = create_state('Himachal Pradesh','1bea358d-9c2f-4cb7-80a2-961157f3ef4d');
  district_id = create_district('Kullu', state_id, '3336d4e7-f15a-4b17-afd6-3899a7741318');
  facility_id = create_facility('Suresh Hospital', district_id, district_hospital_id, '03389e1a-854e-4e78-a4a1-1cf259879512');
  facility_id = create_facility('Good Care', district_id, community_hospital_id, '49a0e1e9-0d7d-4f79-93a4-d0e914794f72');
  facility_id = create_facility('Free Healthcare', district_id, primary_health_center_id, 'd41dba0a-af02-44a9-af50-d04fd417fc15');
  district_id = create_district('Shimla', state_id, '05da120b-1cf9-43e2-a8b4-c26d99c9e1d3');
  facility_id = create_facility('MY Hospital', district_id, district_hospital_id, 'e7a80ae7-7ae7-44bf-a364-0af0e8e3e607');
  facility_id = create_facility('Ramesh Medicare', district_id, primary_health_center_id, 'fc9a15e2-42d3-4580-9b38-a88c7899ac67');
  state_id = create_state('Punjab','35a75b21-0362-4220-8bcd-38b22332d9ce');
  district_id = create_district('Amritsar', state_id, '4d2955d8-87d6-476c-aad1-8bcb6d89a4ab');
  facility_id = create_facility('Fortis Hospital', district_id, district_hospital_id, 'b63d69ad-b0ce-4f71-8bff-1362d1b52954');
  facility_id = create_facility('ESI Corp', district_id, community_hospital_id, '85b46524-f9e7-4d2d-af79-69c1cf4930aa');
  facility_id = create_facility('Shoor Hospital', district_id, primary_health_center_id, '3af845c2-792a-44dc-95f7-b3eb26a6b075');
  district_id = create_district('Jalandhar', state_id, 'e4bfb0ef-ca37-4e31-997a-d062244022c5');
  facility_id = create_facility('National Kidney Hospitals', district_id, district_hospital_id, '85f977df-60c5-456d-bb87-72188a0d57d4');
  facility_id = create_facility('Kapil Hospital', district_id, community_hospital_id, '507267c2-0e57-472a-967d-5807fd7f4725');
  facility_id = create_facility('Balaji Medicare Hospital', district_id, primary_health_center_id, '110b46bc-829e-4175-ade7-47b6e3e44819');
  department_id = create_department('Labour Room', '20cef8c0-3c81-4357-a525-842760e32c7a');
  assessment_tool_id = create_assessment_tool('District Hospital (DH)', 'c76a1683-ea08-4472-a7b2-6588716501e5');
  checklist_id = create_checklist('DH Labour Room', department_id, assessment_tool_id,
                                  'f27c4147-248b-4d9a-a1ee-8c0e1f62d56f');
  aoc_id = create_area_of_concern('Service Provision', 'A', 'd6928ca6-c032-4d13-822f-6738ec41c937');
  std_id = create_standard('The facility provides Curative Services', 'A1', aoc_id,
                           'e3062d35-3a4b-43d3-8edf-4b5faa85cab1');
  me_id = create_measurable_element('The facility provides General Medicine services', 'A1.1', std_id,
                                    '56282098-3ac1-4e73-8765-662f8e3b82da');
  me_id = create_measurable_element('The facility provides General Surgery services', 'A1.2', std_id,
                                    '444b9fae-7af0-4ff2-8d5a-6fb9b8da6f43');
  me_id = create_measurable_element('The facility provides Obstetrics & Gynaecology Services', 'A1.3', std_id,
                                    '358d23f2-afec-49a6-941a-21b33f7f274a');
  checkpoint_id = create_checkpoint('Availability of comprehensive obstetric services',
                                    'e66486d8-1c22-4b86-9f30-a1187875fe55', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility provides Paediatric Services', 'A1.4', std_id,
                                    '7d5cd5a8-3875-4221-b754-e5b0932f90b9');
  me_id = create_measurable_element('The facility provides Ophthalmology Services', 'A1.5', std_id,
                                    'd25039f0-619a-4bcc-908b-7315ba05f6cb');
  me_id = create_measurable_element('The facility provides ENT Services', 'A1.6', std_id,
                                    '13d40dae-622f-4c04-a556-f6b580940c1f');
  me_id = create_measurable_element('The facility provides Orthopaedics Services', 'A1.7', std_id,
                                    '16396a73-d2b7-4259-8596-b85354281aeb');
  me_id = create_measurable_element('The facility provides Skin & VD Services', 'A1.8', std_id,
                                    'f49fdf6b-aedc-44e7-9802-ab6929e1f508');
  me_id = create_measurable_element('The facility provides Psychiatry Services', 'A1.9', std_id,
                                    '101dd62e-3e05-40b3-9538-1e745b05c672');
  me_id = create_measurable_element('The facility provides Dental Treatment Services', 'A1.10', std_id,
                                    '184eb291-567f-48f7-9dc4-15c77f197786');
  me_id = create_measurable_element('The facility provides AYUSH Services', 'A1.11', std_id,
                                    '7f21bbb2-ae33-4c8a-a4bf-efaf346164b1');
  me_id = create_measurable_element('The facility provides Physiotherapy Services', 'A1.12', std_id,
                                    '4e7c01a3-c6f6-4c16-853a-7d518bc8131a');
  me_id = create_measurable_element('The facility provides services for OPD procedures', 'A1.13', std_id,
                                    '1a986ed0-5c60-478e-9b5a-5d83107a3534');
  me_id = create_measurable_element('Services are available for the time period as mandated', 'A1.14', std_id,
                                    'cd397749-ccd9-47a8-bf5a-7dd2c0dc8d50');
  checkpoint_id = create_checkpoint('Labour room service are functional 24X7', '9eef3eb5-580b-404e-87ab-5eb4132bb04f',
                                    me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility provides services for Super specialties, as mandated', 'A1.15',
                                    std_id, '04255ffe-11c7-4ba0-b16f-0396f982962b');
  me_id = create_measurable_element('The facility provides Accident & Emergency Services', 'A1.16', std_id,
                                    '06fdf7ad-3dc2-4b96-a4fb-74886cb57247');
  me_id = create_measurable_element('The facility provides Intensive care Services', 'A1.17', std_id,
                                    '4278341c-4dcd-4918-a500-254a3cf60c4d');
  me_id = create_measurable_element('The facility provides Blood bank & transfusion services', 'A1.18', std_id,
                                    '5bf225a0-a3dd-434c-a775-4c552452ed1c');
  std_id = create_standard('The facility provides RMNCHA Services', 'A2', aoc_id,
                           '175a67a0-c994-4661-ac64-6d69343bc5d0');
  me_id = create_measurable_element('The facility provides Reproductive health  Services', 'A2.1', std_id,
                                    'b90aa1f1-c02a-4b3d-8e80-c72f9b0cd62d');
  checkpoint_id = create_checkpoint('Availability of Post partum sterilization services',
                                    'cd8cc4a5-3acc-4baa-a29e-c985d961d15b', me_id, checklist_id, 'PPIUD insertion',
                                    TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('The facility provides Maternal health Services', 'A2.2', std_id,
                                    'b2a9acff-550f-4c81-9721-335465084cb9');
  checkpoint_id = create_checkpoint('Vaginal Delivery', '52092206-6d64-49a7-935d-23bd38f01998', me_id, checklist_id,
                                    'Term, post Date and pre term', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Assisted Delivery', '8ed63e56-8f2e-490a-a7ef-85e19eda9ac4', me_id, checklist_id,
                                    'Forceps delivery and vacuum delivery', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Management of Postpartum Haemorrhage', '41255a76-eeb3-4e56-b7c0-c9c2b4e77c5c',
                                    me_id, checklist_id, 'Medical /Surgical', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Management of Retained Placenta', '970b3669-baa6-4e3d-a45b-cf93d18e19d0', me_id,
                                    checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Septic Delivery', '3ed8c766-f744-4bd2-8933-51a8bf8e5d78', me_id, checklist_id, '',
                                    TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Delivery of   HIV positive PW', 'a5655f02-dd45-4d28-8566-7becda51a042', me_id,
                                    checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Management of PIH/Eclampsia/ Pre eclampsia',
                                    'f7965409-9c82-4af8-8332-9f4c2aa875f9', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Initial Diagnosis and management of MTP and Ectopics',
                                    '57da1b45-e391-42af-b096-1149fef545cb', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility provides Newborn health  Services', 'A2.3', std_id,
                                    '639b2545-caf0-4f1f-85ad-467f624207ee');
  checkpoint_id = create_checkpoint('Availability of New born resuscitation', '4b76c17f-9a15-49b1-9b09-81b57f8d8c25',
                                    me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Essential new born care', 'de3e32a3-baeb-423c-a21d-6be6167ddceb',
                                    me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('The facility provides Child health Services', 'A2.4', std_id,
                                    '70120efd-e046-4c3b-a5e7-7f17017c2dd8');
  me_id = create_measurable_element('The facility provides Adolescent health Services', 'A2.5', std_id,
                                    'e0ec0d51-b708-4ef3-9064-dd70bbefa8c6');
  std_id = create_standard('The facility Provides diagnostic Services', 'A3', aoc_id,
                           '5e2efcd1-c8cf-4956-b214-c984651469fc');
  me_id = create_measurable_element('The facility provides Radiology Services', 'A3.1', std_id,
                                    'f783646e-80c7-4c97-9c57-ceffb4b82b44');
  checkpoint_id = create_checkpoint('Availability of dedicated services for  USG',
                                    'fac2dd92-b447-4bf1-bdbb-2daef8fd2171', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility Provides Laboratory Services', 'A3.2', std_id,
                                    '70514a54-1359-4c5b-b2ef-31083cac9865');
  checkpoint_id = create_checkpoint('Availability of point of care diagnostic test',
                                    'f0ce2bcd-d7fa-48b0-9139-d543f18bcd0b', me_id, checklist_id,
                                    'HIV, Hb% , Random blood sugar /as per state guideline', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('The facility provides other diagnostic services, as mandated', 'A3.3', std_id,
                                    'd2834308-42e4-4788-979d-632aacfa98f1');
  std_id = create_standard('The facility provides services as mandated in national Health Programmes/ state scheme',
                           'A4', aoc_id, 'e9e52e7f-e396-4ffa-9c12-51af1f4f1ea8');
  me_id = create_measurable_element(
      'The facility provides services under National Vector Borne Disease Control Programme as per guidelines', 'A4.1',
      std_id, '8944eb7d-e37f-442d-8f38-c0b666a12928');
  me_id = create_measurable_element(
      'The facility provides services under Revised National TB Control Programme as per guidelines', 'A4.2', std_id,
      '63b3ff97-c1bc-4ffd-b8f4-ce68960f9f72');
  me_id = create_measurable_element(
      'The facility provides services under National Leprosy Eradication Programme as per guidelines', 'A4.3', std_id,
      '313e49ad-80b8-4ab2-970d-8b174f595e42');
  me_id = create_measurable_element(
      'The facility provides services under National AIDS Control Programme as per guidelines', 'A4.4', std_id,
      'e3ebab00-9b2f-42a3-94a4-8da16ce54c53');
  me_id = create_measurable_element(
      'The facility provides services under National Programme for prevention and control of Blindness as per guidelines',
      'A4.5', std_id, 'c816a3ec-657a-46fe-b6cc-8b159f896483');
  me_id = create_measurable_element('The facility provides services under Mental Health Programme  as per guidelines',
                                    'A4.6', std_id, 'd5337fd2-24e9-448f-8bfa-fe18364d145f');
  me_id = create_measurable_element(
      'The facility provides services under National Programme for the health care of the elderly as per guidelines',
      'A4.7', std_id, '6c941b74-01a3-418d-bef1-50c5e668c08a');
  me_id = create_measurable_element(
      'The facility provides services under National Programme for Prevention and control of Cancer, Diabetes, Cardiovascular diseases & Stroke (NPCDCS)  as per guidelines',
      'A4.8', std_id, '3ef8e5e7-9ad0-42a8-91b3-3d102c88dea3');
  me_id = create_measurable_element(
      'The facility Provides services under Integrated Disease Surveillance Programme as per Guidelines', 'A4.9',
      std_id, '9abe9742-9350-4e58-9fbd-ff17165780fe');
  me_id = create_measurable_element('The facility provide services under National health Programme for deafness',
                                    'A4.10', std_id, '764de437-cc62-4b4f-9e9f-6162424600c1');
  me_id = create_measurable_element('The facility provides services as per State specific health programmes', 'A4.11',
                                    std_id, 'ff741740-fc02-47c4-9136-e40bc19a568b');
  std_id = create_standard('The facility provides support services', 'A5', aoc_id,
                           '84e8f23f-1a18-4bfc-a844-20ba8ec3bb83');
  me_id = create_measurable_element('The facility provides dietary services', 'A5.1', std_id,
                                    'cb235193-73c9-4f97-a494-8c755c431d99');
  me_id = create_measurable_element('The facility provides laundry services', 'A5.2', std_id,
                                    '1c079ad9-eac8-400b-a8da-83a959fb045d');
  me_id = create_measurable_element('The facility provides security services', 'A5.3', std_id,
                                    '9ae7e055-bcdf-4daa-b72b-5f71df94445d');
  me_id = create_measurable_element('The facility provides housekeeping services', 'A5.4', std_id,
                                    '4c591fb4-503f-4363-a152-68f728f234ad');
  me_id = create_measurable_element('The facility ensures maintenance services', 'A5.5', std_id,
                                    '83a80bd6-e434-480c-9a84-a1bae436898f');
  me_id = create_measurable_element('The facility provides pharmacy services', 'A5.6', std_id,
                                    'cf1d73c7-127d-43dd-8d22-bf6d9c62c2b8');
  me_id = create_measurable_element('The facility has services of medical record department', 'A5.7', std_id,
                                    '584e2b89-e134-4f74-8d81-764bc9b92cd8');
  std_id = create_standard('Health services provided at the facility are appropriate to community needs.', 'A6', aoc_id,
                           'aa0a4ca5-b4f2-40a7-a984-11c05032a175');
  me_id = create_measurable_element(
      'The facility provides curatives & preventive services for the health problems and diseases, prevalent locally.',
      'A6.1', std_id, '408f762f-ef58-4526-9a5c-119a38abae4b');
  me_id = create_measurable_element(
      'There is process for consulting community/ or their representatives when planning or revising scope of services of the facility',
      'A6.2', std_id, '75832e8f-f801-4a78-87ab-c35a9b28969a');
  aoc_id = create_area_of_concern('Patient Rights', 'B', '80de038d-30e9-4919-af88-029408979699');
  std_id = create_standard(
      'The facility provides the information to care seekers, attendants & community about the available  services  and their modalities',
      'B1', aoc_id, '474b5a99-4e31-455f-b0fc-55b6e9825c70');
  me_id = create_measurable_element('The facility has uniform and user-friendly signage system', 'B1.1', std_id,
                                    '3f05a851-9bdd-4683-8fb1-a760b222e428');
  checkpoint_id = create_checkpoint('Availability  departmental signage''s', 'cc726c90-d79d-4c2e-9b10-b5f7d74e676b',
                                    me_id, checklist_id, '(Numbering, main department and internal sectional signage',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Directional signage for  department is  displayed',
                                    '217f9952-7483-4275-af43-9bd4d3fc64a6', me_id, checklist_id,
                                    'Direction is displayed from main gate to direct.', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Restricted area signage displayed', '80d06035-9e39-4995-a847-88bd5cb6f496', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility displays the services and entitlements available in its departments',
                                    'B1.2', std_id, 'd1a8367d-47a6-4f68-b326-fc143e7832a6');
  checkpoint_id = create_checkpoint('Entitlements under JSSK Displayed', '6b8a2eb0-1120-4b4a-92ce-9ef4141466f6', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Entitlement under JSY displayed', '704674bb-b224-4b96-8dce-ade55b5d0cc1', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Name of doctor and Nurse on duty  are displayed and updated',
                                    'ee84fee2-9570-4f2e-a605-e904c872e0e8', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Contact details of referral transport / ambulance displayed',
                                    '15563c22-c342-4e44-bd5c-70bcef131d47', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Services provision of labour room  are displayed at the entrance',
                                    '33f77fdb-04aa-4708-8993-5e9a06a9baff', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility has established citizen charter, which is followed at all levels',
                                    'B1.3', std_id, 'f1c29903-7210-41ae-91cf-c719cebe2497');
  me_id = create_measurable_element('User charges are displayed and communicated to patients effectively', 'B1.4',
                                    std_id, 'da210e2e-4be1-4c8f-a3df-2f4d849ebf99');
  me_id = create_measurable_element(
      'Patients & visitors are sensitised and educated through appropriate IEC / BCC approaches', 'B1.5', std_id,
      'a3e4d613-26fa-463d-8f13-2718212d0ba8');
  checkpoint_id = create_checkpoint('IEC Material is displayed', '69ed40a4-f9d9-4d47-89dd-a66842f904d6', me_id,
                                    checklist_id,
                                    'Breast feeding, kangaroo care, family planning etc (Pictorial and chart ) in circulation area',
                                    TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('Information is available in local language and easy to understand', 'B1.6', std_id,
                                    '208f4674-d70b-46bb-af59-be1557f62302');
  checkpoint_id = create_checkpoint('Signage''s and information  are available in local language',
                                    'f920b6ac-3298-4acc-8671-b41489073003', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element(
      'The facility provides information to patients and visitor through an exclusive set-up.', 'B1.7', std_id,
      '8bb787ff-aa45-4241-b628-088401f56b48');
  checkpoint_id = create_checkpoint('Availability of Enquiry Desk with dedicated staff',
                                    'd408940b-c220-4310-be1d-9e79ddec18c4', me_id, checklist_id,
                                    'Enquiry desk serving both maternity ward and labour', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility ensures access to clinical records of patients to entitled personnel',
                                    'B1.8', std_id, '9de491c2-31ad-4f1a-b0dc-a2953cc14205');
  std_id = create_standard(
      'Services are delivered in a manner that is sensitive to gender, religious and cultural needs, and there are no barrier on account of physical  economic, cultural or social reasons.',
      'B2', aoc_id, 'a7d8d024-34da-49d1-9028-753d87f00a8b');
  me_id = create_measurable_element('Services are provided in manner that are sensitive to gender', 'B2.1', std_id,
                                    'c6cae2cb-7e76-4b65-9282-81e9d544ff0a');
  checkpoint_id = create_checkpoint('Only on duty  staff is allowed in the labour room when it is occupied',
                                    '7711a90e-3bcc-448c-ae4d-1dd4822fe157', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of female staff if a male doctor examine a female patients',
                                    '020a5e01-02b4-495e-9ebd-52df7f1743bb', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element(
      'Religious and cultural preferences of patients and attendants are taken into consideration while delivering services',
      'B2.2', std_id, '5ba5ec56-d998-4bbe-a481-e20efa2962a2');
  me_id = create_measurable_element(
      'Access to facility is provided without any physical barrier & and friendly to people with disabilities', 'B2.3',
      std_id, '39912137-f5d0-4812-8435-ba0838fb327f');
  checkpoint_id = create_checkpoint('Availability of Wheel chair or stretcher for easy Access to the labour room',
                                    '3af5b184-e583-4ba9-9844-c6f33fd21bed', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of ramps and railing', '5925f61f-1dd8-4e31-8801-9cad4096f9ab', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Labour room is located at ground floor', 'fc691ee4-44a7-4b72-a0cc-0d6bdff3394f',
                                    me_id, checklist_id,
                                    'If not located on the ground floor availability of the ramp / lift with person for shifting',
                                    TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('There is no discrimination on basis of social and economic status of the patients',
                                    'B2.4', std_id, '5046e50f-2008-4175-a124-7632b7dbd306');
  me_id = create_measurable_element(
      'There is affirmative actions to ensure that vulnerable sections can access services', 'B2.5', std_id,
      'f3572276-8ead-4b52-81c1-cf91d3510c5c');
  std_id = create_standard(
      'The facility maintains privacy, confidentiality & dignity of patient, and has a system for guarding patient related information.',
      'B3', aoc_id, '9bfcb455-47ac-4bc1-bad7-a3032f294ab4');
  me_id = create_measurable_element('Adequate visual privacy is provided at every point of care', 'B3.1', std_id,
                                    'f65af7a3-9032-42b5-a727-fafe7b956c61');
  checkpoint_id = create_checkpoint('Availability of screen/ partition at delivery tables',
                                    'bf0c4e6a-f564-40e3-a555-2545a62a37c9', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Curtains / frosted glass have been provided at windows',
                                    '2de00722-287c-43db-bcdc-336fd1b55cae', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Confidentiality of patients records and clinical information is maintained',
                                    'B3.2', std_id, 'ec38d2fc-8cce-4e86-b24a-38a25b8a026f');
  checkpoint_id = create_checkpoint('Patient Records are kept at secure place beyond access to general staff/visitors',
                                    '4c6d13f7-6579-45d6-88ad-78820645e84a', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element(
      'The facility ensures the behaviours of staff is dignified and respectful, while delivering the services', 'B3.3',
      std_id, '97f56a38-410b-459e-9e25-a3bb19a5ab5d');
  checkpoint_id = create_checkpoint('Behaviour of staff is empathetic and curteous',
                                    '0cc6194b-ebef-4472-b962-33ea8cfeafee', me_id, checklist_id, '', TRUE, FALSE, TRUE,
                                    FALSE);
  me_id = create_measurable_element(
      'The facility ensures privacy and confidentiality to every patient, especially of those conditions having social stigma, and also safeguards vulnerable groups',
      'B3.4', std_id, 'd1c34562-3860-4c02-b6fd-bc8c3d1bcc15');
  checkpoint_id = create_checkpoint(
      'HIV status of patient is not disclosed except to staff that is directly involved in care',
      '4b6be744-5bb9-43b1-b967-c3f26ee4bb96', me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  std_id = create_standard(
      'The facility has defined and established procedures for informing patients about the medical condition, and involving them in treatment planning, and facilitates informed decision making',
      'B4', aoc_id, 'e511d4bc-567b-4b70-a887-fbce1e77f43a');
  me_id = create_measurable_element(
      'There is established procedures for taking informed consent before treatment and procedures', 'B4.1', std_id,
      '4d02d399-1b6b-4565-98c6-1caace7f83ce');
  checkpoint_id = create_checkpoint('General consent is taken before delivery', '31aaeae0-5958-45bc-8a0e-f3586f7c58af',
                                    me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('Patient is informed about his/her rights  and responsibilities', 'B4.2', std_id,
                                    '81f14cef-4eab-412a-be28-5a1105459c4a');
  me_id = create_measurable_element('Staff are aware of Patients rights responsibilities', 'B4.3', std_id,
                                    '751ec456-5ff4-4b22-a768-c38339c7fb10');
  me_id = create_measurable_element('Information about the treatment is shared with patients or attendants, regularly',
                                    'B4.4', std_id, 'cfdafa93-beac-497a-9c6a-c4a7652562de');
  checkpoint_id = create_checkpoint(
      'Labour room has system in place to involve patient relative in decision making about pregnant women treatment',
      '760cb4f9-0971-4343-85b4-253c01e5ec94', me_id, checklist_id, '', FALSE, FALSE, TRUE, FALSE);
  me_id = create_measurable_element('The facility has defined and established grievance redressal system in place',
                                    'B4.5', std_id, 'c0cf58cc-a302-4b1e-9361-06edf46f728e');
  checkpoint_id = create_checkpoint(
      'Availability of complaint box and display of process for grievance re redressal and whom to contact is displayed',
      'fe42afae-b099-4be1-a878-95f77e012e81', me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  std_id = create_standard(
      'The facility ensures that there are no financial barrier to access, and that there is financial protection given from the cost of hospital services.',
      'B5', aoc_id, '833cba3b-083a-4892-be48-dbe08c2a19c3');
  me_id = create_measurable_element(
      'The facility provides cashless services to pregnant women, mothers and neonates as per prevalent government schemes',
      'B5.1', std_id, '376454c0-48f5-4c97-945e-d7d74f2f9260');
  checkpoint_id = create_checkpoint('Drugs and consumables under JSSK are available free of cost',
                                    '57a2fef5-1e99-4f72-a4e7-6c37178e626c', me_id, checklist_id, '', FALSE, TRUE, TRUE,
                                    FALSE);
  me_id = create_measurable_element('The facility ensures that drugs prescribed are available at Pharmacy and wards',
                                    'B5.2', std_id, 'ba94a9aa-788e-4a85-8550-2f52434adf48');
  checkpoint_id = create_checkpoint(
      'Check that  patient party has not spent on purchasing drugs or consumables from outside.',
      'e86c2ec5-5cfa-400e-9f12-8f5f99812902', me_id, checklist_id, '', FALSE, TRUE, TRUE, FALSE);
  me_id = create_measurable_element(
      'It is ensured that facilities for the prescribed investigations are available at the facility', 'B5.3', std_id,
      '95e686dc-6fd4-435f-8b17-67be6d19b68d');
  checkpoint_id = create_checkpoint('Check that  patient party has not spent on diagnostics from outside.',
                                    '334c5924-4f49-4c18-99cf-2b27fe6674a3', me_id, checklist_id, '', FALSE, TRUE, TRUE,
                                    FALSE);
  me_id = create_measurable_element(
      'The facility provide free of cost treatment to Below poverty line patients without administrative hassles',
      'B5.4', std_id, 'f3f4ce74-903d-419d-a277-23649385bc93');
  me_id = create_measurable_element(
      'The facility ensures timely reimbursement of financial entitlements and reimbursement to the patients', 'B5.5',
      std_id, '88405871-3398-4e9a-8843-bf5a25325731');
  checkpoint_id = create_checkpoint('If any other expenditure occurred it is reimbursed from hospital',
                                    '211b8d47-b13d-44b2-be3a-047eee8e24db', me_id, checklist_id, '', FALSE, TRUE, TRUE,
                                    TRUE);
  me_id = create_measurable_element(
      'The facility ensure implementation of health insurance schemes as per National /state scheme', 'B5.6', std_id,
      '6cdb297d-c387-411d-91a9-56c6b28c3538');
  aoc_id = create_area_of_concern('Inputs', 'C', 'f68ac022-e752-43b6-95a4-53f3683da582');
  std_id = create_standard(
      'The facility has infrastructure for delivery of assured services, and available infrastructure meets the prevalent norms',
      'C1', aoc_id, '8da36ae1-2a95-465e-97d8-fc25c21d6eea');
  me_id = create_measurable_element('Departments have adequate space as per patient or work load', 'C1.1', std_id,
                                    '8be2e055-8fbc-4783-a607-21d94c9d20e3');
  checkpoint_id = create_checkpoint('Adequate space as per delivery load', 'd367d540-7dd8-4198-ab4f-b20c06b0e187',
                                    me_id, checklist_id,
                                    'One labour table requires 10X10 sqft of space,  Every labour table should have space for vertical trolley with space for six trays',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Waiting area for attendants/ASHA',
                                    '1e5c8285-86a3-4b24-9fb5-cd7996ea8c53', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Patient amenities are provide as per patient load', 'C1.2', std_id,
                                    '5399f6a6-253a-428b-8683-4216cfb43f5c');
  checkpoint_id = create_checkpoint('Attached toilet and bathroom facility available',
                                    '2759d1c3-1253-470f-b8b5-4d61d13d69eb', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of Hot water facility', 'a06172c0-36e1-47d9-9d07-fe3236939bf4', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Drinking water', '3ead39a0-eb02-4bc5-9109-54a5616de149', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Changing area', '0561c768-4ac5-48b8-87c8-0c3641fabcbc', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('Departments have layout and demarcated areas as per functions', 'C1.3', std_id,
                                    '8d2f74e6-2f10-4d77-8ce1-c597858b9343');
  checkpoint_id = create_checkpoint('Delivery unit has dedicated Receiving area',
                                    '719bd537-8bbf-4ae5-a239-43f19f0fe976', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of Examination Room', 'fa15a8eb-0de8-482b-a94c-5bdeb8b16309', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Pre delivery room', 'b0c4f328-5208-4015-b364-814e486f882c', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Delivery room', '3d3c2564-c11b-4d24-b14e-2424377453c2', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability  of Post delivery observation room',
                                    '80c60875-6ecf-4865-9f29-028c39df6c3b', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Dedicated nursing station within or proximity labour room',
                                    '9739e026-57c4-43a2-a15b-24101154fe04', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Area earmarked for newborn care Corner', 'a29aa228-b37d-4a87-b9fa-c9f5f82b8f89',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Dedicated Eclampsia room  available', '1d31cee4-c625-40d7-9ff4-50e91374f15e',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Dedicated Septic Labour Room with NBCC', 'd7135da9-18f2-4f16-aec5-e9c55ab6382c',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Preparation of medicine and injection area',
                                    'bc45208d-b455-418c-9bcd-414f8c4b5871', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of dirty utility room', '4571b23e-cc74-45c3-a10e-fbd0329cbde3', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of store', '70253430-70b1-4067-93a0-5f1cbbac5041', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element(
      'The facility has adequate circulation area and open spaces according to need and local law', 'C1.4', std_id,
      '72f5cd14-3021-43fe-b4da-463af0c4ba35');
  checkpoint_id = create_checkpoint(
      'Corridors connecting labour room are broad enough to manage stretcher and trolleys',
      '80e48d54-bc2f-486f-9de6-5777af3949eb', me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility has infrastructure for intramural and extramural communication',
                                    'C1.5', std_id, '10c3e0fe-e5df-4f62-a600-5555fbbd3a99');
  checkpoint_id = create_checkpoint('Availability of functional telephone and Intercom Services',
                                    'ca5e2f85-0d67-4a94-9804-44861a891d56', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Service counters are available as per patient load', 'C1.6', std_id,
                                    'bdc3c3a5-db30-4b77-8695-49c385e0b987');
  checkpoint_id = create_checkpoint('Availability of labour tables as per delivery load',
                                    '73b8dbdf-f94f-497e-896b-53ad99b6dd77', me_id, checklist_id,
                                    'At least 2 labour table for 100 deliveries per month (Minimum 4)', TRUE, FALSE,
                                    FALSE, FALSE);
  me_id = create_measurable_element(
      'The facility and departments are planned to ensure structure follows the function/processes (Structure commensurate with the function of the hospital)',
      'C1.7', std_id, 'ab46c56a-1db1-4fab-90d4-1b821d45e3eb');
  checkpoint_id = create_checkpoint('Labour room is in Proximity and function linkage with OT',
                                    '00017b5b-b3e1-464f-904c-009bd2ed16ce', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Labour room is in proximity a proximity and functional linkage with SNCU',
                                    'e0762878-9406-4778-a9d9-0a5e5a3e6e76', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Unidirectional  flow of care', 'ab64385f-a5e4-4621-a36f-1ea554b1b5f9', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  std_id = create_standard('The facility ensures the physical safety of the infrastructure.', 'C2', aoc_id,
                           '5b18726f-5df8-4a7c-807d-979a9d679f09');
  me_id = create_measurable_element('The facility ensures the seismic safety of the infrastructure', 'C2.1', std_id,
                                    '92d2f6fa-3ad7-49b3-aeb1-398b75fd020c');
  checkpoint_id = create_checkpoint('Non structural components are properly secured',
                                    'ce46fcb4-ca78-45f6-9f9b-35bd5d4d519d', me_id, checklist_id,
                                    'Check for fixtures and furniture like cupboards, cabinets, and heavy equipments , hanging objects are properly fastened and secured',
                                    TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element(
      'The facility ensures safety of lifts and lifts have required certificate from the designated bodies/ board',
      'C2.2', std_id, '62519e7d-df37-4a42-84e2-03d975e79595');
  me_id = create_measurable_element('The facility ensures safety of electrical establishment', 'C2.3', std_id,
                                    '00096122-b720-4b5e-b1b1-08867697564f');
  checkpoint_id = create_checkpoint('Labour room does not have temporary connections and loosely hanging wires',
                                    '2a9cdc23-b9b3-4187-8f7a-8d1b1eb8752c', me_id, checklist_id,
                                    'Switch Boards other electrical installations are intact', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Stabilizer is provided for Radiant warmer', '4dc4caab-79fd-412b-82e6-46e03c327bff',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('Physical condition of buildings are safe for providing patient care', 'C2.4',
                                    std_id, '97aa9319-ec6c-4dff-81ed-ead882579631');
  checkpoint_id = create_checkpoint('Floors of the labour room are non slippery and even',
                                    '9d9328a1-845f-433b-9173-5cb5ce683e3a', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Windows have grills and wire meshwork', 'e578a701-2c84-4fd4-b5fb-2adf8a4eb0e0',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  std_id = create_standard('The facility has established Programme for fire safety and other disaster', 'C3', aoc_id,
                           '35960a4f-b112-4ab4-b468-bea8bf59e052');
  me_id = create_measurable_element('The facility has plan for prevention of fire', 'C3.1', std_id,
                                    '941eb6f8-0914-4c5a-a167-6efc68d5280c');
  checkpoint_id = create_checkpoint(
      'Labour room has sufficient fire  exit to permit safe escape to its occupant at time of fire',
      '53f86e1b-497f-4dcf-b8da-b7d42eeba640', me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint(
      'Check the fire exits are clearly visible and routes to reach exit are clearly marked.',
      'cc47b6a6-02e3-46a9-a915-b96619da6166', me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility has adequate fire fighting Equipment', 'C3.2', std_id,
                                    '327a8d0c-f78e-43c6-8d8a-bfc72346d650');
  checkpoint_id = create_checkpoint(
      'Labour  room  has installed fire Extinguisher  that is Class A , Class B, C type or ABC type',
      '24e50ba5-24b9-47ee-8fd4-3a22e092c9e8', me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint(
      'Check the expiry date for fire extinguishers are displayed on each extinguisher as well as due date for next refilling is clearly mentioned',
      '331e4bd3-868e-4930-adf0-7b9c7f1b3fa9', me_id, checklist_id, '', TRUE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element(
      'The facility has a system of periodic training of staff and conducts mock drills regularly for fire and other disaster situation',
      'C3.3', std_id, 'e69ba6d8-18ad-4055-b208-312e20f6683d');
  checkpoint_id = create_checkpoint(
      'Check for staff competencies for operating fire extinguisher and what to do in case of fire',
      'a8bf6143-aced-49b9-8214-b5958815bbd5', me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  std_id = create_standard(
      'The facility has adequate qualified and trained staff,  required for providing the assured services to the current case load',
      'C4', aoc_id, '436efa2a-aa24-4963-92fb-9a3492f63ef8');
  me_id = create_measurable_element('The facility has adequate specialist doctors as per service provision', 'C4.1',
                                    std_id, '160fb8a8-80b1-4e10-b0ed-25864f03538e');
  checkpoint_id = create_checkpoint('Availability of Ob&G specialist on duty and on call paediatrician',
                                    'cb06364d-ddfc-40e2-a52d-cb4a735fc1c9', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    TRUE);
  me_id = create_measurable_element(
      'The facility has adequate general duty doctors as per service provision and work load', 'C4.2', std_id,
      'ddd9eeb8-f586-4039-9910-2f2eeb46fad0');
  checkpoint_id = create_checkpoint('Availability of General duty doctor at  all time at labour room',
                                    '815b984f-3142-4b6a-b82f-244c9e6fbeae', me_id, checklist_id,
                                    'At least One per shift', TRUE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('The facility has adequate nursing staff as per service provision and work load',
                                    'C4.3', std_id, '74182567-ddda-4428-b27d-d16828d275bb');
  checkpoint_id = create_checkpoint('Availability of Nursing staff /ANM', '55ef8a19-c9cd-472d-ada3-a8789b5e7726', me_id,
                                    checklist_id, 'At least Three per shift', TRUE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility has adequate technicians/paramedics as per requirement', 'C4.4',
                                    std_id, 'ab659ae4-bea5-4076-9609-bf2a6ac3554b');
  me_id = create_measurable_element('The facility has adequate support / general staff', 'C4.5', std_id,
                                    '969ef45d-dee4-4871-9e9d-c4bf20dfd32f');
  checkpoint_id = create_checkpoint('Availability of labour room attendants/ Birth Companion',
                                    '652315d0-ac39-432d-a0c0-d3d2c5eb5848', me_id, checklist_id,
                                    'At least 1 sanitary worker and 1 ayah per shift', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of dedicated  female security staff',
                                    '8bbf11b8-cc75-49bf-a60c-0fa456403049', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('The staff has been provided required training / skill sets', 'C4.6', std_id,
                                    '50f9d447-a7e0-4bcd-849f-1d8645b6ad94');
  checkpoint_id = create_checkpoint('Navjat Shishu Surkasha Karyakarm (NSSK) training',
                                    '6bc15ef3-f4eb-44e7-b59c-522703411f8b', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Skilled birth Attendant (SBA)', 'c6cc21c4-ec70-4163-aa13-08efb57fa1bf', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Biomedical Waste Management', 'aeae8213-93d1-4793-8c8e-f293c4519da9', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Infection control and hand hygiene', '4304ed8b-7c4c-4b41-80af-61d67149959b', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Patient safety', 'f62b7374-6947-404f-b016-de4ac452c73d', me_id, checklist_id, '',
                                    FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The Staff is skilled as per job description', 'C4.7', std_id,
                                    'd66858c2-a713-47ab-9ae2-0df66ef1bd50');
  checkpoint_id = create_checkpoint('Nursing staff is skilled  for operating radiant warmer',
                                    '3df5349d-5834-4592-ad8f-7997f4ed159f', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Nursing staff is skilled  for resuscitation',
                                    'b7dc9eeb-8bbf-4c25-afcd-1efb6a0b4095', me_id, checklist_id,
                                    'Newborn as well as Mother', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Nursing staff is skilled identifying and managing complication',
                                    '3a6cf2e5-4cad-45ed-a6ae-f8521bf9db19', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Counsellor is skilled for postnatal counselling',
                                    '8b07ada1-73fe-4e93-80c9-ca009dd3fc3f', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Nursing Staff is skilled for maintaining clinical records including partograph',
                                    '766c520f-75a2-4538-8fa2-4038fa5e9dbd', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  std_id = create_standard('The facility provides drugs and consumables required for assured services.', 'C5', aoc_id,
                           'a7bf4fc1-6951-418e-8087-d2a1d3d18171');
  me_id = create_measurable_element('The departments have availability of adequate drugs at point of use', 'C5.1',
                                    std_id, '0f462e37-5371-45f4-ade9-829d3f4aba11');
  checkpoint_id = create_checkpoint('Availability of uterotonic Drugs', 'cb0c00ba-284c-498c-8e0e-c55d07e237c1', me_id,
                                    checklist_id, 'Inj Oxytocin 10 IU (to be kept in fridge)', TRUE, FALSE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Availability of Antibiotics', '8678f8a1-396c-4063-aa7b-e5234ad158d3', me_id,
                                    checklist_id, 'Cap Ampicillin 500mg, Tab Metronidazole 400mg, Gentamicin,', TRUE,
                                    FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of Antihypertensive', 'e637066e-c0ab-4c77-a950-42989570625a', me_id,
                                    checklist_id, 'Tab Misprostol 200mg, Nefedipine,', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availabity of analgesics and antipyretics', 'a32bc542-6d82-44cd-9bac-1334123e0373',
                                    me_id, checklist_id, 'Tab Paracetamol, Tab Ibuprofen', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of IV Fluids', '3a954524-d819-4ec2-9626-d061328339a6', me_id,
                                    checklist_id, 'IV fluids, Normal saline, Ringer lactate,', TRUE, FALSE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Availability of local anaesthetics', '3d5343e6-d941-42d5-a3d2-5fe6a7c1b40c', me_id,
                                    checklist_id, 'Inj Xylocaine 2%,', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Others', '8ae23c02-800a-4f98-bcba-1ee42e317604', me_id, checklist_id,
                                    'Tab B complex, Inj Betamethasone, Inj Hydralazine,  methyldopa, (Nevirapine and other HIV  drugs)',
                                    TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of emergency drugs', '1af3defe-7541-48ed-87dd-650e32bebc83', me_id,
                                    checklist_id,
                                    'Inj Magsulf 50%, Inj Calcium gluconate 10%, Inj Dexamethasone, inj Hydrocortisone, Succinate, Inj diazepam, inj Pheniramine maleate, inj Corboprost, Inj Fortwin, Inj Phenergen, Betamethasone, Inj Hydralazine, Nefedipine, Methyldopa,ceftriaxone',
                                    TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of drugs for newborn', '8517d304-2ce6-4c26-b257-431034b4731e', me_id,
                                    checklist_id, 'Vit K', TRUE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('The departments have adequate consumables at point of use', 'C5.2', std_id,
                                    '01ee489e-f039-4297-b937-ea6b311e2073');
  checkpoint_id = create_checkpoint('Availability of dressings and Sanitary pads',
                                    '98cdc11f-8a32-4439-b964-52c69a9974d6', me_id, checklist_id,
                                    'gauze piece and cotton swabs, sanitary pads, needle (round body and cutting), chromic catgut no. 0,',
                                    TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of syringes and IV Sets /tubes',
                                    'f6dd19b7-1f7f-496f-af11-198546a322c9', me_id, checklist_id,
                                    'Paediatric iv sets,urinery catheter,', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of Antiseptic Solutions', 'd70f85b9-2f29-4b0e-8657-0cf358708d01',
                                    me_id, checklist_id, 'Antiseptic lotion', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of consumables for new born care',
                                    '46e97379-41c1-4407-b952-f7f57dea3f59', me_id, checklist_id,
                                    'gastric tube and cord clamp, Baby ID tag', TRUE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element(
      'Emergency drug trays are maintained at every point of care, where ever it may be needed', 'C5.3', std_id,
      'ef7c9d6b-f1b2-4a8a-b7c1-91c8d0f47514');
  checkpoint_id = create_checkpoint('Emergency Drug Tray is maintained', '435e959d-c073-480d-979b-af3006fffe09', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, TRUE);
  std_id = create_standard('The facility has equipment & instruments required for assured list of services.', 'C6',
                           aoc_id, '32d1c8fd-a6d6-4db6-ad6e-b8d337e1a8f5');
  me_id = create_measurable_element('Availability of equipment & instruments for examination & monitoring of patients',
                                    'C6.1', std_id, 'fda6b8c3-fbff-4203-87e6-ae26326d6c22');
  checkpoint_id = create_checkpoint('Availability of functional Equipment  &Instruments for examination & Monitoring',
                                    '2dcf0552-85cf-4730-b0e0-97a7f9d1d0bb', me_id, checklist_id,
                                    'BP apparatus, stethoscope Thermometer, foetoscope/ Doppler, baby weighting scale, Wall clock (tracers)',
                                    TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element(
      'Availability of equipment & instruments for treatment procedures, being undertaken in the facility', 'C6.2',
      std_id, 'dda9b89e-17e6-4342-9350-0c7cfbb190fc');
  checkpoint_id = create_checkpoint('Availability of  instrument arranged in Delivery treys',
                                    '732a5e0b-0cc4-4182-9f91-fe7d229fd07b', me_id, checklist_id,
                                    'Scissor, Artery forceps, Cord clamp, Sponge holder, speculum, kidney tray,  bowl for antiseptic lotion,',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Delivery kits are in adequate numbers as per load',
                                    '1e8cdb5d-6112-499a-9492-01c5dd8f0c98', me_id, checklist_id,
                                    'As per delivery load and cycle time for processing of instrument', TRUE, FALSE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Instruments arranged  for Episiotomy  trays',
                                    '47ac894d-c050-47a2-818a-ae72542f0681', me_id, checklist_id,
                                    'Episiotomy scissor, kidney tray, artery forceps, allis forceps, sponge holder, toothed forceps, needle holder,thumb forceps,',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Baby tray', '00dcf94a-7ce8-4024-82de-fe11605bb30f', me_id,
                                    checklist_id,
                                    'Two pre warmed towels/sheets for wrapping the baby, mucus extractor, bag and mask (0 &1 no.), sterilized thread for cord/cord clamp, nasogastric tube,',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of instruments arranged for MVA/EVA tray',
                                    '0a8af265-8e65-42e7-93d3-db9e132dd3d5', me_id, checklist_id,
                                    'Speculum, anterior  vaginal wall retractor, posterior wall retractor, sponge holding forceps, MVA syringe, cannulas, MTP, cannulas, small bowl of antiseptic lotion,',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of instruments arranged for PPIUCD tray',
                                    '4c8d291b-7955-409c-99d0-966a6c2da246', me_id, checklist_id,
                                    'PPIUCD insertion forceps, CuIUCD 380A/Cu IUCD375 in sterile package', TRUE, FALSE,
                                    FALSE, FALSE);
  me_id = create_measurable_element(
      'Availability of equipment & instruments for diagnostic procedures being undertaken in the facility', 'C6.3',
      std_id, '379a84a3-6168-453f-a293-81e9ae4795b2');
  checkpoint_id = create_checkpoint('Availability of Point of care diagnostic instruments',
                                    'db82f242-541d-43f0-9292-16fcfe98aa1d', me_id, checklist_id,
                                    'Glucometer, Doppler and HIV rapid diagnostic kit', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element(
      'Availability of equipment and instruments for resuscitation of patients and for providing intensive and critical care to patients',
      'C6.4', std_id, 'ca0db97f-6ca2-4e62-b926-4fc16e92e030');
  checkpoint_id = create_checkpoint('Availability of resuscitation  Instruments  for Newborn Care',
                                    'a4b47283-4d44-4d16-bd72-b5fdf4d17f47', me_id, checklist_id,
                                    'Oxygen, Suction machine/ mucus sucker , radiant warmer, laryngoscope', TRUE, FALSE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of resuscitation  instrument for mother',
                                    '741bbc8b-12ca-4142-8489-c60cade087e5', me_id, checklist_id,
                                    'Suction machine, Oxygen, Adult bag and mask, mouth gag,', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Availability of Equipment for Storage', 'C6.5', std_id,
                                    '6bd56d7e-c9d7-42d3-acb1-69681b58d2e3');
  checkpoint_id = create_checkpoint('Availability of equipment for storage for drugs',
                                    '7f9398f0-ecb6-4351-b85b-cceeca67fdee', me_id, checklist_id,
                                    'Refrigerator, Crash cart/Drug trolley, instrument trolley, dressing trolley', TRUE,
                                    FALSE, FALSE, FALSE);
  me_id = create_measurable_element('Availability of functional equipment and instruments for support services', 'C6.6',
                                    std_id, '7c979f59-496e-4e59-8a82-9dafdd7c2b53');
  checkpoint_id = create_checkpoint('Availability of equipments for cleaning', '2665eb1a-fbc8-4295-ae3f-4e4c1f28b935',
                                    me_id, checklist_id,
                                    'Buckets for mopping, Separate mops for labour room and circulation area duster, waste trolley, Deck brush',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of equipment for sterilization and disinfection',
                                    'e4f48e92-e974-465f-9c26-3e9eaf55213c', me_id, checklist_id, 'Boiler/Autocalve',
                                    TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('Departments have patient furniture and fixtures as per load and service provision',
                                    'C6.7', std_id, '24d88f1a-4cdf-40c8-8261-676754b5894c');
  checkpoint_id = create_checkpoint('Availability of Delivery tables', 'a928dc26-794d-4b04-8b18-a28d8cc04a50', me_id,
                                    checklist_id, 'Steel Top', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of attachment/ accessories  with delivery table',
                                    '34898cd1-c53e-40f4-bac8-0941c469e6d6', me_id, checklist_id,
                                    'Hospital graded Mattress, IV stand, Kelly''s pad,  support for delivery tables, Macintosh, foot step, Bed pan',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of fixture', '3b3800ca-9a05-494d-8cac-858259a15944', me_id,
                                    checklist_id,
                                    'Wall clock with Second arm Lamps- wall mounted /side, electrical fixture for equipments like radiant warmer, suction .',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Furniture', 'aae8677f-da85-49aa-aa01-ba242a238c18', me_id,
                                    checklist_id, 'Cupboard, Table, chair, Counter.', TRUE, FALSE, FALSE, FALSE);
  aoc_id = create_area_of_concern('Support Services', 'D', '5a1f8ebb-5d46-495c-b4ca-c459125a1dc2');
  std_id = create_standard(
      'The facility has established Programme for inspection, testing and maintenance and calibration of Equipment.',
      'D1', aoc_id, '43b66e79-53ed-4b19-94de-19fedecd68cc');
  me_id = create_measurable_element('The facility has established system for maintenance of critical Equipment', 'D1.1',
                                    std_id, '4d404649-536d-43ba-baf4-61fa00d4160e');
  checkpoint_id = create_checkpoint('All equipments are covered under AMC including preventive maintenance',
                                    '060604cb-7b59-47cf-bbb4-58acaae034bd', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('There is system of timely corrective  break down maintenance of the equipments',
                                    '8a685436-ab86-4e22-97b4-0fd4ab944fe4', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element(
      'The facility has established procedure for internal and external calibration of measuring Equipment', 'D1.2',
      std_id, '362377b5-a0af-4dbd-88ef-6429bd842671');
  checkpoint_id = create_checkpoint('All the measuring equipments/ instrument  are calibrated',
                                    '64a35cd2-4399-47d1-af13-f71c2925facd', me_id, checklist_id,
                                    'BP apparatus, thermometers, weighing scale , radiant warmer Etc are calibrated',
                                    TRUE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('Operating and maintenance instructions are available with the users of equipment',
                                    'D1.3', std_id, '99bebf7c-fcdc-4449-bf21-0f7ee3ffe612');
  checkpoint_id = create_checkpoint(
      'Up to date instructions for operation and maintenance of equipments are readily available with labour room staff.',
      'd0fcc9ae-5b2f-4c1b-9cd5-3ddbc9156b3f', me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  std_id = create_standard(
      'The facility has defined procedures for storage, inventory management and dispensing of drugs in pharmacy and patient care areas',
      'D2', aoc_id, '8397a09d-bbec-495b-bd88-a796a4ffe269');
  me_id = create_measurable_element(
      'There is established procedure for forecasting and indenting drugs and consumables', 'D2.1', std_id,
      'aa6dbb9d-74eb-46b2-be1b-aa84f8be4e41');
  checkpoint_id = create_checkpoint('There is established system of timely  indenting of consumables and drugs',
                                    '0dd6a41c-b60c-48e4-8a35-0de29569ec5b', me_id, checklist_id, 'Stock level are daily updated
Requisition are timely placed', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility has establish procedure for procurement of drugs', 'D2.2', std_id,
                                    '97825935-b608-4f16-9de8-2f6c598c0a7e');
  me_id = create_measurable_element('The facility ensures proper storage of drugs and consumables', 'D2.3', std_id,
                                    '9fd6bf2b-2aa1-41ef-893d-ca0757363500');
  checkpoint_id = create_checkpoint('Drugs are stored in containers/tray/crash cart and are labelled',
                                    'a3222cf0-b011-460a-b8a8-29946a5b13be', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Empty and  filled cylinders are labelled', '50a6f1d2-020e-464e-aa65-198d4d16a5a3',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility ensures management of expiry and near expiry drugs', 'D2.4', std_id,
                                    'da53ce80-3ed6-4f15-9ad0-6f13cb47238e');
  checkpoint_id = create_checkpoint('Expiry dates'' are maintained at emergency drug tray',
                                    'c0e2069f-1281-4337-b430-b233511e0767', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('No expiry drug found', 'b06dba3f-92e8-40db-986c-daac7ea0a9f4', me_id, checklist_id,
                                    '', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Records for expiry and near expiry drugs are maintained for drug stored at department',
      '89fe01ba-ecd8-49ef-b463-638b5f46af7a', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('The facility has established procedure for inventory management techniques',
                                    'D2.5', std_id, '70f1d7ce-0d86-4f00-b1e5-0579e54411f3');
  checkpoint_id = create_checkpoint('There is practice of calculating and maintaining buffer stock',
                                    '837a25ab-ab76-4fec-95d9-51311e20841d', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Department maintained stock and expenditure register of drugs and consumables',
                                    'dd418fc3-5c91-4c1b-894b-73d1886cacfe', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element(
      'There is a procedure for periodically replenishing the drugs in patient care areas', 'D2.6', std_id,
      'a26d8fe0-36d2-4a82-a1a7-de6e5a5c1922');
  checkpoint_id = create_checkpoint('There is procedure for replenishing drug tray /crash cart',
                                    'e9861df6-a10e-41b5-a6f6-dc1325f53763', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('There is no stock out of drugs', 'a1be43c1-7974-4e0c-988d-89b30811cd46', me_id,
                                    checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element(
      'There is process for storage of vaccines and other drugs, requiring controlled temperature', 'D2.7', std_id,
      '6f76986f-6baa-4e59-8a12-f60782bd7925');
  checkpoint_id = create_checkpoint(
      'Temperature of refrigerators are kept as per storage requirement  and records are maintained',
      '8142ad5b-02eb-4123-b804-dc4d1652f167', me_id, checklist_id,
      'Check for temperature charts are maintained and updated periodically', TRUE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('There is a procedure for secure storage of narcotic and psychotropic drugs',
                                    'D2.8', std_id, '93e1adee-bfd7-4731-8d7b-845de083eb0e');
  checkpoint_id = create_checkpoint('Narcotics and psychotropic drugs are kept in lock and key',
                                    'fcf6f61f-e4b5-40cd-8221-857bb783dc7e', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  std_id = create_standard(
      'The facility provides safe, secure and comfortable environment to staff, patients and visitors.', 'D3', aoc_id,
      '3b17c06b-bc61-4330-9b38-9c0ab1f4f876');
  me_id = create_measurable_element('The facility provides adequate illumination level at patient care areas', 'D3.1',
                                    std_id, '5d78d771-a5b8-4a4b-b84b-f33ac781aa4e');
  checkpoint_id = create_checkpoint('Adequate Illumination at delivery table', 'ebcbe642-1297-41e0-a63f-0fa464ebe93b',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Adequate Illumination at observation area', '63a6b41c-7d2d-4558-8929-7d0ca1947db0',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility has provision of restriction of visitors in patient areas', 'D3.2',
                                    std_id, '4dd70de1-8b6c-4a1d-b8db-7a3d24543b7d');
  checkpoint_id = create_checkpoint('There is no overcrowding in labour room', 'bb1a674f-ceb7-4e2e-9eae-ce8b021003be',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('One female family members allowed to stay with the PW',
                                    '5f8bf37d-5816-4c25-9346-ace9264e0c04', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Visitors are restricted at labour room', '9a36a486-5292-4d07-a102-f08f3d5a8cc7',
                                    me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element(
      'The facility ensures safe and comfortable environment for patients and service providers', 'D3.3', std_id,
      'e38fb158-7484-4267-89d6-883420e413cb');
  checkpoint_id = create_checkpoint('Temperature control and ventilation in patient care area',
                                    '30cc54fc-735b-4479-a889-6abaa88bfacd', me_id, checklist_id,
                                    'Optimal temperature and warmth is ensured  at labour room.          Fans/ Air conditioning/Heating/Exhaust/Ventilators as per environment condition and requirement',
                                    TRUE, FALSE, TRUE, FALSE);
  checkpoint_id = create_checkpoint('Temperature control and ventilation in nursing station/duty room',
                                    'e6be620c-d963-4c5c-925a-18e9e4da42f9', me_id, checklist_id,
                                    'Fans/ Air conditioning/Heating/Exhaust/Ventilators as per environment condition and requirement',
                                    TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('The facility has security system in place at patient care areas', 'D3.4', std_id,
                                    '8801b5ad-e9cb-4412-98d6-b694a347d186');
  checkpoint_id = create_checkpoint('Lockable doors in labour room', 'ebe52ac8-4208-4146-bde6-1d1b10b88e23', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Security arrangement in labour room', '1e5a35f5-54d0-4e88-9e77-06d177c00527',
                                    me_id, checklist_id, 'Preferably female security staff', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('New born identification band and foot prints are in practice',
                                    '0d4298a2-9546-42d1-9aa3-c96317437433', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('The facility has established measure for safety and security of female staff',
                                    'D3.5', std_id, '8f8ea559-46d4-43b7-81ca-2805d0d917fc');
  checkpoint_id = create_checkpoint('Ask female staff weather they feel secure at work place',
                                    'ffa1becb-c549-4049-a25c-24eb70f0168b', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  std_id = create_standard('The facility has established Programme for maintenance and upkeep of the facility', 'D4',
                           aoc_id, '65fb84f7-fb25-4729-9668-5bb2bb89bea2');
  me_id = create_measurable_element('Exterior of the  facility building is maintained appropriately', 'D4.1', std_id,
                                    '394b6baa-5f45-4352-9c62-1a8fcb836161');
  checkpoint_id = create_checkpoint('Building is painted/whitewashed in uniform colour',
                                    '4e79fcb3-a31b-45c3-befe-8b23f8b85fae', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Interior of patient care areas are plastered & painted',
                                    '9dedc657-3930-4079-8896-668cc67ca908', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Patient care areas are clean and hygienic', 'D4.2', std_id,
                                    '3822e974-d5af-4b45-b03b-1414e04c9fce');
  checkpoint_id = create_checkpoint(
      'Floors, walls, roof, roof topes, sinks patient care and circulation  areas are Clean',
      '353e8ccd-ae5e-4370-9b86-c25e6d42dbee', me_id, checklist_id,
      'All area are clean  with no dirt,grease,littering and cobwebs', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Surface of furniture and fixtures are clean',
                                    '1b63957f-57e5-4dbf-b924-2742e087ee77', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Toilets are clean with functional flush and running water',
                                    '575988bb-1a6b-46ff-a98a-a6e2b9a69c3c', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Hospital infrastructure is adequately maintained', 'D4.3', std_id,
                                    'da665a49-cf2b-437a-9a43-6f6d947ab79e');
  checkpoint_id = create_checkpoint('Check for there is no seepage , Cracks, chipping of plaster',
                                    'd3f41fa2-324d-4866-934c-26ad498ff692', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Window panes , doors and other fixtures are intact',
                                    'f6a84e71-f417-4161-970f-96c2d538db29', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Delivery table are intact and  without rust',
                                    '66398df0-6133-447c-be4d-815b90e306bd', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Mattresses are intact and clean', '50f46a22-5e44-4a8e-88b4-7837c6853fc2', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('Hospital maintains the open area and landscaping of them', 'D4.4', std_id,
                                    'c869d156-ba73-4669-9ef9-1e5fdaf166e3');
  me_id = create_measurable_element('The facility has policy of removal of condemned junk material', 'D4.5', std_id,
                                    '395745b5-2bab-4902-a8f2-4ba52d16ed91');
  checkpoint_id = create_checkpoint('No condemned/Junk material in the Labour room',
                                    '908a8f91-eefd-475a-8434-bd3bf97beac0', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility has established procedures for pest, rodent and animal control',
                                    'D4.6', std_id, '15d5a8e7-9680-4a48-ab3b-7705664e52fd');
  checkpoint_id = create_checkpoint('No stray animal/rodent/birds', '08e93a79-6591-44d7-a5ac-80a624331e68', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  std_id = create_standard(
      'The facility ensures 24X7 water and power backup as per requirement of service delivery, and support services norms',
      'D5', aoc_id, 'acbb835e-60f3-4435-99da-4d1717e8b4d6');
  me_id = create_measurable_element(
      'The facility has adequate arrangement storage and supply for portable water in all functional areas', 'D5.1',
      std_id, 'bc07ad8e-7d89-43d8-8f53-5c9d993af7e9');
  checkpoint_id = create_checkpoint('Availability of 24x7 running and potable water',
                                    '7acae280-b5bc-492a-ae0e-9bf993b4a53d', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of hot water', '776625e0-96e4-401b-8dec-c3cbb3d5d944', me_id,
                                    checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('The facility ensures adequate power backup in all patient care areas as per load',
                                    'D5.2', std_id, '07418a24-e05e-40ba-a850-a25fbac1d975');
  checkpoint_id = create_checkpoint('Availability of power back  up in labour room',
                                    'a4bbe939-469b-449c-ae42-c46c8c3489c6', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of UPS', '0d545760-9e20-4180-a3ab-269883a8e72d', me_id, checklist_id,
                                    '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Emergency light', '92fcc9ff-e15b-4e60-b72a-4c4ef8962c2c', me_id,
                                    checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element(
      'Critical areas of the facility ensures availability of oxygen, medical gases and vacuum supply', 'D5.3', std_id,
      '80c59197-1027-4e9c-ab62-50dfc4c2e6ce');
  checkpoint_id = create_checkpoint('Availability  of Centralized /local piped Oxygen and vacuum supply',
                                    'ecfd36ab-2775-4fc3-a457-2fa72c00464f', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('StandardD6', '05670b21-277d-4078-9127-78ae5e189a05', me_id, checklist_id, '',
                                    FALSE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility has provision of nutritional assessment of the patients', 'D6.1',
                                    std_id, '17304ad0-fe11-42b2-a300-45c840b5a074');
  me_id = create_measurable_element('The facility provides diets according to nutritional requirements of the patients',
                                    'D6.2', std_id, '1cf1a3c7-8a30-442c-b417-010ae72590a3');
  me_id = create_measurable_element(
      'Hospital has standard procedures for preparation, handling, storage and distribution of diets, as per requirement of patients',
      'D6.3', std_id, '43f81542-9804-4b12-970d-3c71d19fd26f');
  std_id = create_standard('The facility ensures clean linen to the patients', 'D7', aoc_id,
                           '6dfb6628-dc35-4109-b461-d81016cdce20');
  me_id = create_measurable_element('The facility has adequate sets of linen', 'D7.1', std_id,
                                    'f247a608-cf13-4ccb-9d9e-0d3cac5656e7');
  checkpoint_id = create_checkpoint('Availability of clean Drape, Macintosh on the Delivery table,',
                                    '078e1270-a304-455a-a585-3000b244f2a5', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Gown are provided in labour room', 'efb8deb9-dc80-4453-99fe-ee459bbbf7cf', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of Baby blanket, sterile drape for baby',
                                    '9d1809d7-6d7c-4fec-9522-0ae520bdcfc3', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    TRUE);
  me_id = create_measurable_element(
      'The facility has established procedures for changing of linen in patient care areas', 'D7.2', std_id,
      '595dbd19-7f41-43b6-8488-275e53c1c693');
  me_id = create_measurable_element(
      'The facility has standard procedures for handling , collection, transportation and washing  of linen', 'D7.3',
      std_id, 'aa0fd3ee-308f-4062-a310-791de2337632');
  checkpoint_id = create_checkpoint(
      'There is  system to check the cleanliness and Quantity of the linen received from laundry',
      'f4303534-f7c2-48eb-8795-18b6780cf9f8', me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  std_id = create_standard(
      'The facility has defined and established procedures for promoting public participation in management of hospital transparency and accountability.',
      'D8', aoc_id, '8e781c6d-b076-41df-8f33-7ac2eebf90d6');
  me_id = create_measurable_element(
      'The facility has established procures for management of activities of Rogi Kalyan Samitis', 'D8.1', std_id,
      '99c31393-483a-4b28-b3f5-c3f23e247fcd');
  me_id = create_measurable_element(
      'The facility has established procedures for community based monitoring of its services', 'D8.2', std_id,
      'd08c7884-af57-4e4c-b8c8-8d1cd1ba6762');
  std_id = create_standard('Hospital has defined and established procedures for Financial Management', 'D9', aoc_id,
                           'e889b3b3-2974-47bb-8eea-7fc5794c8c6c');
  me_id = create_measurable_element('The facility ensures the proper utilization of fund provided to it', 'D9.1',
                                    std_id, '34cd1672-8ebb-4ee0-b82e-7c64fc96df78');
  me_id = create_measurable_element(
      'The facility ensures proper planning and requisition of resources based on its need', 'D9.2', std_id,
      'a6879c9a-2eee-4c2f-aa64-7f59b61ed4c8');
  std_id = create_standard(
      'The facility is compliant with all statutory and regulatory requirement imposed by local, state or central government',
      'D10', aoc_id, '9294a628-1108-48a6-ab93-a8471cf6a92b');
  me_id = create_measurable_element(
      'The facility has requisite licences and certificates for operation of hospital and different activities',
      'D10.1', std_id, 'fdbdfb2f-eaad-4488-94c5-cacaa94f43e7');
  me_id = create_measurable_element(
      'Updated copies of relevant laws, regulations and government orders are available at the facility', 'D10.2',
      std_id, '344725ec-bdb5-48dc-b2b2-1d0d9eb5d869');
  me_id = create_measurable_element(
      'The facility ensure relevant processes are in compliance with statutory requirement', 'D10.3', std_id,
      'd7b24f7e-f76a-41cc-b16c-c19e00bc2b63');
  std_id = create_standard(
      'Roles & Responsibilities of administrative and clinical staff are determined as per govt. regulations and standards operating procedures.',
      'D11', aoc_id, '99b4428c-cc5d-4ff9-a7c9-812f02b05760');
  me_id = create_measurable_element('The facility has established job description as per govt guidelines', 'D11.1',
                                    std_id, '57c152ec-5cd9-4f22-9271-565fd28fbbd7');
  checkpoint_id = create_checkpoint('Staff is aware of their role and responsibilities',
                                    '846cbc73-aa57-42fb-a632-9a09f0b510b0', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element(
      'The facility has a established procedure for duty roster and deputation to different departments', 'D11.2',
      std_id, '3b9da678-12d3-4f7a-8843-0d2d4d86b70f');
  checkpoint_id = create_checkpoint('There is procedure to ensure that staff is available on duty as per duty roster',
                                    'df143ccc-76ba-4f26-b5cc-911192bedaa2', me_id, checklist_id,
                                    'Check for system for recording time of reporting and relieving (Attendance register/ Biometrics etc)',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('There is designated  in charge for department',
                                    'acbb5bf6-9c9a-411f-a612-b7d97b832ce7', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element(
      'The facility ensures the adherence to dress code as mandated by its administration / the health department',
      'D11.3', std_id, 'f26aacd7-04c8-4b01-9ddd-9c6832bfdbfe');
  checkpoint_id = create_checkpoint('Doctor, nursing staff and support staff adhere to their respective dress code',
                                    'd67ddd65-a2a8-4f95-972c-e10acc79d7d1', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  std_id = create_standard(
      'The facility has established procedure for monitoring the quality of outsourced services and adheres to contractual obligations',
      'D12', aoc_id, 'cd9215b7-bdfe-4696-a5fe-fe579caefca6');
  me_id = create_measurable_element('There is established system for contract management for out sourced services',
                                    'D12.1', std_id, '61835fb0-debf-4861-86ea-57faccda2152');
  checkpoint_id = create_checkpoint(
      'There is procedure to  monitor the quality and adequacy of  outsourced services on regular basis',
      '4a0dd78f-bfe6-4435-8303-5c5a9ac814df', me_id, checklist_id,
      'Verification of outsourced services (cleaning/ Dietary/Laundry/Security/Maintenance)  provided are done by designated in-house staff',
      FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('There is a system of periodic review of quality of out sourced services', 'D12.2',
                                    std_id, 'ec3c0a3e-d2f1-4754-a058-1f840c8b6473');
  aoc_id = create_area_of_concern('Clinical Services', 'E', '66e9c1b9-50d3-42ab-925f-df6e3cf1d701');
  std_id = create_standard(
      'The facility has defined procedures for registration,  consultation and admission of patients.', 'E1', aoc_id,
      '5f4f3167-77b1-4db5-ab43-c1a54b34251e');
  me_id = create_measurable_element('The facility has established procedure for registration of patients', 'E1.1',
                                    std_id, '83b50f81-3b90-4b43-af9b-a95f9328299f');
  checkpoint_id = create_checkpoint(
      'Unique  identification number  is given to each patient during process of registration',
      'e4355173-f2cf-46c8-b2fc-44aea643ecc5', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Patient demographic details are recorded in admission records',
                                    '554d3aa1-453c-4439-8646-9dfbdc8340a0', me_id, checklist_id,
                                    'Check for that patient demographics like Name, age, Sex, Chief complaint, etc.',
                                    FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('The facility has a established procedure for OPD consultation', 'E1.2', std_id,
                                    '3992df9b-13bf-4919-bfc6-df1087d4248a');
  me_id = create_measurable_element('There is established procedure for admission of patients', 'E1.3', std_id,
                                    'cab1d1e6-c487-4b90-8f1f-7f45fc1292e9');
  checkpoint_id = create_checkpoint('There is procedure for admitting Pregnant women directly coming to Labour room',
                                    '0f1b2c88-8791-4933-8f05-861f47db1777', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Admission is done by written order of a qualified doctor',
                                    '3bf2c3b1-65f1-45db-be43-df2f8fd98dbd', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('There is no delay in admission of pregnant women in labour pain',
                                    '64b90fe8-f4fa-42fe-aa57-c653d411a1cd', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Time of admission is recorded in patient record',
                                    '3f80d970-0a84-49e9-aeca-3793c34ca046', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  me_id = create_measurable_element(
      'There is established procedure for managing patients, in case beds are not available at the facility', 'E1.4',
      std_id, 'fabc8c6d-296e-41bc-a152-c653920e0963');
  checkpoint_id = create_checkpoint(
      'Check how service provider cope with shortage of delivery tables due to high patient load',
      'f8cea815-4a13-46c5-8263-9970cbf9cfe6', me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  std_id = create_standard(
      'The facility has defined and established procedures for clinical assessment and reassessment of the patients.',
      'E2', aoc_id, '5dfd770f-38c9-4bd0-ad7a-ca25e89887c9');
  me_id = create_measurable_element('There is established procedure for initial assessment of patients', 'E2.1', std_id,
                                    '5ff0a558-9666-4fcd-9fda-9d35ec7b23eb');
  checkpoint_id = create_checkpoint(
      'Rapid Initial assessment of Pregnant Women to identify complication and Prioritize care',
      'ce088486-d4be-42d0-876d-c65ca4d31d19', me_id, checklist_id,
      'Assessment and immediate sign if following danger sign are present - difficulty in breathing, fever, sever abdominal pain, Convulsion or unconsciousness, Severe headache or blurred vision',
      TRUE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Recording and reporting of Clinical History',
                                    'a3d29bfe-252c-4b34-9595-48ce4bdf7a00', me_id, checklist_id, 'Recording of women obstetric History including
LMP and EDD Parity, gravid status, h/o CS, Live birth, Still Birth, Medical History (TB, Heart diseases, STD etc, HIV status and Surgical History',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Recording of current labour details', 'f5900a34-d9e9-4e5b-9017-7871931b7898',
                                    me_id, checklist_id,
                                    'Time of start, frequency of contractions, time of bag of water leaking, colour and smell of fluid and baby movement',
                                    FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Physical Examination', 'f7e4434b-488f-40a9-a1a3-50c31fea0e3c', me_id, checklist_id,
                                    'Recording of Vitals , shape & Size of abdomen , presence of  scars, foetal lie  and presentation. & vaginal examination',
                                    FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('There is established procedure for follow-up/ reassessment of Patients', 'E2.2',
                                    std_id, '203aef08-50d6-41cc-935a-f28bff91cdf5');
  checkpoint_id = create_checkpoint(
      'There is fixed schedule for reassessment of Pregnant women as per standard protocol',
      '05cc8e22-e40d-4db3-b57f-6ebd279f9934', me_id, checklist_id,
      'There is fix schedule of reassessment as per protocols', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Partograph is used and  updated as per stages of labour',
                                    'df578cc5-766a-4e59-bc7a-03de8b58b035', me_id, checklist_id,
                                    'All step are recorded in timely manner', TRUE, FALSE, FALSE, TRUE);
  std_id = create_standard(
      'The facility has defined and established procedures for continuity of care of patient and referral', 'E3',
      aoc_id, 'e226c95a-e1ac-481c-ba4b-96a26bedc744');
  me_id = create_measurable_element(
      'The facility has established procedure for continuity of care during interdepartmental transfer', 'E3.1', std_id,
      '19f68838-33ac-44de-a262-033d5bf6d380');
  checkpoint_id = create_checkpoint(
      'There is procedure of handing  over patient / new born form labour room to OT/ Ward/SNCU',
      'fc26f8db-0ab1-4d75-9d30-51700c2e0fd2', me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'There is a procedure for consultation of  the patient to other specialist with in the hospital',
      '0143020f-c223-407e-af23-acc4dbc02431', me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element(
      'The facility provides appropriate referral linkages to the patients/Services  for transfer to other/higher facilities to assure the continuity of care.',
      'E3.2', std_id, 'd23ea2e7-6e86-44b6-913e-2f54bf6be86d');
  checkpoint_id = create_checkpoint('Patient referred with referral slip', 'fb7a6fbe-6ccd-492e-8fae-6a16fa6e1c48',
                                    me_id, checklist_id,
                                    'A referral slip/ Discharge card is provide to patient when referred to another health care facility',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Advance communication is done with higher centre',
                                    '9ef6ae34-69d2-4696-bd0a-a30bf752f768', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Referral vehicle is being arranged', '838293f9-f4ba-462b-b7e4-288078fcca0b', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Referral in or referral out register is maintained',
                                    'c6d65145-1c03-4838-b92b-6fafe69de159', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Facility has functional referral linkages to lower facilities',
                                    '8fc88c5f-99af-4556-bb9a-806186037f2d', me_id, checklist_id,
                                    'Check for referral cards filled from lower facilities', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('There is a system of follow up of referred patients',
                                    '45fefb33-22d7-47df-ae29-e627ddd1fcad', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('A person is identified for care during all steps of care', 'E3.3', std_id,
                                    'd182fdba-8939-43ee-a211-6bf15bbbfff2');
  checkpoint_id = create_checkpoint('Nurse is assigned for each patients', '78815d72-1dd3-4adb-9fab-b9b52641b428',
                                    me_id, checklist_id, 'Check for nursing hand over', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility is connected to medical colleges through telemedicine services',
                                    'E3.4', std_id, '8446ff17-5db7-4aef-8305-0646ee078b5e');
  std_id = create_standard('The facility has defined and established procedures for nursing care', 'E4', aoc_id,
                           '0956714b-cfb1-46be-a485-918c87d903d0');
  me_id = create_measurable_element('Procedure for identification of patients is established at the facility', 'E4.1',
                                    std_id, '863bd758-9827-465f-8fd3-c08e6802035c');
  checkpoint_id = create_checkpoint(
      'There is a process  for ensuring the  identification before any clinical procedure',
      '7cd28b01-206d-49ee-92c8-40550e613939', me_id, checklist_id,
      'Identification  tags for mother and baby / foot print are used for identification of newborns', TRUE, TRUE,
      FALSE, FALSE);
  me_id = create_measurable_element(
      'Procedure for ensuring timely and accurate nursing care as per treatment plan is established at the facility',
      'E4.2', std_id, '58cecf95-98f2-4e66-90e0-a2902c4e756b');
  checkpoint_id = create_checkpoint('There is a process to ensue the accuracy of verbal/telephonic orders',
                                    'ce2ec1b6-c6d4-4781-b326-262f0b895311', me_id, checklist_id,
                                    'Verbal orders are rechecked before administration', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element(
      'There is established procedure of patient hand over, whenever staff duty change happens', 'E4.3', std_id,
      'e61729c6-1d40-4daf-95e0-b4fcae7d7f89');
  checkpoint_id = create_checkpoint('Patient hand over is given during the change in the shift',
                                    'f02e6093-7901-412b-916f-05013fed3a98', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Nursing Handover register is maintained', '16ca82ca-64cb-4523-b699-c6058cc17ec4',
                                    me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Hand over is given bed side', '591c5645-44dc-46d1-8336-3d0dc6fc11e5', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('Nursing records are maintained', 'E4.4', std_id,
                                    'b3b28c0b-a4f0-4305-9e4b-aabbf66eec76');
  me_id = create_measurable_element('There is procedure for periodic monitoring of patients', 'E4.5', std_id,
                                    'eea87823-4ccd-46e2-a2c7-135778670bae');
  checkpoint_id = create_checkpoint('Patient Vitals are monitored and recorded periodically',
                                    '2298a213-8935-4a32-ac42-7d737895fec4', me_id, checklist_id,
                                    'Check  for BP, pluse,temp,Respiratory rate  FHR, Uterine contraction Contractions, any other vital required is monitored',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Critical patients are monitored continuously',
                                    '760f3846-dadf-4813-ab14-bf4465098aaa', me_id, checklist_id,
                                    'Check  for BP, pluse,temp,Respiratory rate  FHR, Uterine contraction Contractions, any other vital required is monitored',
                                    FALSE, TRUE, FALSE, TRUE);
  std_id = create_standard('The facility has a procedure to identify high risk and vulnerable patients.', 'E5', aoc_id,
                           '3042b1ae-f6fd-45da-b362-ba2db9bbfdd4');
  me_id = create_measurable_element('The facility identifies vulnerable patients and ensure their safe care', 'E5.1',
                                    std_id, '773a23d6-2017-4bc3-ba1a-86b362cecc48');
  checkpoint_id = create_checkpoint(
      'Vulnerable patients are identified and measures are taken to protect them from any harm',
      'f0621a08-21ae-4758-bcf1-a1291bf34588', me_id, checklist_id,
      'Check the measure taken to prevent new born theft, sweeping and baby fall', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element(
      'The facility identifies high risk  patients and ensure their care, as per their need', 'E5.2', std_id,
      '3c6db957-38ff-4dfb-85c3-8839b85667af');
  checkpoint_id = create_checkpoint('High Risk Pregnancy cases are identified and kept in intensive monitoring',
                                    'd539fdec-3671-4509-bb78-6f6f027a2ccf', me_id, checklist_id,
                                    'Check for the frequency of observation: Ist stage :half an hour and 2nd stage: every 5 min',
                                    TRUE, TRUE, FALSE, FALSE);
  std_id = create_standard(
      'The facility follows standard treatment guidelines defined by state/Central government for prescribing the generic drugs & their rational use.',
      'E6', aoc_id, '378bc54d-01f6-4dea-a2fd-ad0d1c5a9d86');
  me_id = create_measurable_element('The facility ensured that drugs are prescribed in generic name only', 'E6.1',
                                    std_id, 'e4d31e04-5fbf-4acd-9f67-8d35d2a122b8');
  checkpoint_id = create_checkpoint('Check for BHT  if drugs are prescribed under generic name only',
                                    '2d935035-8c7b-4466-a3eb-e91b677eb525', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  me_id = create_measurable_element('There is procedure of rational use of drugs', 'E6.2', std_id,
                                    '907bb3c8-1a7a-4808-87eb-11ee8f135cc9');
  checkpoint_id = create_checkpoint(
      'Check for that relevant Standard treatment guideline are available at point of use',
      '2c0b3bf3-952a-40af-956b-a69ce5514137', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Check staff is aware of the drug regime and doses as per STG',
                                    '19249a91-e992-471a-848d-5f0bbf8279c3', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Check BHT that drugs are prescribed as per STG',
                                    '975934db-8ae7-4707-86f4-979c57030560', me_id, checklist_id,
                                    'Check for rational use of uterotonic drugs', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Availability of drug formulary', 'cb4dabfb-6ae9-4185-a8fc-de0a353f7008', me_id,
                                    checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  std_id = create_standard('The facility has defined procedures for safe drug administration', 'E7', aoc_id,
                           'ea87c20f-9781-4c60-a3e3-0fc2141c0914');
  me_id = create_measurable_element('There is process for identifying and cautious administration of high alert drugs',
                                    'E7.1', std_id, '51ad1eef-c66e-4241-a1ab-cd036a6f7e01');
  checkpoint_id = create_checkpoint('High alert drugs available in department are identified',
                                    '0c979dcf-97d1-4caa-968d-60bd1b5b719c', me_id, checklist_id,
                                    'Magsulf (to be kept in fridge) , Methergine', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Maximum dose of high alert drugs are defined and communicated',
                                    'f69a25fd-1876-477a-95f7-c9343d975b4e', me_id, checklist_id,
                                    'Value for maximum doses as per age, weight and diagnosis are available with nursing station and doctor',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('There is process to ensure that right doses of high alert drugs are only given',
                                    '71d7cb46-170e-4107-ab41-75849b45888a', me_id, checklist_id,
                                    'A system of independent double check before administration, Error prone medical abbreviations are avoided',
                                    FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('Medication orders are written legibly and adequately', 'E7.2', std_id,
                                    '6fe4fa44-db2a-4614-af2e-73d9d9281600');
  checkpoint_id = create_checkpoint('Every Medical advice and procedure is accompanied with date , time and signature',
                                    'eebf3be5-88b3-41ba-8f77-f3b91a7bfb9c', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Check for the writing, It  comprehendible by the clinical staff',
                                    '373259c5-f829-4b77-8e0e-8929c0509678', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('There is a procedure to check drug before administration/ dispensing', 'E7.3',
                                    std_id, 'aba4c58c-9c77-4660-b0f9-cee3e1fd57d1');
  checkpoint_id = create_checkpoint('Drugs are checked for expiry and   other inconsistency before administration',
                                    '7c30613b-918f-4512-8349-a1bfc69ca74f', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Check single dose vial are not used for more than one dose',
                                    '002bd056-371d-4e78-b6e9-e96db7fa5293', me_id, checklist_id,
                                    'Check for any open single dose vial with left  over content intended to be used later on',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Check for separate sterile needle is used every time for multiple dose vial',
                                    'c2216a10-8cb6-4417-9408-d77028b83264', me_id, checklist_id,
                                    'In multi dose vial needle is not left in the septum', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Any adverse drug reaction is recorded and reported',
                                    'b22b4a4d-d2c8-4e09-83c0-c4e7eabd283d', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('There is a system to ensure right medicine is given to right patient', 'E7.4',
                                    std_id, 'fc1b9bf8-51c6-4957-b908-eceac8851daa');
  checkpoint_id = create_checkpoint(
      'Administration of medicines done after ensuring right patient, right drugs , right route, right time',
      '020cb394-e28d-4e7c-a671-72adb349a2df', me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('Patient is counselled for self drug administration', 'E7.5', std_id,
                                    '611b0b78-5b93-40d2-aadf-ef793d079382');
  std_id = create_standard(
      'The facility has defined and established procedures for maintaining, updating of patients clinical records and their storage',
      'E8', aoc_id, 'be32d804-104e-4009-b8d3-eb1b57812ced');
  me_id = create_measurable_element('All the assessments, re-assessment and investigations are recorded and updated',
                                    'E8.1', std_id, '9bdfdafe-04b4-4e4a-aedf-5ebd3fa01784');
  checkpoint_id = create_checkpoint('Progress of labour is recorded', '066c2079-ddce-4ba8-950a-a26dc3a6d123', me_id,
                                    checklist_id,
                                    'Partograph Full compliance and on bed head ticket partial compliance', FALSE,
                                    FALSE, FALSE, TRUE);
  me_id = create_measurable_element('All treatment plan prescription/orders are recorded in the patient records.',
                                    'E8.2', std_id, 'a27c85dd-5e4a-49dc-bb1c-60f73f26bab5');
  checkpoint_id = create_checkpoint('Treatment prescribed in nursing records', 'f8ecb539-958a-4c8a-a148-3bb9538dbefb',
                                    me_id, checklist_id,
                                    'Medication order, treatment plan, lab investigation are recoded adequately', FALSE,
                                    FALSE, FALSE, TRUE);
  me_id = create_measurable_element('Care provided to each patient is recorded in the patient records', 'E8.3', std_id,
                                    '7644f02e-634d-4d4c-9762-d8c867ac8b59');
  me_id = create_measurable_element('Procedures performed are written on patients records', 'E8.4', std_id,
                                    'fe3376b5-3335-45f1-84bf-f8f9bae1a75f');
  checkpoint_id = create_checkpoint('Delivery note is adequate', '2becd08f-d7d3-4126-bd2b-53ee3810ff88', me_id,
                                    checklist_id,
                                    'Outcome of delivery, date and time, gestation age, delivery conducted by, type of delivery, complication if any ,indication of intervention, date and time of transfer, cause of death etc',
                                    FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Baby note is adequate', '61f23a2b-5d2d-48a8-a112-b9e0daebeacf', me_id,
                                    checklist_id,
                                    'Did baby cry, Essential new born care, resuscitation if any, Sex, weight, time of initiation of breast feed, birth doses, congenital anomaly if any.',
                                    FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('Adequate form and formats are available at point of use', 'E8.5', std_id,
                                    'e3e691f9-b3d7-4c95-b679-32acf9ff5401');
  checkpoint_id = create_checkpoint('Standard Formats available', '7b174175-ca67-4b7a-9df6-875a361e8f2f', me_id,
                                    checklist_id, 'Availability of BHT, Partograph, etc.', TRUE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('Register/records are maintained as per guidelines', 'E8.6', std_id,
                                    'a43e7481-bf82-4251-830c-2ab875741938');
  checkpoint_id = create_checkpoint('Registers and records are maintained as per guidelines',
                                    '31be743f-927d-4a03-8bd0-e487731d992a', me_id, checklist_id,
                                    'labour room register, OT register, MTP register,FP register, Maternal death register and records, lab register, referral in /out register, internal& PPIUD register etc.',
                                    FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('All register/records are identified and numbered',
                                    '01b61914-ea27-43b8-8225-ef0387f7d7ed', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  me_id = create_measurable_element('The facility ensures safe and adequate storage and retrieval  of medical records',
                                    'E8.7', std_id, 'ffa19caf-bb65-4277-b6a6-6345486186e9');
  std_id = create_standard('The facility has defined and established procedures for discharge of patient.', 'E9',
                           aoc_id, '2ceb9170-285d-48d3-8ce4-708ed47885b7');
  me_id = create_measurable_element('Discharge is done after assessing patient readiness', 'E9.1', std_id,
                                    '7efe6620-9e2e-4ff2-8b19-fa0cebb7476f');
  me_id = create_measurable_element('Case summary and follow-up instructions are provided at the discharge', 'E9.2',
                                    std_id, 'a68d444f-50c4-4553-ae61-d346931062fe');
  me_id = create_measurable_element('Counselling services are provided as during discharges wherever required', 'E9.3',
                                    std_id, '10a5793e-4ec0-432a-a418-730f3e9a17fb');
  me_id = create_measurable_element(
      'The facility has established procedure for patients leaving the facility against medical advice, absconding, etc',
      'E9.4', std_id, 'b8d25e00-9278-45ad-8ea0-4e746bf715fe');
  std_id = create_standard('The facility has defined and established procedures for intensive care.', 'E10', aoc_id,
                           '5be4a1cf-a548-4041-9618-defdb0a3e7d2');
  me_id = create_measurable_element(
      'The facility has established procedure for shifting the patient to step-down/ward  based on explicit assessment criteria',
      'E10.1', std_id, 'd1d25e0a-fd17-4394-8b66-fec0688ba8cd');
  me_id = create_measurable_element('The facility has defined and established procedure for intensive care', 'E10.2',
                                    std_id, '406d3c44-5de4-4699-99d9-dce08aa7ecf1');
  me_id = create_measurable_element(
      'The facility has explicit clinical criteria for providing intubation & extubation, and care of patients on ventilation and subsequently on its removal',
      'E10.3', std_id, '5d03ebae-fc45-4c92-bb99-771e8e70c5a6');
  std_id = create_standard(
      'The facility has defined and established procedures for Emergency Services and Disaster Management', 'E11',
      aoc_id, '6101eca2-c31f-4c3f-9aa6-7a7d33cb9b9e');
  me_id = create_measurable_element('There is procedure for Receiving and triage of patients', 'E11.1', std_id,
                                    'bc5d75c2-ccc5-4e2b-98f5-671efe5e0f95');
  me_id = create_measurable_element('Emergency protocols are defined and implemented', 'E11.2', std_id,
                                    '01de79bb-dcd7-432c-b5c4-780abae0e53b');
  me_id = create_measurable_element('The facility has disaster management plan in place', 'E11.3', std_id,
                                    '62b7f304-d2a1-43c4-b234-d83aaaa4c23e');
  checkpoint_id = create_checkpoint('Staff is aware of disaster plan', 'f22ca3dc-9c1f-4035-8a9f-53e10eae8523', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Role and responsibilities of staff in disaster is defined',
                                    'b429a4d0-bd90-43a0-951b-a46314465fda', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element(
      'The facility ensures adequate and timely availability of ambulances services and mobilisation of resources, as per requirement',
      'E11.4', std_id, 'b57682a5-0123-4770-acef-888dd9ceceff');
  me_id = create_measurable_element('There is procedure for handling medico legal cases', 'E11.5', std_id,
                                    '1ab678ca-92d9-4edc-b72e-4e9538704030');
  std_id = create_standard('The facility has defined and established procedures of diagnostic services', 'E12', aoc_id,
                           '52d97f7e-e406-447a-8d32-faee71f055e5');
  me_id = create_measurable_element('There are established  procedures for Pre-testing Activities', 'E12.1', std_id,
                                    'a1a0eca0-98da-4ac7-94f8-909d10fabfeb');
  checkpoint_id = create_checkpoint('Container is labelled properly after the sample collection',
                                    'e5efde25-1d15-4646-8a0d-ae1c14727dda', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('There are established  procedures for testing Activities', 'E12.2', std_id,
                                    'c31a28cb-8187-45f7-9b8a-25dc5a6943ae');
  me_id = create_measurable_element('There are established  procedures for Post-testing Activities', 'E12.3', std_id,
                                    '7a30e69e-242a-42ef-b24c-01cec584b242');
  checkpoint_id = create_checkpoint('Nursing station is provided with the critical value of different test',
                                    '80d9c259-3cbe-4691-a9c6-2a4dbb0ecb10', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  std_id = create_standard(
      'The facility has defined and established procedures for Blood Bank/Storage Management and Transfusion.', 'E13',
      aoc_id, '30da0bfd-2c7e-49db-84e5-c9413236d7dd');
  me_id = create_measurable_element('Blood bank has defined and implemented donor selection criteria', 'E13.1', std_id,
                                    '25508cff-c8a8-4f73-bb37-d5c46fddb9ee');
  me_id = create_measurable_element('There is established procedure for the collection of blood', 'E13.2', std_id,
                                    '52f208ac-d194-4c1d-bbb0-01ccac192421');
  me_id = create_measurable_element('There is established procedure for the testing of blood', 'E13.3', std_id,
                                    '1b7ea1cb-bbd7-4d78-bd2b-d0fb848cfb34');
  me_id = create_measurable_element('There is established procedure for preparation of blood component', 'E13.4',
                                    std_id, 'fc5e8ed9-f5c7-4075-8486-2126f841096b');
  me_id = create_measurable_element(
      'There is establish procedure for labelling and identification of blood and its product', 'E13.5', std_id,
      '009d5c72-70fc-4754-9c2f-2ec08d59707d');
  me_id = create_measurable_element('There is established procedure for storage of blood', 'E13.6', std_id,
                                    'eb88700f-ffbb-4a71-9b16-965399dc3985');
  me_id = create_measurable_element('There is established the compatibility testing', 'E13.7', std_id,
                                    '8a1057fb-2fc9-464a-a59d-f2992999700f');
  me_id = create_measurable_element('There is established procedure for issuing blood', 'E13.8', std_id,
                                    'edd733bf-1193-4990-8847-6e52d422ae92');
  me_id = create_measurable_element('There is established procedure for transfusion of blood', 'E13.9', std_id,
                                    'e53c5104-8877-4893-b972-8b1a1698f860');
  checkpoint_id = create_checkpoint('Consent is taken before transfusion', '5f04cb3f-65dc-494b-9328-92a939f7d1a2',
                                    me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Patient''s identification is verified before transfusion',
                                    '1465db92-4b9e-47b5-b4bf-66e08d8af64f', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('blood is kept on optimum temperature before transfusion',
                                    '88c0d3d9-d3f0-4669-a49c-0efea24feff3', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Blood transfusion is monitored and regulated by qualified person',
                                    '49ce8ecc-13df-405d-b845-5f379b231526', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Blood transfusion note is written in patient record',
                                    '13e1fb51-398e-42d5-9137-cd80b69ad4d6', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  me_id = create_measurable_element(
      'There is a established procedure for monitoring and reporting Transfusion complication', 'E13.10', std_id,
      'bfedc62b-5435-41ba-afeb-ba900d4a3482');
  checkpoint_id = create_checkpoint(
      'Any major or minor transfusion reaction is recorded and reported to responsible person',
      '9f074813-8287-4cda-aa5d-858fb0c8554f', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  std_id = create_standard('The facility has established procedures for Anaesthetic Services', 'E14', aoc_id,
                           '082c4668-d536-4fdf-8805-0f5aaf132695');
  me_id = create_measurable_element(
      'The facility has established procedures for Pre-anaesthetic Check up and maintenance of records', 'E14.1',
      std_id, '56eed167-6737-46c6-98c3-467896b4c99f');
  me_id = create_measurable_element(
      'The facility has established procedures for monitoring during anaesthesia and maintenance of records', 'E14.2',
      std_id, '8d685f1e-82bf-437e-9bc1-f19eeb44378d');
  me_id = create_measurable_element('The facility has established procedures for Post-anaesthesia care', 'E14.3',
                                    std_id, '7047600e-7a6f-49ff-89a4-912fbb8c4c97');
  std_id = create_standard('The facility has defined and established procedures of Operation theatre services', 'E15',
                           aoc_id, '226c133b-56a0-4fec-add9-31f4bc7cbe76');
  me_id = create_measurable_element('The facility has established procedures OT Scheduling', 'E15.1', std_id,
                                    '72e5708b-f81b-42b0-a8ba-2cae5d2d91d8');
  me_id = create_measurable_element('The facility has established procedures for Preoperative care', 'E15.2', std_id,
                                    '85daac20-04fc-43e3-9ba8-1e71ee0da480');
  me_id = create_measurable_element('The facility has established procedures for Surgical Safety', 'E15.3', std_id,
                                    '52077e10-6b43-482a-9040-8030a088ca9f');
  me_id = create_measurable_element('The facility has established procedures for Post operative care', 'E15.4', std_id,
                                    'e864fafc-37aa-42fa-bfd6-d6c792f9b21c');
  std_id = create_standard('The facility has defined and established procedures for end of life care and death', 'E16',
                           aoc_id, '269b661b-fb4f-495e-9498-792f1e02e64a');
  me_id = create_measurable_element('Death of admitted patient is adequately recorded and communicated', 'E16.1',
                                    std_id, '25336e70-5fa9-4243-b2e5-cc4e6d5a3048');
  checkpoint_id = create_checkpoint('Facility has a standard procedure to decent communicate death to relatives',
                                    'd5533b47-402a-4a64-9d8a-b506302936c7', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Death note is written on patient record', '3959d4bd-d879-4dfe-99e0-c5d2c6a0e7ed',
                                    me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('The facility has standard procedures for handling the death in the hospital',
                                    'E16.2', std_id, '0dfa47d7-0015-4c7b-8238-316786ca4e84');
  checkpoint_id = create_checkpoint('Death note including efforts done for resuscitation is noted in patient record',
                                    '24ac1436-f73f-4523-9f29-97621361502d', me_id, checklist_id,
                                    'Maternal and neonatal death are recorded as per MDR guideline', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'There is established criteria for distinguish between newborn death and still birth',
      '71c145ef-3d51-44fe-b912-f013d27d8805', me_id, checklist_id,
      'Every still record/ birth is examined by paediatrician before declaration', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Death summary is given to patient attendant quoting the immediate cause and underlying cause if possible',
      '5a8bb79d-f2d0-4714-83f0-d4754c4fe0ba', me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility has standard operating procedure for end of life support', 'E16.3',
                                    std_id, '4828c5f3-40a7-4e86-94b1-14d62d7fc816');
  me_id = create_measurable_element(
      'The facility has standard procedures for conducting post-mortem, its recording and meeting its obligation under the law',
      'E16.4', std_id, 'af850e76-4e50-43d0-ae54-c88639d22722');
  std_id = create_standard('The facility has established procedures for Antenatal care as per  guidelines', 'E17',
                           aoc_id, '706d235c-7dc9-44fb-a065-9b255b0c26d7');
  me_id = create_measurable_element(
      'There is an established procedure for Registration and follow up of pregnant women.', 'E17.1', std_id,
      'df6e0cad-06b2-4640-858b-e65ceaf2f79c');
  checkpoint_id = create_checkpoint('Facility provides and updates “Mother and Child Protection Card”.',
                                    'c124551a-a2c7-487f-9530-b28278ad707b', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element(
      'There is an established procedure for History taking, Physical examination, and counselling of each antenatal woman, visiting the facility.',
      'E17.2', std_id, '0d67c860-ca06-430d-90be-29534c403ce0');
  me_id = create_measurable_element(
      'The facility ensures availability of diagnostic and drugs during antenatal care of pregnant women', 'E17.3',
      std_id, 'b0aeefff-3b3e-4f50-9257-3e52d2fa67b5');
  checkpoint_id = create_checkpoint('Tests for Urine albumin, haemoglobin, blood grouping',
                                    '4b2b0538-edab-49b3-84b9-6dfb7a848b4f', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element(
      'There is an established procedure for identification of High risk pregnancy and appropriate treatment/referral as per scope of services.',
      'E17.4', std_id, '03cb5e5a-072b-4dcc-be87-11b6609eebc4');
  me_id = create_measurable_element(
      'There is an established procedure for identification and management of moderate and severe anaemia', 'E17.5',
      std_id, '985ac4f5-9c24-41ac-97b4-3c331f0a513a');
  me_id = create_measurable_element(
      'Counselling of pregnant women is done as per standard protocol and gestational age', 'E17.6', std_id,
      '65d84a3d-2c5d-4352-86d6-8c65ce6b4f14');
  std_id = create_standard('The facility has established procedures for Intranatal care as per guidelines', 'E18',
                           aoc_id, '679ddd80-9292-43d2-88d3-de65501c3b3b');
  me_id = create_measurable_element(
      'Established procedures and standard protocols for management of different stages of labour including AMTSL (Active Management of third Stage of labour) are followed at the facility',
      'E18.1', std_id, 'a0017004-5e16-4a48-b89c-fb79b794c5a2');
  checkpoint_id = create_checkpoint('Management of 1st stage of labour:', '778064b7-264e-47d7-a99d-ecf4c0a1ac28', me_id,
                                    checklist_id,
                                    'Check progress is recorded, Women is allowed to give birth in the position she wants , Check progress is recorded on partograph',
                                    TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Management of 2nd stage of labour:', 'bdb3da99-9f8c-4c8f-aac1-8cc73f5ab243', me_id,
                                    checklist_id,
                                    'Allows the spontaneous delivery of head , gives Perineal support and assist in delivering baby. Check progress is recorded on partograph',
                                    TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Active Management of Third stage of labour',
                                    '439537c3-7d7a-4e93-8c2a-2996f322a17b', me_id, checklist_id,
                                    'Palpation of  mother''s abdomen to rule out presence of second baby', TRUE, TRUE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint('Use of Uterotonic Drugs', '7241512e-e897-41af-8587-744d62f65dfd', me_id,
                                    checklist_id, 'Administration of 10 IU of oxytocin IM with in 1 minute of Birth',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Control Cord Traction', 'c1905144-7104-4cee-b095-bf59a1eaa47e', me_id,
                                    checklist_id, 'Only during Contraction', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Uterine Massage', '0c972059-860c-4a8c-9b6b-6a58293f3513', me_id, checklist_id,
                                    'After placenta expulsion , Checks Placenta & Membranes for Completeness', FALSE,
                                    TRUE, FALSE, TRUE);
  me_id = create_measurable_element(
      'There is an established procedure for assisted and C-section deliveries per scope of services.', 'E18.2', std_id,
      'bd3ca1be-e7c9-42ce-bfcf-8cc747c172a2');
  checkpoint_id = create_checkpoint('Staff is aware of Indications for refereeing patient for to Surgical Intervention',
                                    '39529b89-6727-4010-aa14-31b4fdd72ba2', me_id, checklist_id,
                                    'Ask staff how they identify slow progress of labour , Hoe they interpret Partogram',
                                    FALSE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element(
      'There is established procedure for management/Referral of Obstetrics Emergencies as per scope of services.',
      'E18.3', std_id, '98a292ef-4a05-4796-87b4-11ab89c817bf');
  checkpoint_id = create_checkpoint('Management and follow up of PIH/Eclampsia \Pre Eclampsia',
                                    'f2d70d9e-0610-4204-bdfe-ce8bd51ba0e5', me_id, checklist_id, 'Monitors BP in every case, and tests for proteinuria if BP is >140/90 mmHg
If BP is 140/90 mmHg or more with proteinuria 2+  along with any two of the following danger signs: severe headache, blurring of vision, severe pain abdomen or reduced urine output, BP > 160/110 or more with proteinuria 3+;  OR in cases of eclampsia—administers loading dose of Magnesium Sulphate (MgSO4) and refers/ calls for specialist attention; continues maintenance dose of MgSO4- 5 g of MgSO4 IM in alternate buttocks every four hours, for 24 hours after birth/last convulsion, whichever is later
If BP is >160/110 mmHg or more, give appropriate anti-hypertensive (Hydralazine/Methyl Dopa/ Nifedipine)', FALSE, TRUE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Management of Postpartum Haemorrhage', 'd7fac891-a0e5-4b36-8dfe-2c9d9518a099',
                                    me_id, checklist_id,
                                    'Assessment of bleeding (PPH if >500 ml or > 1 pad soaked in 5 Minutes. IV Fluid, bladder catheterization, measurement of urine output,  Administration of 20 IU of Oxytocin in 500 ml Normal Saline or RL at 40-60 drops per minute . Performs Bimanual Compression of Uterus',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Management of Retained Placenta', 'a4a02084-ae51-47b8-8ce1-e87f32b77da3', me_id,
                                    checklist_id,
                                    'Administration of another dose of Oxytocin 20IU in 500 ml of RL at 40-60 drops/min an attempt to deliver placenta with repeat controlled cord traction. If this fails performs manual removal of Placenta',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Management of Uterine Atony', '6e8fd488-1cb4-47e2-9365-66223a760442', me_id,
                                    checklist_id,
                                    'Vigorous Uterine massage, gives Oxytocin 20 IU in 500 ml of R/L  40 to 60 drops/minute (Continue to administer Oxytocin upto maximum of 3 litres of solution with Oxytocin) If still bleeding perform bi manual uterine compression with palpation of femoral pulse',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Management of Obstructed Labour', '36f9d9df-319a-4d55-a11b-c83b295557e4', me_id,
                                    checklist_id,
                                    'Diagnoses obstructed labour based on data registered from the partograph, Re-hydrates the patient to maintain normal plasma volume, check vitals, gives broad spectrum antibiotics, perform bladder catheterization and takes blood for Hb & grouping, Decides on the mode of delivery as per the condition of mother and the baby',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Management of Puerperal sepsis', 'c08498a2-6b74-4540-8d75-95e118bfa7c7', me_id,
                                    checklist_id,
                                    'Diagnosis puerperal sepsis based on clinical criteria: continuous fever for at least 24 hours or recurring within the first 10 days after delivery, increased pulse rate, increased respiration, offensive/foul smelling lochia, sub involution of the uterus, headache and general malaise, pelvic pain, pain, swelling and pus discharge from laceration or episiotomy or incision. Conduct appropriate lab. investigations, Prescribes IV fluids and broad spectrum antibiotics for seven days & advises perineal care',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Delivery of  infectious cases HIV positive PW',
                                    'bcfe0f7b-1e19-4a2f-b0d4-687e63025578', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('There is an established procedure for new born resuscitation and newborn care.',
                                    'E18.4', std_id, '2867f342-9c6f-4359-a81b-1d5e86618df4');
  checkpoint_id = create_checkpoint('Recording date and Time of Birth, Weight', '5a64b000-d2f3-47a1-8e1e-d6ef56dd8cb3',
                                    me_id, checklist_id, 'Check the records', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Dried and put on mothers abdomen', 'f38cbbbc-a26c-4ddd-8984-5398f5435979', me_id,
                                    checklist_id,
                                    'With a clean towel from head to feet, discards the used towel and covers baby including head in a clean dry towel',
                                    TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Vitamin K for low birth weight', '04792053-f9cf-439f-a26f-aed9c39a4398', me_id,
                                    checklist_id,
                                    'Given to all new born (1.0 mg IM in > 1500 gms and 0.5 mg in < 1500 gms', FALSE,
                                    TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Warmth', '7f4b8235-6128-4574-9193-489734ac17db', me_id, checklist_id,
                                    'Check use of radiant warmer', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Care of Cord and Eyes', 'a49ab0a3-f3b5-4411-9e50-3b98adeca7c9', me_id,
                                    checklist_id, 'Delayed Cord Clamping, Clamps & Cut the cords by sterile instruments within 1-3 minutes of Birth
Clean baby''s eyes with sterile cotton/Gauge', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('APGAR Score', '3280f19d-c345-44fd-9553-550aed732b9e', me_id, checklist_id,
                                    'Check practice of maintaining APGAR Score, Nurse is skilled for it', FALSE, TRUE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Kangaroo Mother Care', '84f56e65-30f1-477c-a9e3-c296a240c8b0', me_id, checklist_id,
                                    'Observe /Ask staff about the practice', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('New born Resuscitation', '138d23a4-406d-448a-8e8c-72a4f2249d76', me_id,
                                    checklist_id, 'Ask Nursing staff to demonstrate Resuscitation Technique', FALSE,
                                    TRUE, FALSE, TRUE);
  std_id = create_standard('The facility has established procedures for postnatal care as per guidelines', 'E19',
                           aoc_id, 'b35f19f0-5bd8-4afa-9525-44356d1720cf');
  me_id = create_measurable_element('Post partum Care is provided to the mothers', 'E19.1', std_id,
                                    '42fe1ce0-6d1a-4da0-a486-6cb4fbafe453');
  checkpoint_id = create_checkpoint('Prevention of Hypothermia of new born', '1ddefd0f-1b70-4079-a919-1978c3061adb',
                                    me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Initiation of Breastfeeding with in 1 Hour',
                                    '7d3680ef-36c6-48ee-9541-47da64e49892', me_id, checklist_id, '', FALSE, FALSE, TRUE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Mother is monitored as per post natal care guideline',
                                    '86878905-2dfc-4990-89b4-0774d249349e', me_id, checklist_id,
                                    'Check for records of Uterine contraction, bleeding, temperature, B.P, pulse, Breast examination, (Nipple care, milk initiation)',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Check for perineal washes performed', '1852ff90-0835-41ef-ba7d-8424d031df37',
                                    me_id, checklist_id, '', FALSE, FALSE, TRUE, FALSE);
  me_id = create_measurable_element(
      'The facility ensures adequate stay of mother and newborn in a safe environment as per standard Protocols.',
      'E19.2', std_id, '13b192b3-d7cd-48ca-b13b-06984ea2996f');
  me_id = create_measurable_element('There is an established procedure for Post partum counselling of mother', 'E19.3',
                                    std_id, 'ac91edb3-c84f-46e2-949c-2e02ec0aa8da');
  checkpoint_id = create_checkpoint('Labour room has procedure to provide post partum Counselling',
                                    '545405d2-1a65-4b22-81f9-d01417222d9f', me_id, checklist_id,
                                    'Breast feeding and prevention of hypothermia', FALSE, TRUE, TRUE, FALSE);
  me_id = create_measurable_element(
      'The facility has established procedures for stabilization/treatment/referral of post natal complications',
      'E19.4', std_id, 'de24da02-a785-49b6-b298-3931d3e78281');
  checkpoint_id = create_checkpoint('There is established criteria for shifting newborn to SNCU',
                                    'e7e958af-87f3-4a0f-aec3-c757d6774b07', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('There is established procedure for discharge and follow up of mother and newborn.',
                                    'E19.5', std_id, '18d7765a-41a0-4a62-845d-f63bd9aa17ec');
  std_id = create_standard(
      'The facility has established procedures for care of new born, infant and child as per guidelines', 'E20', aoc_id,
      'fda0b5ff-992a-44a3-ae03-3d714445fe6f');
  me_id = create_measurable_element('The facility provides immunization services as per guidelines', 'E20.1', std_id,
                                    '2261ed19-c83f-40cc-a15e-ba3bc94c8552');
  me_id = create_measurable_element('Triage, Assessment & Management of newborns having
emergency signs are done as per guidelines', 'E20.2', std_id, 'c5ebbdd0-a058-4b1c-b745-733e313fb100');
  me_id = create_measurable_element('Management of Low birth weight
newborns is done as per  guidelines', 'E20.3', std_id, 'bbbce88b-f571-4eb2-a97d-129f71dba8d7');
  me_id = create_measurable_element('Management of neonatal asphyxia, jaundice and sepsis is done as per guidelines',
                                    'E20.4', std_id, '42af5cc4-3b13-4705-a36c-3250742f4eba');
  me_id = create_measurable_element('Management of children presenting
with fever, cough/ breathlessness is done as per guidelines', 'E20.5', std_id, '4cbd6917-60cc-4e97-84ce-55104fd03e3d');
  me_id = create_measurable_element('Management of children with severe
Acute Malnutrition is done as per  guidelines', 'E20.6', std_id, '6ae973ff-2c29-4da1-b5bc-0d340354275a');
  me_id = create_measurable_element('Management of children presenting
diarrhoea is done per  guidelines', 'E20.7', std_id, 'fc3a80bf-fd97-4d84-9272-9a9328bcdb43');
  std_id = create_standard(
      'The facility has established procedures for abortion and family planning as per government guidelines and law',
      'E21', aoc_id, '192328d0-deb4-402a-9081-31989af4a3c6');
  me_id = create_measurable_element('Family planning counselling services provided as per guidelines', 'E21.1', std_id,
                                    'a5242cc1-8e74-4a0b-8e9f-fd4b1ebb2584');
  me_id = create_measurable_element('The facility provides spacing method of family planning as per guideline', 'E21.2',
                                    std_id, 'de96d618-da7e-41b3-a658-68ffa825cf17');
  me_id = create_measurable_element('The facility provides limiting method of family planning as per guideline',
                                    'E21.3', std_id, '4a4740f8-f4c4-49fd-9f54-94df6f73218c');
  me_id = create_measurable_element('The facility provide counselling services for abortion as per guideline', 'E21.4',
                                    std_id, '599cc606-1db9-49b9-8b54-8765dd047399');
  me_id = create_measurable_element('The facility provide abortion services for 1st trimester as per guideline',
                                    'E21.5', std_id, '4adfbaed-1c5a-42a1-a8ae-aff49bbc8f3f');
  me_id = create_measurable_element('The facility provide abortion services for 2nd trimester as per guideline',
                                    'E21.6', std_id, '7c92f8fb-af65-4412-914b-7012ccda6e1b');
  std_id = create_standard('The facility provides Adolescent Reproductive and Sexual Health services as per guidelines',
                           'E22', aoc_id, '6d3d3b38-f96b-48fe-9d9c-b2c5709bd783');
  me_id = create_measurable_element('The facility provides Promotive ARSH Services', 'E22.1', std_id,
                                    '08bbc210-7627-4d46-b5c6-13ae582df51b');
  me_id = create_measurable_element('The facility provides Preventive ARSH Services', 'E22.2', std_id,
                                    '343b8699-bf91-499b-8394-a0db626ff2db');
  me_id = create_measurable_element('The facility Provides Curative ARSH Services', 'E22.3', std_id,
                                    '490d4ce9-540d-4748-9737-7c50d8d3541a');
  me_id = create_measurable_element('The facility Provides Referral Services for ARSH', 'E22.4', std_id,
                                    'ae63bd12-462f-4a30-b085-edbfdf03e5f6');
  std_id = create_standard('The facility provides National health Programme as per operational/Clinical Guidelines',
                           'E23', aoc_id, 'efa74d8a-9b7a-4b60-8b9d-557d6339485a');
  me_id = create_measurable_element(
      'The facility provides services under National Vector Borne Disease Control Programme as per guidelines', 'E23.1',
      std_id, 'c4af767b-3819-437c-a0a6-6e1b4b3d1e55');
  me_id = create_measurable_element(
      'The facility provides services under Revised National TB Control Programme as per guidelines', 'E23.2', std_id,
      '47534fde-b3c9-41b8-a72a-fe0a49b73fd9');
  me_id = create_measurable_element(
      'The facility provides services under National Leprosy Eradication Programme as per guidelines', 'E23.3', std_id,
      '78140bbd-d1a8-48c7-9042-f9675d9e67b8');
  me_id = create_measurable_element(
      'The facility provides services under National AIDS Control Programme as per guidelines', 'E23.4', std_id,
      '7364d07c-70fd-458e-b6b8-a7c75e9c9908');
  me_id = create_measurable_element(
      'The facility provides services under National Programme for control of Blindness as per guidelines', 'E23.5',
      std_id, '74e8f205-82d2-4a11-ad26-3d9015286696');
  me_id = create_measurable_element('The facility provides services under Mental Health Programme  as per guidelines',
                                    'E23.6', std_id, 'f1a63084-1e7b-4914-8b33-dafa1f18adc4');
  me_id = create_measurable_element(
      'The facility provides services under National Programme for the health care of the elderly as per guidelines',
      'E23.7', std_id, '1e53c424-1999-4e45-9f2d-45ee1a5a4cf4');
  me_id = create_measurable_element(
      'The facility provides service under National Programme for Prevention and Control of cancer, diabetes, cardiovascular diseases & stroke (NPCDCS)  as per guidelines',
      'E23.8', std_id, 'c6f91f1b-36d7-4271-88e0-3389b504ff28');
  me_id = create_measurable_element('The facility provide service for Integrated disease surveillance Programme',
                                    'E23.9', std_id, 'b43eb844-c469-42fa-b6d6-caa8f95feee0');
  me_id = create_measurable_element(
      'The facility provide services under National  Programme for prevention and control of  deafness', 'E23.10',
      std_id, '73309b0c-f43a-4a63-9539-65692bd58262');
  aoc_id = create_area_of_concern('Infection Control', 'F', 'c1887ddb-8b51-4ad5-b890-7b8878e644b7');
  std_id = create_standard(
      'The facility has infection control Programme and procedures in place for prevention and measurement of hospital associated infection',
      'F1', aoc_id, '00d2ab49-4cb3-4796-8417-d3fc15acabd7');
  me_id = create_measurable_element('The facility has functional infection control committee', 'F1.1', std_id,
                                    '153b2d03-c698-4c86-87f6-3adae7758644');
  me_id = create_measurable_element(
      'The facility  has provision for Passive  and active culture surveillance of critical & high risk areas', 'F1.2',
      std_id, 'cc44aa13-fda5-4e06-9c49-eea6dbc1d944');
  checkpoint_id = create_checkpoint('Surface and environment samples are taken for microbiological surveillance',
                                    '634e4e53-7e7b-4a4e-9c58-f8a4af6f0c93', me_id, checklist_id,
                                    'Swab are taken from infection prone surfaces', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility measures hospital associated infection rates', 'F1.3', std_id,
                                    'eff0943d-0764-4e8f-ab79-fd5426c3ca79');
  me_id = create_measurable_element('There is Provision of Periodic Medical Check-up and immunization of staff', 'F1.4',
                                    std_id, '5113b7b5-dd21-4ced-8238-59041453165c');
  checkpoint_id = create_checkpoint('There is procedure for immunization of the staff',
                                    'c3e1b7eb-ba58-4005-a825-2eba202e144d', me_id, checklist_id,
                                    'Hepatitis B, Tetanus Toxic etc', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Periodic medical checkups of the staff', 'a8a8e046-f743-4a13-ac9f-ad17f6cf265a',
                                    me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element(
      'The facility has established procedures for regular monitoring of infection control practices', 'F1.5', std_id,
      '2ca2ba7b-ac14-4421-b3b0-451544f133ed');
  checkpoint_id = create_checkpoint('Regular monitoring of infection control practices',
                                    '232d18ce-c688-4780-a5ea-d161b0d45ad9', me_id, checklist_id,
                                    'Hand washing and infection control audits done at periodic intervals', FALSE, TRUE,
                                    FALSE, TRUE);
  me_id = create_measurable_element('The facility has defined and established antibiotic policy', 'F1.6', std_id,
                                    'ac1275b1-cf25-471a-90ed-1afdce44ba54');
  std_id = create_standard(
      'The facility has defined and Implemented procedures for ensuring hand hygiene practices and antisepsis', 'F2',
      aoc_id, '9f26ee60-5def-4998-9fca-35cb4a4c9a96');
  me_id = create_measurable_element('Hand washing facilities are provided at point of use', 'F2.1', std_id,
                                    'a7762f64-0ed2-44d2-84b4-64b89216f4ff');
  checkpoint_id = create_checkpoint('Availability of hand washing Facility at Point of Use',
                                    'e41173c9-fb93-4454-a82e-0567e525429a', me_id, checklist_id,
                                    'Check for availability of wash basin near the point of use', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of running Water', 'de8bc920-79ec-49b9-b1d3-3b18757360f1', me_id,
                                    checklist_id, 'Ask to Open the tap. Ask Staff  water supply is regular', TRUE, TRUE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of antiseptic soap with soap dish/ liquid antiseptic with dispenser.',
                                    '968a0505-b1b1-438e-a19f-b18a730a70e0', me_id, checklist_id,
                                    'Check for availability/ Ask staff if the supply is adequate and uninterrupted',
                                    TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Alcohol based Hand rub', 'b369b35f-d473-4224-9833-866a20131e6c',
                                    me_id, checklist_id, 'Check for availability/  Ask staff for regular supply.', TRUE,
                                    TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Display of Hand washing Instruction at Point of Use',
                                    '93001778-6892-46a4-88f5-b64625143f3f', me_id, checklist_id,
                                    'Prominently displayed above the hand washing facility , preferably in Local language',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of elbow operated taps', '83945d35-1003-4dbf-9b0b-37ecc6049007',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint(
      'Hand washing sink is wide and deep enough to prevent splashing and retention of water',
      'acf63705-2deb-4164-9e96-92eb8a77622c', me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element(
      'The facility staff is trained in hand washing practices and they adhere to standard hand washing practices',
      'F2.2', std_id, '946bc892-4eb6-480f-b90c-c04202318919');
  checkpoint_id = create_checkpoint('Adherence to 6 steps of Hand washing', 'df8bda9c-e675-49ab-ac7b-a1403741d173',
                                    me_id, checklist_id, 'Ask of demonstration', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Staff aware of when to hand wash', '271af997-e63c-4606-81b2-e28fdaa9d307', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('The facility ensures standard practices and materials for antisepsis', 'F2.3',
                                    std_id, '7f434465-4122-463a-b1b6-708a07467f8d');
  checkpoint_id = create_checkpoint('Availability of Antiseptic Solutions', '9f8ed8f1-3b7b-4f35-8b29-6ebaa9bcad7a',
                                    me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Proper cleaning of procedure site  with antisepetics',
                                    'c0b9412d-9b93-4dce-9343-2dd47e609dd0', me_id, checklist_id,
                                    'like before giving IM/IV injection, drawing blood, putting Intravenous and urinary catheter',
                                    TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Proper cleaning of perineal area before procedure with antisepsis',
                                    '8f3ed48e-fa74-4624-9bcc-277c4f17ffbb', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Check Shaving is not done during part preparation/delivery cases',
                                    'bca85182-bc73-49c2-bd53-b9d2241b49ff', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  std_id = create_standard('The facility ensures standard practices and materials for Personal protection', 'F3',
                           aoc_id, '0cdabd90-de41-40b0-b135-4bf8466b1734');
  me_id = create_measurable_element('The facility ensures adequate personal protection Equipment as per requirements',
                                    'F3.1', std_id, '44c9795f-6e72-48f7-816d-9d360ee5e3d6');
  checkpoint_id = create_checkpoint('Availability of Masks', '0455c075-420f-4f4a-9dfa-7531bfae9537', me_id,
                                    checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Sterile s gloves are available at OT and labour room',
                                    'd95e2a97-bbee-4cc7-8d64-cfc6e8f03622', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Use of elbow length gloves for obstetrical purpose',
                                    '73d53f5b-ecae-4e36-a6c9-52c9559585d1', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of gown/ Apron', 'c47271cf-81fc-46ac-97fb-0fd4cabeb8ca', me_id,
                                    checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of shoe cover/gum boots', '81d73ae1-49d3-42eb-b393-9ffd27969685',
                                    me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Caps', '644232d5-c36c-420b-b718-8c84e0f1fe5c', me_id, checklist_id,
                                    '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Heavy duty gloves and gum boats for housekeeping staff',
                                    'f5eb0378-1864-4023-b5fa-70e36678ee69', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Personal protective kit for delivering HIV patients',
                                    '2f73f0be-c784-4832-8a8f-4a8d6003702e', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility staff adheres to standard personal protection practices', 'F3.2',
                                    std_id, 'd1f44aac-1fe3-4c41-8136-87d6f466a05a');
  checkpoint_id = create_checkpoint('No reuse of disposable gloves, Masks, caps and aprons.',
                                    '8252f6ce-b25f-4ec5-8290-4528d22cbba4', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Compliance to correct method of wearing and removing the gloves',
                                    '8276db63-bc92-409c-be63-93a5485c657b', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  std_id = create_standard('The facility has standard procedures for processing of equipment and instruments', 'F4',
                           aoc_id, '05dbc4d5-d107-4065-a6b9-f1309398ee2f');
  me_id = create_measurable_element(
      'The facility ensures standard practices and materials for decontamination and cleaning of instruments and  procedures areas',
      'F4.1', std_id, 'dc01801f-cd34-444a-a0cf-b504e25d9797');
  checkpoint_id = create_checkpoint('Decontamination of operating & Procedure surfaces',
                                    '591b0a04-cb1d-4dfe-85e3-63352f7d80df', me_id, checklist_id, 'Ask stff about how they decontaminate the procedure surface like Delivery Table, Stretcher/Trolleys  etc.
(Wiping with .5% Chlorine solution', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Proper Decontamination of instruments after use',
                                    '771d12e4-442c-499e-8ff6-7ab1bd07bb33', me_id, checklist_id, 'Ask staff how they decontaminate the instruments like ambubag, suction cannula, Delivery Instruments
(Soaking in 0.5% Chlorine Solution, Wiping with 0.5% Chlorine Solution or 70% Alcohol as applicable', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Proper handling of Soiled and infected line',
                                    'b1a4be38-a39f-4e54-84b6-e13c9ef1363a', me_id, checklist_id,
                                    'No sorting ,Rinsing or sluicing at Point of use/ Patient care area', TRUE, TRUE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint('Contact time for decontamination  is adequate',
                                    '7ef5d0b3-3872-49e6-a63b-e15d063bf03c', me_id, checklist_id, '10 minutes', TRUE,
                                    TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Cleaning of instruments after decontamination',
                                    '13b2bfd7-9b99-473a-a583-b775c2fa1b3b', me_id, checklist_id,
                                    'Cleaning is done with detergent and running water after decontamination', TRUE,
                                    TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Proper handling of Soiled and infected linen',
                                    '3ded49d2-9285-413c-b35a-c7f98a08c2a3', me_id, checklist_id,
                                    'No sorting ,Rinsing or sluicing at Point of use/ Patient care area', TRUE, TRUE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint('Staff know how to make chlorine solution', '51d34020-1146-43a3-8217-bf0fa87e67db',
                                    me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element(
      'The facility ensures standard practices and materials for disinfection and sterilization of instruments and equipment',
      'F4.2', std_id, 'ea56532b-c658-4500-b79e-9aa46ef0c86c');
  checkpoint_id = create_checkpoint('Equipment and instruments are  sterilized after each use as per requirement',
                                    'bf4523ed-165c-4469-b03c-cd4b62e6a649', me_id, checklist_id,
                                    'Autoclaving/HLD/Chemical Sterilization', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('High level Disinfection of instruments/equipments  is done  as per protocol',
                                    '4d7cdd20-7f2b-4d17-b55f-408a5b0a634e', me_id, checklist_id,
                                    'Ask staff about method and time required for boiling', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Autoclaving of delivery kits is done as per protocols',
                                    '10b640b8-1a1f-4e2c-98a3-d39c6a7ea824', me_id, checklist_id,
                                    'Ask staff about temperature, pressure and time', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Chemical sterilization  of instruments/equipments is done as per protocols',
                                    'e36897f9-b565-4477-a0f6-4870310b17a7', me_id, checklist_id,
                                    'Ask staff about method, concentration and contact time  required for chemical sterilization',
                                    TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Autoclaved linen are used for procedure', '5c00cd51-bcd4-4bbf-932c-e1585511965f',
                                    me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Autoclaved dressing material is used', '1e6cbb81-99cb-4368-82f7-12bc93cd7abd',
                                    me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('There is a procedure to ensure the traceability of sterilized packs',
                                    'a6afe650-ad0c-4b4b-afc2-31102dbc117f', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Sterility of autoclaved packs is maintained during storage',
                                    '6203ab89-e9f1-49a3-9387-7f02097de11c', me_id, checklist_id,
                                    'Sterile packs are kept in clean, dust free, moist free environment.', TRUE, TRUE,
                                    FALSE, FALSE);
  std_id = create_standard(
      'Physical layout and environmental control of the patient care areas ensures infection prevention', 'F5', aoc_id,
      '521eaeb6-3475-474e-bd4d-847f15aa569d');
  me_id = create_measurable_element('Layout of the department is conducive for the infection control practices', 'F5.1',
                                    std_id, 'f970b362-7949-4517-84c8-62928ef412db');
  checkpoint_id = create_checkpoint('Facility layout ensures separation of routes for clean and dirty items',
                                    'bd9a4ddf-5300-4410-a7c9-cb47af227f44', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element(
      'The facility ensures availability of  standard materials for cleaning and disinfection of patient care areas',
      'F5.2', std_id, '147c9a9c-1b78-4194-97aa-17db7a1adc8f');
  checkpoint_id = create_checkpoint('Availability of disinfectant as per requirement',
                                    '1eb03168-e8b9-4442-8b3f-e764423f5e17', me_id, checklist_id,
                                    'Chlorine solution, Gluteraldehye, carbolic acid', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of cleaning agent as per requirement',
                                    '7720905b-39c2-42f1-ac39-6b0a7b206f94', me_id, checklist_id,
                                    'Hospital grade phenyl, disinfectant detergent solution', TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element(
      'The facility ensures standard practices are followed for the cleaning and disinfection of patient care areas',
      'F5.3', std_id, 'ac796e0a-2c8b-428e-b028-5b69d61b5088');
  checkpoint_id = create_checkpoint('Staff is trained for spill management', 'f95a5296-2f9c-4642-b3c0-d2a1715419e9',
                                    me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Cleaning of patient care area with detergent solution',
                                    '4f945743-7820-4b36-8774-f0bf85d6bd04', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Staff is trained for preparing cleaning solution as per standard procedure',
                                    '3f9a99b0-aba1-410a-9426-2b4911ac5b22', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Cleaning equipments like broom are not used in patient care areas',
                                    'aeb35bb9-40e6-4b23-981f-0d975d4a3b61', me_id, checklist_id,
                                    'Any cleaning equipment leading to dispersion of dust particles in air should be avoided',
                                    TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Use of three bucket system for mopping', '91247613-ae64-4db5-9f7e-d3f9dd9d5e22',
                                    me_id, checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Fumigation/carbolization as per schedule', 'c5043a1e-3af7-46a8-9982-197bf7d8e73b',
                                    me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('External footwares are restricted', '4996b53e-ab89-4d50-af71-52fc1e8d0c14', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  std_id = create_standard('OB/SI', 'practice of mopping and scrubbing are followed', aoc_id,
                           '129b74f5-f7c1-4aaa-bffb-09fae7a78799');
  me_id = create_measurable_element('The facility ensures segregation infectious patients', 'F5.4', std_id,
                                    '21d3a361-60c5-468e-ba83-ea1668da2eca');
  checkpoint_id = create_checkpoint('Isolation and barrier nursing procedure are followed for septic cases',
                                    'c270ec25-cd56-4481-bf21-cf3c8bae11d7', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility ensures air quality of high risk area', 'F5.5', std_id,
                                    '901fbee5-a8cc-4c3e-9b32-e0c31dd95e4a');
  std_id = create_standard(
      'The facility has defined and established procedures for segregation, collection, treatment and disposal of Bio Medical and hazardous Waste.',
      'F6', aoc_id, '567dc92a-50b6-406d-9893-7fbcd4f47b94');
  me_id = create_measurable_element(
      'The facility Ensures segregation of Bio Medical Waste as per guidelines and ''on-site'' management of waste is carried out as per guidelines',
      'F6.1', std_id, '2a7845b1-df9f-4192-82ea-3a5997e24a9c');
  checkpoint_id = create_checkpoint('Availability of colour coded bins at point of waste generation',
                                    '77c07b5e-bd55-4b05-8309-e5b5c9b14da9', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of plastic colour coded plastic bags',
                                    '82529f69-4f74-4361-9afd-6a81b85f85e5', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Segregation of different category of waste as per guidelines',
                                    'c28caa30-0dc6-400d-bad0-b0962e591f4f', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Display of work instructions for segregation and handling of Biomedical waste',
                                    '90d83b14-e080-4671-9b66-e18c95c23f2d', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('There is no mixing of infectious and general waste',
                                    'b8bcbe42-2298-442d-a5dc-31d73bde2742', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility ensures management of sharps as per guidelines', 'F6.2', std_id,
                                    '1ac00968-5f34-4778-9b15-0027050d0704');
  checkpoint_id = create_checkpoint('Availability of functional needle cutters', 'a0073113-8c66-428f-86fe-291baa92b711',
                                    me_id, checklist_id, 'See if it has been used or just lying idle', TRUE, FALSE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of puncture proof box', '762004e2-74b7-42bc-9489-394a8c37fc23', me_id,
                                    checklist_id,
                                    'Should be available nears the point of generation like nursing station and injection room',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Disinfection of sharp before disposal', '63ad31c4-4fb2-47b4-b021-6a77b1ee45ea',
                                    me_id, checklist_id, 'Disinfection of syringes is not done in open buckets', TRUE,
                                    TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Staff is aware of contact time for disinfection of sharps',
                                    'd2b0713f-5443-47fc-8354-535bf01c0447', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Availability of post exposure prophylaxis', '55b39a08-3a7c-4f3e-b3dc-a54690437a52',
                                    me_id, checklist_id,
                                    'Ask if available. Where it is stored and who is in charge of that.', TRUE, TRUE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint('Staff knows what to do in condition of needle stick injury',
                                    'ff79a17b-5ce1-47b8-9a71-e86ef9336e5b', me_id, checklist_id,
                                    'Staff knows what to do in case of shape injury. Whom to report. See if any reporting has been done',
                                    FALSE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('The facility ensures transportation and disposal of waste as per guidelines',
                                    'F6.3', std_id, 'cdb2e076-4290-43bd-8ef7-5bfdf3661d4e');
  checkpoint_id = create_checkpoint('Check bins are not overfilled', 'df8f28a2-ede9-46b1-b0fb-2e0b98ab0cd4', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Disinfection of liquid waste before disposal',
                                    'c59d564c-822f-47c2-8cb8-5bf51017fd79', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Transportation of bio medical waste is done in close container/trolley',
                                    '9e0c38b7-3f2d-4819-ac4f-3b4e4c2b2684', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Staff aware of mercury spill management', '0a62ba1b-24cc-4ca2-a3a9-7f995de0b363',
                                    me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  aoc_id = create_area_of_concern('Quality Management', 'G', '366c3e41-8716-41c4-bef9-17c6f4581c48');
  std_id = create_standard('The facility has established organizational framework for quality improvement', 'G1',
                           aoc_id, '3552629b-0a45-4e47-b92b-5ad7914fd44e');
  me_id = create_measurable_element('The facility has a quality team in place', 'G1.1', std_id,
                                    'f20c697e-f894-4b51-9d4d-9393d3710afd');
  checkpoint_id = create_checkpoint(
      'There is a designated departmental  nodal person for coordinating Quality Assurance activities',
      '0131ada5-b72b-4d8c-ad2a-718fea3f37e0', me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility reviews quality of its services at periodic intervals', 'G1.2',
                                    std_id, 'b165afa7-b8b4-43ce-b07f-486ff0fde6e6');
  std_id = create_standard('The facility has established system for patient and employee satisfaction', 'G2', aoc_id,
                           '9fdfae44-35cb-4982-a06c-c745fb3ddaba');
  me_id = create_measurable_element('Patient satisfaction surveys are conducted at periodic intervals', 'G2.1', std_id,
                                    '29b3a15c-99b5-4cc6-8769-241029159d88');
  me_id = create_measurable_element('The facility analyses the patient feed back, and root-cause analysis', 'G2.2',
                                    std_id, 'b8863ec1-3573-4d9d-af53-db687cfbe028');
  me_id = create_measurable_element(
      'The facility prepares the action plans for the areas, contributing to low satisfaction of patients', 'G2.3',
      std_id, 'b7aff832-9bf0-4447-9d63-3b32c280217b');
  std_id = create_standard(
      'The facility have established internal and external quality assurance Programmes wherever it is critical to quality.',
      'G3', aoc_id, '595148b6-ec06-44eb-b636-3f14fdacf76f');
  me_id = create_measurable_element(
      'The facility has established internal quality assurance programme in key departments', 'G3.1', std_id,
      '7ea063c0-6adb-4c3a-a697-a7ecfec8977c');
  checkpoint_id = create_checkpoint(
      'There is system daily round by matron/hospital manager/ hospital superintendent/ Hospital Manager/ Matron in charge for monitoring of services',
      '32ab5675-b360-4514-acf0-cdd1e51b7c44', me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element(
      'The facility has established external assurance programmes at relevant departments', 'G3.2', std_id,
      'c04d348f-0603-427a-b502-2115301cd2b7');
  me_id = create_measurable_element(
      'The facility has established system for use of check lists in different departments and services', 'G3.3',
      std_id, 'a0815c8e-32f9-4e71-9be4-7e3511c7ee10');
  checkpoint_id = create_checkpoint('Departmental checklist are used for monitoring and quality assurance',
                                    'b9386e06-a1e9-48c8-8869-04d6b299a2cf', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Staff is designated for filling and monitoring of these checklists',
                                    'a81478bb-c022-4933-9976-c18c48db7e81', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  std_id = create_standard(
      'The facility has established, documented implemented and maintained Standard Operating Procedures for all key processes and support services.',
      'G4', aoc_id, '57a4be5f-788c-40e6-a533-6881e1cd0731');
  me_id = create_measurable_element('Departmental standard operating procedures are available', 'G4.1', std_id,
                                    '4337b1a2-b33a-4801-8c27-fc61b227c385');
  checkpoint_id = create_checkpoint('Standard operating procedure for department has been prepared and approved',
                                    '128af903-ffc4-428a-ae30-a7a48059c145', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Current version of SOP are available with  process owner',
                                    'a87a5f8e-7a4a-436d-93b8-1cc422d66cbd', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('Standard Operating Procedures adequately describes process and procedures', 'G4.2',
                                    std_id, '399e3495-a58c-475a-bd9c-da14f81b7bd4');
  checkpoint_id = create_checkpoint(
      'Department has documented procedure for receiving and assessment of  the patient of delivery',
      'ca976713-5c1f-4d85-9ff5-7e42c2671797', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Labour room has documented procedure for Emergency obstetric care',
                                    'cdfee47e-2baf-468e-85ee-83f791c0d794', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Department has documented procedure for management of high risk pregnancy',
                                    'e94bbde0-f304-431b-bea0-f270b35dace3', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Department has documented procedure for rapid initial assessment',
                                    '7e314cbf-d4b1-4529-a112-0aa8719244ed', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Department has documented procedure for requisition of diagnosis and receiving of the reports',
      '8a64c372-efbf-4323-9277-0401e8f157be', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Department has documented procedure for intra partum care',
                                    'e204cf5e-e777-4863-bb20-8b0702c8c967', me_id, checklist_id,
                                    'Intrapartum care includes Management of 1st stage of labour, 2nd stage of labour and 3rd stage of labour',
                                    FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Department has documented immediate post partum care',
                                    '628d62dc-f0aa-4168-90ce-2eb8193d833d', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Department has documented essential new born care',
                                    'e5bf2975-8373-46b8-bede-a7baaa9f138e', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Department has documented procedure for neonatal resuscitation',
                                    'b5b51dd9-5a8a-4248-98a5-92e5e479ab90', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Department has documented procedure for admission, shifting and referral of the patient',
      'f2f749bb-e0c4-48f3-bfb1-42685f476cf9', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Department has documented procedure for arrangement of intervention for labour room',
      'd7064f51-1b0f-4943-9b5b-e5ec3918b18c', me_id, checklist_id,
      'Labour room management include maintenance and calibration of equipments and  inventory management etc', FALSE,
      FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Labour room has documented procedure for blood transfusion',
                                    'acef249e-ae6d-4883-8447-fef3db64d3f2', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Labour room has documented criteria for distinguish between newborn death and still birth',
      'a0225780-2dd9-4a8c-a2e7-2a76284bcce9', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Labour room has documented procedure for environmental cleaning and processing of the equipment',
      'f4ff52fc-ec00-48dd-9665-ecd61c12a24e', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Labour room has documented procedure for maintenance of rights and dignity of pregnant women',
      '9ce0cd3f-9632-4123-8320-6994b01e79e1', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Department has documented procedure for record Maintenance including   taking consent',
      '1a07fc58-2c0c-4d04-b23f-4f252a3fd2cc', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('Staff is trained and aware of the procedures written in SOPs', 'G4.3', std_id,
                                    '1685a654-40ad-462a-9b1f-b261cfb39330');
  checkpoint_id = create_checkpoint('Check Staff is a aware of relevant part of SOPs',
                                    'db207b8b-c3bb-4fab-894b-be7720b7fdc0', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('Work instructions are displayed at Point of use', 'G4.4', std_id,
                                    'f9585b83-a27c-46d0-a43b-a476da97d977');
  checkpoint_id = create_checkpoint('Work instruction/clinical  protocols  are displayed',
                                    '3a3b8200-7ddd-49da-9abe-452d66abf4f1', me_id, checklist_id,
                                    'AMSTL, PPH,Infection control,Eclamsia, New born resuscitation, kangaroo care',
                                    TRUE, FALSE, FALSE, FALSE);
  std_id = create_standard(
      'The facility maps its key processes and seeks to make them more efficient by reducing non value adding activities and wastages',
      'G 5', aoc_id, 'c1eb1715-de71-4ece-ba6f-b1bd6101525a');
  me_id = create_measurable_element('The facility maps its critical processes', 'G5.1', std_id,
                                    '9389eb0e-8b74-4ed7-a999-f21236e6f501');
  checkpoint_id = create_checkpoint('Process mapping of critical processes done',
                                    '2344f1b5-079f-4a90-aa57-9ab1ab6750b3', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element(
      'The facility identifies non value adding activities / waste / redundant activities', 'G5.2', std_id,
      '3d15f825-31c9-4592-b377-426108017cf1');
  checkpoint_id = create_checkpoint('Non value adding activities are identified',
                                    '1195bcb2-9317-4da0-995a-75f550afe8c6', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('The facility takes corrective action to improve the processes', 'G5.3', std_id,
                                    '30f38bc9-0033-4f0b-a833-a8c482e4efbf');
  checkpoint_id = create_checkpoint('Processes are rearranged as per requirement',
                                    'bf5b9c6a-2166-4c61-a6e9-b7b6c20f1ffa', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  std_id = create_standard(
      'The facility has established system of periodic review as internal  assessment , medical & death audit and prescription audit',
      'G6', aoc_id, 'ba32ba24-0ade-4aef-ad1e-f2497853a83c');
  me_id = create_measurable_element('The facility conducts periodic internal assessment', 'G6.1', std_id,
                                    'd14dcba6-b9ac-4dce-8d62-8fe60a1ada56');
  checkpoint_id = create_checkpoint('Internal assessment is done at periodic interval',
                                    '5c432140-fba4-446a-86cf-c4ffe884e19b', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('The facility conducts the periodic prescription/ medical/death audits', 'G6.2',
                                    std_id, '6f2beae7-b53e-4309-854d-43c2c0fe6379');
  me_id = create_measurable_element('The facility ensures non compliances are enumerated and recorded adequately',
                                    'G6.3', std_id, 'fa21ae26-e6ee-4b1c-b9fa-70fc030aa50b');
  checkpoint_id = create_checkpoint('Non Compliance are enumerated and recorded',
                                    '0fe2f557-b6b4-4b2a-a48e-29b413dbdf2b', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('Action plan is made on the gaps found in the assessment / audit process', 'G6.4',
                                    std_id, '4992d38d-dea6-4f57-9955-d51f1ad57bc5');
  checkpoint_id = create_checkpoint('Action plan prepared', 'a12d2dde-111d-4e91-91bd-3c22520bd47a', me_id, checklist_id,
                                    '', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element(
      'Corrective and preventive actions are taken to address issues, observed in the assessment & audit', 'G6.5',
      std_id, 'fda1eb9a-199c-4879-b0c3-d4c9ef197b00');
  checkpoint_id = create_checkpoint('Corrective and preventive  action taken', 'bc5ad5cd-cfd0-4bdc-a12f-c265aac51dbd',
                                    me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  std_id = create_standard('The facility has defined and established Quality Policy & Quality Objectives', 'G7', aoc_id,
                           '3e84e814-249c-4d4f-b9a9-9709e8342ff7');
  me_id = create_measurable_element('The facility defines its quality policy', 'G7.1', std_id,
                                    'd7a41bd4-4265-4fd6-b31f-7bbf57a2479a');
  me_id = create_measurable_element(
      'The facility periodically defines its quality objectives and key departments have their own objectives', 'G7.2',
      std_id, 'bbe9e753-8ecc-4545-bc82-eeea9756b2b6');
  checkpoint_id = create_checkpoint('Quality objective for labour room  are defined',
                                    '578f4404-8b00-4c31-b560-9098264a0601', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('Quality policy and objectives are disseminated and staff is aware of that', 'G7.3',
                                    std_id, '8229e006-5bea-4892-a647-774860e3007e');
  checkpoint_id = create_checkpoint('Check of staff is aware of quality policy and objectives',
                                    '3ea03fd0-d765-42b0-8674-f7cdd0d8d9f1', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Progress towards quality objectives is monitored periodically', 'G7.4', std_id,
                                    '4a6deb8e-a87b-414b-9431-05ceaabeed8c');
  checkpoint_id = create_checkpoint('Quality objectives are monitored and reviewed periodically',
                                    '5842bb58-5524-48d2-9946-6260441dd731', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  std_id = create_standard('The facility seeks continually improvement by practicing Quality method and tools.', 'G8',
                           aoc_id, '0cee0846-0d69-4564-b43d-750519298615');
  me_id = create_measurable_element('The facility uses method for quality improvement in services', 'G8.1', std_id,
                                    'd8cf928d-70dc-461d-ad6b-b30518170a88');
  checkpoint_id = create_checkpoint('PDCA', '9c607790-ba02-4c60-8aec-cdf0b5665650', me_id, checklist_id, '', FALSE,
                                    TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('5S', 'ec66b811-e468-45b6-af9f-411671d54bc2', me_id, checklist_id, '', TRUE, TRUE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint('Mistake proofing', '671c2bd0-cff5-482f-9101-ace5db3816d1', me_id, checklist_id, '',
                                    TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Six Sigma', 'b6c02408-e4b9-4238-ba74-7168710a7734', me_id, checklist_id, '', FALSE,
                                    TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility uses tools for quality improvement in services', 'G8.2', std_id,
                                    '61bf75f7-251f-4749-b11c-cb79084c7b4b');
  checkpoint_id = create_checkpoint('6 basic tools of Quality', '0785a7cf-5b72-41f2-9bd6-1740fa5499d7', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Pareto/Prioritization', '8edd8573-d092-4e05-9af7-b6ce54162fb1', me_id,
                                    checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  aoc_id = create_area_of_concern('Outcome', 'H', 'b4b38234-a335-4035-b2c2-62c2d536231d');
  std_id = create_standard(
      'The facility measures Productivity Indicators and ensures compliance with State/National benchmarks', 'H1',
      aoc_id, '40e0091e-ee02-4a81-953c-770d6af52dd0');
  me_id = create_measurable_element('Facility measures productivity Indicators on monthly basis', 'H1.1', std_id,
                                    '089fa11b-29a1-4b7c-94bb-642b3f993e87');
  checkpoint_id = create_checkpoint('Normal Deliveries per 1000 population', 'f19f41dc-4c02-4529-808e-49060d4afff9',
                                    me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Proportion of deliveries conducted at night',
                                    '37667eff-ba0b-445b-96e2-83bb3d41a366', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Proportion of complicated
cases managed', '441d9dca-d0de-4058-994e-66ff7ad5d098', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Proportion assisted delivery conducted', 'e170871c-b377-4a86-82f8-80c6fbca71ec',
                                    me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('% PPIUCD inserted against
total IUCD', 'ef883ab8-1c17-4c8b-9a99-dd0082b3c25f', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('The Facility measures equity indicators periodically', 'H1.2', std_id,
                                    'ea1e0c3e-37c9-4822-bd74-a7731b76dbd4');
  checkpoint_id = create_checkpoint('Proportion of BPL Deliveries', '01007e3a-05fb-464d-ae41-9e11472d4201', me_id,
                                    checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element(
      'Facility ensures compliance of key productivity indicators with national/state benchmarks', 'H1.3', std_id,
      '8dc449e3-7d76-4aad-ae05-4ee1314d68f2');
  std_id = create_standard('The facility measures Efficiency Indicators and ensure to reach State/National Benchmark',
                           'H2', aoc_id, '9d3f0a9b-365d-4fce-8360-b021393ce704');
  me_id = create_measurable_element('Facility measures efficiency Indicators on monthly basis', 'H2.1', std_id,
                                    'bf0a3d5b-2b00-44d5-9890-262dc1c1e7b8');
  checkpoint_id = create_checkpoint('Proportion of cases referred to OT', '94813574-67ff-4707-86e7-0230d8100543', me_id,
                                    checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Proportion of cases referred to Higher Facilities',
                                    '6e66e395-d8cd-4ecb-bcfe-39a945105f33', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('% of newborns required
resuscitation out of total live
births', '90fa78be-9a3a-4d75-b745-8bf28e97ef5f', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('% of newborns required
resuscitation out of total live
births', '55da31fc-7e9e-4ce5-98ad-10043b0d2301', me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element(
      'Facility ensures compliance of key efficiency indicators with national/state benchmarks', 'H2.2', std_id,
      '975d928a-22ec-4eff-9cc4-f23deb6479c2');
  std_id = create_standard(
      'The facility measures Clinical Care & Safety Indicators and tries to reach State/National benchmark', 'H3',
      aoc_id, '3cfb4eb4-d6b5-423d-bf7f-a5751b9bda9e');
  me_id = create_measurable_element('Facility measures Clinical Care & Safety Indicators on monthly basis', 'H3.1',
                                    std_id, '97d374b4-c9fe-4176-bf5c-fb5a039c3117');
  checkpoint_id = create_checkpoint('Proportion of cases partograph maintained', '1fb12276-7f24-4a0b-b7d0-e5d3adbbf874',
                                    me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Episiotomy site infection rate', 'df3df270-9213-49bf-a6b8-3d673082ff2b', me_id,
                                    checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('No of adverse events per thousand patients',
                                    '15869d4e-f851-47e6-bfbf-36b967432680', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  checkpoint_id = create_checkpoint('Culture Surveillance sterility rate', '39c03218-39ab-4be1-99a7-34d54bd17fad',
                                    me_id, checklist_id, '% of environmental swab culture reported positive', FALSE,
                                    FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Proportion of cases of different complications',
                                    '93815b87-f73d-4c69-8800-baca7a9b0da9', me_id, checklist_id,
                                    'PPH, Eclampsia, obstructed labour etc.', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Rational oxytocin usage Index', '1de940d3-f985-422c-a2f1-868d8c4863ef', me_id,
                                    checklist_id, 'No. of Oxytocin doses used /No. of normal deliveries conducted',
                                    FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element(
      'Facility ensures compliance of key Clinical Care & Safety with national/state benchmarks', 'H3.2', std_id,
      '9d26cdcc-3966-48c2-a822-55dba97f2b73');
  std_id = create_standard(
      'The facility measures Service Quality Indicators and endeavours to reach State/National benchmark', 'H4', aoc_id,
      'fcee71b7-d3f4-4377-adb1-2180fa5a3298');
  me_id = create_measurable_element('Facility measures Service Quality Indicators on monthly basis', 'H4.1', std_id,
                                    '354ccd91-7773-486a-8f83-7be324094d33');
  checkpoint_id = create_checkpoint('Patient satisfaction', '81af0a3f-02ec-4112-8e8b-450dc1e1f429', me_id, checklist_id,
                                    '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('Facility ensures compliance of key Service Quality with national/state benchmarks',
                                    'H4.2', std_id, '592e64ae-b959-4d3a-b98f-f105fc9ac1c2');
  department_id = create_department('OPD', 'd4591d8e-3249-4cea-90b2-425665fdb0d0');
  checklist_id = create_checklist('DH OPD', department_id, assessment_tool_id, '785dfe09-9c0f-4656-b620-dc7fe9659a54');
  department_id = create_department('Pharmacy', '95f0e5fb-69fe-409a-bb9a-80715205fed3');
  assessment_tool_id = create_assessment_tool('Primary Health Center (PHC)', 'd8de2716-cc30-4af7-9011-638e34077222');
  checklist_id = create_checklist('PHC General', department_id, assessment_tool_id,
                                  '88a4b7ff-70f4-4dcf-bcec-70a424b7be52');
  aoc_id = create_area_of_concern('Service Provision', 'A', 'e6aa0616-f065-478a-a9ac-f67f45940671');
  std_id = create_standard('Facility provides Promotive, preventive and curative services', 'A1', aoc_id,
                           '86f45a8a-90c6-46fc-8e96-18dd014abe28');
  me_id = create_measurable_element('The facility provides treatment of common ailments', 'A1.1', std_id,
                                    'e8335e23-1f97-4ef5-8c00-1e65a26e3f96');
  me_id = create_measurable_element('The facility provides Accident & Emergency Services', 'A1.2', std_id,
                                    '925b4d0d-ac60-42c9-96bf-168da5476a3d');
  me_id = create_measurable_element('The facility provides AYUSH Services', 'A1.3', std_id,
                                    'a577eebd-c925-46ff-a525-67b31cdeb78a');
  checkpoint_id = create_checkpoint('Dispensary services are available during OPD hours',
                                    '8480c3bc-42e8-4f84-9546-1c83b779cab7', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('Services are available for the time period as mandated', 'A1.4', std_id,
                                    '1c41d639-d322-4a43-a7a2-8a0dc0b7f9f7');
  std_id = create_standard('The facility provides RMNCHA Services', 'A2', aoc_id,
                           '7a1abaf3-2ede-4aa2-a2a3-24e518261fc2');
  me_id = create_measurable_element('The facility provides Reproductive health  Services', 'A2.1', std_id,
                                    'e8ad3ab6-3f86-4b17-b0dd-159573f9edf3');
  me_id = create_measurable_element('The facility provides Maternal health Services', 'A2.2', std_id,
                                    '663c9ed9-a568-42f2-8a4f-74acb878c096');
  me_id = create_measurable_element('The facility provides New-born health  Services', 'A2.3', std_id,
                                    '3ca34a08-c9b8-4f69-827b-aef0a2aee3a8');
  me_id = create_measurable_element('The facility provides Child health Services', 'A2.4', std_id,
                                    '8d6d0029-cd03-4503-b138-f38cbbd30d3a');
  me_id = create_measurable_element('The facility provides Adolescent health Services', 'A2.5', std_id,
                                    '1b9077f7-4dac-4090-b5ea-8f223a78c449');
  checkpoint_id = create_checkpoint('Availability of Drug Dispensing counter', '635206b1-4190-4b50-b868-44fad7d3b078',
                                    me_id, checklist_id, 'For both Allopathic & Alternate medicines', FALSE, TRUE,
                                    FALSE, TRUE);
  std_id = create_standard('The Facility provides Diagnostic Services, Para-clinical  & support services.', 'A3',
                           aoc_id, 'c45a9dc6-0b3a-4f39-b42f-d877c2b37c11');
  me_id = create_measurable_element('The facility provides Pharmacy services', 'A3.1', std_id,
                                    '1fa82605-707c-4b2b-8565-68c01b7e1d50');
  checkpoint_id = create_checkpoint('Generic Drug Store', '5f7566e8-5e1e-424b-84a7-36c741ed751d', me_id, checklist_id,
                                    'Functional Jan ayushdhalya in premises or equivalent', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Cold chain management services', '5d31aa6a-52ef-4e2f-ab46-f272c10ec555', me_id,
                                    checklist_id, 'Functional refrigerator(s), cool box available', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('The facility provides diagnostic services', 'A3.2', std_id,
                                    '2292eb8b-ecaa-4fc7-b4c6-49845d3a172d');
  me_id = create_measurable_element('The facility provides medico legal and administartive services', 'A3.3', std_id,
                                    'e333fa29-4bb4-406e-af8a-0171f3a1dfca');
  me_id = create_measurable_element('The facility provides support services', 'A3.4', std_id,
                                    '402c4452-3746-43ed-a8cb-d29403ae6d1e');
  checkpoint_id = create_checkpoint('Availability of Drugs under NVBDCP', '2ac422ba-3c32-4f6f-95dc-ba5ced80a03a', me_id,
                                    checklist_id, 'Chloroquine, Primaquine, ACT (Artemisinin Combination Therapy)',
                                    FALSE, TRUE, FALSE, TRUE);
  std_id = create_standard('The facility provide services as mandated in National Health Programmes', 'A4', aoc_id,
                           'd48ca47f-6385-4356-9398-728ddb4acaae');
  me_id = create_measurable_element(
      'The facility provides services under National Vector Borne Disease Control Programme as per guidelines', 'A4.1',
      std_id, '16732914-0708-43da-84f8-a87b5c461431');
  checkpoint_id = create_checkpoint('Availability of Drugs under RNTBCP', 'd51a53b8-8755-43fe-875f-de8160ef5b76', me_id,
                                    checklist_id, 'CAT I  &  CAT II', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element(
      'The facility provides services under Revised National TB Control Programme as per guidelines', 'A4.2', std_id,
      'b777858f-cb7b-4a30-957b-ecae281b7837');
  checkpoint_id = create_checkpoint('Availability of Drugs under NLEP', '2d2e0eb5-bcda-4ac1-8d9c-1d76580ea9f6', me_id,
                                    checklist_id, '', FALSE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element(
      'The facility provides services under National Leprosy Eradication Programme as per guidelines', 'A4.3', std_id,
      'e4773598-5918-40f9-972c-62b2e3b819df');
  me_id = create_measurable_element(
      'The facility provides services under National AIDS Control Programme as per guidelines', 'A4.4', std_id,
      'e3422a2e-1c02-402b-a8bd-e16d9193071f');
  me_id = create_measurable_element(
      'The facility provides services under National Programme for prevention and control of Blindness as per guidelines',
      'A4.5', std_id, '133b25a7-9f1a-462e-a8dd-7a67d85047b8');
  me_id = create_measurable_element('The facility provides services under Mental Health Programme  as per guidelines',
                                    'A4.6', std_id, '3456acf6-c7ba-49d1-964f-c5d94ef687a9');
  me_id = create_measurable_element(
      'The facility provides services under National Programme for the health care of the elderly as per guidelines',
      'A4.7', std_id, '82f8b6c7-9f2d-4628-9ede-006bc971ab3b');
  me_id = create_measurable_element(
      'The facility provides services under National Programme for Prevention and control of Cancer, Diabetes, Cardiovascular diseases & Stroke (NPCDCS)  as per guidelines',
      'A4.8', std_id, '72fa0d7d-c787-49a0-b3f5-a99a3e2b4392');
  me_id = create_measurable_element(
      'The facility Provides services under Integrated Disease Surveillance Programme as per Guidelines', 'A4.9',
      std_id, 'f47290ec-35e2-4e75-8d94-2231613c46dd');
  me_id = create_measurable_element('The facility provide services under National health Programme for deafness',
                                    'A4.10', std_id, '553014ab-0231-41b0-8b41-2a5719e376e2');
  checkpoint_id = create_checkpoint('Availability of Vaccines As per National Immunization Schedule',
                                    'eb6fd448-0c67-423b-9344-306fc9361140', me_id, checklist_id,
                                    'BCG, DPT, OPV, Hepatitis B, Measles, TT, Japanese encephalitis (in select districts)',
                                    FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element(
      'The facility provides services under Universal Immunization Programme (UIP) as per guidelines', 'A4.11', std_id,
      '3025c106-abfd-44db-a09e-a0675a7a6d51');
  me_id = create_measurable_element(
      'The facility provides services under National Iodine deficiency Programme as per guidelines', 'A4.12', std_id,
      'c6d16532-f243-41b0-9fcd-a3770ae8efc7');
  me_id = create_measurable_element(
      'The facility provides services under National Tobacco Control Programme as per guidelines', 'A4.13', std_id,
      '4380a3c3-3bbc-4113-8a2d-eacef23a8456');
  me_id = create_measurable_element('The facility provides services under National Oral Health Care Program', 'A4.14',
                                    std_id, 'c3fb8240-36c7-4212-8465-293651160204');
  std_id = create_standard(
      'The facility provides services as per local needs / State specific health programmes as per guidelines', 'A5',
      aoc_id, 'dbd76223-a6ce-4c73-b45e-5dacc8757496');
  me_id = create_measurable_element(
      'The facility maps its vulnerable population enabling micro-planning for  outreach services', 'A5.1', std_id,
      '791b53c5-4133-43a7-87a4-0675c8726aa9');
  me_id = create_measurable_element(
      'Facility provides services as per local needs/ state specific health programmes as per guidelines', 'A5.2',
      std_id, '970adf87-818c-4dbd-9285-38a110e0c14e');
  aoc_id = create_area_of_concern('Patient''s Rights', 'B', '581326d3-bca4-40dd-b350-ccf1fe4a8506');
  std_id = create_standard('The service provided at facility are accessible', 'B1', aoc_id,
                           '94564eda-5b53-4b9b-9d73-ff6208de26a3');
  me_id = create_measurable_element('The facility has uniform and user-friendly signage system', 'B1.1', std_id,
                                    'e67af3f4-602c-4eb4-875d-54b8a77aef7d');
  checkpoint_id = create_checkpoint('List of Drugs available displayed & updated daily at Pharmacy',
                                    '11785305-23bc-44d9-906a-636b25a999e3', me_id, checklist_id,
                                    'Updated daily is too stringent and also sometimes list may also be very long for it to be displayed and change daily.  In fact some facilities write and circulate list of drug not available',
                                    TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility displays the services and entitlements available', 'B1.2', std_id,
                                    'ec28fd83-4d2c-41bd-9fe3-055cded76a77');
  me_id = create_measurable_element('The facility has established citizen charter', 'B1.3', std_id,
                                    'f2d00a12-df3c-4d0e-b623-5fb195f35a5c');
  me_id = create_measurable_element(
      'Patients & visitors are sensitized and educated through appropriate IEC / BCC approaches', 'B1.4', std_id,
      'a4a0fe46-dc64-4951-bff6-88bbfe51f654');
  me_id = create_measurable_element('Information is available in bi-lingual signage  and easy to understand', 'B1.5',
                                    std_id, '782d4f9d-9909-47c2-ac58-7c978150224e');
  me_id = create_measurable_element('The facility has defined and established grievance redressal system in place',
                                    'B1.6', std_id, '5032a5b1-aec9-4a74-887c-67f6536fe62b');
  checkpoint_id = create_checkpoint(
      'Method of Administration /taking of  the medicines is informed to patient/ their relative by pharmacist as per  doctors prescription in OPD Pharmacy',
      'fecf00f1-d721-47a1-aa66-f0f54f78e551', me_id, checklist_id, '', TRUE, FALSE, TRUE, FALSE);
  me_id = create_measurable_element(
      'Information about the treatment is shared with patients or attendants and consent is taken wherever required',
      'B1.7', std_id, 'e4ab2f6d-5c29-497d-a601-28e9e6e52612');
  me_id = create_measurable_element('Access to facility is provided without any physical barrier', 'B1.8', std_id,
                                    'a565e0eb-5fb1-4783-b384-5b9c2871d95d');
  checkpoint_id = create_checkpoint('Availability of separate Queue for Male and female at dispensing counter',
                                    'bfb10640-902d-4931-96e4-01449c96ff8f', me_id, checklist_id,
                                    'Check whether there are separate queues', TRUE, FALSE, FALSE, FALSE);
  std_id = create_standard('The service provided at facility are acceptable', 'B2', aoc_id,
                           'd949b03f-e48b-4abb-afda-eb6902430c06');
  me_id = create_measurable_element('Services are provided in manner that are sensitive to gender', 'B2.1', std_id,
                                    'bbeeddd7-7d65-4306-9619-d0f84c381cb2');
  me_id = create_measurable_element('Adequate visual privacy is provided at every point of care', 'B2.2', std_id,
                                    'eba86049-bce0-4f26-aa7f-4f333e2e2cef');
  me_id = create_measurable_element('Confidentiality of patients'' records and clinical information is maintained',
                                    'B2.3', std_id, 'b9099fbc-7de5-47a7-907c-7b9f9211fac0');
  me_id = create_measurable_element(
      'The facility ensures the behaviours of staff is dignified and respectful, while delivering the services', 'B2.4',
      std_id, 'c87d71a3-6efa-4ad8-b03c-4e3c2dd1d82d');
  me_id = create_measurable_element(
      'Religious and cultural preferences of patients and attendants are taken into consideration while delivering services',
      'B2.5', std_id, '2067cd8d-e84f-483e-b91e-cd9860152656');
  checkpoint_id = create_checkpoint('Free drugs and consumables for  provided to mothers & Children',
                                    '4be93e1d-e6e3-43be-815b-87ead9f3e060', me_id, checklist_id,
                                    'Check Pregnant women, Mother and Childrens upto 5 years are prescribed  and dispensed all drugs and consumables',
                                    FALSE, FALSE, TRUE, FALSE);
  std_id = create_standard('The service provided at facility are affordable', 'B3', aoc_id,
                           'e808c662-cbd8-42e1-89da-96fad7266bcb');
  me_id = create_measurable_element(
      'The facility provides cashless services to all patients including pregnant women, mothers and sick children as per prevalent government schemes',
      'B3.1', std_id, '64e257e5-a42a-47ce-80a5-eb0c52b04e67');
  me_id = create_measurable_element(
      'The facility provide free of cost treatment to Below poverty line patients without administrative hassles',
      'B3.2', std_id, '074567a1-ee74-45a9-b9aa-fd2731be22f6');
  checkpoint_id = create_checkpoint(
      'Check patient has not spent on purchasing drugs & consumbles those are included in essential medicine list',
      '29572f63-8453-4368-ac4d-747dd50fe3aa', me_id, checklist_id,
      'Check for availability of the Essential Medicines List/Formulary', FALSE, FALSE, TRUE, FALSE);
  me_id = create_measurable_element('The facility ensures that the drugs prescribed are available in the pharmacy',
                                    'B3.3', std_id, '700c2793-2799-4e5a-ac99-63d2c80d5e62');
  me_id = create_measurable_element('Facility ensure investigation  prescribed  are available at the Laboratory',
                                    'B3.4', std_id, '2208443b-d63b-4a51-9174-d0edf0954594');
  checkpoint_id = create_checkpoint('Availability of adequate space for Drug store and Dispensing counter',
                                    '6d219ef2-e81f-4d69-b2b3-12381ee9e4b4', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  aoc_id = create_area_of_concern('Inputs', 'C', 'da0cb06e-fc6f-4a92-98c6-57e30d82e641');
  std_id = create_standard(
      'The facility has adequate & Safe infrastructure for delivery of assured services and  meets the prevalent norms',
      'C1', aoc_id, 'a6230b26-e861-4ac0-88db-5c9ed9163dea');
  me_id = create_measurable_element('Departments have adequate space as per patient load', 'C1.1', std_id,
                                    'd256b5ae-37cd-43ba-95dd-08c14cd1c2a7');
  checkpoint_id = create_checkpoint('Provision of shaded area in front of Drug Dispensing Counter',
                                    'e5dfce42-a1a1-42e1-b8e5-57b991b4705d', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Amenities for Patients & Staff are available as per load', 'C1.2', std_id,
                                    '4573a57f-36a6-4223-8c42-520b80a5eff0');
  me_id = create_measurable_element('Departments have layout and demarcated areas as per functions', 'C1.3', std_id,
                                    '7ef98aab-1fa7-4acd-b305-f2f302b10dd7');
  me_id = create_measurable_element('The facility has infrastructure for intramural and extramural communication',
                                    'C1.4', std_id, '04711f98-939d-4022-bdec-980668dfa482');
  me_id = create_measurable_element('The facility ensures safety of electrical installations', 'C1.5', std_id,
                                    '81ba2fe9-3ae9-4b16-9e78-76df6ccdd5f7');
  me_id = create_measurable_element('Physical condition of buildings are safe for providing patient care', 'C1.6',
                                    std_id, '01a536ed-a09a-455e-be81-b77eeb5cf556');
  checkpoint_id = create_checkpoint(
      'Pharmacy has plan for  safe storage and handling of potentially flammable materials.',
      '8bddc55d-6309-4b99-90b3-724ba61ecaa9', me_id, checklist_id,
      'Check for trash (empty cartons) stored in the store; flammables are stored separately; no smoking zone;   and availability of fire extinguishers and extinguisher is not time barred',
      TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility ensures fire safety measures including fire fighting equipment',
                                    'C1.7', std_id, 'd5a669d8-78b7-4d9d-9b40-abb0c57cb5c0');
  std_id = create_standard(
      'The facility has adequate qualified and trained staff,  required for providing the assured services to the current case load',
      'C2', aoc_id, 'eba32d71-0755-45b0-9fa1-aa0c4c463f30');
  me_id = create_measurable_element('The facility has adequate medical officers as per service provision and work load',
                                    'C2.1', std_id, 'a8e180c9-1ceb-4c5e-a6c4-5991881ad266');
  checkpoint_id = create_checkpoint('Availability of one Pharmacist at Drug dispensing counter during OPD timings',
                                    'c356e40d-f37a-4ba0-9736-0a25cf78c6e7', me_id, checklist_id,
                                    'Check whether the pharmacy is manned during OPD hours', FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element(
      'The facility has adequate nursing staff/Paramedics as per service provision and work load', 'C2.2', std_id,
      'b6b71698-5481-4574-a621-fd06f2ed163c');
  me_id = create_measurable_element(
      'The facility has adequate support staff / Health Workers as per service provision and workload', 'C2.3', std_id,
      '2af9ed6e-f4a9-4eaa-9433-5064415e399c');
  checkpoint_id = create_checkpoint('Training on Invantory Mangement and Drug Storage',
                                    '75eee6b9-0ea2-412f-83d0-195534c89bcc', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element(
      'The Staff has been imparted necessary trainings/skill set to enable them to meet their roles & responsibilities',
      'C2.4', std_id, '22dc3cad-ab8b-40c9-a12e-f42976fe4263');
  checkpoint_id = create_checkpoint(
      'Pharmacist is skilled for good dispensing practices and inventory management technique',
      '75714f2c-2161-4fdd-9235-1c616fb02e12', me_id, checklist_id, 'Competence Testing', FALSE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('The Staff is skilled and competent as per job description', 'C2.5', std_id,
                                    'b2d0eda2-a027-45df-a413-9f9883ef8728');
  checkpoint_id = create_checkpoint('Pharmacist is skilled for Cold Chain Mangement',
                                    'b4e33089-1fd8-433a-ab0a-db62f8c91ae8', me_id, checklist_id, 'Competence Testing',
                                    FALSE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Availability of Analgesics/ Antipyretics', '58efef42-6511-4def-8a5b-a3229a99d49f',
                                    me_id, checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  std_id = create_standard('The facility provides drugs and consumables required for assured services.', 'C3', aoc_id,
                           'd9c567f8-67a6-4f00-9a13-b41e097be205');
  me_id = create_measurable_element('The facility has availability of adequate drugs at point of use', 'C3.1', std_id,
                                    '011e4652-3e8d-4ffe-876c-27be8e47ed9a');
  checkpoint_id = create_checkpoint('Antiallergics and Drugs used in Anaphylaxis',
                                    'a442b083-603e-45a6-8e3b-e88cf16731a1', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antidotes and other substances used in Poisoning',
                                    '5685d672-ed08-4422-8c46-dd7d49f8e3b8', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Anticonvulsants/ Antiepileptics', 'e696ca62-c730-4aff-8864-1abfd6e1abb0', me_id,
                                    checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antihelmenthics', '453cbb6b-7ba5-4d48-bdd1-7e66d09add2c', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antibacterial (Beta Lactam)', '16d908ee-d382-4722-8015-2b893704f2d3', me_id,
                                    checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antibacterial (Others))', '0e68c07a-8028-4c41-889d-27cbb9bd11cf', me_id,
                                    checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antifungal', 'e5e13ddb-5c1a-42ab-9263-a847f530de7c', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antianaemia', '07cd603c-08c7-4066-a105-74decbd587a4', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Plasma Substitutes', '80bbde63-b5a4-4db8-8d9c-93b0b2ff9c90', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antianginal medicines', '0e324194-96dc-48d4-80fa-630332839a11', me_id,
                                    checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antihypertensive medicines', 'e4541bef-cb5f-4d9b-9df3-f60c06c44119', me_id,
                                    checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Anti infective & Antifungal (Topical)', '506dfe04-1d48-4b87-a580-544e5edf3095',
                                    me_id, checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antiinfalmatory & Others (Topical)', '16633886-1d5a-47c6-b317-6917b0e519e2', me_id,
                                    checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Gastrointestinal Medicines (Antacids & Antemitics)',
                                    '29c28c64-90d4-4466-aace-ca4a85884ad4', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Gastrointestinal Medicines (Antispasmodic & Laxatives)',
                                    '771db955-9353-48fc-b0b0-5e69b11e6112', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Medicines used in diarrhorea', 'cf4230c0-9f39-4f4d-887e-0c7cc345115a', me_id,
                                    checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Hormones', '69249ae3-e581-4ff0-899e-64cadc78a0d0', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Medicines used in Diabetes mellitus', '9dc7a3c1-45a2-42d8-bff7-734e724b3ca2',
                                    me_id, checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Immunologicals', '32036319-5f42-4537-9604-2bda0a8723b2', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Opthalmic Preperations', 'd14a6a87-0c31-451d-a03c-f2081ebda0ef', me_id,
                                    checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Oxytocics', '17b729a0-f5da-47d5-8547-01ce365e5713', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Medicines acting on the respiratory tract', '0ba6fc33-e050-4f96-950d-32001391597d',
                                    me_id, checklist_id, 'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('IV Fluids', '0a3d5686-986e-49ff-a114-387902555a28', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Vitamin & Minerals', '9e3265bd-5e29-4847-94b2-280e6b2eff3f', me_id, checklist_id,
                                    'As per state Drug List', TRUE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('The Facility has availability of adequate consumables at point of use', 'C3.2',
                                    std_id, '4ee52451-ecaf-4f19-af4e-287249252ed2');
  std_id = create_standard('The facility has equipment & instruments required for assured list of services.', 'C4',
                           aoc_id, 'aad64ae2-fff8-4742-94e0-cbfc038ffafc');
  me_id = create_measurable_element('Availability of equipment & instruments for examination & monitoring of patients',
                                    'C4.1', std_id, '298e42fa-b60c-444a-943e-6b235e8735bd');
  me_id = create_measurable_element(
      'Availability of equipment & instruments for treatment procedures, being undertaken in the facility', 'C4.2',
      std_id, '9fa692ba-a2c5-4472-addc-ad778ed5b531');
  me_id = create_measurable_element(
      'Availability of equipment & instruments for diagnostic procedures being undertaken in the facility', 'C4.3',
      std_id, 'a2d6ad2e-d1fe-41fd-90d5-d7c03a467d9e');
  checkpoint_id = create_checkpoint('Availability of ILR & Deep freezer for cold chain',
                                    '967d5e2c-7842-4b71-9fcd-ab8ab8cc660c', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Availability of equipment for storage', 'C4.4', std_id,
                                    '1d52d48b-8875-41a2-a137-3381ac66b481');
  checkpoint_id = create_checkpoint('Availability of racks for Storage of drugs',
                                    '69a3eeb1-c827-4d85-b30f-e38da18d2f19', me_id, checklist_id,
                                    'Check for medicines are not stored on the floor', TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('Availability of  patient furniture and fixtures as per load and service provision',
                                    'C4.5', std_id, 'fedd3fb5-5490-4dcd-ae12-c4688d45e0f2');
  me_id = create_measurable_element(
      'Availability of functional equipment and instruments for support & outreach services', 'C4.6', std_id,
      'af9afa94-d936-4e70-86ac-be2fdde62bf4');
  checkpoint_id = create_checkpoint('Cold Storage equipments are under AMC  and temperature log book',
                                    'c39922f5-0ef9-4a48-8a64-fc8a99d83e76', me_id, checklist_id,
                                    'Check for AMC for  ILR, deep freezer', FALSE, TRUE, FALSE, TRUE);
  aoc_id = create_area_of_concern('Support Services', 'D', '91b913af-070d-4507-a07e-60be2d5b5aaf');
  std_id = create_standard(
      'The facility has established facility management programme for maintenance & upkeep of equipment & infrastructure to provide safe & secure environment to staff & users',
      'D1', aoc_id, '1505c29c-0115-4719-9b89-68aa48e5b952');
  me_id = create_measurable_element('The facility has  system for maintenance of critical Equipment', 'D1.1', std_id,
                                    '819edf3a-a3b4-4ce4-8dff-1edd5d7c2bba');
  checkpoint_id = create_checkpoint('Temprature control at Pharmacy & medical store',
                                    'd1002484-3864-4af9-9e10-0da5d161b71c', me_id, checklist_id,
                                    'Check drugs are stored at optimum temprature. AC preferably, if not provision adequate ventilation .  Medicines are not stored in corridor or exposed to sunlight',
                                    FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('The facility ensures  comfortable environment for patients and service providers',
                                    'D1.2', std_id, '3c44eabd-1157-4356-9c69-9ec21a120c7d');
  checkpoint_id = create_checkpoint('Drug Storage area and Pharmacy Counter are clean',
                                    'd8011e0c-ba70-438f-9cfc-e130b0bc9c37', me_id, checklist_id,
                                    'Check for dirt, stains, Dust on wall , floors and fixtures.Scattered loose medicines, empty boxes etc',
                                    TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('Patient care areas are clean and hygienic', 'D1.3', std_id,
                                    '495aa581-a805-44a4-9a1d-3407edf5c60b');
  me_id = create_measurable_element('Facility  infrastructure is adequately maintained', 'D1.4', std_id,
                                    'fa936a8b-6c26-497e-8f74-2ff999416bc9');
  checkpoint_id = create_checkpoint('No junk, condemed, unused articles in the pharmacy',
                                    '2ec40cf0-dd3d-4cff-b414-b78dc84705f2', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Facility has policy of removal of condemned junk material', 'D1.5', std_id,
                                    'd4211788-5dfd-4e25-b605-1eb669c8a6e0');
  me_id = create_measurable_element('Facility  maintains both the internal and open area of the facility.', 'D1.6',
                                    std_id, '4a9d9bf5-55c4-480f-b47a-b53c033b4914');
  me_id = create_measurable_element('The facility provides adequate illumination level at patient care areas', 'D1.7',
                                    std_id, 'c3a1bc4a-03cc-4ca1-a64a-c4ff73dd3dd9');
  me_id = create_measurable_element('The facility provides Clean and adequate linen as per requirement', 'D1.8', std_id,
                                    '7718673b-fc6c-4d98-a81e-a02ddb907acc');
  me_id = create_measurable_element(
      'The facility has adequate arrangement for storage and supply of potable water in all functional areas', 'D1.9',
      std_id, '147ac954-0dc5-4615-96a3-337e1b08729b');
  checkpoint_id = create_checkpoint('Power backup arrangement for cold chain equipments',
                                    '926c57c2-510d-4f37-be3b-04590c326408', me_id, checklist_id,
                                    'Check for record of  duration of  power outage and duration of back -up available.',
                                    TRUE, TRUE, FALSE, FALSE);
  me_id = create_measurable_element('The facility ensures adequate power backup', 'D1.10', std_id,
                                    'a559cb12-744c-466d-b633-d705902b45af');
  checkpoint_id = create_checkpoint(
      'UPHC  has process to consolidate and calculate the consumption of all drugs and consumables',
      'a12fe83c-4a92-44f7-80e9-d7d780736e40', me_id, checklist_id, '', FALSE, TRUE, FALSE, TRUE);
  std_id = create_standard(
      'Facility has defined procedure for  storage, Inventory Management & dispensing of drugs in pharmacy', 'D2',
      aoc_id, '65302ee1-e477-44d5-a1be-278e198a9cc9');
  me_id = create_measurable_element(
      'The facility has established procedures for estimation, indenting and procurement of drugs and consumables',
      'D2.1', std_id, 'da3c1d6b-0a23-4169-a8d6-b3342918e709');
  checkpoint_id = create_checkpoint(
      'Forecasting  of drugs and consumables  is done scientifically  based on consumption',
      '77044f00-d434-4ed8-86e4-3c1bad93bd5f', me_id, checklist_id,
      'Check for stock-outs and wastage (expiry, damaged medicines), if any are adjusted while forecasting', FALSE,
      TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Facility has a established procedures for local purchase of drugs in emergency',
                                    '0b38d004-09a0-4853-b07c-ce5f985e0e01', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('UPHC has system for timely placing requisition to district drug store',
                                    '5c2cfcef-05df-4ace-9e58-24046c32ac97', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('There is specified place to store medicines in Pharmacy',
                                    'c7795984-5607-4435-9e46-a693317751f4', me_id, checklist_id,
                                    'Drugs are stored according to therapeutic category/alphabetically or according to their dosage form',
                                    TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element('The facility ensures proper storage of drugs and consumables', 'D2.2', std_id,
                                    'c20f3042-3f09-443f-91cb-2b6244057cf7');
  checkpoint_id = create_checkpoint(
      'All the shelves/racks containing medicines  are labelled in  pharmacy and drug store',
      'd2cc2728-0c23-46ae-8d76-a84d0380e844', me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Product of similar name and different strength are stored separately',
                                    '7af86957-cafa-46bb-9d43-aea9fdf7957b', me_id, checklist_id,
                                    'Facility has a list of drugs with similar names and different strength and are stored separately & labelled',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Heavy items are stored at lower shelves/racks',
                                    '7e767441-36e0-4e4a-8a14-981dc34a4b6e', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Fragile items are not stored at the edges of the shelves.',
                                    '90de2774-2611-4cbb-974a-e94d04c11196', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint(
      'Sound alike and look alike medicines (LASA) are stored separately in patient care area and pharmacy',
      '99db5112-a90f-44b4-b349-1d8a50bef977', me_id, checklist_id,
      'Facility has a list of LASA and are stored separately in patient care area and pharmacy', TRUE, FALSE, FALSE,
      FALSE);
  checkpoint_id = create_checkpoint('Drugs and consumables are stored away from water and sources of  heat,
direct sunlight etc.', '1060936d-1eff-4019-8ae6-dc2d0ce49951', me_id, checklist_id,
                                    'Drugs are not stored in the corridor or outside toilets/damp places', TRUE, FALSE,
                                    FALSE, FALSE);
  checkpoint_id = create_checkpoint(
      'Drugs are not stored directly on the floor and adjacent to wall especially walls directly facing sun light',
      '0dfdbfe8-f8dd-46b3-b537-4a0ff7fcc493', me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint(
      'Facility has a  procedure in place to avoid expiry of medicines and identifies near expiry drugs',
      'ac8c1354-6090-46d9-98f4-7a28d88ae0c3', me_id, checklist_id, '', TRUE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility ensures management of expiry and near expiry drugs', 'D2.3', std_id,
                                    '842a11b6-3655-496f-ab12-83599226bb27');
  checkpoint_id = create_checkpoint(
      'There is a earmarked area for keeping   expiry drugs distant from regular drugs to avoid mixing',
      '649563ff-5b2f-4e3f-9708-1feca9f8dcb6', me_id, checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('There is a established process for disposal fo expiry drugs',
                                    '403f8038-9034-45f7-ba7a-7b83c10e90a7', me_id, checklist_id,
                                    'Staff is aware of the process and Check for last condemnation procedure undertaken',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'There is system about transfer of surplus / near expiry drugs to other nearby facility / district stores',
      '19d7c541-5b61-459b-a84a-2edc13ed9256', me_id, checklist_id,
      'Check for initiation of transfer process done with adequate remaining shelf life (preferably at least 3 months in advance)',
      FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Physical verification of inventory is done periodically',
                                    'e693141f-4b69-4e3f-93ba-215cb407e6a7', me_id, checklist_id,
                                    'Has periodicity of physical verification defined (quaterly/biannually/annually). Check when last physical verification done',
                                    FALSE, TRUE, FALSE, TRUE);
  me_id = create_measurable_element('The facility has established procedure for inventory management techniques',
                                    'D2.4', std_id, '990e77db-d66e-4963-b55c-50a11738ec75');
  checkpoint_id = create_checkpoint('Facility uses bin card system and updated each time stock is handled',
                                    '4b5ccac8-49f2-4159-8407-80401db47869', me_id, checklist_id,
                                    'Bin cards are kept for each item in the stock room and physical count of remainind stock done.  Check for last posting on the bin card.',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('First expiry first out system is established for drugs',
                                    'b7a46419-5fc6-4515-950b-a714b9435aea', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Stores has defined minimum and reorder level defined',
                                    'c755a1e6-0964-4a4c-af54-6615dd421455', me_id, checklist_id,
                                    'Check for minimum and reorder level defined for vital drug as per their consumption pattern',
                                    FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Drugs are categorized in Vital, Essential and Desirable',
                                    '71939867-e3a2-4199-b604-ddcc6ded822c', me_id, checklist_id,
                                    'Check for list of VED  categorization', FALSE, TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint(
      'Check vaccines & diluents are placed  in specified shelf/compartment inside the storage unit and are clearly labeled.',
      '2a9e44d7-7b54-4468-8fc5-e2051cdd8797', me_id, checklist_id,
      '(Top to bottom) : Hep B, DPT, DT, TT, BCG, Measles, OPV. Vaccines are not stored in door. Check food/drinking water not stored in the vaccine refrigerator',
      TRUE, FALSE, FALSE, FALSE);
  me_id = create_measurable_element(
      'There is process for storage of vaccines and other drugs, requiring controlled temperature  & storage environment',
      'D2.5', std_id, '4ba6e855-ed3f-4156-8ef1-6797b3924ecb');
  checkpoint_id = create_checkpoint('Work instruction for storage of vaccines are displayed at point of use',
                                    'ab41d1ad-e53c-49be-a81a-a1be0443fcd4', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('ILR and deep freezer have functional  temperature monitoring devices',
                                    '48e43192-0429-4b26-a4b7-74bac0edab97', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('There is system in place to maintain temperature chart of ILR',
                                    'cb46b21f-2315-472d-b583-c32d77e646e8', me_id, checklist_id,
                                    'Temp. of ILR: Min +2 degree C to 8 degree C in case of power failure min temp. +10 degree C . Daily temperature log are maintained.  Corrective action of any temperature excursion taken.',
                                    TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('There is system in place to maintain temperature chart of  deep freezers',
                                    'e14544d8-01f2-445c-8758-474c1d9f0ac5', me_id, checklist_id,
                                    'Temp. of Deep freezer cabinet is maintained between -15 degree C to -25 degree C.Daily temperature log are maintained. Corrective action of any temperature excursion taken.',
                                    TRUE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Check thermometer in ILR is in hanging position',
                                    '77458736-16f2-43dd-a342-c54b05c26950', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('ILR and deep freezer has functional alarm system',
                                    '600e619f-1738-405d-842b-b1f90cc91e0d', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Conditioning of ice packs is done prior to transport',
                                    '1bff53f7-d56f-443a-813d-50db4e2af17f', me_id, checklist_id,
                                    'Check if staff is aware of how to condition ice pack (water beads on the surface of ice pack and sound of water is heard on shaking it)',
                                    FALSE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Staff is aware of Hold over time of cold storage equipments',
                                    'ec1e6900-9477-4f8d-93c0-d51be4a405f8', me_id, checklist_id,
                                    'Hold over time depends on  Factors - the amount of vaccine being stored in the refrigerator, the external temperatures and the refrigerator will affect the duration of time vaccines within the refrigerator will be kept within +2 °C to +8 °C.Do not allow the vaccine to remain in a non-functioning unit for an extended period of time.',
                                    FALSE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Drugs are arranged in demarcated boxes /containers /trays',
                                    '5c1ca9a2-0471-4d2c-aa01-c979da6bc06d', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility has established procedure for dispensing of drugs', 'D2.6', std_id,
                                    '1079f82c-2d52-4e84-bbfc-ec7d43dae153');
  checkpoint_id = create_checkpoint('Drug boxes/containers are legibly labeled', 'e0089a7d-22be-4209-b007-b39b0ae6a1df',
                                    me_id, checklist_id,
                                    'Label is firmly attached to container with Generic name and strength of drug is written',
                                    TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint(
      'Pharmacist check drugs name, strength, dosage form and route of adminstration before dispensing',
      '14f4645d-1602-4cfe-b03d-3d6900ae183c', me_id, checklist_id,
      'Check if pharmacists dispenses to identified patients', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Drugs are dispensed in Envelops', 'f4ee2de5-9137-4bc1-9c37-4d2d376982f9', me_id,
                                    checklist_id, '', TRUE, FALSE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('List of look alike and sound alike drugs is displayed at dispensing counter',
                                    'd275359e-3032-44e3-b5e9-14996d245617', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Drugs are given for no. of days as prescribed',
                                    'b6c8ab7a-6613-4f7f-a381-9b1cd9875b69', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Drugs are not directly dispensed from drug storage area',
                                    '0fc2f0d7-22a5-41eb-97f0-cfe950065ec0', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    FALSE);
  checkpoint_id = create_checkpoint('Repeat drugs are given only after approval from medical officer',
                                    '1350365d-62a0-40c3-ba4e-f54ddced1358', me_id, checklist_id,
                                    'Medicines are dispensed to only authorized patients registered for the day', TRUE,
                                    TRUE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Strip cutting is not done', '127a4349-a0af-4136-a2ed-3769dd56c866', me_id,
                                    checklist_id, '', TRUE, TRUE, FALSE, FALSE);
  checkpoint_id = create_checkpoint('Dispensing register is updated in real time',
                                    '2e7248e8-1d3c-4b83-a6b7-2c09073f5ba0', me_id, checklist_id, '', TRUE, TRUE, FALSE,
                                    TRUE);
  checkpoint_id = create_checkpoint('Check Patients having knowledge about correct use of medicines.',
                                    '815b08b0-ddd6-4a14-847b-377e6a93b1eb', me_id, checklist_id,
                                    'Pharmacist providing information about correct use of medicines to the patients- at least purpose, no. of tablets, frequency and duration of treatment.',
                                    FALSE, FALSE, TRUE, FALSE);
  std_id = create_standard(
      'Facility has  defined & established procedure  for Community Participation for providing assured services', 'D3',
      aoc_id, 'ec237274-b007-4c00-b2d7-9e4b6b7c7ef1');
  me_id = create_measurable_element(
      'The facility has established procedures for management of activities of Rogi Kalyan Samiti', 'D3.1', std_id,
      '9be44340-a3bd-40b1-9ac1-8553c97a8382');
  me_id = create_measurable_element(
      'The facility has established procedures for community based monitoring of its services', 'D3.2', std_id,
      '96eb0ec0-ad5e-4ce8-a8f0-be5128c3b793');
  me_id = create_measurable_element(
      'The facility has established procedure for supporting and monitoring activities of community health work -ASHA',
      'D3.3', std_id, '9183ffb6-9700-4e3b-941c-85a88d067bec');
  me_id = create_measurable_element(
      'The facility has established procedure for supporting and monitoring activities of Mahila Arogya Samiti', 'D3.4',
      std_id, '7835e7b4-bea5-479e-b696-0e402d8fd8dc');
  std_id = create_standard('Facility has defined  procedure for Governance & work  Management', 'D4', aoc_id,
                           '41846ece-314f-4a87-82c1-7f459119ccb5');
  me_id = create_measurable_element('The facility ensures the proper utilization of fund provided to it', 'D4.1',
                                    std_id, 'fbdb50c8-834a-4d25-b6ce-26704db96a1b');
  me_id = create_measurable_element('There is established system for contract management for out-sourced services',
                                    'D4.2', std_id, '8cb1343c-4b2a-4b1a-80d4-f51c5be260d3');
  me_id = create_measurable_element('The facility has established job description as per Govt guidelines', 'D4.3',
                                    std_id, '8390d4c5-0439-4d4b-887f-5bd82d01e30e');
  me_id = create_measurable_element('The facility has a established procedure for duty roster and deputation of staff',
                                    'D4.4', std_id, 'e8ecc313-6db5-42d4-a57e-46e2feba80fa');
  me_id = create_measurable_element('The facility ensures the adherence to dress code as mandated by the department',
                                    'D4.5', std_id, 'e7f56d45-adad-4601-9cc6-949801fff142');
  me_id = create_measurable_element(
      'The facility has requisite licences and certificates, as required for operation of a health facility', 'D4.6',
      std_id, '780b602e-5a8e-4b66-964c-45cb34402cc3');
  me_id = create_measurable_element(
      'The facility ensures its processes are in compliance with statutory and legal requirement', 'D4.7', std_id,
      'a8b3b05a-28d0-4aaf-ad5a-13d597d782b7');
  me_id = create_measurable_element('The facility has a  defined protocol for the issue of medical certificates',
                                    'D4.8', std_id, 'e29e5211-2403-4077-9c37-e46a305d81f9');
  std_id = create_standard(
      'Facility has procedure for collecting & Reporting of the health facility related information', 'D5', aoc_id,
      'b83f5a11-c84a-4c12-b228-260aa2fade27');
  me_id = create_measurable_element(
      'The facility provides monitoring and reporting services under National Vector Borne Disease Control Programme as per guidelines',
      'D5.1', std_id, '7ad13487-3eb3-4ffa-99a4-8085c174067f');
  me_id = create_measurable_element(
      'The facility provides services monitoring and reporting services under Revised National TB Control Programme, as per guidelines',
      'D5.2', std_id, '99650c5b-768d-4fba-837d-30c2cd933d2b');
  me_id = create_measurable_element(
      'The facility provides monitoring and reporting services under National Leprosy Eradication Programme as per guidelines',
      'D5.3', std_id, 'f87f0852-c938-4224-bf0c-20642465a0e0');
  me_id = create_measurable_element(
      'The facility provides services under National AIDS Control Programme, as per guidelines', 'D5.4', std_id,
      '03001591-c6e8-4ab1-aa24-9a3b74d5927c');
  me_id = create_measurable_element(
      'The facility provides monitoring and reporting services under National Programme for control of Blindness as per guidelines',
      'D5.5', std_id, '1999925c-104e-4651-91ac-0779f6fadd1f');
  me_id = create_measurable_element(
      'The facility provides monitoring and reporting services under Mental Health Programme, as per guideline', 'D5.6',
      std_id, '725bc514-a3cd-4dfa-9e96-4fe73ac09cdf');
  me_id = create_measurable_element(
      'The facility provides monitoring and reporting services under National Programme for the health care of the elderly as per guidelines',
      'D5.7', std_id, '278f3daf-f841-4493-800d-5d3abfe3fa58');
  me_id = create_measurable_element(
      'The facility provide monitoring and reporting service for prevention and control of Cancer, diabetes, cardiovascular disease and stroke as per guidelines',
      'D5.8', std_id, '4d763997-56ac-44b2-bf73-91f7be61d762');
  me_id = create_measurable_element(
      'The facility provide monitoring and reporting service for Integrated Disease Surveillance Programme, as per guidelines',
      'D5.9', std_id, '8781b86a-c6ef-44a3-bdc2-d6419038d065');
  me_id = create_measurable_element(
      'The facility provide services under National Programme for prevention and control of deafness, as per guidelines',
      'D5.10', std_id, 'e6005808-d79c-43f6-86e7-065d037621e7');
  me_id = create_measurable_element(
      'The facility provides monitoring and reporting services under Universal Immunization Programme, as per guidelines',
      'D5.11', std_id, '1cd37b48-14c4-4010-b80f-9d861cf57469');
  me_id = create_measurable_element(
      'The facility provides monitoring and reporting services under National Iodine deficiency Programme, as per guidelines',
      'D5.12', std_id, '620ac34f-0761-42bd-ac16-20bb7dce605c');
  me_id = create_measurable_element(
      'The facility provides monitoring and reporting services under National tobacco Control Programme, as per guidelines',
      'D5.13', std_id, '31527662-ff20-47dd-a2b3-119c0d9d1ee7');
  me_id = create_measurable_element('Facility Reports data for Mother and Child Tracking System as per Guidelines',
                                    'D5.14', std_id, '070b7bd0-aba7-4f72-b610-86b317530c52');
  me_id = create_measurable_element('Facility Reports data for HMIS System as per Guidelines', 'D5.15', std_id,
                                    'dced8593-0985-4e68-9a1e-88628fccaa67');
  aoc_id = create_area_of_concern('Clinical Services', 'E', 'fec0789e-feb3-4320-8001-59d9c9c62d20');
  std_id = create_standard('The facility has defined procedures for registration and consultation  of patients.', 'E1',
                           aoc_id, '0988f363-5273-46bd-bd72-6d14ee084a31');
  me_id = create_measurable_element('The facility has established procedure for registration of patients', 'E1.1',
                                    std_id, '050d5318-492e-40a2-8f6b-31760f195c72');
  me_id = create_measurable_element('The facility has a established procedure for OPD consultation', 'E1.2', std_id,
                                    '6e85376b-39e6-4677-a7cf-bb25a1c1ae8d');
  std_id = create_standard(
      'Facility has defined procedure for  primary management and continuity of care with appropriate maintenance of records',
      'E2', aoc_id, '748d37a2-4a68-4e23-9085-0c84c02fc0d4');
  me_id = create_measurable_element('There is established procedure for initial assessment & Reassessment of patients',
                                    'E2.1', std_id, '374dfc8f-1f6c-4a64-a6d7-c11a239c6b95');
  me_id = create_measurable_element(
      'The facility provides appropriate referral linkages  for transfer to other/higher facilities to assure the continuity of care.',
      'E2.2', std_id, '0f0f11c9-510c-4ee9-85d5-9fdd1623e469');
  me_id = create_measurable_element('Facility ensures follow up of patients', 'E2.3', std_id,
                                    'd9bc369e-75bb-4cd4-88f5-e817c042b6ac');
  me_id = create_measurable_element('Facility has establish procedure for Triage & diaster Management', 'E2.4', std_id,
                                    '362d1899-6f1c-4e16-9ce5-2f3df421cf77');
  me_id = create_measurable_element('Emergency protocols are defined and implemented', 'E2.5', std_id,
                                    '5e8b201d-8ff2-4820-bce7-0ec2ebe7ec90');
  me_id = create_measurable_element('The facility ensures adequate and timely availability of ambulances services',
                                    'E2.6', std_id, 'f94f3c13-aa26-4504-a5ee-91462d5c279e');
  me_id = create_measurable_element('Clinical records are updated for care provided', 'E2.7', std_id,
                                    '2e032a23-63c8-4894-ac1c-728e1869fa0b');
  checkpoint_id = create_checkpoint('Records at Pharmacy are maintained', '18b661c5-813b-41a2-941a-d2addb199249', me_id,
                                    checklist_id, 'Stock Registers, Indent Registers, Expiry drug register etc.', FALSE,
                                    FALSE, FALSE, TRUE);
  me_id = create_measurable_element(
      'The facility ensures that standardised forms and formats are used for all purposes including registers', 'E2.8',
      std_id, '9925543e-dbcb-4cb1-b6ff-48a12ee84e5f');
  me_id = create_measurable_element('The facility ensures safe and adequate storage and retrieval  of medical records',
                                    'E2.9', std_id, '02e837cb-5842-46e5-b8bb-0f3782f1f786');
  std_id = create_standard(
      'Facility has defined & implemented procedures for Drug administration and standard treatment guideline as mandated by Govt.',
      'E 3', aoc_id, 'e3942e51-fb52-427a-9732-199988c7e5c7');
  me_id = create_measurable_element('Medication orders are written legibly and adequately', 'E3.1', std_id,
                                    'cc20182b-6012-40ac-a9b2-436aa9598bbd');
  me_id = create_measurable_element('There is a procedure to check drug before administration &dispensing', 'E3.2',
                                    std_id, '76c182c0-3661-480f-9b30-b91120c4c907');
  checkpoint_id = create_checkpoint('Patient is explained about drug dosages by pharmacist at dispensing counter',
                                    '9585a283-8f2e-4db4-9c7c-8c5b8e7ad144', me_id, checklist_id, '', FALSE, TRUE, TRUE,
                                    FALSE);
  me_id = create_measurable_element('Patient is counselled for self drug medication', 'E3.3', std_id,
                                    '3b4377ce-5cc6-4356-a184-cf8b18dfef23');
  checkpoint_id = create_checkpoint('Drugs are purchased in generic name only', '3deaca65-5471-4edf-93f8-8f05139c933b',
                                    me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('The facility ensures that drugs are prescribed in generic name only', 'E3.4',
                                    std_id, 'e10d83dc-6f32-4224-898e-ff6a4a198eaa');
  checkpoint_id = create_checkpoint('Facility has a copy of essential drug list as per state norms',
                                    '46c9b67b-303e-4f77-b2eb-4b13c2dd2e64', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('There is procedure of rational use of drugs', 'E3.5', std_id,
                                    '3e80cb8e-832b-4821-b631-b8de08c1bdf4');
  me_id = create_measurable_element('Drugs are prescribed according to Standard Treatment Guidelines', 'E3.6', std_id,
                                    '3db88253-2daa-4893-abf3-57ea977edecb');
  std_id = create_standard('Facility has defined & establish procedure for Diagnostic Services', 'E4', aoc_id,
                           'd2699d27-ea18-4b48-86c3-abc462b9ea91');
  me_id = create_measurable_element('There are established  procedures for Pre-testing Activities', 'E4.1', std_id,
                                    '0cb526e2-0db1-4a6f-8cc4-f0adf0ad348d');
  me_id = create_measurable_element('There are established  procedures for testing Activities', 'E4.2', std_id,
                                    'beb7327c-bcfc-46d1-95e9-acbbcfa66c88');
  me_id = create_measurable_element('There are established  procedures for Post-testing Activities', 'E4.3', std_id,
                                    '7c0c8099-77a6-46e8-87d4-520cf9903b37');
  me_id = create_measurable_element(
      'There are established procedures for laboratory diagnosis of Tuberculosis as per prevalent guidelines', 'E4.4',
      std_id, '3773908e-b3f2-4d5d-83b7-520ae7ac96e7');
  me_id = create_measurable_element(
      'There are established procedures for laboratory diagnosis of Malaria as per prevalent guidelines', 'E4.5',
      std_id, 'cba95d26-bd84-44eb-84da-5fcf94186d18');
  std_id = create_standard('The facility has establish procedure for Maternal health care as per guideline', 'E5',
                           aoc_id, '57b0554c-75f0-4e87-b110-87b8137b351c');
  me_id = create_measurable_element(
      'There is an established procedure for Registration and follow up of pregnant women.', 'E5.1', std_id,
      '81108459-ee23-4d4a-8983-db67602e7833');
  me_id = create_measurable_element(
      'There is an established procedure for History taking, Physical examination, and counselling of each antenatal woman, visiting the facility.',
      'E5.2', std_id, 'fade11cf-3220-481c-875e-9ee1486b76e2');
  me_id = create_measurable_element('The facility ensures of drugs & diagnostics are prescribed as per protocol',
                                    'E5.3', std_id, '610833f3-a403-44c5-a6e8-e5ebdb6b38f9');
  me_id = create_measurable_element(
      'There is an established procedure for identification of High risk pregnancy and appropriate & Timely referral.',
      'E5.4', std_id, 'be2b9d79-acb8-4f14-9ff3-9547aecd78e0');
  me_id = create_measurable_element('There is an established procedure for identification and management of anaemia',
                                    'E5.5', std_id, 'a9c52cab-b6c9-4ce9-9572-28533218b2a1');
  me_id = create_measurable_element(
      'Counselling of pregnant women is done as per standard protocol and gestational age', 'E5.6', std_id,
      '2f59897e-f806-4796-8e68-9f8df02c6410');
  me_id = create_measurable_element(
      'There is a established procedures for Postnatal visits & counselling of Mother and Child', 'E5.7', std_id,
      '6957c656-3f04-4cc4-82a1-fb133b176b8a');
  std_id = create_standard('Facility has established procedure for care of  New born & Child as per guideline', 'E6',
                           aoc_id, 'bdd4ae79-ae99-47e5-9b43-95ac95dfa4a9');
  me_id = create_measurable_element('Post natal visit & counselling for New born care is provided as per guideline',
                                    'E 6.1', std_id, 'd9ea2eff-481e-4135-b035-61f2d4f150fc');
  me_id = create_measurable_element(
      'Triage, Assessment & Management of new-borns having emergency signs are done as per guidelines', 'E 6.2', std_id,
      'f894dff4-6cd6-421a-86d7-fb7911b2ed26');
  me_id = create_measurable_element(
      'Management of children presenting with fever, cough/ breathlessness is done as per guidelines', 'E 6.3', std_id,
      'fd2244da-3957-4619-b540-65b3cad09840');
  me_id = create_measurable_element('Management of children with severe Acute Malnutrition is done as per  guidelines',
                                    'E 6.4', std_id, 'ad12b598-a616-4ffa-bdaf-82e563d0631d');
  me_id = create_measurable_element('Management of children presenting diarrhoea is done per  guidelines', 'E 6.5',
                                    std_id, 'b2b0cd64-b15d-4938-9a40-eb177789aaf7');
  me_id = create_measurable_element(
      'Screening & Referral of children as per guuidelines of Rastriya Bal Swasth Karkarm', 'E 6.6', std_id,
      '517ca2fa-5dc3-46fa-a3ea-87684b1e64db');
  std_id = create_standard('Facility has establish procedure for Family Planning as per Govt guideline', 'E7', aoc_id,
                           '0ee14e75-cf4c-4512-bcf4-a0558f340102');
  me_id = create_measurable_element('Family planning counselling services provided as per guidelines', 'E7.1', std_id,
                                    'ffa9c2f3-8e4e-4ab3-be70-34d1170afeae');
  me_id = create_measurable_element('Facility provides spacing method of family planning as per guideline', 'E7.2',
                                    std_id, '4ab03a46-d739-4396-a2f0-78d5ff2a05a6');
  me_id = create_measurable_element('The facility provides IUCD service for family planning as per guidelines', 'E7.3',
                                    std_id, '21fda251-1aa0-4b73-8f1e-c7972625d67e');
  me_id = create_measurable_element(
      'Facility provide counselling services for Medial Termination of Pregnancy as per guideline', 'E7.4', std_id,
      '42362e5e-10f5-45db-8e41-ac03781e25af');
  me_id = create_measurable_element('Facility provide abortion services for 1st trimester as per guideline', 'E7.5',
                                    std_id, 'ad8e2d14-4290-41e3-90bf-bc92b0c463dc');
  std_id = create_standard('Facility provides Adolescent reproductive &sexual health services as per guideline', 'E8',
                           aoc_id, '9d1ecc46-0ff1-40f2-acf8-8dc0a6321650');
  me_id = create_measurable_element('Facility provides Promotive ARSH Services', 'E8.1', std_id,
                                    '8afc9241-2d5e-498e-991c-f592df099a77');
  me_id = create_measurable_element('Facility provides Preventive ARSH Services', 'E8.2', std_id,
                                    'f5294e53-206e-4be0-a14a-fc803c03f26a');
  me_id = create_measurable_element('Facility Provides Curative ARSH Services', 'E8.3', std_id,
                                    'ea726e87-4a5a-46bd-a92c-536a045eadfd');
  me_id = create_measurable_element('Facility Provides Referral Services for ARSH', 'E8.4', std_id,
                                    '6baed0ce-aaf5-4705-b855-0f297a2607c0');
  std_id = create_standard(
      'Facility provides National Health Programmes as per operational/clinical guidelines of the Government', 'E9',
      aoc_id, '7b041a5a-7ca3-4992-ac44-aa25f6a4c8fe');
  me_id = create_measurable_element(
      'Facility provides service under National Vector Borne Disease Control Program as per guidelines', 'E9.1', std_id,
      '692f39b9-1803-45aa-8dd6-0535c2b9d914');
  me_id = create_measurable_element(
      'Facility provides services under Revised National TB Control Program as per guidelines', 'E9.2', std_id,
      '7c6b800d-0ae6-49d8-8172-6561d2f10d99');
  me_id = create_measurable_element(
      'Facility provides service under National Leprosy Eradication Program as per guidelines', 'E9.3', std_id,
      '96c4c3b7-9fd3-4a21-a860-e5caeb7cb467');
  me_id = create_measurable_element('Facility provides service under National AIDS Control program as per guidelines',
                                    'E9.4', std_id, 'c98a4095-54f5-4d82-9b93-b87517fbf6b0');
  me_id = create_measurable_element(
      'The facility provides services under National Programme for control of Blindness as per guidelines', 'E9.5',
      std_id, 'e71b88ff-d0aa-42e6-84ed-4b85c701266b');
  me_id = create_measurable_element('Facility provides service under Mental Health Program  as per guidelines', 'E9.6',
                                    std_id, 'ce248a73-0cc4-4d4a-8358-25f73653acd5');
  me_id = create_measurable_element(
      'Facility provides service under National programme for the health care of the elderly as per guidelines', 'E9.7',
      std_id, '8b1a1bec-1eed-4c77-9265-1e250a5c836b');
  me_id = create_measurable_element(
      'Facility provides service under National Programme for Prevention and Control of cancer, diabetes, cardiovascular diseases & stroke (NPCDCS)  as per guidelines',
      'E9.8', std_id, 'bf443ca3-289e-4889-ad61-fdde849b1bca');
  me_id = create_measurable_element('Facility provide service for Integrated disease surveillance program', 'E9.9',
                                    std_id, 'e0ef027e-a6bc-4df2-a9d6-216b2080fb32');
  me_id = create_measurable_element(
      'Facility provide services under National  program for prevention and control of  deafness', 'E9.10', std_id,
      '1237b6e7-c366-4bc3-98a3-08ae699dcba4');
  me_id = create_measurable_element(
      'The facility provides services under Universal Immunization Programme as per guidelines', 'E9.11', std_id,
      'd8364a21-cb79-4cb5-8817-f4dbcb4eee77');
  me_id = create_measurable_element(
      'The facility provides services under National Iodine deficiency Programme as per guidelines', 'E9.12', std_id,
      'dc10b2f6-ccae-4ba9-8025-503c877c6ceb');
  me_id = create_measurable_element(
      'The facility provides services under National Tobacco Control Programme as per guidelines', 'E9.13', std_id,
      'b0bcc5e9-20db-4ab5-a7d0-078c6823ca0b');
  me_id = create_measurable_element('Facility Provide services under National Oral Health Program as per guideline',
                                    'E9.14', std_id, 'e410dab4-01e8-4fd4-9611-d108e31c49a0');
  aoc_id = create_area_of_concern('Infection Control', 'F', 'cc6fc378-08f2-4c45-8da9-b574e47348f0');
  std_id = create_standard(
      'Facility has defined & implemented procedure for ensuring Hand hygiene practices & asepesis', 'F1', aoc_id,
      '8952ed22-1085-4f85-9e67-2bc5f2e5b883');
  me_id = create_measurable_element('Hand washing facilities are provided at point of use', 'F1.1', std_id,
                                    '08c7bacf-fac9-43af-9aa4-71d48dcbe29d');
  me_id = create_measurable_element('Staff is trained and adhere to standard hand washing practices', 'F1.2', std_id,
                                    '484629a4-2c75-4057-aca6-8a5d3695b433');
  me_id = create_measurable_element('Facility ensures standard practices for maintaining  asepsis', 'F1.3', std_id,
                                    'b60d5d89-8106-4dde-8ea7-19dbc2239b6a');
  std_id = create_standard(
      'Facility ensures availability of Personal Protective equipment &  follows standard precautions.', 'F2', aoc_id,
      '695aae9e-e9e3-4683-9637-419f9250befc');
  me_id = create_measurable_element('Facility ensures adequate personal protection equipment  as per requirements',
                                    'F2.1', std_id, '3ae06253-b7c6-4d87-9951-066937885642');
  me_id = create_measurable_element('Staff  adheres to standard personal protection practices', 'F2.2', std_id,
                                    '3c4b3c54-eb2e-4b59-81f6-e99d69389c9f');
  std_id = create_standard('Facility has standard procedure for disinfection &sterilization of equipment & instrument',
                           'F3', aoc_id, 'ee501557-1d48-47d9-a04a-eb28d7e851a9');
  me_id = create_measurable_element(
      'The facility ensures standard practices and materials for decontamination and cleaning of instruments and  procedures areas',
      'F3.1', std_id, 'daccb86b-f740-4171-b0ee-533c62899018');
  me_id = create_measurable_element(
      'The facility ensures standard practices and materials for disinfection and sterilization of instruments and equipment',
      'F3.2', std_id, 'a93227e5-270d-4c86-bae1-3c3fda96839a');
  std_id = create_standard(
      'Facility has defined & establish procedure for segregation, collection, treatment & disposal of Bio medical &hazardous waste',
      'F4', aoc_id, '297632fc-b3e4-4198-aab9-4c455bad01d0');
  me_id = create_measurable_element('The facility ensures segregation of Bio Medical Waste as per guidelines', 'F4.1',
                                    std_id, '2faec64d-07f2-4cc5-aea3-5a78ac6a2985');
  me_id = create_measurable_element('The facility ensures management of sharps as per guidelines', 'F4.2', std_id,
                                    'b4d809bf-104f-4087-8a98-cf0b97e834a4');
  checkpoint_id = create_checkpoint('Expired Drugs and discarded vaccines are disposed as per guidelines',
                                    '66f6bff0-6433-438a-b0e0-1f907149a74a', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility ensures transportation and disposal of waste as per guidelines',
                                    'F4.3', std_id, '29857982-d6f9-4dc9-9b47-9eec104b05ae');
  aoc_id = create_area_of_concern('Quality Management', 'G', '4a26829e-929e-4cfd-888c-616dc73ddc03');
  std_id = create_standard('Facility has established quality Assurane Program as per state/National guidelines', 'G.1',
                           aoc_id, '9046e2a0-99d2-4b3c-8a07-5c440770b97a');
  me_id = create_measurable_element('The facility has a quality team in place', 'G1.1', std_id,
                                    'c11430d2-64b1-4da5-ac93-ad9c6fc6fd3f');
  me_id = create_measurable_element('The facility has defined quality policy and it has been disseminated', 'G1.2',
                                    std_id, 'a2bacca6-decb-42b4-80cd-a4ca9c23a6d9');
  me_id = create_measurable_element(
      'Quality objectives have been defined, and the objectives are reviewed and monitored', 'G1.3', std_id,
      '8e9a071c-7467-4cec-9d1e-c3d51fe08335');
  me_id = create_measurable_element('The facility reviews quality of its services at periodic intervals', 'G1.4',
                                    std_id, '41e46255-aa19-41e0-b6f7-61d25a3d78c4');
  checkpoint_id = create_checkpoint('Physical verifcation of the inventory by Pharmacist at periodic interval',
                                    'd3278674-1216-480c-8d0f-eb53d6062f60', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('The facility has established internal quality assurance programme', 'G1.5', std_id,
                                    'e032d049-f94e-4f19-a13c-c9def7670628');
  checkpoint_id = create_checkpoint('Periodic and random sampling of drugs for monitoring and quality control',
                                    '8a4d5d7a-8515-401f-b028-87ddbfb57ad5', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('The facility has established external assurance programmes', 'G1.6', std_id,
                                    '6b1c76db-a3b7-4062-8702-9bcecce6f33e');
  checkpoint_id = create_checkpoint('Pharmacy I/C  coordinate prescription audit',
                                    'f1a8cbb0-1dc6-4509-901f-7d47667d973e', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('The facility conducts the periodic prescription/ medical audits', 'G1.7', std_id,
                                    'f40f858d-a609-4926-be50-c1893aae372e');
  me_id = create_measurable_element('The facility ensures that non compliances are enumerated and recorded adequately',
                                    'G1.8', std_id, '9d962e20-716a-4be7-8ed5-875905aab485');
  me_id = create_measurable_element('Action plan is made on gaps found in the assessment/audit process', 'G1.9', std_id,
                                    '828808b0-1f40-4ca9-8370-c4a5e219c438');
  me_id = create_measurable_element(
      'Corrective and Preventive actions are taken to address the issues  observed in the assessment and audit',
      'G1.10', std_id, 'da90eda1-035b-42b6-b862-71224b692d1e');
  std_id = create_standard('Facility has established system for Patients and employees satisfaction', 'G.2', aoc_id,
                           '3f7da5b9-b43f-4284-8178-aecf31f05837');
  me_id = create_measurable_element('Patient Satisfaction surveys are conducted at periodic intervals', 'G2.1', std_id,
                                    '1415ab02-11a6-4190-bf76-9ed841c41eac');
  me_id = create_measurable_element('Employee satisfection Surveys are conducted at periodic intervals', 'G2.2', std_id,
                                    '13321fd8-4b77-4836-baea-98726e771062');
  me_id = create_measurable_element('Facility prepares the action plans for the areas of low satisfaction', 'G2.3',
                                    std_id, 'af8f5d36-1e26-436d-8172-8c09642369ab');
  checkpoint_id = create_checkpoint('Updated SOPs for Pharmacy and cold chain management is available at point of use',
                                    'db489be1-1ef6-4701-befe-43c1df643472', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  std_id = create_standard(
      'Facility has established ,documented &implemented standard operating procedure  system for its all key processes .',
      'G3', aoc_id, '24f566d1-f447-48d2-8499-e794864d9f48');
  me_id = create_measurable_element(
      'Standard Operating procedures are prepared , distributed and implemented for all key processes', 'G3.1', std_id,
      '95fdbeae-8685-4770-9d76-2ab2d79fd9b1');
  checkpoint_id = create_checkpoint('SOPs adequately covers all relevant process of department',
                                    '715fe01d-3029-41ce-9c2c-3008fb4244bb', me_id, checklist_id, '', FALSE, TRUE, FALSE,
                                    TRUE);
  me_id = create_measurable_element('Staff is trained as per  SOPs', 'G3.2', std_id,
                                    '8a8a2ff6-a37d-48cc-84ac-fc3c09d597a7');
  checkpoint_id = create_checkpoint('Work instructions for Storage of drugs available',
                                    '0985f072-b4a4-4ba1-ada3-b5e72208f740', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('Work instructions are displayed at Point of work', 'G3.3', std_id,
                                    'f8c8c5b6-9b71-4ae0-8461-de20a3289a18');
  checkpoint_id = create_checkpoint('Work sinstruction for Operating ILR and Deep Freezers',
                                    '3efa277d-a887-4a9f-a782-083f1dec6d1c', me_id, checklist_id, '', TRUE, FALSE, FALSE,
                                    FALSE);
  me_id = create_measurable_element('The facility uses methods and tools for Quality Improvement', 'G3.4', std_id,
                                    '721da1d7-e394-449c-911c-4be875a51191');
  checkpoint_id = create_checkpoint('Percentage of drugs available against EDL', '4ab41138-c38c-41ac-ae86-02e18d069018',
                                    me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  aoc_id = create_area_of_concern('Outcomes', 'H', 'c94108c5-1c66-4d6d-aade-1200d1251ff8');
  std_id = create_standard(
      'The facility measures its productivity, efficiency, clinical care & service Quality indicators', 'H1', aoc_id,
      'fbbce689-b2f9-4b95-b510-1c14d1c08aa2');
  me_id = create_measurable_element('Facility measures Productivity Indicators on monthly basis', 'H1.1', std_id,
                                    'db4b97fc-966f-47dc-a5b7-5ad51851e490');
  checkpoint_id = create_checkpoint('No. of stock out drugs', '4a8fbc00-d6eb-44f8-8306-fa77820eb493', me_id,
                                    checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('Facility measures efficiency Indicators on monthly basis', 'H1.2', std_id,
                                    'a581eda4-55a4-444e-ae6d-471d255e56b0');
  checkpoint_id = create_checkpoint('Percentage of drugs expired during month', '831ca97c-de4a-46fc-a4fb-ddd9a7af7d18',
                                    me_id, checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  checkpoint_id = create_checkpoint('Antibiotic prescription rate', '6edc53be-d707-4b5f-a47b-9bda3d8533a8', me_id,
                                    checklist_id, '', FALSE, FALSE, FALSE, TRUE);
  me_id = create_measurable_element('Facility measures Clinical Care & Safety Indicators on monthly basis', 'H1.3',
                                    std_id, '4a218d57-9c6b-4088-ac15-ea89a53750fa');
  me_id = create_measurable_element('Facility measures Service Quality Indicators on  monthly basis', 'H1.4', std_id,
                                    'a88b43de-6416-414e-b370-f6ccf1d84db1');
  std_id = create_standard('Facility endeavours to improve its performance to meet bench marks', 'H2', aoc_id,
                           '42810985-045d-436d-b70b-35aa7ab8e10d');
  me_id = create_measurable_element('The facility meets benchmarks set by the state /District for Key Indicators',
                                    'H2.1', std_id, 'f686dfa9-46cf-461b-89e7-91e40fc3b8f3');
  checkpoint_id = create_checkpoint('Trends analysis of Indicators is done at Periodic Intervals',
                                    '97eb65b8-a0e5-4f0c-9eac-a0ed598d06d3', me_id, checklist_id, '', FALSE, FALSE,
                                    FALSE, TRUE);
  me_id = create_measurable_element('The facility strives to improve indicators from its current performance', 'H2.2',
                                    std_id, '23db2156-773f-4ef8-be4a-37ff8a5ec72f');
  RETURN TRUE;
END;
$$ LANGUAGE plpgsql;