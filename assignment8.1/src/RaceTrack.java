import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class RaceTrack extends Application {

    Image carImg = new Image("sportive-car.png");
    private int numCars;
    private int carLength = 32;
    private int trackLength = 400;
    private int finishLine = trackLength - (carLength);

    boolean winner = false;
    boolean paused = false;
    boolean started = false;
    boolean reset = false;

    CarThread[] carThreads;

    Stage stage;
    ImageView[] carViews;

    public void initStage(Stage stage){
        stage.setTitle("Richmond Raceway");

        stage.setX(500);
        stage.setY(500);

        stage.setMinHeight(200);
        stage.setMinWidth(500);
        stage.setResizable(false);

        Button startbt = new Button();
        Button pausebt = new Button();
        Button resetbt = new Button();

        startbt.setText("Start");
        pausebt.setText("Pause");
        resetbt.setText("Reset");

        startbt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                startRace();
            }
        });

        pausebt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                pauseRace();
            }
        });

        resetbt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                resetRace();
            }
        });

        HBox btbox = new HBox();
        btbox.getChildren().addAll(startbt, pausebt, resetbt);
        btbox.setPadding(new Insets(12.5, 0, 12.5, 0));
        btbox.setSpacing(100);
        btbox.setAlignment(Pos.CENTER);

        VBox raceContainer = new VBox();
        raceContainer.setSpacing(10);
        //Image carImg = new Image("sportive-car.png");

        carViews = new ImageView[numCars];
        HBox[] tracks = new HBox[numCars];
        carThreads = new CarThread[numCars];


        for (int i = 0; i < numCars; i++){
            ImageView carFrame = new ImageView(carImg);
            HBox carTrack = new HBox();

            // set car starting spot
            carFrame.setTranslateX((-1) * carLength);

            carTrack.setTranslateX(50);
            carTrack.setMaxWidth(trackLength);
            carTrack.setStyle("-fx-background-color: #CCCCCC");
            carTrack.getChildren().add(carFrame);

            raceContainer.getChildren().add(carTrack);

            carViews[i] = carFrame;
            tracks[i] = carTrack;
        }

        BorderPane pane = new BorderPane();
        pane.setTop(btbox);
        pane.setCenter(raceContainer);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }

    public void moveCar(int carIndex, int position){

        Thread t = new Thread( () -> {

            if(winner || reset){
                return;
            }

            if(position >= finishLine){
                carViews[carIndex].setTranslateX(finishLine);
                alertWinner(carIndex);
            } else {
                carViews[carIndex].setTranslateX(position);
            }

        });
        Platform.runLater(t);
    }

    public void alertWinner(int carIndex){

        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setContentText("Car " + (carIndex+1) + " Wins!" );
        winner = true;
        alert.show();
    }

    public void startRace(){
        Thread th = new Thread( () -> {
            if(started){
                paused = false;
                return;
            }

            started = true;
            reset = false;

            for (int i = 0; i < numCars; i++){
                CarThread t = new CarThread(this, i);
                carThreads[i] = t;
                t.start();
            }
        });
        Platform.runLater(th);
    }

    public void pauseRace(){
        paused = true;
    }

    public void resetRace(){
        Thread th = new Thread( () -> {

            reset = true;

            for (CarThread t : carThreads){
                t.interrupt();
            }

            resetCars();

            winner = false;
            paused = false;
            started = false;
        });
        Platform.runLater(th);
    }

    public void resetCars(){
        for(int i = 0; i < numCars; i++){
            carViews[i].setTranslateX(0);
        }
    }

    public void start(Stage stage) {
        this.numCars = 3;
        initStage(stage);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}