package sample;

import javafx.scene.paint.Color;

import java.util.Random;

public class randomColorGenerator {
    public Color[] colorChoices;
    public randomColorGenerator(Color[] colors){
        colorChoices = colors;
    }
    public Color generateColor() {
        Random rand = new Random();
        int randomNum = rand.nextInt((colorChoices.length));
        return colorChoices[randomNum]; //default return
    }
}
