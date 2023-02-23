create table club(
	id serial,
	name varchar(255) not null,
    primary key (id),
	created timestamp
);

create table contract(
	id serial,
	club_id int,
	footballer_id int,
	contract_start timestamp,
	contract_end timestamp,
	salary int,
    primary key (id),
	constraint fk_club foreign key(club_id) references club(id),
	constraint fk_footballer foreign key(footballer_id) references footballer(id)
);

create table club_representation(
	id serial ,
	club_id int not null,
	footballers_list varchar(255),
    primary key (id),
	constraint fk_club foreign key(club_id) references club(id)
);

create table match(
	id serial,
	host_team_id int,
	guest_team_id int,
	score varchar(255),
	name_of_referee varchar(255),
	date timestamp,
    primary key (id),
	constraint fk_host foreign key(host_team_id) references club_representation(id),
	constraint fk_guest foreign key(guest_team_id) references club_representation(id)
);

create table representation_list(
	id serial,
	match_id int not null,
	club_id int not null,
	representation_id int not null,
    primary key (id),
	constraint fk_match foreign key(match_id) references match(id),
	constraint fk_club foreign key(club_id) references club(id),
	constraint fk_representation foreign key(representation_id) references representation_list(id)
);
