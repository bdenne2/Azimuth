package rockbeatspaper.Compass;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import rockbeatspaper.Compass.CompassPlayerListener;

public class CompassEntityListener extends EntityListener
{
	
	public static Compass plugin;
	public static CompassPlayerListener playerListener;
	
	
	//using access to instance of playerListener to setDeathLocation
	public CompassEntityListener( Compass instance, CompassPlayerListener playerInstance ) 
	{ 	
        plugin = instance;
        playerListener = playerInstance;
	}
	
	public void onEntityDeath(EntityDeathEvent event) 
	{
		if( event.getEntity() instanceof Player )
		{
			Player player = (Player) event.getEntity();
			playerListener.setDeathLocation(player.getLocation(), player);
		}
		else //pay no heed to other entity types
		{
			return;
		}
	}
}
