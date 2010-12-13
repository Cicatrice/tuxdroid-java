
import com.tuxisalive.direct.TuxDroid;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class TestTuxDroid{
	public static void main(String[] args){
		try{

		TuxDroid tuxdroid = new TuxDroid(){
				// I'm sure there is a way to retrieve the double value at the end of the string
				public void on_event(String msg){
					System.out.println("Not registered event : "+msg);
				}
		};
		/*light configuration to get debug logs*/
		tuxdroid.set_log_level(TuxDroid.TuxDriver.LOG_LEVEL_DEBUG);
		tuxdroid.set_log_target(TuxDroid.TuxDriver.LOG_TARGET_SHELL);

		/*init connection and callbacks*/
		tuxdroid.start();
		tuxdroid.reset();

		/*do some stuffs*/
		Thread.sleep(100);
		tuxdroid.close_eyes();
		Thread.sleep(100);
		tuxdroid.open_eyes();
		
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
