package de.fh.kiel.roborally.Games;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Queue;

public class Player {
    private final String id;
    private final String name;
    private final String clientUri;
    private final String robotName;
    private final String secret;
    private int positionX;
    private int positionY;

    /**
     * Creates a new Player.
     *
     * @param id        the Players ID, they can choose it themselves
     * @param name      the name a Player has given themselves
     * @param secret    the secret provided by the game server in order to verify the identity of this player; for
     *                  security reasons only a hash should be provided
     * @param clientUri the URI where the player can be reached via http; for example http://localhost:8081/
     * @param robotName the name of the Robot, for example Smash Bot
     */
    public Player(final String id, final String name, final String secret, final String clientUri, final String robotName) {
        this.id = id;
        this.name = name;
        this.secret = secret;
        this.clientUri = clientUri;
        this.robotName = robotName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClientUri() {
        return clientUri;
    }

    public String getRobotName() {
        return robotName;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Player)) {
            return false;
        }

        Player player = (Player) o;

        return new EqualsBuilder().append(id, player.id).append(name, player.name).append(clientUri, player.clientUri).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(clientUri).toHashCode();
    }
}
