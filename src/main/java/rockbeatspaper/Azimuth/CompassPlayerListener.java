package rockbeatspaper.Azimuth;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import rockbeatspaper.Azimuth.CompassContainer.CompassModes;

public class CompassPlayerListener extends PlayerListener 
{

	public static Azimuth plugin; //
	private HashMap<Player, CompassContainer> playersAndPrefs;

	
	/**
	 * Public constructor that initializes the reference 
	 * @param instance - needed reference back to plugin.
	 */
	public CompassPlayerListener( Azimuth instance ) 
	{
		plugin = instance;
		playersAndPrefs = new HashMap<Player, CompassContainer>();
	}
	
	/**
	 * Removes mode from rotation of player.
	 * @param player
	 * @param mode - string for which mode to remove.
	 * @return true when successful. False if not.
	 */
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
			player.sendMessage(ChatColor.RED + "This compass mode does not exist.");
			return false;
		}
	}
	
	/**
	 * Adds mode to rotation of player.
	 * @param player
	 * @param mode - string for which mode to add.
	 * @return true when successful. False if not.
	 */
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
		else
		{
			player.sendMessage(ChatColor.RED + "This compass mode does not exist.");
			return false;
		}
	}
	
	/**
	 * Catches when player joins the server and creates a player.
	 */
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		createPlayer( event.getPlayer() );
	}
	
	/**
	 * Creates new container, sets its defaults and adds to hashmap of all players
	 * @param player
	 * @return false if player already exists.
	 */
	public boolean createPlayer(Player player)
	{
		//create new player and add to container
		CompassContainer container = playersAndPrefs.get( player );
		if ( container == null ) 
		{
			// create new container with values, insert into dictionary
			container = new CompassContainer( player.getWorld().getSpawnLocation(), player );
			container.setSpawnLocation( player.getCompassTarget() );
			//container.setMode( CompassModes.LAST_DEATH );
			container.setSetPersonalSpawn( true );
			playersAndPrefs.put( player, container );
			return true;
		}
		else
		{
			return false; //already is a player
		}
	}
	
	
	/**
	 * Catches when a player interacts with the world.
	 * If all of the conditions are right, mode will get rotated on the compass and the player will be informed.
	 * 	The conditions are: a right click was executed, the player is holding a compass.
	 */
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
			
			//check if player can't rotate(i.e. has removed all from rotation)
			if( container.emptyRotation() )
			{
				player.sendMessage(ChatColor.RED + "Cannot change modes because all modes are removed.");
				return;
			}
			
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

	/**
	 * Stores where the death location is for specific player.
	 * @param death - (x,y,z) location of death of the player
	 * @param newPlayer - player death location is for.
	 */
	public void setDeathLocation( Location death, Player newPlayer ) 
	{
		this.createPlayer(newPlayer);
		CompassContainer container = playersAndPrefs.get(newPlayer);
		container.setDeathLocation( death );
	}

	/**
	 * Watches for player respawn event, notes location of spawn and call setSpawnLocation(...)
	 */
	public void onPlayerRespawn( PlayerRespawnEvent event ) 
	{
		// declare and initialize player object
		Player newPlayer = event.getPlayer();
		this.createPlayer(newPlayer);
		CompassContainer container = playersAndPrefs.get( newPlayer );
		container.setSpawnLocation( event.getRespawnLocation() );
	}
	
	public void onPlayerBedEnter( PlayerBedEnterEvent event)
	{
		/*
		if(! event.isCancelled() ) //if the player does not cancel the event
		{
			event.getPlayer().sendMessage("Did not cancel bed.");
		}
		else
		{
			event.getPlayer().sendMessage("Cancelled bed.");
		}
		
		if( event.getPlayer().isSleeping() ) //if the player does not cancel the event
		{
			event.getPlayer().sendMessage("Sleeping");
		}
		else
		{
			event.getPlayer().sendMessage("Is not sleeping.");
		}
		*/
		
	}
	
	public void onPlayerBedLeave( PlayerBedLeaveEvent event)
	{
		/*
		if( event.getPlayer().isSleeping() ) //if the player does not cancel the event
		{
			event.getPlayer().sendMessage("Sleeping");
		}
		else
		{
			event.getPlayer().sendMessage("Is not sleeping.");
		}
		*/
	}

	/**
	 * Makes compass point to last death location and notifies player of being in last death compass mode.
	 * @param player - player whose compass is changing modes.
	 */
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

	/**
	 * Makes compass point to world spawn location and notifies player of being in world spawn compass mode.
	 * @param player - player whose compass is changing modes.
	 */
	private void setWorldSpawnPointMode( Player player ) 
	{
		//try to get value to player key in hashmap
		CompassContainer container = playersAndPrefs.get( player );
		
		//record moving to a new mode, then notify player
		container.setMode( CompassContainer.CompassModes.WORLD_SPAWN_POINT );
		player.sendMessage( "Compass set to: world spawn." );
		
		player.setCompassTarget( container.getWorldSpawnLocation() );
	}

	/**
	 * Makes compass point to personal spawn location and notifies player of being in personal spawn compass mode.
	 * @param player - player whose compass is changing modes.
	 */
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