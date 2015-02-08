package engine;

public class EngineConfigurationIsBad extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EngineConfigurationIsBad(){
		super("The engine is not well configured");
	}
}
