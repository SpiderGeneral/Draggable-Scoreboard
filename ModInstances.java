public class ModInstances{
  
  private static ModScoreboard modScoreboard;
  
  public static void register(HUDManager api) {
    
    modScoreboard = new ModScoreboard();
		api.register(modScoreboard);
  }
  
  public static ModScoreboard getModScoreboard() {
		return modScoreboard;
	}
}
