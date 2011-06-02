package rockbeatspaper.Azimuth;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Azimuth extends JavaPlugin 
{
    Logger log = null;
    private final CompassPlayerListener playerListener = new CompassPlayerListener( this );
    private final CompassEntityListener entityListener = new CompassEntityListener( this, playerListener );
    
    
	public void onEnable() 
	{
		log = Logger.getLogger("Minecraft");
		log.info("Your plugin Azimuth(Compass) has been enabled.");	
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent( Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this );
		pm.registerEvent( Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Normal, this );
		pm.registerEvent( Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this );
		
	}
    
    public void onDisable() 
    {
		// TODO Auto-generated method stub

	}
    
}