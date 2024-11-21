USE techjobs;
SELECT database();
DESCRIBE job;
-- Part1: Columns and their data types
-- id: int
-- employer: varchar(255)
-- name: varchar(255)
-- skills: varchar(255)
-- Part2: List the names of employers in St. Louis City
SELECT name FROM employer WHERE location = "St. Louis City";
-- Part 3: Remove job table
DROP TABLE job;
-- Part 4: Return the names of all skills that are attached to jobs in alphabetical order
SELECT * FROM skill
JOIN job_skills
ON skill.id = job_skills.skills_id
WHERE job_skills.jobs_id IS NOT NULL
ORDER BY name ASC;
