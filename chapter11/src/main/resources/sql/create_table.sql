create table t_role
(
    id        int(12)     not null auto_increment,
    role_name varchar(60) not null,
    note      varchar(256),
    primary key (id)
);

create table t_user
(
    id        int(12)      not null auto_increment,
    user_name varchar(60)  not null,
    pwd       varchar(100) not null,
    available int(1) default 1 check (available in (0, 1)),
    note      varchar(256),
    primary key (id),
    unique (user_name)
);

create table t_user_role
(
    id      int(12) not null auto_increment,
    user_id int(12) not null,
    role_id int(12) not null,
    primary key (id),
    unique (user_id, role_id)
);

alter table t_user_role
    add constraint FK_Reference_1 foreign key (role_id) references t_role (id) on delete restrict on update restrict;
alter table t_user_role
    add constraint FK_Reference_2 foreign key (user_id) references t_user (id) on delete restrict on update restrict;