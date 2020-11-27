import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TowerProgressDisplay extends HBox {
	
	BattleWorld world;
	ArrayList<InventoryItem> towerSymbols;
	InventoryItem towerSymbol;
	Rectangle progressBar;
	Rectangle sizingBar;
	
	public TowerProgressDisplay(BattleWorld world) {
		this.world = world;
		//getWorld().setDisplay(this);
		CastleIcon castle = new CastleIcon(world);
		RadioTowerIcon tower = new RadioTowerIcon(world);
		WaterTowerIcon tower2 = new WaterTowerIcon(world);
		towerSymbol = castle;
		towerSymbols = new ArrayList<InventoryItem>();
		towerSymbols.add(castle);
		towerSymbols.add(tower);
		towerSymbols.add(tower2);
		progressBar = new Rectangle();
		progressBar.setHeight(100);
		progressBar.setWidth(0);
		progressBar.setFill(Color.RED);
		sizingBar = new Rectangle();
		sizingBar.setHeight(100);
		sizingBar.setWidth(700);
		sizingBar.setFill(Color.GREY);
		getChildren().addAll(progressBar, sizingBar, towerSymbol);
	}
	
	public void incrementTowerCount(int amt) {
		if ((progressBar.getWidth() + amt) / 700 == 1) {
			getChildren().remove(towerSymbol);
			int index = 0;
			while (!(towerSymbols.get(index) == towerSymbol)) {
				index++;
			}
			getWorld().getInventory().add(getItem(index));
			if (index < towerSymbols.size() - 1) {
				towerSymbol = towerSymbols.get(index + 1);
			} else {
				towerSymbol = towerSymbols.get(0);
			}
			getChildren().add(towerSymbol);
		}
		progressBar.setWidth((progressBar.getWidth() + amt) % 700);
		sizingBar.setWidth(700 - progressBar.getWidth());
	}
	
	public BattleWorld getWorld() {
		return world;
	}
	
	private InventoryItem getItem(int index) {
		if (index == 0) {
			return new CastleIcon(world);
		} else if (index == 1) {
			return new RadioTowerIcon(world);
		} else {
			return new WaterTowerIcon(world);
		}
	}

}
