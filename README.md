# <strong>💻 MovieToGather</strong>


## 📑 개요


- **개발 기간** : 24.02.26 ~ 24.04.05 
- **프로젝트 이름** : MovieToGather
- **프로젝트 설명 :** 영화를 마음이 맞는 사람끼리 함께 즐기기 위한 플랫폼입니다!


## 🤺 MovieToGather Team

- <strong>팀장</strong> 오재영
    - [github](https://github.com/JYOH3246)
    - 역할
      - PM
      - 모니터링 서버
      - 지도 API
      - 테스트 코드
      - 프론트엔드 화면 구성
- <strong>부팀장</strong> 박재은
    - [github](https://github.com/Re-eun)
    - 역할
      - 백엔드 서버 CI/CD
      - 영화 검색 API
- <strong>팀원</strong> 김철학
    - [github](https://github.com/siadeu87)
    - 역할
      - 전반적인 검색 기능
      - 베스트 리뷰
      - 좋아요 기능
      - 채팅
- <strong>팀원</strong> 강군호
    - [github](https://github.com/9nh5)
    - 역할
      - 소셜 로그인
      - 그 외 회원가입 관련 전반 
- <strong>팀원</strong> 지혜영
    - [github](https://github.com/happynslow)
    - 역할
      - 리뷰 CRUD
      - 모임 CRUD
## **📚기술스택**
<div>
  <img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
  <img src="https://img.shields.io/badge/github-000000?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=Intellijidea&logoColor=white">
  <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
  <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">
  <img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">
  <img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">
  <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  <img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white">
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
  <img src="https://img.shields.io/badge/vuedotjs-4FC08D?style=for-the-badge&logo=vuedotjs&logoColor=white">
  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
  <img src="https://img.shields.io/badge/quasar-050A14?style=for-the-badge&logo=quasar&logoColor=white">
  <img src="https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white">
  <br>
</div>

### **Backend**

- Spring Boot: 3.2.2
- Kotlin: 1.9.22
- Data
    - Spring JPA: 3.2.3
    - QueryDsl: 5.0.0
- Security
    - Spring Security: 6.2.0
    - JWT: io.jsonwebtoken:jjwt-api:0.12.3
    - Oauth 2.0
### **DB**
- PostgreSql : 16
- Redis
### **Monitoring**
- Prometheus
- Grafana
### **Frontend**
- Html5
- CSS
- JavaScript
- Vue.js 3
- Bootstrap
- Quasar

### **Collaboration**

- Git, GitHub Issue, Slack

### **의사결정**
- **Prometheus & Grafana**
    - 오픈 소스인 점이 중요하게 작용
    - Spring Actuator와 Spring Metrics의 존재로 사용이 편리한 점
    - 다양한 형태의 메트릭을 수집하는 exporter도 역시 오픈 소스인 점
    - 저장 공간이 모자랄 시에는 하드웨어 용량을 늘려야 하지만, 현재 프로젝트에서 수집되는 메트릭이 양이 그렇게 많지는 않을 것이라고 판단한 점
    - Spring에서 일반적으로 사용할 수 있는 Dashboard UI를 제공한다는 점
- **Github Actions**
    - Github 저장소를 사용해온 친숙함이 가장 크게 작용
    - 추가적인 설치 필요 없이 Github 환경에서 이용 가능
    - 특정 사용량까지 무료로 제공
- **Docker**
    - 배포 프로세스의 간소화
    - 애플리케이션의 구성과 종속성을 캡슐화하기 때문에 환경의 일관성을 보장할 수 있는 점
    - 프로젝트에 사용하는 백엔드, 프론트, 데이터베이스를 단일 서버에서 실행 가능한 점
    - 개인 사용자로서 무료 버전 활용 가능
- **Spring JPA**
    - MyBatis는 쿼리문을 직접 작성해야 하기 때문에 객체와 쿼리문을 함께 관리해주어야 하는 반면 JPA는 쿼리를 만들지 않아도 된다는 점
    - JPA는 DTO까지 수정해야 하는 MyBatis와 달리 데이터 정보만 바뀌어도 객체만 수정해 주면 된다는 점
    - 자바 진영에서 사용하는 표준 ORM이라는 점
    - JPA는 복잡한 쿼리를 해결하기 힘들지만, 이런 JPA의 단점을 QueryDsl같은 기술이 해소해 줄 수 있다는 점
- **QueryDsl**
    - QueryDsl과 Criteria는 JPQL을 더 잘 사용할 수 있게 해 주고, 컴파일 시점에 에러를 발생시키게 해 주는 JPQL 빌더
    - Criteria는 QueryDsl에 비해 학습 난이도도 높으면서, 코드의 가독성 측면에서도 좋지 않을 뿐더러
    - 팀원들이 QueryDsl에 익숙한 상황에서 Cretiral의 특장점을 찾지 못해서 이용
- **Spring Security**
    - SecurityContextHolder를 통해 인가 처리를 수월하게 진행할 수 있는 점
    - PasswordEncoder를 통해 패스워드 암호화를 손쉽게 구현할 수 있는 점
- **JWT**
    - 웹 표준을 따르기 때문에 다양한 언어로 구현이 가능
    - Http를 Stateless하게 유지할 수 있음
    - 다른 인증 방식에 비해, 서비스를 확장하는 측면에서 유리함
- **PostgreSql**
    - MySql이 PostgreSql에 비해 데이터 읽기 성능에 강점이 있어 사실 MySql을 이용해도 되는 프로젝트라고 생각
    - 그러나 PostgreSql의 경우, 대규모 데이터 관리나 트랜잭션 등을 관리하기 더 수월한 경향이 존재하기 때문에 서비스가 확장될 시에 더 유리한 측면이 있다고 판단했으며
    - 팀원들이 MySql보다 PostgreSql에 더 익숙한 상황에서 MySql을 사용해야 할 정도의 읽기 성능이 요구되지는 않는다고 판단
- **WebSocket**
    - 송신만 할경우 HTTP 프로토콜으로 충분
    - 채팅 시스템은 송수신이 되어야 되기때문에 폴링, 롱폴링, 웹소켓등 수신 하는 기법을 같이 사용해야된다
    - 폴링, 롱폴링은 클라이언트가 서버에 새 메시지에 대한 요청을 보내고 응답을 받은 후 연결을 종료후 다시 요청하여 반복하는 형식이다
    - 웹소켓은 한번 연결이 되면 항구적이며 양방향으로 연결되면서 서버는 클라이언트에게 비동기적으로 메시지를 전송할수 있게 된다
    - 그래서 웹소켓 대신 HTTP를 사용할 이유가 없다고 생각
- **RestClient**
    - 이전에 사용해오던 HTTP Client 인 restTemplate 와 webClient 에 비하여 사용방법이 직관적이고 다른 의존성이 필요하지 않아 러닝커브가 적은 점
- **Redis**
    - In-Memory : 디스크(DB)를 조회하는 것 보다 빠른 작업 속도
    - Single Thread : 프로그램의 복잡도가 감소하고, 성능적인 면에서도 유리하기 때문에 보조적인 수단으로 사용하기에는 좋음
    - Key : Value 구조를 기반으로 한 다양한 자료구조를 지원
        - 다양한 자료구조를 기반으로 한 기능 구현에 장점이 있음
- **Vue.js**
    - React.js에 비해 상대적으로 러닝 커브가 완만한 점
    - Thymeleaf에 비해 동적인 화면 구현이 쉬운 점
    - **Quasar**
        - infinite scroll을 쉽게 구현할 수 있기에 이용
        - 다양한 컴포넌트를 사용할 수 있고 Vue.js에 특화되어 있으며 공식 문서가 이해하기 쉽게 쓰여 있는 점
    - **Bootstrap**
        - 백엔드 프로젝트의 중요도를 높게 잡았기에 Vue.js를 지원하는 템플릿을 사용
        - 해당 템플릿에 Bootstrap이 적용


## 주요기능

### 회원
- 회원가입/로그인/유저 정보 확인
- Spring Security 활용
- 소셜 로그인만으로 회원 관리 진행

### 리뷰
- 리뷰 Best 3
- 리뷰 검색하기
- 리뷰 작성/수정/삭제하기

### 모임
- 모임 검색하기
- 모임 작성/수정/삭제하기
- 같은 모임원들끼리 채팅하기


## 🏆 프로젝트 산출물

- [팀 노션](https://succulent-pie-aa0.notion.site/MovieToGather-ea44a5c7a745484f933a174e7d5f98da?pvs=4)
