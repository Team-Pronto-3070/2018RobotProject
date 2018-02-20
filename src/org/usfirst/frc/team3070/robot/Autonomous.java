package org.usfirst.frc.team3070.robot;

public class Autonomous implements Pronstants {
	Drive drive;

	/**
	 * Constructor
	 * 
	 * @param drive
	 *            Drive instance
	 */
	public Autonomous(Drive drive) {
		drive.resetEncDist();
		this.drive = drive;
	}

	public void periodic() {
		if (!drive.getDistance(AA_TICKS)) {
			drive.drivePID(AA_TICKS, AA_TICKS);
		} else {
			drive.stop();
		}
	}
}