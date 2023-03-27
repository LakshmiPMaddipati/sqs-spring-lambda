# SpringBootSQSExample
Example of Spring Boot AWS SQS Producer and Consumer

## Introduction

This is a project for showing how to use AWS SQS with Spring Boot 3.0.4. This is recent as of 3/11/2023 and uses specific package version numbers.

The project needs to use

```
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-messaging</artifactId>
            <version>5.3.25</version>
        </dependency>
```

Other than that it should work fine with latest versions as of 3/11/2023 (see the pom.xml)

Jackson to convert objects to and from string/json which AWS requires

## 2.7.3 Spring Boot Starter Parent

If you want to use a Spring Starter before Version 3.0.4 such as the following:

```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.3</version>
        <relativePath /> <!-- lookup parent from repository -->
    </parent>
```


