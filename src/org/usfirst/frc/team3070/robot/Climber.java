package org.usfirst.frc.team3070.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber implements Pronstants {
	TalonSRX talC;
	boolean locked = false;

	/**
	 * Constructor
	 */
	public Climber() {
		talC = new TalonSRX(TALC_PORT);
		// Want the motor to be locked in place when not receiving
		talC.setNeutralMode(NeutralMode.Brake);

		talC.configOpenloopRamp(0.5, 0);

		talC.configContinuousCurrentLimit(10, 0);
		talC.configPeakCurrentLimit(15, 0);
		talC.configPeakCurrentDuration(100, 0);
		talC.enableCurrentLimit(true);
	}

	/**
	 * Sets motor speed to 1 Make sure it's going the right way
	 */
	public void up() {
		talC.set(ControlMode.PercentOutput, -CLIMB_SPEED);
	}

	/**
	 * Sets motor speed to -1
	 */
	public void down() {
		talC.set(ControlMode.PercentOutput, CLIMB_SPEED);
	}

	/**
	 * Stops the climber
	 */
	public void stop() {
		talC.set(ControlMode.PercentOutput, 0);
	}

	/**
	 * Teleop code, run in teleopPeriodic. Recommend directly passing getRawButton()
	 * 
	 * @param up
	 *            If the button for going up is pressed
	 * @param down
	 *            if the button for going down if pressed.
	 * @param lock
	 *            if the button for locking the rachet is pressed.
	 */
	public void cTeleop(boolean up, boolean down) {
		// if [up] is pressed, it will extend as much as it can
		if (up) {
			up();
		}
		// else if [down] is pressed, it will retract as much as it can
		else if (down) {
			down();
			// else if [lock] is pressed, it will lock the rachet;
		} else {
			stop();
		}
	}
}
