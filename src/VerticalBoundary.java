import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;

public class VerticalBoundary extends BattleWorldActor {
	
	public VerticalBoundary() {
		super();
		setImage(new Image("VerticalBoundary.png"));
	}

	@Override
	public void act(long now) {

	}

	@Override 
	public Bounds getBounds() {
		Bounds temp = super.getBounds();
		return new BoundingBox(temp.getMinX() - 20, temp.getMinY(), temp.getWidth() + 40, temp.getHeight());
	}

}
