// This script implements a simple clock application with stopwatch and countdown functionality using JavaFX for the graphical user interface. The application displays the current time and allows the user to start, reset and stop a stopwatch, as well as start a countdown timer with an optional alarm sound. Lambda expressions are user to handle button actions, simplifying the code structure and improving readability.

// GUI Packages
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
// MISC Packages
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Application {

    // Variables
    private Label lblDateTime;
    private Label lblStopwatch;
    private Timer stopwatchTimer;
    private Timer countdownTimer;
    private int timerSeconds = 0;
    private boolean countdownActive = false;

    @Override
    public void start(Stage primaryStage) {
        // Create GUI DateTime elements
        primaryStage.setTitle("Clock");
        lblDateTime = new Label();
        lblDateTime.setAlignment(Pos.CENTER);
        lblStopwatch = new Label("00:00:00");
        lblStopwatch.setAlignment(Pos.CENTER);
        Button btnStartStopwatch = new Button("Start Stopwatch");
        Button btnResetStopwatch = new Button("Reset Stopwatch");
        Button btnStartCountdown = new Button("Start Countdown");
        TextField tfCountdownSeconds = new TextField();
        tfCountdownSeconds.setPromptText("Countdown Seconds");
        tfCountdownSeconds.setMaxWidth(150);
        tfCountdownSeconds.setAlignment(Pos.CENTER);
        tfCountdownSeconds.setTooltip(new Tooltip("Enter countdown seconds"));
        ComboBox<String> cbTimeZones = new ComboBox<>();
        cbTimeZones.getItems().addAll(DateTimeFormatter.getAvailableZoneIds());
        cbTimeZones.getSelectionModel().select(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(LocalDateTime.now()));

        // Configure buttons actions using lambda expressions
        btnStartStopwatch.setOnAction(e -> {
            if (stopwatchTimer == null) {
                stopwatchTimer = new Timer();
                stopwatchTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        timerSeconds++;
                        lblStopwatch.setText(formatTime(timerSeconds)); // Update the stopwatch label with elapsed time
                    }
                }, 0, 1000);
            } else {
                stopwatchTimer.cancel();
                stopwatchTimer = null;
            }
        });

        btnResetStopwatch.setOnAction(e -> {
            timerSeconds = 0;
            lblStopwatch.setText(formatTime(timerSeconds)); // Reset the stopwatch label to show 00:00:00
        });

        btnStartCountdown.setOnAction(e -> {
            if (countdownTimer == null) {
                countdownTimer = new Timer();
                countdownTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (countdownActive) {
                            timerSeconds--;
                        } else {
                            timerSeconds++;
                        }
                        lblDateTime.setText(formatTime(timerSeconds)); // Update the label with elapsed time
                    }
                }, 0, 1000);
            } else {
                countdownTimer.cancel();
                countdownTimer = null;
            }
            countdownActive = !countdownActive;
        });

        // Layout configuration
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(lblDateTime, lblStopwatch, btnStartStopwatch, btnResetStopwatch, btnStartCountdown, tfCountdownSeconds, cbTimeZones);

        // Displaying the GUI
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to format time in HH:mm:ss format
    private String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
