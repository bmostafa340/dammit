import java.awt.Dimension;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

public class Game extends Application{
	
	private Dimension screenSize;
	private double screenWidth;
	private double screenHeight;
	private long previousTime = 0;
	private long previousTime1 = 0;
	private long previousTime2 = 0;
	private long previousTime3 = 0;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("DAMMIT");
		stage.setResizable(false);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.getWidth();
		screenHeight = screenSize.getHeight();
		
		Group root = new Group();
		BattleWorld world = new BattleWorld();
		world.setPrefWidth(screenWidth);
		world.setPrefHeight(screenHeight);
		world.setLayoutX(-500);
		world.setLayoutY(-500);
		Scene scene = new Scene(root, screenWidth, screenHeight-70);
//		world.setLayoutX(20);
//		world.setLayoutY(20);
		//Scene scene = new Scene(root, screenWidth, screenHeight);
		stage.setScene(scene);
//		stage.setFullScreen(true);
		
		Player player = new Player(world);
		player.setX(screenWidth/2 - player.getWidth()/2);
		player.setY(screenHeight/2 - player.getHeight()/2);
		
		Inventory inventory = new Inventory(world);
		inventory.setLayoutY(scene.getHeight() - 100);
		
		world.addZombieImpenetrable(player);
		world.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//double playerCompensate = player.localToScreen(player.getBoundsInLocal()).getMinX() + player.localToScreen(player.getBoundsInLocal()).getWidth()/2;
				double x = event.getScreenX()-screenWidth/2;
				double y = -event.getScreenY()+screenHeight/2;
				double angle = Math.atan(x/y)*180/(Math.PI);
				RotateTransition move = new RotateTransition(Duration.seconds(0.0001),player);

				if(x<0 && y>0) {
					angle+=192;
				}if(x<0 && y<0) {
					angle+=21;
				}if(x>0 && y>0) {
					angle +=180;
				}if(x>0&&y<0) {
					angle+=7;
				}
				
				angle += 77;
				move.setToAngle(angle);
				move.play();
				
			}
			
		});
		
		HBox displayBox = new HBox();
		Score score = new Score(world);
		Health health = new Health(world);
		//TowerProgressDisplay display = new TowerProgressDisplay(world);
		Gold gold = new Gold(world);
		ImageView view = new ImageView();
		Image img = new Image("Coin.png");
		view.setImage(img);
		view.setFitHeight(20);
		view.setFitWidth(20);
		displayBox.getChildren().addAll(score, view, gold, health);//display);
		
		root.getChildren().addAll(world, player, displayBox, inventory);
		
		world.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				world.keyDown(event.getCode());
			}
			
		});
		world.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {

				if (PistolIcon.class.isInstance(inventory.getSelected())) {
					double x = event.getScreenX()-screenWidth/2;
					double y = -event.getScreenY()+screenHeight/2;
//					double hypot = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
					Bullet b = new Bullet();
					world.add(b);
					//world.getDisplay().incrementTowerCount(100);
					double xPlayer = player.getBounds().getMaxX()-player.getBounds().getWidth()/2;
					double yPlayer = player.getBounds().getMaxY()-player.getBounds().getHeight()/2;
					double xThis = b.getBounds().getMaxX()-b.getWidth()/2;
				    double yThis = b.getBounds().getMaxY()-b.getHeight()/2;
				    double angle = Math.atan(x/y)*180/(Math.PI);
				    
				    if (x < 0 && y < 0) {
				    	b.setX(xPlayer-xThis-Math.abs(20*Math.cos(angle*Math.PI/180)));
						b.setY(yPlayer-yThis-Math.abs(25*Math.sin(angle*Math.PI/180)));
					} else if (x<0 && y>0){
						b.setX(xPlayer-xThis+Math.abs(35*Math.cos(angle*Math.PI/180)));
						b.setY(yPlayer-yThis-Math.abs(20*Math.sin(angle*Math.PI/180)));
					} else if (x>0 &&y>0) {
						b.setX(xPlayer-xThis+Math.abs(30*Math.cos(angle*Math.PI/180)));
						b.setY(yPlayer-yThis+Math.abs(20*Math.sin(angle*Math.PI/180)));
					} else {
						b.setX(xPlayer-xThis-Math.abs(25*Math.cos(angle*Math.PI/180)));
						b.setY(yPlayer-yThis+Math.abs(20*Math.sin(angle*Math.PI/180)));
					}

				    RotateTransition move = new RotateTransition(Duration.seconds(0.0001),b);
//				    		if (x<0) {
//				    	angle+=180;
//				    } if(x<0 && y<0) {
//				    	angle+=180;
//				    } if(x>0 && y>0) {
//				    	angle +=180;
//				    }
				    move.setToAngle(player.getRotate());
				    move.play();
				    
				} else if (RadioTowerIcon.class.isInstance(inventory.getSelected())) {
					if (gold.getGold() >= RadioTowerIcon.getPrice()) {
						RadioTower tower = new RadioTower(world);
//						inventory.getSelected().decrementCount(1);
						gold.setGold(gold.getGold()-RadioTowerIcon.getPrice());
					}
				} else if (CastleIcon.class.isInstance(inventory.getSelected())) {
					if (gold.getGold() >= CastleIcon.getPrice()) {
						Castle tower = new Castle(world);
//						inventory.getSelected().decrementCount(1);
						gold.setGold(gold.getGold()-CastleIcon.getPrice());
					}
				} else if (WaterTowerIcon.class.isInstance(inventory.getSelected())) {
					if (gold.getGold() >= WaterTowerIcon.getPrice()) {
						WaterTower tower = new WaterTower(world);
//						inventory.getSelected().decrementCount(1);
						gold.setGold(gold.getGold()-WaterTowerIcon.getPrice());
					}
				} else if (InvincibilityIcon.class.isInstance(inventory.getSelected())) {
					inventory.getSelected().decrementCount(1);
					world.getPlayer().setSpeed(10);
					world.getPlayer().setInvincibility(true);
					 String fileName= "";
						try {
							fileName = getClass().getResource("Super Mario World - Invincible Theme.mp3").toURI().toString();
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						AudioClip clip = new AudioClip(fileName);
						clip.play();
					AnimationTimer timer3 = new AnimationTimer() {
						
						long interval = (long) (19000*1e6);
						boolean resetPrevTime = false;

						@Override
						public void handle(long now) {
							if (now - previousTime3 > interval) {
								if (resetPrevTime) {
									world.getPlayer().setSpeed(6);
									world.getPlayer().setInvincibility(false);
									stop();
								}
								resetPrevTime = true;
								previousTime3 = now;
							}
						}
						
					};
					timer3.start();
				} else if (UpgradedBulletIcon.class.isInstance(inventory.getSelected())) {
					inventory.getSelected().decrementCount(1);
					double x = event.getScreenX()-screenWidth/2;
					double y = -event.getScreenY()+screenHeight/2;
//					double hypot = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
					UpgradedBullet b = new UpgradedBullet();
					world.add(b);
					//world.getDisplay().incrementTowerCount(100);
					double xPlayer = player.getBounds().getMaxX()-player.getBounds().getWidth()/2;
					double yPlayer = player.getBounds().getMaxY()-player.getBounds().getHeight()/2;
					double xThis = b.getBounds().getMaxX()-b.getWidth()/2;
				    double yThis = b.getBounds().getMaxY()-b.getHeight()/2;
				    double angle = Math.atan(x/y)*180/(Math.PI);
				    
				    if (x < 0 && y < 0) {
				    	b.setX(xPlayer-xThis-Math.abs(20*Math.cos(angle*Math.PI/180)));
						b.setY(yPlayer-yThis-Math.abs(25*Math.sin(angle*Math.PI/180)));
					} else if (x<0 && y>0){
						b.setX(xPlayer-xThis+Math.abs(35*Math.cos(angle*Math.PI/180)));
						b.setY(yPlayer-yThis-Math.abs(20*Math.sin(angle*Math.PI/180)));
					} else if (x>0 &&y>0) {
						b.setX(xPlayer-xThis+Math.abs(30*Math.cos(angle*Math.PI/180)));
						b.setY(yPlayer-yThis+Math.abs(20*Math.sin(angle*Math.PI/180)));
					} else {
						b.setX(xPlayer-xThis-Math.abs(25*Math.cos(angle*Math.PI/180)));
						b.setY(yPlayer-yThis+Math.abs(20*Math.sin(angle*Math.PI/180)));
					}

				    RotateTransition move = new RotateTransition(Duration.seconds(0.0001),b);
//				    		if (x<0) {
//				    	angle+=180;
//				    } if(x<0 && y<0) {
//				    	angle+=180;
//				    } if(x>0 && y>0) {
//				    	angle +=180;
//				    }
				    move.setToAngle(player.getRotate());
				    move.play();
				    String fileName= "";
					try {
						fileName = getClass().getResource("Gun_Shot-Marvin-1140816320.mp3").toURI().toString();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					AudioClip clip = new AudioClip(fileName);
					clip.play();
				}
				
				
			}
			
			
		});
		
		world.setOnKeyReleased(new EventHandler<KeyEvent>() {
					
			@Override
			public void handle(KeyEvent event) {
				world.keyUp(event.getCode());
			}
					
		});
		
		world.setOnScroll(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent event) {
				if (event.getDeltaY() < 0) {
					inventory.decrementSelected();
				} else {
					inventory.incrementSelected();
				}
			}
			
		});
		
		long songInterval = (long) (120*1e9);

		AnimationTimer timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				if(now - previousTime > songInterval) {
					  String fileName= "";
						try {
							fileName = getClass().getResource("di-evantile_destroyer.mp3").toURI().toString();
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}
						AudioClip clip = new AudioClip(fileName);
						clip.play();
						previousTime = now;
				}
			}
			
		};
		long interval = (long) (2000*1e6);
		
		AnimationTimer timer1 = new AnimationTimer() {

			int[] spawnX = {400,  400,  400, 1800, 1800, 3720, 3720, 3720};
			int[] spawnY = {400, 1800, 3720,  400, 3720,  400, 1800, 3720};
			int spawnIndex = 0;
			
			//To change frequency of different Monsters, change the ratio of Monsters in the list below
			Monster[] monsters = {new Zombie(), new Zombie(), new Zombie(), new Zombie(), new Zombie(), new Zombie(), new Demon(), new Demon(), new Demon(), new Scorpion()};
			
			@Override
			public void handle(long now) {
				int indexMonster = (int) (Math.random()*monsters.length);
				if (world.getPlayer().getHealth() == 0) {
					world.stop();
					StackPane pane = new StackPane();
					BackgroundFill[] colors = new BackgroundFill[1];
					colors[0] = new BackgroundFill(Color.RED, null, null);
					pane.setBackground(new Background(colors));
					Text gameOver = new Text("Game Over!\nYou scored: " + world.getScore().getScore());
					gameOver.setFill(Color.WHITE);
					gameOver.setFont(Font.font("Baskerville",gameOver.getFont().getSize() + 10));
					pane.getChildren().add(gameOver);
					pane.setLayoutX(screenWidth/2 - 350/2);
					pane.setLayoutY(screenHeight/2 - 350/2);
					pane.setPrefHeight(350);
					pane.setPrefWidth(350);
					root.getChildren().add(pane);
					world.stop();
					stop();
				}
				if (now - previousTime1 > interval) {
					for (int i = 0; i < 1 + (world.getScore().getScore() / 30); i++) {
						if (i == 8) {
							break;
						} else {
							if (Zombie.class.isInstance(monsters[indexMonster])) {
								Monster m = new Zombie();
								m.setX(spawnX[spawnIndex]);
								m.setY(spawnY[spawnIndex]);
								world.add(m);
							} else if (Demon.class.isInstance(monsters[indexMonster])) {
								Monster m = new Demon();
								m.setX(spawnX[spawnIndex]);
								m.setY(spawnY[spawnIndex]);
								world.add(m);
							} else {
								Monster m = new Scorpion();
								m.setX(spawnX[spawnIndex]);
								m.setY(spawnY[spawnIndex]);
								world.add(m);
							}
						}
						if (++spawnIndex > 7) {
							spawnIndex = 0;
						}
					}
					previousTime1 = now;
				}
				/*if (now - previousTime1 > interval && Demon.class.isInstance(monsters[indexMonster])) {
					ArrayList<Integer> indexArr = new ArrayList<Integer>();
					indexArr.add(0);
					indexArr.add(1);
					indexArr.add(2);
					indexArr.add(3);
					indexArr.add(4);
					indexArr.add(5);
					indexArr.add(6);
					indexArr.add(7);
					for (int i = 0; i < 1 + (world.getScore().getScore() / 30); i++) {
						int indexTemp = (int)(Math.random()*indexArr.size());
						int index = indexArr.get(indexTemp);
						Demon d = new Demon();
						d.setX(spawnX[index]);
						d.setY(spawnY[index]);
						world.add(d);
						while (d.getIntersectingObjects(Monster.class).size() > 0 && indexArr.size() > 1) {
							world.getChildren().remove(d);
							indexArr.remove(indexTemp);
							indexTemp = (int)(Math.random()*indexArr.size());
							index = indexArr.get(indexTemp);
							d = new Demon();
							d.setX(spawnX[index]);
							d.setY(spawnY[index]);
							world.add(d);
						}
						world.addZombieImpenetrable(d);
						
					}
					previousTime1 = now;
					//interval /= world.getScore().getScore()/50;
				}
				if (now - previousTime1 > interval && Zombie.class.isInstance(monsters[indexMonster])) {
					ArrayList<Integer> indexArr = new ArrayList<Integer>();
					indexArr.add(0);
					indexArr.add(1);
					indexArr.add(2);
					indexArr.add(3);
					indexArr.add(4);
					indexArr.add(5);
					indexArr.add(6);
					indexArr.add(7);*/
					//for (int i = 0; i < /*1 + (world.getScore().getScore() / 30)*/8; i++) {
						/*int indexTemp = (int)(Math.random()*indexArr.size());
						int index = indexArr.get(indexTemp);
						Zombie z = new Zombie();
						z.setX(spawnX[index]);
						z.setY(spawnY[index]);
						world.add(z);
						while (z.getIntersectingObjects(Monster.class).size() > 0 && indexArr.size() > 1) {
							world.getChildren().remove(z);
							indexArr.remove(indexTemp);
							indexTemp = (int)(Math.random()*indexArr.size());
							index = indexArr.get(indexTemp);
							z = new Zombie();
							z.setX(spawnX[index]);
							z.setY(spawnY[index]);
							world.add(z);
						}
						world.addZombieImpenetrable(z);
					}
					previousTime1 = now;
					//interval /= world.getScore().getScore()/50;
				}
				if (now - previousTime1 > interval && Scorpion.class.isInstance(monsters[indexMonster])) {
					ArrayList<Integer> indexArr = new ArrayList<Integer>();
					indexArr.add(0);
					indexArr.add(1);
					indexArr.add(2);
					indexArr.add(3);
					indexArr.add(4);
					indexArr.add(5);
					indexArr.add(6);
					indexArr.add(7);*/
					//for (int i = 0; i < /*1 + (world.getScore().getScore() / 30)*/8; i++) {
						/*int indexTemp = (int)(Math.random()*indexArr.size());
						int index = indexArr.get(indexTemp);
						Scorpion z = new Scorpion();
						z.setX(spawnX[index]);
						z.setY(spawnY[index]);
						world.add(z);
						while (z.getIntersectingObjects(Monster.class).size() > 0 && indexArr.size() > 1) {
							world.getChildren().remove(z);
							indexArr.remove(indexTemp);
							indexTemp = (int)(Math.random()*indexArr.size());
							index = indexArr.get(indexTemp);
							z = new Scorpion();
							z.setX(spawnX[index]);
							z.setY(spawnY[index]);
							world.add(z);
						}
						world.addZombieImpenetrable(z);
					}*/
					//previousTime1 = now;
					//interval /= world.getScore().getScore()/50;
				//}
			}
			
		};
		
		long interval2 = (long) (25000*1e6);
		
		AnimationTimer timer2 = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (now - previousTime2 > interval2) {
					InventoryItem item = null;
					InventoryItem[] spawnableInventoryItems = {new InvincibilityIcon(world), new UpgradedBulletIcon(world)};
					int index = (int) (Math.random()*spawnableInventoryItems.length);
					if (InvincibilityIcon.class.isInstance(spawnableInventoryItems[index])) {
						item = new InvincibilityIcon(world);
					} else if (UpgradedBulletIcon.class.isInstance(spawnableInventoryItems[index])) {
						item = new UpgradedBulletIcon(world);
					}
					boolean intersection = true;
					while (intersection) {
						double ranX = Math.random()*3600;
						double ranY = Math.random()*3600;
						item.setLayoutX(ranX);
						item.setLayoutY(ranY);
						world.getChildren().add(item);
						intersection = false;
						for (int i = 0; i < world.getPlayerImpenetrables().size(); i++) {
							if (BoundsUtil.intersects(world.getPlayerImpenetrables().get(i).getBounds(), item.getBounds())) {
								intersection = true;
								world.getChildren().remove(item);
								break;
							}
						}
					}
					previousTime2 = now;
				}
			}
			
		};
		
		timer.start();
		timer1.start();
		timer2.start();
		stage.show();
		world.requestFocus();
		world.start();
	
	}

}