INSERT INTO roles(id, name) VALUES
(1, 'ROLE_ROOT'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_USER');

INSERT INTO users(email, name, password)
VALUES('admin@admin.com', 'Admin', '$2a$10$itnrvZrUZBRoWzWUgmy4suJZRy5wgCI0rbhqwGsPUFfK10U/77K6i');

INSERT INTO users_roles(users_id, roles_id)
VALUES(1,1), (1,2), (1,3);
