update checkpoint set means_of_verification = 'NOT SPECIFIED' where means_of_verification is null or means_of_verification = '';
alter table checkpoint alter means_of_verification set not null;