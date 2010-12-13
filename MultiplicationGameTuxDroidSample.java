
import com.tuxisalive.direct.TuxDroid;
import java.io.InputStreamReader;
import java.io.BufferedReader;
public class MultiplicationGameTuxDroidSample{
	static int counter_left = 0;
	static int counter_right = 0;

	static int a = (int) Math.ceil(Math.random()*10);
	static int b = (int) Math.ceil(Math.random()*10);

	public static void init_game(){
		counter_left = 0;
		counter_right = 0;
		a = (int) Math.ceil(Math.random()*10);
		b = (int) Math.ceil(Math.random()*10);
	}
	
	public static void increment_left(){
		counter_left = (counter_left+1)%10;
	}

	public static void increment_right(){
		counter_right = (counter_right+1)%10;
	}

	public static int sum(){
		return counter_right*10 + counter_left;
	}

	public static void main(String[] args){


		try{
		TuxDroid tuxdroid = new TuxDroid(){
				// I'm sure there is a way to retrieve the double value at the end of the string
				public void on_event(String msg){
					if(msg.matches("right_wing_button:bool:True:.*")) {
						MultiplicationGameTuxDroidSample.increment_right();
						System.out.println("Is it true : "+MultiplicationGameTuxDroidSample.a+" x "+MultiplicationGameTuxDroidSample.b+" = "+MultiplicationGameTuxDroidSample.sum()+" ? ");
					}
					if(msg.matches("left_wing_button:bool:True:.*")) {
						MultiplicationGameTuxDroidSample.increment_left();
						System.out.println("Is it true : "+MultiplicationGameTuxDroidSample.a+" x "+MultiplicationGameTuxDroidSample.b+" = "+MultiplicationGameTuxDroidSample.sum()+" ? ");
					}
					if(msg.matches("head_button:bool:True:.*")) {
						if(MultiplicationGameTuxDroidSample.a*MultiplicationGameTuxDroidSample.b==MultiplicationGameTuxDroidSample.sum()){
							open_mouth();
							close_mouth();
							System.out.println("Yes, it's true !");
							init_game();
							System.out.println("Is it true : "+MultiplicationGameTuxDroidSample.a+" x "+MultiplicationGameTuxDroidSample.b+" = "+MultiplicationGameTuxDroidSample.sum()+" ? ");
							
						}
						else{
							close_eyes();
							open_mouth();
							close_mouth();
							open_eyes();
							System.out.println("It's false, "+MultiplicationGameTuxDroidSample.a+" x "+MultiplicationGameTuxDroidSample.b+" = "+MultiplicationGameTuxDroidSample.a*MultiplicationGameTuxDroidSample.b+" !");
						}
					}
				}
		};
		/*light configuration to get debug logs*/
		tuxdroid.set_log_level(TuxDroid.TuxDriver.LOG_LEVEL_DEBUG);
		tuxdroid.set_log_target(TuxDroid.TuxDriver.LOG_TARGET_SHELL);

		/*init connection and callbacks*/
		tuxdroid.start();
		tuxdroid.reset();

		/*do some stuffs*/
		
		System.out.println("Is it true : "+MultiplicationGameTuxDroidSample.a+" x "+MultiplicationGameTuxDroidSample.b+" = "+MultiplicationGameTuxDroidSample.sum()+" ? ");
		String input = "";
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		while(input != null ) input = keyboard.readLine(); 
 

		/*release hardware*/
		tuxdroid.stop();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
