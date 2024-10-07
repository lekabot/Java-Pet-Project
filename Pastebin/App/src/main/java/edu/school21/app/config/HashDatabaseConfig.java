package edu.school21.app.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("!test")
@EnableJpaRepositories(
        basePackages = "edu.school21.app.repository.hash",
        entityManagerFactoryRef = "hashEntityManagerFactory",
        transactionManagerRef = "hashTransactionManager"
)
public class HashDatabaseConfig {

    @Autowired
    private Environment env;

    @Bean(name = "hashDataSource")
    @Primary
    public DataSource hashDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.datasource.hash.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.hash.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.hash.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.hash.password"));

        dataSource.setMaximumPoolSize(10);
        dataSource.setConnectionTimeout(30000);

        return dataSource;
    }

    @Primary
    @Bean(name = "hashEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean hashEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("hashDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("edu.school21.app.models.hash")
                .persistenceUnit("hash")
                .properties(hibernateProperties())
                .build();
    }

    @Primary
    @Bean(name = "hashTransactionManager")
    public PlatformTransactionManager hashTransactionManager(
            @Qualifier("hashEntityManagerFactory") LocalContainerEntityManagerFactoryBean hashEntityManagerFactory) {
        return new JpaTransactionManager(hashEntityManagerFactory.getObject());
    }

    private Map<String, Object> hibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.dialect", env.getProperty("spring.jpa.hash.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hash.hibernate.ddl-auto"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.hash.show-sql"));
        return properties;
    }
}
