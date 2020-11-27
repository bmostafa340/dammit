import java.util.ArrayList;

import javafx.scene.image.Image;

public class Bullet extends Actor {
	double dx;
	double dy;
	private int damage;
	public Bullet() {
		super();
		Image img = new Image("bullet.png");
		setImage(img);
		damage = 50;
	}
	public Bullet(double x, double y) {
		super();
		Image img = new Image("bullet.png");
		setImage(img);
		dx = x;
		dy = y;
	}
	
	@Override
	public void act(long now) {
		if (getIntersectingObjects(Monster.class).size() > 0) {
			getIntersectingObjects(Monster.class).get(0).setHealth(getIntersectingObjects(Monster.class).get(0).getHealth() - damage);
			getWorld().getChildren().remove(this);
			return;
		}
		double angle = this.getRotate()*Math.PI/180+Math.PI;
		move(-30*Math.cos(angle),30*Math.sin(angle));
//		move(30*dx,30*dy);
	}
	
	@Override
	public void move(double dx, double dy) {
		dy = -dy;
		BattleWorld world = ((BattleWorld) getWorld());
		ArrayList<Actor> impenetrablesList = world.getPlayerImpenetrables();
		boolean allowed = true;
		for (int i = 0; i < impenetrablesList.size(); i++) {
			if (BoundsUtil.intersects(getBounds(), impenetrablesList.get(i).getTransferredBounds(-dx, -dy))) {
				allowed = false;
				getWorld().remove(this);
				break;
			}
		}
		if (allowed) {
			super.move(dx, dy);
		} 
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

}
