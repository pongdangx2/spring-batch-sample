# spring-batch-sample

- Spring Batch를 공부하며 만들어본 Sample 프로젝트입니다.

## 기술 스택
- JDK : 17
- Spring Boot : 3.1.4
- mysql : 8.1.0
- Spring Batch : 5.0.3

## Job Repository

- 배치의 메타데이터는 mysql db에 저장합니다.
- Meta data의 스키마는 Spring batch 버전에 따라 달라질 수 있습니다.<br>
> https://docs.spring.io/spring-batch/docs/current/reference/html/schema-appendix.html 

