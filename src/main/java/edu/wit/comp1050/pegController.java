package edu.wit.comp1050;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class pegController {
    private final Color[] colors;

    public pegController(Color[] colorList){
        colors = colorList;
    } //constructor that takes in the list of colors to be used for pegs

    public Color nextColor(Color[] colorChoices, Color currentColor) { //function to determine what the next color to be rotated to is
        for (int i = 0; i < colorChoices.length; i++) {
            if (colorChoices[i] == currentColor) {
                if (i + 1 == colorChoices.length) {
                    return colorChoices[0];
                } else {
                    return colorChoices[i + 1];
                }
            }
        }
        return colorChoices[0]; //default return
    }

    public Circle generatePeg() { //function to create a new peg
        Circle circle = new Circle();

        //Setting the properties of the circle
        circle.setCenterX(300.0f);
        circle.setCenterY(180.0f);
        circle.setRadius(20.0f);

        //Setting color to the circle
        circle.setFill(Color.DARKGRAY);

        //Setting the stroke width
        circle.setStrokeWidth(1);

        //Setting color to the stroke
        circle.setStroke(Color.BLACK);

        circle.setOnMouseClicked(e -> circle.setFill(nextColor(colors, (Color) circle.getFill()))); //when the circle is clicked rotate to the next color

        return circle;
    }

    public Circle generateCodePegs() { //function to generate the blank answer pegs
        Circle circle = new Circle();

        //Setting the properties of the circle
        circle.setCenterX(300.0f);
        circle.setCenterY(180.0f);
        circle.setRadius(5.0f);

        //Setting color to the circle
        circle.setFill(Color.WHITE);

        //Setting the stroke width
        circle.setStrokeWidth(1);

        //Setting color to the stroke
        circle.setStroke(Color.BLACK);

        return circle;
    }

    public void setCurrentRowStroke(Circle[] circle, int rowCounter, int numberOfPegs) { //function to set a stroke around the current row
        int rowMin = numberOfPegs * rowCounter;
        int rowMax = numberOfPegs * rowCounter + numberOfPegs;
        int rowPrev = numberOfPegs * rowCounter - numberOfPegs;
        if(rowCounter == 0) { //if the user is on the first row of pegs
            for (int j = 0; j < numberOfPegs; j++) {
                circle[j].setStroke(Color.web("#7289da")); //set active circles to blue border
            }
        }else { //else the user is not on row 0
            for (int i = rowMin; i < rowMax; i++) {
                circle[i].setStroke(Color.web("#7289da")); //set active circles to blue border
            }

            for (int i = rowPrev; i < rowMin; i++) { //loop to set the previous rows stroke back to black
                circle[i].setStroke(Color.BLACK);
            }
        }
    }
}
