package fr.kazou.treatment.event;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class TreatmentTransformEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Player player;

    public static HandlerList getHandlers(){return handlers;}

    public TreatmentTransformEvent(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
