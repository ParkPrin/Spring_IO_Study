# JWT(Json Web Token) 인증 기능 적용하기

## 참조 페이지
https://www.javainuse.com/spring/boot-jwt

## JWT 인증과 Security의 변천사


## 학습 진행 순서
1. build.gradle 환경설정
2. application.yml 파일 생성 및 시스템변수 추가
3. config 패키지 생성 
4. JwtTokenUtil 구현
5. JwtUserDetailsService 구현
6. JwtAuthenticationController 구현
7. JwtRequestFillter 구현
8. JwtAuthenticationEntryPoint 구현
9. WebSecurityConfig 구현
10. JWT 적용 후 결과확인

## uild.gradle 환경설정

```
    // spring에서 security를 사용하기 위해서 추가해야 하는 의존성
    implementation 'org.springframework.boot:spring-boot-starter-security'

    // JWT 처리를 하기위해서 필용한 의존성
    compile group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'

    // 인증과정 중 javax/xml/bind/DatatypeConverter 찾을 수 없어 발생하는 문제 해결책
    implementation "jakarta.xml.bind:jakarta.xml.bind-api:2.3.2"
    implementation "org.glassfish.jaxb:jaxb-runtime:2.3.2"
```

## application.yml 파일 생성 및 시스템변수 추가
1. 아래 경로에 application.yml 파일을 생성한다.
```
{root project}/src/main/resources/application.yml
```

2. 아래와 같이 시스템 변수를 입력하며, secret에 들어가는 변수는 임의적으로 입력해도 상관이 없다
```
spring:
  security:
    oauth2:
      secret: FwwAHhXddgv7Ry7F

```



## JWT 적용 후 결과확인
이전에 만들어 놓은 Greeting, Quote에 대한 API를 접근시
해당하는 값에 대해서 값을 제공하였따.
그러나 현재 JWT(Json Web Token) 적용한 경우, 우선적으로
JWT를 발급하는 API 외에 접근이 되지 않도록 설정하였다.
JWT를 발급받은 후 발급 받은 Token을 header에 넣은 상태에서
다른 API에 접근이 가능하다.

정리
1. http://localhost:8080/authenticate Post 방식으로
API 콜을 한다

입력해야 하는 json 데이터는 다음과 같다

![JWT 적용 후 결과확인 1단계](/img/readme/chapter04/JWT 적용 후 결과확인 1단계.png)
```
{
    "username" : "javainuse",
    "password" : "password"
}
```

2. authenticate API가 정상동작시 다음과 같은 형태로 Token이 발행된다.
![JWT 적용 후 결과확인 2단계](/img/readme/chapter04/JWT 적용 후 결과확인 2단계.png)
```
{
    "jwtToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2MDUzMzAwODMsImlhdCI6MTYwNTMxMjA4M30.F0bYd6TI4ySb41eel-gsttDdoAd3w5WagJdSSBGwibLdcaP9s-MtyWrR674PR9qf5UyXAuwg92lb2kGcGlj0ag"
}
```

3. 발급 받은 Token을 Headers 영역에 다음과 같이 입력한다
![JWT 적용 후 결과확인 3단계](/img/readme/chapter04/JWT 적용 후 결과확인 3단계.png)

```
key: Authorization
value: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2MDUyNTc1NjgsImlhdCI6MTYwNTIzOTU2OH0.ra--UhcBcv5CZnTyxuy71W75djSXIaAj2202Yv-XtJZ2nUH4qdzGkk3-QxSBccEJCGz7vUC7wdd2iyE8I0juCQ
```

4. 원하는 API Call을 수행한다.


