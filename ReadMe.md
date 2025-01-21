# CloneGram Project

## 🎯목표
대기업 SNS의 기능을 구현해보는 토이 프로젝트를 진행해보려 한다. 
Spring Boot를 관계형 데이터 베이스와 연동하여 프로젝트를 진행해본 적이 없는데
이번에 나름 사이즈가 있는 프로젝트를 진행해보면서
스프링 부트에 대한 이해도를 높혀보려고 한다.

## ⚙️Tech Stack
### Backend
* **Java 21**
* **SpringBoot 3.2.3**
* **Spring Security** - Authentication
* **MariaDB** - UserData
* **Redis** - TempData (마킹)
* **MyBatis** - DataBinding
* **Cloudinary** - ImageHosting
### Frontend
* **ThymeLeaf** - TemplateEngine
* **javascript** - Script
* **Jquery** - ScriptLibrary
### VCS
* **Git**
### Server
* **Ubuntu 22.0.4 (OracleCloudInstance)**
* **Nginx** - ProxyPass


## 🗒️Record
1. [CloneGram 토이프로젝트](https://velog.io/@qwerty55558/CloneGram-Project-cympbvsd)
2. [CloneGram 토이프로젝트 - 02](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-02)
3. [CloneGram 토이프로젝트 - 03](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-03)
4. [CloneGram 토이프로젝트 - 04](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-04)
5. [CloneGram 토이프로젝트 - 05](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-05)
6. [CloneGram 토이프로젝트 - 06](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-06)
7. [CloneGram 토이프로젝트 - 07 (코드 리뷰, 중간 점검)](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-07-%EC%BD%94%EB%93%9C-%EB%A6%AC%EB%B7%B0-%EC%A4%91%EA%B0%84%EC%A0%90%EA%B2%80)
8. [CloneGram 토이프로젝트 - 08](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-08)
9. [CloneGram 토이프로젝트 - 09](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-09)
10. [CloneGram 토이프로젝트 - 10](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-10)
11. [CloneGram 토이프로젝트 - 11](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-11)
12. [CloneGram 토이프로젝트 - 12](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-12)
13. [CloneGram 토이프로젝트 - 13](https://velog.io/@qwerty55558/CloneGram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-13)
14. [CloneGram 토이프로젝트 - 14 (완)](https://velog.io/@qwerty55558/Clonegram-%ED%86%A0%EC%9D%B4%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-14-%EC%99%84) (시연, 구현, 디버그 등등 총 정리글의 링크입니다.)

## ✅주요 기능
### **계정**
- 로그인
    - 아이디, 비밀번호로 로그인 가능
- 회원가입
    - 검증을 거친 데이터로 회원가입 가능
    - 이메일 중복, 아이디 중복 불허
    - 비밀번호는 암호화하여 저장
    - 인증 이메일 전송으로 유효성 확인
- 비밀번호 찾기
    - 이메일을 입력하면 해당 이메일로 임시 비밀번호 발급
- 프로필
    - 프로필사진, 이름, 비밀번호수정, 자기소개 수정 가능
    - 팔로워, 팔로잉 유저 확인 가능
    - 게시물 확인 가능
### **탭 별 기능**
- 홈
    - 관련 계정 (팔로우) 의 게시글을 출력
- 검색
    - 전체 게시글을 랜덤으로 출력
    - 검색어를 입력하면 포스트의 캡션에 키워드가 포함된 게시글을 필터링하여 출력
- 탐색
    - 미구현
- 릴스
    - 다른 유저들이 등록한 릴스를 감상할 수 있음
    - 스트리밍 알고리즘을 통해 사용자 경험 향상
    - 영상 데이터를 등록할 수 있음
- 메시지
    - emitter 를 활용한 비동기 메시징으로 타 유저와 대화 구현
    - 오프 캔버스를 통해 메시지를 보내고 확인할 수 있음
- 알림
    - emitter 를 활용한 실시간 알림 서비스 구현
    - 각 메시지 별로 해당하는 페이지 이동 가능
- 만들기
    - 피드를 만들 수 있음
    - 이미지와 텍스트 데이터를 통해 게시글 작성 가능
- 대시보드
    - Grade 가 admin 인 계정만 접근 가능
    - 기능 미구현
- 프로필
    - 자신의 프로필에 접근 및 수정, 삭제 가능
    - 자신이 작성한 게시글 접근 및 수정, 삭제 가능
    - 자신이 업로드한 릴스 접근 및 수정, 삭제 가능
    - 팔로워, 팔로잉 목록 확인 가능
### **상호작용**
- 팔로우
    - 상대 유저를 팔로우 하고 이를 관리할 수 있음
- 게시글
    - 댓글로 자신의 의견을 남길 수 있음
    - 좋아요로 게시글에 대한 생각을 남길 수 있음
    - 상대 프로필에서 영상 게시글, 사진 게시글을 확인할 수 있음
- 알림
    - 알림을 통해 타 유저와 소통함을 실시간으로 알 수 있음
- 메시지
    - 팔로우를 할 경우 상대에게 메시지를 보냄으로 소통할 수 있음
### **etc.**
- spring security 를 통한 권한별 접근제어
- spring security 가 지원하는 세션 인증정보를 통해 구현한 비즈니스 로직으로 인증 없는 요청 원천 차단
- 게시글 인피니티 스크롤 구현
- properties 를 통한 국제화 적용
- bootstrap 을 이용한 반응형 웹 페이지 구현
- 라즈베리 OS, ubuntu 등등 리눅스 기반 호스팅으로 서버 구현

## ERD 구조

![스크린샷 2025-01-21 045150](https://github.com/user-attachments/assets/ab421980-3aea-40e2-8210-78e6ea59b03d)


> [ERD Cloud 링크](https://www.erdcloud.com/d/3Pxfs5mR8ZKXuQvGM)




## 🔚엔드포인트 명세
<table>
    <tr>
        <td><b>Domain</b></td>
        <td><b>URL</b></td>
        <td><b>Method</b></td>
        <td><b>Description</b></td>
    </tr>
    <tr>
        <td>Post</td>
        <td>/post</td>
        <td>GET</td>
        <td>포스트 페이지 이동</td>
    </tr>
    <tr>
        <td></td>
        <td>/delete/post</td>
        <td>POST</td>
        <td>페이지 삭제 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/likey</td>
        <td>POST</td>
        <td>라이크 액션 반영</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/likey/info</td>
        <td>POST</td>
        <td>라이크 수 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/posts</td>
        <td>GET</td>
        <td>랜덤 포스트 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/posts</td>
        <td>POST</td>
        <td>포스트 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/posts/recommended</td>
        <td>POST</td>
        <td>팔로우 하는 사용자 포스트 조회</td>
    </tr>
    <tr>
        <td></td>
        <td>/update/post</td>
        <td>POST</td>
        <td>포스트 수정 요청</td>
    </tr>
    <tr>
        <td>User</td>
        <td>/myreels</td>
        <td>GET</td>
        <td>릴스 수정 페이지로 이동</td>
    </tr>
    <tr>
        <td></td>
        <td>/profile</td>
        <td>GET</td>
        <td>프로필 조회 페이지 이동</td>
    </tr>
    <tr>
        <td></td>
        <td>/profile/edit</td>
        <td>GET</td>
        <td>프로필 수정 페이지 이동</td>
    </tr>
    <tr>
        <td></td>
        <td>/profile/edit</td>
        <td>POST</td>
        <td>프로필 수정 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/reels</td>
        <td>GET</td>
        <td>릴스 목록 페이지 이동</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/myfollower</td>
        <td>GET</td>
        <td>팔로워 유저 데이터 요청/td>
    </tr>
    <tr>
        <td></td>
        <td>/find/myfollowing</td>
        <td>GET</td>
        <td>팔로잉 유저 데이터 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/follow</td>
        <td>POST</td>
        <td>유저 팔로우 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/unfollow</td>
        <td>POST</td>
        <td>유저 언팔로우 요청</td>
    </tr>
    <tr>
        <td>Login</td>
        <td>/find/password</td>
        <td>GET</td>
        <td>패스워드 찾기 페이지 이동</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/password</td>
        <td>POST</td>
        <td>패스워드 찾기 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/login</td>
        <td>GET</td>
        <td>로그인 페이지 이동</td>
    </tr>
    <tr>
        <td></td>
        <td>/logout</td>
        <td>GET</td>
        <td>스프링 시큐리티에 의해 처리되는 요청 (실제 값 X)</td>
    </tr>
    <tr>
        <td></td>
        <td>/sign</td>
        <td>GET</td>
        <td>회원가입 페이지 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/sign</td>
        <td>POST</td>
        <td>회원가입 요청</td>
    </tr>
    <tr>
        <td>Error</td>
        <td>/error/redirect</td>
        <td>GET</td>
        <td>접근 거부된 페이지 알림(alert 출력, 접근 이전 페이지로 redirect 처리)</td>
    </tr>
    <tr>
        <td>Comment</td>
        <td>/comment</td>
        <td>POST</td>
        <td>댓글 등록 요청</td>
    </tr>
    <tr>
        <td>Video</td>
        <td>/myreels/list</td>
        <td>POST</td>
        <td>자신의 영상 데이터 조회</td>
    </tr>
    <tr>
        <td></td>
        <td>/reels/list</td>
        <td>POST</td>
        <td>해당 유저의 영상 데이터 조회</td>
    </tr>
    <tr>
        <td></td>
        <td>/video/delete</td>
        <td>POST</td>
        <td>영상 삭제 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/video/list</td>
        <td>POST</td>
        <td>랜덤 영상 데이터 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/video/null</td>
        <td>GET</td>
        <td>empty 비디오 출력용</td>
    </tr>
    <tr>
        <td></td>
        <td>/video/update</td>
        <td>POST</td>
        <td>영상 게시물 수정 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/video/upload</td>
        <td>POST</td>
        <td>영상 게시물 등록 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/video/{videoId}</td>
        <td>GET</td>
        <td>영상 스트리밍 데이터 요청</td>
    </tr>
    <tr>
        <td>Email</td>
        <td>/checkCode</td>
        <td>POST</td>
        <td>이메일 인증 코드 확인 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/sendEmail</td>
        <td>POST</td>
        <td>인증 이메일 전송 요청</td>
    </tr>
    <tr>
        <td>Message</td>
        <td>/find/message</td>
        <td>POST</td>
        <td>유저간 메시지 데이터 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/send/message/new</td>
        <td>POST</td>
        <td>메시지 전송</td>
    </tr>
    <tr>
        <td>Upload</td>
        <td>/upload/postpic</td>
        <td>POST</td>
        <td>사진 데이터 전송</td>
    </tr>
    <tr>
        <td></td>
        <td>/upload/profilepic</td>
        <td>POST</td>
        <td>프로필 사진 데이터 전송</td>
    </tr>
    <tr>
        <td>Validation</td>
        <td>/validation/email</td>
        <td>POST</td>
        <td>이메일 유효성 체크</td>
    </tr>
    <tr>
        <td></td>
        <td>/validation/id</td>
        <td>POST</td>
        <td>아이디 유효성 체크</td>
    </tr>
    <tr>
        <td>Notifications</td>
        <td>/delete/notifications</td>
        <td>GET</td>
        <td>알림 삭제 요청</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/notifications</td>
        <td>GET</td>
        <td>알림 조회</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/notifications/count</td>
        <td>GET</td>
        <td>알림 갯수 조회</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/notifications/page</td>
        <td>POST</td>
        <td>알림 데이터 변환 후 조회</td>
    </tr>
    <tr>
        <td>Emitter</td>
        <td>/delete/emitter/messages</td>
        <td>GET</td>
        <td>메시지 emitter 삭제</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/emitter/messages</td>
        <td>GET</td>
        <td>메시지 emitter 조회</td>
    </tr>
    <tr>
        <td></td>
        <td>/find/emitter/notifications</td>
        <td>GET</td>
        <td>알림 emitter 조회</td>
    </tr>
    <tr>
        <td>Debug</td>
        <td>/hello</td>
        <td>GET</td>
        <td>테스트용 컨트롤러</td>
    </tr>
    <tr>
        <td>Admin</td>
        <td>/dashboard</td>
        <td>GET</td>
        <td>대시보드 페이지 이동</td>
    </tr>
    <tr>
        <td>Home</td>
        <td>/home, /</td>
        <td>GET</td>
        <td>홈 페이지 이동</td>
    </tr>
</table>

