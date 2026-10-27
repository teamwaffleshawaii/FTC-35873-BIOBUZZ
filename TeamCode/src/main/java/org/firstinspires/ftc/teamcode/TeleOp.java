package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp Remote Control")

public class TeleOp extends LinearOpMode {
    Actuators actuator = new Actuators();
    @Override
    public void runOpMode(){
        actuator.init(hardwareMap);
        waitForStart();
        if (opModeIsActive() && !isStopRequested()){
            while (opModeIsActive() && !isStopRequested()){
                //Everything you want the robot do should go here
                //...

                //Drivetrain, adjust the 0.5 as needed if you want your robot to be faster
                actuator.rightMotor(gamepad1.left_stick_y * 1.0);  //adjust speed as needed
                actuator.leftMotor(gamepad1.right_stick_y * 1.0); //adjust speed as needed

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
