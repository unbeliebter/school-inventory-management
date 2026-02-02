INSERT INTO organizational_unit (id, name)
VALUES ('1', 'IT');
INSERT INTO organizational_group(id, name)
VALUES ('1', 'FIAE');
INSERT INTO position(id, school, room, description)
VALUES ('1', 'TG BBZ 1 Saarbrücken', 'A404', 'Im Schrank');
INSERT INTO users(id, username, firstname, lastname, e_mail, role_id, password)
VALUES ('1', 'admin', 'Admin', 'Account', 'tgbbz1@test.de', 'admin', 'test');
INSERT INTO subject(id, name, abbreviation)
VALUES ('2', 'Lernfeld 1', 'LF1');
INSERT INTO equipment(id, inventory_number, equipment_name, state, organizational_unit_id, organizational_group_id,
                      subject_id, position_id, responsible_user_id)
VALUES ('1', '1', 'Notebook', 'IN_USE', '1', '1', '2', '1', '1');



--Password- is Password
insert into users(id, username, firstname, lastname, e_mail, role_id, password)
values ('2', 'admin2', 'Admin2', 'Account', 'tgbbz1@test.de', 'admin', '{bcrypt}$2a$12$KWI9dgiX6keXKrVXSyfghed5gj/OErtl/c1QdOv8Pg0cpSHnmWeaK');

insert into users(id, username, firstname, lastname, e_mail, role_id, password)
values ('3', 'responsible', 'Responsible', 'Account', 'tgbbz1@test.de', 'responsible', '{bcrypt}$2a$12$KWI9dgiX6keXKrVXSyfghed5gj/OErtl/c1QdOv8Pg0cpSHnmWeaK');

insert into users(id, username, firstname, lastname, e_mail, role_id, password)
values ('4', 'teacher', 'Teacher', 'Account', 'tgbbz1@test.de', 'teacher', '{bcrypt}$2a$12$KWI9dgiX6keXKrVXSyfghed5gj/OErtl/c1QdOv8Pg0cpSHnmWeaK');