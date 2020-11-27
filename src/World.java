import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class World extends Pane {
	
	private AnimationTimer timer;
	
	public World() {
		super();
		timer = new AnimationTimer() {		
			@Override
			public void handle(long now) {
				ArrayList <Actor> listActors = (ArrayList<Actor>) getObjects(Actor.class);
				act(now);
				for(Actor a : listActors) {
					a.act(now);
				}
			}
		};
	}
	
	public abstract void act(long now);
	
	public void add(Actor actor) {
		getChildren().add(actor);
	}
	
	public void remove(Actor actor) {
		getChildren().remove(actor);

	}
	
	public <A extends Actor> List<A> getObjects(Class<A> cls) {
		ArrayList<A> classList = new ArrayList<A>();
		for(Node n : getChildren()) {
			if(cls.isInstance(n)) classList.add(cls.cast(n)); 
		}
		return classList;
	}
	
	public void start() {
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}
	
}
