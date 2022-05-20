package com.view.component;

import java.net.URL;

public enum FieldProperties {
	LIBRARY ("Bibliothèque", FieldProperties.class.getResource("/images/library.png")),
	BDE ("BDE", FieldProperties.class.getResource("/images/bde.png")),
	ADMINISTRATIVE_QUARTER ("Quartier administratif", FieldProperties.class.getResource("/images/administrative_quarter.png")),
	INDUSTRIAL_HALLS ("Halles industrielles", FieldProperties.class.getResource("/images/industrial_halls.png")),
	SPORTS_HALL ("Halle Sportive", FieldProperties.class.getResource("/images/sports_hall.png"));

	public final String name;
	public final URL url;

	FieldProperties(String name, URL url) {
		this.name = name;
		this.url = url;
	}
}
