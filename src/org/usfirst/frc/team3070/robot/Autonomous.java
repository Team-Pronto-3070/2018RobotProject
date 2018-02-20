package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.DigitalInput;

public class Autonomous implements Pronstants {
	Drive drive;
	Grabber grabber;
	SendableChooser<String> initPos;
	ProntoGyro prontoGyro;
	AutoSteps autoSteps;
	limitSwitch = new DigitalInput(0);

	

	String gameData, switchPos, scalePos;

	// Auto Distances (SwitchL, SwitchR, ScaleL, ScaleR, straight)
	// rearrange and test
	
	
	public void nextStep(AutoSteps next) {
		// Tells the robot to go to the next step
		autoSteps = next;

		// Stop the robot
		drive.stop();
		grabber.stop();


	}

	



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
	public Autonomous(Drive drive, Grabber grabber, Climber climber, SendableChooser<String> initPos, SendableChooser<String> chooser,

			SendableChooser<String> balanceChoice) {
		this.drive = drive;
		// this.grabber = grabber;
		// this.climber = climber;
		this.initPos = initPos;
		
		nextStep(AutoSteps.FIRST_STRAIGHT);
		// this.prontoGyro = prontoGyro;

		// Sets up field data
		gameData = DriverStation.getInstance().getGameSpecificMessage(); // Gets data from field/dashboard
		if (gameData != null) {
			switchPos = gameData.substring(0, 1); // Position of alliance's switch, either L or R
			scalePos = gameData.substring(1, 2); // Position of scale, either L or R
		}
	}



	

	



	public void periodic() {
		// the list of steps that the robot needs to do in auto
		switch (autoSteps) {
		// this step makes the robot go straight
		case FIRST_STRAIGHT:
			// robot drives the distance defined by firstDist
			if (drive.talLM.getSelectedSensorPosition(0) <= 2000) {
				drive.simpleDrive(AUTO_SPEED, AUTO_SPEED);
			} else {
				drive.stop();
				// this advances the step
				nextStep(AutoSteps.FIRST_TURN);
			}
			break;
		//Turns within 3 degrees of the exchange
		case FIRST_TURN:
			//checks to adjust for angle
			if(Math.abs(prontoGyro.initGyro - prontoGyro.getRawHeading()) >= 3) {
				drive.moveAngle(Math.abs(initGyro - prontoGyro.getRawHeading()));
		}else {
			//stops once it has reached an acceptable angle
			drive.stop();
		}
			break;
		// this step is the straight away for getting the robot in range to the exchange
		case SECOND_STRAIGHT:
			//goes half of a rotation
			if (drive.talLM.getSelectedSensorPosition(0) <= 2000) {
				drive.simpleDrive(AUTO_SPEED, AUTO_SPEED);
			} else {
				//stops when it reaches the desired location
				drive.stop();
				// this advances the step
				nextStep(AutoSteps.FIRST_TURN);
			}
			break;
			//shoots out the cube
		case DISPENSE:
			if(limitSwitch.get()) {
				grabber.ungrab();
			}else {
				//when the cube is out, the grabber stops
				grabber.stop();
				nextStep(AutoSteps.THIRD_STRAIGHT);
			}
			break;
			//beeline across the line
		case THIRD_STRAIGHT:
			if (drive.talLM.getSelectedSensorPosition(0) <= 30000) {
				drive.simpleDrive(AUTO_SPEED, AUTO_SPEED);
			} else {
				//once the robot crosses the line, roughly, stops
				drive.stop();
				// this advances the step
				nextStep(AutoSteps.FIRST_TURN);
			}
			//job is done
			break;
		case STOP:
	public Autonomous(Drive drive) {
		drive.resetEncDist();
		this.drive = drive;
	}

	public void DriveForwards() {
		if (!drive.getDistance(AA_TICKS)) {
			drive.drivePID(AA_TICKS, AA_TICKS);
		} else {
			drive.stop();
		}
}
}		
	
