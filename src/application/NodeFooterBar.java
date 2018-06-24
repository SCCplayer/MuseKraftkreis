package application;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class NodeFooterBar extends FlowPane {
	private Label lblZeitAktiv = new Label("Dauer Aktiv");
	private TextField tfZeitAktiv = new TextField("30");
	private Label lblSek1 = new Label("Sekunden");
	private Label lblZeitPause = new Label("Dauer Pause");
	private TextField tfZeitPause = new TextField("30");
	private Label lblSek2 = new Label("Sekunden");

	private Button btnStart = new Button("Start");

	public NodeFooterBar(Node parent) {
		// TODO Auto-generated constructor stub

	}

}
