create table if not exists users
(
    id                int primary key auto_increment,
    name              varchar(20)  not null,
    surname           varchar(20)  not null,
    lastname          varchar(20)  not null,
    email             varchar(256) not null,
    registration_date timestamp default CURRENT_TIMESTAMP,
    password          varchar(256) not null
)