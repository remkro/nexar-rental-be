CREATE TABLE IF NOT EXISTS nexar_user (
    id uuid PRIMARY KEY,
    first_name character varying(100),
    last_name character varying(100),
    email character varying(255) NOT NULL,
    password character varying(255),
    nexar_role character varying(50),
    creation_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modification_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);
