package block;

public enum Block_type {
	INVISIBLE_BLOCK(0, 0), GRASS_BLOCK(1, 3), DIRT_BLOCK(2, 3), GRAVEL_BLOCK(3, 4), STONE_BLOCK(4, 10),
	COAL_BLOCK(5, 4), IRON_BLOCK(6, 6), SILVER_BLOCK(7, 7), GOLD_BLOCK(8, 8), SAPHIRE_BLOCK(9, 8), EMERALD_BLOCK(10, 9),
	RUBY_BLOCK(11, 9), DIAMOND(12, 9), SKY_BLOCK(13, 0);

	private int id;
	private int hardness;

	private Block_type(int id, int hardness) {
		this.id = id;
		this.hardness = hardness;
	}

	public int getId() {
		return id;
	}

	public int getHardness() {
		return hardness;
	}

	public void setHardness(int hardness) {
		this.hardness = hardness;
	}

	public void setId(int id) {
		this.id = id;
	}
}
