-- create table person
-- (
-- 	id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
-- 	name VARCHAR(200) not null,
-- 	created_on timestamp DEFAULT CURRENT_TIMESTAMP() NOT NULL,
--   updated_on timestamp NULL
-- )
-- ;

create table account
(
	id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	name VARCHAR(100) not null,
-- 	person_id INTEGER not null,
	balance DECIMAL DEFAULT 0 not null,
	active BOOLEAN not null,
	currency varchar(10) not null,

	created_on timestamp DEFAULT CURRENT_TIMESTAMP() NOT NULL,
  updated_on timestamp NULL --,
-- 	constraint ACCOUNT_PERSON_ID_FK foreign key (person_id) references person(id)
);

create table currency
(
	id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	name VARCHAR(100) not null,
	factor DECIMAL DEFAULT 0 not null,

	created_on timestamp DEFAULT CURRENT_TIMESTAMP() NOT NULL,
  updated_on timestamp NULL
);

