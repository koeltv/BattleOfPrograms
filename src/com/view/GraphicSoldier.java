package com.view;

import com.model.EliteSoldier;
import com.model.Soldier;
import com.model.WarMaster;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;

public class GraphicSoldier extends JLabel {

	private static final URL soldierUrl = AttributePanel.class.getResource("/images/soldier.png");
	private static final URL transparentSoldierUrl = AttributePanel.class.getResource("/images/soldier-t.png");
	private static final URL eliteSoldierUrl = AttributePanel.class.getResource("/images/elite_soldier.png");
	private static final URL transparentEliteSoldierUrl = AttributePanel.class.getResource("/images/elite_soldier-t.png");
	private static final URL warMasterUrl = AttributePanel.class.getResource("/images/commander.png");
	private static final URL transparentWarMasterUrl = AttributePanel.class.getResource("/images/commander-t.png");

	public boolean selected;

	public Soldier soldier;

	public GraphicSoldier(WarMaster soldier) {
		super(new ImageIcon(Objects.requireNonNull(transparentWarMasterUrl)));
		this.soldier = soldier;
	}

	public GraphicSoldier(EliteSoldier soldier) {
		super(new ImageIcon(Objects.requireNonNull(transparentEliteSoldierUrl)));
		this.soldier = soldier;
	}

	public GraphicSoldier(Soldier soldier) {
		super(new ImageIcon(Objects.requireNonNull(transparentSoldierUrl)));
		this.soldier = soldier;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;

		URL url;
		if (soldier instanceof WarMaster) url = selected ? warMasterUrl : transparentWarMasterUrl;
		else if (soldier instanceof EliteSoldier) url = selected ? eliteSoldierUrl : transparentEliteSoldierUrl;
		else url = selected ? soldierUrl : transparentSoldierUrl;

		setIcon(new ImageIcon(Objects.requireNonNull(url)));
		repaint();
	}
}
