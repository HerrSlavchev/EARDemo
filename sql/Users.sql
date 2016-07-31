/** 
INTENDED FOR: users/applications allowed to manage the books database
PRIVILEGES: query and modify the DB structure (tables, views, stored procedures, etc) and records
*/
CREATE USER 'developer_books'@'localhost' IDENTIFIED BY 'bo0kD3vP1$$';
GRANT 
    CREATE, ALTER, DROP,
    INSERT, SELECT, UPDATE, DELETE,
    REFERENCES, INDEX, TRIGGER,
    CREATE TEMPORARY TABLES,
    CREATE VIEW, SHOW VIEW,
    CREATE ROUTINE, ALTER ROUTINE, EXECUTE
ON books.* TO 'developer_books'@'localhost';

/**
INTEDED FOR: backend, e.g Application Server connection
PRIVILEGES: query and modify existing records, use views and routines
*/
CREATE USER 'mediator_books'@'localhost' IDENTIFIED BY 'm3di1tOr$$$';
GRANT 
    INSERT, SELECT, UPDATE, DELETE,
    CREATE TEMPORARY TABLES,
    SHOW VIEW,
    EXECUTE
ON books.* TO 'mediator_books'@'localhost';