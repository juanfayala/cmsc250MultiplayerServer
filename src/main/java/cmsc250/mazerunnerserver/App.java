package cmsc250.mazerunnerserver;

import edu.lawrence.pongserver.simulation.Simulation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    // Text area for displaying contents
    private TextArea ta = new TextArea();

    // Number a client
    private int clientNo = 0;

    // The Simulation
    private Simulation game;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        game = new Simulation(2, 2);

        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("MazeRunnerServer"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show(); // Display the stage

        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                ta.appendText("MazeRunnerServer started at " + new Date() + '\n');

                while (clientNo < 2) {
                    // Listen for a new connection request
                    Socket socket = serverSocket.accept();

                    // Increment clientNo
                    clientNo++;

                    Platform.runLater(() -> {
                        // Display the client number
                        ta.appendText("Starting thread for client " + clientNo + '\n');
                    });

                    // Create and start a new thread for the connection
                    new Thread(new HandleAClient(game, clientNo, socket)).start();
                    // Start the game when client number two connects.
                    if (clientNo == 2) {
                        new Thread(new Updater(game)).start();
                    }
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }

    class Updater implements Runnable {

        private Simulation sim;

        public Updater(Simulation sim) {
            this.sim = sim;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (Exception ex) {

                }
                sim.evolve(0.05);
            }
        }
    }

    // Define the thread class for handling new connection
    class HandleAClient implements Runnable, Constants {

        private Socket socket; // A connected socket
        private Simulation game;
        private int playerNumber;

        /**
         * Construct a thread
         */
        public HandleAClient(Simulation game, int player, Socket socket) {
            this.game = game;
            this.playerNumber = player;
            this.socket = socket;
        }

        /**
         * Run a thread
         */
        public void run() {
            try {
                // Create data input and output streams
                BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter outputToClient = new PrintWriter(socket.getOutputStream());

                // Continuously serve the client
                while (true) {
                    // Receive request code from the client
                    int request = Integer.parseInt(inputFromClient.readLine());
                    // Process request
                    switch (request) {
                        case MOVE_UP: {
                            game.moveBox(playerNumber, 0, -2);
                            break;
                        }
                        case MOVE_DOWN: {
                            game.moveBox(playerNumber, 0, 2);
                            break;
                        }
                           case MOVE_LEFT: {
                            game.moveBox(playerNumber, -2, 0);
                            break;
                        }
                        case MOVE_RIGHT: {
                            game.moveBox(playerNumber, 2, 0);
                            break;
                        }
                             case GET_GAME_STATE: {
                            outputToClient.println(game.getGameState());
                            outputToClient.flush();
                            break;
                        }
                    }
                }
            } catch (Exception ex) {

            }
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
