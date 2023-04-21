create table footballer (
	id bigserial,
	pesel varchar(255) not null,
	name varchar(255) not null,
	club varchar(255) not null,
	goals int,
	height int not null,
	primary key (id),
	unique (pesel)
);
