package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Launcher subsystem: motor speed and the launch/flick servo.
 * Keeping these together makes tuning easier and avoids duplicated hardware code.
 */
public class Launcher {
    private DcMotorEx launchMotor;
    private Servo launchServo;

    // Find the motor and servo by the exact names saved in Robot Configuration.
    public void init(HardwareMap hardwareMap) {
        launchMotor = hardwareMap.get(DcMotorEx.class, "launchMotor");
        launchServo = hardwareMap.get(Servo.class, "launchServo");
        launchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        launchMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /** Motor power ranges from -1 to 1; current team launch setting is 0.45. */
    // Clamping protects against invalid commands larger than full power.
    // Motor power is NOT the same as a measured or regulated wheel velocity.
    public void setMotorVelocity() {
        launchMotor.setVelocity(1225); //adjust this
    }

    public void turnMotorOff() {
        launchMotor.setVelocity(0);
    }

    /** Feed/launch a game piece. Ensure the servo has a safe physical range. */
    // Servo position values are normally 0.0 through 1.0, NOT degrees.
    public void feedOn() { launchServo.setPosition(1.0); }

    /** Rest the launch servo at the team's previously used position. */
    public void feedOff() { launchServo.setPosition(0.5); }

    /** Turn motor and feed servo off at the end of an OpMode. */
    public void stop() { turnMotorOff(); feedOff(); }
}
