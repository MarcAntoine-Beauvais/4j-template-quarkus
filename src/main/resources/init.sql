CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS my_table
(
    uuid UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(255) NULL,
    status VARCHAR(255) NULL
);