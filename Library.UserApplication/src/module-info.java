module Library.UserApplication {
	requires javafx.controls;
	requires javafx.fxml;
	requires LibraryEntities;
	requires LibraryServices;
	requires jakarta.ws.rs;
	requires jakarta.annotation;
	requires javafx.base;
	requires jakarta.inject;
	requires com.fasterxml.jackson.core;
	requires jakarta.xml.bind;
	
	opens org.unibl.etf.mdp.library.main to javafx.graphics, javafx.fxml;
	opens org.unibl.etf.mdp.library.controllers to javafx.fxml;
}
