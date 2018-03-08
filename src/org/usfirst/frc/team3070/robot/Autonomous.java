package org.usfirst.frc.team3070.robot;

public class Autonomous implements Pronstants {
	Drive drive;
	Grabber grabber;
	private static BNO055 imu;
	AutoSteps autoStep = AutoSteps.FIRST_STRAIGHT;
	String gameData, switchPos, startPos;
	boolean done = false;
	double initHeading = imu.getHeading();
	double currHeading = 0;

	/**
	 * Constructor
	 * 
	 * @param drive
	 *            Drive instance
	 * @param grabber
	 *            Grabber instance
	 */
	public Autonomous(Drive drive, Grabber grabber, ProntoGyro prontoGyro) {
		this.drive = drive;
		this.grabber = grabber;
		imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER);

	}

	public double rawHeading() {
		return imu.getHeading() - initHeading;
	}
	
	
	/**
	 * Run when want to go to next step. Stops motors and sets the enum var to the
	 * argument
	 * 
	 * @param next
	 *            Step wanted to transition to. Generally the next step in the
	 *            AutoSteps Enumerator
	 */

	public void nextStep(AutoSteps next) {
		// Tells the robot to go to the next step
		autoStep = next;
		System.out.println("Next step: " + autoStep);
		currHeading = imu.getHeading();
		drive.resetEncDist();
		// Stop the robot
		drive.stop();
	}

	/**
	 * The periodic method for Autonomous. Run during autoPeriodic()
	 */
	public void periodic() {
		// the list of steps that the robot needs to do in auto
		switch (autoStep) {
		case FIRST_STRAIGHT:
			// TODO Figure out why gameData.length() isn't working
			if (/* gameData.length() > 0 && */ /* Check if there is any game data first */ startPos.equals("Center")) {
				if (drive.driveDistance(AUTO_SPEED, ROTATE * 4)) {
					drive.stop();
					nextStep(AutoSteps.FIRST_TURN);
				}
			} else {
				if (drive.driveDistance(AUTO_SPEED, SWITCH_TICKS)) {
					drive.stop();
					nextStep(AutoSteps.LOADING);
				}

			}
			break;
		case FIRST_TURN:// add for both ways
			if (switchPos.equals("R")) {
				drive.turn(45, AUTO_SPEED);
				} else {
					drive.stop();
					nextStep(AutoSteps.SECOND_STRAIGHT);
				}
				if (switchPos.equals("L")) {
					drive.turn( -45, AUTO_SPEED);
					} else {
						drive.stop();
						nextStep(AutoSteps.SECOND_STRAIGHT);
					}
				
			
			break;
			case SECOND_STRAIGHT:
			if (drive.driveDistance(AUTO_SPEED, HYPO_SWITCH)) {
				drive.stop();
				nextStep(AutoSteps.LOADING);
			}
			break;
		case LOADING:
			grabber.ungrab();
			nextStep(AutoSteps.DONE);
			break;
		
				
		case DONE:
			nextStep(AutoSteps.DONE);
			break;
			
		default:
				nextStep(AutoSteps.DONE);
				break;
		}
	}
}
