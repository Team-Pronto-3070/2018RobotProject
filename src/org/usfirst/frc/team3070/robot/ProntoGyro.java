package org.usfirst.frc.team3070.robot;

import org.usfirst.frc.team3070.robot.BNO055;

/*
methods:
public ProntoGyro() - Constructs the class
public double getOffsetHeading() - Returns the heading in relation to the angle offset
public void reset() - Sets the angle offset to the current heading
public double getRawHeading() - Gets the heading without taking the angle offset into account
public double getOffset() - Returns the angle offset
public double normalizeHeadingVal(double heading) - Normalizes a heading value to a range of (-180, 180)
 */

public class ProntoGyro {
	// defines the variable imu from the class BNO055
	public static BNO055 imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS,BNO055.vector_type_t.VECTOR_EULER);
	
	// defines the variable for the angle offset.
	// Leave as a class variable so that each instance can have it's own angleOffset
	private double angleOffset;
	
	// Constructs the class
	public ProntoGyro() {
		// Resets the gyro
		reset();
	}
	
	// Returns the heading in relation to the offset
	public double getOffsetHeading() {	
		// Defines a variable for angle
		double angle;

		// Set the angle to the remainder of the current angle divided by 180
		angle = normalizeHeadingVal(getRawHeading() - angleOffset);
		
		return angle;
	}
	
	// Resets the angleOffset to the current heading
	public void reset() {
		// Sets angleOffset to the raw heading of the gyro
		angleOffset = getRawHeading();
		
		// Checks if the angle offset is 360 degrees
		if (angleOffset == 360.0) {
			// If so, set angleOffset to 0
			// This accounts for a problem seen on the field
			// TODO: Ask Alex why we need this, normalizeHeadingValue
			// returns a value between -180 and 180
			angleOffset = 0;
		}
	}
	
	// Gets the normalized heading of the gyro without taking the offset into account
	public double getRawHeading() {
		return normalizeHeadingVal(imu.getVector()[0]);
	}
	
	// Returns the angle offset
	public double getOffset() {
		return angleOffset;
	}
	
	// Normalize a heading value to the range of (-180, 180)
	private double normalizeHeadingVal( double heading ) {
		
		// Checks if the remainder of the given heading and 360 is greater than 180
		if( heading % 360 >  180.0 ) {
			// If so, set the heading to a negative value greater than -180
			heading = ( heading % 360.0 ) - 360.0;
		}
		
		// Otherwise, checks if the opposite case is true
		else if ( ( heading % 360 ) <= -180.0 ) {
			// If so, set the heading to a positive number less than 180
			heading = (heading % 360.0) + 360.0;
		}
		
		return heading;
	}

}
