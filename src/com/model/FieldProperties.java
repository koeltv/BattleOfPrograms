package com.model;

import com.view.Resource;

/**
 * This enumeration contains properties linked to a field and makes it easier to identify them.
 * This includes the name of the field and its logo.
 */
public enum FieldProperties {
	/**
	 * Library field properties.
	 */
	LIBRARY ("Biblioth\u00E8que", Resource.LIBRARY),
	/**
	 * BDE field properties.
	 */
	BDE ("BDE", Resource.BDE),
	/**
	 * The Administrative quarter field properties.
	 */
	ADMINISTRATIVE_QUARTER ("Quartier administratif", Resource.ADMINISTRATIVE_QUARTER),
	/**
	 * The Industrial halls' field properties.
	 */
	INDUSTRIAL_HALLS ("Halles industrielles", Resource.INDUSTRIAL_HALLS),
	/**
	 * The Sports hall field properties.
	 */
	SPORTS_HALL ("Halle Sportive", Resource.SPORTS_HALL);

	/**
	 * The Name.
	 */
	private final String name;
	/**
	 * The Url.
	 */
	public final Resource resource;

	/**
	 * Instantiates a new Field properties.
	 *
	 * @param name the name of the field
	 * @param resource  the resource that represents the field
	 */
	FieldProperties(String name, Resource resource) {
		this.name = name;
		this.resource = resource;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
