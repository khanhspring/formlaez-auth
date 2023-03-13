CREATE TABLE "user"
(
    id                 varchar(36)  NOT NULL,
    username           varchar(30)  NOT NULL,
    email              varchar(250) NOT NULL,
    password           varchar(250) DEFAULT NULL,
    status             varchar(30)  NOT NULL,
    first_name         varchar(250) DEFAULT NULL,
    last_name          varchar(250) DEFAULT NULL,

    created_date       timestamp    NOT NULL,
    created_by         varchar(36)  DEFAULT NULL,
    last_modified_date timestamp    DEFAULT NULL,
    last_modified_by   varchar(36)  DEFAULT NULL,
    version            int,
    PRIMARY KEY (id)
);