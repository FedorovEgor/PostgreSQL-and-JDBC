DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups
(
	group_id SERIAL PRIMARY KEY,
	group_name VARCHAR(10) NOT NULL
);
DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students
(
	student_id SERIAL PRIMARY KEY,
	group_id INTEGER REFERENCES groups (group_id) ON UPDATE CASCADE ON DELETE CASCADE,
	first_name VARCHAR(25) NOT NULL,
	last_name VARCHAR(25) NOT NULL
);
DROP TABLE IF EXISTS courses CASCADE;
CREATE TABLE courses
(
	course_id SERIAL PRIMARY KEY,
	course_name VARCHAR(100) NOT NULL,
	course_description TEXT
);
DROP TABLE IF EXISTS students_courses CASCADE;
CREATE TABLE students_courses
(
	id SERIAL PRIMARY KEY,
	student_id INTEGER REFERENCES students (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
	course_id  INTEGER REFERENCES courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE
);