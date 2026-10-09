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
                actuator.rightMotor(gamepad1.left_stick_y * 0.5);
                actuator.leftMotor(gamepad1.right_stick_y * 0.5);

                //Control your intake using the A button
                if (gamepad1.a) {
                    actuator.intakeOn();
                }
                else {
                    actuator.intakeOff();
                }

                //Control your launch using the right trigger
                if (gamepad1.right_trigger > 0.5) {
                    actuator.launchMotor(0.75); //Adjust the launch power as needed
                }
                else {
                    actuator.launchMotor(0);    //Turn off launch motor
                }

                //Control your launch servo using the right bumper
                if (gamepad1.right_bumper) {
                    actuator.launchServoOn();  //Turn on the windmill launch servo
                }
                else {
                    actuator.launchServoOff();  //Turn off the launch servo
                }

            }
        }
    }
}
