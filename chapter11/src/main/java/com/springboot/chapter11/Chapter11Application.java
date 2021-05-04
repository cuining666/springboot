package com.springboot.chapter11;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * 使用数据库验证
 */
@SpringBootApplication(scanBasePackages = "com.springboot.chapter11")
@MapperScan(basePackages = "com.springboot.chapter11", annotationClass = Repository.class)
@EnableCaching
public class Chapter11Application extends WebSecurityConfigurerAdapter {

	// 使用用户名称查询密码
	private String pwdQuery = "select user_name, pwd, available from t_user where user_name=?";
	// 使用用户名称查询角色信息
	private String roleQuery = "select u.user_name, r.role_name from t_user u, t_user_role ur, t_role r where u.id=ur.user_id and r.id=ur.role_id and u.user_name=?";
	// 注入配置的阴钥
	@Value("${system.user.password.secret}")
	private String secret;
	// 注入数据源
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserDetailsService userDetailsService;


	/**
	 * 覆盖WebSecurityConfigurerAdapter用户详情方法
	 * @param auth 用户签名管理器构造器
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 密码编辑器
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// 在实际的企业生产中还可能通过自己的阴匙对密码进行加密处理，而阴匙存在企业服务器上，这样即使密文被人截取，别人也无法得到阴匙破解密文，这样就能够大大地提高网站的安全性。
		// 对此Spring Security也进行了支持，只需要使用密码编码器（Pbkdf2PasswordEncoder类）对象即可
//		PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(secret);
		// 设置用户密码服务和密码编码器
		System.out.println(passwordEncoder.encode("123456"));
		System.out.println(passwordEncoder.encode("admin"));
		auth.jdbcAuthentication().passwordEncoder(passwordEncoder).dataSource(dataSource).usersByUsernameQuery(pwdQuery).authoritiesByUsernameQuery(roleQuery);
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//				// 使用Spring表达式限定只有角色ROLE_USER或者ROLE_ADMIN
//				.antMatchers("/user/**").access("hasRole('USER') or hasRole('ADMIN')")
//				// 设置访问权限给角色ROLE_ADMIN，要求是完整登录（非记住我登录）
//				.antMatchers("/admin/welcome1").access("hasAnyAuthority('ROLE_ADMIN') && isFullyAuthenticated()")
//				// 限定/admin/welcome2访问权限给角色ROLE_ADMIN，允许不完整登录
//				.antMatchers("/user/welcome2").access("hasAnyAuthority('ROLE_ADMIN')")
//				// 使用记住我功能
//				.and().rememberMe()
//				// 使用Spring Security默认的登录页面
//				.and().formLogin()
//				// 启动HTTP基础验证
//				.and().httpBasic();
//		// 强制使用HTTPS
//		// requiresSecure表示使用HTTPS请求。这样对于Ant 风格下的地址/admin/**就只能使用HTTPS协议进行请求了，而对于requiresInsecure则是取消安全请求的机制，这样就可以使用普通的HTTP请求。
//		http
//				// 使用安全渠道，限定为https请求
//				.requiresChannel().antMatchers("/admin/**").requiresSecure()
//				// 不使用HTTPS请求
//				.and().requiresChannel().antMatchers("/user/**").requiresInsecure()
//				// 限定允许访问的角色
//				.and().authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN")
//				.and().authorizeRequests().antMatchers("/user/**").hasAnyRole("USER", "ADMIN");
//	}

	public static void main(String[] args) {
		SpringApplication.run(Chapter11Application.class, args);
	}

}
