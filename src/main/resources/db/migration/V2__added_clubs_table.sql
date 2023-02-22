drop table if exists clubs;

create table clubs(
	id serial ,
	club_name varchar(255) not null,
	date_of_creation timestamp
);
