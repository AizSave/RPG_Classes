package rpgclasses.data;


import necesse.entity.mobs.PlayerMob;

import java.util.ArrayList;

public class PlayerDataList {
    public static ArrayList<PlayerData> players = new ArrayList<>();

    public static PlayerData getCurrentPlayer(PlayerMob player) {
        return getCurrentPlayer(player.playerName);
    }

    public static PlayerData getCurrentPlayer(String playerName) {
        PlayerData playerData = players.stream().filter(p -> p.playerName.equals(playerName)).findFirst().orElse(null);
        if (playerData == null) {
            playerData = initPlayer(playerName);
        }
        return playerData;
    }

    public static PlayerData initPlayer(PlayerMob player) {
        return initPlayer(player.playerName);
    }

    public static PlayerData initPlayer(String playerName) {
        PlayerData playerData = new PlayerData(playerName);
        players.add(playerData);
        return playerData;
    }

}
