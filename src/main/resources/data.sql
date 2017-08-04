insert into users(userid,password,role) values ('admin','admin','user');
insert into users(userid,password,role) values ('tester','tester','user');
insert into users(userid,password,role) values ('client','client','user');
insert into users(userid,password,role) values ('client2','client2','user');
insert into users(userid,password,role) values ('client3','client3','user');
insert into users(userid,password,role) values ('client4','client4','user');


insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber)
values ('LIMIT', 'USD', 'CAD', 100, 2.00, 'PARTIALLYFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:15','client','03ba55e4c6839f7a3685c592b4cdc855');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber )
values ('LIMIT', 'CAD', 'USD', 200, 5.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:16','client2','3a8da1d0d5d6a36aae96e7e9b1bd4557');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber )
values ('LIMIT', 'SGD', 'AUD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:09:13','client3','c81b587640dbe7d2607432877c234ac2');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber )
values ('LIMIT', 'AUD', 'SGD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:10:13','client4','c81b59cb37dbe7d2607432877c289564');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber )
values ('LIMIT', 'AUD', 'SGD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:10:13','client1','7c8f873e9bd500daaf0fe42bcb3c6c4e');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber, EXECUTEDPRICE, EXECUTEDTIME)
values ('LIMIT', 'USD', 'CAD', 100, 2.00, 'FILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:15','client','e4ebd10ade145e76d6e8edfabada27fe', 2.04, '2017-08-01 23:05:15');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber, EXECUTEDPRICE, EXECUTEDTIME)
values ('LIMIT', 'USD', 'CAD', 100, 2.00, 'FILLED', '2016-07-30 23:04:13', '2017-08-01 23:04:15','client','db4185d3f455dc7ac70385d215d74892', 2.04, '2017-08-01 23:05:15');