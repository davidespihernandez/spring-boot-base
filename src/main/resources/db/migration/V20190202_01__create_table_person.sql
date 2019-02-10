create table person
(
  id         bigserial    not null,
  first_name varchar(255) not null,
  last_name  varchar(255) not null,
  phone      varchar(255),
  primary key (id)
)
