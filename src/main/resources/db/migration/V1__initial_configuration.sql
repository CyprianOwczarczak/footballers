drop table if exists footballers;

create table footballers (
	id serial,
	pesel varchar(255) not null,
	name varchar(255) not null,
	club varchar(255),
	goals int,
	height int not null,
	saves int,
	primary key (id),
	unique (pesel)
);
