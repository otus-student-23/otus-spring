insert into book(id, name, author_id, genre_id) values (
    '7f123ddc-3149-49e9-8ad1-b96837161e6c',
    'Book_A',
    (select a.id from author a where a.name = 'Author_A'),
    (select g.id from genre g where g.name = 'Genre_A'));

insert into book(id, name, author_id, genre_id) values (
    '162a7ecf-aa7e-4305-87ac-73f98504d0b8',
    'Book_B',
    (select a.id from author a where a.name = 'Author_A'),
    (select g.id from genre g where g.name = 'Genre_B'));

insert into book(id, name, author_id, genre_id) values (
    'cb449daf-b782-4bfd-99bf-4346411e549c',
    'Book_C',
    (select a.id from author a where a.name = 'Author_B'),
    (select g.id from genre g where g.name = 'Genre_B'));
