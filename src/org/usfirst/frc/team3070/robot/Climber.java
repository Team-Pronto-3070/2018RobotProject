package org.usfirst.frc.team3070.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Climber implements Pronstants {
	TalonSRX talC;

	/**
	 * Constructor
	 */
	public Climber() {
		talC = new TalonSRX(TALC_PORT);
	}

	/**
	 * Sets motor speed to 1 Make sure it's going the right way
	 */
	public void up() {
		talC.set(ControlMode.PercentOutput, 1);
	}

	/**
	 * Sets motor speed to -1
	 */
	public void down() {
		talC.set(ControlMode.PercentOutput, -1);
	}

	/**
	 * Stops the climber
	 */
	public void stop() {
		talC.set(ControlMode.PercentOutput, 0);
	}

	/**
	 * this method is for the climbing/extending of the robot extender bit
	 */
	public void cTeleop(boolean up, boolean down) {
		// if [up] is pressed, it will extend as much as it can
		if (up) {
			up();
		}
		// else if [down] is pressed, it will retract as much as it can
		else if (down) {
			down();
		}
		// if none of the above are being pressed, the extendy bit wont be moved.
		else {
			stop();
		}
	}
}
