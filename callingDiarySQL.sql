use calling_diary;

create table member(
	userid varchar(20) not null primary key,
    passwd varchar(50) not null,
    nickname varchar(20) not null,
    phonenumber varchar(30) not null,
    uid varchar(100) not null,
    memberdesc varchar(100)
);

create table event(
	eventid BIGINT not null auto_increment primary key,
    userid varchar(20) not null,
    foreign key (userid) references member (userid)
		on update cascade
        on delete cascade,
	title varchar(30) not null,
    start varchar(10) not null,
    end varchar(10) not null,
    eventdesc varchar(100) not null
);

create table smsevent(
    eventid BIGINT not null,
    groupid varchar(100) not null,
    reservationTime varchar(10) not null,
    messageText varchar(100) not null,
    start varchar(10) not null,
    phonenumber varchar(30) not null,
    constraint PK_smsevent_eventid_groupid primary key (eventid, phonenumber)
);

alter table event change startdate start varchar(10);
alter table event change enddate end varchar(10);
alter table event change textcolor textColor varchar(10);
alter table event add favoritestatus varchar(10);
alter table event add textcolor varchar(10);
alter table event add color varchar(10);
alter table event add sms varchar(10);

select json_object("eventid",eventid, "title",title, "startdate",startdate, "enddate",enddate, "eventdesc",eventdesc) from event where userid='sas6659';



delete from member where userid="sas6659";
delete from smsevent where eventid=29;



select * from member;
select * from event;
select * from smsevent;
alter table member add uid varchar(100);

select favoritestatus from event where eventid=18;