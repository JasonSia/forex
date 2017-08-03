insert into users(userid,password,role) values ('admin','admin','user');
insert into users(userid,password,role) values ('tester','tester','user');
insert into users(userid,password,role) values ('client','client','user');
insert into users(userid,password,role) values ('client2','client2','user');
insert into users(userid,password,role) values ('client3','client3','user');
insert into users(userid,password,role) values ('client4','client4','user');


insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid,orderNumber)
values ('limit', 'USD', 'CAD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:15','client','c81b59cb37dbe7d2607432877c12cac29ce53c39

');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid )
values ('limit', 'CAD', 'USD', 200, 5.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:04:16','client2','1055bebcdcd7de41df33cdaf8ab31d02dea1e214');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid )
values ('limit', 'SGD', 'AUD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:09:13','client3','d70cb6979e51f46d026ae6961be41252d0644cf4');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid )
values ('limit', 'AUD', 'SGD', 100, 2.00, 'NOTFILLED', '2020-07-30 23:04:13', '2017-08-01 23:10:13','client4','f828702beda27ab61e83867d8ffdf183d22e10ae');

insert into orderList(orderType, currencyBuy, currencySell, 
size, preferredPrice,status, goodTillDate, submittedTime,userid )
values ('limit', 'AUD', 'SGD', 100, 2.00, 'FILLED', '2020-07-30 23:04:13', '2017-08-01 23:10:13','client1','ecc1e8561be5da89c604305807c3ab46f5c1e48f');