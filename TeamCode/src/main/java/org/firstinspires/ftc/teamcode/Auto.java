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

                actuator.launchMotor(0.45);
                sleep(1500);
                actuator.launchServoOn();
                sleep(5000);
                actuator.launchServoOff();
                actuator.launchMotor(0.20);

                actuator.turnRight(1.3, 0.50);
                actuator.goBackward(2,50);
                actuator.turnLeft(1.4,50);
                actuator.goForward(7.9, 50);
                actuator.turnRight(1.2, 50);
                actuator.goBackward(1, 0.50);




                //Wait for a second
                //sleep(1000);

                //actuator.goBackward(1, 0.3);
                //sleep(1000);

                //actuator.turnLeft(1, 0.3);
                //sleep(1000);

                //actuator.turnRight(1, 0.3);
                //sleep(1000);

                //actuator.intakeOn();
                //sleep(1000);

                //actuator.intakeOff();
                //sleep(1000);

                //actuator.launchMotor(0.50);
                //sleep(1000);

                //actuator.launchMotor(0);
                //sleep(1000);

                //actuator.launchServoOn();
                //sleep(1000);

                //actuator.launchServoOff();
                //sleep(1000);

                break;  //Break out of loop to stop the code
            }
        }
    }
}
