CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL,
    quantity DECIMAL(19,2) NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    registration_date DATE NOT NULL
);
