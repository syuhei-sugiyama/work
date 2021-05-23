create table if not exists teacher (
id serial not null primary key,
user_name varchar(60) not null,
email varchar(254) not null
);