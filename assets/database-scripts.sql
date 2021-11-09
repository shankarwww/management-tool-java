drop table if exists environments;
drop table if exists subscribers;
drop table if exists user_roles;
drop table if exists users;
drop table if exists role_master;
drop table if exists environment_type;

create table role_master(
	role_id varchar(20) primary key,
	role_name varchar(30) not null
);

create table users(
	user_id varchar(50) primary key,
	password varchar(300) not null,
	full_name varchar(100) not null,
	created_on timestamp(6) not null,
	updated_on timestamp(6)
);

create table user_roles(
	user_id varchar(50) not null,
	role_id varchar(20) not null,
	created_on timestamp(6) not null,
	CONSTRAINT pk_user_roles PRIMARY KEY(user_id, role_id),
	CONSTRAINT fk_user_roles_1 FOREIGN KEY(user_id) REFERENCES users(user_id),
	CONSTRAINT fk_user_roles_2 FOREIGN KEY(role_id) REFERENCES role_master(role_id)
);

create table environment_type(
	type varchar(20) primary key,
	description varchar(100)
);

create table subscribers(
	user_id varchar(100) not null,
	subscriber_id varchar(150) primary key,
	type varchar(10) not null,
	description varchar(250),
	created_on timestamp(6) not null,
	updated_by varchar(150),
	updated_on timestamp(6),
	active boolean not null,
	CONSTRAINT fk_subscribers_1 FOREIGN KEY(user_id) REFERENCES users(user_id),
	CONSTRAINT fk_subscribers_2 FOREIGN KEY(type) REFERENCES environment_type(type)
);

create table environments(
	subscriber_id varchar(150) not null,
	environment_id varchar(150) not null,
	environment_seq numeric(3) not null,
	url varchar(50) not null,
	created_by varchar(50) not null,
	created_on timestamp(6) not null,
	updated_by varchar(150),
	updated_on timestamp(6),
	public_key varchar(150) not null,
	active boolean not null,
	auth boolean not null,
	CONSTRAINT pk_environments PRIMARY KEY(subscriber_id, environment_id, environment_seq),
	CONSTRAINT fk_environments_1 FOREIGN KEY(subscriber_id) REFERENCES subscribers(subscriber_id)
);


--inserts starts
insert into environment_type(type, description) values ('SIT', 'SIT environment');
insert into environment_type(type, description) values ('UAT', 'UAT environment');
insert into environment_type(type, description) values ('PROD', 'Production environment');

insert into role_master(role_id, role_name) values('SUPER_ADMIN', 'role super admin');
insert into role_master(role_id, role_name) values ('ADMIN', 'role admin');
insert into role_master(role_id, role_name) values ('USER', 'role user');

insert into users(user_id, password, full_name, created_on, updated_on) values ('superadmin@gmail.com', '$2a$10$ZqBu/csXHmzJctV8yU92LOM7EG2Xjtg370XMnbaqmgoFtOHyiW0mC', 'super admin', now(), null);
insert into users(user_id, password, full_name, created_on, updated_on) values ('admin@gmail.com', '$2a$10$ZqBu/csXHmzJctV8yU92LOM7EG2Xjtg370XMnbaqmgoFtOHyiW0mC', 'admim', now(), null);
insert into user_roles(user_id, role_id, created_on) values ('superadmin@gmail.com', 'SUPER_ADMIN', now());
insert into user_roles(user_id, role_id, created_on) values ('admin@gmail.com', 'ADMIN', now());


commit;