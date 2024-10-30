/* Alex Welk - CMSC 403 Assignment 8 - 12/18/22 */
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


//RaceTrack class
public class RaceTrack extends Application {
    Image carPic = new Image("sportive-car.png");
    private int numCar;
    private int trackLength = 400;
    private int carLength = 32;
    private int finish = trackLength = carLength;

    CarThread[] threads;

    Stage stage;
    ImageView[] view;

    boolean start = false;
    boolean reset = false;
    boolean pause = false;
    boolean winner = false;

    //trackStage function to create the set of the race
    public void trackStage(Stage stage) {
        stage.setTitle("CMSC 403 Raceway!");
        stage.setResizable(false);
        stage.setX(500);
        stage.setY(500);
        stage.setMinWidth(500);
        stage.setMinHeight(200);

        //creating new buttons for start, pause, and reset
        Button start = new Button("Start");
        Button pause = new Button("Pause");
        Button reset = new Button("Reset");

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startRace();
            }
        });

        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pauseRace();
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetRace();
            }
        });

        VBox race = new VBox();
        race.setSpacing(10);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(100);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(start, pause, reset);
        buttonBox.setPadding(new Insets(12.5, 0, 12.5, 0));

        view = new ImageView[numCar];
        threads = new CarThread[numCar];
        HBox[] tracks = new HBox[numCar];

        for(int i = 0; i < numCar; i++) {
            ImageView car = new ImageView(carPic);
            HBox track = new HBox();
            car.setTranslateX((-1) * carLength);

            track.setTranslateX(50);
            track.setMaxWidth(trackLength);
            track.setStyle("-fx-background-color: #CCCCCC");
            track.getChildren().add(car);

            view[i] = car;
            tracks[i] = track;
        }

        BorderPane pane = new BorderPane();
        pane.setTop(buttonBox);
        pane.setCenter(race);

        Scene scene = new Scene(pane);
        stage.setScene(scene);

    }

    //startRace function
    public void startRace() {
        Thread thread = new Thread( () -> {
            if(start) {
                pause = false;
                return;
            }
        });
        Platform.runLater(thread);
    }

    //pauseRace function
    public void pauseRace() {
        pause = true;
    }

    //resetCars function
    public void resetCars() {
        for(int i = 0; i < numCar; i ++) {
            view[i].setTranslateX(0);
        }
    }
    //resetRace function
    public void resetRace() {
        Thread thread = new Thread( () -> {
            reset = true;
            for(CarThread t: threads) {
                t.interrupt();
            }

            resetCars();

            winner = false;
            start = false;
            pause = false;
        });
        Platform.runLater(thread);
    }

    public void start(Stage stage) {
        this.numCar = 3;
        trackStage(stage);
        stage.show();
    }

    //moves the car as the thread is processed
    public void moveCar(int index, int position) {
        Thread thread = new Thread( () -> {
            if(reset || winner)
                return;

            if(position >= finish) {
                view[index].setTranslateX(finish);
                alert(index);
            }
            else {
                view[index].setTranslateX(position);
            }
        });
        Platform.runLater(thread);
    }

    //alerts user of winner
    public void alert(int index) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText((index + 1) + "wins");
        winner = true;
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}