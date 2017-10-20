package com.bill.management.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.bill.management.repository")
@PropertySource(value = { "classpath:application.properties" })
public class DBConfig {
	
	private final String DIALECT = "org.hibernate.dialect.MySQL5InnoDBDialect";
	private final String PACKAGES_TO_SCAN = "com.bill.management.schema";
	
	@Autowired
	private Environment env;

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${init-db:false}")
	private String initDatabase;

	@Bean
	public PlatformTransactionManager transactionManager() {
		EntityManagerFactory factory = entityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		//vendorAdapter.setDatabasePlatform(DIALECT);
		vendorAdapter.setShowSql(Boolean.parseBoolean(env.getProperty("datasource.show-sql")));
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(PACKAGES_TO_SCAN);
		factory.setJpaProperties(getJPAProperties());
		factory.afterPropertiesSet();
		factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		return factory;
	}
	
	private Properties getJPAProperties() {
		Properties properties = new Properties();
		/*
		 * https://www.thoughts-on-java.org/standardized-schema-generation-data-loading-jpa-2-1/
		 * https://docs.oracle.com/javaee/7/tutorial/persistence-intro005.htm
		 * http://www.radcortez.com/jpa-database-schema-generation/
		 */
		//properties.put("javax.persistence.schema-generation.scripts.action", "create");
		//properties.put("javax.persistence.schema-generation.scripts.create-target", "ddl.sql");
		//properties.put("javax.persistence.schema-generation.scripts.drop-target", "ddl-drop.sql");
		//properties.put("javax.persistence.schema-generation.database.action", "create");
		properties.put("hibernate.physical_naming_strategy", "com.bill.management.config.PhysicalNamingStrategyStandardImpl");
		properties.put("hibernate.dialect", DIALECT);
		//properties.put("hibernate.format_sql", true);
		return properties;
	}
	
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("datasource.url"));
		dataSource.setUsername(env.getProperty("datasource.username"));
		dataSource.setPassword(env.getProperty("datasource.password"));
		return dataSource;
	}

	/*@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource("data.sql"));
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(Boolean.parseBoolean(initDatabase));
		return dataSourceInitializer;
	}*/
}
