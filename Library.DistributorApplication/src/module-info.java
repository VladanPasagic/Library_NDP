module Library.DistributorApplication {
	requires javafx.controls;
	requires javafx.fxml;
	requires LibraryEntities;
	requires LibraryServices;
	requires com.rabbitmq.client;
	requires java.rmi;
	requires org.slf4j;
	
	opens org.unibl.etf.mdp.library.main to javafx.graphics, javafx.fxml;
	opens org.unibl.etf.mdp.library.controllers to javafx.fxml;
}
