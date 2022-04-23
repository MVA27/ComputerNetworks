import java.util.function.*;
import java.util.*;

class Station extends Thread{	
	
	String name = null;

	Station(String name){
		this.name = name;
	}
	
	public void run(){
		Main.sleep.accept(new Random().nextInt(10000)); // Send data after some random time 
		System.out.println("Station "+name+" started");

		while(Main.channel == false){ // if channel is not free, wait for fixed amount of time 
			System.out.println("Collision Detected...!");
			System.out.println("Station "+name+" waiting for channel");
			Main.sleep.accept(1000);
		}
		
		if(Main.channel == true){ // if channel is available
			System.out.println("Channel is Available for "+name);
			
			Main.channel = false;
			int time = new Random().nextInt(10000); // Hold the channel for random amount of time 
			System.out.println("Channel taken by "+name+" for "+time+" ms");
			Main.sleep.accept(time);

			Main.channel = true; // Release the channel 
			System.out.println("Channel is Now Free");
		}
	}
}

class Main {
	
	static boolean channel = true;
	static Consumer<Integer> sleep = time -> {
		try{
			Thread.sleep(time);
		}
		catch(Exception e){
			System.out.println(e);
		}
	};
	
  public static void main(String[] args) {
    new Station("A").start();
	new Station("B").start();
	new Station("C").start();
  }
}