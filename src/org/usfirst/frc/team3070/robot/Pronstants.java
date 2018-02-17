package org.usfirst.frc.team3070.robot;

public interface Pronstants {
	// Talon Ports
	public static final int TALLM_PORT = 1;
	public static final int TALLF_PORT = 0;
	public static final int TALRM_PORT = 5;
	public static final int TALRF_PORT = 2;

	public static final int TALC_PORT = 4;

	public static final int TALGL_PORT = 5;
	public static final int TALGR_PORT = 6;

	// Joystick Ports
	public static final int JOYL_PORT = 0;
	public static final int JOYR_PORT = 1;

	public static final int MAX_SPEEED = 400; // max speed in RPM
	// Use these values for something??
	// public static final int UP_BUTTON = 0;
	// public static final int DOWN_BUTTON = 1;
	// Number Constants
	public static final double DEADZONE = 0.1;
	public static final double AUTO_SPEED = 0.5;
	public static final double AUTO_TURN_SPEED = 0.3;

	public static final double TIME_TO_SWITCH = .8;
	public static final double TIME_TO_SCALE = 2.5;
	
	public static final double TIME_FOR_CUBE_IN = 3;
	public static final double TIME_FOR_CUBE_OUT = 1;

	/*
	 * Auto Distances(click to expand) All calculations are assuming the robot
	 * starts as far out l/r as possible.
	 * 
	 * robot is 32 1/4 inches by 23 1/2 inches. 16.125 inches by 11.25 inches is
	 * center mass Bumpers will add 3.25 in to each side, so true dimensions are
	 * 19.375 by 14.5 18.86 inches is circumference wheel
	 * 
	 * 168 - 19.375= 148.625 to the switch 148.625/18.86 = 7.88 rots to the switch
	 * 7.88*4096 = 32276 enc ticks to the switch
	 * 
	 * 323.65- 19.375 = 304.275 to the scale 304.275/18 = 16.9 rotations to the
	 * scale 16.9*4096 = 69222 enc ticks to the scale
	 * 
	 * 71.57 is the dist from the wall to the scale. <-What are these? Spencer 85.25
	 * is the dist from the wall to the switch.
	 * 
	 * Horizontal dist. switch to wall = 85.25 in Angle bit length = 29.69 in
	 * 
	 * 2nd distance to switch = 41.06 in
	 * 
	 * Horizontal dist. scale to wall = 71.57 in 2nd distance to scale = 27.38 in
	 */
	public static final double AUTO_SWITCH_DIST1 = 32276;
	public static final double AUTO_SCALE_DIST1 = 69222;
	public static final double AUTO_SWITCH_DIST2 = 8917;
	public static final double AUTO_SCALE_DIST2 = 5946;

	public static final double AUTO_TURN_LEFT = -90;
	public static final double AUTO_TURN_RIGHT = 90;
}
