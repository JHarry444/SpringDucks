DROP TABLE IF EXISTS `duck` CASCADE;
DROP TABLE IF EXISTS `pond` CASCADE;

CREATE TABLE `duck` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `age` INTEGER NOT NULL CHECK (
        age >= 0
        AND age <= 30
    ),
    `colour` VARCHAR(255) NOT NULL,
    `habitat` VARCHAR(255) NOT NULL,
    `duck_name` VARCHAR(55) NOT NULL,
    `pond_id` BIGINT
);
create table `pond` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255)
);
ALTER TABLE `duck`
ADD CONSTRAINT UK_8ukhqdjo77r8249jx605je74m UNIQUE (`duck_name`);
ALTER TABLE `duck`
add CONSTRAINT FKt84cs5x5pu53f6mddkdxrhhv9 FOREIGN KEY (`pond_id`) REFERENCES `pond` ON DELETE CASCADE;