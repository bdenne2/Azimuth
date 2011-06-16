package rockbeatspaper.Azimuth;

import java.util.ArrayList;
import org.bukkit.Location;

public class CompassContainer 
{
	private Location deathLocation;
	private Location spawnLocation;
	public enum CompassModes { LAST_DEATH, PERSONAL_SPAWN, WORLD_SPAWN_POINT}
	private int mode;
	private ArrayList<CompassModes> playerModes;
	private boolean setPersonalSpawn;
	private Location worldSpawnLocation;
	
	public CompassContainer(Location newWorldSpawnLocation)
	{
		worldSpawnLocation = newWorldSpawnLocation;
		
		//default values for the rest
		deathLocation = null;
		spawnLocation = null;
		mode = 0;
		setPersonalSpawn = true;
		
		//create linked list with three default values
		playerModes = new ArrayList<CompassModes>();
		playerModes.add(CompassModes.WORLD_SPAWN_POINT);
		playerModes.add(CompassModes.PERSONAL_SPAWN);
		playerModes.add(CompassModes.LAST_DEATH);
	}
	
	public boolean removeMode(CompassModes mode)
	{
		if( playerModes.contains(mode) )
		{
			return playerModes.remove(mode);
		}
		return false;
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
