# 스프링 부트 환경구성

## 환경구성 순서
1. 프로젝트 생성
2. build.gradle 환경설정
3. Gradle 빌드
4. Spring Boot Application 클래스 생성


## 프로젝트 생성(인텔리제이 기준)
1. Gradle 선택
2. Java 선택(Java버전 선택)
3. 프로젝트 정보 입력
```
 1) Name: 프로젝트명
 2) Location: 프로젝트 생성할 물리적 위치
 3) GroupId: 프로젝트에서 사용할 패키지 그룹
 4) AtifactId
 5) Version: 프로젝트의 버전 정보 
```

## build.gradle 환경설정

아래와 같이 입력함

```
plugins {
    id 'org.springframework.boot' version '2.3.2.RELEASE'   // 스프링 부트 설정
    id 'io.spring.dependency-management' version '1.0.8.RELEASE'    // 스프링 관련 의존성관리 설정
    id 'java'   // Java 설정
}

group 'me.parkprinn'    // 프로젝트 생성시 입력한 GroupId
version '1.0-SNAPSHOT'  // 프로젝트 생성시 입력한 VersionId
sourceCompatibility = '11'  // 적용하는 JavaVersion

repositories {
    mavenCentral() // dependency를 가지고 오는 저장소 입력: Maven Central 이라는 Repository
}

dependencies {
    // 스프링 부트를 REST 웹 어플리케이션에 대한 의존성
    implementation 'org.springframework.boot:spring-boot-starter-web' 
    // 스프링 부트 테스트 코드 작성 환경 구성에 대한 의존성
    testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
}


// gradle build시 JUnit 테스트 진행하도록 하는 설정 
test {
    useJUnitPlatform()
}

``` 

## Gradle 빌드
좌측 상단 Gradle 클릭 -> 화살표가 돌아가는 아이콘 클릭
-> Gradle에 대한 환경구성 시작 및 의존성 다운로드 시작 

## Spring Boot Application 클래스 생성
1. 아래 경로로 Application 패키지를 만든다.
```
설명: {root project}/src/main/java/{사용하는 패키지명}/Application
예시: /src/main/java/me/parkprin/Application
```

2. Application 생성

```
package {사용하는 패키지명};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // 스프링부트를 적용하겠다는 어노테이션
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);     // 스프링부트에서 스프링 어플리케이션 실행
    }
}

```

