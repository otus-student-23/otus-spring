insert into genre(id, name) values('2142a973-6e72-4bf1-88af-366709d6844d', 'genre_a');
insert into genre(id, name) values('837f0945-0879-4385-b751-a76833811802', 'genre_b');
insert into genre(id, name) values('80e566b1-b489-418b-8821-aa43431d48b7', 'genre_c');
insert into genre(id, name) values('7c069ba3-73fb-4da7-9f98-e827138a9493', 'genre_d');
insert into genre(id, name) values('7b0b26d0-23f5-49cb-8d55-0f61ca25a806', 'genre_e');

insert into author(id, name) values('fa33229d-58aa-4b35-8b9f-d9b680e5b2f3', 'author_a');
insert into author(id, name) values('b7fcb7b1-e51a-4ffd-b231-9a66811f66fd', 'author_b');
insert into author(id, name) values('28919653-01f1-403e-b436-f2e0e659ad30', 'author_c');
insert into author(id, name) values('64c0939b-5dd2-4703-bc10-55416bb51a15', 'author_d');
insert into author(id, name) values('7e29cccc-bca9-46d3-8dc5-65ca543997eb', 'author_e');

insert into book(id, name, author_id, genre_id) values (
    'f8c9bdb7-2709-4721-9426-57e1b8f4d629',
    'book_a',
    (select a.id from author a where a.name = 'author_a'),
    (select g.id from genre g where g.name = 'genre_a'));
insert into book(id, name, author_id, genre_id) values (
    '342cf472-d40e-45d1-a290-a1879f4c1da1',
    'book_b',
    (select a.id from author a where a.name = 'author_b'),
    (select g.id from genre g where g.name = 'genre_b'));
insert into book(id, name, author_id, genre_id) values (
    '9d6bdc35-2477-4264-8b33-22b85284d44c',
    'book_c',
    (select a.id from author a where a.name = 'author_b'),
    (select g.id from genre g where g.name = 'genre_c'));
insert into book(id, name, author_id, genre_id) values (
    '584f3cbd-8bd5-4f94-8039-5b5ca597d7d8',
    'book_d',
    (select a.id from author a where a.name = 'author_c'),
    (select g.id from genre g where g.name = 'genre_b'));
insert into book(id, name, author_id, genre_id) values (
    '9dfd67dd-0e79-4e29-9265-ef4bdb7cd5d3',
    'book_a',
    (select a.id from author a where a.name = 'author_c'),
    (select g.id from genre g where g.name = 'genre_c'));

insert into book_comment(id, book_id, comment) values (
    'd603be04-3157-4746-900f-11cab7aba5c1',
    'f8c9bdb7-2709-4721-9426-57e1b8f4d629',
    'comment_1');
insert into book_comment(id, book_id, comment) values (
    '665495d9-c0e2-436d-a219-5bfc130deff6',
    'f8c9bdb7-2709-4721-9426-57e1b8f4d629',
    'comment_2');
insert into book_comment(id, book_id, comment) values (
    '96be3d60-3673-4a41-a48a-2933356da0f4',
    '342cf472-d40e-45d1-a290-a1879f4c1da1',
    'comment_3');
