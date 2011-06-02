package rockbeatspaper.Compass;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class CompassPlayerListener extends PlayerListener{
	
	public static Compass plugin; 
	public enum CompassModes { LAST_DEATH, PERSONAL_SPAWN, WORLD_SPAWN_POINT}
	private CompassModes mode;
	private Location personalSpawn;
	private Location deathLocation;
	private boolean setPersonalSpawn;
	
	public CompassPlayerListener(Compass instance) 
	{ 	 
        plugin = instance;
        mode = CompassModes.PERSONAL_SPAWN;
        setPersonalSpawn = true;
        deathLocation = null;
	}

//	public void onPlayerChat(PlayerChatEvent thisEvent )
//	{
//		thisEvent.getPlayer().sendMessage("No");
//	}
	
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		//declare and initialize player object
		Player player = null;
		player = event.getPlayer();
		
		//player.getRespawnLocation();
		
		//checks if the event is a right click event, on air or block
		//if not, returns
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
		{
			return; 
		}
		
		//they are right clicking holding nothing, ergo exit
		if(event.getItem() == null)
		{
			return;
		}
		
		//if the item used is compass(entity id 345)
		if( event.getItem().getTypeId() == 345 ) 
		{
			
			if( setPersonalSpawn)
			{
				personalSpawn = player.getCompassTarget();
			}
			 
			switch( mode)
			{
			case PERSONAL_SPAWN:
				this.setLastDeathMode(player);
				break;
			case LAST_DEATH:
				this.setWorldSpawnPointMode(player, event);
				break;
			case WORLD_SPAWN_POINT:
				this.setPersonalSpawnMode(player);
				break;
			}
		}
		
	}
	
	public void setDeathLocation(Location death)
	{
		deathLocation = death;
	}
	
	@Override
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		personalSpawn = event.getRespawnLocation();
		setPersonalSpawn = false;
		//deathLocation = event.getPlayer().getLocation();
		event.getPlayer().sendMessage("You respawned");	
	}

	private void setLastDeathMode(Player player) 
	{
		mode = CompassModes.LAST_DEATH;
		player.sendMessage("Compass set to: last death.");
		//deathLocation = new Location( player.getWorld() , -9, 74, 48);
		//player.setCompassTarget(deathLocation);
		if( deathLocation != null)
		{
			player.setCompassTarget(deathLocation);
		}
		else
		{
			player.sendMessage(ChatColor.RED + "You haven't died yet. Pointing to personal spawn.");
			player.setCompassTarget(personalSpawn);
		}
		
	}

	private void setWorldSpawnPointMode(Player player, PlayerInteractEvent event) 
	{
		mode = CompassModes.WORLD_SPAWN_POINT;
		player.sendMessage("Compass set to: world spawn.");
		Location spawn_location = player.getWorld().getSpawnLocation();
		player.setCompassTarget(spawn_location);
	}

	private void setPersonalSpawnMode(Player player)
	{
		mode = CompassModes.PERSONAL_SPAWN;
		player.sendMessage("Compass set to: personal spawn.");
		player.setCompassTarget(personalSpawn);
	}
	
}