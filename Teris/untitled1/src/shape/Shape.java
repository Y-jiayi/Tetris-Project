package shape;

import jdk.nashorn.internal.ir.Block;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Shape extends JPanel {

    //存储4个blocks
    //存储location
    protected ArrayList<Blocks> blocks = new ArrayList<>();
    protected ArrayList<Tuple> locations = new ArrayList<>();
    public Color color = randomColor();

    public void setBlocks(ArrayList<Blocks> blocks) {
        this.blocks = blocks;
    }

    public ArrayList<Tuple> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Tuple> locations) {
        this.locations = locations;
    }


    //Color color = MainFrame.getShapePen();

    public Shape() {
        color = randomColor();
        try {
            blocks.add(new Blocks());
            locations.add(new Tuple(0, 0));
//            throw new Exception("Warning: default block generated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Shape(ArrayList<Blocks> blocks, ArrayList<Tuple> locations) {
        this.blocks = blocks;
        this.locations = locations;
    }

    public ArrayList<Blocks> getBlocks() {
        return blocks;
    }

    public ArrayList<Tuple> getShapeLocation() {
        return locations;
    }

    public static Color randomColor(){
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }
}
