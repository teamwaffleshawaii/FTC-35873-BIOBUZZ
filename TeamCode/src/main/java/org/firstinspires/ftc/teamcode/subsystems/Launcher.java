package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Launcher subsystem: motor speed and the launch/flick servo.
 * Keeping these together makes tuning easier and avoids duplicated hardware code.
 */
public class Launcher {
    private DcMotor launchMotor;
    private Servo launchServo;

    public void init(HardwareMap hardwareMap) {
        launchMotor = hardwareMap.get(DcMotor.class, "launchMotor");
        launchServo = hardwareMap.get(Servo.class, "launchServo");
        launchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        launchMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        launchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /** Motor power ranges from -1 to 1; current team launch setting is 0.45. */
    public void setMotorPower(double power) {
        launchMotor.setPower(Math.max(-1, Math.min(1, power)));
    }

    /** Feed/launch a game piece. Ensure the servo has a safe physical range. */
    public void feedOn() { launchServo.setPosition(1.0); }

    /** Rest the launch servo at the team's previously used position. */
    public void feedOff() { launchServo.setPosition(0.5); }

    /** Turn motor and feed servo off at the end of an OpMode. */
    public void stop() { setMotorPower(0); feedOff(); }
}
