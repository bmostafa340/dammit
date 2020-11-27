import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class BattleWorld extends World {
	
	private HashSet<KeyCode> keys;
	private Player player;
	private Score score;
	private Health health;
	private Gold gold;
	private Inventory inventory;
	//private TowerProgressDisplay display;
	private ArrayList<Actor> playerImpenetrables;
	private ArrayList<Actor> zombieImpenetrables;
	
	public BattleWorld() {
		super();
		keys = new HashSet<>();
		playerImpenetrables = new ArrayList<Actor>();
		zombieImpenetrables = new ArrayList<Actor>();
		Image image = new Image("Background.png");
		ImageView view = new ImageView(image);
		getChildren().add(view);
		Boulder boulder1 = new Boulder();
		boulder1.setX(1801);
		boulder1.setY(1801);
		VerticalBoundary vB1 = new VerticalBoundary();
		VerticalBoundary vB2 = new VerticalBoundary();
		vB2.setX(3300);
		HorizontalBoundary hB1 = new HorizontalBoundary();
		HorizontalBoundary hB2 = new HorizontalBoundary();
		hB2.setY(3300);
		playerImpenetrables.add(boulder1);
		playerImpenetrables.add(vB1);
		playerImpenetrables.add(vB2);
		playerImpenetrables.add(hB1);
		playerImpenetrables.add(hB2);
		zombieImpenetrables.add(boulder1);
		getChildren().addAll(boulder1, vB1, vB2, hB1, hB2);
	}

	
	@Override
	public void act(long now) {
	}
	
	@Override 
	public <A extends Actor> List<A> getObjects(Class<A> cls) {
		ArrayList<A> classList = new ArrayList<A>();
		for (Node n : getChildren()) {
			if (cls.isInstance(n)) classList.add(cls.cast(n)); 
		}
		if (cls.isInstance(player)) classList.add(cls.cast(player));
		return classList;
	}
	
	public void move(double dx, double dy) {
		TranslateTransition move = new TranslateTransition(Duration.seconds(0.0001),this);
		move.setByX(-dx);
		move.setByY(-dy);
		move.play();
	}
	
	public void keyDown(KeyCode k) {
		keys.add(k);
	}
	
	public void keyUp(KeyCode k) {
		keys.remove(k);
	}
	
	public boolean isKeyDown(KeyCode k) {
		return keys.contains(k);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player p) {
		player = p;
	}
	
	public Score getScore() {
		return score;
	}
	
	public void setScore(Score score) {
		this.score = score;
	}
	
	public Gold getGold() {
		return gold;
	}
	
	public void setGold(Gold gold) {
		this.gold = gold;
	}


	public ArrayList<Actor> getPlayerImpenetrables() {
		ArrayList<Actor> result = new ArrayList<Actor>();
		for (Actor a: playerImpenetrables) {
			result.add(a);
		}
		return result;
	}


	public void addPlayerImpenetrable(Actor impenetrable) {
		playerImpenetrables.add(impenetrable);
	}
	
	public ArrayList<Actor> getZombieImpenetrables() {
		ArrayList<Actor> result = new ArrayList<Actor>();
		for (Actor a: zombieImpenetrables) {
			result.add(a);
		}
		return result;		
	}
	
	public void addZombieImpenetrable(Actor impenetrable) {
		zombieImpenetrables.add(impenetrable);
	}

	public void removeZombieImpenetrable(Actor impenetrable) {
		zombieImpenetrables.remove(impenetrable);
	}

	public Inventory getInventory() {
		return inventory;
	}


	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}


	public Health getHealth() {
		return health;
	}


	public void setHealth(Health health) {
		this.health = health;
	}


	/*public TowerProgressDisplay getDisplay() {
		return display;
	}


	public void setDisplay(TowerProgressDisplay display) {
		this.display = display;
	}*/
	
}