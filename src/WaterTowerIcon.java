
public class WaterTowerIcon extends InventoryItem {
	
	private static int price;

	public WaterTowerIcon(BattleWorld world) {
		super("waterTower.png", true, world);
		setPrice(700);
	}
	
	@Override 
	public void setPrice(int price) {
		super.setPrice(price);
		this.price = price;
	}
	
	public static int getPrice() {
		return price;
	}

}
