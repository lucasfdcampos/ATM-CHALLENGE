package br.com.ibm.challenge.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author lucas
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("br.com.ibm.challenge")
public class SpringRootConfig {
}
