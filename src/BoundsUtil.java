import javafx.geometry.Bounds;

public class BoundsUtil {
	public static boolean intersects (Bounds b1, Bounds b2) {
		if (b1.getMaxX() >= b2.getMinX() && b1.getMinX() <= b2.getMaxX() && b1.getMaxY() >= b2.getMinY() && b1.getMinY() <= b2.getMaxY()) {
			return true;
		}
		return false;
	}
	public static double[] getCenter (Bounds b1) {
		double[] result = new double[2];
		result[0] = ((b1.getMinX() + b1.getMaxX())/2);
		result[1] = ((b1.getMinY() + b1.getMaxY())/2);
		return result;
	}
	public static double centerDistance (Bounds b1, Bounds b2) {
		double dx = getCenter(b1)[0] - getCenter(b2)[0];
		double dy = getCenter(b1)[1] - getCenter(b2)[1];
		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); 
	}
}