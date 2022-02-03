package block;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import entity.Player;
import load.resource.Map;
import load.resource.Texture;
import main.Drawable;
import main.GamePanel;
import object.Ore_type;

public class BlockManager implements Drawable {

	private GamePanel gamePanel;
	private Texture texture;
	public Map map;

	public BlockManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		texture = new Texture();
		map = new Map(gamePanel);
	}

	public void draw(Graphics2D graphics2d) {
		int width = gamePanel.getScreenWidth();
		int height = gamePanel.getScreenHeight();
		GradientPaint gradient = new GradientPaint(0, 0, gamePanel.getColor1(), 0, height, gamePanel.getColor2());
		graphics2d.setPaint(gradient);
		graphics2d.fillRect(0, 0, width, height);

		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gamePanel.getMaxWorldCol() && worldRow < gamePanel.getMaxWorldRow()) {
			Block_type block_type = map.getMapTileNum()[worldCol][worldRow];

			int worldX = worldCol * gamePanel.getTileSize();
			int worldY = worldRow * gamePanel.getTileSize();
			int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
			int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

			if (gamePanel.getPlayer().getScreenX() > gamePanel.getPlayer().getWorldX()) {
				screenX = worldX;
			}
			if (gamePanel.getPlayer().getScreenY() > gamePanel.getPlayer().getWorldY()) {
				screenY = worldY;
			}
			int rightOffset = gamePanel.getScreenWidth() - gamePanel.getPlayer().getScreenX();
			if (rightOffset > gamePanel.getWorldWidth() - gamePanel.getPlayer().getWorldX()) {
				screenX = gamePanel.getScreenWidth() - (gamePanel.getWorldWidth() - worldX);

			}
			int buttomOffset = gamePanel.getScreenHeight() - gamePanel.getPlayer().getScreenY();
			if (buttomOffset > gamePanel.getWorldHeight() - gamePanel.getPlayer().getWorldY()) {
				screenY = gamePanel.getScreenHeight() - (gamePanel.getWorldHeight() - worldY);
			}

			if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX()
					- gamePanel.getPlayer().getScreenX()
					&& worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX()
							+ gamePanel.getPlayer().getScreenX()
					&& worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY()
							- gamePanel.getPlayer().getScreenY()
					&& worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY()
							+ gamePanel.getPlayer().getScreenY()) {
				graphics2d.drawImage(texture.array_block[block_type.getId()], screenX, screenY, null);
			} else if (gamePanel.getPlayer().getScreenX() > gamePanel.getPlayer().getWorldX()
					|| gamePanel.getPlayer().getScreenY() > gamePanel.getPlayer().getWorldY()
					|| rightOffset > gamePanel.getWorldWidth() - gamePanel.getPlayer().getWorldX()
					|| buttomOffset > gamePanel.getWorldHeight() - gamePanel.getPlayer().getWorldY()) {
				graphics2d.drawImage(texture.array_block[block_type.getId()], screenX, screenY, null);
			}

			worldCol++;

			if (worldCol == gamePanel.getMaxWorldCol()) {
				worldCol = 0;
				worldRow++;
			}
		}
	}

	public void deleteBlock(Player player) {
		int entityLeftWorldX = player.getWorldX();
		int entityRightWorldX = player.getWorldX() + gamePanel.getTileSize();
		int entityTopWorldY = player.getWorldY();
		int entityButtomWorldY = player.getWorldY() + gamePanel.getTileSize();

		int entityLeftCol = entityLeftWorldX / gamePanel.getTileSize();
		int entityRightCol = entityRightWorldX / gamePanel.getTileSize();
		int entityTopRow = entityTopWorldY / gamePanel.getTileSize();
		int entityButtomRow = entityButtomWorldY / gamePanel.getTileSize();

		switch (player.getDirection()) {
		case "UP": {
			entityTopRow = (entityTopWorldY + 30) / gamePanel.getTileSize();
			Block_type block = gamePanel.getBlockManager().map.getMapTileNum()[entityLeftCol][entityTopRow];
			boolean isSky = block.equals(Block_type.SKY_BLOCK);
			if (block != Block_type.INVISIBLE_BLOCK && block.getHardness() < player.getLvlDrill() && !isSky) {
				gamePanel.getBlockManager().map
						.getMapTileNum()[entityLeftCol][entityTopRow] = Block_type.INVISIBLE_BLOCK;
				player.addTocargo(blockToOre(block));
			}
			break;
		}
		case "DOWN": {
			entityButtomRow = (entityButtomWorldY - 30) / gamePanel.getTileSize();
			Block_type block = gamePanel.getBlockManager().map.getMapTileNum()[entityLeftCol][entityButtomRow];
			boolean isSky = block.equals(Block_type.SKY_BLOCK);
			if (block != Block_type.INVISIBLE_BLOCK && block.getHardness() < player.getLvlDrill() && !isSky) {
				gamePanel.getBlockManager().map
						.getMapTileNum()[entityLeftCol][entityButtomRow] = Block_type.INVISIBLE_BLOCK;
				player.addTocargo(blockToOre(block));
			}
			break;
		}
		case "LEFT": {
			entityLeftCol = (entityLeftWorldX + 30) / gamePanel.getTileSize();
			Block_type block = gamePanel.getBlockManager().map.getMapTileNum()[entityLeftCol][entityTopRow];
			boolean isSky = block.equals(Block_type.SKY_BLOCK);
			if (block != Block_type.INVISIBLE_BLOCK && block.getHardness() < player.getLvlDrill() && !isSky) {
				gamePanel.getBlockManager().map
						.getMapTileNum()[entityLeftCol][entityTopRow] = Block_type.INVISIBLE_BLOCK;
				player.addTocargo(blockToOre(block));
			}
			break;
		}
		case "RIGHT": {
			entityRightCol = (entityRightWorldX - 30) / gamePanel.getTileSize();
			Block_type block = gamePanel.getBlockManager().map.getMapTileNum()[entityRightCol][entityTopRow];
			boolean isSky = block.equals(Block_type.SKY_BLOCK);
			if (block != Block_type.INVISIBLE_BLOCK && block.getHardness() < player.getLvlDrill() && !isSky) {
				gamePanel.getBlockManager().map
						.getMapTileNum()[entityRightCol][entityTopRow] = Block_type.INVISIBLE_BLOCK;
				player.addTocargo(blockToOre(block));
			}
			break;
		}
		case "STAND": {
			Block_type block = gamePanel.getBlockManager().map.getMapTileNum()[entityLeftCol][entityTopRow];
			boolean isSky = block.equals(Block_type.SKY_BLOCK);
			if (block != Block_type.INVISIBLE_BLOCK && block.getHardness() < player.getLvlDrill() && !isSky) {
				gamePanel.getBlockManager().map
						.getMapTileNum()[entityLeftCol][entityTopRow] = Block_type.INVISIBLE_BLOCK;
				player.addTocargo(blockToOre(block));
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + player.getDirection());
		}
	}

	private Ore_type blockToOre(Block_type block) {
		switch (block) {
		case COAL_BLOCK: {
			return Ore_type.COAL;
		}
		case IRON_BLOCK: {
			return Ore_type.IRON;
		}
		case SILVER_BLOCK: {
			return Ore_type.SILVER;
		}
		case GOLD_BLOCK: {
			return Ore_type.GOLD;
		}
		case SAPHIRE_BLOCK: {
			return Ore_type.SAPHIRE;
		}
		case EMERALD_BLOCK: {
			return Ore_type.EMERALD;
		}
		case RUBY_BLOCK: {
			return Ore_type.RUBY;
		}
		case DIAMOND: {
			return Ore_type.DIAMOND;
		}
		default:
			return Ore_type.NULL;
		}
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

}
