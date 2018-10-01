package com.easybill.config.db;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.easybill.util.Constants;

public class GenerateDDL {

	private final String PACKAGES_TO_SCAN = "com.easybill.model";
	private final String NAMING_STRATEGY = "com.easybill.config.db.PhysicalNamingStrategyStandardImpl";
	private final String DIALECT = "org.hibernate.dialect.MySQL5InnoDBDialect";
	private final static String SCHEMA_FILE = "ddl.sql";

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public static void main(String[] args) throws IOException {
		File file = new File(SCHEMA_FILE);
		if (file.exists()) {
			file.delete();
		}
		new GenerateDDL().entityManagerFactory();
		List<String> queryAppendedWithSemiColonList = new ArrayList<>();
		List<String> queryList = FileUtils.readLines(file, Charset.defaultCharset());
		queryList.forEach(query -> {
			queryAppendedWithSemiColonList.add(query + Constants.SEMICOLON);
		});
		FileUtils.writeLines(file, queryAppendedWithSemiColonList);
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		EntityManagerFactory factory = entityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		// vendorAdapter.setDatabasePlatform(DIALECT);
		vendorAdapter.setShowSql(false);
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
		 * https://www.thoughts-on-java.org/standardized-schema-generation-data-
		 * loading-jpa-2-1/
		 * https://docs.oracle.com/javaee/7/tutorial/persistence-intro005.htm
		 * http://www.radcortez.com/jpa-database-schema-generation/
		 */
		properties.put("javax.persistence.schema-generation.scripts.action", "create");
		properties.put("javax.persistence.schema-generation.scripts.create-target", SCHEMA_FILE);
		// properties.put("javax.persistence.schema-generation.scripts.drop-target",
		// "ddl-drop.sql");
		// properties.put("javax.persistence.schema-generation.database.action",
		// "create");
		properties.put("hibernate.physical_naming_strategy", NAMING_STRATEGY);
		properties.put("hibernate.dialect", DIALECT);
		properties.put("hibernate.delimiter", Constants.SEMICOLON);
		// properties.put("hibernate.format_sql", true);
		return properties;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

}
