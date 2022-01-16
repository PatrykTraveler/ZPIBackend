package com.zpi.transfergenerator.configuration;

import com.zpi.transfergenerator.model.Account;
import com.zpi.transfergenerator.payment.AccountProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:account.properties")
public class AccountConfiguration {

    private final Environment environment;

    public AccountConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public AccountProvider accountProvider() {
        final var account = new Account(environment.getProperty("iban"), environment.getProperty("name"));
        return new AccountProvider(account);
    }
}
