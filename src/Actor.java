import java.util.ArrayList;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class Actor extends ImageView {

	
	public Actor() {
		super();
	}
	
	public abstract void act(long now);
	
	public void move(double dx, double dy) {
		TranslateTransition move = new TranslateTransition(Duration.seconds(0.0001),this);
		move.setByX(dx);
		move.setByY(dy);
		move.play();		
	}
	
	public Bounds getTransferredBounds(double dx, double dy) {
		Bounds ref = getBounds();
		return new BoundingBox(ref.getMaxX() + dx - getWidth(), ref.getMaxY() + dy - getHeight(), getWidth(), getHeight());
	}
		
	public World getWorld() {
		return (World)(getParent());
	}
	
	public Bounds getBounds() {
		World world = getWorld();
		Bounds local = world.localToScene(getBoundsInLocal());
		Bounds parent = getWorld().localToScene(getBoundsInParent());
		return new BoundingBox(BoundsUtil.getCenter(parent)[0] - local.getWidth()/2, BoundsUtil.getCenter(parent)[1] - local.getHeight()/2, local.getWidth(), local.getHeight());
	}
	
	public Bounds getExternalBounds() {
		Bounds temp = getBounds();
		return new BoundingBox(temp.getMinX() - 10, temp.getMinY() - 10, temp.getWidth() + 20, temp.getHeight() + 20);
	}
	
	public double getWidth() {
		return getBounds().getWidth();
	}
	
	public double getHeight() {
		return getBounds().getHeight();
	}

	public <A extends Actor> List<A> getIntersectingObjects(Class<A> cls) {
		ArrayList<Actor> temp = (ArrayList<Actor>)getWorld().getObjects(Actor.class);
		List<A> result = new ArrayList<A>();
		for(int i = 0; i < temp.size(); i++) {
			if(temp.get(i) != this && cls.isInstance(temp.get(i)) && temp.get(i).getBounds().intersects(getBounds())) {
				result.add(cls.cast(temp.get(i)));
			}
		}
		return result;
	}
	
	public <A extends Actor> A getOneIntersectingObject(Class<A> cls) {
		ArrayList<Actor> temp = (ArrayList<Actor>)getWorld().getObjects(Actor.class);
		for(int i = 0; i < temp.size(); i++) {
			if(temp.get(i) != this && cls.isInstance(temp.get(i)) && temp.get(i).getBounds().intersects(getBounds())) {
				return cls.cast(temp.get(i));
			}
		}
		return null;
	}
	
}
