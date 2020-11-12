# Scheduling-Tasks

## Scheduling-Tasks 설명
Scheduling-Tasks는 서버에서 일정시간을 간격을 두고 처리하거나 특정 기능을
실행 할 때 쓰는 기술이다. Spring Boot에서는 두 가지 설정을 통해서
구현이 가능하다.

1. Scheduling-Tasks 할 클래스를 작성한다.
2. Application 클래스에서 @EnableScheduling 어노테이션을 삽입한다.  

## 참조 페이지
https://spring.io/guides/gs/scheduling-tasks/

## 학습 진행 순서
1. build.gradle 환경설정
2. ScheduledTasks 클래스 작성
3. Application 클래스에서 @EnableScheduling 어노테이션 삽입


## build.gradle 환경설정

Scheduling-Tasks에 대한 의존성을 추가한다
```
dependencies {
    // Scheduling-Tasks에 대한 의존성을 추가한다
    testImplementation 'org.awaitility:awaitility:3.1.2' 
```

## ScheduledTasks 클래스 작성
예제에서는 5초마다 현재 시간을 콘솔에 찍는 기능을 구현하였다. 
핵심 기능으로는 @Scheduled(fixedRate = 5000) 이다

```
package me.parkprin.schedulingtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
    // LoggerFactory를 통한 Logger 객체 생성
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    // 콘솔에 출력할 Date 형식 정의함
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // 스케줄링에 사용하는 어노테이션:Scheduled
    // fixedRate: 시간간격을 입력할 수 있다.
    // 밀리세컨드 기준이므로 5000은 5초를 의미한다.
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        // log.info() 안에는 콘솔에 찍을 내용을 입력한다. 
        // {} 안에는 dateFormat.format(new Date())에 대한 데이터 결과가 들어간다.
        log.info("The Time is now {}", dateFormat.format(new Date()));
    }
}

```

## Application 클래스에서 @EnableScheduling 어노테이션 삽입

```
package me.parkprin;

...
// scheduling 어노테이션에 대한 라이브러리 추가
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// scheduling-tasks 동작을 위해 넣어주어야 하는 어노테이션
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
여기에서는 @EnableScheduling과 import만 삽입하면 scheduling-tasks 설정이 반영이 된다

## 반영결과
반영 후 콘솔에 아래와 같은 일정한 시간이 찍힌다.
```
2020-11-12 14:54:08.298  INFO 38954 --- [   scheduling-1] m.p.schedulingtasks.ScheduledTasks       : The Time is now 14:54:08
2020-11-12 14:54:13.300  INFO 38954 --- [   scheduling-1] m.p.schedulingtasks.ScheduledTasks       : The Time is now 14:54:13
2020-11-12 14:54:18.299  INFO 38954 --- [   scheduling-1] m.p.schedulingtasks.ScheduledTasks       : The Time is now 14:54:18
2020-11-12 14:54:23.299  INFO 38954 --- [   scheduling-1] m.p.schedulingtasks.ScheduledTasks       : The Time is now 14:54:23
2020-11-12 14:54:28.300  INFO 38954 --- [   scheduling-1] m.p.schedulingtasks.ScheduledTasks       : The Time is now 14:54:28
```

## 활용범위
예제를 통해 5초마다 현재 시간을 콘솔에 찍는 기능을 볼수 있었다. 
이외에도 DB에 담겨있는 데이터를 정규적으로 확인하여 데이터유무에 따라
처리를 하는 기능이나, 특정시간을 간격을 두고 특정 서버에 메세지를 날리는 작업등에
활용할 수 있다.

