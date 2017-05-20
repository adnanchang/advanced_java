package de.fh.kiel.roborally.Games;

import java.util.ArrayList;
import java.util.List;

public class Games {
    private String id;
    private String name;
    private int maxRobotCount;
    private int currentRobotCount;
    private boolean hasStarted;

    public Games(String name, int maxRobotCount, int currentRobotCount, boolean hasStarted){
        this.name = name;
        this.maxRobotCount = maxRobotCount;
        this.currentRobotCount = currentRobotCount;
        this.hasStarted = hasStarted;
    }

    public Games(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrentRobotCount() {
        return currentRobotCount;
    }

    public void setCurrentRobotCount(int currentRobotCount) {
        this.currentRobotCount = currentRobotCount;
    }

    public int getMaxRobotCount() {
        return maxRobotCount;
    }

    public void setMaxRobotCount(int maxRobotCount) {
        this.maxRobotCount = maxRobotCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

}
