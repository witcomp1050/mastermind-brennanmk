package sample;

import javafx.scene.paint.Color;
import java.util.Random;

public class randomColorGenerator {
    public Color[] colorChoices; //declaration of color choice array
    public randomColorGenerator(Color[] colors){
        colorChoices = colors;
    } //constructor that takes in the different color choices
    public Color generateColor() { //function to generate a random color from the array of colors passed in
        Random rand = new Random();
        int randomNum = rand.nextInt((colorChoices.length));
        return colorChoices[randomNum];
    }
}
