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
	
	public int getShotDamage(Zombie target) {
		if (aimCount == 1 && target == this.aimTarget) {
			return super.getShotDamage() * 2;
		}
		else if (aimCount == 2 && target == this.aimTarget) {
			return 2000;
		}
		else {
			return super.getShotDamage();
		}
	}
	
	public double getHitChance(Zombie target) {
		if (aimCount == 1 && target == this.aimTarget) {
			return 0.9;
		}
		else if (aimCount == 2 && target == this.aimTarget) {
			return 1;
		}
		else {
			return super.getHitChance();
		}
	}
	
	public int getAimCount() {
		return aimCount;
	}
	
	public Zombie getTarget() {
		return aimTarget;
	}
}
