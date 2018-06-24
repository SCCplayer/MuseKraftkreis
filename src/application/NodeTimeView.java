package application;

import java.io.File;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class NodeTimeView extends BorderPane {
	private Label lblZeitAnzeige;
	private Label lblTop = new Label("Startverzögerung");

	private BorderPane bpTop = new BorderPane();

	private Random random = new Random();

	private int zeitAktiv = 0;
	private int zeitAktivStartwert = 0;
	private int zeitPause = 0;
	private int zeitPauseStartwert = 0;
	private int startDelay = 5;
	private Label lblHeader;
	private MediaPlayer playerActive;
	private MediaPlayer playerPause;
	private MediaPlayer playerWechselSignal;
	private MediaPlayer playerStartSignal;
	private MediaPlayer playerStopSignal;

	private ObservableList<File> listMusicAktiv = null;
	private ObservableList<File> listMusicPause = null;

	public NodeTimeView(int zeitAktiv, int zeitPause, ObservableList<File> listMusicAktiv,
			ObservableList<File> listMusicPause) {
		// TODO Auto-generated constructor stub
		this.zeitAktivStartwert = zeitAktiv;
		this.zeitPauseStartwert = zeitPause;
		this.zeitAktiv = zeitAktivStartwert;
		this.zeitPause = zeitPauseStartwert;
		this.listMusicAktiv = listMusicAktiv;
		this.listMusicPause = listMusicPause;

		lblTop.getStyleClass().add("lblTop");
		bpTop.setCenter(lblTop);
		this.setTop(bpTop);
		lblZeitAnzeige = new Label(String.valueOf(startDelay));
		lblZeitAnzeige.getStyleClass().add("labelZeitanzeige");
		this.getStyleClass().add("bpZeitanzeige");
		this.setCenter(lblZeitAnzeige);
		playerStartSignal = new MediaPlayer(
				new Media(getClass().getResource("resources/Startsignal.mp3").toExternalForm()));
		playerStopSignal = new MediaPlayer(
				new Media(getClass().getResource("resources/LeiderGeil.mp3").toExternalForm()));

		playerWechselSignal = new MediaPlayer(
				new Media(getClass().getResource("resources/Wechselsignal.mp3").toExternalForm()));

		Timeline timerStartDelay = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
			startDelay = startDelay - 1;
			lblZeitAnzeige.setText(String.valueOf(startDelay));
			if (startDelay == 0) {
				startAktiv();
			}

		}));
		timerStartDelay.setCycleCount(5);
		playerStartSignal.play();
		timerStartDelay.play();

	}

	private void startAktiv() {
		playAktivSong();
		lblTop.setText("Aktive Phase");
		Timeline timerAktiv = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
			zeitAktiv = zeitAktiv - 1;
			lblZeitAnzeige.setText(String.valueOf(zeitAktiv));
			zeitPause = zeitPauseStartwert;
			if (zeitAktiv == zeitAktivStartwert / 2) {
				playerWechselSignal.stop();
				playerWechselSignal.play();
				System.out.println(playerActive.getVolume());
				playerActive.setVolume(0.2);
				playerVolumeFadeMax(playerActive);
			} else if (zeitAktiv == 1) {
				playerVolumeFadeOut(playerActive);
			} else if (zeitAktiv == 0) {
				playerWechselSignal.stop();
				playerWechselSignal.play();
				playerActive.stop();
				startPause();
			}
		}));
		timerAktiv.setCycleCount(zeitAktiv);
		timerAktiv.play();

	}

	private void startPause() {
		lblTop.setText("Pause Phase");
		playPauseSong();
		Timeline timerPause = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
			zeitPause = zeitPause - 1;
			lblZeitAnzeige.setText(String.valueOf(zeitPause));
			zeitAktiv = zeitAktivStartwert;
			if (zeitPause == 15) {
				playerWechselSignal.stop();
				playerWechselSignal.play();
			} else if (zeitPause == 6) {
				playerStartSignal.stop();
				playerStartSignal.play();
			} else if (zeitPause == 0) {
				playerPause.stop();
				startAktiv();
			}
		}));
		timerPause.setCycleCount(zeitPause);
		timerPause.play();
	}

	private void playAktivSong() {
		int z = 0;
		z = (int) (random.nextDouble() * listMusicAktiv.size());
		playerActive = new MediaPlayer(new Media(listMusicAktiv.get(z).toURI().toString()));
		playerActive.play();
		playerActive.setOnStopped(new Runnable() {

			@Override
			public void run() {
				playerActive.dispose();
				playerActive = null;
				System.out.println(playerActive);
				System.out.println("Player Disposed");
			}
		});
		System.out.println(z);
	}

	private void playPauseSong() {
		int z = 0;
		z = (int) (random.nextDouble() * listMusicPause.size());
		playerPause = new MediaPlayer(new Media(listMusicPause.get(z).toURI().toString()));
		playerPause.play();
		System.out.println(z);
	}

	private void playerVolumeFadeMax(MediaPlayer mp) {
		Timeline timerFadeInMax = new Timeline(new KeyFrame(Duration.millis(10), ae -> {
			mp.setVolume(mp.getVolume() + 0.8 / 100);
			System.out.println(mp.getVolume());

		}));
		timerFadeInMax.setCycleCount(100);
		timerFadeInMax.play();
		timerFadeInMax.statusProperty().addListener((ov, oldValue, newValue) -> {
			System.out.println(newValue.toString() + " " + oldValue.toString());
		});
		// timerFadeInMax.statusProperty().addListener(listener);
	}

	private void playerVolumeFadeOut(MediaPlayer mp) {
		Timeline timerFadeInMax = new Timeline(new KeyFrame(Duration.millis(10), ae -> {
			mp.setVolume(mp.getVolume() - 0.8 / 100);
			System.out.println(mp.getVolume());
		}));
		timerFadeInMax.setCycleCount(100);
		timerFadeInMax.play();
	}

	public void stopAll() {

	}
}
