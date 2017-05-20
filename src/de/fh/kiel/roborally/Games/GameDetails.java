package de.fh.kiel.roborally.Games;

public class GameDetails {
    private String id;
    private String playerName;
    private String robotName;
    private String clientBaseURL;
    private Games game;

    public GameDetails(String playerName, String robotName, String clientBaseURL, Games game) {
        this.playerName = playerName;
        this.robotName = robotName;
        this.clientBaseURL = clientBaseURL;
        this.game = game;
    }

    public GameDetails(){

    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*
    Here you define the relation
     */
    public Games getGame() {
        return game;
    }

    public void setGame(Games game) {
        this.game = game;
    }

    public String getClientBaseURL() {
        return clientBaseURL;
    }

    public void setClientBaseURL(String clientBaseURL) {
        this.clientBaseURL = clientBaseURL;
    }
}
