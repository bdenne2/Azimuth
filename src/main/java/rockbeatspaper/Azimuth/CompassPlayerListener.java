package rockbeatspaper.Azimuth;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;

import rockbeatspaper.Azimuth.CompassContainer.CompassModes;

public class CompassPlayerListener extends PlayerListener 
{

	public static Azimuth plugin;
	private HashMap<Player, CompassContainer> playersAndPrefs;

	public CompassPlayerListener( Azimuth instance ) 
	{
		plugin = instance;
		playersAndPrefs = new HashMap<Player, CompassContainer>();
	}
	
	public boolean removeModeFromPlayer(Player player, String mode)
	{
		CompassContainer container = playersAndPrefs.get( player );
		if( mode.equals("worldspawn") )
		{
			return container.removeMode( CompassModes.WORLD_SPAWN_POINT );
		}
		else if( mode.equals("personalspawn") )
		{
			return container.removeMode( CompassModes.PERSONAL_SPAWN );
		}
		else if( mode.equals("lastdeath") )
		{
			return container.removeMode( CompassModes.LAST_DEATH );
		}
		else //this mode does not exist 
		{
			return false;
		}
	}
	
	public boolean addModeToPlayer(Player player, String mode)
	{
		CompassContainer container = playersAndPrefs.get( player );
		if( mode.equals("worldspawn") )
		{
			return container.addMode( CompassModes.WORLD_SPAWN_POINT );
		}
		else if( mode.equals("personalspawn") )
		{
			return container.addMode( CompassModes.PERSONAL_SPAWN );
		}
		else if( mode.equals("lastdeath") )
		{
			return container.addMode( CompassModes.LAST_DEATH );
		}
		
		return false;
	}
	
	private void createPlayer(Player player)
	{
		//create new player and add to container
		CompassContainer container = playersAndPrefs.get( player );
		if ( container == null ) 
		{
			// create new container with values, insert into dictionary
			container = new CompassContainer( player.getWorld().getSpawnLocation() );
			container.setSpawnLocation( player.getCompassTarget() );
			//container.setMode( CompassModes.LAST_DEATH );
			container.setSetPersonalSpawn( true );
			playersAndPrefs.put( player, container );
		}
	}
	
	
		
	public void onPlayerInteract( PlayerInteractEvent event ) 
	{
		// declare and initialize player object
		Player player = null;
		player = event.getPlayer();

		// checks if the event is a right click event, on air or block
		// if not, returns
		if ( event.getAction() != Action.RIGHT_CLICK_AIR &&
		     event.getAction() != Action.RIGHT_CLICK_BLOCK ) 
		{
			return;
		}

		// they are right clicking holding nothing, ergo exit
		if ( event.getItem() == null ) 
		{
			return;
		}
		
		// if the item used is compass(entity id 345)
		if ( event.getItem().getTypeId() == 345 ) 
		{
			createPlayer(player);
			CompassContainer container = playersAndPrefs.get( player );
			
			//logic for circling what compass is pointing to
			CompassModes mode = container.nextMode();
			switch ( mode ) 
			{
				case PERSONAL_SPAWN:
					this.setPersonalSpawnMode( player );
					break;
				case LAST_DEATH:
					this.setLastDeathMode( player );
					break;
				case WORLD_SPAWN_POINT:
					this.setWorldSpawnPointMode( player );
					break;
			}
		}

	}

	public void setDeathLocation( Location death, Player newPlayer ) 
	{
		//try to get value to player key in hashmap
		CompassContainer container = playersAndPrefs.get( newPlayer );
		if (container == null) // I haven't seen them yet
		{
			//create new container instance, populate with known values, insert into dictionary
			container = new CompassContainer( newPlayer.getWorld().getSpawnLocation() );
			container.setDeathLocation( death );
			container.setSpawnLocation( newPlayer.getCompassTarget() );
			container.setMode( CompassModes.LAST_DEATH );
			
			playersAndPrefs.put( newPlayer, container );
			setWorldSpawnPointMode(newPlayer);
		} 
		else // I have seen them, just set the location
		{
			container.setDeathLocation( death );
		}
	}

	public void onPlayerRespawn( PlayerRespawnEvent event ) 
	{
		// declare and initialize player object
		Player newPlayer = event.getPlayer();
		//try to get value to player key in hashmap
		CompassContainer container = playersAndPrefs.get( newPlayer );
		if ( container == null ) // I haven't seen them yet
		{
			//create new container instance, populate with known values, insert into dictionary
			container = new CompassContainer( newPlayer.getWorld().getSpawnLocation() );
			container.setSpawnLocation( event.getRespawnLocation() );
			container.setMode( CompassModes.LAST_DEATH );
			container.setSetPersonalSpawn( false );
			
			playersAndPrefs.put( newPlayer, container );
			setWorldSpawnPointMode( newPlayer );
		}
		else //I have seen them, just set the new location
		{
			container.setSpawnLocation( event.getRespawnLocation() );
		}
	}

	private void setLastDeathMode( Player player ) 
	{
		//try to get value to player key in hashmap
		CompassContainer container = playersAndPrefs.get( player );
		
		//record moving to a new mode, then notify player
		container.setMode( CompassContainer.CompassModes.LAST_DEATH );
		player.sendMessage( "Compass set to: last death." );
		
		//don't know death location, player needs to die first
		if ( container.getDeathLocation() == null )  
		{
			player.sendMessage( ChatColor.RED + "You haven't died yet. Pointing to personal spawn." );
			player.setCompassTarget( container.getSpawnLocation() );	
		} 
		else
		{
			player.setCompassTarget( container.getDeathLocation() );
		}

	}

	private void setWorldSpawnPointMode( Player player ) 
	{
		//try to get value to player key in hashmap
		CompassContainer container = playersAndPrefs.get( player );
		
		//record moving to a new mode, then notify player
		container.setMode( CompassContainer.CompassModes.WORLD_SPAWN_POINT );
		player.sendMessage( "Compass set to: world spawn." );
		
		player.setCompassTarget( container.getWorldSpawnLocation() );
	}

	private void setPersonalSpawnMode( Player player ) 
	{
		//try to get value to player key in hashmap
		CompassContainer container = playersAndPrefs.get( player );
		
		//record moving to a new mode, then notify player
		container.setMode( CompassContainer.CompassModes.PERSONAL_SPAWN );
		player.sendMessage( "Compass set to: personal spawn." );
		
		player.setCompassTarget( container.getSpawnLocation() );
	}

}