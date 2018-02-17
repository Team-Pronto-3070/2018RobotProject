package org.usfirst.frc.team3070.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class Grabber implements Pronstants {
	TalonSRX talGL, talGR;
	DigitalInput limitSwitch;
	Joystick joyL, joyR;

	public Grabber() {
		talGL = new TalonSRX(TALGL_PORT);
		talGR = new TalonSRX(TALGR_PORT);
		limitSwitch = new DigitalInput(0);
		joyL = new Joystick(0);
		joyR = new Joystick(1);

		// TODO Make sure this goes the correct way
		talGL.setInverted(false);
		// Makes sure talGR is inverse of galGL
		talGR.setInverted(!talGL.getInverted());
	}

	/**
	 * Sets Right to follow Left speed
	 * 
	 * @param speed
	 *            Speed of motors; + is in, - is out
	 */
	public void setGrab(double speed) {
		talGL.set(ControlMode.PercentOutput, speed);
		talGR.set(ControlMode.Follower, TALGL_PORT);
	}

	/**
	 * Succs in a powercube
	 */
	public void grab() {
		setGrab(1);
	}

	/**
	 * Spits out a powercube
	 */
	public void ungrab() {
		setGrab(-1);
	}

	/**
	 * Stops the motors
	 */
	public void stop() {
		setGrab(0);
	}

	/**
	 * Moves the grabber based on two booleans. If limit switch is pressed, it won't
	 * succ in. Prioritizes in, then out
	 * 
	 * @param in
	 * @param out
	 */
	public void teleop(boolean in, boolean out) {
		// if [in] is passed in and the limit switch is not pressed, the grabber will
		// intake the not-cube.
		if (in && !limitSwitch.get()) {
			grab();
		} else if (out) {// if the button is passed and pressed on the right joystick, the grabber will
							// shoot out the cube the not cube
			ungrab();
		}
		// if none of the assigned buttons are pressed, the robot's grabber won't move
		// at all.
		else {
			stop();
		}

	}
}
