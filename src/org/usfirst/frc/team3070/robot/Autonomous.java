package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Autonomous implements Pronstants {
	Drive drive;
	Grabber grabber;
	Climber climber;
	Timer timer;
	AutoSteps autoStep;
	SendableChooser<String> chooser;
	SendableChooser<String> initPos;
	int mode;
	

	String gameData, switchPos, scalePos;

	// Auto Distances (SwitchL, SwitchR, ScaleL, ScaleR, straight)
	// rearrange and test
	double[] firstDist = { AUTO_SWITCH_DIST1, AUTO_SWITCH_DIST1, AUTO_SCALE_DIST1, AUTO_SCALE_DIST1,
			AUTO_SWITCH_DIST1 };
	double[] firstTurn = { AUTO_TURN_LEFT, AUTO_TURN_RIGHT, AUTO_TURN_LEFT, AUTO_TURN_RIGHT, 0 };
	double[] secondDist = { AUTO_SWITCH_DIST2, AUTO_SWITCH_DIST2, AUTO_SCALE_DIST2, AUTO_SCALE_DIST2, 0 };

	public void nextStep(AutoSteps next) {
		// Tells the robot to go to the next step
		autoStep = next;

		// Stop the robot
		drive.stop();
		climber.stop();
		grabber.stop();

		// Resets the gyro
		resetSensors();

		// Reset and start the timer
		timer.reset();
		timer.start();
	}
	
	public void printMode(boolean firstChoice) {
		if(!firstChoice) {
			System.out.print("Balance selected not available. Instead chose ");
		} else {
			System.out.print("Mode: ");
		}
		if(mode == 0) {
			System.out.println(mode + "; Left switch");
		} else if(mode == 1) {
			System.out.print(mode + "; Right switch");
		} else if(mode == 2) {
			System.out.println(mode + "; Left scale");
		} else if(mode == 3) {
			System.out.println(mode + ";Right scale");
		} else {
			System.out.println(mode + "; Straight");
		}
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
	 */
	public Autonomous(Drive drive, /* Grabber grabber, Climber climber, */ SendableChooser<String> initPos,

			SendableChooser<String> chooser) {
		this.drive = drive;
		// this.grabber = grabber;
		// this.climber = climber;
		this.initPos = initPos;
		this.chooser = chooser;
		nextStep(AutoSteps.FIRST_STRAIGHT);
		// this.prontoGyro = prontoGyro;

		// Sets up field data
		gameData = DriverStation.getInstance().getGameSpecificMessage(); // Gets data from field/dashboard
		if (gameData != null) {
			switchPos = gameData.substring(0, 1); // Position of alliance's switch, either L or R
			scalePos = gameData.substring(1, 2); // Position of scale, either L or R
		}
	}

	public void resetSensors() {
		timer.reset();
		drive.prontoGyro.reset();

	}

	public void go() {
		// left start position
		if (initPos.getSelected().equals("l")) {
			// going to the switch
			if (chooser.getSelected().equals("w")) {
				// switch is on the left side
				if (switchPos.equals("L")) {
					// this sets the path to go to the left switch when the robot is on the left
					// side
					mode = 0;
					printMode(true);
				}else if(scalePos.equals("L")) {
					mode = 2;
					printMode(false);
				} else {
					mode = 4;
					printMode(false);
				}
			} // going to the scale
			if (chooser.getSelected().equals("c")) {
				// switch is on the left side
				if (scalePos.equals("L")) {
					// this sets the path to go to the left scale when the robot is on the left side
					mode = 2;
					printMode(true);
				} else if(switchPos.equals("L")) {
					mode = 0;
					printMode(false);
				} else {
					mode = 4;
					printMode(false);
				}
			} // if none of the left or the center options are selected, the path will move to
				// the right side
		} else if (initPos.getSelected().equals("r")) {
			// path that goes to the switch
			if (chooser.getSelected().equals("w")) {
				// goes to right side of the switch
				if (switchPos.equals("R")) {
					// this sets the path to the right side of the switch, while starting on the
					// right side
					mode = 1;
					printMode(true);
				} else if(scalePos.equals("R")) {
					mode = 3;
					printMode(false);
				} else {
					mode = 4;
				}
			} // this sets the path to scale
			if (chooser.getSelected().equals("c")) {
				// this sets the path to the right side of scale
				if (scalePos.equals("R")) {
					// this sets the path to the right side of the scale, when the robot starts on
					// the right
				} // if none of the left or the center options are selected, the path will move to
					// the right side
			} else if (initPos.getSelected().equals("r")) {
				// path that goes to the switch
				if (chooser.getSelected().equals("w")) {
					// goes to right side of the switch
					if (switchPos.equals("R")) {
						// this sets the path to the right side of the switch, while starting on the
						// right side
						mode = 1;
					}
				} // this sets the path to scale
				if (chooser.getSelected().equals("c")) {
					// this sets the path to the right side of scale
					if (scalePos.equals("R")) {
						// this sets the path to the right side of the scale, when the robot starts on
						// the right
						mode = 3;
						System.out.println("Mode " + "right side scale, start right");
					}
				}
			} else if (initPos.getSelected().equals("c")) {
				// this sets the path to the path that just sets the robot to forward
				mode = 4;
			} else {
				System.out.println("Mode " + "no auto selected");
			}
		}
	}

	public boolean timerWait(double seconds) {
		if (timer.get() - initTimer >= seconds) {
			return true;
		} else {
			return false;
		}
	}

	public void periodic() {
		// the list of steps that the robot needs to do in auto
		switch (autoStep) {
		// this step makes the robot go straight
		case FIRST_STRAIGHT:
			// robot drives the distance defined by firstDist
			if (drive.getLeftDist() < firstDist[mode]) {
				drive.simpleDrive(AUTO_SPEED, AUTO_SPEED);
			} else {
				// this makes sure all of the momentum stops
				drive.stop();
				// this advances the step
				// robot drives the distance defined by firstDist
				drive.drivePID(10, 10);
				// this advances the step
				nextStep(AutoSteps.FIRST_BREAK);
			}
			break;
		// Break step that stops for .1 seconds
		case FIRST_BREAK:
			if (timerWait(.1)) {
				nextStep(AutoSteps.FIRST_TURN);
			}
			break;
		// this step is the first turn
		case FIRST_TURN:
			if (drive.turn(90)) {
				// advances the step
				nextStep(AutoSteps.SECOND_BREAK);
			}
			break;
		case SECOND_BREAK:
			if (timerWait(.1)) {
				nextStep(AutoSteps.SECOND_STRAIGHT);
			}
			break;
		case SECOND_STRAIGHT:
			drive.driveDistance(AUTO_SPEED, secondDist[mode]);
			drive.simpleDrive(AUTO_SPEED, AUTO_SPEED);
			nextStep(AutoSteps.THIRD_BREAK);
			break;
		case THIRD_BREAK:
			if (timerWait(.1)) {
				nextStep(AutoSteps.LOADING);
			}
			break;
		case LOADING:
			climber.up();

			if (mode <= 1) { // If we are going to the switch
				if (timer.get() - initTimer >= TIME_TO_SWITCH) { // When the timer is greater than the time it takes to
																	// get to the switch
					climber.stop(); // Stop the climber
					if (timer.get() < TIME_FOR_CUBE_OUT) { // While the cube is getting spit out
						grabber.ungrab(); // Spit the cube out
					} else { // When the cube is done getting spit out
						grabber.stop(); // Stop the grabber
					}
					nextStep(AutoSteps.DONE); // Advance steps
				}
			} else if (mode > 1) { // If we are going to scale
				if (timer.get() - initTimer >= TIME_TO_SCALE) {// When the timer is greater than the time it takes toget
																// to the switch
					climber.stop();
					if (timer.get() < TIME_FOR_CUBE_OUT) { // While the cube is getting spit out
						grabber.ungrab(); // Spit the cube out
					} else { // When the cube is done getting spit out
						grabber.stop(); // Stop the grabber
					}
					nextStep(AutoSteps.DONE); // Advance steps
				}
			}

			break;
		default:
		case DONE:
			drive.stop();
			break;
		}
	}
}