drop table if exists gambler cascade;
drop table if exists team cascade;
drop table if exists crew cascade;
drop table if exists crewjoin cascade;
drop table if exists game cascade;
drop table if exists pronostic cascade;
drop table if exists score cascade;
drop table if exists result cascade;

create table gambler (id serial primary key, name varchar(255) not null, login varchar(255) not null unique, password varchar(255) not null, photo bytea, gain int);

create table team (id serial primary key, name varchar(255) not null);

create table crew (id serial primary key, admin int not null references gambler(id), name varchar(255), uid varchar(32) not null);

create table crewjoin (id serial primary key, gambler_id int not null references gambler(id), crew_id int not null references crew(id));

create table game (id serial primary key, team_1 int not null references team(id), team_2 int not null references team(id), date timestamp with time zone, processed int);

create table pronostic (id serial primary key, prono1 int not null, prono2 int not null, gain int, game_id int not null references game(id), gambler_id int not null references gambler(id));

create table score (id serial primary key, game_id int not null references game(id), team_id int not null references team(id), res int not null);

create table result (id serial primary key, gambler_id int not null references gambler(id), pronostic_id int not null references pronostic(id), point int not null);

INSERT INTO team (id, name) VALUES(808,'Russia');
INSERT INTO team (id, name) VALUES(801,'Saudi Arabia');
INSERT INTO team (id, name) VALUES(825,'Egypt');
INSERT INTO team (id, name) VALUES(758,'Uruguay');
INSERT INTO team (id, name) VALUES(815,'Morocco');
INSERT INTO team (id, name) VALUES(840,'Iran');
INSERT INTO team (id, name) VALUES(765,'Portugal');
INSERT INTO team (id, name) VALUES(760,'Spain');
INSERT INTO team (id, name) VALUES(773,'France');
INSERT INTO team (id, name) VALUES(779,'Australia');
INSERT INTO team (id, name) VALUES(762,'Argentina');
INSERT INTO team (id, name) VALUES(1066,'Iceland');
INSERT INTO team (id, name) VALUES(832,'Peru');
INSERT INTO team (id, name) VALUES(782,'Denmark');
INSERT INTO team (id, name) VALUES(799,'Croatia');
INSERT INTO team (id, name) VALUES(776,'Nigeria');
INSERT INTO team (id, name) VALUES(793,'Costa Rica');
INSERT INTO team (id, name) VALUES(780,'Serbia');
INSERT INTO team (id, name) VALUES(759,'Germany');
INSERT INTO team (id, name) VALUES(769,'Mexico');
INSERT INTO team (id, name) VALUES(764,'Brazil');
INSERT INTO team (id, name) VALUES(788,'Switzerland');
INSERT INTO team (id, name) VALUES(792,'Sweden');
INSERT INTO team (id, name) VALUES(772,'Korea Republic');
INSERT INTO team (id, name) VALUES(805,'Belgium');
INSERT INTO team (id, name) VALUES(1836,'Panama');
INSERT INTO team (id, name) VALUES(802,'Tunisia');
INSERT INTO team (id, name) VALUES(770,'England');
INSERT INTO team (id, name) VALUES(818,'Colombia');
INSERT INTO team (id, name) VALUES(766,'Japan');
INSERT INTO team (id, name) VALUES(794,'Poland');
INSERT INTO team (id, name) VALUES(804,'Senegal');

# Group matches
INSERT INTO game (id, team_1, team_2, date) VALUES(165069, 808, 801, '2018-06-14T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165084, 825, 758, '2018-06-15T12:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165083, 815, 840, '2018-06-15T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165076, 765, 760, '2018-06-15T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165072, 773, 779, '2018-06-16T10:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165073, 762, 1066, '2018-06-16T13:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165071, 832, 782, '2018-06-16T16:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165074, 799, 776, '2018-06-16T19:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165075, 793, 780, '2018-06-17T12:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165082, 759, 769, '2018-06-17T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165070, 764, 788, '2018-06-17T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165081, 792, 772, '2018-06-18T12:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165077, 805, 1836, '2018-06-18T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165078, 802, 770, '2018-06-18T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165080, 818, 766, '2018-06-19T12:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165079, 794, 804, '2018-06-19T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165100, 808, 825, '2018-06-19T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165087, 765, 815, '2018-06-20T12:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165086, 758, 801, '2018-06-20T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165085, 840, 760, '2018-06-20T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165099, 782, 779, '2018-06-21T12:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165096, 773, 832, '2018-06-21T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165094, 762, 799, '2018-06-21T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165092, 764, 793, '2018-06-22T12:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165098, 776, 1066, '2018-06-22T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165091, 780, 788, '2018-06-22T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165088, 805, 802, '2018-06-23T12:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165089, 772, 769, '2018-06-23T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165090, 759, 792, '2018-06-23T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165093, 770, 1836, '2018-06-24T12:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165095, 766, 804, '2018-06-24T15:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165097, 794, 818, '2018-06-24T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165111, 801, 825, '2018-06-25T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165101, 758, 808, '2018-06-25T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165112, 840, 765, '2018-06-25T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165109, 760, 815, '2018-06-25T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165113, 782, 773, '2018-06-26T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165107, 779, 832, '2018-06-26T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165115, 776, 762, '2018-06-26T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165114, 1066, 799, '2018-06-26T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165102, 769, 792, '2018-06-27T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165106, 772, 759, '2018-06-27T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165116, 780, 764, '2018-06-27T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165108, 788, 793, '2018-06-27T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165104, 766, 794, '2018-06-28T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165103, 804, 818, '2018-06-28T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165110, 1836, 802, '2018-06-28T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165105, 770, 805, '2018-06-28T18:00:00Z');

# Matchs 8e
INSERT INTO game (id, team_1, team_2, date) VALUES(165119, 773, 762, '2018-06-30T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165123, 758, 765, '2018-06-30T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165122, 760, 808, '2018-07-01T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165121, 799, 782, '2018-07-01T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165118, 764, 769, '2018-07-02T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165120, 805, 766, '2018-07-02T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165117, 792, 788, '2018-07-03T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165124, 818, 766, '2018-07-03T18:00:00Z');

INSERT INTO game (id, team_1, team_2, date) VALUES(165127, '', '', '2018-07-06T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165126, '', '', '2018-07-06T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165125, '', '', '2018-07-07T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165128, '', '', '2018-07-07T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165130, '', '', '2018-07-10T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165129, '', '', '2018-07-11T18:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165131, '', '', '2018-07-14T14:00:00Z');
INSERT INTO game (id, team_1, team_2, date) VALUES(165132, '', '', '2018-07-15T15:00:00Z');
