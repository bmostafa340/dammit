import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class InventoryItem extends VBox {
	
	private BattleWorld world;
	private int count;
	private Text top;
	private ImageView imgView;

	public InventoryItem(String imgFile, BattleWorld world) {
		super();
		this.world = world;
		top = new Text("");
		top.setFill(Color.WHITE);
		Image img = new Image(imgFile);
		imgView = new ImageView(img);
		StackPane topBackground = new StackPane();
		topBackground.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), new Insets(0))));
		topBackground.getChildren().add(top);
		StackPane.setMargin(top, new Insets(0,30,0,0));
		getChildren().add(topBackground);
		getChildren().add(imgView);
	}

	public InventoryItem(String imgFile, boolean hasCount, BattleWorld world) {
		super();
		this.world = world;
		top = new Text("");
		top.setFill(Color.WHITE);
		Image img = new Image(imgFile);
		imgView = new ImageView(img);
		StackPane topBackground = new StackPane();
		topBackground.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), new Insets(0,0,0,0))));
		topBackground.getChildren().add(top);
		StackPane.setMargin(top, new Insets(0,30,0,0));
		getChildren().add(topBackground);
		getChildren().add(imgView);
		if (hasCount) {
			count = 0;
			top.setText("" + count);
		}
	}
	
	public void setPrice(int price) {
		top.setText(""+price);
	}
	
	public void incrementCount(int plus) {
		count += plus;
		top.setText("" + count);
	}
	
	public void decrementCount(int minus) {
		count -= minus;
		top.setText("" + count);
		if (count <= 0 && StackPane.class.isInstance(getParent())) {
			world.getInventory().decrementSelected();
			((StackPane)getParent()).getChildren().remove(this);
		}
	}
	
	public BattleWorld getWorld() {
		return world;
	}
	
	public Bounds getBounds() {
		Bounds local = getWorld().localToScene(getBoundsInLocal());
		Bounds parent = getWorld().localToScene(getBoundsInParent());
		return new BoundingBox(BoundsUtil.getCenter(parent)[0] - local.getWidth()/2, BoundsUtil.getCenter(parent)[1] - local.getHeight()/2, local.getWidth(), local.getHeight());
	}
	
	public void drop() {
		double xWorld = world.getBoundsInParent().getMinX();
		double yWorld = world.getBoundsInParent().getMinY();
		double xPlayer = world.getPlayer().getBounds().getMaxX()-world.getPlayer().getBounds().getWidth()/2;
	    double yPlayer = world.getPlayer().getBounds().getMaxY()-world.getPlayer().getBounds().getHeight()/2;
	    getWorld().getChildren().add(this);
	    setLayoutX(xPlayer - xWorld);
		setLayoutY(yPlayer - yWorld);
	}
	
	public int getCount() {
		return count;
	}
	
	public ImageView getImage() {
		return imgView;
	}

}
