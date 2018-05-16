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
    public static final String MODE_MENU = "MENU";
    public static final String PROTOCOL_SPLIT = "_";

    //Remote Mode
    public static final String MODE_REMOTE = "REMOTE";

    public static final int GEAR_INIT = 0;
    public static final int GEAR_MAX = 3;
    public static final String GEAR_CHANGE = "change gear";

    public static final int GYRO_MAX_FORWARD = 2;
    public static final int GYRO_MAX_SOFT = 6;
    public static final int GYRO_MAX_HARD = 10;

    public static final String PROTOCOL_LIGHTS_OFF = "lights off";
    public static final String PROTOCOL_LIGHTS_ON = "lights on";

    public static final String PROTOCOL_MOVEMENT_FORWARD = "forward";
    public static final String PROTOCOL_MOVEMENT_SOFT_LEFT = "soft left";
    public static final String PROTOCOL_MOVEMENT_HARD_LEFT = "hard left";
    public static final String PROTOCOL_MOVEMENT_SOFT_RIGHT = "soft right";
    public static final String PROTOCOL_MOVEMENT_HARD_RIGHT = "hard right";

    public static final float PREDICTOR_MIN_SCORE = 1;

    public static final int[] VELOCITY = {0,50,100};

    public static final String PROTOCOL_BRAKE = "brake";
    public static final String PROTOCOL_THROTTLE = "throttle";
    public static final String PROTOCOL_AUTOMATIC = "automatic";
    public static final String PROTOCOL_MANUAL = "manual";
    public static final String PROTOCOL_REMOTE = "rem";
    public static final String PROTOCOL_REMOTE_TEMPERATURE = "temperature";
    public static final String PROTOCOL_REMOTE_DANGER = "danger";

    //Labyrinth Mode
    public static final String MODE_LABYRINTH = "LABYRINTH";

    public static final int LABYRINTH_NROWS = 5;
    public static final int LABYRINTH_NCOLUMNS = 5;

    //Accelerometer Challenge
    public static final String MODE_ACCELEROMETER = "ACCELEROMETER";
    public static final String PROTOCOL_ACCELEROMETER = "acc";
    public static final String PROTOCOL_ACCELEROMETER_X = "x";
    public static final String PROTOCOL_ACCELEROMETER_Y = "y";
    public static final String PROTOCOL_ACCELEROMETER_Z = "z";

    //LOG
    public static final String LOG_TITLE = "LOG REGISTER" + '\n' + '\n';
    public static final int INPUT_BYTES = 5000;
    public static final String MOBIL_IP = "192.168.1.48";
    public static final String ARDUINO_IP = "192.168.1.139"; //PC
    public static final int MOBIL_PORT = 10502;
    public static final int ARDUINO_PORT = 10501; //PC

}
