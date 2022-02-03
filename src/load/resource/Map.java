package load.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import block.Block_type;
import main.GamePanel;

public class Map {
	private GamePanel gamePanel;
	private Block_type[][] mapTileNum;

	public Map(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		load_tilemap(gamePanel);
	}

	public void load_tilemap(GamePanel gamePanel) {
		Block_type mapTileNum[][] = new Block_type[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];

		try {
			InputStream is = Map.class.getResourceAsStream("/map/yod1_new2.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {

				String line = br.readLine();
				while (col < gamePanel.getMaxWorldCol()) {
					String number[] = line.split(" ");

					int num = Integer.parseInt(number[col]);
					Block_type blockType;
					switch (num) {
					case 0: {
						blockType = Block_type.SKY_BLOCK;
						break;
					}
					case 1: {
						blockType = Block_type.GRASS_BLOCK;
						break;
					}
					case 2: {
						blockType = Block_type.DIRT_BLOCK;
						break;
					}
					case 3: {
						blockType = Block_type.GRAVEL_BLOCK;
						break;
					}
					case 4: {
						blockType = Block_type.STONE_BLOCK;
						break;
					}
					case 5: {
						blockType = Block_type.COAL_BLOCK;
						break;
					}
					case 6: {
						blockType = Block_type.IRON_BLOCK;
						break;
					}
					case 7: {
						blockType = Block_type.SILVER_BLOCK;
						break;
					}
					case 8: {
						blockType = Block_type.GOLD_BLOCK;
						break;
					}
					case 9: {
						blockType = Block_type.SAPHIRE_BLOCK;
						break;
					}
					case 10: {
						blockType = Block_type.EMERALD_BLOCK;
						break;
					}
					case 11: {
						blockType = Block_type.RUBY_BLOCK;
						break;
					}
					case 12: {
						blockType = Block_type.DIAMOND;
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + num);
					}
					mapTileNum[col][row] = blockType;
					col++;
				}
				if (col == gamePanel.getMaxWorldCol()) {
					col = 0;
					row++;
				}
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mapTileNum = mapTileNum;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public Block_type[][] getMapTileNum() {
		return mapTileNum;
	}

	public void setMapTileNum(Block_type[][] mapTileNum) {
		this.mapTileNum = mapTileNum;
	}

}
