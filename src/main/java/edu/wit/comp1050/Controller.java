package edu.wit.comp1050;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import java.util.*;

public class Controller extends Application {
    public int numberOfPegs, numberOfRows; //integer variables for row and peg counts
    public boolean allowDuplicate, allowBlank, darkMode; //boolean variables for duplicates and blanks
    public int rowCounter = 0; //row counter variable
    private final Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.WHITE, Color.BLACK, Color.DARKGRAY}; //list of peg colors


    public int[] pegRandomizer() { //peg randomizer function to randomize the answer pegs that are returned
        int[] randomPegs = new int[numberOfPegs];
        Arrays.fill(randomPegs, -1);

        for (int i = 0; i < numberOfPegs; i++) {
            Random rnd = new Random();

            int nextNum = rnd.nextInt(numberOfPegs) + 1;

            if (Arrays.stream(randomPegs).anyMatch(j -> j == nextNum)) {
                if (i != 0) {
                    i--;
                }

            } else {
                randomPegs[i] = nextNum;
            }
        }
        return randomPegs;
    }

    public Button createButton(Color[] colorCode, Circle[] circles, Circle[] pegs, masterMindCode codeGenerator, pegController pegGenerator, Stage stage) {
        Button btn = new Button("Check"); //Create the check button

        btn.setOnAction(arg0 -> { //set the action when the check button is clicked
            GridPane popupGrid = new GridPane(); //create a new grid pane to be used for the win/loss popup
            popupGrid.setHgap(10); //format popupGrid
            popupGrid.setVgap(10);
            popupGrid.setAlignment(Pos.CENTER);

            popupGrid.setMinSize(200,100);
            if(darkMode) {
                popupGrid.setStyle(" -fx-background-color: #1e2124;  -fx-border-color: #7289da;");
            }else{
                popupGrid.setStyle(" -fx-background-color: #FFFFFF;  -fx-border-color: #7289da;");
            }
            Button replay = new Button("Replay"); //create replay button to be used in the popup
            Button exit = new Button("Quit"); //create quit button to be used in the popup
            popupGrid.add(replay, 0, 1); //add buttons to gridpane
            popupGrid.add(exit, 2, 1);
            Popup popup = new Popup();
            popup.getContent().add(popupGrid);
            EventHandler<ActionEvent> replayEvent = //set action for the replay button on click
                        e -> popup.hide();
                EventHandler<ActionEvent> exitEvent = //set action for the quit button on click
                        e -> System.exit(0);

            replay.setOnAction(replayEvent); //set the buttons event on action
            exit.setOnAction(exitEvent);

            boolean win = codeGenerator.codeChecker(colorCode, circles, pegs, rowCounter); //use the codeChecker function to determine if the player has won
            rowCounter++; //iterate the row counter so the player goes to the next row of pegs
            if(win){ //check if the player won
                System.out.println("YOU WIN\n"); //print win message in console
                rowCounter = 0; //reset the row counter for if the game is restarted
                Label winMessage = new Label("You Won!"); //create win message
                if(darkMode) {
                    winMessage.setTextFill(Color.WHITE); //format the win message label
                }
                popupGrid.add(winMessage, 1,0);
                popup.show(stage); //display the popup stating that the player won, and prompt if they want to restart or exit
                start(stage); //reset the program
            }else if (rowCounter == numberOfRows) {
                System.out.println("YOU LOSE\n"); //print that the player lost in the console
                rowCounter = 0;
                Label lostMessage = new Label("You Lost!"); //create player lost message
                if(darkMode) {
                    lostMessage.setTextFill(Color.WHITE);
                }
                popupGrid.add(lostMessage, 1,0);
                popup.show(stage); //display the popup stating that the player lost, and prompt if they want to restart or exit
                start(stage); //reset the program
            }

            pegGenerator.setCurrentRowStroke(circles, rowCounter, numberOfPegs); //set the stroke of the next row to show the user where to enter their answers
        });

        return btn;
    }

    @Override
    public void start(Stage stage) {
        parseConfig(); //call the parse config method to check the contents of the .properties file
        masterMindCode codeGenerator = new masterMindCode(numberOfPegs, numberOfRows, allowDuplicate, allowBlank, rowCounter, colors); //create a global instance of masterMindCode to be used throughout the controller
        pegController pegGenerator = new pegController(colors); //create a global instance of masterMindCode to be used throughout the controller
        GridPane grid = new GridPane(); //create a gridpane to be displayed
        Color[] code = codeGenerator.generateCode(); //generate a code

        grid.setHgap(10); //format the grid
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        //Create the user entry and result pegs
        int circleCount = numberOfRows * numberOfPegs;
        Circle[] circles = new Circle[circleCount];
        Circle[] pegs = new Circle[circleCount];

        int heightCounter = 0, widthCounter = 0;

        for (int i = 0; i < circleCount; i++) { //loop to create circles, and add them to the grid
            circles[i] = pegGenerator.generatePeg(); //create circle

            grid.add(circles[i], widthCounter, heightCounter); //add circles(pegs) to grid

            widthCounter++;

            if (widthCounter == numberOfPegs) {
                widthCounter = 0;
                heightCounter++;
            }
        }

        pegGenerator.setCurrentRowStroke(circles, rowCounter, numberOfPegs); //set the stroke of the first row


        heightCounter = 0;
        for (int i = 0; i < circleCount; i++) { //loop to create circles, and add them to the grid
            pegs[i] = pegGenerator.generateCodePegs(); //create circle

            if ((i % numberOfPegs == 0 && i != 0) || i + 1 == circleCount) {
                if (i + 1 == circleCount) {
                    i++;
                }
                int[] pegOrder = pegRandomizer(); //randomize the peg order
                VBox vbox1;
                VBox vbox2;
                HBox hbox;

                if (numberOfPegs == 3) { //check to see how many pegs should be in each row, 3, 4, or 5. (defaults to 4)
                    vbox1 = new VBox(pegs[i - pegOrder[0]], pegs[i - pegOrder[1]]);
                    vbox2 = new VBox(pegs[i - pegOrder[2]]);
                    hbox = new HBox(vbox1, vbox2);

                } else if (numberOfPegs == 5) {
                    vbox1 = new VBox(pegs[i - pegOrder[0]], pegs[i - pegOrder[1]], pegs[i - pegOrder[2]]);
                    vbox2 = new VBox(pegs[i - pegOrder[3]], pegs[i - pegOrder[4]]);
                    hbox = new HBox(vbox1, vbox2);

                } else {
                    vbox1 = new VBox(pegs[i - pegOrder[0]], pegs[i - pegOrder[1]]);
                    vbox2 = new VBox(pegs[i - pegOrder[2]], pegs[i - pegOrder[3]]);
                    hbox = new HBox(vbox1, vbox2);
                }

                vbox1.setSpacing(5);
                vbox2.setSpacing(5);
                hbox.setSpacing(5);

                vbox1.setAlignment(Pos.CENTER);
                vbox2.setAlignment(Pos.CENTER);
                hbox.setAlignment(Pos.CENTER);

                grid.add(hbox, numberOfPegs, heightCounter);
                heightCounter++;

            }

        }

        Button btn = createButton(code, circles, pegs, codeGenerator, pegGenerator, stage); //create the check button with the createButton function

        grid.add(btn, numberOfPegs, numberOfRows + 1); //add the check button to the grid


        Scene scene = new Scene(grid, 300, 600); //set window size

        stage.setTitle("MasterMind"); //set title

        stage.setScene(scene); //set the stage scene

        System.out.println("MasterMind Launched");
        if(darkMode) {
            grid.setStyle("-fx-background-color: #1e2124;"); //set Background color
        }else{
            grid.setStyle("-fx-background-color: #FFFFFF;");
        }

        stage.show();
    }

    public void parseConfig(){ //function to parse property file
        configParser parseConfig = new configParser();

        try {
            numberOfRows = Integer.parseInt(parseConfig.getProperty("numberOfRows"));
            numberOfPegs = Integer.parseInt(parseConfig.getProperty("numberOfPegs"));
            allowDuplicate = Boolean.parseBoolean(parseConfig.getProperty("allowDuplicate"));
            allowBlank = Boolean.parseBoolean(parseConfig.getProperty("allowBlank"));
            darkMode = Boolean.parseBoolean(parseConfig.getProperty("darkMode"));
        }catch(Exception e){
                numberOfPegs = 4;
                numberOfRows = 8;
                allowDuplicate = false;
                allowBlank = false;
                darkMode = false;
                System.out.println("Invalid .properties file, using default configuration");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}