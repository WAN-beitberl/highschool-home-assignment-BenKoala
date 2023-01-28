Sql&Java Project
=-=-=-=-=-=-=-=-=

By: Ben David Ivgi
Date: 28.1.2023

=-=-=-=-=-=-=-=-=
Data Related Isuues:
=-=-=-=-=-=-=-=-=-=-=
1. Some fields were seperated by the '\n' key, and not by the ',' key. 
#(if not handled -> two values seperated by '\n' would count as one.)

2. Some fields ended with the '\r' key.
#(if not handled -> printing/matching data would be corrupted.)

3. Some fields contained the "'" key, reserved for strings in sql.
#(if not handled -> query would become corrupted.)

4. The "car_color" field in "highschool" table had values when the "has_car" column was false, and vice versa.
#(logically impossible.)

5. Strings were not surrounded by ''.
#(required by sql.)

6. The "friend_id"/"other_friend_id" was sometimes null.
#(logically impossible.)

=-=-=-=-=-=-=-=-=-=-=
Solutions to Said Issues:
=-=-=-=-=-=-=-=-=-=-=-=-=-=
1. '\n' was added to the delimiters, along with the ',' key.
2. '\r' was deleted from the data.
3. "'" was deleted from the data.
4. when "has_car" was false, "car_color" was set to "none",
   when "has_car" was true but no color was inserted, "car_color" was set to "unknown".
5. when a value was being inserted to a varchar field, it was surrounded with "'".
6. when one field was null, the entire row was deleted.
=-=-=-=-=-=-=-=-=-=-=-=-=-=
Reasoning behind Data Constraints:
=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=
Highschool table:
1.id -> primary key, single form of identification.
2.first_name not null -> student must have a name.
3.last_name not null -> student must have a name.
4.email -> not mandatory.
5.gender -> not mandatory.
6.ip_address -> not mandatory.
7.cm_height not null -> everyone must have a height.
8.age not null -> everyone must have an age.
9.has_car not null -> everyone either has a car, or does'nt have a car.
10.car_color not null -> handled through the code, so color is mandatory (none, unknown are options).
11.grade not null -> if in highschool, students must have a grade.
12.grade_avg -> possible that student is yet to have an avg.
13.identification_card unique not null -> as a form of identification, must be unique and not null.

Highschool Friendships table:
1.id -> primary key, single form of identification.
2.friend_id -> foreign key, connected to a student id in Highschool table.
3.other_friend_id -> foreign key, connected to a student id in Highschool table.
=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Reasoning behind Table Relations:
=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
the highschool friendships table is connected to the highschool main table, as the table represents the connection between two students.
=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
