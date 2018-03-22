package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("unused")
public class Robot extends IterativeRobot implements Pronstants {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> servoTest = new SendableChooser<String>();
	SendableChooser<String> initPos = new SendableChooser<String>();
	
	Drive drive;
	Grabber grabber;
	Climber climber;
	Autonomous autonomous;
	ProntoGyro prontoGyro;
	Joystick joyL, joyR;

	double rp = .8;
	double ri = .006;
	double rd = 15.0;
	double rf = 0.337;

	double lp = .8;
	double li = .006;
	double ld = 15.0;
	double lf = 0.327;

	@Override
	public void robotInit() {
		//Allows driver to tell robot where it starts
		initPos.addObject("Left", "L");
		initPos.addDefault("Center", "C");
		initPos.addObject("Right", "R");
		SmartDashboard.putData("Initial Position", initPos);

		// Class initialization
		prontoGyro = new ProntoGyro();
		drive = new Drive(autonomous, prontoGyro);
		grabber = new Grabber();
		climber = new Climber();
		autonomous = new Autonomous(drive, grabber, prontoGyro);
		//Joystick initialization
		joyL = new Joystick(0);
		joyR = new Joystick(1);

		// In SmartDashboard, do View->Add->CameraServer Stream Viewer
		// CameraServer.getInstance().startAutomaticCapture();

		SmartDashboard.putNumber("Setpoint", 0);
		SmartDashboard.putNumber("SpeedL", 0);
		SmartDashboard.putNumber("SpeedR", 0);
		SmartDashboard.putNumber("Distance", 0);

		SmartDashboard.putNumber("LP", 0);
		SmartDashboard.putNumber("LI", 0);
		SmartDashboard.putNumber("LD", 0);
		SmartDashboard.putNumber("LF", 0);

		SmartDashboard.putNumber("RP", 0);
		SmartDashboard.putNumber("RI", 0);
		SmartDashboard.putNumber("RD", 0);
		SmartDashboard.putNumber("RF", 0);

		SmartDashboard.putNumber("Left output", 0);
		SmartDashboard.putNumber("Right output", 0);

		SmartDashboard.putString("Mode:", "mode");
	}
//setup for the autonomous period
	public void autonomousInit() {
		// Sets up field data
		if(DriverStation.getInstance().getGameSpecificMessage().length() > 0) {
			autonomous.gameData = DriverStation.getInstance().getGameSpecificMessage(); // Gets data from field/dashboard
			autonomous.switchPos = autonomous.gameData.substring(0, 1); // Position of alliance's switch, either L or R
			System.out.println("Game data is" + autonomous.gameData);
		}
		//sets the start position to what was selected in initPos
		autonomous.startPos = initPos.getSelected();
		//starts the autonomous code at the first step
		autonomous.nextStep(AutoSteps.FIRST_STRAIGHT);
		//stops and resets all of the robots functions
		drive.resetEncDist();
		drive.stop();
		grabber.stop();
		climber.stop();
		prontoGyro.reset();
	}

	@Override
	public void autonomousPeriodic() {
		//calls the autonomous code
		autonomous.periodic();
		//gives values of the sensors
		SmartDashboard.putNumber("SpeedL", drive.talLM.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("SpeedR", drive.talRM.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Distance", (drive.getLeftDist() + drive.getRightDist()) / 2);
		SmartDashboard.putNumber("Gyro Value", prontoGyro.getOffsetHeading());
	}

	public void teleopInit() {
		//tumbleweeds and crickets
	}

	@Override
	public void teleopPeriodic() {
		//grabber goes in and out with button 2 & 3 on the left joystick
		grabber.teleop(joyL.getRawButton(3), joyL.getRawButton(2));
		//Drives with the position of the joysticks on the y axis
		drive.joystickDrive(joyL.getRawAxis(1), joyR.getRawAxis(1));
		SmartDashboard.putNumber("SpeedL", drive.talLM.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("SpeedR", drive.talRM.getSelectedSensorVelocity(0));
	}

	@Override
	public void testPeriodic() {
		SmartDashboard.putBoolean("Limit Switch", grabber.getLimit());
		System.out.println("limit switch: " + grabber.getLimit());
		climber.cTeleop(joyL.getRawButton(3), joyL.getRawButton(2), (joyR.getRawButton(6) && joyR.getRawButton(11)));
			

	}

}