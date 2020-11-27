import java.util.ArrayList;

public abstract class BattleWorldActor extends Actor {

	@Override
	public BattleWorld getWorld() {
		return (BattleWorld)(getParent());
	}
	
	@Override
	public void move(double dx, double dy) {
		ArrayList<Actor> impenetrablesList = getWorld().getZombieImpenetrables();
		if (impenetrablesList.contains(this)) {
			impenetrablesList.remove(this);
		}
		boolean allowed = true;
		for (int i = 0; i < impenetrablesList.size(); i++) {
			if (BoundsUtil.intersects(getBounds(), impenetrablesList.get(i).getTransferredBounds(-dx, -dy))) {
				allowed = false;
				break;
			}
		}
		if (allowed) {
			super.move(dx, dy);
		} 
		allowed = true;
		for (int i = 0; i < impenetrablesList.size(); i++) {
			if (BoundsUtil.intersects(getBounds(), impenetrablesList.get(i).getTransferredBounds(-dx, 0))) {
				allowed = false;
				break;
			}
		}
		if (allowed) {
			super.move(dx, 0);
		} 
		allowed = true;
		for (int i = 0; i < impenetrablesList.size(); i++) {
			if (BoundsUtil.intersects(getBounds(), impenetrablesList.get(i).getTransferredBounds(0, -dy))) {
				allowed = false;
				break;
			}
		}
		if (allowed) {
			super.move(0, dy);
		} 
		/*boolean intersecting = false;
		Actor intersectingActor = null;
		for (int i = 0; i < impenetrablesList.size(); i++) {
			if (BoundsUtil.intersects(getBounds(), impenetrablesList.get(i).getBounds())) {
				intersecting = true;
				intersectingActor = impenetrablesList.get(i);
				break;
			}
		}
		if (intersecting) {
			String direction = "dxdy";
			double minDist = BoundsUtil.centerDistance(getBounds(), intersectingActor.getTransferredBounds(-dx, -dy));
			if (BoundsUtil.centerDistance(getBounds(), intersectingActor.getTransferredBounds(-dx, 0)) < minDist) {
				minDist = BoundsUtil.centerDistance(getBounds(), intersectingActor.getTransferredBounds(-dx, 0));
				direction = "dx";
			} 
			if (BoundsUtil.centerDistance(getBounds(), intersectingActor.getTransferredBounds(0, -dy)) < minDist) {
				minDist = BoundsUtil.centerDistance(getBounds(), intersectingActor.getTransferredBounds(0, -dy));
				direction = "dy";
			} 
			if (BoundsUtil.centerDistance(getBounds(), intersectingActor.getBounds()) < minDist) {
				direction = "";
			} 
			if (direction.equals("")) {
				return;
			} else if (direction.equals("dx")) {
				super.move(dx, 0);
			} else if (direction.equals("dy")) {
				super.move(0, dy);
			} else {
				super.move(dx, dy);
			}
		}*/
	}

	public abstract void act(long now);
}
