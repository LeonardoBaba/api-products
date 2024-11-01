CREATE TABLE transactions (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    quantity DECIMAL(19,2) NOT NULL,
    operation_type VARCHAR(15) NOT NULL,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    transaction_date DATE NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
