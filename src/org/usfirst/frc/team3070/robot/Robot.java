package org.usfirst.frc.team3070.robot;

//Merge test
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot implements Pronstants {
	Drive drive;
	Grabber grabber;
	Climber climber;
	Autonomous auto;

	Joystick joyL, joyR;

	@Override
	public void robotInit() {

		// Class initialization
		drive = new Drive();
		grabber = new Grabber();
		climber = new Climber();
		auto = new Autonomous(drive);

		joyL = new Joystick(0);
		joyR = new Joystick(1);

		SmartDashboard.putNumber("Setpoint", 0);
		SmartDashboard.putNumber("SpeedL", 0);
		SmartDashboard.putNumber("SpeedR", 0);

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

		joyL = new Joystick(0);
		joyR = new Joystick(1);

		SmartDashboard.putNumber("Setpoint", 0);
		SmartDashboard.putNumber("SpeedL", 0);
		SmartDashboard.putNumber("SpeedR", 0);

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

	public void autonomousInit() {
		drive.resetEncDist();
	}

	@Override
	public void autonomousPeriodic() {
		auto.periodic();
		SmartDashboard.putNumber("EncL", drive.talLM.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("EncR", drive.talRM.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("GetEncL", drive.getLeftDist());
		SmartDashboard.putNumber("GetEncR", drive.getRightDist());
	}

	public void teleopInit() {
		grabber.stop();
	}

	@Override
	public void teleopPeriodic() {
		drive.joystickDrive(joyL.getRawAxis(1), joyR.getRawAxis(1));
		climber.cTeleop(joyL.getRawButton(2), joyR.getRawButton(3));
		grabber.teleop(joyL.getRawButton(3), joyR.getRawButton(3));
		SmartDashboard.putNumber("SpeedL", drive.talLM.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("SpeedR", drive.talRM.getSelectedSensorVelocity(0));
	}

	@Override
	public void testPeriodic() {
	}

}