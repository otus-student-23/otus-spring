insert into book(name, author_id, genre_id) values('Aaaa Bbb Cc', (select a.id from author a where a.name = 'Aaaa Bbb Cc'), (select g.id from genre g where g.name = 'Aaaa Bbb Cc'));
insert into book(name, author_id, genre_id) values('BbbB Ccc Dd', (select a.id from author a where a.name = 'BbbB Ccc Dd'), (select g.id from genre g where g.name = 'Aaaa Bbb Cc'));
insert into book(name, author_id, genre_id) values('Cccc Ddd Ff', (select a.id from author a where a.name = 'Aaaa Bbb Cc'), (select g.id from genre g where g.name = 'Cccc Ddd Ff'));
