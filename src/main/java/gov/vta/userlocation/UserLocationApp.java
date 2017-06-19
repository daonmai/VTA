package gov.vta.userlocation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;


/**
 * Created by x210104
 */
@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {
        "gov.vta.userlocation.service",
        "gov.vta.userlocation.dto",
        "gov.vta.userlocation.controller",
        "gov.vta.userlocation.repository"

})

public class UserLocationApp extends SpringBootServletInitializer {

    final private static Logger LOGGER = Logger.getLogger(UserLocationApp.class);

    @Autowired
    Environment env;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UserLocationApp.class);
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users");
    }
    
    @Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
/**
    @Bean
    DataSource dataSource() throws SQLException {

        String url = env.getProperty("spring.datasource.url");// "jdbc:oracle:thin:@co-udb-dev.chnvpyugh6ds.us-east-1.rds.amazonaws.com:1563/DSCD";
        String username = env.getProperty("spring.datasource.username");//
        String password = env.getProperty("spring.datasource.password");//
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    } **/
    
    public static void main(String[] args) {
        LOGGER.debug("Spring Boot Started");
        SpringApplication.run(UserLocationApp.class, args);
        LOGGER.debug("Spring Boot Ended");
    }


  
}
