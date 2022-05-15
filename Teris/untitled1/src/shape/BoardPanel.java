package shape;

import view.MainFrame;

import javax.swing.*;
import java.awt.*;

//游戏界面,绘制线条,边框,不同的形状
public class BoardPanel extends JPanel {



	//界面的内边距
	private static final int BORDER_WIDTH = 5;
	
	//水平方向格子的数量
	public static final int COL_COUNT = 10;

	//垂直方向格子数量
	private static final int VISIBLE_ROW_COUNT = 20;
	

	private static final int HIDDEN_ROW_COUNT = 2;
	

	public static final int ROW_COUNT = VISIBLE_ROW_COUNT + HIDDEN_ROW_COUNT;
	

	public static final int TILE_SIZE = 24;
	

	public static final int SHADE_WIDTH = 4;
	

	private static final int CENTER_X = COL_COUNT * TILE_SIZE / 2;
	

	private static final int CENTER_Y = VISIBLE_ROW_COUNT * TILE_SIZE / 2;
		

	public static final int PANEL_WIDTH = COL_COUNT * TILE_SIZE + BORDER_WIDTH * 2;
	

	public static final int PANEL_HEIGHT = VISIBLE_ROW_COUNT * TILE_SIZE + BORDER_WIDTH * 2;
	

	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 16);


	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);

	private MainFrame tetris;
	
	//二维数组保存游戏界面,null表示该格子为空
	private ShapeType[][] shapes;
		
	//构造函数初始化
	public BoardPanel(MainFrame tetris) {
		this.tetris = tetris;
		this.shapes = new ShapeType[ROW_COUNT][COL_COUNT];
		
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.darkGray);
	}
	
	//清空画面
	public void clear() {

		for(int i = 0; i < ROW_COUNT; i++) {
			for(int j = 0; j < COL_COUNT; j++) {
				shapes[i][j] = null;
			}
		}
	}
	
	//这个位置是否是合法的并且是空的
	public boolean isValidAndEmpty(ShapeType type, int x, int y, int rotation) {
				
		//判断X是否合法
		if(x < -type.getLeftInset(rotation) || x + type.getDimension() - type.getRightInset(rotation) >= COL_COUNT) {
			return false;
		}
		

		if(y < -type.getTopInset(rotation) || y + type.getDimension() - type.getBottomInset(rotation) >= ROW_COUNT) {
			return false;
		}

		for(int col = 0; col < type.getDimension(); col++) {
			for(int row = 0; row < type.getDimension(); row++) {
				if(type.isTile(col, row, rotation) && isOccupied(x + col, y + row)) {
					return false;
				}
			}
		}
		return true;
	}
	

	public void addPiece(ShapeType type, int x, int y, int rotation) {
		/*
		 * Loop through every tile within the piece and add it
		 * to the board only if the boolean that represents that
		 * tile is set to true.
		 */
		for(int col = 0; col < type.getDimension(); col++) {
			for(int row = 0; row < type.getDimension(); row++) {
				if(type.isTile(col, row, rotation)) {
					setShape(col + x, row + y, type);
				}
			}
		}
	}
	

	public int checkLines() {
		int completedLines = 0;
		
		/*
		 * Here we loop through every line and check it to see if
		 * it's been cleared or not. If it has, we increment the
		 * number of completed lines and check the next row.
		 * 
		 * The checkLine function handles clearing the line and
		 * shifting the rest of the board down for us.
		 */
		for(int row = 0; row < ROW_COUNT; row++) {
			if(checkLine(row)) {
				completedLines++;
			}
		}
		return completedLines;
	}
			

	private boolean checkLine(int line) {
		/*
		 * Iterate through every column in this row. If any of them are
		 * empty, then the row is not full.
		 */
		for(int col = 0; col < COL_COUNT; col++) {
			if(!isOccupied(col, line)) {
				return false;
			}
		}
		
		/*
		 * Since the line is filled, we need to 'remove' it from the game.
		 * To do this, we simply shift every row above it down by one.
		 */
		for(int row = line - 1; row >= 0; row--) {
			for(int col = 0; col < COL_COUNT; col++) {
				setShape(col, row + 1, getShape(col, row));
			}
		}
		return true;
	}
	
	

	private boolean isOccupied(int x, int y) {
		return shapes[y][x] != null;
	}

	private void setShape(int  x, int y, ShapeType type) {
		shapes[y][x] = type;
	}
		

	private ShapeType getShape(int x, int y) {
		return shapes[y][x];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.translate(BORDER_WIDTH, BORDER_WIDTH);
		
		//暂停状态
		if(tetris.isPaused()) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.WHITE);
			String msg = "PAUSED";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, CENTER_Y);
			//还未开始游戏
		} else if(tetris.isNewGame()) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.WHITE);

			String msg = "TETRIS";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 150);
			g.setFont(SMALL_FONT);
			msg = "Press [1]: E  [2]: N [3]: H to Play" ;
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 300);
			//游戏中
		} else {
			//已经存在的方块们
			//绘制每个格子上对应的内容,没有就不处理
			for(int x = 0; x < COL_COUNT; x++) {
				for(int y = HIDDEN_ROW_COUNT; y < ROW_COUNT; y++) {
					ShapeType tile = getShape(x, y);
					if(tile != null) {
						drawTile(tile, x * TILE_SIZE, (y - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
					}
				}
			}
			

			ShapeType type = tetris.getPieceType();
			int pieceCol = tetris.getPieceCol();
			int pieceRow = tetris.getPieceRow();
			int rotation = tetris.getPieceRotation();
			
			//绘制当前正在控制的形状
			for(int col = 0; col < type.getDimension(); col++) {
				for(int row = 0; row < type.getDimension(); row++) {
					if(pieceRow + row >= 2 && type.isTile(col, row, rotation)) {
						drawTile(type, (pieceCol + col) * TILE_SIZE, (pieceRow + row - HIDDEN_ROW_COUNT) * TILE_SIZE, g);
					}
				}
			}

			Color base = type.getBaseColor();
			base = new Color(base.getRed(), base.getGreen(), base.getBlue(), 20);
			for(int lowest = pieceRow; lowest < ROW_COUNT; lowest++) {
				if(isValidAndEmpty(type, pieceCol, lowest, rotation)) {					
					continue;
				}

				lowest--;
				

				break;
			}
			//绘制网格
			g.setColor(Color.BLACK);
			for(int x = 0; x < COL_COUNT; x++) {
				for(int y = 0; y < VISIBLE_ROW_COUNT; y++) {
					g.drawLine(0, y * TILE_SIZE, COL_COUNT * TILE_SIZE, y * TILE_SIZE);
					g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, VISIBLE_ROW_COUNT * TILE_SIZE);
				}
			}
		}

		g.setColor(Color.WHITE);
		g.drawRect(0, 0, TILE_SIZE * COL_COUNT, TILE_SIZE * VISIBLE_ROW_COUNT);
	}

	private void drawTile(ShapeType type, int x, int y, Graphics g) {
		drawTile(type.getBaseColor(), x, y, g);
	}
	

	private void drawTile(Color base,  int x, int y, Graphics g) {
		

		g.setColor(base);
		g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

	}

}
