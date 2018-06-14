drop table if exists gambler cascade;
drop table if exists team cascade;
drop table if exists crew cascade;
drop table if exists crewjoin cascade;
drop table if exists game cascade;
drop table if exists pronostic cascade;
drop table if exists score cascade;
drop table if exists result cascade;

create table gambler (id serial primary key, name varchar(255) not null, login varchar(255) not null unique, password varchar(255) not null, photo bytea);

create table team (id serial primary key, name varchar(255) not null);

create table crew (id serial primary key, admin int not null references gambler(id), name varchar(255), uid varchar(32) not null);

create table crewjoin (id serial primary key, gambler_id int not null references gambler(id), crew_id int not null references crew(id));

create table game (id serial primary key, team_1 int not null references team(id), team_2 int not null references team(id), date date);

create table pronostic (id serial primary key, prono1 int not null, prono2 int not null, gain int, game_id int not null references game(id), gambler_id int not null references gambler(id));

create table score (id serial primary key, game_id int not null references game(id), team_id int not null references team(id), res int not null);

create table result (id serial primary key, gambler_id int not null references gambler(id), pronostic_id int not null references pronostic(id), point int not null);
