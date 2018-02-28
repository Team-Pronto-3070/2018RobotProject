package org.usfirst.frc.team3070.robot;

public interface Pronstants {

	public static final double WHEEL_DIAM = 6;
	public static final double WHEEL_CIRCUM = WHEEL_DIAM * Math.PI;
	public static final String SCALE = "c";
	public static final String SWITCH = "w";

	public static final int UNLOCKED_ANGLE = 0;
	public static final int LOCKED_ANGLE = 1;
	public static final int TIMEOUTMS = 10;
	public static final int kPIDLoopIdx = 0;

	public static final double SWITCH_DIST = 144;
	public static final double SWITCH_TICKS = (SWITCH_DIST / WHEEL_CIRCUM) * 4096;

	public static final double SECONDS_TO_100MS = 600;
	
	public static final boolean RIGHT_INV = true;
	public static final boolean LEFT_INV = false;

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

	public static final int MAX_SPEEED = 800;

	public static final double DEADZONE = 0.2;// TODO: change back
	public static final double AUTO_SPEED = 0.5;
}