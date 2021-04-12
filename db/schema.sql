create table items(
    id serial primary key,
    description text,
    created timestamp not null ,
    status text not null
);

create table users(
    id serial primary key,
    name text not null ,
    email text unique not null ,
    password text not null
);

create table categories(
    id serial primary key,
    name text not null
);