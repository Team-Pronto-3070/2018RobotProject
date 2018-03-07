package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous implements Pronstants {
	Drive drive;
	Grabber grabber;
	Climber climber;
	AutoSteps autoStep = AutoSteps.FIRST_STRAIGHT;
	String gameData, switchPos, startPos;
	boolean done = false;
	
	Timer timer;

	/**
	 * Constructor
	 * 
	 * @param drive
	 *            Drive instance
	 * @param grabber
	 *            Grabber instance
	 */
	public Autonomous(Drive drive, Grabber grabber, Climber climber) {
		this.drive = drive;
		this.grabber = grabber;
		this.climber = climber;
		timer = new Timer();
		timer.reset();
		timer.start();
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
		timer.reset();
		timer.start();
		// Stop the robot
		drive.stop();
		climber.stop();
		grabber.stop();
	}

	/**
	 * The periodic method for Autonomous. Run during autoPeriodic()
	 */
	public void periodic() {
		// the list of steps that the robot needs to do in auto
		switch (autoStep) {
		case FIRST_STRAIGHT:
//			if(drive.driveDistance(AUTO_SPEED, SWITCH_TICKS/2)) { // go halfway to switch
			drive.simpleDrive(-AUTO_SPEED, -AUTO_SPEED);
			if(timer.get()<3.5) {
				if(/*gameData.length() > 0 &&*/ /*Check if there is any game data first*/ startPos.equals(switchPos)) {
					nextStep(AutoSteps.LIFTING);
				} else {
					nextStep(AutoSteps.SECOND_STRAIGHT);
				}
			}
			break;
		case LIFTING:
			if(timer.get() < 1) {
				climber.up();
			} else {
				nextStep(AutoSteps.SECOND_STRAIGHT);
			}
			break;
		case SECOND_STRAIGHT:
//			if(drive.driveDistance(AUTO_SPEED, SWITCH_TICKS/2)) { // Go the second half of the distance to the switch
			drive.simpleDrive(-AUTO_SPEED, -AUTO_SPEED);
			if(timer.get() > 3.5) {
				if(startPos.equals(switchPos)) {
					nextStep(AutoSteps.LOADING);
				} else {
				nextStep(AutoSteps.DONE);
				}
			}
			SmartDashboard.putString("switchPos", switchPos);
			SmartDashboard.putString("startPos", startPos);
			break;
		case LOADING:
			SmartDashboard.putString("switchPos", switchPos);
			if(startPos.equals(switchPos)) {
				grabber.ungrab();
			}
			if(timer.get() > 2) {
				nextStep(AutoSteps.DONE);
			}
			break;
		default:
		case DONE:
			nextStep(AutoSteps.DONE);
			break;
		}
	}
}
