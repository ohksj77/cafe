drop table if exists member;
drop table if exists product;

create table member
(
    deleted      bit          not null comment 'Soft-delete indicator',
    created_at   datetime(6),
    updated_at   datetime(6),
    deleted_at   datetime(6),
    id           BINARY(16) not null,
    password     varchar(255) not null,
    phone_number varchar(255) not null,
    role         enum ('ROLE_ADMIN','ROLE_USER') not null,
    primary key (id)
) DEFAULT CHARACTER SET UTF8;

create table product
(
    deleted         bit not null comment 'Soft-delete indicator',
    cost            integer,
    expiration_date date,
    price           integer,
    created_at      datetime(6),
    updated_at      datetime(6),
    deleted_at      datetime(6),
    id              BINARY(16) not null,
    owner_id        BINARY(16) not null,
    barcode         varchar(255),
    category        varchar(255),
    description     varchar(255),
    name            varchar(255),
    product_size    enum ('SMALL','LARGE'),
    chosung         varchar(255) not null,
    primary key (id)
) DEFAULT CHARACTER SET UTF8;

alter table member
    add constraint UK_n2qryhkfoqeel6njfhrcq6k7u unique (phone_number);

create index IDXrmeql25277vk6we3pnf5qqlb2
    on product (owner_id);

CREATE INDEX idx_product_chosung ON product (chosung);

CREATE FULLTEXT INDEX idx_product_name ON product (name);
