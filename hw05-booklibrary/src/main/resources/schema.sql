drop table if exists book;
drop table if exists author;
drop table if exists genre;

create table author(
    id bigint auto_increment primary key,
    name varchar(100) not null,
    constraint idx_author_name unique (name)
);

create table genre(
    id bigint auto_increment primary key,
    name varchar(100) not null,
    constraint idx_genre_name unique (name)
);

create table book(
    id bigint auto_increment primary key,
    name varchar(100) not null,
    author_id bigint,
    foreign key (author_id) references author(id),
    constraint idx_book_name unique (name, author_id),
    genre_id bigint,
    foreign key (genre_id) references genre(id)
);