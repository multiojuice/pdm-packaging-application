create table if not exists orders (order_ID integer primary key auto_increment, sender_ID integer not null, receiver_ID integer not null, is_prepaid bit default 0, cost decimal(20, 2), tracking_ID integer not null);
create table if not exists users (user_ID integer primary key auto_increment, name varchar(255) not null, is_premium bit default 0 not null, phone_number varchar(11), business_ID integer);
create table if not exists business (business_ID integer primary key auto_increment, name varchar(255) not null, address varchar(255) not null);
create table if not exists package (package_ID integer primary key, order_ID integer not null, shipping_status varchar(1) not null, weight integer, delivery_time integer, trait varchar(1));
create table if not exists traits (trait_ID varchar(1), name varchar(255));
create table if not exists tracking (tracking_ID integer primary key auto_increment, transport_ID varchar(1), current_location_ID varchar(1));
create table if not exists transport (transport_ID integer primary key auto_increment, type varchar(255) not null);
create table if not exists locations (location_ID integer primary key auto_increment, location_name varchar(255) not null);
create table if not exists stops (tracking_ID integer not null, location_ID integer not null, stop_num integer not null, primary key (tracking_ID,location_ID));

