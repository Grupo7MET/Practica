package model;

/**
 * Created by Manel on 21/5/18.
 */

public class RemotePacket {

    private String temperature;
    private String danger;
    private String movement;
    private String velocity;
    private String lights;
    private String manual;

    public RemotePacket(String temperature, String danger, String movement, String velocity, String lights, String manual){
        this.temperature = temperature;
        this.danger = danger;
        this.movement = movement;
        this.velocity = velocity;
        this.lights = lights;
        this.manual = manual;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public String getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    public String getLights() {
        return lights;
    }

    public void setLights(String lights) {
        this.lights = lights;
    }

    public boolean getManual() {
        if(this.manual.equals("MANUAL")){
            return true;
        }else{
            return false;
        }
    }

    public void setManual(String manual) {
        this.manual = manual;
    }
}
