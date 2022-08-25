create table if not exists restaurants
(
    id            bigint auto_increment primary key,
    name          varchar(20) not null,
    phone_number  varchar(15) not null,
    email         varchar(30),
    description   varchar(255),
    creation_date date        not null default '2000-01-01'
);