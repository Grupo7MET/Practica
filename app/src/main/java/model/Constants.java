package model;

/**
 * Class that declares the constants
 * Authors: Cristina Abad, Manel Benavides, Miguel Martinez
 */

public class Constants {

    //Splash
    public static final int ANIMATION_DURATION = 1800;
    public static final int MIN_PROGRESS = 0;
    public static final int MAX_PROGRESS = 100;
    public static final int SPLASH_DURATION = 1000;

    //Main Menu
    public static final String PROTOCOL_SPLIT = "_";

    //Log Mode
    public static final String FILE_NAME = "logs.txt";

    //Remote Mode
    public static final int GEAR_INIT = 0;
    public static final int GEAR_MAX = 3;

    public static final int DANGER_BUMPER1 = 1;
    public static final int DANGER_BUMPER2 = 2;
    public static final int DANGER_US = 4;

    public static final int GYRO_MAX_FORWARD = 2;
    public static final int GYRO_MAX_SOFT = 6;
    public static final int GYRO_MAX_HARD = 10;

    public static final String PREDICTION_SQUARE = "Square";
    public static final String PREDICTION_CIRCLE = "Circle";
    public static final String PREDICTION_TRIANGLE = "Triangle";

    /**
     * All the constants used on the communication protocol to send/receive
     */
    public static final String PROTOCOL_LIGHTS_OFF = "0";
    public static final String PROTOCOL_LIGHTS_ON = "1";

    public static final String SENDING_PROTOCOL_MOVEMENT_FORWARD = "a";
    public static final String SENDING_PROTOCOL_MOVEMENT_SOFT_RIGHT = "b";
    public static final String SENDING_PROTOCOL_MOVEMENT_HARD_RIGHT = "c";
    public static final String SENDING_PROTOCOL_MOVEMENT_SOFT_LEFT = "d";
    public static final String SENDING_PROTOCOL_MOVEMENT_HARD_LEFT = "e";
    public static final String SENDING_PROTOCOL_MOVEMENT_SQUARE = "f";
    public static final String SENDING_PROTOCOL_MOVEMENT_CIRCLE = "g";
    public static final String SENDING_PROTOCOL_MOVEMENT_TRIANGLE = "h";
    public static final String SENDING_PROTOCOL_FRONT_LIGHTS = "i";
    public static final String SENDING_PROTOCOL_AUTOMATIC = "j";
    public static final String SENDING_PROTOCOL_MANUAL = "k";
    public static final String SENDING_PROTOCOL_REMOTE = "l";
    public static final String SENDING_PROTOCOL_ACCELEROMETER = "m";
    public static final String SENDING_PROTOCOL_LABYRINTH = "n";
    public static final String SENDING_PROTOCOL_REPLAY_LABYRINTH = "o";
    public static final String SENDING_PROTOCOL_BACK_TO_MENU = "q";
    public static final String SENDING_PROTOCOL_VELOCITY_MEDIUM = "2";
    public static final int SENDING_PROTOCOL_VELOCITY_MINUS1 = 4;
    public static final int SENDING_PROTOCOL_VELOCITY_MINUS2 = 5;
    public static final int SENDING_PROTOCOL_VELOCITY_MINUS3 = 6;

    public static final String PROTOCOL_LIGHTS_CHANGED = "lightschanged";
    public static final String PROTOCOL_MANUAL_CHANGED = "modechanged";


    public static final float PREDICTOR_MIN_SCORE = 1;

    public static final int[] VELOCITY = {0,40,80,120};

    //Labyrinth Mode

    public static final int LABYRINTH_NROWS = 5;
    public static final int LABYRINTH_NCOLUMNS = 5;
    public static final String PROTOCOL_STATE_VISITED = "visited";
    public static final String PROTOCOL_STATE_CURRENT = "current";
    public static final String PROTOCOL_STATE_SOLUTION = "solution";
    public static final String PROTOCOL_STATE_NOT_SOLUTION = "not solution";

    //Accelerometer Challenge

    //LOG
    public static final String LOG_TITLE = "LOG REGISTER" + '\n' + '\n';
    public static final int INPUT_BYTES = 5000;
    public static final String PROTOCOL_ANDROID = "Android";
    public static final String PROTOCOL_ARDUINO = "Arduino";
    public static final char PROTOCOL_SPACE = '\n';
    public static final String PROTOCOL_WHO_ANDROID = " Android says: ";
    public static final String PROTOCOL_WHO_ROBOT = " Robot says: ";

    public static final String PROTOCOL_REFRESH_ACCELEROMETER_LOGMESSAGE = "Acceleration refresh.";
    public static final String PROTOCOL_REFRESH_ACCELEROMETER_LOGMESSAGE_HELLO = "Entering in accelerometer mode";
    public static final String PROTOCOL_REFRESH_BACK_TO_MENU = "Back to menu";
    public static final String PROTOCOL_REFRESH_REMOTE_CIRCLE = "Making circle movement at gear";

    public static final String PROTOCOL_NORTH = "n";
    public static final String PROTOCOL_SOUTH = "s";
    public static final String PROTOCOL_EAST = "e";
    public static final String PROTOCOL_WEST = "w";
    public static final String PROTOCOL_FINISHED = "f";

    /**
     * IP and ports for the transmission
     */
    public static final String MOBIL_IP = "192.168.1.48";
    public static final String ARDUINO_IP = "192.168.1.46"; //PC
    public static final int MOBIL_PORT = 10502;
    public static final int ARDUINO_PORT = 10501; //PC

}
