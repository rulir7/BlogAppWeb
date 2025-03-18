package com.web.BlogApp.configuration;
//package com.web.BlogApp.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	
//	 private static final String[] AUTH_LIST = {
//		        "/",
//		        "/posts",
//		        "/posts/{id}"
//		    };
//	
//	//ImplementsUserDetailsService userDetailsService;
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//			   http    
//			   	 .csrf((csrf) -> csrf.disable()) // habilita e desabila recursos de segurança	
//		 		 .authorizeHttpRequests((authz) -> authz 
//				 .requestMatchers(AUTH_LIST).permitAll()
//				 .requestMatchers(HttpMethod.GET, "/cadastrarEvento").hasRole("ADMIN")
//				 .requestMatchers(HttpMethod.POST, "/cadastrarEvento").hasRole("ADMIN")
//				 .anyRequest().authenticated()
//				)
//		 		.formLogin((formLogin) -> formLogin
//		 				.permitAll()
//		 		)
//		 		.logout((logout) -> logout
//		 				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//				);
//		 		 
//			return http.build();
//			
//		}
//
//	
//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//	       return (web) ->  web.ignoring().requestMatchers("/bootstrap/**");
//	    }
//
//	
//	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {//para senha gerada pelo spring precisa comentar esse metodo
//		 UserDetails user = User.withDefaultPasswordEncoder()
//		            .username("helo")
//		    	    .password("123")
//		    	    .roles("ADMIN")
//		    	    .build();
//		 UserDetails admin = User.withDefaultPasswordEncoder()
//		        		.username("user")
//		        		.password("245")
//		        		.roles("USER")
//		        		.build();
//		            
//		        return new InMemoryUserDetailsManager(user, admin);
//		    }
//	
//
//}
