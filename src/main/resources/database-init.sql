# DROP TABLE IF EXISTS employers_employees;
DROP TABLE IF EXISTS work_records;
DROP TABLE IF EXISTS shifts;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS employers;


CREATE TABLE employers
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    email      VARCHAR(45) UNIQUE    NOT NULL,
    password   LONGTEXT              NOT NULL,
    first_name VARCHAR(45)           NULL,
    last_name  VARCHAR(45)           NULL,
    user_role  INT                   NULL,
    CONSTRAINT pk_employers PRIMARY KEY (id)
);

INSERT INTO employers (email, password, first_name, last_name, user_role)
VALUES ('init1@example.com', 'password', 'david', 'a', 0),
       ('init2@example.com', 'password','daiming', 'yang', 0);



CREATE TABLE employees
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    email      VARCHAR(45)    UNIQUE NOT NULL,
    password   LONGTEXT              NOT NULL,
    first_name VARCHAR(45)           NULL,
    last_name  VARCHAR(45)           NULL,
    active     BIT(1)                NULL,
    pay_rate   DECIMAL(10)           NULL,
    user_role  INT                   NULL,
    employer   BIGINT                NULL,
    CONSTRAINT pk_employees PRIMARY KEY (id)
);

ALTER TABLE employees
    ADD CONSTRAINT FK_EMPLOYEES_ON_EMPLOYER FOREIGN KEY (employer) REFERENCES employers (id);

INSERT INTO employees (email, password, first_name, last_name, active, pay_rate, user_role, employer)
VALUES ('init_employee_email@example.com', 'password', 'employee1', 'employee1', true, 1, 1,1);



CREATE TABLE shifts
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    employer      BIGINT                NULL,
    `description` LONGTEXT              NOT NULL,
    start_time    datetime              NOT NULL,
    end_time      datetime              NOT NULL,
    CONSTRAINT pk_shifts PRIMARY KEY (id)
);

ALTER TABLE shifts
    ADD CONSTRAINT FK_SHIFTS_ON_EMPLOYER FOREIGN KEY (employer) REFERENCES employers (id);

CREATE TABLE work_records
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    employee   BIGINT                NULL,
    shift      BIGINT                NULL,
    start_time datetime              NULL,
    end_time   datetime              NULL,
    CONSTRAINT pk_work_records PRIMARY KEY (id)
);

ALTER TABLE work_records
    ADD CONSTRAINT FK_WORK_RECORDS_ON_EMPLOYEE FOREIGN KEY (employee) REFERENCES employees (id);

ALTER TABLE work_records
    ADD CONSTRAINT FK_WORK_RECORDS_ON_SHIFT FOREIGN KEY (shift) REFERENCES shifts (id);

