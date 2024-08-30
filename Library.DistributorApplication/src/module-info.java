module Library.DistributorApplication {
	requires javafx.controls;
	requires javafx.fxml;
	requires LibraryEntities;
	requires LibraryServices;
	
	opens org.unibl.etf.mdp.library.main to javafx.graphics, javafx.fxml;
	opens org.unibl.etf.mdp.library.controllers to javafx.fxml;
}
