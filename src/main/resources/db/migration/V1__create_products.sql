CREATE TABLE products (
    id BINARY(16) NOT NULL,
    sku VARCHAR(30) NOT NULL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(1000),
    unit_price DECIMAL(19, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT pk_products PRIMARY KEY (id),
    CONSTRAINT uk_products_sku UNIQUE (sku),
    CONSTRAINT chk_products_unit_price_positive CHECK (unit_price > 0),
    CONSTRAINT chk_products_status
        CHECK (status IN ('ACTIVE', 'INACTIVE'))
) ENGINE = InnoDB;