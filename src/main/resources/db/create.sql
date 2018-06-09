drop table if exists gambler;
create table gambler (id serial primary key, name varchar(255) not null, login varchar(255) not null, password varchar(255) not null, photo bytea);

drop table if exists team;
create table team (id serial primary key, name varchar(255) not null);

drop table if exists game;
create table game (id serial primary key, team_1 int not null references team(id), team_2 int not null references team(id), date date);

drop table if exists pronostic;
create table pronostic (id serial primary key, match_id int references game(id), res_team_1 int not null, res_team_2 int not null);

drop table if exists score;
create table score (id serial primary key, match_id int references game(id), res_team_1 int not null, res_team_2 int not null);

drop table if exists result;
create table result (id serial primary key, gambler int not null references gambler(id), pronostic_id int not null references pronostic(id), point int not null);
