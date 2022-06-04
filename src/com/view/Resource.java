package com.view;

import javax.swing.*;
import java.util.Objects;

/**
 * This enumeration loads all the images needed for the game.
 */
public enum Resource {
	/**
	 * Background resource.
	 */
	BACKGROUND("/images/background_map.PNG"),
	/**
	 * Camouflage resource.
	 */
	CAMOUFLAGE("/images/camo.jpg"),
	/**
	 * Realistic map resource.
	 */
	REALISTIC_MAP("/images/interactive_map.PNG"),

	/**
	 * Soldier resource.
	 */
	SOLDIER("/images/soldier.png"),
	/**
	 * Transparent soldier resource.
	 */
	TRANSPARENT_SOLDIER("/images/soldier-t.png"),
	/**
	 * Elite soldier resource.
	 */
	ELITE_SOLDIER("/images/elite_soldier.png"),
	/**
	 * Transparent elite soldier resource.
	 */
	TRANSPARENT_ELITE_SOLDIER("/images/elite_soldier-t.png"),
	/**
	 * War master resource.
	 */
	WAR_MASTER("/images/commander.png"),
	/**
	 * Transparent war master resource.
	 */
	TRANSPARENT_WAR_MASTER("/images/commander-t.png"),

	/**
	 * Library resource.
	 */
	LIBRARY("/images/library.png"),
	/**
	 * Bde resource.
	 */
	BDE("/images/bde.png"),
	/**
	 * Administrative quarter resource.
	 */
	ADMINISTRATIVE_QUARTER("/images/administrative_quarter.png"),
	/**
	 * Industrial halls' resource.
	 */
	INDUSTRIAL_HALLS("/images/industrial_halls.png"),
	/**
	 * Sports hall resource.
	 */
	SPORTS_HALL("/images/sports_hall.png"),

	/**
	 * Vs animated resource.
	 */
	VS_ANIMATED("/images/vs.gif"),
	/**
	 * White flag resource.
	 */
	WHITE_FLAG("/images/white_flag.gif"),
	/**
	 * Dust cloud resource.
	 */
	DUST_CLOUD("/images/dust_cloud.gif");

	/**
	 * The Image.
	 */
	public final ImageIcon image;

	/**
	 * Instantiates a new Resource.
	 *
	 * @param url the url of the image to fetch
	 */
	Resource(String url) {
		ImageIcon temp = null;
		try {
			temp = new ImageIcon(Objects.requireNonNull(Resource.class.getResource(url)));
		} catch (NullPointerException e) {
			NullPointerException exception = new NullPointerException("Ressource not found at " + url);
			exception.initCause(e);
			exception.printStackTrace();
		} finally {
			image = temp;
		}
	}
}
