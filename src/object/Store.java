package object;

import main.GamePanel;

public class Store extends GameObject {

	public Store(GamePanel gamepanal, int worldX, int worldY, int width, int height) {
		super(gamepanal, worldX, worldY, width, height);
		resorceImage = gamePanel.getImageStore();
	}

	public void sellCargo() {
		gamePanel.playSE(5);
		int sum = 0;
		sum += gamePanel.getPlayer().getCargo()[Ore_type.COAL.getId()] * Ore_type.COAL.getValue();
		sum += gamePanel.getPlayer().getCargo()[Ore_type.IRON.getId()] * Ore_type.IRON.getValue();
		sum += gamePanel.getPlayer().getCargo()[Ore_type.SILVER.getId()] * Ore_type.SILVER.getValue();
		sum += gamePanel.getPlayer().getCargo()[Ore_type.GOLD.getId()] * Ore_type.GOLD.getValue();
		sum += gamePanel.getPlayer().getCargo()[Ore_type.SAPHIRE.getId()] * Ore_type.SAPHIRE.getValue();
		sum += gamePanel.getPlayer().getCargo()[Ore_type.EMERALD.getId()] * Ore_type.EMERALD.getValue();
		sum += gamePanel.getPlayer().getCargo()[Ore_type.RUBY.getId()] * Ore_type.RUBY.getValue();
		sum += gamePanel.getPlayer().getCargo()[Ore_type.DIAMOND.getId()] * Ore_type.DIAMOND.getValue();
		gamePanel.getPlayer().setMoney(gamePanel.getPlayer().getMoney() + sum);
		gamePanel.getPlayer().clearCargo();
	}

}
