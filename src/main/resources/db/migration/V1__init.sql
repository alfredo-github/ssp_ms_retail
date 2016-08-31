CREATE TABLE product (
    product_id  BIGINT         NOT NULL AUTO_INCREMENT
    COMMENT 'Product PK',
    name        VARCHAR(64)    NOT NULL
    COMMENT 'Product name',
    description VARCHAR(256) COMMENT 'Product description',
    sku         VARCHAR(64)    NOT NULL
    COMMENT 'Stock keeping unit identifier',
    price_amt   DECIMAL(15, 2) NOT NULL
    COMMENT 'Product price',
    store_id    BIGINT         NOT NULL
    COMMENT 'Store FK',
    count       INT            NOT NULL
    COMMENT 'Count of product in its associated store',
    PRIMARY KEY (product_id)
)
    COMMENT 'Product';

CREATE UNIQUE INDEX product_uidx1 ON product (sku);

CREATE TABLE store (
    store_id BIGINT      NOT NULL AUTO_INCREMENT
    COMMENT 'Store PK',
    name     VARCHAR(64) NOT NULL
    COMMENT 'Store name',
    PRIMARY KEY (store_id)
)
    COMMENT 'Store';


CREATE TABLE purchase_order (
    order_id   BIGINT       NOT NULL AUTO_INCREMENT
    COMMENT 'Order PK',
    store_id   BIGINT       NOT NULL
    COMMENT 'FK to store',
    order_date DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT 'Date and time this row was created',
    status     VARCHAR(50)  NOT NULL
    COMMENT 'Order status',
    first_name VARCHAR(100) NOT NULL
    COMMENT 'Purchaser first name',
    last_name  VARCHAR(100) NOT NULL
    COMMENT 'Purchaser last name',
    email      VARCHAR(100) NOT NULL
    COMMENT 'Purchaser email',
    phone      VARCHAR(20)  NOT NULL
    COMMENT 'Purchaser phone',
    PRIMARY KEY (order_id)
)
    COMMENT 'Order';


CREATE TABLE purchase_order_item (
    order_item_id BIGINT NOT NULL AUTO_INCREMENT
    COMMENT 'Order item PK',
    order_id      BIGINT NOT NULL
    COMMENT 'FK to order',
    product_id    BIGINT NOT NULL
    COMMENT 'FK to product',
    count         INT    NOT NULL
    COMMENT 'Count of product in order item',
    PRIMARY KEY (order_item_id)
)
    COMMENT 'Order item';
