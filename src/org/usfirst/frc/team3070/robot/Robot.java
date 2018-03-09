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
	private static BNO055 imu;
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
		initPos.addObject("Left", "L");
		initPos.addDefault("Center", "C");
		initPos.addObject("Right", "R");
		SmartDashboard.putData("Initial Position", initPos);

		servoTest.addDefault("lock", "lock");
		servoTest.addDefault("unlock", "unlock");

		// Class initialization
		drive = new Drive(autonomous, prontoGyro);
		grabber = new Grabber();
		climber = new Climber();
		imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,
				        		BNO055.vector_type_t.VECTOR_EULER);
		autonomous = new Autonomous(drive, grabber, prontoGyro);

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

	public void autonomousInit() {
		// Sets up field data
		if(DriverStation.getInstance().getGameSpecificMessage().length() > 0) {
			autonomous.gameData = DriverStation.getInstance().getGameSpecificMessage(); // Gets data from field/dashboard
			autonomous.switchPos = autonomous.gameData.substring(0, 1); // Position of alliance's switch, either L or R
			System.out.println("Game data is" + autonomous.gameData);
		}
		autonomous.startPos = initPos.getSelected();
		autonomous.done = false;
		autonomous.nextStep(AutoSteps.FIRST_STRAIGHT);
		drive.resetEncDist();
		drive.stop();
		grabber.stop();
		climber.stop();
	}

	@Override
	public void autonomousPeriodic() {
		autonomous.periodic();
		SmartDashboard.putNumber("SpeedL", drive.talLM.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("SpeedR", drive.talRM.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Distance", (drive.getLeftDist() + drive.getRightDist()) / 2);
	}

	public void teleopInit() {
		double lp = .8;
		double li = .006;
		double ld = 7.0;
		double lf = 0.327;
		drive.setLeftPID(lp, li, ld, lf);

		double rp = .8;
		double ri = .006;
		double rd = 7.0;
		double rf = 0.337;
		drive.setRightPID(rp, ri, rd, rf);
	}

	@Override
	public void teleopPeriodic() {
		grabber.teleop(joyL.getRawButton(3), joyL.getRawButton(2));
		//drive.joystickDrive(joyL.getRawAxis(1), joyR.getRawAxis(1));
		climber.cTeleop(joyR.getRawButton(3), joyR.getRawButton(2), (joyR.getRawButton(6) && joyR.getRawButton(11))); // Press 6 AND 11 to engage the ratchet
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