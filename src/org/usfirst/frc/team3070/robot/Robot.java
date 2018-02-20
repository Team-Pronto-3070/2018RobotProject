package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot implements Pronstants {
	Drive drive;
	// Grabber grabber;
	// Climber climber;
	Autonomous auto;

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

		// Class initialization
		drive = new Drive();
		// grabber = new Grabber();
		// climber = new Climber();
		auto = new Autonomous(drive);

		joyL = new Joystick(0);
		joyR = new Joystick(1);
		drive.setLeftPID(lp, li, ld, lf);

		drive.setRightPID(rp, ri, rd, rf);

		SmartDashboard.putNumber("Setpoint", 0);
		SmartDashboard.putNumber("SpeedL", 0);
		SmartDashboard.putNumber("SpeedR", 0);

		SmartDashboard.putNumber("LP", lp);
		SmartDashboard.putNumber("LI", li);
		SmartDashboard.putNumber("LD", ld);
		SmartDashboard.putNumber("LF", lf);

		SmartDashboard.putNumber("RP", rp);
		SmartDashboard.putNumber("RI", ri);
		SmartDashboard.putNumber("RD", rp);
		SmartDashboard.putNumber("RF", rf);

		SmartDashboard.putNumber("Left output", 0);
		SmartDashboard.putNumber("Right output", 0);

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
		// grabber.stop();
		drive.setNeutralMode(false);
	}

	@Override
	public void teleopPeriodic() {
		drive.joystickDrive(joyL.getRawAxis(1), joyR.getRawAxis(1));
		// climber.cTeleop(joyL.getRawButton(2), joyR.getRawButton(3));
		// grabber.teleop(joyL.getRawButton(3), joyR.getRawButton(3));
		SmartDashboard.putNumber("SpeedL", drive.talLM.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("SpeedR", drive.talRM.getSelectedSensorVelocity(0));
		
		 lp = SmartDashboard.getNumber("LP", lp);
		 li = SmartDashboard.getNumber("LI", li);
		 ld = SmartDashboard.getNumber("LD", ld);
		 lf = SmartDashboard.getNumber("LF", lf);

		 rp = SmartDashboard.getNumber("RP", rp);
		 ri = SmartDashboard.getNumber("RI", ri);
		 rd = SmartDashboard.getNumber("RD", rd);
		 rf = SmartDashboard.getNumber("RF", rf);
	}

	@Override
	public void testPeriodic() {
	}

}