package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Actuators{
    public DcMotor leftMotor;
    public DcMotor rightMotor;
    public DcMotor launchMotor;
    public DcMotor intakeMotor;
    public Servo rightIntakeServo;
    public Servo leftIntakeServo;
    public Servo launchServo;
    public void init(HardwareMap hwMap){
        //Set up motor hardware here
        leftMotor = hwMap.get(DcMotor.class, "leftMotor");
        rightMotor = hwMap.get(DcMotor.class, "rightMotor");
        intakeMotor = hwMap.get(DcMotor.class, "intakeMotor");
        launchMotor = hwMap.get(DcMotor.class, "launchMotor");
        //Set up motor mode when power is zero
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        launchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //Change motor directions as needed
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        launchMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        //Set up servo hardware here
        leftIntakeServo = hwMap.get(Servo.class, "leftIntakeServo");
        rightIntakeServo = hwMap.get(Servo.class, "rightIntakeServo");
        launchServo = hwMap.get(Servo.class, "launchServo");
    }

    //Set up leftMotor here to be used in TeleOp
    public void leftMotor(double power){
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor.setPower(power);
    }
    //Set up rightMotor here to be used in TeleOp
    public void rightMotor(double power){
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setPower(power);
    }
    //Set up intake here to be used in TeleOp and Auto
    public void intakeOn(){
        //Intake Motor
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setPower(1); //Adjust this speed as needed, currently 100%
        //Intake Servos (Either 0 or 1)
        leftIntakeServo.setPosition(1);
        rightIntakeServo.setPosition(1);
    }

    public void intakeOff(){
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setPower(0);
        leftIntakeServo.setPosition(0.5);
        rightIntakeServo.setPosition(0.5);
    }

    //Set up launchMotor here to be used in TeleOp and Auto
    public void launchMotor(double power){
        launchMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        launchMotor.setPower(power);
    }
    //Set up launchServo here to be used in TeleOp and Auto
    public void launchServoOn(){
        //To turn on servos, use either 0 or 1 (opposite directions)
        launchServo.setPosition(0);
    }
    public void launchServoOff(){
        //To turn off servos, use 0.5
        launchServo.setPosition(0.5);
    }
    //Set up movement directions here to be used in Auto
    public void goForward(double rotation, double power){
        //this is a function to make your robot go forward
        //use actuator.goForward(1, 0.3); in your auto code
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setTargetPosition((int) (-537.7 * rotation));
        rightMotor.setTargetPosition((int) (-537.7 * rotation));
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(power);
        rightMotor.setPower(power);
        while (leftMotor.isBusy() && rightMotor.isBusy()){
        }
    }
    //Set up movement directions here to be used in Auto
    public void goBackward(double rotation, double power){
        //this is a function to make your robot go forward
        //use actuator.goForward(1, 0.3); in your auto code
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setTargetPosition((int) (537.7 * rotation));
        rightMotor.setTargetPosition((int) (537.7 * rotation));
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(power);
        rightMotor.setPower(power);
        while (leftMotor.isBusy() && rightMotor.isBusy()){
        }
    }
    //Set up movement directions here to be used in Auto
    public void turnLeft(double rotation, double power){
        //this is a function to make your robot go forward
        //use actuator.goForward(1, 0.3); in your auto code
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setTargetPosition((int) (537.7 * rotation));
        rightMotor.setTargetPosition((int) (-537.7 * rotation));
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(power);
        rightMotor.setPower(power);
        while (leftMotor.isBusy() && rightMotor.isBusy()){
        }
    }
    //Set up movement directions here to be used in Auto
    public void turnRight(double rotation, double power){
        //this is a function to make your robot go forward
        //use actuator.goForward(1, 0.3); in your auto code
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setTargetPosition((int) (-537.7 * rotation));
        rightMotor.setTargetPosition((int) (537.7 * rotation));
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(power);
        rightMotor.setPower(power);
        while (leftMotor.isBusy() && rightMotor.isBusy()){
        }
    }
}
