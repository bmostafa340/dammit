public class CastleIcon extends InventoryItem {

	private static int price;
	
	public CastleIcon(BattleWorld world) {
		super("castleIcon.png", true, world);
		setPrice(500);
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
