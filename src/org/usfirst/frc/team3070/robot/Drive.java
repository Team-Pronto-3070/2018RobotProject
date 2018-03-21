package org.usfirst.frc.team3070.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class Drive implements Pronstants {
	TalonSRX talLM, talLF, talRM, talRF;
	Joystick joyL, joyR;
	ProntoGyro prontoGyro;
	Autonomous autonomous;

	int initDistL, initDistR;

	public Drive(Autonomous autonomous, ProntoGyro prontoGyro) {
		talLM = new TalonSRX(TALLM_PORT);
		talLF = new TalonSRX(TALLF_PORT);
		talRM = new TalonSRX(TALRM_PORT);
		talRF = new TalonSRX(TALRF_PORT);
		joyL = new Joystick(JOYL_PORT);
		joyR = new Joystick(JOYR_PORT);
		
		this.autonomous = autonomous;
		this.prontoGyro = prontoGyro;
		
		setInverted();
		setNeutralMode(false);
		setCurrentLimits(10, 15, 100);
		talLM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		talRM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

		initDistL = talLM.getSelectedSensorPosition(0);
		initDistR = talRM.getSelectedSensorPosition(0);

		resetEncDist();

		talRM.setSensorPhase(true);
		talLM.setSensorPhase(true);
	}

	public boolean getDistance(double distance) {
		if (Math.abs(getRightDist()) >= distance && Math.abs(getLeftDist()) >= distance) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * PID joystick drive with a deadzone.
	 * 
	 * @param joyL
	 *            Left joystick Y-axis
	 * @param joyR
	 *            Right joystick Y-axis
	 */
	public void joystickDrive(double joyL, double joyR) {
		double left = 0;
		double right = 0;
		if (Math.abs(joyL) > DEADZONE) {
			left = joyL * MAX_SPEEED / 3;
		} else {
			left = 0;

		}
		if (Math.abs(joyR) > DEADZONE) {
			right = joyR * MAX_SPEEED / 3;
		} else {
			right = 0;
		}
		simpleDrive(joyR, joyL);

	}

//	public void altJoystickDrive(double joyX, double joyY) {
//		double left = 0;
//		double right = 0;
//		if (Math.abs(joyX) > DEADZONE) {
//			left = joyX * MAX_SPEEED / 3;
//		} else {
//			left = 0;
//
//		}
//		if (Math.abs(joyY) > DEADZONE) {
//			right = joyY * MAX_SPEEED / 3;
//		} else {
//			right = 0;
//		}
//		drivePID(left, right);
//
//	}

	/**
	 * Drive a certain distance
	 * 
	 * @param power
	 *            Speed of motors
	 * @param dist
	 *            Distance wanted, in encoder ticks
	 */
	public boolean driveDistance(double power, double dist) {
		//drivePID(dist, dist); // TODO fix yo shit
		simpleDrive(power, power);
		if (getDistance(dist)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean turn(double angle, double maxSpeed) {
		// "simple" regression to 0
		double delta = Math.abs(angle)-Math.abs(prontoGyro.getOffsetHeading());
		double adjuster = Math.abs(delta/angle);
		double newSpeed = Math.abs(maxSpeed*adjuster);
		if(newSpeed < 0.15) {
			newSpeed = 0.15;
		}
		// if ccw
		if(angle<0) {
			if(angle < prontoGyro.getOffsetHeading()) {
				
				simpleDrive(-newSpeed, newSpeed);
				return false;
			}
			else {
				stop();
				return true;
			}
		}
		// if cw
		else {
			if(angle > prontoGyro.getOffsetHeading()) {
				simpleDrive(newSpeed, -newSpeed);
				return false;
			}
			else {
				stop();
				return true;
			}
		}
//		// Defaults the speed to the maximum speed
//		double speed = maxSpeed;
//		// Adjusts speed linearly when within 10 degrees of expected value
//		double delta = Math.abs(prontoGyro.getOffsetHeading() - angle);
//
//		if (delta <= Pronstants.MAX_DEGREES_FULL_SPEED) {
//			// "Simple" linear regression
//			// m = (Y2 - Y1) / ( X2 - X1 )
//			double m = ((maxSpeed - Pronstants.AUTO_SPEED) / (Pronstants.MAX_DEGREES_FULL_SPEED - Pronstants.TURN_OFFSET));
//			// y = m*x + b => b = y - m*x. Choosing Y1 and X1:
//			double b = Pronstants.MIN_TURN_SPEED - (m * Pronstants.TURN_OFFSET);
//
//			// Substitute in the angle delta for x and
//			speed = m * delta + b;
//
//		}
//
//		// Checks if the gyro angle is less than the desired angle
//		if (prontoGyro.getOffsetHeading() < angle - Pronstants.TURN_OFFSET) {
//			// If it is, turn left
//			simpleDrive(-speed, speed);
//			// and tell the source that turning is not done
//			return false;
//
//		}
//
//		// Otherwise, checks if the gyro angle is greater than the desired angle
//		else if (prontoGyro.getOffsetHeading() > angle + Pronstants.TURN_OFFSET) {
//			// If it is, turn right
//			simpleDrive(speed, -speed);
//			// and tell the source that turning is not done
//			return false;
//		}
//
//		else {
//			// If the gyro angle is aligned with the desired angle,
//			// tell the source that the robot has turned the desired amount
//			stop();
//			return true;
//		}
	}

	public void setRight(double power) {
		talRM.set(ControlMode.PercentOutput, power);
		talRF.set(ControlMode.Follower, TALRM_PORT);
	}

	public void setLeft(double power) {
		talLM.set(ControlMode.PercentOutput, power);
		talLF.set(ControlMode.Follower, TALLM_PORT);
	}

	/**
	 * Sets motor speeds, without any ramp or closed-loop control
	 * 
	 * @param left
	 *            Sets speed of left motor, from -1 to 1
	 * @param right
	 *            Sets speed of right motor, from -1 to 1
	 */
	public void simpleDrive(double left, double right) {
		talLM.set(ControlMode.PercentOutput, left);
		talRM.set(ControlMode.PercentOutput, right);
		talLF.set(ControlMode.Follower, TALLM_PORT);
		talRF.set(ControlMode.Follower, TALRM_PORT);
	}

	/**
	 * Sets the speeds to 0
	 */
	public void stop() {
		talLM.set(ControlMode.PercentOutput, 0);
		talRM.set(ControlMode.PercentOutput, 0);
		talLF.set(ControlMode.Follower, TALLM_PORT);
		talRF.set(ControlMode.Follower, TALRM_PORT);
	}

	/**
	 * Inverts right motors Verify if these are going the right way
	 */
	private void setInverted() {
		talLM.setInverted(LEFT_INV);
		talLF.setInverted(LEFT_INV);
		
		talRM.setInverted(RIGHT_INV);
		talRF.setInverted(RIGHT_INV);
	}

	/**
	 * @return Encoder-unit distance since start/reset for L
	 */
	public int getLeftDist() {
		return talLM.getSelectedSensorPosition(0) - initDistL;
	}

	/**
	 * @return Encoder-unit distance since start/reset for R
	 */
	public int getRightDist() {
		return talRM.getSelectedSensorPosition(0) - initDistR;
	}

	/**
	 * Resets encoder position units
	 */
	public void resetEncDist() {
		initDistL = talLM.getSelectedSensorPosition(0);
		initDistR = talRM.getSelectedSensorPosition(0);
	}

	/**
	 * Keep this false probably
	 * 
	 * @param brake
	 *            True for Brake, False for Coast
	 */
	public void setNeutralMode(boolean brake) {
		if (brake) {
			talLM.setNeutralMode(NeutralMode.Brake);
			talLF.setNeutralMode(NeutralMode.Brake);
			talRM.setNeutralMode(NeutralMode.Brake);
			talRF.setNeutralMode(NeutralMode.Brake);
		} else {
			talLM.setNeutralMode(NeutralMode.Coast);
			talLF.setNeutralMode(NeutralMode.Coast);
			talRM.setNeutralMode(NeutralMode.Coast);
			talRF.setNeutralMode(NeutralMode.Coast);
		}
	}

	/**
	 * Limits current to all motors.
	 * 
	 * If the current is at [high] for [time], it sets the current limit to [low].
	 * 
	 * @param low
	 *            Fallback current limit
	 * @param high
	 *            Initial current limit
	 * @param time
	 *            Time allowed for max current limit before it goes to low
	 */
	private void setCurrentLimits(int low, int high, int time) {
		talLM.configContinuousCurrentLimit(low, 0);
		talLM.configPeakCurrentLimit(high, 0);
		talLM.configPeakCurrentDuration(time, 0);
		talLM.enableCurrentLimit(true);

		talLF.configContinuousCurrentLimit(low, 0);
		talLF.configPeakCurrentLimit(high, 0);
		talLF.configPeakCurrentDuration(time, 0);
		talLF.enableCurrentLimit(true);

		talRM.configContinuousCurrentLimit(low, 0);
		talRM.configPeakCurrentLimit(high, 0);
		talRM.configPeakCurrentDuration(time, 0);
		talRM.enableCurrentLimit(true);

		talRF.configContinuousCurrentLimit(low, 0);
		talRF.configPeakCurrentLimit(high, 0);
		talRF.configPeakCurrentDuration(time, 0);
		talRF.enableCurrentLimit(true);
	}

	public void setLeftPID(double p, double i, double d, double f) {
		talLM.config_kF(0, f, 10);
		talLM.config_kP(0, p, 10);
		talLM.config_kI(0, i, 10);
		talLM.config_kD(0, d, 10);
	}

	public void setRightPID(double p, double i, double d, double f) {
		talRM.config_kF(0, f, 10);
		talRM.config_kP(0, p, 10);
		talRM.config_kI(0, i, 10);
		talRM.config_kD(0, d, 10);
	}

	public void drivePID(double setPointL, double setPointR) {
		talRM.set(ControlMode.Velocity, setPointR * 4096 / SECONDS_TO_100MS);
		talLM.set(ControlMode.Velocity, -setPointL * 4096 / SECONDS_TO_100MS);
		talLF.set(ControlMode.Follower, TALLM_PORT);
		talRF.set(ControlMode.Follower, TALRM_PORT);
	}
}
