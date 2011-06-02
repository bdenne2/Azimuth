package rockbeatspaper.Compass;

import org.bukkit.Location;

public class CompassContainer 
{
	private Location deathLocation;
	private Location spawnLocation;
	public enum CompassModes { LAST_DEATH, PERSONAL_SPAWN, WORLD_SPAWN_POINT}
	private CompassModes mode;
	private boolean setPersonalSpawn;
	private Location worldSpawnLocation;
	
	/*CompassContainer(Location newDeathLocation, Location newSpawnLocation, Location newWorldSpawnLocation, CompassModes newMode, boolean newSetPersonalSpawn)
	{
		deathLocation = newDeathLocation;
		spawnLocation = newSpawnLocation;
		worldSpawnLocation = newWorldSpawnLocation;
		mode = newMode;
		setPersonalSpawn = newSetPersonalSpawn;
	}
	*/
	
	CompassContainer(Location newWorldSpawnLocation)
	{
		worldSpawnLocation = newWorldSpawnLocation;
		
		//default values for the rest
		deathLocation = null;
		spawnLocation = null;
		mode = CompassModes.WORLD_SPAWN_POINT;
		setPersonalSpawn = true;
		
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
		return mode;
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
	
	public void setMode(CompassModes newMode)
	{
		mode = newMode;
	}
	
	public void setSetPersonalSpawn(boolean newValue)
	{
		setPersonalSpawn = newValue;
	}
}
