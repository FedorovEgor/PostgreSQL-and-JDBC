
INSERT INTO groups (group_id, group_name) VALUES (1,'ef-41');
INSERT INTO groups (group_id, group_name) VALUES (2,'ed-48');
INSERT INTO groups (group_id, group_name) VALUES (3,'TO-36');
INSERT INTO groups (group_id, group_name) VALUES (4,'er-48');
INSERT INTO groups (group_id, group_name) VALUES (0,'');


INSERT INTO students (group_id, first_name, last_name) VALUES (2, 'Betty', 'Walker');
INSERT INTO students (group_id, first_name, last_name) VALUES (1, 'Paul', 'Howard');
INSERT INTO students (group_id, first_name, last_name) VALUES (4, 'Thomas', 'Reed');
INSERT INTO students (group_id, first_name, last_name) VALUES (2, 'Mary', 'Lee');

INSERT INTO courses (course_name, course_description) VALUES ('Astronomy', 'Astronomy is a natural science that studies celestial objects and phenomena.');
INSERT INTO courses (course_name, course_description) VALUES ('History', 'History is the study of the past.');

INSERT INTO students_courses (student_id, course_id) VALUES (2, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (3, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (4, 2);
INSERT INTO students_courses (student_id, course_id) VALUES (1, 2);