package com.sku.elcoco.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
@RequiredArgsConstructor
public class DatabaseConfig {

    /**ApplicationContext
     * 스프링의 컨테이너(Container) 중 하나로, 빈(Bean)의 생성과 사용, 관계, 생명 주기 등을 관리합니다.
     *
     * 여기서는 MyBatis의 Mapper XML 경로를 처리하기 위해 사용됩니다.
     */
    private final ApplicationContext context;
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    /**
     *SqlSessionFactory
     DB의 커넥션과, SQL 실행에 대한 모든 것을 갖는 객체입니다.

     factoryBean.setMapperLocations를 통해 Mapper XML의 경로를 지정하는데요.

     classpath는 src/main/resources 디렉터리를 의미하며, 해당 경로에 Mapepr XML을 추가하게 됩니다.
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setMapperLocations(context.getResources("classpath:/mappers/**/*Mapper.xml"));
        return factoryBean.getObject();
    }

    /**
     * SqlSession
     *
     * SQL 실행에 필요한 모든 메서드(INSERT, UPDATE, DELETE, SELECT)를 갖는 객체입니다.
     * */
    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

}