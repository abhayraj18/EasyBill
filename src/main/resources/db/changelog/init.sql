--liquibase formatted sql logicalFilePath:init.sql

--changeset Abhay:init
create table BILL_INFORMATION (ID integer not null, AMOUNT float(9,2) not null, BILLED_AT datetime not null, EXCESS_AMOUNT float(9,2), PENDING_AMOUNT float(9,2) not null, primary key (ID)) engine=InnoDB;
create table DISTRIBUTOR (ID integer not null, primary key (ID)) engine=InnoDB;
create table EMAIL (ID integer not null auto_increment, CREATED_AT datetime not null, DATA text, EMAIL_TYPE varchar(255) not null, ERROR varchar(500), SENT bit(1) default b'0', SENT_AT datetime, USER_ID integer not null, primary key (ID)) engine=InnoDB;
create table ITEM (ID integer not null auto_increment, ADDED_AT datetime not null, ARCHIVE bit(1) default b'0', BASE_UNIT enum ('PC', 'BOX', 'BAG') not null, BASE_UNIT_PRICE float(7,2) not null, LARGE_UNIT enum ('PC', 'BOX', 'BAG') not null, LARGE_UNIT_PRICE float(7,2) not null, MODIFIED_AT datetime, NAME varchar(100) not null, UNIT_CONVERSION_VALUE integer not null, ADDED_BY integer not null, MODIFIED_BY integer, primary key (ID)) engine=InnoDB;
create table ORDER_DETAIL (ID integer not null auto_increment, QUANTITY float(5,2) not null, UNIT enum ('PC', 'BOX', 'BAG') not null, ITEM_ID integer not null, ORDER_INFO_ID integer not null, primary key (ID)) engine=InnoDB;
create table ORDER_INFO (ID integer not null auto_increment, APPROVED bit(1) default b'0', DESCRIPTION varchar(255), MODIFIED_AT datetime, ORDERED_AT datetime not null, MODIFIED_BY integer, ORDERED_BY integer not null, primary key (ID)) engine=InnoDB;
create table PAYMENT_INFORMATION (ID integer not null auto_increment, AMOUNT float(9,2) not null, APPROVED bit(1) default b'0', APPROVED_AT datetime, DESCRIPTION varchar(255), PAID_AT datetime, APPROVED_BY integer, BILL_ID integer not null, primary key (ID)) engine=InnoDB;
create table ROLE (ID integer not null auto_increment, NAME varchar(255) not null, primary key (ID)) engine=InnoDB;
create table USER (USER_TYPE varchar(31) not null, ID integer not null auto_increment, ADDRESS varchar(255), CREATED_AT datetime not null, EMAIL varchar(50) not null, EMAIL_VERIFICATION_TOKEN varchar(255), EMAIL_VERIFIED bit(1) default b'0', EMAIL_VERIFIED_AT datetime, LAST_MODIFIED_AT datetime, NAME varchar(50) not null, PASSWORD varchar(100) not null, PASSWORD_CHANGED_AT datetime, PHONE_NUMBER varchar(15) not null, STATUS enum ('ACTIVE', 'INACTIVE') not null, STATUS_CHANGED_AT datetime, USERNAME varchar(50) not null, primary key (ID)) engine=InnoDB;
create table USER_ROLE (USER_ID integer not null, ROLE_ID integer not null, primary key (USER_ID, ROLE_ID)) engine=InnoDB;
create table USER_PASSWORD (ID integer not null auto_increment, CREATED_AT datetime, PASSWORD varchar(100) not null, USER_ID integer not null, primary key (ID)) engine=InnoDB;
create table WHOLESALER (ID integer not null, primary key (ID)) engine=InnoDB;
alter table ITEM add constraint UK_tjd0g5ys823p20xkden39a2xg unique (NAME);
alter table ROLE add constraint UK_lqaytvswxwacb7s84gcw7tk7l unique (NAME);
alter table DISTRIBUTOR add constraint FKq72m7mn77urs0vsuk94pp7hjp foreign key (ID) references USER (ID);
alter table EMAIL add constraint FKb0hpb7vrclbxndcoqrvmxjeo4 foreign key (USER_ID) references USER (ID);
alter table ITEM add constraint FK5i7ryfcbll7djjxd6wjgdtuos foreign key (ADDED_BY) references USER (ID);
alter table ITEM add constraint FKh6hvr1q34rhdiki7mddi0r4nv foreign key (MODIFIED_BY) references USER (ID);
alter table ORDER_DETAIL add constraint FKbe30jlhy5kaltqnmxgxs10p2w foreign key (ITEM_ID) references ITEM (ID);
alter table ORDER_DETAIL add constraint FKf5qo4egpv6d2o5r58weofhr53 foreign key (ORDER_INFO_ID) references ORDER_INFO (ID) on delete cascade;
alter table ORDER_INFO add constraint FKavblpstusp30gwoc33fanmqjh foreign key (MODIFIED_BY) references USER (ID);
alter table ORDER_INFO add constraint FK318h8wr7pr6ux7hjuat4mkiu4 foreign key (ORDERED_BY) references USER (ID);
alter table PAYMENT_INFORMATION add constraint FKjfb57jk58r6el6r98yjs6aipf foreign key (APPROVED_BY) references USER (ID);
alter table PAYMENT_INFORMATION add constraint FK6p98i38rg2ljjpd71d8ci3bcb foreign key (BILL_ID) references BILL_INFORMATION (ID) on delete cascade;
alter table USER_ROLE add constraint FKn1rn9qodd3u4le8uf3kl33qe3 foreign key (ROLE_ID) references ROLE (ID);
alter table USER_ROLE add constraint FKa8x5mvctia7u43u2mm3hyy5bm foreign key (USER_ID) references USER (ID);
alter table USER_PASSWORD add constraint FKselq4trckqfobsxnit7eeujf7 foreign key (USER_ID) references USER (ID);
alter table WHOLESALER add constraint FK5hswgkrw51id6r0d7mc52w1r5 foreign key (ID) references USER (ID);
alter table BILL_INFORMATION add constraint FK_BILL_INFORMATION_ID foreign key (ID) references ORDER_INFO (ID) on delete cascade;
