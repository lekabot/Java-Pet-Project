package edu.school21.app.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("!test")
@EnableJpaRepositories(
        basePackages = "edu.school21.app.repository.past",
        entityManagerFactoryRef = "s3EntityManagerFactory",
        transactionManagerRef = "s3TransactionManager"
)
public class S3DatabaseConfig {

    @Autowired
    private Environment env;

    @Bean(name = "s3DataSource")
    public DataSource s3DataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.datasource.s3.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.s3.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.s3.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.s3.password"));

        dataSource.setMaximumPoolSize(10);
        dataSource.setConnectionTimeout(30000);

        return dataSource;
    }

    @Bean(name = "s3EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean s3EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("s3DataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("edu.school21.app.models.past")
                .persistenceUnit("s3")
                .properties(hibernateProperties())
                .build();
    }

    @Bean(name = "s3TransactionManager")
    public PlatformTransactionManager s3TransactionManager(
            @Qualifier("s3EntityManagerFactory") LocalContainerEntityManagerFactoryBean s3EntityManagerFactory) {
        return new JpaTransactionManager(s3EntityManagerFactory.getObject());
    }

    private Map<String, Object> hibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.dialect", env.getProperty("spring.jpa.s3.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.s3.hibernate.ddl-auto"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.s3.show-sql"));
        return properties;
    }
}

