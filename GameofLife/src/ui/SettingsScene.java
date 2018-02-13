package ui;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SettingsScene {
	private boolean settingsSet;
	private int widthOfSimulation;
	private int heightOfSimulation;
	private int widthOfCells;
	private int heightOfCells;
	
	public SettingsScene() {;
		this.settingsSet = false;
		this.widthOfSimulation = 0;
		this.heightOfSimulation = 0;
		this.widthOfCells = 0;
		this.heightOfCells = 0;
	}
	
	public VBox initialize() {
		VBox firstSceneLayout = new VBox();
		
		VBox dimensionsLayout = new VBox();
		dimensionsLayout.setPrefWidth(300);
		dimensionsLayout.setPadding(new Insets(100, 50, 20, 50));
		TextField widthOfSimulationField = new TextField();
		widthOfSimulationField.setPromptText("Width of the simulation in cells");
		TextField heightOfSimulationField = new TextField();
		heightOfSimulationField.setPromptText("Height of the simulation in cells");
		TextField widthOfCellsField = new TextField();
		widthOfCellsField.setPromptText("Width of cells");
		TextField heightOfCellsField = new TextField();
		heightOfCellsField.setPromptText("Height of cells");
		dimensionsLayout.setSpacing(20);
		dimensionsLayout.getChildren().addAll(widthOfSimulationField, heightOfSimulationField, widthOfCellsField, heightOfCellsField);
		
		firstSceneLayout.getChildren().add(dimensionsLayout);
		
		VBox startButtonLayout = new VBox();
		Button startButton = new Button("Start");
		startButton.setOnAction((event -> {
			// set GOL height and width if all of the TextFields .isEmpty() != true;
			if (!(widthOfSimulationField.getText().isEmpty()) && !(heightOfSimulationField.getText().isEmpty()) && !(widthOfCellsField.getText().isEmpty()) && !(heightOfCellsField.getText().isEmpty())) {
				this.widthOfSimulation = Integer.parseInt(widthOfSimulationField.getText());
				this.heightOfSimulation = Integer.parseInt(heightOfSimulationField.getText());
				this.widthOfCells = Integer.parseInt(widthOfCellsField.getText());
				this.heightOfCells = Integer.parseInt(heightOfCellsField.getText());
				
			} else {
				this.widthOfSimulation = 90;
				this.heightOfSimulation = 50;
				this.widthOfCells = 10;
				this.heightOfCells = 10;
			}
			this.settingsSet = true;
		}));
		startButtonLayout.setPadding(new Insets(0, 50, 80, 50));
		startButtonLayout.getChildren().add(startButton);	
		firstSceneLayout.getChildren().add(startButtonLayout);
		
		return firstSceneLayout;
	}
	
	public boolean getSettingsSet() {
		return this.settingsSet;
	}
	
	public void setSettingsSet(boolean value) {
		this.settingsSet = value;
	}
	
	public int getWidthOfSimulation() {
		return this.widthOfSimulation;
	}
	
	public int getHeightOfSimulation() {
		return this.heightOfSimulation;
	}
	
	public int getWidthOfCells() {
		return this.widthOfCells;
	}
	
	public int getHeightOfCells() {
		return this.heightOfCells;
	}
	
}
