package org.usfirst.frc.team3070.robot;

public class Autonomous implements Pronstants {
	Drive drive;
	Grabber grabber;
	AutoSteps autoStep = AutoSteps.FIRST_STRAIGHT;
	String gameData, switchPos, startPos;
	boolean done = false;

	/**
	 * Constructor
	 * 
	 * @param drive
	 *            Drive instance
	 * @param grabber
	 *            Grabber instance
	 */
	public Autonomous(Drive drive, Grabber grabber) {
		this.drive = drive;
		this.grabber = grabber;
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
			if(drive.driveDistance(AUTO_SPEED, SWITCH_TICKS)) {
				// TODO Figure out why gameData.length() isn't working
				if(/*gameData.length() > 0 &&*/ /*Check if there is any game data first*/ startPos.equals(switchPos)) {
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
		default:
		case DONE:
			nextStep(AutoSteps.DONE);
			break;
		}
	}
}
