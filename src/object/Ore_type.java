package object;

public enum Ore_type {

	NULL(-1, 0), COAL(0, 20), IRON(1, 50), SILVER(2, 80), GOLD(3, 150), SAPHIRE(4, 250), EMERALD(5, 400), RUBY(6, 700),
	DIAMOND(7, 1000);

	private int id;
	private int value;

	private Ore_type(int id, int value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public int getValue() {
		return value;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
