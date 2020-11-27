import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score extends Text {
	
	//Changed to BattleWorld from World
	private BattleWorld world;
	private int score;
	
	//Changed to BattleWorld from world
	public Score(BattleWorld world) {
		super();
		this.world = world;
		world.setScore(this);
		score = 0;
		setFont(Font.font("Baskerville",getFont().getSize() + 5));
		setFill(Color.WHITE);
		updateScore();
	}

	public void updateScore() {
		String val = "Score: " + score + "  ";
		setText(val);
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		updateScore();
	}
	
}