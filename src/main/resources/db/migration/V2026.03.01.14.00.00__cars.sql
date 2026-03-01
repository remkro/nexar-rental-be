CREATE TABLE IF NOT EXISTS cars (
    id uuid PRIMARY KEY,
    license_plate character varying(50),
    make character varying(100),
    model character varying(150),
    year integer,
    mileage integer,
    seats integer,
    horse_power integer,
    featured_image character varying(255),
    daily_rate numeric(10, 2),
    fuel character varying(20),
    transmission character varying(20),
    status character varying(20),
    body character varying(20),
    creation_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modification_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);