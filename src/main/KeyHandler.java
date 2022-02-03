package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import block.BlockManager;
import entity.Player;

public class KeyHandler implements KeyListener {
	private GamePanel gamePanel;
	private boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isSpacePressed;

	public KeyHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_P) {
			if (gamePanel.getGameState() == gamePanel.getPlayState()) {
				gamePanel.playSE(1);
				gamePanel.setGameState(gamePanel.getPauseState());
			} else if (gamePanel.getGameState() == gamePanel.getPauseState()) {
				gamePanel.playSE(1);
				gamePanel.setGameState(gamePanel.getPlayState());
			}
		}

		if (gamePanel.getGameState() == gamePanel.getTitleState()) {
			if (code == KeyEvent.VK_W) {
				gamePanel.playSE(1);
				gamePanel.getUi().commandNum--;
				if (gamePanel.getUi().commandNum < 0) {
					gamePanel.getUi().commandNum = 1;
				}
			} else if (code == KeyEvent.VK_S) {
				gamePanel.playSE(1);
				gamePanel.getUi().commandNum++;
				if (gamePanel.getUi().commandNum > 1) {
					gamePanel.getUi().commandNum = 0;
				}
			} else if (code == KeyEvent.VK_SPACE) {
				if (gamePanel.getUi().commandNum == 0) {
					gamePanel.playSE(4);
					Player player = new Player(gamePanel, this);
					gamePanel.setPlayer(player);
					BlockManager blockManager = new BlockManager(gamePanel);
					gamePanel.setBlockManager(blockManager);
					gamePanel.setGameState(gamePanel.getPlayState());
				}
				if (gamePanel.getUi().commandNum == 1) {
					System.exit(1);
				}
			}
		}

		if (gamePanel.getGameState() == gamePanel.getPlayState()) {
			if (code == KeyEvent.VK_W) {
				isUpPressed = true;
			} else if (code == KeyEvent.VK_S) {
				isDownPressed = true;
			} else if (code == KeyEvent.VK_A) {
				isLeftPressed = true;
			} else if (code == KeyEvent.VK_D) {
				isRightPressed = true;
			} else if (code == KeyEvent.VK_SPACE) {
				isSpacePressed = true;
			} else if (code == KeyEvent.VK_ESCAPE) {
				System.exit(1);
			}
		}
		if (gamePanel.getGameState() == gamePanel.getOutOfFuelState()
				|| gamePanel.getGameState() == gamePanel.getWinState()) {
			if (code == KeyEvent.VK_SPACE) {
				gamePanel.setGameState(gamePanel.getTitleState());
			}
		}
	}

	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			isUpPressed = false;
		} else if (code == KeyEvent.VK_S) {
			isDownPressed = false;
		} else if (code == KeyEvent.VK_A) {
			isLeftPressed = false;
		} else if (code == KeyEvent.VK_D) {
			isRightPressed = false;
		} else if (code == KeyEvent.VK_SPACE) {
			isSpacePressed = false;
		}

	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public boolean isUpPressed() {
		return isUpPressed;
	}

	public void setUpPressed(boolean upPressed) {
		this.isUpPressed = upPressed;
	}

	public boolean isDownPressed() {
		return isDownPressed;
	}

	public void setDownPressed(boolean downPressed) {
		this.isDownPressed = downPressed;
	}

	public boolean isLeftPressed() {
		return isLeftPressed;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.isLeftPressed = leftPressed;
	}

	public boolean isRightPressed() {
		return isRightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.isRightPressed = rightPressed;
	}

	public boolean isSpacePressed() {
		return isSpacePressed;
	}

	public void setSpacePressed(boolean isSpacePressed) {
		this.isSpacePressed = isSpacePressed;
	}
}
