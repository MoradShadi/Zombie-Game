package game;

import java.io.IOException;
import java.util.List;

import edu.monash.fit2099.engine.ActorLocations;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.GroundFactory;

public class MyGameMap extends GameMap {

	public MyGameMap(GroundFactory groundFactory, char groundChar, int width, int height) {
		super(groundFactory, groundChar, width, height);
		// TODO Auto-generated constructor stub
	}

	public MyGameMap(GroundFactory groundFactory, List<String> lines) {
		super(groundFactory, lines);
		// TODO Auto-generated constructor stub
	}

	public MyGameMap(GroundFactory groundFactory, String mapFile) throws IOException {
		super(groundFactory, mapFile);
		// TODO Auto-generated constructor stub
	}

	public ActorLocations getAllActors() {
		return this.actorLocations;
	}
}
