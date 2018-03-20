package org.usfirst.frc.team3070.robot;

public class Autonomous implements Pronstants {
	Drive drive;
	Grabber grabber;
	private static ProntoGyro imu;
	AutoSteps autoStep = AutoSteps.FIRST_STRAIGHT;
	String gameData, switchPos, startPos;
	boolean done = false;
	double initHeading;
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
		imu = prontoGyro;
		imu.reset();
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

		drive.resetEncDist();
		imu.reset();
		// Stop the robot
		drive.stop();
	}

	/**
	 * The periodic method for Autonomous. Run during autoPeriodic() TODO Re-fix
	 * left and right start autos
	 */
	public void periodic() {
		// the list of steps that the robot needs to do in auto
		switch (autoStep) {
		case FIRST_STRAIGHT:
			// TODO Figure out why gameData.length() isn't working
			if (/* gameData.length() > 0 && */ /* Check if there is any game data first */ startPos.equals("C")) {
				System.out.println("Center Dist: " + drive.getDistance(ROTATE * 2));
				if (drive.driveDistance(AUTO_SPEED, ROTATE * 2)) {
					nextStep(AutoSteps.FIRST_TURN);
				}
			} else {
				System.out.println("L/R Dist: " + drive.getDistance(SWITCH_TICKS));
				if (drive.driveDistance(AUTO_SPEED, SWITCH_TICKS)) {
					nextStep(AutoSteps.DONE);
				}
			}
			break;
		case FIRST_TURN:// add for both ways
			if (switchPos.equals("R")) {
				if (drive.turn(20, AUTO_TURN_SPEED)) {
					drive.stop();
					nextStep(AutoSteps.SECOND_STRAIGHT);
				}
			}
			if (switchPos.equals("L")) {
				if (drive.turn(-20, AUTO_TURN_SPEED)) {
					drive.stop();
					nextStep(AutoSteps.SECOND_STRAIGHT);
				}
			}

			break;

		case SECOND_STRAIGHT:
			if (drive.driveDistance(AUTO_SPEED, HYPO_SWITCH)) {
				drive.stop();
				if (startPos.equals("C")) {
					nextStep(AutoSteps.LOADING);
				} else {
					nextStep(AutoSteps.DONE);
				}
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
