package domain;

import java.util.Random;

public class Simulation {

    private int[][] currentGeneration;
    private int[][] nextGeneration;
    private int rows;
    private int columns;
    private boolean play;
    private int countOfGenerations;
    private int probabilityOfReproduction;
    private int probabilityOfDying;
    private Random random;

    public Simulation(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.currentGeneration = new int[this.columns][this.rows];
        this.nextGeneration = new int[this.columns][this.rows];
        this.play = false;
        this.countOfGenerations = 0;
        this.probabilityOfReproduction = 100;
        this.probabilityOfDying = 100;
        this.random = new Random();
    }

    public int[][] getCurrentGeneration() {
        return this.currentGeneration;
    }

    public void setCurrentGeneration(int[][] currentGeneration) {
        this.currentGeneration = currentGeneration;
    }

    public void setPlayState(boolean state) {
        this.play = state;
    }
    
    public boolean getPlayState() {
    	return this.play;
    }

    public void setprobabilityOfReproduction(double probability) {
        this.probabilityOfReproduction = (int) probability;
    }

    public void setprobabilityOfDying(double probability) {
        this.probabilityOfDying = (int) probability;
    }

    public void evolve() {
        if (play) {
            this.nextGeneration = new int[this.columns][this.rows];
            
            // Goes through each cell on the currentGeneration
            for (int x = 1; x < this.columns - 1; x++) { 
                for (int y = 1; y < this.rows - 1; y++) { 
                    // Goes through each neighbour cell and adds their value to the overall numberOfNeighbours variable 
                    int numberOfNeighbours = 0;
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            numberOfNeighbours += this.currentGeneration[x + i][y + j];
                        }
                    }
                    // Excludes current cell from previous calculation
                    numberOfNeighbours -= this.currentGeneration[x][y];

                    int max = 100;
                    int min = 1;

                    int randomprobabilityOfReproduction = this.random.nextInt(max - min + 1) + min;
                    int randomprobabilityOfDying = this.random.nextInt(max - min + 1) + min;

                    // Rules of the "evolution" in the Game of Life
                    if (this.currentGeneration[x][y] == 1 && numberOfNeighbours < 2 && randomprobabilityOfDying <= this.probabilityOfDying) {
                        this.nextGeneration[x][y] = 0;
                    } else if (this.currentGeneration[x][y] == 1 && numberOfNeighbours > 3 && randomprobabilityOfDying <= this.probabilityOfDying) {
                        this.nextGeneration[x][y] = 0;
                    } else if (this.currentGeneration[x][y] == 0 && numberOfNeighbours == 3 && randomprobabilityOfReproduction <= this.probabilityOfReproduction) {
                        this.nextGeneration[x][y] = 1;
                    } else { 
                        this.nextGeneration[x][y] = this.currentGeneration[x][y];
                    }
                }
            }
            this.countOfGenerations++;
        }
        // nextGeneration becomes the currentGeneration, and the whole process starts again
        this.setCurrentGeneration(this.nextGeneration);
    }
    
    public String getCountOfGenerations() {
        return Integer.toString(this.countOfGenerations);
    }
    
    public void setCountOfGenerations(int count) {
        this.countOfGenerations = count;
    }

}
