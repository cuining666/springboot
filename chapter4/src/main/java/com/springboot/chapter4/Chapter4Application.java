package com.springboot.chapter4;

import com.springboot.chapter4.dao.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

// 定义spring扫描包路径
@SpringBootApplication(scanBasePackages = {"com.springboot.chapter4"})
// 定义JPA接口扫描包路径
@EnableJpaRepositories(basePackages = "com.springboot.chapter4.dao")
// 定义实体Bean扫描包路径
@EntityScan(basePackages = "com.springboot.chapter4.pojo")
@MapperScan(
		// 指定扫描包
		basePackages = "com.springboot.chapter4.*",
		// 指定sqlSessionFactory，如果sqlSessionTemplate被指定，则作废
		sqlSessionFactoryRef = "sqlSessionFactory",
		// 指定sqlSessionTemplate，将忽略sqlSessionFactory的配置
		sqlSessionTemplateRef = "sqlSessionTemplate",
		annotationClass = Repository.class)
public class Chapter4Application {
//	@Autowired
//	private SqlSessionFactory sqlSessionFactory;
//
//	/**
//	 * 使用MapperFactoryBean装配MyBatis接口
//	 * 缺点：每个Dao需要一个个定义
//	 * @return
//	 */
//	@Bean
//	public MapperFactoryBean<UserMapper> initUserMapper() {
//		// 定义一个Mybatis的Mapper接口
//		MapperFactoryBean<UserMapper> bean = new MapperFactoryBean<UserMapper>();
//		bean.setMapperInterface(UserMapper.class);
//		bean.setSqlSessionFactory(sqlSessionFactory);
//		return bean;
//	}

//	/**
//	 * 配置MyBatis接口扫描
//	 * 优点：避免一个个定义；缺点：要写java代码
//	 * @return
//	 */
//	@Bean
//	public MapperScannerConfigurer mapperScannerConfigurer() {
//		// 定义扫描器实例
//		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//		// 加载SqlSessionFactory, Spring Boot会自动生产SqlSessionFactory实例
//		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//		// 定义扫描包
//		mapperScannerConfigurer.setBasePackage("com.springboot.chapter4.*");
//		// 限定被标注@Repository的接口才被扫描
//		mapperScannerConfigurer.setAnnotationClass(Repository.class);
//		return mapperScannerConfigurer;
//	}

	public static void main(String[] args) {
		SpringApplication.run(Chapter4Application.class, args);
	}

}
