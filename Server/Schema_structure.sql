create database Sakshatkar;
use Sakshatkar;
create table user(username varchar(15) NOT NULL, fname varchar(40), lname varchar(40), password varchar(64)
					,salt varchar(64), lastseen timestamp,addressline1 varchar(100), addressline2 varchar(100), city varchar(20),
					pincode int,ugroupid int,  primary key(username));

create table friendrequests(sendername varchar(15), recievername varchar(15), requesttime timestamp
					, primary key(sendername, recievername));

create table login(username varchar(15), ip varchar(30), status boolean, primary key(username));

create table contacts(username varchar(15), contactname varchar(15), primary key(username, contactname));

create table groups(groupid int, ownername varchar(15), groupname varchar(40), groupcapacity int, primary key(groupid));

create table partof(groupid int, username varchar(15), primary key(groupid, username));

create table profilepic(username varchar(15), pic mediumblob, primary key(username));

-- inSecondary -> true means in secondary storage
create table videomessage(messageid int, video longblob, inSecondary boolean, lastused timestamp , primary key(messageid));

create table imagemessage(messageid int, pic mediumblob, inSecondary boolean, lastused timestamp , primary key(messageid));

create table textmessage(messageid int, msg tinytext, inSecondary boolean, lastused timestamp , primary key(messageid));

-- viewStatus >> 0 -> sent , 1-> received by the recipient, 2 -> seen (to be added in future)
create table chat(type varchar(3),messageid int, sendername varchar(15), recievername int, sendtime timestamp,
						 primary key(type,messageid),viewStatus int(1));

create table files(fileid int, filelocation varchar(100), creationtime timestamp, primary key(fileid));

create table storedin(fileid int, type varchar(3), messageid int, primary key(fileid, type ,messageid));