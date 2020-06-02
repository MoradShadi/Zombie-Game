package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.World;

/**
 * The main class for the zombie apocalypse game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Fence(), new Tree());
		
		List<String> compoundMap = Arrays.asList(
		"................................................................................",
		"................................................................................",
		"....................................##########..................................",
		"..........................###########........#####..............................",
		"............++...........##......................########.......................",
		"..............++++.......#..............................##......................",
		".............+++...+++...#...............................#......................",
		".........................##..............................##.....................",
		"..........................#...............................#.....................",
		".........................##...............................##....................",
		".........................#...............................##.....................",
		".........................###..............................##....................",
		"...........................####......................######.....................",
		"..............................#########.........####............................",
		"............+++.......................#.........#...............................",
		".............+++++....................#.........#...............................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap compoundGameMap = new GameMap(groundFactory, compoundMap );
		world.addGameMap(compoundGameMap);
		
		List<String> townMap = Arrays.asList(
		"................................................................................",
		"................................................................................",
		"................................................................#########.......",
		".....############..............############.....................#.......#.......",
		".....#..........#.+............#..........#.....................#.......#.......",
		".....#..........#..............#..........#.....................#.......#.......",
		".....#....................................#.......###################..##.......",
		".....#..........#.........................#.......#.....................#.......",
		".....#..........#..............#..........#.......#.....................#.......",
		".....###############...........#..........#.......#.....................#.......",
		".....#.............#...........############.......#.....................#.......",
		".....#.............#...........#..........#.......#.....................#.......",
		".....#.......................+.#..........#.......#.....................#.......",
		".....#....................................#.......#.....................#.......",
		".....#.............#......................#.......#.....................#.......",
		".....#.............#.........+.#..........#.......########.......########.......",
		".....####....#######...........#..........#........+.+.+.+.......+.+.+.+........",
		".....#.............#...........############.....................................",
		".....#.............#..+.........................................................",
		".....#.............#..+.........................................................",
		".....#.............#..+.........................................................",
		".....###############............................................................",
		".......+.+.+.+.+.+..............................................................",
		"................................................................................",
		"................................................................................");
		GameMap townGameMap = new GameMap(groundFactory, townMap );
		world.addGameMap(townGameMap);
		
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, compoundGameMap.at(42, 15));
		
	    // Place some random humans
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};
		int x, y;
		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (compoundGameMap.at(x, y).containsAnActor());
			compoundGameMap.at(x,  y).addActor(new Human(name));	
		}
		
		// place a simple weapon
		compoundGameMap.at(45, 20).addItem(new Plank());
		
		// FIXME: Add more zombies!
		compoundGameMap.at(30, 20).addActor(new Zombie("Groan"));
		compoundGameMap.at(30,  18).addActor(new Zombie("Boo"));
		compoundGameMap.at(10,  4).addActor(new Zombie("Uuuurgh"));
		compoundGameMap.at(50, 18).addActor(new Zombie("Mortalis"));
		compoundGameMap.at(1, 10).addActor(new Zombie("Gaaaah"));
		compoundGameMap.at(62, 12).addActor(new Zombie("Aaargh"));
		
		// adding farmers
		Actor farmer1 = new Farmer("Farmer 1");
		compoundGameMap.at(5, 20).addActor(farmer1);
		
		Actor farmer2 = new Farmer("Farmer 2");
		compoundGameMap.at(10,  7).addActor(farmer2);
		
		Actor farmer3 = new Farmer("Farmer 3");
		compoundGameMap.at(10,  2).addActor(farmer3);
		
		Actor farmer4 = new Farmer("Farmer 4");
		compoundGameMap.at(40, 13).addActor(farmer4);
		
		// placing car in compound map and town map to connect them
		Location compoundCarLocation = compoundGameMap.at(43, 23);
		Location townCarLocation = townGameMap.at(45, 22);
		compoundCarLocation.addItem(new Car(townCarLocation, "to town"));
		townCarLocation.addItem(new Car(compoundCarLocation, "to compound"));
		
		world.run();
		
		}
}
