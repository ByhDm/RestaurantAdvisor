create table links_users_roles(
    id int primary key auto_increment,
    id_user int not null references users (id),
    id_roles int not null references roles (id)
)