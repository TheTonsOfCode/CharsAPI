package pl.merbio.utilities;

import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.mcstats.MetricsLite;
import pl.merbio.Main;

/**
 * @author Merbio
 */

public class Metrics {
    
    MetricsLite metrics;
    
    public Metrics(Main main){
        try {
            metrics = new MetricsLite(main);
            metrics.start();
        } catch (IOException ex) {}
    }
    
    public boolean isDebug(){
        return YamlConfiguration.loadConfiguration(metrics.getConfigFile()).getBoolean("debug");
    }
    
    public boolean isOptOut(){
        return metrics.isOptOut();
    }
    
    public String getGuid(){
        return YamlConfiguration.loadConfiguration(metrics.getConfigFile()).getString("guid");
    }
}
