create table items(
    id serial primary key,
    description text,
    created timestamp not null ,
    status text not null
);