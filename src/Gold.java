import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Gold extends Text{
	//Changed to BattleWorld from World
		private BattleWorld world;
		private int score;
		
		//Changed to BattleWorld from world
		public Gold(BattleWorld world) {
			super();
			this.world = world;
			world.setGold(this);
			score = 1000;
			setFont(Font.font("Baskerville",getFont().getSize() + 5));
			setFill(Color.WHITE);
			updateGold();
		}

		public void updateGold() {
			String val = ": " + score;
			setText(val);
		}
		
		public int getGold() {
			return score;
		}

		public void setGold(int score) {
			this.score = score;
			updateGold();
		}
		
}
