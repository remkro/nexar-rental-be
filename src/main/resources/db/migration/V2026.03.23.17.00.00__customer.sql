CREATE TABLE IF NOT EXISTS customer (
    id uuid PRIMARY KEY,
    user_id uuid,
    driver_license_no character varying(255),
    date_of_birth date,
    address character varying(255),
    city character varying(255),
    blacklisted boolean NOT NULL DEFAULT false,
    creation_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modification_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_customer_user FOREIGN KEY (user_id) REFERENCES nexar_user(id)
);