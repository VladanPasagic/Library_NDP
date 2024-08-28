module Library.WorkerApplication {
	requires javafx.controls;
	requires javafx.fxml;
	requires LibraryServices;
	requires LibraryEntities;

	opens org.unibl.etf.mdp.library.main to javafx.graphics, javafx.fxml;
	opens org.unibl.etf.mdp.library.controllers to javafx.fxml;
}
