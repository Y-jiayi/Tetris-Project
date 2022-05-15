package view;
import shape.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class MainFrame extends JFrame {


	private static final long FRAME_TIME = 1000L / 50L;
	
	private static final ShapeType values[]={new S_shape1(),new T_shape2(),new Z_shape3(),
			new Line_shape4(),new L_shape5(),new MirroredL_shape6(),new Square_shape7()};
	private static final int TYPE_COUNT = values.length;
		

	private BoardPanel board;
	

	private SidePanel side;
	

	private boolean isPaused;
	

	private boolean isNewGame;
	

	private boolean isGameOver;
	

//	private int level;
	

	private int score;
	

	private Random random;
	

	private Clock logicTimer;
				

	private ShapeType currentType;
	

	private ShapeType nextType;
		

	private int currentCol;

	private int currentRow;

	private int currentRotation;
		

	private int dropCooldown;
	

	private double gameSpeed;
	private double oneStep=1;
	private double gameSpeedMin=1.0;
	private double gameSpeedMax=9.0;

	private MainFrame() {

		super("Tetris");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		

		this.board = new BoardPanel(this);
		this.side = new SidePanel(this);
		

		add(board, BorderLayout.CENTER);
		add(side, BorderLayout.EAST);
		
		//键盘监听事件,
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("press: "+e.getKeyCode()+" speed: "+gameSpeed);
				switch(e.getKeyCode()) {
					//+
					case 61:
						if(gameSpeed+oneStep<gameSpeedMax)
							gameSpeed+=oneStep;
						else gameSpeed=gameSpeedMax;
						break;
					//-
						case 45:
							if(gameSpeed-oneStep>gameSpeedMin)
								gameSpeed-=oneStep;
							else gameSpeed=gameSpeedMin;
							break;
				//S
				case KeyEvent.VK_S:
					if(!isPaused && dropCooldown == 0) {
						logicTimer.setCyclesPerSecond(25.0f);
					}
					break;
				//left
				case KeyEvent.VK_A:
					if(!isPaused && board.isValidAndEmpty(currentType, currentCol - 1, currentRow, currentRotation)) {
						currentCol--;
					}
					break;
				//right
				case KeyEvent.VK_D:
					if(!isPaused && board.isValidAndEmpty(currentType, currentCol + 1, currentRow, currentRotation)) {
						currentCol++;
					}
					break;
					//逆时针旋转
				case KeyEvent.VK_Q:
					if(!isPaused) {
						rotatePiece((currentRotation == 0) ? 3 : currentRotation - 1);
					}
					break;
				
					//顺时针旋转
				case KeyEvent.VK_E:
					if(!isPaused) {
						rotatePiece((currentRotation == 3) ? 0 : currentRotation + 1);
					}
					break;
					
				//暂停和恢复
				case KeyEvent.VK_P:
					if(!isGameOver && !isNewGame) {
						isPaused = !isPaused;
						logicTimer.setPaused(isPaused);
					}
					break;

//				case KeyEvent.VK_ENTER:
//					if(isGameOver || isNewGame) {
//						gameSpeed=1;
//						resetGame();
//					}
//					break;
					//简单
					case 49:
						if(isGameOver || isNewGame) {
							gameSpeed=1;

							resetGame();
						}
						break;
					//一般
					case 50:
						if(isGameOver || isNewGame) {
							gameSpeed=5;

							resetGame();
						}
						break;
						//困难
					case 51:
						if(isGameOver || isNewGame) {
							gameSpeed=9;

							resetGame();
						}
						break;
//					case KeyEvent.VK_R:
//						resetGame();
//						break;
				}


			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				switch(e.getKeyCode()) {

				case KeyEvent.VK_S:
					logicTimer.setCyclesPerSecond(gameSpeed);
					logicTimer.reset();
					break;
				}
				
			}
			
		});

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void startGame() {

		this.random = new Random();
		this.isNewGame = true;
//		this.gameSpeed = 1.0f;

		this.logicTimer = new Clock(gameSpeed);
		logicTimer.setPaused(true);
		
		while(true) {
			long start = System.nanoTime();

			logicTimer.update();
			


			if(logicTimer.hasElapsedCycle()) {
				updateGame();
			}
		

			if(dropCooldown > 0) {
				dropCooldown--;
			}
			

			renderGame();

			long delta = (System.nanoTime() - start) / 1000000L;
			if(delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	private void updateGame() {

		if(board.isValidAndEmpty(currentType, currentCol, currentRow + 1, currentRotation)) {

			currentRow++;
		} else {

			board.addPiece(currentType, currentCol, currentRow, currentRotation);
			

			int cleared = board.checkLines();
			if(cleared > 0) {
				score += 50 << cleared;
			}
			

			logicTimer.setCyclesPerSecond(gameSpeed);
			logicTimer.reset();
			

			dropCooldown = 25;
			

			spawnPiece();
		}		
	}
	

	private void renderGame() {
		board.repaint();
		side.repaint();
	}
	

	private void resetGame() {
//		this.level = 1;
		this.score = 0;
//		this.gameSpeed = 1.0f;
		for(int i=0;i<values.length;i++)
		{
			values[i].setColor(new Color((int)(Math.random()*255),(int)(Math.random()*255),
				(int)(Math.random()*255)));
		}
		//随机生成下一个形状
		this.nextType = values[random.nextInt(TYPE_COUNT)];
//		this.nextType.setColor(new Color((int)(Math.random()*255),(int)(Math.random()*255),
//				(int)(Math.random()*255)));

		this.isNewGame = false;
		this.isGameOver = false;		
		board.clear();
		logicTimer.reset();
		logicTimer.setCyclesPerSecond(gameSpeed);
		spawnPiece();
	}
		

	private void spawnPiece() {

		this.currentType = nextType;
		this.currentCol = currentType.getSpawnColumn();
		this.currentRow = currentType.getSpawnRow();
		this.currentRotation = 0;
		this.nextType = values[random.nextInt(TYPE_COUNT)];

		if(!board.isValidAndEmpty(currentType, currentCol, currentRow, currentRotation)) {
			this.isGameOver = true;
			logicTimer.setPaused(true);
		}		
	}


	private void rotatePiece(int newRotation) {

		int newColumn = currentCol;
		int newRow = currentRow;
		

		int left = currentType.getLeftInset(newRotation);
		int right = currentType.getRightInset(newRotation);
		int top = currentType.getTopInset(newRotation);
		int bottom = currentType.getBottomInset(newRotation);

		if(currentCol < -left) {
			newColumn -= currentCol - left;
		} else if(currentCol + currentType.getDimension() - right >= BoardPanel.COL_COUNT) {
			newColumn -= (currentCol + currentType.getDimension() - right) - BoardPanel.COL_COUNT + 1;
		}
		

		if(currentRow < -top) {
			newRow -= currentRow - top;
		} else if(currentRow + currentType.getDimension() - bottom >= BoardPanel.ROW_COUNT) {
			newRow -= (currentRow + currentType.getDimension() - bottom) - BoardPanel.ROW_COUNT + 1;
		}
		

		if(board.isValidAndEmpty(currentType, newColumn, newRow, newRotation)) {
			currentRotation = newRotation;
			currentRow = newRow;
			currentCol = newColumn;
		}
	}
	

	public boolean isPaused() {
		return isPaused;
	}

	public boolean isGameOver() {
		return isGameOver;
	}
	

	public boolean isNewGame() {
		return isNewGame;
	}

	public int getScore() {
		return score;
	}



	public ShapeType getPieceType() {
		return currentType;
	}
	

	public ShapeType getNextPieceType() {
		return nextType;
	}
	

	public int getPieceCol() {
		return currentCol;
	}

	public int getPieceRow() {
		return currentRow;
	}
	

	public int getPieceRotation() {
		return currentRotation;
	}

	public static void main(String[] args) {
		MainFrame tetris = new MainFrame();
		tetris.startGame();
	}

}
