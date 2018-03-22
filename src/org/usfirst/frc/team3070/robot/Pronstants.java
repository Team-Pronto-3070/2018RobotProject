package org.usfirst.frc.team3070.robot;

public interface Pronstants {
	//wheel diameter is used for distances 
	public static final double WHEEL_DIAM = 6;
	public static final double WHEEL_CIRCUM = WHEEL_DIAM * Math.PI;
	public static final String SCALE = "c";
	public static final String SWITCH = "w";

	public static final int TIMEOUTMS = 10;
	//distance to switch in inches, then converted to encoder ticks
	public static final double SWITCH_DIST = 144;
	public static final double SWITCH_TICKS = (SWITCH_DIST / WHEEL_CIRCUM) * 4096;
	//encoder ticks measure with milliseconds
	public static final double MINUTES_TO_100MS = 600;
	//one rotation of the wheel equals 4096 encoder ticks
	public static final double ROTATE = 4096;
 	public static final boolean RIGHT_INV = true;
	public static final boolean LEFT_INV = false;
	
	//148 inches to the switch(hypotenuse)
	public static final double HYPO_SWITCH = 20524; 
	

	// Talon Ports
	public static final int TALLM_PORT = 2;
	public static final int TALLF_PORT = 7;

	public static final int TALRM_PORT = 0;
	public static final int TALRF_PORT = 3;

	public static final int TALC_PORT = 1;

	public static final int TALGL_PORT = 4;
	public static final int TALGR_PORT = 6;

	// Joystick Ports
	public static final int JOYL_PORT = 0;
	public static final int JOYR_PORT = 1;
	//deadzone for the joysticks 
	public static final double DEADZONE = 0.2;// TODO: change back
	//speed for the auto code
	public static final double AUTO_SPEED = 0.3;
	//the speed at which robot in auto turns
	public static final double AUTO_TURN_SPEED = 0.25;
	
	public static double MAX_SPEEED = 800;
	
	
	// Adjusting constant for turn function
		static final double TURN_OFFSET = 3;
}