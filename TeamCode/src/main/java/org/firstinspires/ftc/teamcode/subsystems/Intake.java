package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/** Intake hardware and behaviors belong here, not inside driver controls. */
public class Intake {
    private DcMotor intakeMotor;
    private Servo leftIntakeServo, rightIntakeServo;

    public void init(HardwareMap hardwareMap) {
        // Names below must match the Robot Controller configuration exactly.
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        leftIntakeServo = hardwareMap.get(Servo.class, "leftIntakeServo");
        rightIntakeServo = hardwareMap.get(Servo.class, "rightIntakeServo");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /** Pull game pieces in. Servo positions match the team's original code. */
    public void on() {
        intakeMotor.setPower(1.0);
        leftIntakeServo.setPosition(0);
        rightIntakeServo.setPosition(1);
    }

    /** Push pieces out to help clear a jam. */
    public void reverse() {
        intakeMotor.setPower(-0.5); // 50% reverse: tune gently to protect the mechanism.
        leftIntakeServo.setPosition(0);
        rightIntakeServo.setPosition(1);
    }

    /** Stop the motor and return the servos to their original neutral positions. */
    public void off() {
        intakeMotor.setPower(0);
        leftIntakeServo.setPosition(0.5);
        rightIntakeServo.setPosition(0.5);
    }
}
