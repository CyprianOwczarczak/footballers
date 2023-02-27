drop table if exists club_representation_footballer cascade;
drop table if exists contract cascade;
drop table if exists footballer cascade;
drop table if exists match cascade;
drop table if exists club_representation cascade;
drop table if exists club cascade;
drop table if exists score cascade;

---------- CREATING ALL THE TABLES ---------
create table footballer (
	id serial,
	pesel varchar(255) not null,
	name varchar(255) not null,
	goals int,
	height int not null,
	primary key (id),
	unique (pesel)
);

create table club(
	id serial,
	name varchar(255) not null,
	created timestamp,
	primary key (id),
	unique (name)
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
---------- THE END OF TABLES CREATION ----------

-- Footballer initial data
INSERT INTO footballer VALUES (default, '111111', 'Lewandowski', 10, 150);
INSERT INTO footballer VALUES (default, '222222', 'Neymar', 20, 160);
INSERT INTO footballer VALUES (default, '333333', 'Ikar', 20, 160);
INSERT INTO footballer VALUES (default, '444444', 'Messi', 20, 170);
INSERT INTO footballer VALUES (default, '555555', 'Ronaldo', 30, 170);
INSERT INTO footballer VALUES (default, '666666', 'Zlatan', 20, 180);
INSERT INTO footballer VALUES (default, '777777', 'Iniesta', 30, 180);
INSERT INTO footballer VALUES (default, '888888', 'Peszko', 30, 190);
INSERT INTO footballer VALUES (default, '999999', 'Chairman', 30, 200);
INSERT INTO footballer VALUES (default, '000000', 'Benchguy', 30, 200);

-- Club initial data
INSERT INTO club VALUES (default, 'Barcelona',  '2013-03-21 09:10:59.897666');
INSERT INTO club VALUES (default, 'Lech',  '2016-08-21 09:10:59.897666');
INSERT INTO club VALUES (default, 'Real',  '2021-03-21 09:10:59.897666');
INSERT INTO club VALUES (default, 'Manchester',  '2022-03-21 09:10:59.897666');

-- Contract initial data
INSERT INTO contract VALUES (default, 1, 1, '2016-08-21', '2026-03-21', 10000);
INSERT INTO contract VALUES (default, 1, 2, '2016-08-21', '2023-03-21', 20000);
INSERT INTO contract VALUES (default, 1, 3, '2017-08-21', '2026-03-21', 30000);
INSERT INTO contract VALUES (default, 1, 4, '2017-08-21', '2023-03-21', 45000);
INSERT INTO contract VALUES (default, 2, 5, '2017-08-21', '2022-03-21', 50000);
INSERT INTO contract VALUES (default, 2, 6, '2018-08-21', '2026-03-21', 60000);
INSERT INTO contract VALUES (default, 4, 7, '2018-08-21', '2026-03-21', 60000);
INSERT INTO contract VALUES (default, 4, 9, '2020-08-21', '2024-03-21', 30000);
INSERT INTO contract VALUES (default, 4, 10, '2020-08-21', '2024-03-21', 100000);

INSERT INTO contract VALUES (default, 1, 10, '2010-08-21', '2012-03-21', 20000);
INSERT INTO contract VALUES (default, 2, 10, '2012-08-21', '2014-03-21', 30000);
INSERT INTO contract VALUES (default, 3, 10, '2014-08-21', '2018-03-21', 40000);



-- Club_representation intial data
INSERT INTO club_representation VALUES (default, 1);
INSERT INTO club_representation VALUES (default, 2);
INSERT INTO club_representation VALUES (default, 4);
INSERT INTO club_representation VALUES (default, 1);
INSERT INTO club_representation VALUES (default, 2);
INSERT INTO club_representation VALUES (default, 4);

INSERT INTO club_representation VALUES (default, 1);
INSERT INTO club_representation VALUES (default, 1);
INSERT INTO club_representation VALUES (default, 1);
INSERT INTO club_representation VALUES (default, 1);
INSERT INTO club_representation VALUES (default, 1);


INSERT INTO club_representation VALUES (default, 2);
INSERT INTO club_representation VALUES (default, 2);
INSERT INTO club_representation VALUES (default, 2);


-- Club_representation_footballer join table initial data
-- Clubs 1,2,3 interwoven
INSERT INTO club_representation_footballer VALUES (1, 1);
INSERT INTO club_representation_footballer VALUES (1, 2);
INSERT INTO club_representation_footballer VALUES (1, 3);
INSERT INTO club_representation_footballer VALUES (2, 5);
INSERT INTO club_representation_footballer VALUES (2, 6);
INSERT INTO club_representation_footballer VALUES (3, 7);
INSERT INTO club_representation_footballer VALUES (3, 9);
INSERT INTO club_representation_footballer VALUES (3, 10);
INSERT INTO club_representation_footballer VALUES (4, 1);
INSERT INTO club_representation_footballer VALUES (4, 2);
INSERT INTO club_representation_footballer VALUES (5, 5);
INSERT INTO club_representation_footballer VALUES (5, 6);
INSERT INTO club_representation_footballer VALUES (6, 7);
INSERT INTO club_representation_footballer VALUES (6, 8);

-- Club number 1
INSERT INTO club_representation_footballer VALUES (7, 1);
INSERT INTO club_representation_footballer VALUES (7, 2);
INSERT INTO club_representation_footballer VALUES (8, 1);
INSERT INTO club_representation_footballer VALUES (9, 1);
INSERT INTO club_representation_footballer VALUES (10, 1);
INSERT INTO club_representation_footballer VALUES (11, 1);
INSERT INTO club_representation_footballer VALUES (11, 2);
INSERT INTO club_representation_footballer VALUES (11, 3);

-- Club number 2
INSERT INTO club_representation_footballer VALUES (12, 5);
INSERT INTO club_representation_footballer VALUES (12, 6);
INSERT INTO club_representation_footballer VALUES (13, 5);
INSERT INTO club_representation_footballer VALUES (14, 5);

-- Match initial data
INSERT INTO match VALUES (default, 1, 2, 'Example Referee', '2022-03-21 09:10:59.897666');
INSERT INTO match VALUES (default, 3, 4, 'Example Referee2', '2020-03-21 14:10:59.897666');
INSERT INTO match VALUES (default, 7, 5, 'Example Referee3', '2021-03-21 10:10:59.897666');
INSERT INTO match VALUES (default, 8, 6, 'Example Referee3', '2021-03-21 10:10:59.897666');
INSERT INTO match VALUES (default, 9, 12, 'Example Referee3', '2021-03-21 10:10:59.897666');
INSERT INTO match VALUES (default, 10, 13, 'Example Referee3', '2021-03-21 10:10:59.897666');
INSERT INTO match VALUES (default, 11, 14, 'Example Referee3', '2021-03-21 10:10:59.897666');


-- Score initial data
--INSERT INTO score VALUES (default, 1, 1, 20);
--INSERT INTO score VALUES (default, 1, 2, 30);
--INSERT INTO score VALUES (default, 1, 6, 40);
--
--INSERT INTO score VALUES default, 2, 7, 60);
--INSERT INTO score VALUES (default, 2, 9, 65);
--INSERT INTO score VALUES (default, 2, 2, 70);
--INSERT INTO score VALUES (default, 2, 2, 80);
--
--INSERT INTO score VALUES (default, 2, 1, 25);