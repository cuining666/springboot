package com.springboot.chapter11.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * 使用数据库验证
 * 注解分别是标识该类是配置类、开启 Security 服务、开启全局 Securtiy 注解
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 指定了密码的加密方式（5.0 版本强制要求设置），因为我们数据库是明文存储的，所以明文返回即可
        PasswordEncoder passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        };
        // 在实际的企业生产中还可能通过自己的阴匙对密码进行加密处理，而阴匙存在企业服务器上，这样即使密文被人截取，别人也无法得到阴匙破解密文，这样就能够大大地提高网站的安全性。
        // 对此Spring Security也进行了支持，只需要使用密码编码器（Pbkdf2PasswordEncoder类）对象即可
//		PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(secret);
        // 设置用户密码服务和密码编码器
//        auth.jdbcAuthentication().passwordEncoder(passwordEncoder).dataSource(dataSource).usersByUsernameQuery(pwdQuery).authoritiesByUsernameQuery(roleQuery);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

//	@Override
	protected void configure(HttpSecurity http) throws Exception {
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
        http.authorizeRequests()
                // 访问/admin下的请求街要管理员权限
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                // 访问/user下的请求街要用户权限
                .antMatchers("/user/**").access("hasRole('USER')")
                // 启用remember me功能，有效时间为1天(86400s)
                .and().rememberMe().tokenValiditySeconds(86400).key("remember-me-key")
                // 启用HTTP Basic功能，而realName方法的作用是设置模态对话框的标题
                .and().httpBasic().realmName("my-basic-name")
                // 通过签名后可以访问任何请求
                .and().authorizeRequests().antMatchers("/**").permitAll()
                // 设置登录页和默认的跳转路径
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/index").permitAll();
	}

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }
}
