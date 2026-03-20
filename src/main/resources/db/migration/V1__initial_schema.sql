-- Categories (no dependencies)
CREATE TABLE categories (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            active BIT(1) NOT NULL,
                            description VARCHAR(255),
                            name VARCHAR(255),
                            PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Users (no dependencies)
CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       city VARCHAR(120),
                       email VARCHAR(190) NOT NULL,
                       first_name VARCHAR(120) NOT NULL,
                       last_name VARCHAR(120) NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       phone VARCHAR(30),
                       state VARCHAR(50),
                       street_address VARCHAR(200),
                       username VARCHAR(120) NOT NULL,
                       zip_code VARCHAR(20),
                       role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
                       PRIMARY KEY (id),
                       UNIQUE KEY uk_users_email (email),
                       UNIQUE KEY uk_users_username (username)
) ENGINE=InnoDB;

-- Listings (depends on users + categories)
CREATE TABLE listings (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          city VARCHAR(255),
                          color VARCHAR(255),
                          created_at DATETIME(6),
                          description VARCHAR(255),
                          image_url TEXT,
                          price DECIMAL(38,2),
                          status VARCHAR(255),
                          title VARCHAR(255),
                          category_id BIGINT,
                          owner_id BIGINT NOT NULL,
                          PRIMARY KEY (id),
                          KEY idx_listings_category (category_id),
                          KEY idx_listings_owner (owner_id),
                          CONSTRAINT fk_listings_category FOREIGN KEY (category_id) REFERENCES categories(id),
                          CONSTRAINT fk_listings_owner FOREIGN KEY (owner_id) REFERENCES users(id)
) ENGINE=InnoDB;

-- Offers (depends on users + listings)
CREATE TABLE offers (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        amount DECIMAL(38,2),
                        created_at DATETIME(6),
                        message VARCHAR(500),
                        status VARCHAR(255),
                        buyer_id BIGINT NOT NULL,
                        listing_id BIGINT NOT NULL,
                        PRIMARY KEY (id),
                        KEY idx_offers_buyer (buyer_id),
                        KEY idx_offers_listing (listing_id),
                        CONSTRAINT fk_offers_buyer FOREIGN KEY (buyer_id) REFERENCES users(id),
                        CONSTRAINT fk_offers_listing FOREIGN KEY (listing_id) REFERENCES listings(id)
) ENGINE=InnoDB;

-- Listing images (depends on listings)
CREATE TABLE listing_image (
                               id BIGINT NOT NULL AUTO_INCREMENT,
                               image_url VARCHAR(255),
                               listing_id BIGINT,
                               PRIMARY KEY (id),
                               KEY idx_listing_image_listing (listing_id),
                               CONSTRAINT fk_listing_image_listing FOREIGN KEY (listing_id) REFERENCES listings(id)
) ENGINE=InnoDB;