module Library.UserApplication {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens org.unibl.etf.mdp.library.main to javafx.graphics, javafx.fxml;
	opens org.unibl.etf.mdp.library.controllers to javafx.fxml;
}
