package org.usfirst.frc.team3070.robot;

public class Config_Auto_PID implements Pronstants {
	Drive drive;

	public Config_Auto_PID(Drive drive) {
		this.drive = drive;
	}

	public void config() {
		drive.talRM.configPeakOutputForward(AUTO_SPEED, TIMEOUTMS);
		drive.talRF.configPeakOutputForward(AUTO_SPEED, TIMEOUTMS);
		drive.talLM.configPeakOutputForward(AUTO_SPEED, TIMEOUTMS);
		drive.talLF.configPeakOutputForward(AUTO_SPEED, TIMEOUTMS);

		drive.talRM.configPeakOutputReverse(-AUTO_SPEED, TIMEOUTMS);
		drive.talRF.configPeakOutputReverse(-AUTO_SPEED, TIMEOUTMS);
		drive.talLM.configPeakOutputReverse(-AUTO_SPEED, TIMEOUTMS);
		drive.talLF.configPeakOutputReverse(-AUTO_SPEED, TIMEOUTMS);

		drive.talRM.configNominalOutputForward(0, TIMEOUTMS);
		drive.talRF.configNominalOutputForward(0, TIMEOUTMS);
		drive.talLM.configNominalOutputForward(0, TIMEOUTMS);
		drive.talLF.configNominalOutputForward(0, TIMEOUTMS);

		drive.talRM.configNominalOutputReverse(-0, TIMEOUTMS);
		drive.talRF.configNominalOutputReverse(-0, TIMEOUTMS);
		drive.talLM.configNominalOutputReverse(-0, TIMEOUTMS);
		drive.talLF.configNominalOutputReverse(-0, TIMEOUTMS);

		drive.talRM.config_kF(kPIDLoopIdx, 0.0, TIMEOUTMS);
		drive.talRF.config_kF(kPIDLoopIdx, 0.0, TIMEOUTMS);
		drive.talLM.config_kF(kPIDLoopIdx, 0.0, TIMEOUTMS);
		drive.talLF.config_kF(kPIDLoopIdx, 0.0, TIMEOUTMS);

		drive.talRM.config_kP(kPIDLoopIdx, 0.1, TIMEOUTMS);
		drive.talRF.config_kP(kPIDLoopIdx, 0.1, TIMEOUTMS);
		drive.talLM.config_kP(kPIDLoopIdx, 0.1, TIMEOUTMS);
		drive.talLF.config_kP(kPIDLoopIdx, 0.1, TIMEOUTMS);

		drive.talRM.config_kI(kPIDLoopIdx, 0.0, TIMEOUTMS);
		drive.talRF.config_kI(kPIDLoopIdx, 0.0, TIMEOUTMS);
		drive.talLM.config_kI(kPIDLoopIdx, 0.0, TIMEOUTMS);
		drive.talLF.config_kI(kPIDLoopIdx, 0.0, TIMEOUTMS);

		drive.talRM.config_kD(kPIDLoopIdx, 0.0, TIMEOUTMS);
		drive.talRF.config_kD(kPIDLoopIdx, 0.0, TIMEOUTMS);
		drive.talLM.config_kD(kPIDLoopIdx, 0.0, TIMEOUTMS);
		drive.talLF.config_kD(kPIDLoopIdx, 0.0, TIMEOUTMS);
	}
}
