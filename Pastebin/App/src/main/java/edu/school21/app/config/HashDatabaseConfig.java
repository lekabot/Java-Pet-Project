package edu.school21.app.config;

import edu.school21.app.repository.HashRepository;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = HashRepository.class,
        entityManagerFactoryRef = "hashEntityManagerFactory",
        transactionManagerRef = "hashTransactionManager"
)
public class HashDatabaseConfig {
    @Autowired
    @Qualifier("hashDataSource")
    private DataSource hashDataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean
    hasEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .withDataSource(hashDataSource)
                .packages()
    }
}
