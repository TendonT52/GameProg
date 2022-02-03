package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.Random;
import load.resource.ImagePlayer;
import main.Animation;
import main.Drawable;
import main.GamePanel;
import main.KeyHandler;
import object.Ore_type;

public class Player implements Drawable {

	private int money = 300;
	private int lvlDrill = 9;
	private int fuel = 100;
	private int fuelCount = 0;
	private GamePanel gamePanel;
	public ImagePlayer skin = new ImagePlayer();

	private Animation player_animation_up;
	private Animation player_animation_down;
	private Animation player_animation_left;
	private Animation player_animation_right;
	private BufferedImage currentImage;

	private int x_axis, y_axis;
	private final int screenX;
	private final int screenY;
	private int hardness;
	private int[] cargo = new int[8];
	private KeyHandler keyH;
	private int worldX, worldY;
	private int velocity;
	private int maxVelocity;
	private String direction;
	private int goal;

	public Player(GamePanel gamePanel, KeyHandler keyH) {
		this.gamePanel = gamePanel;
		this.keyH = keyH;
		loadAnimation();
		screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
		screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);
		clearCargo();
		setDefaultValues();
		setGoal();
	}

	public void loadAnimation() {
		player_animation_up = new Animation(skin.player_animation_up);
		player_animation_down = new Animation(skin.player_animation_down);
		player_animation_left = new Animation(skin.player_animation_left);
		player_animation_right = new Animation(skin.player_animation_right);
	}

	private void setDefaultValues() {
		worldX = 800;
		worldY = 750;
		velocity = 2;
		direction = "STAND";
		currentImage = player_animation_right.getCurrentFrame();
	}

	public void update() {
		move();
		decreasefuel();
		goalUpdate();
		if (keyH.isSpacePressed() && gamePanel.getGasStation().isInside()) {
			gamePanel.getGasStation().buyFuel();
		}
		if (keyH.isSpacePressed() && gamePanel.getStore().isInside()) {
			gamePanel.getStore().sellCargo();
		}
	}

	public void draw(Graphics2D graphics2d) {

		x_axis = screenX;
		y_axis = screenY;

		if (screenX > worldX) {
			x_axis = worldX;
		}
		if (screenY > worldY) {
			y_axis = worldX;
		}
		int rightOffset = gamePanel.getScreenWidth() - screenX;
		if (rightOffset > gamePanel.getWorldWidth() - worldX) {
			x_axis = gamePanel.getScreenWidth() - (gamePanel.getWorldWidth() - worldX);
		}
		int buttomOffset = gamePanel.getScreenHeight() - screenY;
		if (buttomOffset > gamePanel.getWorldHeight() - worldY) {
			y_axis = gamePanel.getScreenHeight() - (gamePanel.getWorldHeight() - worldY);
		}
		switch (direction) {
		case "STAND": {
			graphics2d.drawImage(currentImage, x_axis, y_axis, null);
			break;
		}
		case "UP": {
			player_animation_up.drawAnimation(graphics2d, x_axis, y_axis);
			currentImage = player_animation_up.getCurrentFrame();
			break;
		}
		case "DOWN": {
			player_animation_down.drawAnimation(graphics2d, x_axis, y_axis);
			currentImage = player_animation_down.getCurrentFrame();
			break;
		}
		case "LEFT": {
			player_animation_left.drawAnimation(graphics2d, x_axis, y_axis);
			currentImage = player_animation_left.getCurrentFrame();
			break;
		}
		case "RIGHT": {
			player_animation_right.drawAnimation(graphics2d, x_axis, y_axis);
			currentImage = player_animation_right.getCurrentFrame();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	public void move() {
		if (keyH.isUpPressed()) {
			direction = "UP";
			if (worldX % 50 <= maxVelocity) {
				snapToTileX();

				hardness = gamePanel.getCollisionChecker().checkHardness(this);
				if (hardness <= lvlDrill) {
					int speed = velocity + (lvlDrill / 3) - hardness;
					if (speed < 1) {
						setWorldY(getWorldY() - 1);
					} else {
						setWorldY(getWorldY() - speed);
					}

				}
			} else {
				goToTileX();
			}
			gamePanel.getBlockManager().deleteBlock(this);
		} else if (keyH.isDownPressed()) {
			direction = "DOWN";
			if (worldX % 50 <= maxVelocity) {
				snapToTileX();

				hardness = gamePanel.getCollisionChecker().checkHardness(this);
				if (hardness <= lvlDrill) {
					int speed = velocity + (lvlDrill / 3) - hardness;
					if (speed < 1) {
						setWorldY(getWorldY() + 1);
					} else {
						setWorldY(getWorldY() + speed);
					}
				}
			} else {
				goToTileX();
			}
		} else if (keyH.isLeftPressed()) {
			direction = "LEFT";
			if (worldY % 50 <= maxVelocity) {
				snapToTileY();

				hardness = gamePanel.getCollisionChecker().checkHardness(this);
				if (hardness <= lvlDrill) {
					int speed = velocity + (lvlDrill / 3) - hardness;
					if (speed < 1) {
						setWorldX(getWorldX() - 1);
					} else {
						setWorldX(getWorldX() - speed);
					}

				}
			} else {
				goToTileY();
			}
		} else if (keyH.isRightPressed()) {
			direction = "RIGHT";

			if (worldY % 50 <= maxVelocity) {

				snapToTileY();

				hardness = gamePanel.getCollisionChecker().checkHardness(this);
				if (hardness <= lvlDrill) {
					int speed = velocity + (lvlDrill / 3) - hardness;
					if (speed < 1) {
						setWorldX(getWorldX() + 1);
					} else {
						setWorldX(getWorldX() + speed);
					}

				}
			} else {
				goToTileY();
			}
		} else {
			direction = "STAND";
		}
	}

	public void decreasefuel() {
		if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {
			if (fuelCount >= 20) {
				fuel--;
				fuelCount = 0;
			} else {
				fuelCount++;
			}
		}
		if (fuel <= 0) {
			clearCargo();
			setWorldX(800);
			setWorldY(750);
			gamePanel.setGameState(4);
		}
	}

	public void setGoal() {
		Random random = new Random();
		this.goal = random.nextInt(3000) + 2650;

	}

	public void goalUpdate() {
		if (this.money >= this.goal) {
			gamePanel.setGameState(5);
		}
	}

	public void snapToTileX() {
		int number = worldX % 50;
		if (number > 25) {
			setWorldX((getWorldX() / 50) * 50 + 50);
		} else {
			setWorldX((getWorldX() / 50) * 50);
		}
	}

	public void snapToTileY() {
		int number = worldY % 50;
		if (number > 25) {
			setWorldY((getWorldY() / 50) * 50 + 50);
		} else {
			setWorldY((getWorldY() / 50) * 50);
		}
	}

	public void goToTileXX() {

		int number = worldX % 50;
		if (number > 25) {
			setWorldX(getWorldX() + 1);
		} else if (number > 0) {
			setWorldX(getWorldX() - 1);
		}
	}

	public void goToTileYY() {
		int number = worldY % 50;
		if (number > 25) {
			setWorldY(getWorldY() + 1);
		} else if (number > 0) {
			setWorldY(getWorldY() - 1);
		}
	}

	public void goToTileX() {
		switch (direction) {

		case "LEFT": {
			setWorldX(getWorldX() - 1);
			break;
		}
		case "RIGHT": {
			setWorldX(getWorldX() + 1);
			break;
		}
		default:
			goToTileXX();
		}
	}

	public void goToTileY() {
		switch (direction) {
		case "UP": {
			setWorldY(getWorldY() - 1);
			break;
		}
		case "DOWN": {
			setWorldY(getWorldY() + 1);
			break;
		}
		default:
			goToTileYY();
		}
	}

	public void clearCargo() {
		for (int index = 0; index < cargo.length; index++) {
			cargo[index] = 0;
		}
	}

	public void addTocargo(Ore_type ore) {

		int id = ore.getId();
		if (id != -1) {
			gamePanel.playSE(4);
			gamePanel.getUi().showMessage("+" + ore);
			cargo[id] += 1;
		}
	}

	public int getWorldX() {
		return worldX;
	}

	public void setWorldX(int worldX) {
		hardness = gamePanel.getCollisionChecker().checkHardness(this);
		if (worldX >= gamePanel.getTileSize() && worldX <= gamePanel.getWorldWidth() - 2 * gamePanel.getTileSize()
				&& hardness <= lvlDrill) {
			this.worldX = worldX;
		}
	}

	public int getWorldY() {
		return worldY;
	}

	public void setWorldY(int worldY) {
		hardness = gamePanel.getCollisionChecker().checkHardness(this);
		if (worldY >= 750 && worldY <= gamePanel.getWorldHeight() - 2 * gamePanel.getTileSize()
				&& hardness <= lvlDrill) {
			this.worldY = worldY;
		}
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public int getFuelCount() {
		return fuelCount;
	}

	public void setFuelCount(int fuelCount) {
		this.fuelCount = fuelCount;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public Animation getPlayer_animation_up() {
		return player_animation_up;
	}

	public void setPlayer_animation_up(Animation player_animation_up) {
		this.player_animation_up = player_animation_up;
	}

	public Animation getPlayer_animation_down() {
		return player_animation_down;
	}

	public void setPlayer_animation_down(Animation player_animation_down) {
		this.player_animation_down = player_animation_down;
	}

	public Animation getPlayer_animation_left() {
		return player_animation_left;
	}

	public void setPlayer_animation_left(Animation player_animation_left) {
		this.player_animation_left = player_animation_left;
	}

	public Animation getPlayer_animation_right() {
		return player_animation_right;
	}

	public void setPlayer_animation_right(Animation player_animation_right) {
		this.player_animation_right = player_animation_right;
	}

	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
	}

	public int getLvlDrill() {
		return lvlDrill;
	}

	public void setLvlDrill(int lvlDrill) {
		this.lvlDrill = lvlDrill;
	}

	public int[] getCargo() {
		return cargo;
	}

	public void setCargo(int[] cargo) {
		this.cargo = cargo;
	}

	public KeyHandler getKeyH() {
		return keyH;
	}

	public void setKeyH(KeyHandler keyH) {
		this.keyH = keyH;
	}

	public int getScreenX() {
		return screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public int getX_axis() {
		return x_axis;
	}

	public void setX_axis(int x) {
		this.x_axis = x;
	}

	public int getY_axis() {
		return y_axis;
	}

	public void setY_axis(int y) {
		this.y_axis = y;
	}

	public int getHardness() {
		return hardness;
	}

	public void setHardness(int hardness) {
		this.hardness = hardness;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int vel) {
		this.velocity = vel;
	}

	public int getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(int maxVel) {
		this.maxVelocity = maxVel;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getGoal() {
		return goal;
	}

}
