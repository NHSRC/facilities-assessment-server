delete
from assessment_custom_info
where value_string is null
  and assessment_metadata_id in
      (select id from assessment_metadata where name = 'Assessor name');