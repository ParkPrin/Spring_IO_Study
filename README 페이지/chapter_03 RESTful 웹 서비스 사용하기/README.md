# RESTful 웹 서비스 사용하기

## 참조 페이지
https://spring.io/guides/gs/consuming-rest/

## RESTful 웹 서비스 사용하기 개념정리
아래와 같은 json 값을 내가(우리가) 만든 서버에서 받아서
사용하고 싶다고 하자

https://gturnquist-quoters.cfapps.io/api/random

```
{
   type: "success",
   value: {
      id: 10,
      quote: "Really loving Spring Boot, makes stand alone Spring apps easy."
   }
}
```

받는 절차를 생각해보면 다음과 같을 것이다.
1. RESTful API를 콜한다.
2. 위의 데이터를 받는다.
3. 받은 데이터를 객체로 변환하여 사용한다.

위에서 언급한 2~3번에 대한 내용을 다루어보겠다.
나는 Spring.io에서 작성한 예제 시나리오와 달리
내가 현재 만든 스프링부트에서 특정 URL을 호출시
https://gturnquist-quoters.cfapps.io/api/random을
콜한 후 데이터를 받아와서 변경작업 후 화면에 반환하는
시나리오로 진행하였다.

## 학습 진행 순서
1. build.gradle 환경설정(생략, [chapter_01 REST 웹 어플리케이션 시작](https://github.com/ParkPrin/Spring_IO_Study/tree/master/README%20%ED%8E%98%EC%9D%B4%EC%A7%80/chapter_01%20REST%20%EC%9B%B9%20%EC%96%B4%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98%20%EC%8B%9C%EC%9E%91) 참조)
2. Domain 생성
3. REST Controller 생성
4. Service 생성

## Domain 생성

Domain 생성에 앞서 타서버에서 받을 JSON 데이터 형태 다시한번 보자

```
{
   // type은 String 타입
   type: "success", 
   // value 라는 타입은 존재하지 않으므로 Value라는 도메인을 하나 더 만들어야 한다.
   value: {
      //id는 long이나 int 타입
      id: 10, 
      // quote는 String 타입
      quote: "Really loving Spring Boot, makes stand alone Spring apps easy."
   }
}
```
반환 받을 데이터의 형태와 같은 형태의 도메인을 만들어야 한다.
: Quote, Value

1. 생성 위치 아래와 같다.
```
설명: {root project}/src/main/java/{사용하는 패키지명}/domain/
예시: /src/main/java/me/parkprin/domain/
```
2. src/main/me/parkprin/domain/Quote.java, 
src/main/me/parkprin/domain/Value.java 생성
3. Quote.java 

```
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// JsonIgnoreProperties는 REST 방식의 응답시 특정 프로퍼티를 보이지 않도록 처리하는 어노테이션이다
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    //getter, setter, toString을 기본적으로 명시한다

    private String type;
    private Value value;

    public Quote() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}

```

Value.java

```
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// JsonIgnoreProperties는 REST 방식의 응답시 특정 프로퍼티를 보이지 않도록 처리하는 어노테이션이다
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

    //getter, setter, toString을 기본적으로 명시한다

    private Long id;
    private String quote;

    public Value() {}

    public Long getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                '}';
    }
}
```

## REST Controller 생성
1. 생성 위치 아래와 같다.
```
설명: {root project}/src/main/java/{사용하는 패키지명}/controller/
예시: /src/main/java/me/parkprin/controller/
``` 
2. src/main/me/parkprin/webservice/QuoteController.java 생성
3. QuoteController.java

```
 
 import me.parkprin.domain.Quote;
 import me.parkprin.service.QuoteService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RestController;
 
 import javax.websocket.server.PathParam;
 
 @RestController
 public class QuoteController {
 
     // 서비스 처리하는 영역
     @Autowired
     QuoteService quoteService;
 
     @GetMapping("/quote")
     public Quote quoteGetRestApiCall(@PathParam(value = "type")String type){
        // 서비스 처리하는 영역으로 파라미터 전달
         return quoteService.getQuoteAfterUpdateTask(type);
     }
 }

```

GreetingController와 비교하면 QuoteController는 비지니스 로직처리하는 것을
QuoteService 영역과 구분하였으며, 비즈니스 로직에서 필요한 인자값을 
QuoteService로 주입한다.

## Service 생성

```

import me.parkprin.domain.Quote;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {
    // 로그처리 객체 생성
    private static final Logger log = LoggerFactory.getLogger(QuoteService.class);

    // 외부 API 통신에서 사용하는 RestTemplate 객체
    private RestTemplate restTemplate;

    // @Autowired를 사용할 경우 Spring framework에서 객체 생명주기 관리를 함(생성 및 소멸)
    // RestTemplate를 사용하기 위해서 아래와 같이 생성자에 RestTempateBuild를 인자로 받아야 함
    @Autowired
    public QuoteService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Quote getQuoteAfterUpdateTask(String type) {

        // restTemplate.getForObject({Url 주소}, {mapping 할 객체 클래스});
        Quote quote = restTemplate.getForObject(
                "https://gturnquist-quoters.cfapps.io/api/random",
                Quote.class
                );
        if (StringUtils.isNotEmpty(type)) {
            log.info(quote.toString());
            log.info("type 변경전: " + quote.getType());
            quote.setType(type);
            log.info("type 변경완료: " + quote.getType());
            log.info(quote.toString());
        } else {
            log.info("변경할 type이 지정되지 않았습니다");
            log.info(quote.toString());
        }
        return quote;
    }
}

```