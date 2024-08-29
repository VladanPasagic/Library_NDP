module Library.WorkerApplication {
	requires javafx.controls;
	requires javafx.fxml;
	requires LibraryServices;
	requires LibraryEntities;
	requires jakarta.ws.rs;
	requires jakarta.annotation;
	requires jakarta.inject;
	requires com.fasterxml.jackson.core;
	requires jakarta.xml.bind;
	requires jersey.hk2;

	opens org.unibl.etf.mdp.library.main to javafx.graphics, javafx.fxml;
	opens org.unibl.etf.mdp.library.controllers to javafx.fxml;
}
