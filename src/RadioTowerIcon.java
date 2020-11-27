public class RadioTowerIcon extends InventoryItem {
	
	private static int price;

	public RadioTowerIcon(BattleWorld world) {
		super("Radio-Tower-icon.png", true, world);
		setPrice(600);
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
