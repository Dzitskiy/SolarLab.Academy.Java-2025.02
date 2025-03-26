CREATE TABLE advertisement
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    category VARCHAR(100) NOT NULL,
    subcategory VARCHAR(100),
    cost INTEGER NOT NULL,
    description VARCHAR(500),
    seller VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    create_date_time TIMESTAMP DEFAULT now()
)