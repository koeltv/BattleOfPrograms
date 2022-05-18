package com.view;

import com.model.EliteSoldier;
import com.model.Soldier;
import com.model.WarMaster;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;

public class GraphicSoldier extends JLabel {

	private static final URL soldierUrl = AttributePanel.class.getResource("/images/soldier.png");
	private static final URL eliteSoldierUrl = AttributePanel.class.getResource("/images/elite_soldier.png");
	private static final URL warMasterUrl = AttributePanel.class.getResource("/images/commander.png");

	public Soldier soldier;

	public GraphicSoldier(WarMaster soldier) {
		super(new ImageIcon(Objects.requireNonNull(warMasterUrl)));
		this.soldier = soldier;
	}

	public GraphicSoldier(EliteSoldier soldier) {
		super(new ImageIcon(Objects.requireNonNull(eliteSoldierUrl)));
		this.soldier = soldier;
	}

	public GraphicSoldier(Soldier soldier) {
		super(new ImageIcon(Objects.requireNonNull(soldierUrl)));
		this.soldier = soldier;
	}
}
