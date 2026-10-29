package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.RobotSensors;

/**
 * FIELD-CENTRIC TeleOp: the IMU heading adjusts the wheel commands so that
 * stick directions stay aligned with the field, not the robot.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    // Save an offset so a driver can choose which direction counts as heading 0.
    private double resetAngle = 0;
    // Keep the previous bumper state to detect a NEW press, not every held loop.
    private boolean wasResetPressed = false;
    private final Drivetrain drivetrain = new Drivetrain();
    private final Intake intake = new Intake();
    private final Launcher launcher = new Launcher();
    private final RobotSensors sensors = new RobotSensors();

    @Override public void runOpMode() {
        drivetrain.init(hardwareMap);
        intake.init(hardwareMap);
        launcher.init(hardwareMap);
        // Field-centric drive needs an initialized IMU (gyro) to know its heading.
        sensors.init(hardwareMap);
        telemetry.addLine("Field-centric ready. Verify IMU mounting and heading.");
        telemetry.update();
        waitForStart();
        try {
            while (opModeIsActive() && !isStopRequested()) {
                // Rising-edge detection prevents repeated heading-reset commands.
                boolean resetPressed = gamepad1.left_bumper;
                if (resetPressed && !wasResetPressed) resetAngle = sensors.getHeading(AngleUnit.DEGREES);
                wasResetPressed = resetPressed;

                drive();
                if (gamepad2.a) intake.on();
                else if (gamepad2.b) intake.reverse();
                else intake.off();

                if (gamepad2.x) launcher.setMotorPower(0.45);
                else if (gamepad2.y) launcher.setMotorPower(0);
                if (gamepad2.right_bumper) launcher.feedOn();
                else launcher.feedOff();
                telemetry.update();
            }
        } finally {
            drivetrain.stop();
            intake.off();
            launcher.stop();
        }
    }

    private void drive() {
        // Rotation competes with translation; reserve magnitude for turning.
        double rotate = -gamepad1.right_stick_x;
        double scale = 1 - Math.abs(rotate);
        double stickX = -gamepad1.left_stick_x * scale;
        double stickY = -gamepad1.left_stick_y * scale;

        // Preserve the existing team's field-centric coordinate convention.
        double gyroAngle = Math.toRadians(getHeading());
        if (gyroAngle <= 0) gyroAngle += Math.PI / 2;
        else if (gyroAngle < Math.PI / 2) gyroAngle += Math.PI / 2;
        else gyroAngle -= 3 * Math.PI / 2;
        gyroAngle = -gyroAngle;
        // Holding right bumper temporarily bypasses field-centric rotation.
        if (gamepad1.right_bumper) gyroAngle = -Math.PI / 2;

        // D-pad drives at a fixed 50% in the original joystick coordinate system.
        if (gamepad1.dpad_right) stickX = 0.5;
        else if (gamepad1.dpad_left) stickX = -0.5;
        if (gamepad1.dpad_up) stickY = -0.5;
        else if (gamepad1.dpad_down) stickY = 0.5;

        // atan2 finds the joystick's direction; subtracting heading changes
        // from field-relative input into wheel-relative movement instructions.
        double theta = Math.atan2(stickY, stickX) - gyroAngle - Math.PI / 2;
        double magnitude = Math.hypot(stickX, stickY);
        double px = magnitude * Math.sin(theta + Math.PI / 4);
        double py = magnitude * Math.sin(theta - Math.PI / 4);
        double frontLeft = py - rotate, frontRight = px + rotate;
        double backLeft = px - rotate, backRight = py + rotate;
        // Only Drivetrain.java touches the drive motors themselves.
        drivetrain.setWheelPowers(frontLeft, frontRight, backLeft, backRight);

        telemetry.addData("Stick_X", stickX);
        telemetry.addData("Stick_Y", stickY);
        telemetry.addData("Magnitude", magnitude);
        telemetry.addData("Heading (Degrees)", getHeading());
        telemetry.addData("Heading (Radians)", Math.toRadians(getHeading()));
        telemetry.addData("Motor Front Left", frontLeft);
        telemetry.addData("Motor Front Right", frontRight);
        telemetry.addData("Motor Back Left", backLeft);
        telemetry.addData("Motor Back Right", backRight);
    }

    /** Relative heading: 0 means the direction chosen by the most recent reset. */
    private double getHeading() {
        double heading = sensors.getHeading(AngleUnit.DEGREES) - resetAngle;
        // Bring yaw into [-180, 180] after subtracting the reset angle.
        while (heading > 180) heading -= 360;
        while (heading < -180) heading += 360;
        return heading;
    }
}
