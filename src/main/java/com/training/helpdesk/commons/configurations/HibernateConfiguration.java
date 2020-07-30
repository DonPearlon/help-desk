package com.training.helpdesk.commons.configurations;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.training.helpdesk.*")
@PropertySource(value = {"classpath:/db/db.properties", 
        "classpath:/hibernate/hibernate.properties"})
public class HibernateConfiguration {
          
    private final DataSourceProperties dataSourceProperties;
    private final HibernateProperties hibernateProperties;
    
    /**
     * Constructor.
     *
     * @param environment
     *            {@link Environment}.
     */   
    public HibernateConfiguration(final Environment environment) {
        dataSourceProperties = new DataSourceProperties(environment);
        hibernateProperties = new HibernateProperties(environment);
    }
    
    /**
     * @return {@link LocalSessionFactoryBean} bean.
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.training.helpdesk.*" );
        sessionFactory.setHibernateProperties(hibernateProperties.get());
        return sessionFactory;
    }
    
    /**
     * @param sessionFactory {@link SessionFactory}.
     * @return {@link HibernateTransactionManager} bean.
     */
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
    
    /**
     * @return {@link DataSource}.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDbDriver());
        dataSource.setUrl(dataSourceProperties.getDbUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        
        Resource createTables = new ClassPathResource(DataSourceProperties.CREATE_TABLE_SQL_PATH);
        Resource fillTables = new ClassPathResource(DataSourceProperties.FILL_TABLE_SQL_PATH);
        DatabasePopulator databasePopulatorCreate = new ResourceDatabasePopulator(createTables);
        DatabasePopulator databasePopulatorFill = new ResourceDatabasePopulator(fillTables);
        DatabasePopulatorUtils.execute(databasePopulatorCreate, dataSource);
        DatabasePopulatorUtils.execute(databasePopulatorFill, dataSource);
        
        return dataSource;
    }
    
    /**
     * Inner class contains provides access to the 'data source' properties.
     */
    private class DataSourceProperties {
    
        private static final String CREATE_TABLE_SQL_PATH = "sql/schema.sql";
        private static final String FILL_TABLE_SQL_PATH = "sql/data.sql";
    
        private static final String DRIVER = "db.driver";
        private static final String URL = "db.url";
        private static final String USERNAME = "db.username";
        private static final String PASSWORD = "db.password";
    
        private final Environment environment;
        
        /**
         * Constructor.
         *
         * @param environment
         *            {@link Environment}.
         */  
        public DataSourceProperties(final Environment environment) {
            this.environment = environment;
        }
        
        /**
         * @return 'driver' property.
         */
        private String getDbDriver() {
            return this.environment.getProperty(DRIVER);
        }
        
        /**
         * @return 'url' property.
         */
        private String getDbUrl() {
            return this.environment.getProperty(URL);
        }
        
        /**
         * @return 'username' property.
         */
        private String getUsername() {
            return this.environment.getProperty(USERNAME);
        }

        /**
         * @return 'password' property.
         */
        private String getPassword() {
            return this.environment.getProperty(PASSWORD);
        }

    }
    
    /**
     * Inner class contains provides access to the 'hibernate' properties.
     */
    private class HibernateProperties {
        private static final String DIALECT = "hibernate.dialect";
        private static final String SHOW_SQL = "hibernate.show_sql";
        private static final String FORMAT_SQL = "hibernate.format_sql";
        private static final String HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
        private static final String USE_SECOND_LEVEL_CACHE
                = "hibernate.cache.use_second_level_cache";
        private static final String REGION_FACTORY_CLASS 
                = "hibernate.cache.region.factory_class";
        private static final String USE_QUERY_CACHE 
                = "hibernate.cache.use_query_cache";
    
        private final Environment environment;
        
        /**
         * Constructor.
         *
         * @param environment
         *            {@link Environment}.
         */  
        public HibernateProperties(final Environment environment) {
            this.environment = environment;
        }
        
        /**
         * @return 'hibernate' properties {@link Properties}.
         */
        private Properties get() {
            Properties properties = new Properties();
            properties.put(DIALECT, this.environment.getProperty(DIALECT));
            properties.put(SHOW_SQL,  this.environment.getProperty(SHOW_SQL));
            properties.put(FORMAT_SQL, this.environment.getProperty(FORMAT_SQL));
            properties.put(HBM2DDL_AUTO, this.environment.getProperty(HBM2DDL_AUTO));
            properties.put( USE_SECOND_LEVEL_CACHE,
                    this.environment.getProperty(USE_SECOND_LEVEL_CACHE));
            properties.put(REGION_FACTORY_CLASS,
                    this.environment.getProperty(REGION_FACTORY_CLASS));
            properties.put(USE_QUERY_CACHE, this.environment.getProperty(USE_QUERY_CACHE));
            return properties;
        }
    }
}