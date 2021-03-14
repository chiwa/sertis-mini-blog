DROP TABLE IF EXISTS USERS CASCADE;
CREATE TABLE USERS(
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  password CHAR(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX (username)
);

DROP TABLE IF EXISTS CATEGORIES CASCADE;
CREATE TABLE CATEGORIES(
  id INT NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS CARDS CASCADE;
CREATE TABLE CARDS(
  id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  category_id INT NOT NULL,
  topic VARCHAR(255) NOT NULL,
  content VARCHAR(512) NOT NULL,
  last_modified DATE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES USERS(id),
  FOREIGN KEY (category_id) REFERENCES CATEGORIES(id)
);


INSERT INTO USERS (id, first_name, last_name, username, password) VALUES (1, 'Chiwa','Kantawong', 'chiwa','$2a$10$D02XjIOKVZOFV5kL6aezXubWwkseex9uQZAiYLUFcUCZ3CPfiGS32');
INSERT INTO USERS (id, first_name, last_name, username, password)  VALUES (2, 'Jirapa', 'Kantawong', 'jirapa', '$2a$10$D02XjIOKVZOFV5kL6aezXubWwkseex9uQZAiYLUFcUCZ3CPfiGS32');
INSERT INTO USERS (id, first_name, last_name, username, password)  VALUES (3, 'Sura', 'Jiranan', 'sura', '$2a$10$D02XjIOKVZOFV5kL6aezXubWwkseex9uQZAiYLUFcUCZ3CPfiGS32');


INSERT INTO CATEGORIES (id, category_name) VALUES (1, 'Travel');
INSERT INTO CATEGORIES (id, category_name) VALUES (2, 'Programming');
INSERT INTO CATEGORIES (id, category_name) VALUES (3, 'Foods');

INSERT INTO CARDS (id, user_id, category_id, topic, content, last_modified) VALUES (1, 1, 2, 'Install JDK', 'This is the guide......', '2011-03-14');
INSERT INTO CARDS (id, user_id, category_id, topic, content, last_modified) VALUES (2, 1, 2, 'Hello World Java', 'Create new application......', '2011-03-14');
INSERT INTO CARDS (id, user_id, category_id, topic, content, last_modified) VALUES (3, 2, 1, 'Travel to Japan', 'Tokyo......', '2011-03-14');
INSERT INTO CARDS (id, user_id, category_id, topic, content, last_modified) VALUES (4, 3, 3, 'Pizza', 'How to cook pizza......', '2011-03-14');
