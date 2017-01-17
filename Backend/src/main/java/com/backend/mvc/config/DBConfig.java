package com.backend.mvc.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.backend.mvc.model.BlogComment;
import com.backend.mvc.model.BlogPost;
import com.backend.mvc.model.Friend;
import com.backend.mvc.model.Job;
import com.backend.mvc.model.UploadFile;
import com.backend.mvc.model.User;

@Configuration
@EnableTransactionManagement
public class DBConfig {
	
	//----------- Bean configuration for SessionFactory to create bean of SessionFactory -------------//
    //---------- use hibernate to create table automatically -----------//
	
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder lsf=new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties=new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		lsf.addProperties(hibernateProperties);
		return lsf.addAnnotatedClass(User.class).addAnnotatedClass(Job.class).addAnnotatedClass(UploadFile.class).
				addAnnotatedClass(Friend.class).addAnnotatedClass(BlogPost.class).addAnnotatedClass(BlogComment.class)
				.buildSessionFactory();
	}
	
	//----------- Bean configuration of Oracle Database to find the DataSource with userName & password -------------//

	@Bean
	public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
	    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
	    dataSource.setUsername("system");
	    dataSource.setPassword("pwd");
	    return dataSource;
	}
	
	//----------- Bean configuration for HibernateTransactionManager  -------------//

	@Bean
	public HibernateTransactionManager hibTransManagement(){
		return new HibernateTransactionManager(sessionFactory());
	}


}
