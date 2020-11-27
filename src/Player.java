import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Player extends BattleWorldActor {

	private BattleWorld world;
	private int health;
	private int speed;
	private boolean invincible;

	public Player(BattleWorld world) {
		super();
		speed = 6;
		setInvincibility(false);
		this.world = world;
		Image img = new Image("Player Image.png");
		setImage(img);
		world.setPlayer(this);
		health = 100;
	}

	@Override
	public void move(double dx, double dy) {
		dy = -dy;
		ArrayList<Actor> impenetrablesList = getWorld().getPlayerImpenetrables();
		boolean allowed = true;
		for (int i = 0; i < impenetrablesList.size(); i++) {
			if (BoundsUtil.intersects(getBounds(), impenetrablesList.get(i).getTransferredBounds(-dx, -dy))) {
				allowed = false;
				break;
			}
		}
		if (allowed) {
			world.move(dx, dy);
		} 
		allowed = true;
		for (int i = 0; i < impenetrablesList.size(); i++) {
			if (BoundsUtil.intersects(getBounds(), impenetrablesList.get(i).getTransferredBounds(-dx, 0))) {
				allowed = false;
				break;
			}
		}
		if (allowed) {
			world.move(dx, 0);
		} 
		allowed = true;
		for (int i = 0; i < impenetrablesList.size(); i++) {
			if (BoundsUtil.intersects(getBounds(), impenetrablesList.get(i).getTransferredBounds(0, -dy))) {
				allowed = false;
				break;
			}
		}
		if (allowed) {
			world.move(0, dy);
		} 
	}
	
	@Override
	public BattleWorld getWorld() {
		return world;
	}
	
	@Override
	public Bounds getBounds() {
		return new BoundingBox(getBoundsInLocal().getMinX() + 30, getBoundsInLocal().getMinY() + 30, getBoundsInLocal().getWidth() - 60, getBoundsInLocal().getHeight() - 60);
	}

	@Override
	public void act(long now) {
		if (getWorld().isKeyDown(KeyCode.D) && getWorld().isKeyDown(KeyCode.W)) {
			move(Math.sqrt(Math.pow(speed, 2)/2),Math.sqrt(Math.pow(speed, 2)/2));
		} else if (getWorld().isKeyDown(KeyCode.A) && getWorld().isKeyDown(KeyCode.W)) {
			move(-Math.sqrt(Math.pow(speed, 2)/2),Math.sqrt(Math.pow(speed, 2)/2));
		} else if (getWorld().isKeyDown(KeyCode.D) && getWorld().isKeyDown(KeyCode.S)) {
			move(Math.sqrt(Math.pow(speed, 2)/2),-Math.sqrt(Math.pow(speed, 2)/2));
		} else if (getWorld().isKeyDown(KeyCode.A) && getWorld().isKeyDown(KeyCode.S)) {
			move(-Math.sqrt(Math.pow(speed, 2)/2),-Math.sqrt(Math.pow(speed, 2)/2));
		} else if (getWorld().isKeyDown(KeyCode.A)) {
			move(-speed,0);
		} else if (getWorld().isKeyDown(KeyCode.D)) {
			move(speed,0);
		} else if (getWorld().isKeyDown(KeyCode.W)) {
			move(0,speed);
		} else if (getWorld().isKeyDown(KeyCode.S)) {
			move(0,-speed);
		}
		if (getWorld().isKeyDown(KeyCode.E)) {
			for (int i = 0; i < getIntersectingInventoryItems().size(); i++) {
				InventoryItem item = getIntersectingInventoryItems().get(0);
				if (getWorld().getInventory().add(item)) {
					getWorld().getChildren().remove(item);
				}
			}
		}
		if (getWorld().isKeyDown(KeyCode.Q)) {
			if (getWorld().getInventory().getSelectedIndex() == 2) {
				InventoryItem temp = getWorld().getInventory().getSelected();
				getWorld().getInventory().decrementSelected();
				temp.drop();
			}
		}
		if (invincible) {
			Image img = new Image("PlayerInvincible.png");
			setImage(img);
			for (int i = 0; i < getIntersectingObjects(Monster.class).size(); i++) {
				getIntersectingObjects(Monster.class).get(0).setHealth(0);
			}
		}else {
			Image img = new Image("Player Image.png");
			setImage(img);
		}
		/*if(!getIntersectingObjects(Actor.class).isEmpty()) {
			//Removed Unnecessary casting
			getWorld().getScore().setScore(getWorld().getScore().getScore() + 1);
		}*/
	}

	private List<InventoryItem> getIntersectingInventoryItems() {
		List<Node> temp = getWorld().getChildren();
		List<InventoryItem> result = new ArrayList<InventoryItem>();
		for(int i = 0; i < temp.size(); i++) {
			if(temp.get(i) != this && InventoryItem.class.isInstance(temp.get(i)) && ((InventoryItem) temp.get(i)).getBounds().intersects(getBounds())) {
				result.add(InventoryItem.class.cast(temp.get(i)));
			}
		}
		return result;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (invincible) {
			
		} else if (health >= 0) {
			this.health = health;
			world.getHealth().updateHealth();
		} else {
			health = 0;
		}
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}

	public boolean isInvincibility() {
		return invincible;
	}

	public void setInvincibility(boolean invincibility) {
		this.invincible = invincibility;
	}
	
}
