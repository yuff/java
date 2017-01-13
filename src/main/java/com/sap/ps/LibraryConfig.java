package com.sap.ps;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
//@Resource(name = "psLibrary", type = DataSource.class)
public class LibraryConfig {	
    @Bean
    public FilterRegistrationBean corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
    
	@Profile({ "neo", "default", "prod" })
	@Bean(destroyMethod = "")
	public DataSource jndiDataSource() throws SQLException {
		DataSource ds = new JndiDataSourceLookup().getDataSource("java:comp/env/jdbc/psLibrary");
		log.info(ds.getConnection().getMetaData().getDatabaseProductName());
		return ds;
	}

	@Profile({ "dev" })
	@Bean
	public DataSource h2embedded() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("ps-library").build();
	}
}
