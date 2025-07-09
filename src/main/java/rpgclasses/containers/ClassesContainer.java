package rpgclasses.containers;

import necesse.engine.GameLog;
import necesse.engine.localization.Localization;
import necesse.engine.network.NetworkClient;
import necesse.engine.network.packet.PacketOpenContainer;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.ContainerRegistry;
import necesse.inventory.container.Container;
import necesse.inventory.container.customAction.StringCustomAction;
import rpgclasses.RPG_Classes;
import rpgclasses.base.ClassAbility;
import rpgclasses.base.RPGClass;
import rpgclasses.data.PlayerData;
import rpgclasses.data.PlayerDataList;
import rpgclasses.packets.UpdateClientDataPacket;
import rpgclasses.registry.RPGClassRegistry;

public class ClassesContainer extends Container {
    public final StringCustomAction getAbility;

    public ClassesContainer(final NetworkClient client, int uniqueSeed) {
        super(client, uniqueSeed);
        this.getAbility = this.registerAction(
                new StringCustomAction() {
                    @Override
                    protected void run(String s) {
                        if (client.isServer()) {
                            String abilityStringID = s.split("_")[0];
                            int abilityLevel = Integer.parseInt(s.split("_")[1]);
                            String classStringID = s.split("_")[2];

                            ServerClient serverClient = client.getServerClient();

                            RPGClass rpgClass = RPGClassRegistry.getClass(classStringID);
                            ClassAbility classAbility = rpgClass.getAbility(abilityStringID, abilityLevel);
                            if (classAbility == null) {
                                GameLog.warn.println(serverClient.getName() + " tried to unlock a non-existing ability of " + rpgClass.getDisplayName());
                            } else {
                                String err = classAbility.canUnlock(serverClient.playerMob);
                                if (err != null) {
                                    serverClient.sendChatMessage(Localization.translate("classmessage", err));
                                } else {
                                    serverClient.sendChatMessage(Localization.translate("classmessage", "abilityunlocked", "ability", classAbility.ability.getDisplayName(), "level", abilityLevel + 1));
                                    PlayerData playerData = PlayerDataList.getCurrentPlayer(serverClient.playerMob);
                                    playerData.classAbilitiesStringIDs.add(classAbility.getStringID());
                                    if (client.getServerClient().checkHasRequestedSelf() && !client.getServerClient().isDead()) {
                                        client.getServerClient().checkSpawned();
                                        client.getServerClient().sendPacket(new UpdateClientDataPacket(client.playerMob.playerName, PlayerDataList.getCurrentPlayer(client.playerMob)));
                                        PacketOpenContainer p = new PacketOpenContainer(RPG_Classes.CLASSES_CONTAINER);
                                        playerData.updateActiveBuffs();
                                        ContainerRegistry.openAndSendContainer(client.getServerClient(), p);
                                        playerData.updateBuffs(serverClient.playerMob);
                                    }
                                }
                            }
                        }
                    }
                }
        );
    }
}
