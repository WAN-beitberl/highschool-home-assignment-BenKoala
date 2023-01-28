--DDL Commands Used in Project.
--By: Ben David Ivgi.
--Date: 28.01.2023.

--Creating The highschool table.
create table highschool (
	id int ,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	email varchar(255) ,
	gender varchar(255) ,
	ip_address varchar(255) ,
	cm_height int not null,
	age int not null,
	has_car bool not null,
	car_color varchar(255) not null,
	grade int not null,
	grade_avg float ,
	identification_card int unique not null,
	primary key (id)
);


--Creating The highschool_friendships table.
create table highschool_friendships (
	id int ,
	friend_id int ,
	other_friend_id int ,
	primary key (id),
	foreign key (friend_id) references highschool(id),
	foreign key (other_friend_id) references highschool(id)
);

--Creating The idAndAvg view.
create view idAndAvg as select id, grade_avg from highschool;
