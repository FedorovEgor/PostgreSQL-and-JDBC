DROP TABLE IF EXISTS public.students CASCADE;
CREATE TABLE public.students
(  
	student_id SERIAL PRIMARY KEY,
	group_id INTEGER REFERENCES public.groups (group_id) ON UPDATE CASCADE ON DELETE CASCADE,
	first_name VARCHAR(25) NOT NULL,
	last_name VARCHAR(25) NOT NULL
);
DROP TABLE IF EXISTS public.groups CASCADE;
CREATE TABLE public.groups
(
	group_id SERIAL PRIMARY KEY,
	group_name VARCHAR(10) NOT NULL
);
DROP TABLE IF EXISTS public.courses CASCADE;
CREATE TABLE public.courses
(
	course_id SERIAL PRIMARY KEY,
	course_name VARCHAR(100) NOT NULL,
	course_description TEXT
);
DROP TABLE IF EXISTS public.students_courses CASCADE;
CREATE TABLE public.students_courses
(
	id SERIAL PRIMARY KEY,
	student_id INTEGER REFERENCES public.students (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
	course_id  INTEGER REFERENCES public.courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE
);
