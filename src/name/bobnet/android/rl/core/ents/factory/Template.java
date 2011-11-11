/*
 * Class Name:			Template.java
 * Class Purpose:		A template for generating entities
 * Created by:			boris on 2011-11-04
 */
package name.bobnet.android.rl.core.ents.factory;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import name.bobnet.android.rl.core.ents.Entity;

/**
 * A template used to generate entities by a factory
 * 
 * @author boris
 */
public abstract class Template {

	// variables
	private int spawnOdd;
	private String name;

	/**
	 * Create a template from the data in the JSON object for the template
	 * 
	 * @param self
	 *            the JSON object holding the definition of the template
	 * @throws JSONException
	 *             Thrown when content has failed to load
	 * @throws NullPointerException
	 *             thrown if self is null
	 */
	public Template(JSONObject self) throws JSONException, NullPointerException {
		// get the values from the JSON object

		// load spawning odds
		setSpawnOdd(self.getInt("spawnodds"));

		// load the name of the object
		setName(self.getString("name"));
	}

	/**
	 * Check whether the entity can be created randomly with the given roll
	 * 
	 * @param roll
	 *            the number between 1 and 100 that was rolled
	 * @return whether or not this entity can be spawned
	 */
	public boolean checkRoll(int roll) {
		return spawnOdd <= roll;
	}

	/**
	 * Generate an entity based on this template
	 * 
	 * @param rnd
	 *            the random number generator to be used
	 * @return the entity generated from the template
	 */
	public abstract Entity generate(Random rnd);

	/**
	 * @return the spawnOdd
	 */
	public int getSpawnOdd() {
		return spawnOdd;
	}

	/**
	 * @param spawnOdd
	 *            the spawnOdd to set
	 */
	public void setSpawnOdd(int spawnOdd) {
		this.spawnOdd = spawnOdd;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
