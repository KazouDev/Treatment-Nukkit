package fr.kazou.treatment;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import fr.kazou.treatment.commands.treatmentCommand;
import fr.kazou.treatment.listener.joinListener;

import java.util.HashMap;
import java.util.Map;

public class Treatment extends PluginBase {
    Config config;
    public static String prefix = "§c[§6TREATMENT§c]§f";
    public String[] itema;
    public String[] itemb;
    public boolean msg_no_item;
    public int time;
    public static Treatment instance;

    public Map<String, Object> language = new HashMap<>();

    private double minX;
    private double maxX;
    private double minZ;
    private double maxZ;
    private String world;

    @Override
    public void onLoad() {
        if(getDataFolder().exists()){

        } else{
            saveDefaultConfig();
        }
    }

    @Override
    public void onEnable() {
        config = new Config(getDataFolder() + "/config.yml", Config.YAML);
        instance = this;

        if(config.getInt("time") == 0){
            config.set("time", 5);
        }
        if(config.get("item_a") instanceof Integer){
            config.set("item_a", config.getInt("item_a")+":0:1");
        }
        if(config.get("item_b") instanceof Integer){
            config.set("item_b", config.getInt("item_b")+":0:1");
        }
        config.save();

        prefix = config.getString("prefix");
        language = config.getSection("messages").getAllMap();
        msg_no_item = getConfig().getBoolean("msg_no_item");

        getLogger().info(prefix + " §aSuccefully started ! By Kazou.");

        setupConfig();

        getServer().getPluginManager().registerEvents(new joinListener(), this);
        getServer().getCommandMap().register("treatment", new treatmentCommand());
    }

    @Override
    public void onDisable() {
    }

    public void setupConfig(){
        String[] p1 = getConfig().getSection("Area").getString("p1").split(":");
        String[] p2 = getConfig().getSection("Area").getString("p2").split(":");

        prefix = config.getString("prefix");
        itema = getConfig().getString("item_a").split(":");
        itemb = getConfig().getString("item_b").split(":");
        time = getConfig().getInt("time");

        minX = Math.min(Integer.parseInt(p1[0]), Integer.parseInt(p2[0]));
        maxX = Math.max(Integer.parseInt(p1[0]), Integer.parseInt(p2[0]));
        minZ = Math.min(Integer.parseInt(p1[1]), Integer.parseInt(p2[1]));
        maxZ = Math.max(Integer.parseInt(p1[1]), Integer.parseInt(p2[1]));
        world = p1[2];
    }

    public boolean isTreatment(Player p){
        if(p.getLevel().getName().equalsIgnoreCase(world)) {
            if (p.getLocation().x > minX && p.getLocation().x < maxX) {
                if (p.getLocation().z > minZ && p.getLocation().z < maxZ) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getLanguage(String path){
        return ((String) language.get(path)).replace("&", "§");
    }

    public static Treatment getInstance(){
        return instance;
    }

}
