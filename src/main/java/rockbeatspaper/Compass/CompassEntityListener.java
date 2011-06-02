package rockbeatspaper.Compass;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import rockbeatspaper.Compass.CompassPlayerListener;

public class CompassEntityListener extends EntityListener{
	
	public static Compass plugin;
	public static CompassPlayerListener playerListener;
	
	public CompassEntityListener(Compass instance, CompassPlayerListener playerInstance) 
	{ 	
        plugin = instance;
        playerListener = playerInstance;
	}
	
	public void onEntityDeath(EntityDeathEvent event) 
	{
		if( event.getEntity() instanceof Player )
		{
			Player player = (Player) event.getEntity();
			player.sendMessage("Haaahaaa. You died.");
			playerListener.setDeathLocation(player.getLocation());
		}
		else
		{
			return;
		}
	}
}
