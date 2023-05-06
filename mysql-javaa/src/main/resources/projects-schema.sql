/* Create DROP TABLES in the reverse order (dependent tables first
then primary*/
DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS project;

/* Creating tables primary first, dependent second*/

/* primary key = project_id */
CREATE TABLE project (
project_id INT AUTO_INCREMENT NOT NULL,
project_name VARCHAR(128) NOT NULL,
estimated_hours DECIMAL(7,2),
actual_hours DECIMAL(7,2),
difficulty INT,
notes TEXT,
PRIMARY KEY (project_id)
);

/* Primary key = material_id */
/* foreign key = project_id include References & on delete cascade (ODC)*/
CREATE TABLE material (
material_id INT AUTO_INCREMENT NOT NULL,
project_id INT NOT NULL,
material_name VARCHAR(128) NOT NULL,
num_required INT,
cost DECIMAL(7,2),
PRIMARY KEY (material_id),
FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

/* projects referenced here provide foreign key*/
/* primary key = step_id
 * foreign key = project_id include Refs. & ODC */
CREATE TABLE step (
step_id INT AUTO_INCREMENT NOT NULL,
project_id INT NOT NULL,
step_text TEXT NOT NULL,
step_order INT NOT NULL,
PRIMARY KEY (step_id),
FOREIGN KEY (project_id) REFERENCES project (project_id)
ON DELETE CASCADE
);

/* primary key = category_id */
CREATE TABLE category (
category_id INT AUTO_INCREMENT NOT NULL,
category_name VARCHAR(128) NOT NULL,
PRIMARY KEY (category_id)
);

/* join table
 * include:
 * Foreign keys (project & category) w/ ODC 
 * & unique key 
 * */

CREATE TABLE project_category (
project_id INT NOT NULL,
category_id INT NOT NULL,
FOREIGN KEY(project_id) REFERENCES project (project_id) ON DELETE CASCADE,
FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE,
UNIQUE KEY (project_id, category_id)
);