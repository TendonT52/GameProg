package object;

import java.awt.Graphics2D;

import load.resource.ImageObject;
import main.Drawable;
import main.GamePanel;

public abstract class GameObject implements Drawable {
	protected boolean isInside;
	protected String dialog;
	protected GamePanel gamePanel;
	protected ImageObject resorceImage;
	protected int worldX, worldY;
	protected int width, height;

	public GameObject(GamePanel gamePanel, int worldX, int worldY, int width, int height) {
		this.gamePanel = gamePanel;
		this.worldX = worldX;
		this.worldY = worldY;
		this.width = width;
		this.height = height;
		this.dialog = "";
		checkInside(worldX, worldY);
	}

	public void checkInside(int worldX, int worldY) {
		if (worldX - this.worldX >= 0 && worldX - this.worldX <= width - (this.gamePanel.getTileSize())
				&& worldY - this.worldY >= 0 && worldY - this.worldY <= height - 0.5 * (this.gamePanel.getTileSize())) {
			isInside = true;
		} else {
			isInside = false;
		}
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}

	public void draw(Graphics2D graphics2d) {

		int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
		int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

		graphics2d.drawImage(resorceImage.getImage(), screenX, screenY, null);

	}

	public boolean isInside() {
		return isInside;
	}

	public void setInside(boolean isInside) {
		this.isInside = isInside;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public int getWorldX() {
		return worldX;
	}

	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	public int getWorldY() {
		return worldY;
	}

	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getDialog() {
		return dialog;
	}

	public ImageObject getResorceImage() {
		return resorceImage;
	}

	public void setResorceImage(ImageObject resorceImage) {
		this.resorceImage = resorceImage;
	}

}
