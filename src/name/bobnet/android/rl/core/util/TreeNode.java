/*
 * Class Name:			TreeNode.java
 * Class Purpose:		A generic tree structure
 * Created by:			boris on 2011-11-04
 */
package name.bobnet.android.rl.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TreeNode {
	// variables
	private Map<String, TreeNode> children;
	private Object value;
	private String name;

	/**
	 * Create a tree node with a value
	 * 
	 * @param the
	 *            value of the tree node
	 */
	public TreeNode(Object value, String name) {
		this(name);
		this.value = value;
	}

	/**
	 * Create a tree node with a null value
	 * 
	 * @param name
	 *            the name of the node
	 */
	public TreeNode(String name) {
		children = new HashMap<String, TreeNode>();
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * get the number of children
	 * 
	 * @return the number of children
	 */
	public int getChildCount() {
		return children.size();
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

	/**
	 * Find a child of the node by name
	 * 
	 * @param name
	 *            the name of the child
	 */
	public TreeNode getChild(String name) {
		return children.get(name);
	}

	/**
	 * Add a tree node to this node
	 * 
	 * @param node
	 *            the tree node to add
	 */
	public void addChild(TreeNode node) {
		children.put(node.getName(), node);
	}

	/**
	 * get a set of the names of all the children
	 * 
	 * @return a set of the names of all the children
	 */
	public Set<String> getNames() {
		return children.keySet();
	}

	/**
	 * Clear the children of the node
	 */
	public void clearChildren() {
		children.clear();
	}
}