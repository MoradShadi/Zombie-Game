package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

public class MamboMarie extends Human {
	
	private int turnCount;
	private Behaviour[] behaviours = {
			new WanderBehaviour(),
			new ChantingBehaviour()
	};
	
	public MamboMarie(String name) {
		super(name,'M',100);
		turnCount = 0;
		// TODO Auto-generated constructor stub
	}


	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (turnCount%10 == 0 && turnCount != 30) {
			
		}
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null) {
				return action;
			}
		}
		return new DoNothingAction();
	}
}
