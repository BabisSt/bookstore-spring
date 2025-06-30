-- Insert Authors First
INSERT INTO author (id, first_name, last_name, about_section) VALUES (1, 'George', 'Orwell', 'British writer');
INSERT INTO author (id, first_name, last_name, about_section) VALUES (2, 'Aldous', 'Huxley', 'English writer and philosopher');
INSERT INTO author (id, first_name, last_name, about_section) VALUES (3, 'Ray', 'Bradbury', 'American author known for Fahrenheit 451');

-- Insert Books with FK references to author.id
INSERT INTO books (isdn, title, content, author_id, release_date) VALUES ('9783161484100', '1984', 'Dystopian novel', 1, '1949-06-08');
INSERT INTO books (isdn, title, content, author_id, release_date) VALUES ('0306406152', 'Brave New World', 'Sci-fi novel', 2, '1932-01-01');
INSERT INTO books (isdn, title, content, author_id, release_date) VALUES ('9780131103627', 'Fahrenheit 451', 'Dystopian novel', 3, '1953-10-19');

-- Insert Users -- store password as plain text for now
INSERT INTO users (first_name, last_name, email, password) VALUES ('John', 'Doe', 'johnDoe@gmail.com', '123');
INSERT INTO users (first_name, last_name, email, password) VALUES ('Maria', 'Smit', 'mariaS@yahoo.com','123');
INSERT INTO users (first_name, last_name, email, password) VALUES ('Luke', 'Arrow', 'look@windowslive.com', '123');

-- Insert into orders (no book_isdn column anymore!) -- removed manual ids
INSERT INTO orders (user_id, amount) VALUES (1, 1);
INSERT INTO orders (user_id, amount) VALUES (2, 2);
INSERT INTO orders (user_id, amount) VALUES (3, 3);

-- Now associate books to orders via the join table `order_books`
INSERT INTO order_books (order_id, book_isdn) VALUES (1, '9783161484100'); -- Order 1 -> 1984
INSERT INTO order_books (order_id, book_isdn) VALUES (1, '0306406152');    -- Order 1 -> Brave New World
INSERT INTO order_books (order_id, book_isdn) VALUES (2, '0306406152');    -- Order 2 -> Brave New World
INSERT INTO order_books (order_id, book_isdn) VALUES (3, '9780131103627'); -- Order 3 -> Fahrenheit 451
