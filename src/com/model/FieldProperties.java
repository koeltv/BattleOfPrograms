package com.model;

import java.net.URL;

/**
 * This enumeration contains properties linked to a field and makes it easier to identify them.
 * This includes the name of the field and its logo.
 */
public enum FieldProperties {
	/**
	 * Library field properties.
	 */
	LIBRARY ("Biblioth\u00E8que", FieldProperties.class.getResource("/images/library.png")),
	/**
	 * BDE field properties.
	 */
	BDE ("BDE", FieldProperties.class.getResource("/images/bde.png")),
	/**
	 * The Administrative quarter field properties.
	 */
	ADMINISTRATIVE_QUARTER ("Quartier administratif", FieldProperties.class.getResource("/images/administrative_quarter.png")),
	/**
	 * The Industrial halls' field properties.
	 */
	INDUSTRIAL_HALLS ("Halles industrielles", FieldProperties.class.getResource("/images/industrial_halls.png")),
	/**
	 * The Sports hall field properties.
	 */
	SPORTS_HALL ("Halle Sportive", FieldProperties.class.getResource("/images/sports_hall.png"));

	/**
	 * The Name.
	 */
	private final String name;
	/**
	 * The Url.
	 */
	public final URL url;

	/**
	 * Instantiates a new Field properties.
	 *
	 * @param name the name
	 * @param url  the url
	 */
	FieldProperties(String name, URL url) {
		this.name = name;
		this.url = url;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
