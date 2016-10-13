
package com.gtm.acc.pelatihan.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@EnableWebSecurity
public class KonfigurasiSekurity extends WebSecurityConfigurerAdapter {
    
    private static final String SQL_login
        ="select username,password,active as enabled from s_users where username=?";
    private static final String sQL_PERMITION
            ="select u.username,r.nama as authority "
            +"from s_users u join s_users_role ur on u.id=ur.id_user "
            +"join s_roles r on ur.id_role=r.id "
            +"where u.username = ?";
    

    @Autowired
    private DataSource ds;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        auth
                //.inMemoryAuthentication()
                //.withUser("halasson").password("123").roles("USER");
                //----ganti dengan........
                .jdbcAuthentication()
                .dataSource(ds)
                .usersByUsernameQuery(SQL_login)
                .authoritiesByUsernameQuery(sQL_PERMITION);
    }
    
    //konfigurasi Login Page dan Permition
    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http
           
            .authorizeRequests()
                .anyRequest().authenticated()
              .and()     
                 
                 .formLogin()                    
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/halo")
                .and()
                .logout();
                
                 
                
    }
    
    private CsrfTokenRepository csrfTokenRepository() {
         HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
         repository.setHeaderName("X-XSRF-TOKEN");
         return repository;
     }
}
