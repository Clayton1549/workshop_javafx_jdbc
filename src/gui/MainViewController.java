package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.service.DepartmentService;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem MenuItemSeller;
	@FXML
	private MenuItem MenuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void  onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
		
	}
	
	@FXML
	public void  onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) ->  {
			
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
			
		});
		
	}
	
	@FXML
	public void  onMenuItemAboutAction() {
		loadView("/gui/About.fxml" , x -> {});
		
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	
		
	}
	 
	public synchronized <T> void loadView(String absolutName ,Consumer<T> inicializingAction) {
		try {
			FXMLLoader loader  = new FXMLLoader(getClass().getResource(absolutName));
			VBox  newVBOXBox = loader.load();
		  
			Scene mainScene = Main.getMainScene();
			VBox mainBox =  (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainBox.getChildren().get(0);
			mainBox.getChildren().clear();
			mainBox.getChildren().add(mainMenu);
			mainBox.getChildren().addAll(newVBOXBox.getChildren());
			
			T controller = loader.getController();
			inicializingAction.accept(controller);
			
			
		}
		
		catch(IOException e) {
			
			Alerts.showAlert("IO Exception " , "Error loading view" ,e.getMessage(),AlertType.ERROR);
			
			
		 }
	   }
	
	}
	


