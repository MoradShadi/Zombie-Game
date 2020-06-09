package game;

/**
 * A shotgun that can shoot in the eight directions and fires a 90 degree cone of pellets that can hit any Actor within 3 squares.
 * It has a 75% hit chance and can also damage friendly humans so it must be used carefully.
 * 
 * @author User
 *
 */
public class Shotgun extends Gun {

	public Shotgun() {
		super("shotgun", 'S', 25, 35, false, 0.75, 3);
		// TODO Auto-generated constructor stub
	}
}
