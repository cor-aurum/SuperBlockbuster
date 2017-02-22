package de.recondita.blockbuster.gui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

	private BorderPane background = new BorderPane();
	private Pane feld = new Pane();
	private int maxx = 10;
	private int maxy = 10;
	private ArrayList<Spielstein> steine = new ArrayList<Spielstein>();
	private VBox menu = new VBox();
	private Slider x = new Slider();
	private Slider y = new Slider();
	private Slider groesse = new Slider();
	private Button neu = new Button("Neues Spiel");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Super Blockbuster");
		Scene scene = new Scene(background, 820, 600, true);
		background.setLeft(feld);
		background.setRight(menu);
		menu.setSpacing(50);

		x.setMin(3);
		x.setValue(10);
		x.setMax(100);

		y.setMin(3);
		y.setValue(10);
		y.setMax(100);

		groesse.setMin(0.5);
		groesse.setValue(1);
		groesse.setMax(10);

		feld.scaleXProperty().bind(groesse.valueProperty());
		feld.scaleYProperty().bind(groesse.valueProperty());
		feld.translateXProperty().bind(background.heightProperty().divide(2)
				.subtract(feld.heightProperty().divide(2).multiply(groesse.valueProperty())).multiply(-1));
		feld.translateYProperty().bind(background.heightProperty().divide(2)
				.subtract(feld.heightProperty().divide(2).multiply(groesse.valueProperty())));

		menu.getChildren().addAll(new Label("X und Y Wert wählen"), x, y, neu, new Label("Größe des Feldes"), groesse);
		neu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				maxx = (int) x.getValue();
				maxy = (int) y.getValue();
				feld.getChildren().clear();
				steine = new ArrayList<Spielstein>();
				initSpielfeld();
			}

		});

		feld.prefWidthProperty().bind(scene.heightProperty());
		scene.getStylesheets().add("de/recondita/blockbuster/gui/material.css");
		background.setId("background");
		stage.setScene(scene);

		initSpielfeld();
		stage.show();
	}

	public void initSpielfeld() {
		for (int i = 0; i < maxx; i++) {
			for (int j = 0; j < maxy; j++) {
				steine.add(new Spielstein(this, i, j));
			}
		}
	}

	public Pane getSpielfeld() {
		return feld;
	}

	public int getMaxx() {
		return maxx;
	}

	public int getMaxy() {
		return maxy;
	}

	public int getMax() {
		return maxy > maxx ? maxy : maxx;
	}

	public Spielstein[] getSteine() {
		Spielstein[] steine = new Spielstein[this.steine.size()];
		this.steine.toArray(steine);
		return steine;
	}

	public ArrayList<Spielstein> getSteinListe() {
		return steine;
	}
}
