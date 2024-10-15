create database if not exists warehouse;
use warehouse ;

CREATE TABLE if not exists batch_infor(
    
                      batch_id INT PRIMARY KEY,
                      batch_name VARCHAR(255) NOT NULL,
                      description VARCHAR(255),
                      supplier VARCHAR(255),
                      total_price DECIMAL(10, 2) NOT NULL,
                      create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                   


);
CREATE TABLE if not exists batch (
                       batch_id INT NOT NULL,
                       product_id INT NOT NULL,
                       product_name VARCHAR(255) NOT NULL,
                       arrival_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       quantity INT NOT NULL,
                       purchase_price DECIMAL(10,2),
                       supplier VARCHAR(255),
                       CONSTRAINT pk_batch_product PRIMARY KEY (batch_id, product_id),
                       CONSTRAINT fk_batch_id FOREIGN KEY (batch_id) REFERENCES batch_infor (batch_id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE if not exists product (
                          product_id INT NOT NULL,
                          batch_id INT NOT NULL,
                          product_name VARCHAR(100) NOT NULL,
                          selling_price DECIMAL(10, 2) NOT NULL,
                          image_url VARCHAR(255),
                          quantity INT NOT NULL,
                          expiration_date TIMESTAMP NOT NULL,
                          manufacturer VARCHAR(100),
                          CONSTRAINT pk_product_batch PRIMARY KEY (product_id, batch_id),
                          CONSTRAINT fk_batch_id FOREIGN KEY (batch_id) REFERENCES batch (batch_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE if not exists invoice (
                          invoice_code INT AUTO_INCREMENT,
                          total_revenue DECIMAL(10, 2) NOT NULL,
                          invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT pk_invoice_code PRIMARY KEY (invoice_code)
);

CREATE TABLE if not exists sales (
                       invoice_code INT NOT NULL,
                       product_id INT NOT NULL,
                       batch_id INT NOT NULL,
                       quantity INT NOT NULL,
                       unit_price DECIMAL(10, 2) NOT NULL,
                       total_amount DECIMAL(10,2) NOT NULL,
                       CONSTRAINT fk_invoice_code FOREIGN KEY (invoice_code) REFERENCES invoice (invoice_code) ON DELETE CASCADE ON UPDATE CASCADE,
                       CONSTRAINT fk_product_batch FOREIGN KEY (product_id, batch_id) REFERENCES product (product_id, batch_id) ON DELETE CASCADE ON UPDATE CASCADE
);



