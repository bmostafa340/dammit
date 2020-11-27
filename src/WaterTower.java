import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WaterTower extends BattleWorldActor {
	
	private BattleWorld world;
	private int health;
	private long previousTime = 1;
	private Rectangle rect;

	public WaterTower(BattleWorld world) {
		super();
		this.world = world;
		setImage(new Image("waterTowerTopDown.png"));
		setHealth(5000);
		world.addZombieImpenetrable(this);
		world.getChildren().add(this);
		double xPlayer = world.getPlayer().getBounds().getMaxX()-world.getPlayer().getBounds().getWidth()/2;
		double yPlayer = world.getPlayer().getBounds().getMaxY()-world.getPlayer().getBounds().getHeight()/2;
		double xThis = getBounds().getMaxX()-getWidth()/2;
	    double yThis = getBounds().getMaxY()-getHeight()/2;
	    setX(xPlayer - xThis);
	    setY(yPlayer - yThis);
		rect = new Rectangle();
		rect.setX(getX() - 100);
		rect.setY(getY() - 100);
		rect.setWidth(getWidth() + 200);
		rect.setHeight(getHeight() + 200);
		rect.setFill(Color.rgb(255, 0, 0, 0.2));
		world.getChildren().add(rect);
		long interval = (long) (500*1e6);
//		AnimationTimer timer = new AnimationTimer() {
//			
//			@Override
//			public void handle(long now) {
//				if (now - previousTime > interval) {
//					for (int i = 0; i < getWorld().getObjects(Zombie.class).size(); i++) {
//						if (BoundsUtil.intersects(getWorld().getObjects(Zombie.class).get(i).getBounds(), getWorld().localToScene(rect.getBoundsInLocal()))) {
//							getWorld().getObjects(Zombie.class).get(i).setHealth(getWorld().getObjects(Zombie.class).get(i).getHealth() - 10);
//						}
//					}
//					previousTime = now;
//				}
//			}
//			
//		};
//		timer.start();
	}
	
	@Override
	public void act(long now) { 
		if (health <= 0) {
			getWorld().removeZombieImpenetrable(this);
			getWorld().getChildren().remove(rect);
			getWorld().getChildren().remove(this);
			return;
		}
		for (int i = 0; i < getWorld().getObjects(Monster.class).size(); i++) {
			if (BoundsUtil.intersects(getWorld().getObjects(Monster.class).get(i).getBounds(), getWorld().localToScene(rect.getBoundsInLocal()))) {
				getWorld().getObjects(Monster.class).get(i).setHealth(getWorld().getObjects(Monster.class).get(i).getHealth() - 1);
			}
		}
		for (Demon demon: world.getObjects(Demon.class)) {
			demon.setTarget(this);
		}
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
