package rockbeatspaper.Azimuth;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class CompassContainer 
{
	private Location deathLocation;
	private Location spawnLocation;
	public enum CompassModes { LAST_DEATH, PERSONAL_SPAWN, WORLD_SPAWN_POINT}
	private int mode;
	private ArrayList<CompassModes> playerModes;
	private boolean setPersonalSpawn;
	private Location worldSpawnLocation;
	private Player myPlayer;
	
	public CompassContainer(Location newWorldSpawnLocation, Player newPlayer)
	{
		worldSpawnLocation = newWorldSpawnLocation;
		
		//default values for the rest
		deathLocation = null;
		spawnLocation = null;
		mode = 0;
		setPersonalSpawn = true;
		myPlayer = newPlayer;
		
		//create arraylist with three default values
		playerModes = new ArrayList<CompassModes>();
		playerModes.add(CompassModes.WORLD_SPAWN_POINT);
		playerModes.add(CompassModes.PERSONAL_SPAWN);
		playerModes.add(CompassModes.LAST_DEATH);
	}
	
	public boolean removeMode(CompassModes modeToRemove)
	{
		if( playerModes.contains(modeToRemove) )
		{
			mode = 0;
			return playerModes.remove(modeToRemove);
		}
		else
		{
			myPlayer.sendMessage(ChatColor.RED + "This compass mode is already removed from your rotation.");
			return false;
		}
		
	}
	
	public boolean emptyRotation()
	{
		return playerModes.isEmpty();
	}
	
	public boolean addMode(CompassModes modeToAdd)
	{
		if( !(playerModes.contains(modeToAdd))  )
		{
			mode = 0;
			return playerModes.add(modeToAdd);
		}
		else
		{
			myPlayer.sendMessage(ChatColor.RED + "This compass mode is already in your rotation.");
			return false;
		}
	}
	
	public CompassModes nextMode()
	{
		if( (mode+1) == playerModes.size() )
		{
			return playerModes.get(0);
		}
		
		return playerModes.get(mode+1);
	}
	
	public Location getWorldSpawnLocation()
	{
		return worldSpawnLocation;
	}
	
	public Location getDeathLocation()
	{
		return deathLocation;
	}
	
	public Location getSpawnLocation()
	{
		return spawnLocation;
	}
	
	public CompassModes getMode()
	{
		return playerModes.get(mode);
	}
	
	public boolean getSetPersonalSpawn()
	{
		return setPersonalSpawn;
	}
	
	public void setWorldSpawnLocation(Location newWorldLocation)
	{
		worldSpawnLocation = newWorldLocation;
	}
	
	public void setDeathLocation(Location newDeathLocation)
	{
		deathLocation = newDeathLocation;
	}
	
	public void setSpawnLocation(Location newSpawnLocation)
	{
		spawnLocation = newSpawnLocation;
	}
	
	public boolean setMode(CompassModes newMode)
	{
		if( playerModes.contains(newMode) )
		{
			mode = playerModes.indexOf(newMode);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void setSetPersonalSpawn(boolean newValue)
	{
		setPersonalSpawn = newValue;
	}
}
