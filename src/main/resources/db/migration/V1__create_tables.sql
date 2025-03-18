CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE advertisement
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    category VARCHAR(100) NOT NULL,
    subcategory VARCHAR(100),
    cost INTEGER NOT NULL,
    description VARCHAR(500),
    seller VARCHAR(255) NOT NULL,
    create_date_time TIMESTAMP DEFAULT now(),
    user_id INT REFERENCES users (id) ON DELETE CASCADE
)