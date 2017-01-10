package com.sap.ps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import com.sap.icd.odatav2.spring.SpringODataLibraryPackageMarker;

@SpringBootApplication
@ComponentScan(basePackageClasses = { SpringODataLibraryPackageMarker.class, LibraryApplication.class })
public class LibraryApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}
}
