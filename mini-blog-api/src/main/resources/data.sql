DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  password CHAR(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX (username)
);

DROP TABLE IF EXISTS CATEGORIES;
CREATE TABLE CATEGORIES (
  id INT NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);


INSERT INTO USERS (id, first_name, last_name, username, password) VALUES (1, 'Chiwa','Kantawong', 'chiwa','$2a$10$D02XjIOKVZOFV5kL6aezXubWwkseex9uQZAiYLUFcUCZ3CPfiGS32');
INSERT INTO USERS (id, first_name, last_name, username, password)  VALUES (2, 'Jirapa', 'Kantawong', 'jirapa', '$2a$10$D02XjIOKVZOFV5kL6aezXubWwkseex9uQZAiYLUFcUCZ3CPfiGS32');
INSERT INTO USERS (id, first_name, last_name, username, password)  VALUES (3, 'Sura', 'Jiranan', 'sura', '$2a$10$D02XjIOKVZOFV5kL6aezXubWwkseex9uQZAiYLUFcUCZ3CPfiGS32');


INSERT INTO CATEGORIES (id, category_name) VALUES (1, 'Travel');
INSERT INTO CATEGORIES (id, category_name) VALUES (2, 'Programming');
INSERT INTO CATEGORIES (id, category_name) VALUES (3, 'Foods');

