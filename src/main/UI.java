package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import load.resource.Icon;
import object.Ore_type;

public class UI {

	private Graphics2D graphics2d;
	private GamePanel gamePanel;
	private Font arial_30 = new Font("Arial", Font.PLAIN, 30);
	private BufferedImage image;
	private boolean isMessageOn = false;
	private String message = "";
	private int messageCounter = 0;
	private String currentDialogue = "";
	public int commandNum = 0;
	public Icon resIcon = new Icon();

	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void showMessage(String text) {
		message = text;
		isMessageOn = true;
	}

	public void drawScreen(Graphics2D graphics2d) {
		this.graphics2d = graphics2d;
		graphics2d.setFont(arial_30);
		graphics2d.setColor(Color.WHITE);

		if (gamePanel.getGameState() == gamePanel.getPlayState()) {
			drawPlayScreen();
		} else if (gamePanel.getGameState() == gamePanel.getPauseState()) {
			drawPauseScreen();
		} else if (gamePanel.getGameState() == gamePanel.getDialogueState()) {
			drawDialogueScreen();
		} else if (gamePanel.getGameState() == gamePanel.getTitleState()) {
			drawTitleScreen();
		} else if (gamePanel.getGameState() == gamePanel.getOutOfFuelState()) {
			drawRunOutFual();
		} else if (gamePanel.getGameState() == gamePanel.getWinState()) {
			drawWin();
		}

	}

	public void drawPicture(BufferedImage image, int xImage, int yImage, int width, int height, int number, int xString,
			int yString) {
		graphics2d.drawImage(image, xImage, yImage, width, height, null);
		graphics2d.drawString(String.valueOf(number), xString, yString);
	}

	public void drawPlayScreen() {
		graphics2d.setFont(arial_30);
		graphics2d.setColor(Color.WHITE);

		drawPicture(resIcon.ore_icon[Ore_type.COAL.getId()], 970, 673, 30, 30,
				gamePanel.getPlayer().getCargo()[Ore_type.COAL.getId()], 1020, 700);
		drawPicture(resIcon.ore_icon[Ore_type.IRON.getId()], 970, 638, 30, 30,
				gamePanel.getPlayer().getCargo()[Ore_type.IRON.getId()], 1020, 665);
		drawPicture(resIcon.ore_icon[Ore_type.SILVER.getId()], 970, 603, 30, 30,
				gamePanel.getPlayer().getCargo()[Ore_type.SILVER.getId()], 1020, 630);
		drawPicture(resIcon.ore_icon[Ore_type.GOLD.getId()], 970, 568, 30, 30,
				gamePanel.getPlayer().getCargo()[Ore_type.GOLD.getId()], 1020, 595);
		drawPicture(resIcon.ore_icon[Ore_type.SAPHIRE.getId()], 970, 533, 30, 30,
				gamePanel.getPlayer().getCargo()[Ore_type.SAPHIRE.getId()], 1020, 560);
		drawPicture(resIcon.ore_icon[Ore_type.EMERALD.getId()], 970, 498, 30, 30,
				gamePanel.getPlayer().getCargo()[Ore_type.EMERALD.getId()], 1020, 525);
		drawPicture(resIcon.ore_icon[Ore_type.RUBY.getId()], 970, 463, 30, 30,
				gamePanel.getPlayer().getCargo()[Ore_type.RUBY.getId()], 1020, 490);
		drawPicture(resIcon.ore_icon[Ore_type.DIAMOND.getId()], 970, 428, 30, 30,
				gamePanel.getPlayer().getCargo()[Ore_type.DIAMOND.getId()], 1020, 455);
		drawPicture(resIcon.fuel_icon, 75, 665, 40, 40, gamePanel.getPlayer().getFuel(), 25, 700);
		drawPicture(resIcon.money_icon, 80, 20, 40, 40, gamePanel.getPlayer().getMoney(), 25, 50);
		drawPicture(resIcon.goal_icon, 950, 20, 40, 40, gamePanel.getPlayer().getGoal(), 985, 50);

		if (isMessageOn) {
			int positionX = gamePanel.getPlayer().getX_axis() + 80 + gamePanel.getPlayer().getY_axis()
					- gamePanel.getScreenWidth() / 3;
			if (gamePanel.getPlayer().getWorldX() == gamePanel.getWorldWidth() - 2 * (gamePanel.getTileSize())) {
				positionX -= ((gamePanel.getTileSize()) * 2 + 40);
			}
			graphics2d.setFont(graphics2d.getFont().deriveFont(20F));
			graphics2d.drawString(message, positionX, gamePanel.getScreenHeight() / 2);
			messageCounter++;
			if (messageCounter > 30) {
				messageCounter = 0;
				isMessageOn = false;
			}
		}
		if (gamePanel.getGasStation().isInside()) {
			currentDialogue = gamePanel.getGasStation().getDialog();
			drawDialogueScreen();
		}

		if (gamePanel.getStore().isInside()) {
			currentDialogue = gamePanel.getStore().getDialog();
			drawDialogueScreen();
		}
	}

	public int getSizeOfWindowX(String text) {
		return (int) graphics2d.getFontMetrics().getStringBounds(text, graphics2d).getWidth();
	}

	public int getSizeOfWindowY(String text) {
		return (int) graphics2d.getFontMetrics().getStringBounds(text, graphics2d).getHeight();
	}

	public int getXforCenterText(String text) {
		int lengthX = getSizeOfWindowX(text);
		int x = gamePanel.getScreenWidth() / 2 - lengthX / 2;
		return x;
	}

	public int getYforCenterText(String text) {
		int lengthY = getSizeOfWindowY(text);
		int y = gamePanel.getScreenHeight() / 2 + lengthY / 2;
		return y;
	}

	public void drawPauseScreen() {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 74F));
		String text = "PAUSE";
		int lengthX = getSizeOfWindowX(text);
		int lengthY = getSizeOfWindowY(text);
		int x = getXforCenterText(text);
		int y = getYforCenterText(text);
		graphics2d.setColor(new Color(20, 20, 20, 100));
		drawSubWindow(x - gamePanel.getTileSize(), y - lengthY + 20, lengthX + gamePanel.getTileSize() * 2, lengthY);

		graphics2d.setColor(Color.WHITE);
		graphics2d.drawString(text, x, y);
	}

	public void drawDialogueScreen() {
		int x = gamePanel.getTileSize() * 2;
		int y = gamePanel.getTileSize();
		int width = gamePanel.getScreenWidth() - (gamePanel.getTileSize() * 4);
		int height = gamePanel.getTileSize() * 2;
		drawSubWindow(x, y, width, height);
		graphics2d.setFont(graphics2d.getFont().deriveFont(30));
		x += gamePanel.getTileSize();
		y += gamePanel.getTileSize();
		graphics2d.drawString(currentDialogue, x, y);
	}

	public void drawTitleScreen() {
		graphics2d.drawImage(resIcon.title, 0, 0, gamePanel.getWidth(), gamePanel.getHeight(), null);
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 74F));
		int x = gamePanel.getScreenWidth() / 2;
		int y = gamePanel.getTileSize() * 9;

		graphics2d.setColor(new Color(0, 0, 0));
		graphics2d.fillRoundRect(x - 105, y - 60, 210, 70, 25, 25);
		graphics2d.setColor(new Color(240, 202, 41));
		String text = "PLAY";
		x = getXforCenterText(text);
		graphics2d.drawString(text, x - 3, y + 3);
		if (commandNum == 0) {
			graphics2d.drawString(">", x - gamePanel.getTileSize() - 3, y + 3);
			drawSubWindow(0, 510, 340, 145);
			drawStart();
		}

		y += gamePanel.getTileSize() * 3;

		graphics2d.setColor(new Color(0, 0, 0));
		graphics2d.fillRoundRect(x - 50, y - 60, 290, 70, 25, 25);
		graphics2d.setColor(new Color(240, 202, 41));
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 74F));
		text = "Tutorial";
		x = getXforCenterText(text);
		graphics2d.drawString(text, x - 3, y + 3);
		if (commandNum == 1) {
			graphics2d.drawString("> ", x - gamePanel.getTileSize() - 3, y + 3);
			drawSubWindow(0, 0, 1080, 350);
			drawTutorial();
		}
	}

	private void drawStart() {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 15F));
		graphics2d.setColor(new Color(240, 202, 41));
		graphics2d.drawString("Press W or S to select Menu", 20, 540);
		graphics2d.drawString("(Play or Tutorial)", 20, 570);
		graphics2d.drawString("To enter the game : Please select PLAY", 20, 600);
		graphics2d.drawString("and press spacebar ;)", 20, 630);
	}

	private void drawTutorial() {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 30F));
		graphics2d.setColor(new Color(240, 202, 41));
		graphics2d.drawString("Tutorial :", 30, 40);
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 20F));
		graphics2d.drawString(
				"Our mining truck can drill soil to find many minerals. To control it, \"Press W\" to move the truck upward",
				30, 65);
		graphics2d.drawString("\"Press S\" to move the truck downward", 30, 95);
		graphics2d.drawString("\"Press A\" to the truck move left", 30, 125);
		graphics2d.drawString("\"Press D\" to the truck move right", 30, 155);
		graphics2d.drawString(
				"Minerals can be sold at the store.\r\n" + " Different resources sell for different amounts. \r\n", 30,
				185);
		graphics2d.drawString("You will have to drill deeper to find more valuable minerals.", 30, 215);
		graphics2d.setColor(new Color(255, 0, 0));
		graphics2d.drawString("Beware of running out of fuel", 30, 245);
		graphics2d.setColor(new Color(240, 202, 41));
		graphics2d.drawString("To win the game : You must collect money more than or equal to the goal", 30, 275);
		graphics2d.drawString("", 30, 305);
		graphics2d.drawString(
				"-----------------------------------------------------------------If you want to quit game now : Press spacebar--------------------------",
				30, 335);
	}

	public void drawSubWindow(int x, int y, int width, int height) {
		graphics2d.setColor(new Color(0, 0, 0, 150));
		graphics2d.fillRoundRect(x, y, width, height, 35, 35);
		graphics2d.setColor(new Color(200, 200, 200));
		graphics2d.setStroke(new BasicStroke(5));
		graphics2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	public void drawRunOutFual() {
		String status = "GAME OVER";
		String option = "Press SPACE to back to the MENU";
		drawWinorLose(status, option);
	}

	public void drawWin() {
		String status = "YOU WIN";
		String option = "Press SPACE to back to the MENU";
		drawWinorLose(status, option);
	}

	public void drawWinorLose(String status, String option) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(50F));
		int lengthX = getSizeOfWindowX(status);
		int lengthY = getSizeOfWindowY(status);
		int x = getXforCenterText(status);
		int y = getYforCenterText(status);
		graphics2d.setColor(new Color(20, 20, 20, 100));
		drawSubWindow(x - gamePanel.getTileSize(), y - lengthY * 2 + 20, lengthX + gamePanel.getTileSize() * 2,
				lengthY + gamePanel.getTileSize());

		graphics2d.setColor(Color.WHITE);
		graphics2d.drawString(status, x, y - 20);

		graphics2d.setFont(graphics2d.getFont().deriveFont(20F));
		x = getXforCenterText(option);
		graphics2d.drawString(option, x, y + 180);
	}

	public Graphics2D getGraphics2d() {
		return graphics2d;
	}

	public void setGraphics2d(Graphics2D graphics2d) {
		this.graphics2d = graphics2d;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public Font getArial_30() {
		return arial_30;
	}

	public void setArial_30(Font arial_30) {
		this.arial_30 = arial_30;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean isMessageOn() {
		return isMessageOn;
	}

	public void setMessageOn(boolean messageOn) {
		this.isMessageOn = messageOn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMessageCounter() {
		return messageCounter;
	}

	public void setMessageCounter(int messageCounter) {
		this.messageCounter = messageCounter;
	}

	public String getCurrentDialogue() {
		return currentDialogue;
	}

	public void setCurrentDialogue(String currentDialogue) {
		this.currentDialogue = currentDialogue;
	}

}
