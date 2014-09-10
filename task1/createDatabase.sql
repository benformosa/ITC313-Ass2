create user 'user'@'localhost' identified by 'password';
create database grades;
grant all on grades.* to 'user'@'localhost';
