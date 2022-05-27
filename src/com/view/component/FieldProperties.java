package com.view.component;

import java.net.URL;

/**
 * This enumeration contains properties linked to a field and makes it easier to identify them.
 * This includes the name of the field and its logo.
 */
public enum FieldProperties {
	LIBRARY ("Biblioth\u00E8que", FieldProperties.class.getResource("/images/library.png")),
	BDE ("BDE", FieldProperties.class.getResource("/images/bde.png")),
	ADMINISTRATIVE_QUARTER ("Quartier administratif", FieldProperties.class.getResource("/images/administrative_quarter.png")),
	INDUSTRIAL_HALLS ("Halles industrielles", FieldProperties.class.getResource("/images/industrial_halls.png")),
	SPORTS_HALL ("Halle Sportive", FieldProperties.class.getResource("/images/sports_hall.png"));

	private final String name;
	public final URL url;

	FieldProperties(String name, URL url) {
		this.name = name;
		this.url = url;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
