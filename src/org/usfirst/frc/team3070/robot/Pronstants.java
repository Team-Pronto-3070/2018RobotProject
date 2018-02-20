package org.usfirst.frc.team3070.robot;

public interface Pronstants {
	public static final int TIMEOUTMS = 10;
	public static final int kPIDLoopIdx = 0;

	public static final double AA_TICKS = 31307; // Encoder ticks to AA

	// Talon Ports
	public static final int TALLM_PORT = 1;
	public static final int TALLF_PORT = 0;
	public static final int TALRM_PORT = 5;
	public static final int TALRF_PORT = 2;

	public static final int TALC_PORT = 4;

	public static final int TALGL_PORT = 7;
	public static final int TALGR_PORT = 6;

	// Joystick Ports
	public static final int JOYL_PORT = 0;
	public static final int JOYR_PORT = 1;

	public static final int MAX_SPEEED = 800;
	
	public static final double DEADZONE = 0.1;
	public static final double AUTO_SPEED = 0.5;
}