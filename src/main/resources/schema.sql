drop table orderList if exists;
drop table users if exists;
drop table orderAudit if exists;
drop table historical if exists;
drop table historicalAudit if exists;

CREATE TABLE users(
	userId varchar (255) NOT NULL, 
	password varchar (255) NOT NULL,
	role varchar (255) NOT NULL,
	PRIMARY KEY (userid)
);

CREATE TABLE orderList(
	orderId int NOT NULL AUTO_INCREMENT,
	orderType varchar(10) NOT NULL,
	currencyBuy varchar(3) NOT NULL,
	currencySell varchar(3) NOT NULL,
	size int NOT NULL,
	preferredPrice double,
	executedPrice double NOT NULL,
	status varchar (10) NOT NULL,
	goodTillDate timestamp NOT NULL,
	submittedTime timestamp NOT NULL, 
	executedTime timestamp NOT NULL,
	userid varchar (255) NOT NULL,
	PRIMARY KEY (orderId)
);

CREATE TABLE orderAudit(
	recordId int NOT NULL AUTO_INCREMENT,
	orderId int NOT NULL AUTO_INCREMENT,
	orderType varchar (10) NOT NULL,
	currencyBuy varchar(3) NOT NULL,
	currencySell varchar(3) NOT NULL,
	size int NOT NULL,
	preferredPrice double,
	executedPrice double NOT NULL,
	status varchar (10) NOT NULL,
	goodTillDate timestamp NOT NULL,
	submittedTime timestamp NOT NULL, 
	executedTime timestamp NOT NULL,
	userid varchar (255) NOT NULL,
	modifiedTime timestamp NOT NULL,
	PRIMARY KEY (recordId)
);

CREATE TABLE historical(
	historicalId int (10) NOT NULL AUTO_INCREMENT, 
	currencyBuy varchar(3) NOT NULL, 
	currencySell varchar(3) NOT NULL,
	lastPrice double,
	lotSize int,
	transactionTime timestamp,
	PRIMARY KEY (historicalId)
);

CREATE TABLE historicalAudit(
	historicalAuditId int NOT NULL AUTO_INCREMENT,
	filename varchar(255) NOT NULL, 
	processingTime double,
	PRIMARY KEY (historicalAuditId)
); 




