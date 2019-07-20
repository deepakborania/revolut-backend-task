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
	currency_code varchar(10) not null,

	created_on timestamp DEFAULT CURRENT_TIMESTAMP() NOT NULL,
  updated_on timestamp NULL --,
-- 	constraint ACCOUNT_PERSON_ID_FK foreign key (person_id) references person(id)
);

create table currency
(
	id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
	code VARCHAR (100) unique not null,
	name VARCHAR(100) not null,
	factor DECIMAL DEFAULT 0 not null,
  symbol VARCHAR(3) NOT NULL,
	created_on timestamp DEFAULT CURRENT_TIMESTAMP() NOT NULL,
  updated_on timestamp NULL
);

CREATE table transactions
(
  id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  from_account INT default 0,
  to_account int default 0,
  currency varchar(3) not null,
  amount DECIMAL not null,
  converted_amount DECIMAL not null,
  created_on timestamp DEFAULT CURRENT_TIMESTAMP() NOT NULL
);

INSERT INTO currency (name, code, factor, symbol)
VALUES
  ('Pounds', 'GBP', 1, '£'),
  ('US Dollars', 'USD', 0.81, 'US$'),
  ('Indian Rupees', 'INR', 0.012, '₹');

INSERT INTO account (id, name, balance, active, currency_code)
VALUES
  (10001, 'Savings Account', 0, true, 'GBP'),
  (10002, 'Savings Account', 100, true, 'GBP'),
  (10003, 'INR Savings Account', 0, true, 'INR'),
  (10004, 'INR Current Account', 100, true, 'INR');
