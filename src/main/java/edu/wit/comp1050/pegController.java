package edu.wit.comp1050;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class pegController {
    private final Color[] colors;

    public pegController(Color[] colorList){
        colors = colorList;
    }

    public Color nextColor(Color[] colorChoices, Color currentColor) {
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

    public Circle generatePeg() {
        Circle circle = new Circle();

        //Setting the properties of the circle
        circle.setCenterX(300.0f);
        circle.setCenterY(180.0f);
        circle.setRadius(20.0f);

        //Setting color to the circle
        circle.setFill(Color.BLACK);

        //Setting the stroke width
        circle.setStrokeWidth(1);

        //Setting color to the stroke
        circle.setStroke(Color.BLACK);

        circle.setOnMouseClicked(e -> circle.setFill(nextColor(colors, (Color) circle.getFill())));

        return circle;
    }

    public Circle generateCodePegs() {
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

    public void setCurrentRowStroke(Circle[] circle, int rowCounter, int numberOfPegs) {
        int rowMin = numberOfPegs * rowCounter;
        int rowMax = numberOfPegs * rowCounter + numberOfPegs;
        int rowPrev = numberOfPegs * rowCounter - numberOfPegs;
        if(rowCounter == 0) {
            for (int j = 0; j < numberOfPegs; j++) {
                circle[j].setStroke(Color.web("#7289da")); //set active circles to green border
            }
        }else {
            for (int i = rowMin; i < rowMax; i++) {
                circle[i].setStroke(Color.web("#7289da"));
            }

            for (int i = rowPrev; i < rowMin; i++) {
                circle[i].setStroke(Color.BLACK);
            }
        }
    }
}
