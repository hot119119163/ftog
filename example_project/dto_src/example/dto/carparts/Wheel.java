package example.dto.carparts;

public class Wheel {
	
	public static final int STEEL_MATERIAL=1;
	public static final int TITANIUM_MATERIAL=2;
	public static final int RUBBER_MATERIAL=4;
	
	public int material=STEEL_MATERIAL|RUBBER_MATERIAL;
}
