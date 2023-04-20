drop table if exists club_representation_footballer;
drop table if exists contract;
drop table if exists score;
drop table if exists match;
drop table if exists club_representation;
drop table if exists club;

alter table footballer
drop column if exists club,
drop column if exists goals;

create table club(
	id serial,
	name varchar(255) not null,
	created date,
	primary key (id),
	unique (name)
);

create table contract(
	id serial,
	club_id int,
	footballer_id int,
	contract_start date,
	contract_end date,
	salary int,
    primary key (id),
	constraint fk_club foreign key(club_id) references club(id),
	constraint fk_footballer foreign key(footballer_id) references footballer(id)
);

create table club_representation(
	id serial ,
	club_id int not null,
    primary key (id),
	constraint fk_club foreign key(club_id) references club(id)
);

create table match(
	id serial,
	host_representation_id int,
	guest_representation_id int,
	name_of_referee varchar(255),
	date timestamp,
    primary key (id),
	constraint fk_host foreign key(host_representation_id) references club_representation(id),
	constraint fk_guest foreign key(guest_representation_id) references club_representation(id),
	constraint team_cannot_play_with_itself_check
    check (host_representation_id <> guest_representation_id)
);

create table club_representation_footballer(
	club_representation_id int not null,
	footballer_id int not null,
	constraint fk_representation foreign key(club_representation_id) references club_representation(id),
	constraint fk_footballer foreign key(footballer_id) references footballer(id),
	unique(club_representation_id, footballer_id)
);

create table score(
    id serial,
	match_id int not null,
	footballer_id int not null,
	minute_scored int,
	constraint fk_match foreign key(match_id) references match(id),
	constraint fk_footballer foreign key(footballer_id) references footballer(id)
);
