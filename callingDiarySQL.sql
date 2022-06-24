use calling_diary;

create table member(
	userid varchar(20) primary key,
    passwd varchar(50),
    nickname varchar(20),
    phonenumber varchar(30)
);

delete from member where userid="sas6659";
select * from member;
alter table member add memberdesc varchar(100);