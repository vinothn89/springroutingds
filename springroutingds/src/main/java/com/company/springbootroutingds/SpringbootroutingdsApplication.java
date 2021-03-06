package com.company.springbootroutingds;

import com.company.springbootroutingds.routing.MyRoutingDataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
/*@EnableJpaRepositories(basePackages = "com.company.springbootroutingds")*/

//@EnableAutoConfiguration
//@EntityScan("com.company.springbootroutingds.domain")


//disable auto config DataSource & DataSourceTransactionManager
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class})

//load to environment
// resources/datasource-cfg.properties
@PropertySources({@PropertySource("classpath:datasource-cfg.properties")})
public class SpringbootroutingdsApplication {

	//stores all the properties loaded by the @PropertySource
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootroutingdsApplication.class, args);
	}

	//returns routing DataSource (MyRoutingDataSource)
	@Autowired
	@Bean(name = "dataSource")
	public DataSource getDataSource(DataSource legacy, DataSource strategic){
		
		MyRoutingDataSource dataSource = new MyRoutingDataSource();
		dataSource.initDataSources(legacy, strategic);
		return dataSource;

	}
	
	@Bean(name="asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("DataDAOLookup-");
        executor.initialize();
        return executor;
    }

	@Bean(name = "strategic")
	public DataSource getDataSource1() throws SQLException{
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		//datasource-cgf.properties
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name.1"));
		dataSource.setUrl(env.getProperty("spring.datasource.url.1"));
		dataSource.setUsername(env.getProperty("spring.datasource.username.1"));
		dataSource.setPassword(env.getProperty("spring.datasource.password.1"));

		System.out.println("## DataSource1: "+dataSource);
		return dataSource;
	}

	@Bean(name = "legacy")
	public DataSource getDataSource2() throws SQLException{
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		//datasource-cfg.properties
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name.2"));
		dataSource.setUrl(env.getProperty("spring.datasource.url.2"));
		dataSource.setUsername(env.getProperty("spring.datasource.username.2"));
		dataSource.setPassword(env.getProperty("spring.datasource.password.2"));

		System.out.println("## DataSource2: " + dataSource);

		return dataSource;
	}

	@Autowired
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager getTransactionManager(DataSource dataSource){
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource);

		return txManager;
	}
}
