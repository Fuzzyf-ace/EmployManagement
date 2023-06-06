DROP TABLE IF EXISTS employers_employees;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS employers;
DROP TABLE IF EXISTS shifts;


CREATE TABLE employers
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    email      VARCHAR(45)           NOT NULL,
    password   LONGTEXT              NOT NULL,
    first_name VARCHAR(45)           NULL,
    last_name  VARCHAR(45)           NULL,
    user_role  INT                   NULL,
    CONSTRAINT pk_employers PRIMARY KEY (id)
);

CREATE TABLE employers_employees
(
    employer_id  BIGINT NOT NULL,
    employees_id BIGINT NOT NULL
);


CREATE TABLE employees
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    email       VARCHAR(45)           NOT NULL,
    password    LONGTEXT              NOT NULL,
    first_name  VARCHAR(45)           NULL,
    last_name   VARCHAR(45)           NULL,
    active      BIT(1)                NULL,
    pay_rate    DECIMAL(10)           NULL,
    user_role   INT                   NULL,
    employer_id BIGINT                NULL,
    CONSTRAINT pk_employees PRIMARY KEY (id)
);

ALTER TABLE employees
    ADD CONSTRAINT FK_EMPLOYEES_ON_EMPLOYER FOREIGN KEY (employer_id) REFERENCES employers (id);
ALTER TABLE employers_employees
    ADD CONSTRAINT uc_employers_employees_employees UNIQUE (employees_id);

ALTER TABLE employers_employees
    ADD CONSTRAINT fk_empemp_on_employee FOREIGN KEY (employees_id) REFERENCES employees (id);

ALTER TABLE employers_employees
    ADD CONSTRAINT fk_empemp_on_employer FOREIGN KEY (employer_id) REFERENCES employers (id);
CREATE TABLE shifts
(
    id         SERIAL PRIMARY KEY   NOT NULL,
    description TEXT                NOT NULL,
    start_time  datetime            NOT NULL,
    end_time    datetime            NOT NULL
);


