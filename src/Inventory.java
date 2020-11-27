import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Inventory extends HBox {
	
	private BattleWorld world;
	private StackPane[] inventorySlots = new StackPane[6];
	private InventoryItem selected;
	private int selectedIndex;

	public Inventory(BattleWorld world) {
		setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), new Insets(0))));
		for (int i = 0 ; i < 6; i++) {
			StackPane temp = new StackPane();
			temp.setPrefHeight(100);
			temp.setMaxHeight(100);
			temp.setPrefWidth(100);
			getChildren().add(temp);
			inventorySlots[i] = temp;
		}
		Text txt = new Text("Inventory");
		txt.setFill(Color.WHITE);
		txt.setFont(new Font(16));
		inventorySlots[0].getChildren().add(txt);
		inventorySlots[1].getChildren().add(new PistolIcon(world));
		CastleIcon castle = new CastleIcon(world);
//		castle.decrementCount(1);
		RadioTowerIcon radioTower = new RadioTowerIcon(world);
//		radioTower.decrementCount(1);
		WaterTowerIcon waterTower = new WaterTowerIcon(world);
//		waterTower.decrementCount(1);
		inventorySlots[inventorySlots.length - 1].getChildren().add(waterTower);
		inventorySlots[inventorySlots.length - 2].getChildren().add(radioTower);
		inventorySlots[inventorySlots.length - 3].getChildren().add(castle);
		this.world = world;
		world.setInventory(this);
		selected = (InventoryItem) inventorySlots[1].getChildren().get(0);
		selectedIndex = 1;
		selected.getImage().setEffect(new Glow(1));
	}
	
	public boolean add(InventoryItem item) {
		for (StackPane pane: inventorySlots) {
			for (Node node: pane.getChildren()) {
				if (item.getClass().isInstance(node)) {
					((InventoryItem)(node)).incrementCount(item.getCount());
					return true;
				}
			}
		}
		for (StackPane pane: inventorySlots) {
			if (pane.getChildren().isEmpty()) {
				pane.getChildren().add(item);
				return true;
			}
		}
		System.out.print("\007");
		return false;
	}
	
	public InventoryItem getSelected() {
		return selected;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	public void incrementSelected() {
		selected.getImage().setEffect(new Glow(0));
		if (selectedIndex == inventorySlots.length - 1) {
			selectedIndex = inventorySlots.length-1;
		} else {
			selectedIndex++;
		}
		if (inventorySlots[selectedIndex].getChildren().size() > 0) {
			selected = (InventoryItem) inventorySlots[selectedIndex].getChildren().get(0);
		} else {
			incrementSelected();
		}
		selected.getImage().setEffect(new Glow(1));
	}
	
	public void decrementSelected() {
		selected.getImage().setEffect(new Glow(0));
		if (selectedIndex == 1) {
			selectedIndex = 1;
		} else {
			selectedIndex--;
		}
		if (inventorySlots[selectedIndex].getChildren().size() > 0) {
			selected = (InventoryItem) inventorySlots[selectedIndex].getChildren().get(0);
		} else {
			decrementSelected();
		}
		selected.getImage().setEffect(new Glow(1));
	}

}
