package application;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StageTimeView extends Stage {

	public StageTimeView(int zeitAktiv, int zeitPause, ObservableList<File> listMusicAktiv,
			ObservableList<File> listMusicPause) {
		BorderPane root = new BorderPane();
		root.setTop(new NodeMenuBar(this));
		NodeTimeView ntv = new NodeTimeView(20, 20, listMusicAktiv, listMusicPause);
		root.setCenter(ntv);
		Scene scene = new Scene(root, 800, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.setOnCloseRequest(e -> {
			ntv.stopAll();
		});
		this.setFullScreen(true);
		this.setScene(scene);
		this.show();
	}

}
