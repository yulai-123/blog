CREATE DATABASE blog;

USE blog;

# 创建admin
CREATE TABLE admin (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(24) NOT NULL UNIQUE,
  passwd VARCHAR(64) NOT NULL
);

#创建category
CREATE TABLE category (
  id INT PRIMARY key AUTO_INCREMENT,
  name VARCHAR(24) NOT NULL UNIQUE
);

#创建article_content
CREATE TABLE article_content (
    id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT、 NOT NULL
);

#创建article
CREATE TABLE article (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  create_date datetime NOT NULL,
  revise_date datetime DEFAULT NULL,
  category_id int(11) NOT NULL,
  author_id int(11) NOT NULL,
  content_id int(11) NOT NULL,
  title varchar(64) NOT NULL,
  FOREIGN KEY (category_id) REFERENCES category (id),
  FOREIGN KEY (author_id) REFERENCES admin (id),
  FOREIGN KEY (content_id) REFERENCES article_content (id)
);