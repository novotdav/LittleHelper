CREATE TABLE files (
  id int not null PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  task_id int not null,
  name varchar(64),
  path varchar(256)
);

CREATE TABLE tasks (
  id int not null PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  hp varchar(16),
  description varchar(256),
  workspace_id int,
  notes clob,
  state varchar(16)
);

CREATE TABLE time_interval (
  id int not null PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  task_id int not null,
  time_from time,
  time_to time,
  duration float,
  date date 
);

CREATE TABLE workspace (
  id int not null PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  name varchar(32),
  path varchar(256),
  type varchar(16)
);

ALTER TABLE files
  ADD CONSTRAINT files_ibfk_1 FOREIGN KEY (task_id) REFERENCES tasks (id);

ALTER TABLE tasks
  ADD CONSTRAINT tasks_ibfk_1 FOREIGN KEY (workspace_id) REFERENCES workspace (id);

ALTER TABLE time_interval
  ADD CONSTRAINT time_interval_ibfk_1 FOREIGN KEY (task_id) REFERENCES tasks (id);
