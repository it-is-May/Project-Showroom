/*	Created by: Giao Nguyen
	Reviewed by: Hannah Swiney
	Date created: 11/18/2024
	Last updated: 11/27/2024
*/
create database abc;
use abc;
create table person (
personalID varchar(9) primary key,
age int not null,
gender char(1) not null,
fname varchar(10) not null,
lname varchar(20) not null,
line1 varchar(40) not null,
line2 varchar(30), 
city varchar(40) not null,
zip int not null,
check (age < 65)
);

create table phone (
personID varchar(9),
pnumber char(10),
primary key (personID, pnumber),
foreign key (personID) references person(personalID) on delete cascade
);

create table department (
deptID int primary key,
dname varchar(15) unique not null
);

create table job (
jobId char(5) primary key,
jdescription varchar(70) not null,
deptid int not null,
date_posted date,
foreign key (deptid) references department(deptID) on delete cascade
);

create table mkt_site (
siteID int primary key,
sname varchar(20) not null,
slocation varchar(35) not null
);

create table employee (
empId varchar(9) primary key,
rank varchar(25) not null,
title varchar(25) not null,
supervisorId varchar(9) unique not null,
deptId int not null,
foreign key (empId) references person(personalID) on delete cascade,
foreign key (deptId) references department(deptID) on delete restrict
);

create table works_at(
eid varchar(9),
siteid int,
primary key (eid, siteid),
foreign key (eid) references employee(empId) on delete cascade,
foreign key (siteid) references mkt_site(siteID) on delete cascade
);

create table customer (
customerId varchar(9) primary key,
foreign key (customerId) references person(personalID)
);

create table prefers(
cid varchar(9),
eid varchar(9),
primary key (cid, eid),
foreign key (cid) references customer(customerId) on delete cascade,
foreign key (eid) references employee(empId) on delete cascade
);

create table potential_emp (
potentialEmpId varchar(9) primary key,
foreign key (potentialEmpId) references person(personalID) on delete cascade
);

create table product (
productId varchar(15) primary key,
productType varchar(35) not null,
size varchar(50) not null,
price decimal(19, 2) default 0.00, 
weight decimal(6, 2) default 0.00,
style varchar(40) not null
);

create table vendor (
vendorId varchar(4) primary key,
vname varchar(50) unique not null,
acctno varchar(17) unique not null,
credit_rating int not null,
url varchar(2048) unique,
line1 varchar(40) not null,
line2 varchar(30), 
city varchar(40) not null,
zip int not null
);

create table part (
partType varchar(50) primary key,
price decimal(19, 2) default 0.00,
vid varchar(4) not null,
weight decimal(6, 2) default 0.00,
foreign key (vid) references vendor(vendorId) on delete restrict
);

create table consists_of (
productid varchar(15),
partType varchar(50),
qtyUsed int default 0,
primary key (productid, partType),
foreign key (productid) references product(productId) on delete cascade,
foreign key (partType) references part(partType) on delete restrict
);

create table sales_history (
sid int,
pid varchar(15),
cid varchar(9),
sale_time date,
primary key (sid, pid, cid, sale_time),
foreign key (sid) references mkt_site(siteID) on delete restrict,
foreign key (pid) references product(productId) on delete restrict,
foreign key (cid) references customer(customerId) on delete cascade
);

create table internal_application(
eid varchar(9),
jid char(5),
primary key (eid, jid),
foreign key (eid) references employee(empId) on delete cascade,
foreign key (jid) references job(jobId) on delete cascade
);

create table external_application(
peid varchar(9),
jid char(5),
primary key (pid, jid),
foreign key (peid) references potential_emp(potentialEmpId) on delete cascade,
foreign key references job(jobId) on delete cascade
);

create table interview(
interview_time datetime,
jid char(5),
interviewee varchar(9),
interviewer varchar(9),
primary key (interview_time, jid, interviewee, interviewer)
foreign key (jid) references job(jobId) on delete cascade,
foreign key (interviewee) references potential_emp(potentialEmpId) on delete cascade,
foreign key (interviewer) references employee(empId) on delete restrict
);

create table interview_grade(
itime datetime,
jid char(5),
interviewee varchar(9),
interviewer varchar(9),
roundNo int,
grade int not null,
round_passed int not null,
primary key (itime, jid, interviewee, interviewer, roundNo)
foreign key (jid) references job(jobId) on delete cascade,
foreign key (interviewee) references potential_emp(potentialEmpId)on delete cascade,
foreign key (interviewer) references employee(empId) on delete restrict,
check (grade >= 0 AND grade <= 100) 
);

create table interview_result(
jid char(5),
interviewee varchar(9),
avg_grade int,
rounds_passed int default 0,
is_selected Boolean,
primary key (jid, interviewee),
foreign key (jid) references job(jobId) on delete cascade,
foreign key (interviewee) references potential_emp(potentialEmpId) on delete cascade,
check (avg_grade >=0 AND avg_grade <= 100),
check (rounds_passed >= 0)
);

create table monthly_salary (
transaction_no int,
empId varchar(9),
amount decimal(19, 2) default 0.00,
pay_date date not null,
primary key (transaction_no, empId)
foreign key (empId) references employee(empId) on delete cascade
);

insert into person 
values ('E0000001', 35, 'M', 'John', 'Doe', '123 Main St', 'Dallas', 75201),
('E0000002', 42, 'F', 'Jane', 'Smith', '456 Oak Ave', 'Houston', 77002),
('E0000003', 28, 'M', 'Mike', 'Johnson', '789 Pine Rd', 'Austin', 78701),
('E0000004', 39, 'F', 'Sarah', 'Williams', '321 Elm St', 'San Antonio', 78205),
('E0000005', 45, 'M', 'David', 'Brown', '654 Cedar Ln', 'Fort Worth', 76102), -- 5 employees
('C0000002', 55, 'M', 'Robert', 'Wilson', '246 Birch St', 'Irving', 75061),
('C0000003', 33, 'F', 'Lisa', 'Anderson', '135 Spruce Ave', 'Arlington', 76010),
('C0000004', 47, 'M', 'Thomas', 'Taylor', '864 Ash Rd', 'Corpus Christi', 78401),
('C0000005', 29, 'F', 'Amanda', 'Martinez', '753 Walnut Ln', 'El Paso', 79901), -- 5 customers
('P0000001', 31, 'F', 'Hellen', 'Cole', '159 Cherry St', 'Lubbock', 79401),
('P0000002', 36, 'M', 'Daniel', 'Lee', '357 Pineapple Rd', 'Laredo', 78040),
('P0000003', 27, 'F', 'Jessica', 'Garcia', '951 Orange Ave', 'Garland', 75040); --- 3 potential employees

insert into phone
values ('E0000001', '2141234567'), ('E0000002', '7131234567'),('E0000003', '5121234567'),
('E0000004', '2101234567'), ('E0000005', '8171234567'), -- 5 employees
('C0000001', '9721234567'), ('C0000002', '9721234568'), ('C0000003', '8171234568'),
('C0000004', '3611234567'), ('C0000005', '9151234567'), -- 5 customers
('P0000001', '8061234567'),('P0000002', '9561234567'), ('P0000003', '9721234569'); --- 3 potential employees

insert into department 
values (1, 'Marketing'), (2, 'Sales'), (3, 'Engineering'), (4, 'Human Resources'); -- 4 departments

insert into job
values ('11111', 'Marketing Manager', 1, '2011-01-15'), ('12345', 'Sales Representative', 2, '2011-01-20'), ('22222', 'Software Engineer', 3, '2011-02-05'), ('33333', 'HR Specialist', 4, '2011-01-10');

insert into mkt_site
values (1, 'Downtown Store', 'Dallas City Center'),
(2, 'Galleria Shop', 'Houston Galleria'),
(3, 'Tech Hub', 'Austin Tech District'),
(4, 'River Walk Outlet', 'San Antonio River Walk'); -- 4 departments

insert into employee
values ('E0000001', 'Manager', 'Marketing Manager', 'E0000002', 1), ('E0000002', 'Director', 'Sales Director', 'E0000002', 2), ('E0000003', 'Associate', 'Software Engineer', 'E0000001', 3),
('E0000004', 'Specialist', 'HR Specialist', 'E0000002', 4), ('E0000005', 'Associate', 'Sales Associate', 'E0000002', 2);

insert into works_at
values ('E0000001', 1), ('E0000002', 2),
('E0000003', 3), ('E0000004', 4),
('E0000005', 1); --  site Id not dept Id

insert into customer
values ('C0000001'), ('C0000002'),('C0000003'),
('C0000004'), ('C0000005');

insert into prefers
values ('C0000001', 'E0000005'), ('C0000002', 'E0000002'), ('C0000003', 'E0000005'),
('C0000004', 'E0000002'), ('C0000005', 'E0000005');

insert into potential_emp
values ('P0000001'), ('P0000002'), ('P0000003');

insert into product
values ('PROD001', 'Electronics', 'Medium', 299.99, 2.5, 'Modern'), ('PROD002', 'Furniture', 'Large', 599.99, 50.0, 'Classic'), ('PROD003', 'Clothing', 'Small', 49.99, 0.5, 'Casual'),
('PROD004', 'Electronics', 'Small', 199.99, 1.0, 'Compact'), ('PROD005', 'Appliances', 'Large', 799.99, 75.0, 'Energy Efficient');

insert into vendor
values ('V001', 'Tech Supplies Inc', '1234567890123456', 85, 'http://techsupplies.com', '456 Vendor St', 'Dallas', 75202), ('V002', 'Furniture Wholesale', '2345678901234567', 90, 'http://furnwholesale.com', '789 Supplier Rd', 'Houston', 77003), ('V003', 'Textile Traders', '3456789012345678', 80, 'http://textiletraders.com', '321 Fabric Ln', 'Austin', 78702);

insert into part
values ('CPU', 150.00, 'V001', 0.2),
('Wood Panel', 50.00, 'V002', 10.0),
('Fabric', 10.00, 'V003', 0.5),
('Display', 100.00, 'V001', 0.5),
('Motor', 75.00, 'V001', 2.0),
('Cup', 5.00, 'V003', 0.3),
('Cup', 4.50, 'V002', 0.3),
('Cup', 4.75, 'V001', 0.3);

insert into consists_of
values ('PROD001', 'CPU', 1),
('PROD001', 'Display', 1),
('PROD002', 'Wood Panel', 4),
('PROD003', 'Fabric', 2),
('PROD004', 'Display', 1),
('PROD005', 'Motor', 1),
('PROD005', 'Display', 1);

insert into sales_history
values (1, 'PROD001', 'C0000001', '2011-03-15'),
(2, 'PROD002', 'C0000002', '2011-03-20'),
(3, 'PROD003', 'C0000003', '2011-04-01'),
(4, 'PROD004', 'C0000004', '2011-03-10'),
(5, 'PROD005', 'C0000005', '2011-03-25'),
(1, 'PROD001', 'C0000001', '2011-04-05'),
(2, 'PROD003', 'C0000002', '2011-04-10'),
(3, 'PROD002', 'C0000003', '2011-04-15'),
(4, 'PROD005', 'C0000004', '2011-04-20'),
(5, 'PROD004', 'C0000005', '2011-04-25');

insert into internal_application
values ('E0000003', '11111'), ('E0000005', '12345'), ('E0000001', '22222');

insert into external_application
values ('P0000001', '11111'), ('P0000002', '12345'), ('P0000003', '33333');

insert into interview 
values ('2011-02-01 10:00:00', '11111', 'P0000001', 'E0000001'), ('2011-02-02 14:00:00', '12345', 'P0000002', 'E0000002'), ('2011-02-03 11:00:00', '33333', 'P0000003', 'E0000004'),
('2011-02-04 13:00:00', '11111', 'P0000001', 'E0000002'), ('2011-02-05 15:00:00', '12345', 'P0000002', 'E0000005'), ('2011-02-06 10:00:00', '33333', 'P0000003', 'E0000001');

insert into interview_grade
values ('2011-02-01 10:00:00', '11111', 'P0000001', 'E0000001', 1, 75, 1),
('2011-02-02 14:00:00', '12345', 'P0000002', 'E0000002', 1, 80, 1),
('2011-02-03 11:00:00', '33333', 'P0000003', 'E0000004', 1, 70, 1),
('2011-02-04 13:00:00', '11111', 'P0000001', 'E0000002', 2, 85, 1),
('2011-02-05 15:00:00', '12345', 'P0000002', 'E0000005', 2, 78, 1),
('2011-02-06 10:00:00', '33333', 'P0000003', 'E0000001', 2, 72, 1);

insert into interview_result
values ('11111', 'P0000001', 80, 2, true),
('12345', 'P0000002', 79, 2, true),
('33333', 'P0000003', 71, 2, false);

insert into monthly_salary
values (1, 'E0000001', 5000.00, '2011-01-31'),
(2, 'E0000002', 6000.00, '2011-01-31'),
(3, 'E0000003', 4500.00, '2011-01-31'),
(4, 'E0000004', 4000.00, '2011-01-31'),
(5, 'E0000005', 3500.00, '2011-01-31'),
(6, 'E0000001', 5000.00, '2011-02-28'),
(7, 'E0000002', 6000.00, '2011-02-28'),
(8, 'E0000003', 4500.00, '2011-02-28'),
(9, 'E0000004', 4000.00, '2011-02-28'),
(10, 'E0000005', 3500.00, '2011-02-28'),
(11, 'E0000001', 5000.00, '2011-03-31'),
(12, 'E0000002', 6000.00, '2011-03-31'),
(13, 'E0000003', 4500.00, '2011-03-31'),
(14, 'E0000004', 4000.00, '2011-03-31'),
(15, 'E0000005', 3500.00, '2011-03-31');