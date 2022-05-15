//package view;
//
//import java.awt.*;
//import java.awt.event.*;
//import java.util.ArrayList;
//import java.util.Random;
//import javax.swing.Timer;
//import javax.swing.JPanel;
//
//import shape.*;
//import shape.Shape;
//
//class GamePanel extends JPanel implements ActionListener {
//    GameButton pause, start;
//    private int step = 10;
//    private int time = 1000;//需要改，游戏速度
//    private Timer timer;
//    Random r = new Random(System.currentTimeMillis());
//    private ArrayList<Shape> shapes = new ArrayList<>();
//    private Shape[] allRect = {new S_shape1(400, 0), new T_shape2(400, 0) ,new Z_shape3(400,0),new Line_shape4(400,0),
//            new L_shape5(5,0),new MirroredL_shape6(6,0),new Square_shape7(7,0)}; // 所有的方块类型，用16个字节来存储，俄罗斯方块图形都是在4*4格子里
//    private Shape rect; // 当前游戏下落的方块类型；
//    public MainFrame mainFrame;
//
//
//    public  GamePanel(MainFrame mainFrame) {
//        this.mainFrame = mainFrame;
//        pause = new GameButton(this, "暂停", "pause", 0, 0, 80, 40);
//        this.add(pause);
//        start = new GameButton(this, "开始游戏", "start", 100, 0, 80, 40);
//        this.add(start);
//        this.setLayout(null);//随机释放
//        this.setEnabled(false);
//        addKeyListener(new Adapter());
//
//        //allRect = ;//19种方块形状，如0x00cc就是   0000 表示一个2*2的正方形方块
//
//
//    }
//
//    private void removeAllShape() {
//        shapes.clear();
//    }
//
//    //TODO: rotate the shape
//    private static void rotate(Shape shape) {
//        int[][] a=new int[4][4];//创建4*4矩阵
//        Random r=new Random();
//        for(int i=0;i<4;i++){
//            for(int j=0;j<4;j++){
//                a[i][j]=r.nextInt(10);
//            }
//        }
//            //左转九十度
//            for(int i=0;i<4;i++){
//                for(int j=0;j<4;j++) {
//                    System.out.print(a[a[i].length - 1 - j][i] + " ");//行列转换，变换位置实现旋转
//                }
//    }}
//
//
//    private void generated() {
//        Random random = new Random();
//        Shape newShape = allRect[random.nextInt(7)];
//        //ranRect(newShape);
//        shapes.add(newShape);
//        //return newShape;
//    }
//        //TODO: random generate Shape.Shape
//    /*绘制下落方格形状*/
////    public void ranRect(Shape shape) {
////        Random random = new Random();
////        shape = allRect[random.nextInt(7)];// 随机生成方块类型（共7种，19个形状）
////        //return shape;
////    }
//
//    //图形下移
//    private void flush() {
//        //TODO: your flush algorithm,
//        for (Shape shape : shapes) {
//            for (Tuple location : shape.getLocations()) {
//                location.setY(location.getY() + 10);//移动距离
//            }
//        }
//        repaint();//重绘
//    }
//
//    /**
//     * Handle the keyboard event.
//     */
//    static class Adapter extends KeyAdapter {
//        @Override
//        public void keyPressed(KeyEvent e) {
//            int keyCode = e.getKeyCode();
//            switch (keyCode) {
////               case KeyEvent.VK_LEFT -> methodHere
////                case KeyEvent.VK_RIGHT -> methodHere
////                case KeyEvent.OTHER -> methodHere
//            }
//        }
//    }
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        draw(g);
//    }
//
//    private void draw(Graphics g) {
//        //g.setColor(mainFrame.getShapePen());
//        try {
//            for (Shape shape : shapes) {
//                ArrayList<Blocks> blocks = shape.getBlocks();
//                ArrayList<Tuple> locations = shape.getLocations();
//                if (blocks.size() == locations.size()) {
//                    for (int j = 0; j < blocks.size(); j++) {
//                        int len = blocks.get(j).getUnitSize().getX();
//                        int wid = blocks.get(j).getUnitSize().getY();
//                        Tuple location = locations.get(j);
//                        g.fillRect(location.getX(), location.getY(), len, wid);
//                    }
//                } else throw new Exception("Length of location is not same as blocks!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void hitCheck() {
//
//    }
//
//    private void startGame() {
//        //TODO:
//    }
//
//    private class Cycle implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            flush();
//        }
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals(pause.getActionCommand())) {
//            System.err.println("按下暂停按钮");
//            //TODO
//        } else if (e.getActionCommand().equals(start.getActionCommand())) {
//            System.err.println("按下开始按钮");
//            //TODO
//
//            generated();//面板上生成方块
//            timer = new Timer(time, new Cycle());//下落速度
//            timer.start();
//        }
//    }
//}
