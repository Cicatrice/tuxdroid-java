import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Callback;
import com.sun.jna.Structure;

/**
 *	Command your TuxDroid !
 *	It is really smaller than the Python API, currently linked to an HTTP server. 
 *
 *	Keep It Simple and Stupid !
 *
 *	@author Geoffrey Gouez
 *	@version 0.2
 *
 **/

public class TuxDroid {
	public interface TuxDriver extends Library {
		TuxDriver INSTANCE = (TuxDriver) Native.loadLibrary("tuxdriver", TuxDriver.class);
		public static boolean started = true;

		public static final int LOG_LEVEL_DEBUG = 0;
		public static final int LOG_LEVEL_INFO = 1;
		public static final int LOG_LEVEL_WARNING = 2;
		public static final int LOG_LEVEL_ERROR = 3;
		public static final int LOG_LEVEL_NONE = 4;

		public static final int LOG_TARGET_TUX = 0;
		public static final int LOG_TARGET_SHELL = 1;

		void TuxDrv_Start();
		void TuxDrv_Stop();

		String TuxDrv_PerformCommand(double delay, String cmd_str);
		void TuxDrv_ClearCommandStack();
		void TuxDrv_SetLogLevel(int level);
		void TuxDrv_SetLogTarget(int target);
		void TuxDrv_ResetPositions();
		void TuxDrv_ResetDongle();
		double get_time();

		/**
		 * Callback management (buttons and other events)
 		 **/
		public interface EventCallback extends com.sun.jna.Callback {
			public boolean callback(String msg);
		}
		public int TuxDrv_SetStatusCallback(EventCallback ec);

		/**
		 * Not yet used methods
 		 **/
		//TuxDrvError TuxDrv_GetStatusName(int id, char* name);
		//TuxDrvError TuxDrv_GetStatusId(char* name, int *id);
		//TuxDrvError TuxDrv_GetStatusState(int id, char *state);
		//TuxDrvError TuxDrv_GetStatusValue(int id, char *value);
		//void TuxDrv_GetAllStatusState(char *state);
		//int TuxDrv_TokenizeStatus(char *status, drv_tokens_t *tokens);
		//TuxDrvError TuxDrv_PerformMacroFile(char *file_path);
		//TuxDrvError TuxDrv_PerformMacroText(char *macro);
		//TuxDrvError TuxDrv_SoundReflash(char *tracks);
	}
	
	public static void start(){
		new Thread(){
			public void run(){
				TuxDriver.INSTANCE.TuxDrv_Start();
			}
		}.start();

		TuxDriver.INSTANCE.TuxDrv_SetStatusCallback(new TuxDriver.EventCallback(){
			public boolean callback(String msg){
				// I'm sure there is a way to retrieve the double value at the end of the string
				if(msg.matches("left_wing_button:bool:True.*"))	TuxDroid.on_left_wing_down();
				else if(msg.matches("left_wing_button:bool:False.*")) TuxDroid.on_left_wing_up();
				else if(msg.matches("right_wing_button:bool:True.*")) TuxDroid.on_right_wing_down();
				else if(msg.matches("right_wing_button:bool:False.*")) TuxDroid.on_right_wing_up();
				else if(msg.matches("head_button:bool:True.*")) TuxDroid.on_head_button_down();
				else if(msg.matches("head_button:bool:False.*")) TuxDroid.on_head_button_up();
				else System.out.println("Not registered event : "+msg);
				return true;
			}
		});
		
	}
	
	public static void stop(){
		TuxDriver.INSTANCE.TuxDrv_Stop();
	}

	public static void set_log_level(int level){
		TuxDriver.INSTANCE.TuxDrv_SetLogLevel(level);
	}

	public static void set_log_target(int target){
		TuxDriver.INSTANCE.TuxDrv_SetLogTarget(target);
	}

	public static void reset(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_ResetPositions();
	}

	public static void right_spin(int count){
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0,"TUX_CMD:SPINNING:RIGHT_ON:"+count);
	}
	
	public static void left_spin(int count){
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0,"TUX_CMD:SPINNING:LEFT_ON:"+count);
	}
	
	public static void wings_down(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:FLIPPERS:DOWN");
	}

	public static void wings_up(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:FLIPPERS:UP");
	}

	public static void open_eyes(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:EYES:OPEN");
	}

	public static void close_eyes(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:EYES:CLOSE");
	}

	public static void open_mouth(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:MOUTH:OPEN");
	}

	public static void close_mouth(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:MOUTH:CLOSE");
	}

/**
 * Callbacks ; those methods should be abstract, and defined in a better class
 * I'm too lazy to create a new class for test right know.
 */
	public static void on_left_wing_down(/*double since*/){
		System.out.println("!! Left wing down");
	}

	public static void on_right_wing_down(/*double since*/){
		System.out.println("!! Right wing down");
	}

	public static void on_left_wing_up(/*double since*/){
		System.out.println("!! Left wing up");
	}

	public static void on_right_wing_up(/*double since*/){
		System.out.println("!! Right wing up");
	}

	public static void on_head_button_up(/*double since*/){
		System.out.println("!! Head button up");
	}

	public static void on_head_button_down(/*double since*/){
		System.out.println("!! Head button down");
	}
	

	public static void main(String[] args){
		try{
		/*light configuration to get debug logs*/
		TuxDroid.set_log_level(TuxDriver.LOG_LEVEL_DEBUG);
		TuxDroid.set_log_target(TuxDriver.LOG_TARGET_SHELL);

		/*init connection and callbacks*/
		TuxDroid.start();
		TuxDroid.reset();

		/*do some stuffs*/
		TuxDroid.open_mouth();
		Thread.sleep(100);
		TuxDroid.left_spin(3);
		Thread.sleep(1000);
		TuxDroid.right_spin(3);
		Thread.sleep(1000);

		/*release hardware*/
		TuxDroid.stop();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
