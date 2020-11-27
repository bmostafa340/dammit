
import javafx.animation.RotateTransition;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Monster extends BattleWorldActor {
	
	private int health;
	private BattleWorldActor target;

	public Monster() {
		super();
	}
	
	/*@Override
	public Bounds getBounds() {
		Bounds temp = super.getBounds();
		return new BoundingBox(temp.getMinX() + 80, temp.getMinY() + 80, getBoundsInLocal().getWidth() - 160, getBoundsInLocal().getHeight() - 160);
	}*/

	@Override
	public void act(long now) {
		if (health <= 0) {
			getWorld().removeZombieImpenetrable(this);
			getWorld().getScore().setScore(getWorld().getScore().getScore()+1);
			getWorld().getGold().setGold(getWorld().getGold().getGold()+20);
			getWorld().getChildren().remove(this);
			return;
		}
		updateTarget();
	    double xTarget = target.localToScreen(target.getBoundsInParent()).getMaxX()-target.getBoundsInParent().getWidth()/2;
		double yTarget = target.localToScreen(target.getBoundsInParent()).getMaxY()-target.getBoundsInParent().getHeight()/2;
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
	    if (BoundsUtil.intersects(getBounds(), target.getExternalBounds())) {
	    	if (Player.class.isInstance(target)) {
	    		((Player)target).setHealth(((Player)target).getHealth() - 1);
	    	} else if (WaterTower.class.isInstance(target)) {
	    		((WaterTower)target).setHealth(((WaterTower)target).getHealth() - 1);
	    	} else if (Castle.class.isInstance(target)) {
	    		((Castle)target).setHealth(((Castle)target).getHealth() - 1);
	    	} else if (RadioTower.class.isInstance(target)) {
	    		((RadioTower)target).setHealth(((RadioTower)target).getHealth() - 1);
	    	}
	    }
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void updateTarget() {
		if (!getWorld().getObjects(Actor.class).contains(target)) {
			double minDist = Integer.MAX_VALUE;
			BattleWorldActor tempTarget = null;
			for (Actor node: getWorld().getObjects(Actor.class)) {
				if ((Player.class.isInstance(node) || RadioTower.class.isInstance(node) || WaterTower.class.isInstance(node) || Castle.class.isInstance(node)) && BoundsUtil.centerDistance(getBounds(), node.getBounds()) < minDist) {
					minDist =  BoundsUtil.centerDistance(getBounds(), node.getBounds());
					tempTarget = (BattleWorldActor) node;
				}
			}
			target = tempTarget;
		}
	}
	
	public void setTarget(BattleWorldActor actor) {
		target = actor;
	}
	public BattleWorldActor getTarget() {
		return target;
	}

}

