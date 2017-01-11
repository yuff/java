package com.sap.ps.odata;

import java.io.InputStream;

import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import org.springframework.stereotype.Component;


@Component
public class BookLibraryExtension implements JPAEdmExtension{

	@Override
	public void extendWithOperation(JPAEdmSchemaView view) {
		view.registerOperations(BookUserOperations.class, null);
	}

	@Override
	public void extendJPAEdmSchema(JPAEdmSchemaView view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream getJPAEdmMappingModelStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
