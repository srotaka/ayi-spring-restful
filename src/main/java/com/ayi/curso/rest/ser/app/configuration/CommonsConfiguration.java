package com.ayi.curso.rest.ser.app.configuration;


// Packages and classes to import of springframework 5.x
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@ComponentScan(basePackages={"com.ayi.curso.rest.ser.app.configuration" +
        "com.ayi.curso.rest.ser.app.service" +
        "com.ayi.curso.rest.ser.app.mapper" +
        "com.ayi.curso.rest.ser.app.repository"})
public class CommonsConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}