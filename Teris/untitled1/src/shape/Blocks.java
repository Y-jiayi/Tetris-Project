package shape;//import com.sun.tools.javac.Main;

import view.MainFrame;
import java.awt.*;

public class Blocks {
//    public MainFrame mainFrame = new MainFrame();
//    private static Tuple unitSize = MainFrame.getBlockSize();
//    private Color color = Shape.randomColor();
//
//    public Blocks(Tuple tuple, Color color) {
//        Blocks.unitSize = tuple;
//        this.color = color;
//    }
//
//    public Blocks(Color color) {
//        this.color = color;
//    }
//
//    public Blocks(Tuple unitSize) {
//        Blocks.unitSize = unitSize;
//    }
//
//    public Blocks(int length) {
//        Blocks.unitSize = new Tuple(length, length);
//    }
//
//    public Blocks(int width, int height) {
//        Blocks.unitSize = new Tuple(width, height);
//    }
//
//    public Blocks() {
//        //TODO: default value
//    }
//
//
//    /**
//     * 此处默认block为正方形
//     *
//     * @return Return half the length of each side of the square.
//     */
//    public static int getHalfLength() {
//        try {
//            if (Blocks.unitSize.getX() == unitSize.getY())
//                return unitSize.getX() / 2;
//            else throw new Exception("Not a square! width: " + unitSize.getX() + " height: " + unitSize.getY());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//    public Tuple getUnitSize() {
//        return unitSize;
//    }
//
//    public Color getColor() {
//        return color;
//    }
//
//    public void setColor(Color color) {
//        this.color = color;
//    }
}