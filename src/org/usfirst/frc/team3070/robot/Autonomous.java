package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Autonomous implements Pronstants {
	Drive drive;
	Grabber grabber;
	SendableChooser<String> initPos;
	ProntoGyro prontoGyro;
	AutoSteps autoStep;
	String gameData, switchPos, scalePos;
	boolean done;
	/**
	 * Constructor
	 * 
	 * @param drive
	 *            Pass in drive object
	 * @param grabber
	 *            Pass in grabber object
	 * @param climber
	 *            Pass in climber object Drive instance
	 */
	public Autonomous(Drive drive, Grabber grabber, ProntoGyro prontoGyro) {
		this.drive = drive;
		// this.grabber = grabber;
		// this.climber = climber;toGyro;

		// Sets up field data
		gameData = DriverStation.getInstance().getGameSpecificMessage(); // Gets data from field/dashboard
		if (gameData != null) {
			switchPos = gameData.substring(0, 1); // Position of alliance's switch, either L or R
			scalePos = gameData.substring(1, 2); // Position of scale, either L or R
		}
	}

	public void nextStep(AutoSteps next) {
		// Tells the robot to go to the next step
		autoStep = next;

		// Stop the robot
		drive.stop();
	}

	public void periodic() {
		// the list of steps that the robot needs to do in auto
		switch (autoStep) {
		case FIRST_STRAIGHT:
			if (!done) {
				if (drive.driveDistance(AUTO_SPEED, SWITCH_TICKS)) {
					done = true;
				}
			} else {
				nextStep(AutoSteps.LOADING);
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
