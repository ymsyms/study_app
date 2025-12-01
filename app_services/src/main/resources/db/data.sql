INSERT INTO topics (title, description) VALUES
('SELECT Basics', 'Learn how to retrieve data from a table using SELECT'),
('JOINs', 'Understand INNER JOIN, LEFT JOIN, RIGHT JOIN'),
('Indexes', 'Introduction to indexing and performance optimization');

INSERT INTO notes (topic_id, content) VALUES
(1, 'SELECT * returns all columns.'),
(2, 'INNER JOIN requires matching keys.'),
(3, 'Indexes improve read performance.');

INSERT INTO questions (topic_id, question, answer) VALUES
(1, 'Return all rows from a table named users.', 'SELECT * FROM users;'),
(2, 'Which join returns all rows from left table?', 'LEFT JOIN'),
(3, 'Purpose of an index?', 'Speed up queries');
