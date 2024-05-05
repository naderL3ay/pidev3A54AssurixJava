package Controllers;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoadingscreenController {
    @FXML
    private ImageView logoImageView;
    @FXML
    private Label progressLabel;
    @FXML
    private AnchorPane bord;
    @FXML
    private ProgressBar progressBar;

    public void initialize() {
        playLogoAnimation();
        fillProgressBar();
    }

    private void playLogoAnimation() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), logoImageView);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.play();
    }

    private void fillProgressBar() {
        int numSteps = 10; // Number of steps to fill the progress bar linearly
        Duration duration = Duration.seconds(4); // Total duration for filling the progress bar
        double stepValue = 1.0 / numSteps; // Value to increment progress bar per step

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), bord);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        Timeline timeline = new Timeline();

        // Start from 0% progress
        progressBar.setProgress(0.0);

        for (int i = 0; i <= numSteps; i++) {
            final double progress = i * stepValue;
            Duration delay = Duration.seconds(duration.toSeconds() * progress);
            KeyFrame keyFrame = new KeyFrame(delay, e -> {
                progressBar.setProgress(progress);
                progressLabel.setText(String.format("%.0f%%", progress * 100)); // Update progress label
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        // Add the final key frame to ensure the progress reaches 100%
        KeyFrame finalKeyFrame = new KeyFrame(duration, e -> {
            progressBar.setProgress(1.0);
            progressLabel.setText("100%"); // Update progress label to show completion
            loadAdminLoginScene(); // Transition to AdminLogin.fxml
        });
        timeline.getKeyFrames().add(finalKeyFrame);

        timeline.play();
    }


    private void loadAdminLoginScene() {
        RouterController Routes = new RouterController();
        Routes.navigate("../fxml/AdminLogin.fxml");
    }
}
