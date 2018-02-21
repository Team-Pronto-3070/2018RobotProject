package org.usfirst.frc.team3070.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Climber implements Pronstants {
	TalonSRX talC;
	DigitalInput limitSwitch;
	Servo ratchet;
	boolean locked = false;

	/**
	 * Constructor
	 */
	public Climber() {
		talC = new TalonSRX(TALC_PORT);
		ratchet = new Servo(0);
		unlock();
		// Want the motor to be locked in place when not recieving
		talC.setNeutralMode(NeutralMode.Brake);
	}
	
	public void unlock() {
		ratchet.set(UNLOCKED_ANGLE);
	}

	public void lock() {
		ratchet.set(LOCKED_ANGLE);
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
	public void cTeleop(boolean up, boolean down, boolean lock) {
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
		
		if(lock && !this.locked) {
			lock();
			locked = true;
		}
		
		// TODO Remove this testing code
		else if(!lock) {
			unlock();
			locked = false;
		}
	}
}
