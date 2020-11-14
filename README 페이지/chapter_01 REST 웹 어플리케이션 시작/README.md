# REST 웹 어플리케이션 시작

## 참조 페이지
https://spring.io/guides/gs/rest-service/

## 학습 진행 순서
1. build.gradle 환경설정
2. Domain 생성
3. REST Controller 생성

## build.gradle 환경설정

스프링 부트를 REST 웹 어플리케이션에 대한 의존성을 추가한다
```
dependencies {
    // 스프링 부트를 REST 웹 어플리케이션에 대한 의존성
    implementation 'org.springframework.boot:spring-boot-starter-web' 
```

## Domain 생성
1. 생성 위치 아래와 같다.
```
설명: {root project}/src/main/java/{사용하는 패키지명}/domain/
예시: /src/main/java/me/parkprin/domain/
```
2. src/main/me/parkprin/domain/Gretting.java 생성
3. Gretting.java 

```
package {패키지명}/domain;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent(){
        return content;
    }
}
```
코드 내용: Greeting 생성시 id와 content를 인자로 받고 Getter 메소드를 통해서
값을 전달한다

## REST Controller 생성
1. 생성 위치 아래와 같다.
```
설명: {root project}/src/main/java/{사용하는 패키지명}/controller/
예시: /src/main/java/me/parkprin/controller/
``` 
2. src/main/me/parkprin/webservice/GreetingController.java 생성
3. GreetingController.java

```
package me.parkprin.restervice;

import me.parkprin.domain.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController // 해당 어노테이션을 선언하면 스프링부트에서 제공하는 REST에 설정을 해당 클래스에 적용이 된다
public class GreetingController {

    private static final String template = "안녕 %s!";
    private final AtomicLong counter = new AtomicLong(); // 동적으로 Id 값을 증가 시키는 기능이 있음

    // GetMapping 어노테이션 선언시 해당 메소드는 REST의 GET 방식의 API 연동에 설정이 된다
    // RequestParam 어노테이션은 해당 GET API 진입시 들어온 파라미터 값중 name에 대한 내용을 바인딩 하며
    // 값이 존재하지 않을시 default 값으로 World를 삽입한다
    @GetMapping("/greeting") 
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        // counter.incrementAndGet은 동적으로 Long 타입의 아이디 값을 부여와 동시에 증가시킨다
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}

```