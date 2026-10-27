/* This template drives a four wheel mecanum drive as a POV controller
    This code is "robot centric", which means driving with a robot's point of view.
    You should learn this code first so you know how mecanum wheels work,
    but it's not recommended to be used for actual competitions.
    Use "field centric", template 3 for competitions.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp-RobotCentric")
public class TeleOperated extends LinearOpMode {
    Actuators actuator = new Actuators();
    @Override
    public void runOpMode() {

        float vertical;
        float horizontal;
        float pivot;

        actuator.init(hardwareMap);

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {
            while (opModeIsActive() && !isStopRequested()) {
                vertical = -gamepad1.left_stick_y;
                horizontal = gamepad1.left_stick_x;
                pivot = gamepad1.right_stick_x;

                actuator.leftFront((pivot + vertical + horizontal));
                actuator.rightFront((-pivot + (vertical - horizontal)));
                actuator.leftBack((pivot + (vertical - horizontal)));
                actuator.rightBack((-pivot + vertical + horizontal));

                telemetry.addData("Left Front Power", actuator.leftFront.getPower());
                telemetry.addData("Right Front Power", actuator.rightFront.getPower());
                telemetry.addData("Left Back Power", actuator.leftBack.getPower());
                telemetry.addData("Right Back Power", actuator.rightBack.getPower());
                telemetry.update();

                //Control your intake using the CROSS button
                if (gamepad2.a) {
                    actuator.intakeOn();
                }
                else if (gamepad2.b) {
                    actuator.intakeReverse();
                }
                else {
                    actuator.intakeOff();
                }

                //Control your launch using the x & y
                if (gamepad2.x) {
                    actuator.launchMotor(0.45); //Press X to turn on
                }
                else if (gamepad2.y) {
                    actuator.launchMotor(0);    //Press Y to turn off
                }

                //Control your launch servo using the right bumper
                if (gamepad2.right_bumper) {
                    actuator.launchServoOn();  //Turn on the windmill launch servo
                }
                else {
                    actuator.launchServoOff();  //Turn off the launch servo
                }
            }
        }
    }

}
