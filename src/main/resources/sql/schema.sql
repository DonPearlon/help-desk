CREATE TABLE IF NOT EXISTS user (id BIGINT IDENTITY PRIMARY KEY,  first_name VARCHAR(255), last_name VARCHAR(255), role_id INT, email VARCHAR(255), password VARCHAR(255));
CREATE TABLE IF NOT EXISTS ticket (id BIGINT IDENTITY PRIMARY KEY, name VARCHAR(255), description VARCHAR(255), created_on DATE, desired_resolution_date DATE, assignee_id BIGINT, owner_id BIGINT, state_id INT, category_id INT, urgency_id INT, approver_id BIGINT, attachment_id BIGINT);
CREATE TABLE IF NOT EXISTS category (id BIGINT IDENTITY PRIMARY KEY, name VARCHAR(255));
CREATE TABLE IF NOT EXISTS comment (id BIGINT IDENTITY PRIMARY KEY, user_id BIGINT, text VARCHAR(255), date TIMESTAMP, ticket_id BIGINT);
CREATE TABLE IF NOT EXISTS attachment (id BIGINT IDENTITY PRIMARY KEY, blob BLOB, ticket_id BIGINT, name VARCHAR(255));
CREATE TABLE IF NOT EXISTS history (id BIGINT IDENTITY PRIMARY KEY, user_id BIGINT, date TIMESTAMP, ticket_id BIGINT, action VARCHAR(255), description VARCHAR(255));
CREATE TABLE IF NOT EXISTS feedback (id BIGINT IDENTITY PRIMARY KEY, user_id BIGINT, rate INT, date DATE, text VARCHAR(255), ticket_id BIGINT);
CREATE INDEX email ON user(email);