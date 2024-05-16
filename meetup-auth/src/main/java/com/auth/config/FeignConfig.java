package com.auth.config;

import feign.Contract;
import feign.RetryableException;
import feign.Retryer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Common configuration for Feign clients.
 *
 * @author David Sapozhnik
 */
@EnableFeignClients(basePackages = "com.auth.client")
@Configuration
public class FeignConfig {

    private static final int RETRY_ATTEMPTS = 5;

    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }

    @Bean
    public FeignRetryer feignRetryer() {
        return new FeignRetryer(RETRY_ATTEMPTS);
    }

    @Bean
    public feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.NONE;
    }

    /**
     * Custom {@link Retryer} implementation to enable token renewal logic.
     *
     * @author Maksym Shamanovskyi
     */
    public static class FeignRetryer implements Retryer {

        private static final Logger LOG = LoggerFactory.getLogger(FeignRetryer.class);

        private final int retryAttempts;
        private int attempt;

        FeignRetryer(int retryAttempts) {
            this.retryAttempts = retryAttempts;
            this.attempt = 1;
        }

        @Override
        public void continueOrPropagate(RetryableException e) {
            if (attempt++ >= this.retryAttempts) {
                LOG.error("Request has failed after {} retries", retryAttempts);
                throw e;
            }
        }

        @Override
        public Retryer clone() {
            return new FeignRetryer(retryAttempts);
        }
    }
}
