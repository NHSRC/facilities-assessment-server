create table theme
(
    id                 serial                                                 not null
        constraint theme_pkey
            primary key,
    created_date       timestamp default (now())::timestamp without time zone not null,
    last_modified_date timestamp default (now())::timestamp without time zone not null,
    uuid               uuid      default uuid_generate_v4()                   not null
        constraint theme_uuid_key
            unique,
    inactive           boolean   default false                                not null,
    short_name         varchar(300)                                           not null
        constraint theme_short_name_key
            unique,
    name               varchar(1024)                                          not null
        constraint theme_name_key
            unique,
    sort_order         integer   default 0                                    not null
);

create table checkpoint_theme
(
    id                 serial                                                 not null
        constraint checkpoint_theme_pkey
            primary key,
    checkpoint_id      integer                                                not null
        constraint checkpoint_theme_checkpoint_id_fkey
            references checkpoint,
    theme_id           integer                                                not null
        constraint checkpoint_theme_theme_id_fkey
            references theme,
    created_date       timestamp default (now())::timestamp without time zone not null,
    last_modified_date timestamp default (now())::timestamp without time zone not null,
    uuid               uuid      default uuid_generate_v4()                   not null
        constraint checkpoint_theme_uuid_key
            unique,
    inactive           boolean   default false                                not null
);
