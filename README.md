# Calling Diary

## 프로젝트 설명
특정 날짜에 작성해둔 일정이 있을 때, 사용자가 정해둔 시간에 맞춰 해당 일정에 대한 알림 문자를 발송해주는 일정 알림 웹 사이트 입니다.
- 원하는 날짜를 지정하여 시간 별로 원하는 일정을 작성할 수 있습니다.
- 각 일정 별로 휴대폰 문자 메시지를 통해 원하는 시간대에 알림을 받을지 말지 결정할 수 있습니다.

## 프로젝트 참여자
- 서호준
  - 이메일(1) : ghwns6659@gmail.com
  - 이메일(2) : ghwns6659@naver.com
  - 작업 내역

## 기술 스택
- 프론트 엔드
  - HTML/CSS
  - BootStrap
  - Javascript
  - Ajax
  - jQuery
  - Thymeleaf

- 백엔드
  - JAVA(JDK >= 11)
  - Spring Boot Framework(Gradle)
  - MySQL
  - JPA

- 사용 툴
  - IntelliJ IDEA Community Version
  - Visual Studio Code
  - MySQL WorkBench

- 버전 관리
  - GitHub
 
## API 명세
- http://localhost:8080/swagger-ui.html
- 위 링크는 Repository 를 다운 받으신 후 스프링 부트의 내부 톰캣을 구동시키면 접근이 가능합니다.

## 개발 현황(2022.06.07 ~)
### 2022.06.07
- ver 1.0.0
    - 부트스트랩 활용 웹 사이트 메인 인덱스 페이지 생성
### 2022.06.17
- ver 1.1.0
    - 회원가입 기능 구현
    - 로그인 기능 구현
    - 로그아웃 기능 구현
    - 아이디 찾기, 비밀번호 찾기 기능 구현
- ver 1.1.1
    - 로그아웃 시 발생하던 Request method 'DELETE' not supported 문제 해결
