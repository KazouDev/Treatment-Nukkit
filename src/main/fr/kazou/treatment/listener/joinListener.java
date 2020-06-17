package fr.kazou.treatment.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import fr.kazou.treatment.Treatment;
import fr.kazou.treatment.TreatmentTask;

public class joinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Treatment.getInstance().getServer().getScheduler().scheduleRepeatingTask(new TreatmentTask(e.getPlayer()), 20);
    }
}
