CREATE TABLE IF NOT EXISTS requests
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY,
    application_name VARCHAR(128)                NOT NULL,
    request_uri      VARCHAR(128)                NOT NULL,
    ip_address       VARCHAR(128)                NOT NULL,
    time_requested   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_request PRIMARY KEY (id)
);