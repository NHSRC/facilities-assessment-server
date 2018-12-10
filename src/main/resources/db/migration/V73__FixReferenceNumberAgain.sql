update measurable_element set ref_num =
  (ascii(substring(reference from 1 for 1)) - 64)*1000 + (split_part(substring(reference from 2), '.', 1)::int * 100) + split_part(reference, '.', 2)::int;