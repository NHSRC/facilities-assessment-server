update measurable_element set ref_num = (ref_num + 0.8) where (ref_num % 1) > 0.1 and (ref_num % 1) < 0.2;