drop table if exists clubs;

create table club_representations(
	id serial ,
	match_id int,
	club_id int not null,
	footballer_list varchar(255),
	constraint fk_match foreign key(match) references matches(match_id)
	constraint fk_club foreign key(club) references clubs(club_id)
);
