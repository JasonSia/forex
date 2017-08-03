insert into users(userid,password,role) values ('admin','admin','user');
insert into users(userid,password,role) values ('tester','tester','user');
insert into users(userid,password,role) values ('client','client','user');
insert into users(userid,password,role) values ('client2','client2','user');
insert into users(userid,password,role) values ('client3','client3','user');
insert into users(userid,password,role) values ('client4','client4','user');


insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber)
values ('limit', 'USD', 'CAD', 100, 2.00, 'PARTIALLYFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:15','client','c81b59cb37dbe7d2607432877c12cac2');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber )
values ('limit', 'CAD', 'USD', 200, 5.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:16','client2','c81b59cb37dbe7d2607432877c234ac2');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber )
values ('limit', 'SGD', 'AUD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:09:13','client3','c81b587640dbe7d2607432877c234ac2');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber )
values ('limit', 'AUD', 'SGD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:10:13','client4','c81b59cb37dbe7d2607432877c289564');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber )
values ('limit', 'AUD', 'SGD', 100, 2.00, 'FILLED', '2020-07-30 23:04:13', '2017-08-01 23:10:13','client1','7c8f873e9bd500daaf0fe42bcb3c6c4e');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber, EXECUTEDPRICE, EXECUTEDTIME)
values ('limit', 'USD', 'CAD', 100, 2.00, 'FILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:15','client','c81b59cb37dbe7d2607432877c12cac2', 2.04, '2017-08-01 23:05:15');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber, EXECUTEDPRICE, EXECUTEDTIME)
values ('limit', 'USD', 'CAD', 100, 2.00, 'FILLED', '2016-07-30 23:04:13', '2017-08-01 23:04:15','client','c81b59cbde3he7d2607432877c12cac2', 2.04, '2017-08-01 23:05:15');