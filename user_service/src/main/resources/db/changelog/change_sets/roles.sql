create table if not exists roles (
    id int primary key auto_increment,
    role_name varchar(256) not null unique
)