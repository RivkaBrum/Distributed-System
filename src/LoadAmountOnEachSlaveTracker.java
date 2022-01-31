public class LoadAmountOnEachSlaveTracker	 {
	//shared memory that keep track of diff threads
	private int currentLoadOnSlaveA = 0;
	private int currentLoadOnSlaveB = 0;

	
	public int getCurrentLoadOnSlaveA() {
		return currentLoadOnSlaveA;
	}
	public int getCurrentLoadOnSlaveB() {
		return currentLoadOnSlaveB;
	}
	
	public void addToSlaveALoad(int i) {
		currentLoadOnSlaveA +=i; 
	}
	public void addToSlaveBLoad(int i) {
		currentLoadOnSlaveB +=i; 
	}
	public void removeFromSlaveALoad(int i) {
		currentLoadOnSlaveA -=i; 

	}
	public void removeFromSlaveBLoad(int i) {
		currentLoadOnSlaveB -=i; 

	}
	
	
}
