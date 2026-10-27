package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous (name = "Autonomous")
public class Auto extends LinearOpMode {
    Actuators actuator = new Actuators();

    @Override
    public void runOpMode() {
        actuator.init(hardwareMap);
        waitForStart();
        if (opModeIsActive() && !isStopRequested()) {
            while (opModeIsActive() && !isStopRequested()) {
                //...Put what you want your robot to do here
                //...Below are examples
                //...
                //go forward for 1 rotation at 30% speed

                //actuator.launchMotor(0.45);
                //sleep(1500);
                //actuator.launchServoOn();
                //sleep(5000);
                //actuator.launchServoOff();
                //actuator.launchMotor(0.20);

                actuator.goForward(1, 0.20);
                actuator.goBackward(1,0.20);
                actuator.turnLeft(1,0.20);
                actuator.turnRight(1,0.20);
                actuator.strafeLeft(1,0.20);
                actuator.strafeRight(1,0.20);


                break;  //Break out of loop to stop the code
            }
        }
    }
}
