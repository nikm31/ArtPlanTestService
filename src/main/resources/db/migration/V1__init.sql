create table users
(
    id         bigserial primary key,
    username   varchar(30) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('nikolay', '$2y$10$p/Ep33HVsvpMS69qNj335.aq4dSx0uiuJWHYS9jqa746n8WXKE1H2', 'firewather@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2);

create table animals
(
    id         bigserial primary key,
    name       varchar(150) not null unique,
    birth_date date         not null,
    sex        enum ('male', 'female'),
    user_id    bigint       not null references users (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into animals (name, birth_date, sex, user_id)
values ('barsik', '2022-07-01', 'male', 1),
       ('kisel', '2022-07-02', 'male', 1);

create table attempts
(
    id      bigserial primary key,
    user_id bigint references users (id),
    count  bigint,
    time_finish timestamp
);