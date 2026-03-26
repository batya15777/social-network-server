CREATE DATABASE social_network;
USE social_network;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL,
                       lastName VARCHAR(50) NOT NULL,
                       phone VARCHAR(10) NOT NULL,
                       generalSex VARCHAR(10) NOT NULL,
                       username VARCHAR(8) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       token VARCHAR(255),
                       profileUrl VARCHAR(255)
);

CREATE TABLE posts (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       user_id INT NOT NULL,
                       content VARCHAR(255),
                       image_url VARCHAR(500),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE follows (
                         follower_id INT NOT NULL,
                         followed_id INT NOT NULL,
                         PRIMARY KEY (follower_id, followed_id),
                         FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE,
                         FOREIGN KEY (followed_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE likes (
                       user_id INT NOT NULL,
                       post_id INT NOT NULL,
                       date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       PRIMARY KEY (user_id, post_id),
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                       FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

CREATE TABLE comments (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          post_id INT,
                          user_id INT,
                          content TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);