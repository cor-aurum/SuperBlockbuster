package de.recondita.blockbuster.gui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Spielstein extends Button {
	private int farbe = 0;
	private int x;
	private int y;
	private GUI gUI;

	public Spielstein(GUI gUI, int x, int y) {
		this.x = x;
		this.y = y;
		this.gUI = gUI;
		farbe = (int) (Math.random() * 4);
		setId("stein" + farbe);
		gUI.getSpielfeld().getChildren().add(this);
		super.layoutXProperty().bind(gUI.getSpielfeld().widthProperty().divide(gUI.getMaxx()).multiply(x));
		super.layoutYProperty().bind(gUI.getSpielfeld().heightProperty().divide(gUI.getMaxy()).multiply(y));
		super.maxHeightProperty().bind(gUI.getSpielfeld().heightProperty().divide(gUI.getMaxy()));
		super.maxWidthProperty().bind(gUI.getSpielfeld().heightProperty().divide(gUI.getMaxx()));
		super.minHeightProperty().bind(gUI.getSpielfeld().heightProperty().divide(gUI.getMaxy()));
		super.minWidthProperty().bind(gUI.getSpielfeld().heightProperty().divide(gUI.getMaxx()));

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Spielstein steine[] = findeNachbar(new ArrayList<Spielstein>());
				if (steine.length > 1) {
					for (Spielstein s : steine) {
						s.verschwinde();
					}
				}
			}

		});
	}

	public void verschwinde() {
		gUI.getSpielfeld().getChildren().remove(this);
		gUI.getSteinListe().remove(this);
		Spielstein[] steine = getUeber();
		for (Spielstein s : steine) {
			s.rutscheRunter();
		}
		if (steine.length == 0 && getUnter().length == 0) {
			for (Spielstein s : getNeben()) {
				s.rutscheRueber();
			}
		}
	}

	private Spielstein[] getUeber() {
		ArrayList<Spielstein> steine = new ArrayList<Spielstein>();
		for (Spielstein s : gUI.getSteine()) {
			if (s.getX() == x && s.getY() < y) {
				steine.add(s);
			}
		}
		Spielstein[] ret = new Spielstein[steine.size()];
		steine.toArray(ret);
		return ret;
	}

	private Spielstein[] getUnter() {
		ArrayList<Spielstein> steine = new ArrayList<Spielstein>();
		for (Spielstein s : gUI.getSteine()) {
			if (s.getX() == x && s.getY() > y) {
				steine.add(s);
			}
		}
		Spielstein[] ret = new Spielstein[steine.size()];
		steine.toArray(ret);
		return ret;
	}

	private Spielstein[] getNeben() {
		ArrayList<Spielstein> steine = new ArrayList<Spielstein>();
		for (Spielstein s : gUI.getSteine()) {
			if (s.getX() > x) {
				steine.add(s);
			}
		}
		Spielstein[] ret = new Spielstein[steine.size()];
		steine.toArray(ret);
		return ret;
	}

	public int getFarbe() {
		return farbe;
	}

	public void rutscheRunter() {
		y++;
		super.layoutYProperty().bind(gUI.getSpielfeld().heightProperty().divide(gUI.getMaxy()).multiply(y));
	}

	public void rutscheRueber() {
		x--;
		super.layoutXProperty().bind(gUI.getSpielfeld().widthProperty().divide(gUI.getMaxx()).multiply(x));
	}

	public Spielstein[] findeNachbar(ArrayList<Spielstein> steine) {
		if(!steine.contains(this))
			steine.add(this);
		for (Spielstein s : gUI.getSteine()) {
			if ((s.getX() == x && (s.getY() == y - 1 || s.getY() == y + 1) && s.getFarbe() == farbe)
					|| (s.getY() == y && (s.getX() == x - 1 || s.getX() == x + 1) && s.getFarbe() == farbe)) {
				if (!steine.contains(s)) {
					steine.add(s);
					s.findeNachbar(steine);
				}
			}
		}
		Spielstein[] ret = new Spielstein[steine.size()];
		steine.toArray(ret);
		return ret;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
