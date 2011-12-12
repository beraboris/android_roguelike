/*
 * Class Name:			Tile.java
 * Class Purpose:		Game tile that contains entities
 * Created by:			boris on 2011-09-30
 */
package name.bobnet.android.rl.core.ents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import name.bobnet.android.rl.core.ents.tiles.TileType;
import name.bobnet.android.rl.core.ents.tiles.Wall;
import name.bobnet.android.rl.core.ents.tiles.TileType.TileStyle;
import name.bobnet.android.rl.core.message.Message;

public class Tile extends Entity {

	/*
	 * Entities that can be on the tile
	 * 
	 * - Super Entities (Spells, Arrows, Rocks, etc.) 
	 * - Monster or player 
	 * - Items on the floor 
	 * - The type of the tile itself 
	 * 		(Floor, Wall, Stairs, Shallow Water, Deep Water, Lava, etc.)
	 */
	private ArrayList<Entity> superEnts;
	private Creature mob;
	private Stack<Item> items;
	private TileType tileType;
	private boolean visible;
	private boolean genUsed;
	private int x, y;

	/**
	 * @param x
	 *            position of the tile in the dungeon
	 * @param y
	 *            position of the tile in the dungeon
	 * @throws IndexOutOfBoundsException
	 *             thrown when x or y are out of bounds
	 */
	public Tile(int x, int y) throws IndexOutOfBoundsException {
		this(new Wall(TileStyle.ROCK), x, y);
	}

	/**
	 * @param tileType
	 *            the type of tile to be made (Wall, floor, door, etc.)
	 * @param x
	 *            position of the tile in the dungeon
	 * @param y
	 *            position of the tile in the dungeon
	 * @throws IndexOutOfBoundsException
	 *             thrown when x or y are out of bounds
	 */
	public Tile(TileType tileType, int x, int y)
			throws IndexOutOfBoundsException {
		setTileType(tileType);

		// create the items stack
		superEnts = new ArrayList<Entity>();
		items = new Stack<Item>();

		// set position
		setX(x);
		setY(y);

		// set fields
		setVisible(false);
		setGenUsed(false);
	}

	@Override
	public void tick() {
		// call the tick method of our children

		// general purpose iterator
		Iterator<Entity> eIt;

		// super ents
		eIt = superEnts.iterator();
		while (eIt.hasNext()) {
			eIt.next().tick();
		}

		// mob
		if (mob != null)
			mob.tick();

		// items
		Iterator<Item> iIt = items.iterator();
		while (iIt.hasNext())
			iIt.next().tick();

		// tileType
		tileType.tick();
	}

	@Override
	public void processMessage(Message message) {
		// send the message to all children

		// general purpose iterator
		Iterator<Entity> it;

		// super ents
		it = superEnts.iterator();
		while (it.hasNext()) {
			it.next().processMessage(message);
		}

		// mob
		if (mob != null)
			mob.processMessage(message);

		// items
		Iterator<Item> iIt = items.iterator();
		while (iIt.hasNext())
			iIt.next().processMessage(message);

		// tileType
		tileType.processMessage(message);
	}

	/**
	 * @return the iterator of the super ents
	 */
	public Iterator<Entity> getSuperEntsIterator() {
		return superEnts.iterator();
	}

	/**
	 * Add a super entity
	 * 
	 * @throws NullPointerException
	 *             thrown when supEnt is null
	 */
	public void addSuperEntity(Entity supEnt) {
		if (supEnt == null)
			throw new NullPointerException("proj cannot be null");

		// set the parent
		supEnt.setParent(this);

		// add the superEnt
		superEnts.add(supEnt);
	}

	/**
	 * @return the number of items on the floor
	 */
	public int getNumItems() {
		return items.size();
	}

	/**
	 * @return the iterator of the items
	 */
	public Iterator<Item> getItemsIterator() {
		return items.iterator();
	}

	/**
	 * @param item
	 *            item to be added
	 */
	public void addItem(Item item) {
		if (item == null)
			throw new NullPointerException("item cannot be null");

		// set the parent
		item.setParent(this);

		// add the item
		items.push(item);
	}

	/**
	 * @return the mob
	 */
	public Creature getMob() {
		return mob;
	}

	/**
	 * @param mob
	 *            the mob to set
	 * @throws RuntimeException
	 *             thrown when there's already a mob on the tile
	 */
	public void setMob(Creature mob) {
		if (this.mob != null)
			throw new RuntimeException("Mob already exists");

		if (mob != null)
			// set the parent
			mob.setParent(this);

		// set the new mob
		this.mob = mob;
	}

	/**
	 * Delete the current mob
	 * 
	 * @return the mob that was deleted
	 */
	public Creature delMob() {
		Creature tMob = mob;
		mob = null;
		return tMob;
	}

	/**
	 * @return the tileType
	 */
	public TileType getTileType() {
		return tileType;
	}

	/**
	 * @param tileType
	 *            the tileType to set
	 * 
	 * @throws NullPointerException
	 *             thrown when tileType is null
	 */
	public void setTileType(TileType tileType) {
		if (tileType == null)
			throw new NullPointerException("tileType cannot be null");

		// set the parent
		tileType.setParent(this);

		// set the tile type
		this.tileType = tileType;
	}

	/**
	 * @return whether or not a creature can go in the tile
	 */
	public boolean isPassthrough() {
		// check if there's a creature there
		if (mob != null)
			return false;
		else
			// use the tileType to determine
			return tileType.isPassThrough();
	}

	/**
	 * @return the seeThrough
	 */
	public boolean isSeeThrough() {
		return tileType.isSeeThrough();
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 * @throws IndexOutOfBoundsException
	 *             thrown when x is out of bounds
	 */
	public void setX(int x) {
		if (x < 0 && x >= Dungeon.D_WIDTH)
			throw new IndexOutOfBoundsException("x is out of bounds");

		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 * @throws IndexOutOfBoundsException
	 *             thrown when y is out of bounds
	 */
	public void setY(int y) {
		if (y < 0 && y >= Dungeon.D_HEIGHT)
			throw new IndexOutOfBoundsException("y is out of bounds");

		this.y = y;
	}

	/**
	 * @return true if the generator has used this tile
	 */
	public boolean isGenUsed() {
		return genUsed;
	}

	/**
	 * @param genUsed
	 *            whether or not the generator has used that tile
	 */
	public void setGenUsed(boolean genUsed) {
		this.genUsed = genUsed;
	}
}
