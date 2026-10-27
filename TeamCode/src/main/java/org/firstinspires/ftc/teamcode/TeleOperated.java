package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

/**
 * ROBOT-CENTRIC TeleOp: left stick moves relative to the robot's front.
 * Driver 1 drives. Driver 2 runs intake and launcher.
 * Try this mode before field-centric to learn mecanum wheel mixing.
 */
@TeleOp(name = "TeleOp-RobotCentric")
public class TeleOperated extends LinearOpMode {
    private final Drivetrain drivetrain = new Drivetrain();
    private final Intake intake = new Intake();
    private final Launcher launcher = new Launcher();

    @Override public void runOpMode() {
        drivetrain.init(hardwareMap);
        intake.init(hardwareMap);
        launcher.init(hardwareMap);
        telemetry.addLine("Robot-centric ready. Check both controllers.");
        telemetry.update();
        waitForStart();
        try {
            while (opModeIsActive() && !isStopRequested()) {
                // FTC stick Y is negative when pushed forward, hence the minus sign.
                double forward = -gamepad1.left_stick_y;
                double strafe = gamepad1.left_stick_x;
                double turn = gamepad1.right_stick_x;
                drivetrain.driveRobotCentric(forward, strafe, turn);

                // Driver 2: A = intake, B = reverse, otherwise stop.
                if (gamepad2.a) intake.on();
                else if (gamepad2.b) intake.reverse();
                else intake.off();

                // X latches the launcher on; Y latches it off.
                // No button: maintain previous speed, as in the original code.
                if (gamepad2.x) launcher.setMotorPower(0.45);
                else if (gamepad2.y) launcher.setMotorPower(0);

                // Right bumper controls the launcher feed servo only while held.
                if (gamepad2.right_bumper) launcher.feedOn();
                else launcher.feedOff();

                telemetry.addData("Left Front Power", drivetrain.getLeftFrontPower());
                telemetry.addData("Right Front Power", drivetrain.getRightFrontPower());
                telemetry.addData("Left Back Power", drivetrain.getLeftBackPower());
                telemetry.addData("Right Back Power", drivetrain.getRightBackPower());
                telemetry.update();
            }
        } finally {
            // Always stop moving mechanisms on driver stop or error.
            drivetrain.stop();
            intake.off();
            launcher.stop();
        }
    }
}
