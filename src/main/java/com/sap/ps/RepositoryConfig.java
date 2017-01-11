package com.sap.ps;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import com.sap.ps.model.Book;
import com.sap.ps.model.Borrow;
import com.sap.ps.model.User;


/**
 *
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config){
        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Borrow.class);
    }
}
