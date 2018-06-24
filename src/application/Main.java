package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	public void start(Stage primaryStage) {
		try {
			Font.loadFont(getClass().getResource("resources/fontawesome-webfont.ttf").toExternalForm(), 20);
			final Stage stageRef = primaryStage;
			StackPane root = new StackPane();
			BorderPane node0bp = new BorderPane();
			node0bp.setTop(new NodeMenuBar(stageRef));
			GridPane gpCenter = new GridPane();

			NodeDragMusicfolder plAktivView = new NodeDragMusicfolder("Playlist Aktiv");
			NodeDragMusicfolder plPauseView = new NodeDragMusicfolder("Playlist Pause");

			gpCenter.add(plAktivView, 0, 0);
			gpCenter.add(plPauseView, 1, 0);

			node0bp.setCenter(gpCenter);

			Button btnStart = new Button("Start");
			Button btnStop = new Button("Stop");

			btnStart.setOnAction(a -> {
				new StageTimeView(20, 20, plAktivView.getListMusicFiles(), plPauseView.getListMusicFiles());
				node0bp.setBottom(btnStop);
				btnStop.setVisible(false);
			});

			btnStop.setOnAction(a -> {
				node0bp.setCenter(gpCenter);
				node0bp.setBottom(btnStart);
			});
			node0bp.setBottom(btnStart);
			root.getChildren().add(node0bp);

			Scene scene = new Scene(root, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// stageRef.setFullScreen(true);
			stageRef.setScene(scene);
			stageRef.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
