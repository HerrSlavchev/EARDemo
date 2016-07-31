CREATE TABLE user (
    id integer unsigned NOT NULL AUTO_INCREMENT,
    nickname varchar(32) NOT NULL,
    f_name varchar(32) NOT NULL,
    s_name varchar(32),
    l_name varchar(32) NOT NULL,
    PRIMARY KEY(id)
);


CREATE TABLE cred (
    id_user integer unsigned NOT NULL,
    pass varchar(256) NOT NULL,
    iter integer unsigned NOT NULL,
    salt varchar(64) NOT NULL,
    len integer unsigned NOT NULL,
    PRIMARY KEY(id_user),
    FOREIGN KEY(id_user) REFERENCES user(id)
);

