import javafx.scene.image.Image;

public class UpgradedBullet extends Bullet {

	public UpgradedBullet() {
		super();
		Image img = new Image("upgradedBullet.png");
		setImage(img);
		setDamage(300);
	}

}
