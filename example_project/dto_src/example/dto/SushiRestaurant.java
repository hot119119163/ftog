package example.dto;

//Unrelated import
import example.dto.carparts.Wheel;

public class SushiRestaurant extends Restaurant {
	public SushiRestaurant(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	
	public Wheel aLittleHack() {
		Wheel sushiBeltWheel = new Wheel();
		return sushiBeltWheel;
	}
}
