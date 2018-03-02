package gameoflife;

import domain.Simulation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ui.MainScene;
import ui.SettingsScene;
import ui.SimulationScene;

public class GameOfLife extends Application {

	private int[][] currentGeneration;
	private Rectangle[][] cellGUIArray;
	private Simulation simulation;
	private SimulationScene simulationScene;
	private BorderPane simulationSceneLayout;
	private boolean play;
	private int columns;
	private int rows;
	private int widthOfCells;
	private int heightOfCells;

	public static void main(String[] args) {
		launch(GameOfLife.class);
	}

	@Override
	public void start(Stage primaryStage) {
		MainScene mainScene = new MainScene();
		BorderPane mainLayout = mainScene.initialize();

		// settingsScene e.g. the first scene where the settings of the simulation are set.
		SettingsScene settingsScene = new SettingsScene();
		VBox settingsSceneLayout = settingsScene.initialize();

		mainLayout.setCenter(settingsSceneLayout);

		// simulationLayout e.g. the layout of the Game of Life simulation itself.
		this.simulationScene = null;
		this.simulationSceneLayout = null;

		new AnimationTimer() {
			private long sleepNanoseconds = 30 * 1000000; // Original value: 200 * 1000000, and after that it was 50 * 1000000
			private long previousTime = 0;

			@Override
			public void handle(long currentNanoTime) {
				if (settingsScene.getSettingsSet()) {
					columns = settingsScene.getWidthOfSimulation();
					rows = settingsScene.getHeightOfSimulation();
					widthOfCells = settingsScene.getWidthOfCells();
					heightOfCells = settingsScene.getHeightOfCells();
				
					simulation = new Simulation(columns, rows);
					currentGeneration = simulation.getCurrentGeneration();
					cellGUIArray = new Rectangle[columns][rows];
					play = false;

					// simulationLayout e.g. the layout of the Game of Life simulation itself.
					simulationScene = new SimulationScene(currentGeneration, cellGUIArray, simulation, settingsScene, play, columns, rows, widthOfCells, heightOfCells);
					simulationSceneLayout = simulationScene.initialize();
					mainLayout.setCenter(simulationSceneLayout);
					primaryStage.setWidth((columns + 0.5) * widthOfCells);
					primaryStage.setHeight((rows * heightOfCells) + 90);
					settingsScene.setSettingsSet(false);					
				}
				
				if ((currentNanoTime - previousTime) < sleepNanoseconds || columns < 1 || rows < 1 || widthOfCells < 1 || heightOfCells < 1 || simulation == null || currentGeneration == null) {
					return;
				}

				currentGeneration = simulation.getCurrentGeneration();

				for (int x = 0; x < columns; x++) {
					for (int y = 0; y < rows; y++) {
						cellGUIArray[x][y].setFill(Color.web("#FFFFFF")); 		// White
						cellGUIArray[x][y].setStroke(Color.web("#C0C0C0")); 	// Grey cell border color
						cellGUIArray[x][y].setStrokeWidth(widthOfCells * 0.02);	// Width of the border
						if (currentGeneration[x][y] == 1) {
							cellGUIArray[x][y].setFill(Color.web("#000000")); 	// Black
						}	
					}
				}
				simulation.evolve();
				
				if (simulation.getPlayState()) {
					simulationScene.setCountofGenerations("Generation: " + simulation.getCountOfGenerations());
				}
				// Can't touch this
				previousTime = currentNanoTime;
			}

		}.start();

		Scene scene = new Scene(mainLayout);
		primaryStage.setTitle("Game of Life");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
