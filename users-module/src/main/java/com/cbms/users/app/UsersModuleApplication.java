package com.cbms.users.app;

import com.cbms.users.app.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class UsersModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersModuleApplication.class, args);
    }

}
