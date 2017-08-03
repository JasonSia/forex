insert into users(userid,password,role) values ('admin','admin','user');
insert into users(userid,password,role) values ('tester','tester','user');
insert into users(userid,password,role) values ('client','client','user');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid )
values ('limit', 'USD', 'CAD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:13','client');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid )
values ('limit', 'CAD', 'USD', 200, 5.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:13','client');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid )
values ('limit', 'SGD', 'AUD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:13','client');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid )
values ('limit', 'AUD', 'SGD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:13','client');