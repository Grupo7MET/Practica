package model;

/**
 * Created by Manel on 21/5/18.
 */

public class RemotePacket {

    private float temperature;
    private int danger;
    private String movement;
    private int velocity;
    private boolean lights;
    private String manual;

    public RemotePacket(float temperature, int danger, String movement, int velocity, boolean lights, String manual){
        this.temperature = temperature;
        this.danger = danger;
        this.movement = movement;
        this.velocity = velocity;
        this.lights = lights;
        this.manual = manual;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getDanger() {
        return danger;
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public boolean isLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }
}
