-- Schema projetsgbd --
DROP DATABASE IF EXISTS projetsgbd;
CREATE SCHEMA IF NOT EXISTS projetsgbd DEFAULT CHARACTER SET utf8 ;
USE projetsgbd;

DROP TABLE IF EXISTS roles,
					 workers,
                     teams,
                     authorities,
                     roles_authorities;


-- Tables definitions --
USE projetsgbd;

CREATE TABLE roles (
  id INT NOT NULL AUTO_INCREMENT,
  descr VARCHAR(2000) NULL,
  label VARCHAR(255) NOT NULL,
  CONSTRAINT pk_roles PRIMARY KEY (id),
  CONSTRAINT roles_check_label_unique UNIQUE (label)
);

CREATE TABLE teams (
  id INT NOT NULL AUTO_INCREMENT,
  label VARCHAR(255) NOT NULL,
  CONSTRAINT pk_teams PRIMARY KEY (id),
  CONSTRAINT teams_check_label_unique UNIQUE (label)
);

CREATE TABLE workers (
  id INT NOT NULL AUTO_INCREMENT,
  roles_id INT NOT NULL,
  teams_id INT NOT NULL,
  birthdate DATE NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  is_deleted BOOLEAN NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  login VARCHAR(255) NOT NULL,
  password_key VARCHAR(255) NOT NULL,
  sexe ENUM('mâle','femelle','autre') NOT NULL,
  CONSTRAINT pk_workers PRIMARY KEY (id),
  CONSTRAINT fk_workers_roles_id FOREIGN KEY (roles_id) REFERENCES roles (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_workers_teams_id FOREIGN KEY (teams_id) REFERENCES teams (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT workers_check_login_unique UNIQUE (login)  
);

CREATE TABLE authorities (
  id INT NOT NULL AUTO_INCREMENT,
  descr VARCHAR(2000) NULL,
  label VARCHAR(255) NOT NULL,
  CONSTRAINT pk_authorities PRIMARY KEY (id),
  CONSTRAINT authorities_check_label_unique UNIQUE (label)
);

CREATE TABLE roles_authorities (
  id INT NOT NULL AUTO_INCREMENT,
  roles_id INT NOT NULL,
  authorities_id INT NOT NULL,
  CONSTRAINT pk_roles_authorities PRIMARY KEY (id),
  CONSTRAINT fk_roles_authorities_roles FOREIGN KEY (roles_id) REFERENCES roles (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_roles_authorities_authorities FOREIGN KEY (authorities_id) REFERENCES authorities (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT roles_authorities_check_roles_authorities_unique UNIQUE (roles_id, authorities_id)
);

CREATE TABLE weekly_schedules (
  id INT NOT NULL AUTO_INCREMENT,
  worker_id INT NOT NULL,
  start_date DATE NULL,
  end_date DATE NULL,
  day_week INT(1) NULL,
  start_time TIME NULL,
  end_time TIME NULL,
  is_training BOOLEAN NOT NULL,
  CONSTRAINT pk_weekly_schedules PRIMARY KEY (id),
  CONSTRAINT fk_weekly_schedules_worker_id FOREIGN KEY (worker_id) REFERENCES workers (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE clockings (
  id INT NOT NULL AUTO_INCREMENT,
  worker_id INT NOT NULL,
  start_datetime DATETIME NULL,
  end_datetime DATETIME NULL,
  CONSTRAINT pk_clockings PRIMARY KEY (id),
  CONSTRAINT fk_clockings_worker_id FOREIGN KEY (worker_id) REFERENCES workers (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE weekly_plans (
  id INT NOT NULL AUTO_INCREMENT,
  worker_id INT NOT NULL,
  hours_per_week DOUBLE NULL,
  start_date DATE NULL,
  end_date DATE NULL,
  CONSTRAINT pk_weekly_plans PRIMARY KEY (id),
  CONSTRAINT fk_weekly_plans_worker_id FOREIGN KEY (worker_id) REFERENCES workers (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Populate tables --
INSERT INTO `roles` (`id`,`label`,`descr`)  VALUES (1,"travailleur","un travailleur lambda"); 
INSERT INTO `roles` (`id`,`label`,`descr`)  VALUES (2,"second d'équipe","le second du chef d'équipe");
INSERT INTO `roles` (`id`,`label`,`descr`)  VALUES (3,"chef d'équipe","le chef d'une équipe ou d'un rayon");
INSERT INTO `roles` (`id`,`label`,`descr`)  VALUES (4,"secrétaire","la secrétaire du patron"); 

INSERT INTO `teams` (`id`,`label`)  VALUES (1,"employés");
INSERT INTO `teams` (`id`,`label`)  VALUES (2,"service technique");

INSERT INTO `workers`
  (`id`,`roles_id`,`teams_id`,`first_name`,`last_name`,`login`,`password_key`,`birthdate`,`is_deleted`,`sexe`)
  VALUES (1,3,1,"Jessica","SuperDactylo","jessica.sd","pass","2000-01-01",FALSE,"femelle");

INSERT INTO `authorities`
  (`id`,`label`,`descr`)
  VALUES (1,"consulter les utilisateurs","permet de consulter tous les utilisateurs de l'application et leurs détails");

INSERT INTO `roles_authorities` (`roles_id`,`authorities_id`) VALUES (4,1);









