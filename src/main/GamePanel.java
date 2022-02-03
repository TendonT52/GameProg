package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import block.BlockManager;
import entity.Player;
import load.resource.ImageObject;
import load.resource.Sound;
import object.GasStation;
import object.Store;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	private final int originalTileSize = 50; // 50x50 tile
	private final int scale = 1;
	private final int tileSize = originalTileSize * scale;
	private final int screenWidth = 1080; // 1250 pixels
	private final int screenHeight = 720; // 800 pixels

	// WORLD SETTING
	private final int maxWorldCol = 92;
	private final int maxWorldRow = 135;
	private final int worldWidth = tileSize * maxWorldCol;
	private final int worldHeight = tileSize * maxWorldRow;
	private Color color1 = new Color(84, 49, 10);
	private Color color2 = new Color(48, 25, 0);

	// SYSTEM
	public Sound soundEffect = new Sound();
	public Sound music = new Sound();
	private Thread gameThread;
	private KeyHandler keyHandler = new KeyHandler(this);
	private Player player = new Player(this, keyHandler);
	private BlockManager blockManager = new BlockManager(this);
	private UI ui = new UI(this);
	private CollisionChecker collisionChecker = new CollisionChecker(this);
	private ImageObject imageGasStation = new ImageObject("/image/gas_station.png");
	private ImageObject imageStore = new ImageObject("/image/store.png");
	private GasStation gasStation;
	private Store store;

	// FPS
	public static final int fps = 60;

	// GAME STATE
	private int gameState;
	private final int titleState = 0;
	private final int playState = 1;
	private final int pauseState = 2;
	private final int dialogueState = 3;
	private final int outOfFuelState = 4;
	private final int winState = 5;

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
		initObject();
		setupGame();
	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();

	}

	public void setupGame() {
		gameState = titleState;
		playMusic(0);
	}

	public void initObject() {
		this.gasStation = new GasStation(this, 1150, 650, tileSize * 3, tileSize * 3);
		gasStation.setDialog("Press space to buy fuel!");
		this.store = new Store(this, 1550, 600, tileSize * 3, tileSize * 4);
		store.setDialog("Press space to sell your cargo for cash!");
	}

	public void run() {
		double drawInterval = 1000000000 / fps;
		double delta = 0;
		double lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	public void update() {
		if (gameState == playState) {
			player.update();
			blockManager.deleteBlock(player);
			gasStation.checkInside(player.getWorldX(), player.getWorldY());
			store.checkInside(player.getWorldX(), player.getWorldY());
		}

	}

	public void paintComponent(Graphics graphics) {

		super.paintComponent(graphics);
		Graphics2D graphics2d = (Graphics2D) graphics;
		if (gameState == playState || gameState == pauseState) {
			blockManager.draw(graphics2d);
			gasStation.draw(graphics2d);
			store.draw(graphics2d);
			player.draw(graphics2d);
			ui.drawScreen(graphics2d);

		} else if (gameState == titleState || gameState == outOfFuelState || gameState == winState) {
			ui.drawScreen(graphics2d);
		}
		graphics2d.dispose();
	}

	public void playMusic(int number) {
		music.setFile(number);
		music.play();
		music.loop();

	}

	public void stopMusic() {
		music.stop();
	}

	public void playSE(int number) {
		soundEffect.setFile(number);
		soundEffect.play();
	}

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}

	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public void setKeyHandler(KeyHandler keyHandler) {
		this.keyHandler = keyHandler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public BlockManager getBlockManager() {
		return blockManager;
	}

	public void setBlockManager(BlockManager blockManager) {
		this.blockManager = blockManager;
	}

	public UI getUi() {
		return ui;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

	public CollisionChecker getCollisionChecker() {
		return collisionChecker;
	}

	public void setCollisionChecker(CollisionChecker collisionChecker) {
		this.collisionChecker = collisionChecker;
	}

	public GasStation getGasStation() {
		return gasStation;
	}

	public void setGasStation(GasStation gasStation) {
		this.gasStation = gasStation;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	public int getOriginalTileSize() {
		return originalTileSize;
	}

	public int getScale() {
		return scale;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getMaxWorldCol() {
		return maxWorldCol;
	}

	public int getMaxWorldRow() {
		return maxWorldRow;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public int getPlayState() {
		return playState;
	}

	public int getPauseState() {
		return pauseState;
	}

	public int getDialogueState() {
		return dialogueState;
	}

	public int getOutOfFuelState() {
		return outOfFuelState;
	}

	public int getTitleState() {
		return titleState;
	}

	public int getWinState() {
		return winState;
	}

	public ImageObject getImageGasStation() {
		return imageGasStation;
	}

	public void setImageGasStation(ImageObject imageGasStation) {
		this.imageGasStation = imageGasStation;
	}

	public ImageObject getImageStore() {
		return imageStore;
	}

	public void setImageStore(ImageObject imageStore) {
		this.imageStore = imageStore;
	}

}
