package com.eerichmond.core.domain;

/**
 * The hierarchy level associated with a node in a hierarchy.
 */
public enum HierarchyLevel {
	/**
	 * The edge node that does not have any child nodes.
	 */
	LEAF,
	/**
	 * The branch right before a leaf node, indicating that this node only has one level of child nodes beneath.
	 */
	TWIG,
	/**
	 * Has one or more parent nodes and one or more child nodes.
	 */
	BRANCH,
	/**
	 * The highest node in the hierarchy. Does not have a parent node.
	 */
	ROOT
}
