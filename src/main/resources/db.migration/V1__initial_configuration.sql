drop table if exists footballers;

create table footballers (
	id serial,
	pesel varchar(255),
	name varchar(255),
	club varchar(255),
	goals int,
	height int,
	saves int,
	primary key (id),
	unique (pesel)
);