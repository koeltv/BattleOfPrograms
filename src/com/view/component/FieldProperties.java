package com.view.component;

import java.net.URL;

public enum FieldProperties {
	LIBRARY ("Biblioth\u00E8que", FieldProperties.class.getResource("/images/library.png")),
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

	@Override
	public String toString() {
		char[] charArray = super.toString().replace("_", " ").toCharArray();
		for (int i = 1; i < charArray.length; i++) {
			if (charArray[i - 1] != ' ') charArray[i] = Character.toLowerCase(charArray[i]);
		}
		return new String(charArray);
	}
}
