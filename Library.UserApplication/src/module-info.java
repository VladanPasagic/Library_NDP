module Library.UserApplication {
	requires javafx.controls;
	requires javafx.fxml;
	requires LibraryEntities;
	requires LibraryServices;
	requires jakarta.ws.rs;
	requires jakarta.annotation;
	requires javafx.base;
	
	opens org.unibl.etf.mdp.library.main to javafx.graphics, javafx.fxml;
	opens org.unibl.etf.mdp.library.controllers to javafx.fxml;
}
