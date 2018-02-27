package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.DigitalInput;

public class Autonomous implements Pronstants {
	Drive drive;
	Grabber grabber;
	SendableChooser<String> initPos;
	ProntoGyro prontoGyro;
	AutoSteps autoSteps;
	String gameData, switchPos, scalePos;

	// Auto Distances (SwitchL, SwitchR, ScaleL, ScaleR, straight)
	// rearrange and test

	/**
	 * Constructor
	 * 
	 * @param drive
	 *            Pass in drive object
	 * @param grabber
	 *            Pass in grabber object
	 * @param climber
	 *            Pass in climber object
	 *            Drive instance
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
		autoSteps = next;

		// Stop the robot
		drive.stop();
		grabber.stop();
	}
	
	public void periodic() {
		double initGyro = prontoGyro.getRawHeading();
	
		// the list of steps that the robot needs to do in auto
		switch(autoStep) {
		
		}
	}
}		
	
