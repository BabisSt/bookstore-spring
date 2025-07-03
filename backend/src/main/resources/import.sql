-- Insert Authors First
INSERT INTO author (id, first_name, last_name, about_section) VALUES (1, 'George', 'Orwell', 'British writer');
INSERT INTO author (id, first_name, last_name, about_section) VALUES (2, 'Aldous', 'Huxley', 'English writer and philosopher');
INSERT INTO author (id, first_name, last_name, about_section) VALUES (3, 'Ray', 'Bradbury', 'American author known for Fahrenheit 451');

-- Insert Books with FK references to author.id
INSERT INTO books (isdn, title, content, author_id, release_date) VALUES ('9783161484100', '1984', 'Dystopian novel', 1, '1949-06-08');
INSERT INTO books (isdn, title, content, author_id, release_date) VALUES ('0306406152', 'Brave New World', 'Sci-fi novel', 2, '1932-01-01');
INSERT INTO books (isdn, title, content, author_id, release_date) VALUES ('9780131103627', 'Fahrenheit 451', 'Dystopian novel', 3, '1953-10-19');

-- Insert Book Genre and make the connections
INSERT INTO book_genres(book_isdn, book_genre) VALUES ('9783161484100', 'Action');
INSERT INTO book_genres(book_isdn, book_genre) VALUES ('9783161484100', 'Adventure');
INSERT INTO book_genres(book_isdn, book_genre) VALUES ('0306406152', 'Adventure');
INSERT INTO book_genres(book_isdn, book_genre) VALUES ('9780131103627', 'Mystery');

-- Insert Users -- store password as plain text for now
INSERT INTO users (first_name, last_name, email, password,about_section) VALUES ('John', 'Doe', 'johnDoe@gmail.com', '123','British writer');
INSERT INTO users (first_name, last_name, email, password,about_section) VALUES ('Maria', 'Smit', 'mariaS@yahoo.com','123','English writer and philosopher');
INSERT INTO users (first_name, last_name, email, password,about_section) VALUES ('Luke', 'Arrow', 'look@windowslive.com', '123','American author known for Fahrenheit 451');

-- Corrected: No explicit 'order_id', since it's auto-generated as 'id'
INSERT INTO orders (user_id) VALUES (1);
INSERT INTO orders (user_id) VALUES (2);
INSERT INTO orders (user_id) VALUES (3);

-- For `order_books`, use the actual order IDs from above
-- Assume generated IDs are 1, 2, 3 for orders

INSERT INTO order_books (order_id, book_isdn, amount) VALUES (1, '9783161484100', 1); 
INSERT INTO order_books (order_id, book_isdn, amount) VALUES (1, '0306406152', 2);    
INSERT INTO order_books (order_id, book_isdn, amount) VALUES (2, '0306406152', 5);    
INSERT INTO order_books (order_id, book_isdn, amount) VALUES (3, '9780131103627', 8); 

