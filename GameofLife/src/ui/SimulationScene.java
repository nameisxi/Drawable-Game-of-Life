package ui;

import domain.Simulation;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SimulationScene {
	private int[][] currentGeneration;
	private Rectangle[][] cellGUIArray;
	private Simulation simulation;
	private SettingsScene settingsScene;
	private Label countOfGenerations;
	private boolean play;
	private int columns;
	private int rows;
	private int widthOfCells;
	private int heightOfCells;
	private double probabilityOfReproductionValue;
	private double probabilityOfDyingValue;
	
	public SimulationScene(int[][] currentGeneration, Rectangle[][] cellGUIArray, Simulation simulation, SettingsScene settingsScene, boolean play, int columns, int rows, int widthOfCells, int heightOfCells) {
		this.currentGeneration = currentGeneration;
		this.cellGUIArray = cellGUIArray;
		this.simulation = simulation;
		this.settingsScene = settingsScene;
		this.play = play;
		this.columns = columns;
		this.rows = rows;
		this.widthOfCells = widthOfCells;
		this.heightOfCells = heightOfCells;
		this.probabilityOfReproductionValue = 100;
		this.probabilityOfDyingValue = 100;
	}
	
	public BorderPane initialize() {
		BorderPane simulationLayout = new BorderPane();
		GridPane world = this.initializeGridPane();
		HBox controlButtons = this.initializeHBox();
		
		simulationLayout.setCenter(world);
		simulationLayout.setBottom(controlButtons);
		return simulationLayout;
	}
	
	/** Initializes the GridPane in which the simulation itself is represented*/
	public GridPane initializeGridPane() {
		GridPane world = new GridPane();
		
		for (int x = 0; x < this.columns; x++) {
			for (int y = 0; y < this.rows; y++) {
				Rectangle cellGUI = new Rectangle(this.widthOfCells - 1, this.heightOfCells - 1);
				cellGUI.setFill(Color.web("#FFFFFF"));		// White cell background color
                cellGUI.setStroke(Color.web("#C0C0C0")); 	// Grey cell border color
                cellGUI.setStrokeWidth(1); 					// Width of the border

                world.add(cellGUI, x, y);
                this.cellGUIArray[x][y] = cellGUI;

                cellGUI.setOnMouseClicked((event) -> {
                	this.currentGeneration = this.simulation.getCurrentGeneration();
                    for (int i = 0; i < columns; i++) {
                        for (int j = 0; j < rows; j++) {
                            if (this.cellGUIArray[i][j].equals(cellGUI) && this.currentGeneration[i][j] == 0) {
                                this.currentGeneration[i][j] = 1;
                            } else if (this.cellGUIArray[i][j].equals(cellGUI) && this.currentGeneration[i][j] == 1) {
                                this.currentGeneration[i][j] = 0;
                            }
                        }
                    }
                    this.simulation.setCurrentGeneration(this.currentGeneration);
                });
			}
		}
		return world;
	}
	
	/** Initializes the HBox panel that contains the control buttons at the bottom of the simulation scene*/
	public HBox initializeHBox() {
		// Container for the control buttons
		HBox controlButtons = new HBox();
		controlButtons.setPadding(new Insets(10.0, 0.0, 10.0, 10.0));
        controlButtons.setSpacing(80);

        // Container for the Play/Pause and Reset buttons
        HBox simulationStateButtons = new HBox();
		simulationStateButtons.setSpacing(5);

        Button playButton = new Button("Play");
        simulationStateButtons.getChildren().add(playButton);

        playButton.setOnAction((event) -> {
            this.play = !this.play;
            if (this.play) {
                playButton.setText("Pause");
            } else {
                playButton.setText("Play");
            }
            simulation.setPlayState(this.play);
        });

        Button resetButton = new Button("Reset");
        simulationStateButtons.getChildren().add(resetButton);

        resetButton.setOnAction((event) -> {
        	this.settingsScene.setSettingsSet(true);
        });

        controlButtons.getChildren().add(simulationStateButtons);

        // Container for the probability of death and reproduction sliders
		HBox probabilitySliders = new HBox();
		probabilitySliders.setSpacing(20);

		// Container for the probability of reproduction slider
		VBox probabilityOfReproductionSlider = new VBox();

        // probability of Reproduction
        Slider probabilityOfReproduction = new Slider(1, 100, 100);
        probabilityOfReproduction.setMinWidth(200);
        probabilityOfReproduction.setMaxWidth(200);
		probabilityOfReproduction.setShowTickMarks(true);
		probabilityOfReproductionSlider.getChildren().add(probabilityOfReproduction);

		Label probabilityOfReproductionCurrentValue = new Label("probability of reproduction: " + Double.toString(Math.round(probabilityOfReproduction.getValue())) + "%");
		probabilityOfReproductionCurrentValue.setPadding(new Insets(0, 15, 0, 15));
		probabilityOfReproductionSlider.getChildren().add(probabilityOfReproductionCurrentValue);

		probabilityOfReproduction.setOnMouseDragged((event) -> {
			this.probabilityOfReproductionValue = Math.round(probabilityOfReproduction.getValue());
			this.simulation.setprobabilityOfReproduction(this.probabilityOfReproductionValue);
			probabilityOfReproductionCurrentValue.setText("probability of reproduction " + Double.toString(Math.round(probabilityOfReproduction.getValue())) + "%");
		});

		probabilitySliders.getChildren().add(probabilityOfReproductionSlider);

		// Container for the probability of dying slider
		VBox probabilityOfDyingSlider = new VBox();

		// probability of dying in the event of under, or overpopulation
        Slider probabilityOfDying = new Slider(1, 100, 100);
        probabilityOfDying.setMinWidth(200);
        probabilityOfDying.setMaxWidth(200);
        probabilityOfDying.setShowTickMarks(true);
        probabilityOfDyingSlider.getChildren().add(probabilityOfDying);

		Label probabilityOfDyingCurrentValue = new Label("probability of dying: " + Double.toString(Math.round(probabilityOfDying.getValue())) + "%");
		probabilityOfDyingCurrentValue.setPadding(new Insets(0, 35, 0, 35));
		probabilityOfDyingSlider.getChildren().add(probabilityOfDyingCurrentValue);

		probabilityOfDying.setOnMouseDragged((event) -> {
			this.probabilityOfDyingValue = Math.round(probabilityOfDying.getValue());
			this.simulation.setprobabilityOfDying(this.probabilityOfDyingValue);
			probabilityOfDyingCurrentValue.setText("probability of dying: " + Double.toString(Math.round(probabilityOfDying.getValue())) + "%");
		});

		probabilitySliders.getChildren().add(probabilityOfDyingSlider);
		controlButtons.getChildren().add(probabilitySliders);

        this.countOfGenerations = new Label("Generation: " + 0);
        controlButtons.getChildren().add(countOfGenerations);
		
		return controlButtons;
	}

	public void setCountofGenerations(String count) {
		this.countOfGenerations.setText(count);
	}
	
}
