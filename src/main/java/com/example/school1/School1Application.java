package com.example.school1;

import com.example.school1.model.User;
import com.example.school1.model.UserType;
import com.example.school1.reposotory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class School1Application extends WebMvcConfigurerAdapter implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(School1Application.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
   public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.findOneByEmail("admin@mail.com");
        if (user == null) {
            User admin = User.builder()
                    .name("Admin")
                    .surname("Admin")
                    .email("admin@mail.com")
                    .password(new BCryptPasswordEncoder().encode("1915"))
                    .userType(UserType.ADMIN).build();
            userRepository.save(admin);
        }
    }

}
