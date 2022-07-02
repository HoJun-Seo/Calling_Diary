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
    startdate varchar(10) not null,
    enddate varchar(10) not null,
    eventdesc varchar(100) not null
);

-- create table memberevent(


-- );

select json_object("eventid",eventid, "title",title, "startdate",startdate, "enddate",enddate, "eventdesc",eventdesc) from event where userid='sas6659';



delete from member where userid="sas6659";
select * from member;
select * from event;
alter table member add uid varchar(100);