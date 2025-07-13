ALTER TABLE author ALTER COLUMN id RESTART WITH 1;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

-- Insert Authors First
INSERT INTO author (id,first_name, last_name, about_section) VALUES (1,'George', 'Orwell', 'British writer');
INSERT INTO author (id,first_name, last_name, about_section) VALUES (2,'Aldous', 'Huxley', 'English writer and philosopher');
INSERT INTO author (id,first_name, last_name, about_section) VALUES (3,'Ray', 'Bradbury', 'American author known for Fahrenheit 451');

-- Insert Books with FK references to author.id
INSERT INTO books (isdn, title, content, author_id, release_date) VALUES ('9783161484100', '1984', 'Dystopian novel', 1, '1949-06-08');
INSERT INTO books (isdn, title, content, author_id, release_date) VALUES ('0306406152', 'Brave New World', 'Sci-fi novel', 2, '1932-01-01');
INSERT INTO books (isdn, title, content, author_id, release_date) VALUES ('9780131103627', 'Fahrenheit 451', 'Dystopian novel', 3, '1953-10-19');

-- Insert Book Genre and make the connections
INSERT INTO book_genres(book_isdn, book_genre) VALUES ('9783161484100', 'Action');
INSERT INTO book_genres(book_isdn, book_genre) VALUES ('9783161484100', 'Adventure');
INSERT INTO book_genres(book_isdn, book_genre) VALUES ('0306406152', 'Adventure');
INSERT INTO book_genres(book_isdn, book_genre) VALUES ('9780131103627', 'Mystery');

-- Insert Users 
INSERT INTO users (id,first_name, last_name, email, password,about_section,role) VALUES (1,'John', 'Doe', 'johnDoe@gmail.com', '$2a$12$c9jGoq8jyQ5Pta1wAM1.1.G/0oLELfTXBjmtOFbbAJYxiEhDp4D4G','British writer','ADMIN');
INSERT INTO users (id,first_name, last_name, email, password,about_section,role) VALUES (2,'Maria', 'Smit', 'mariaS@yahoo.com','$2a$12$c9jGoq8jyQ5Pta1wAM1.1.G/0oLELfTXBjmtOFbbAJYxiEhDp4D4G','English writer and philosopher','USER');
INSERT INTO users (id,first_name, last_name, email, password,about_section,role) VALUES (3,'Luke', 'Arrow', 'look@windowslive.com', '$2a$12$c9jGoq8jyQ5Pta1wAM1.1.G/0oLELfTXBjmtOFbbAJYxiEhDp4D4G','American author known for Fahrenheit 451','USER');

-- Insert Book Reviews and make the connections
INSERT INTO book_reviews (book_isdn, user_id, stars) VALUES ('9783161484100', 1, 1);
INSERT INTO book_reviews (book_isdn, user_id, stars) VALUES ('9783161484100', 2, 3);
INSERT INTO book_reviews (book_isdn, user_id, stars) VALUES ('0306406152', 1, 4);
INSERT INTO book_reviews (book_isdn, user_id, stars) VALUES ('9780131103627', 3, 2);

-- Insert orders
INSERT INTO orders (user_id) VALUES (1); -- assume ID = 1
INSERT INTO orders (user_id) VALUES (2); -- assume ID = 2
INSERT INTO orders (user_id) VALUES (3); -- assume ID = 3

-- Insert into order_books using those order IDs
INSERT INTO order_books (order_id, book_isdn, amount) VALUES (1, '9783161484100', 1); 
INSERT INTO order_books (order_id, book_isdn, amount) VALUES (2, '0306406152', 2);    
INSERT INTO order_books (order_id, book_isdn, amount) VALUES (2, '0306406152', 5);    
INSERT INTO order_books (order_id, book_isdn, amount) VALUES (3, '9780131103627', 8); 


