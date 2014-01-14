
-- Software Systems
insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @id = identity();
insert into people.software_system(id, name) values(@id, 'Role Manager');

-- Organizations
insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @hogwartsId = identity();
insert into people.organization(id, name, name_searchable) values(@hogwartsId, 'Hogwarts School of Witchcraft and Wizardry', 'HOGWARTS SCHOOL OF WITCHCRAFT AND WIZARDRY');
insert into people.institution(id, code) values(@hogwartsId, 'Hogwarts');

insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @id = identity();
insert into people.organization(id, name, name_searchable) values(@id, 'Gryffindor House', 'GRYFFINDOR HOUSE');
insert into people.department(id, dept_code) values(@id, 'G');
insert into people.association ( member, role_code, organization, start_date, active_status_code ) values ( @id, 'SUBNIT', @hogwartsId, current_timestamp(), 'ACTIVE' );

insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @id = identity();
insert into people.organization(id, name, name_searchable) values(@id, 'Slytherin House', 'SLYTHERIN HOUSE');
insert into people.department(id, dept_code) values(@id, 'S');
insert into people.association ( member, role_code, organization, start_date, active_status_code ) values ( @id, 'SUBNIT', @hogwartsId, current_timestamp(), 'ACTIVE' );

insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @id = identity();
insert into people.organization(id, name, name_searchable) values(@id, 'Hufflepuff House', 'HUFFLEPUFF HOUSE');
insert into people.department(id, dept_code) values(@id, 'H');
insert into people.association ( member, role_code, organization, start_date, active_status_code ) values ( @id, 'SUBNIT', @hogwartsId, current_timestamp(), 'ACTIVE' );

insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @id = identity();
insert into people.organization(id, name, name_searchable) values(@id, 'Ravenclaw House', 'RAVENCLAW HOUSE');
insert into people.department(id, dept_code) values(@id, 'R');
insert into people.association ( member, role_code, organization, start_date, active_status_code ) values ( @id, 'SUBNIT', @hogwartsId, current_timestamp(), 'ACTIVE' );

-- People
insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @id = identity();
insert into people.person(id, first_name, last_name, first_name_searchable, last_name_searchable, email, login_id, student_id) values ( @id, 'Harry', 'Potter', 'HARRY', 'POTTER', 'hpotter@hogwarts.edu', 'hpotter', '123456789');
insert into people.association ( member, role_code, organization, start_date, active_status_code ) values ( @id, 'STUDENT', @hogwartsId, current_timestamp(), 'ACTIVE' );

insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @id = identity();
insert into people.person(id, first_name, last_name, first_name_searchable, last_name_searchable, email, login_id) values ( @id, 'Hermione', 'Granger', 'HERMIONE', 'GRANGER', 'hgranger@hogwarts.edu', 'hgranger');
insert into people.association ( member, role_code, organization, start_date, active_status_code ) values ( @id, 'STUDENT', @hogwartsId, current_timestamp(), 'ACTIVE' );

insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @id = identity();
insert into people.person(id, first_name, last_name, first_name_searchable, last_name_searchable, email, login_id) values ( @id, 'Ronald', 'Weasley', 'RONALD', 'WEASLEY', 'rweasley@hogwarts.edu', 'rweasely');
insert into people.association ( member, role_code, organization, start_date, active_status_code ) values ( @id, 'STUDENT', @hogwartsId, current_timestamp(), 'ACTIVE' );

insert into people.party (created_date, created_by, last_modified_date, last_modified_by) values (current_timestamp(), 1, current_timestamp(), 1);
set @id = identity();
insert into people.person(id, first_name, last_name, first_name_searchable, last_name_searchable, email, login_id, employee_id) values ( @id, 'Albus', 'Dumbledore', 'ALBUS', 'DUMBLEDORE', 'adumbledore@hogwarts.edu', 'adumbledore', '987654321');
insert into people.association ( member, role_code, organization, start_date, active_status_code ) values ( @id, 'STUDENT', @hogwartsId, current_timestamp(), 'ACTIVE' );
