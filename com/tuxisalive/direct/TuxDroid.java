package com.tuxisalive.direct;

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
 *	@version 0.3
 *
 **/

public abstract class TuxDroid {
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
		public abstract class EventCallback implements com.sun.jna.Callback {
			protected TuxDroid tuxdroid;
			public EventCallback(TuxDroid td){
				super();
				tuxdroid = td;
			}
			public abstract boolean callback(String msg);
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
	
	public void start(){
		new Thread(){
			public void run(){
				TuxDriver.INSTANCE.TuxDrv_Start();
			}
		}.start();

		TuxDriver.INSTANCE.TuxDrv_SetStatusCallback(new TuxDriver.EventCallback(this){
			public boolean callback(String msg){
				tuxdroid.on_event(msg);
				return true;
			}
		});
		
	}
	
	public void stop(){
		TuxDriver.INSTANCE.TuxDrv_Stop();
	}

	public void set_log_level(int level){
		TuxDriver.INSTANCE.TuxDrv_SetLogLevel(level);
	}

	public void set_log_target(int target){
		TuxDriver.INSTANCE.TuxDrv_SetLogTarget(target);
	}

	public void reset(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_ResetPositions();
	}

	public void right_spin(int count){
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0,"TUX_CMD:SPINNING:RIGHT_ON:"+count);
	}
	
	public void left_spin(int count){
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0,"TUX_CMD:SPINNING:LEFT_ON:"+count);
	}
	
	public void wings_down(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:FLIPPERS:DOWN");
	}

	public void wings_up(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:FLIPPERS:UP");
	}

	public void open_eyes(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:EYES:OPEN");
	}

	public void close_eyes(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:EYES:CLOSE");
	}

	public void open_mouth(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:MOUTH:OPEN");
	}

	public void close_mouth(){
		if(TuxDriver.started)
		TuxDriver.INSTANCE.TuxDrv_PerformCommand(0.0, "TUX_CMD:MOUTH:CLOSE");
	}

/**
 * Callbacks ; those methods should be abstract, and defined in a better class
 * I'm too lazy to create a new class for test right know.
 
	public void on_left_wing_down(){
		System.out.println("!! Left wing down");
	}

	public void on_right_wing_down(){
		System.out.println("!! Right wing down");
	}

	public void on_left_wing_up(){
		System.out.println("!! Left wing up");
	}

	public void on_right_wing_up(){
		System.out.println("!! Right wing up");
	}

	public void on_head_button_up(){
		System.out.println("!! Head button up");
	}

	public void on_head_button_down(){
		System.out.println("!! Head button down");
	}
	*/
	public abstract void on_event(String msg);
}
