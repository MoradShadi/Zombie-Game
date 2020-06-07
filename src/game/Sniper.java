package game;


public class Sniper extends Gun {
	private int aimCount;
	private Zombie aimTarget;
	
	public Sniper() {
		super("sniper", '1', 25, 30, true, 0.75);
		aimCount = 0;
	}
	
	public void aimSniper(Zombie target) {
		if ((aimCount == 0) || (aimTarget != target)) {
			this.aimCount = 1;
			this.aimTarget = target;
		}
		else if (aimCount == 1 && aimTarget == target) {
			this.aimCount += 1;
		}
	} 
	
	public int getAimCount() {
		return aimCount;
	}
	
	public Zombie getTarget() {
		return aimTarget;
	}
}
