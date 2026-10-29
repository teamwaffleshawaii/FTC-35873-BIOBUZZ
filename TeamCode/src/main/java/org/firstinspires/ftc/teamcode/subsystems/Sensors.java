package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Reads the REV hub IMU (inertial measurement unit).
 * A sensor subsystem READS information; it should not command motors.
 */
public class Sensors {
    // IMU stands for inertial measurement unit; yaw measures horizontal rotation.
    private IMU imu;

    // A wrong hub orientation makes field-centric drive behave unexpectedly.
    public void init(HardwareMap hardwareMap) {
        imu = hardwareMap.get(IMU.class, "imu");
        // These directions describe how the physical hub is mounted on this robot.
        // If mounting changes, update these values BEFORE using field-centric drive.
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP);
        imu.initialize(new IMU.Parameters(orientation));
    }

    /** Returns the robot's yaw in the caller's requested angular units. */
    // The AngleUnit argument lets the caller request degrees or radians.
    // This method returns a sensor reading; it never changes the motors.
    public double getHeading(AngleUnit unit) {
        return imu.getRobotYawPitchRollAngles().getYaw(unit);
    }
}
