import javafx.animation.RotateTransition;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Zombie extends Monster {
	

	public Zombie() {
		super();
		Image img = new Image("Zombie.png");
		setImage(img);
		setHealth(150);
	}
	
	/*@Override
	public Bounds getBounds() {
		Bounds temp = super.getBounds();
		return new BoundingBox(temp.getMinX() + 80, temp.getMinY() + 80, getBoundsInLocal().getWidth() - 160, getBoundsInLocal().getHeight() - 160);
	}*/

	/*@Override
	public void act(long now) {
		if (getHealth() <= 0) {
			getWorld().removeZombieImpenetrable(this);
			getWorld().getScore().setScore(getWorld().getScore().getScore()+1);
			getWorld().getGold().setGold(getWorld().getGold().getGold()+20);
			getWorld().getChildren().remove(this);
			return;
		}
		updateTarget();
	    double xTarget = getTarget().localToScreen(getTarget().getBoundsInParent()).getMaxX()-getTarget().getBoundsInParent().getWidth()/2;
		double yTarget = getTarget().localToScreen(getTarget().getBoundsInParent()).getMaxY()-getTarget().getBoundsInParent().getHeight()/2;
	    double xThis = getWorld().localToScreen(getBoundsInParent()).getMaxX()-getBoundsInParent().getWidth()/2;
	    double yThis = getWorld().localToScreen(getBoundsInParent()).getMaxY()-getBoundsInParent().getHeight()/2;
	    double hypot = Math.sqrt(Math.pow(xTarget-xThis, 2) + Math.pow(yTarget-yThis, 2));
	    double dx = 4*((xTarget-xThis)/hypot);
	    double dy = 4*((yTarget-yThis)/hypot);
	    double angle = Math.atan((xTarget-xThis)/(yTarget-yThis))*180/(Math.PI);
		RotateTransition move = new RotateTransition(Duration.seconds(0.0001),this);
		if (xTarget-xThis<0 ) {
			angle+=180;
		} if(xTarget-xThis<0 && yTarget-yThis<0) {
			angle+=180;
		} if(xTarget-xThis>0 && yTarget-yThis>0) {
			angle +=180;
		}
		angle+=90;
		
		move.setToAngle(-angle);
		move.play();
	    move(dx, dy);
	    if (BoundsUtil.intersects(getBounds(), getTarget().getExternalBounds())) {
	    	if (Player.class.isInstance(getTarget())) {
	    		((Player)getTarget()).setHealth(((Player)getTarget()).getHealth() - 1);
	    	} else if (WaterTower.class.isInstance(getTarget())) {
	    		((WaterTower)getTarget()).setHealth(((WaterTower)getTarget()).getHealth() - 1);
	    	} else if (Castle.class.isInstance(getTarget())) {
	    		((Castle)getTarget()).setHealth(((Castle)getTarget()).getHealth() - 1);
	    	} else if (RadioTower.class.isInstance(getTarget())) {
	    		((RadioTower)getTarget()).setHealth(((RadioTower)getTarget()).getHealth() - 1);
	    	}
	    }
	    if (BoundsUtil.intersects(getBounds(), getWorld().getPlayer().getExternalBounds())) {
	    	getWorld().getPlayer().setHealth(getWorld().getPlayer().getHealth() - 1);
	    } if (WaterTower.class.isInstance(getTarget()) && BoundsUtil.intersects(getBounds(), getTarget().getExternalBounds())) {
	    	((WaterTower)getTarget()).setHealth(((WaterTower)getTarget()).getHealth() - 1);
	    } if (Castle.class.isInstance(getTarget()) && BoundsUtil.intersects(getBounds(), getTarget().getExternalBounds())) {
	    	((Castle)getTarget()).setHealth(((Castle)getTarget()).getHealth() - 1);
	    } if (RadioTower.class.isInstance(getTarget()) && BoundsUtil.intersects(getBounds(), getTarget().getExternalBounds())) {
	    	((RadioTower)getTarget()).setHealth(((RadioTower)getTarget()).getHealth() - 1);
	    }
	}*/

	

}
