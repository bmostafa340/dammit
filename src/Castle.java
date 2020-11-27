import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Castle extends BattleWorldActor {
	
	private BattleWorld world;
	private BattleWorldActor target;
	private int health;
	private int count = 0;

	public Castle(BattleWorld world) {
		super();
		setImage(new Image("castleTopView.png"));
		this.world = world;
		setHealth(500);
		world.addZombieImpenetrable(this);
		world.getChildren().add(this);
		double xPlayer = world.getPlayer().getBounds().getMaxX()-world.getPlayer().getBounds().getWidth()/2;
		double yPlayer = world.getPlayer().getBounds().getMaxY()-world.getPlayer().getBounds().getHeight()/2;
		double xThis = getBounds().getMaxX()-getWidth()/2;
	    double yThis = getBounds().getMaxY()-getHeight()/2;
	    setX(xPlayer - xThis);
	    setY(yPlayer - yThis);
	}
	
	@Override
	public void act(long now) {
		if (health <= 0) {
			getWorld().removeZombieImpenetrable(this);
			getWorld().getChildren().remove(this);
			return;
		}
		updateTarget();
		count++;
		if (count > 50 && target != null) {
			double xTarget = target.getBounds().getMaxX() - target.getWidth();
			double yTarget = target.getBounds().getMaxY() - target.getHeight();
			double xThis = getBounds().getMaxX() - getWidth();
			double yThis = getBounds().getMaxY() - getHeight();
			double hypot = Math.sqrt(Math.pow(xTarget-xThis, 2) + Math.pow(yTarget-yThis, 2));
			double dx = 40*((xTarget-xThis)/hypot);
		    double dy = -40*((yTarget-yThis)/hypot);
		    double angle = Math.atan(dx/dy)*180/(Math.PI);
		    Bullet b = new Bullet();
			getWorld().add(b);
			b.setX(xThis - getWorld().getBoundsInParent().getMinX() + getWidth()/2);
			b.setY(yThis - getWorld().getBoundsInParent().getMinY() + getWidth()/2);
			RotateTransition move = new RotateTransition(Duration.seconds(0.0001),b);
			if(dx<0 && dy>0) {
				angle+=190;
			}if(dx<0 && dy<0) {
				angle+=21;
			}if(dx>0 && dy>0) {
				angle +=190;
			}if(dx>0&&dy<0) {
				angle+=15;
			}
			angle += 77;
			move.setToAngle(angle);
			move.play();
			count = 0;
		}
		for (Demon demon: world.getObjects(Demon.class)) {
			demon.setTarget(this);
		}
	}
	
	public void updateTarget() {
		if (!getWorld().getObjects(Monster.class).contains(target)) {
			double minDist = Integer.MAX_VALUE;
			BattleWorldActor tempTarget = null;
			for (Actor node: getWorld().getObjects(Monster.class)) {
				if (BoundsUtil.centerDistance(getBounds(), node.getBounds()) < minDist) {
					minDist =  BoundsUtil.centerDistance(getBounds(), node.getBounds());
					tempTarget = (BattleWorldActor) node;
				}
			}
			target = tempTarget;
		}
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
