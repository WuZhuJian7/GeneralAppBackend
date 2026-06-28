package com.general.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动类
 */
@SpringBootApplication
@MapperScan("com.general.app.module.*.mapper")
public class GeneralAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralAppBackendApplication.class, args);
        System.out.println();
        System.out.println("  (♥◠‿◠)ﾉﾞ  WUZHUJIAN 启动成功   ლ(´ڡ`ლ)ﾞ  ");
        System.out.println("  Swagger: http://localhost:8080/backend/swagger-ui.html");
        System.out.println();
    }
}
