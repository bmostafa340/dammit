import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Health extends HBox {
	
	private BattleWorld world;
	private Rectangle red;
	private Rectangle grey;
	private ImageView heart;
	
	public Health(BattleWorld world) {
		super();
		world.setHealth(this);
		this.world = world;
		heart = new ImageView(new Image("heart.png"));
		BackgroundFill[] colors = new BackgroundFill[1];
		colors[0] = new BackgroundFill(Color.GREY, null, null);
		StackPane heartBackGround = new StackPane();
		heartBackGround.setPrefWidth(60);
		heartBackGround.setBackground(new Background(colors));
		heartBackGround.getChildren().add(heart);
		red = new Rectangle();
		red.setHeight(40);
		red.setFill(Color.RED);
		grey = new Rectangle();
		grey.setHeight(40);
		grey.setFill(Color.GREY);
		updateHealth();
		getChildren().addAll(heartBackGround, red, grey);
	}
	
	public void updateHealth() {
		red.setWidth(world.getPlayer().getHealth() * 5);
		grey.setWidth(500 - red.getWidth());
	}
	
}