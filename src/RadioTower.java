import javafx.scene.image.Image;

public class RadioTower extends BattleWorldActor {
	
	private BattleWorld world;
	private int health;

	public RadioTower(BattleWorld world) {
		super();
		setImage(new Image("RadioTowerTopView.png"));
		setHealth(5000);
		this.world = world;
		world.getChildren().add(this);
		double xPlayer = world.getPlayer().getBounds().getMaxX()-world.getPlayer().getBounds().getWidth()/2;
		double yPlayer = world.getPlayer().getBounds().getMaxY()-world.getPlayer().getBounds().getHeight()/2;
		double xThis = getBounds().getMaxX()-getWidth()/2;
	    double yThis = getBounds().getMaxY()-getHeight()/2;
	    setX(xPlayer - xThis);
	    setY(yPlayer - yThis);
		world.addZombieImpenetrable(this);
	}

	@Override
	public void act(long now) {
		if (health <= 0) {
			getWorld().removeZombieImpenetrable(this);
			getWorld().getChildren().remove(this);
			return;
		}
		for (Monster monster: world.getObjects(Monster.class)) {
			monster.setTarget(this);
		}
		/*for (Demon demon: world.getObjects(Demon.class)) {
			demon.setTarget(this);
		}*/
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
