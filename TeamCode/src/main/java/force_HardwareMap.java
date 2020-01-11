//SkyStoneAlignDetectorimport

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

public class force_HardwareMap
{
    /* Public Sensors */
    public WGWIMU2018 imu;
    public BNO055IMU wgwIMU2018        = null;

//    public ModernRoboticsI2cRangeSensor leftRangeSensor = null;
//    public ModernRoboticsI2cRangeSensor rightRangeSensor = null;
//    public DistanceSensor deathStar = null;
//    public ModernRoboticsI2cRangeSensor wookie = null;                        //wookie is upper ultrasonic range sensor


    /* Public Motors */
    public DcMotor  frontLeftDrive         = null;
    public DcMotor  frontRightDrive        = null;
    public DcMotor  backLeftDrive          = null;
    public DcMotor  backRightDrive         = null;
    public DcMotor  LiftMotorLeft          = null;
    public DcMotor  LiftMotorRight         = null;
    public DcMotor  IntakeLeft             = null;
    public DcMotor  IntakeRight            = null;


    /* Public Servos */
    public Servo OpenServo  = null;
    public Servo TwistServo = null;
    public Servo LeftWaffle = null;  // new servo
    public Servo RightWaffle = null;
    public Servo ExtendClaw = null;
    public Servo RevwhlLeft = null;
    public Servo RevwhlRight = null;

    //public sensors
    public AnalogInput armPosInput=null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public force_HardwareMap(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        wgwIMU2018 = hwMap.get(BNO055IMU.class, "imu");
        imu = new WGWIMU2018(wgwIMU2018);

        // Define and Initialize Motors
        frontLeftDrive = hwMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hwMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive = hwMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive = hwMap.get(DcMotor.class, "backRightDrive");
        LiftMotorLeft = hwMap.get(DcMotor.class, "LiftMotorLeft");
        LiftMotorRight = hwMap.get(DcMotor.class, "LiftMotorRight");
        IntakeLeft = hwMap.get(DcMotor.class, "IntakeLeft");
        IntakeRight = hwMap.get(DcMotor.class, "IntakeRight");


        OpenServo = hwMap.get(Servo.class, "OpenServo");
        TwistServo = hwMap.get(Servo.class, "TwistServo");
        RightWaffle = hwMap.get(Servo.class, "RightWaffle");
        LeftWaffle = hwMap.get(Servo.class, "LeftWaffle");
        ExtendClaw = hwMap.get(Servo.class, "ExtendClaw");
        RevwhlLeft = hwMap.get(Servo.class, "RevwhlLeft");
        RevwhlRight = hwMap.get(Servo.class, "RevwhlRight");


        //  rightRangeSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "rightRangeSensor");
        // leftRangeSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "leftRangeSensor");
        // frontRangeSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "frontRangeSensor");


        armPosInput = hwMap.analogInput.get("armPos");

//        leftRangeSensor  = hwMap.get(ModernRoboticsI2cRangeSensor.class,"leftRangeSensor");
//        rightRangeSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class,"rightRangeSensor");
//        deathStar        = hwMap.get(DistanceSensor.class, "deathStar");
//        wookie  = hwMap.get(ModernRoboticsI2cRangeSensor.class,"wookie");                               //wookie is upper ultrasonic range sensor


        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to FORWARD if using AndyMark motors
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE if using AndyMark motors
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD); //  Set to FORWARD if using AndyMark motors
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);//  Set to FORWARD if using AndyMark motors

        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        LiftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        IntakeLeft.setDirection(DcMotor.Direction.REVERSE);
        LiftMotorLeft.setDirection(DcMotor.Direction.REVERSE);
        LiftMotorRight.setDirection(DcMotor.Direction.REVERSE);
        LiftMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RevwhlRight.setDirection(Servo.Direction.REVERSE);
        LeftWaffle.setDirection(Servo.Direction.REVERSE);

        // Set all motors to zero power
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        LiftMotorLeft.setPower(0);
        LiftMotorRight.setPower(0);
        TwistServo.setPosition(0.75);
        OpenServo.setPosition(0.5);
        RightWaffle.setPosition(0.5);
        LeftWaffle.setPosition(0.5);
        ExtendClaw.setPosition(0);
        RevwhlLeft.setPosition(0.5);
        RevwhlRight.setPosition(0.5);


        //resets motor encoders to zero
        LiftMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LiftMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
//        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
        // Odometery Section
       public DcMotor verticalLeft     = null,
                      verticalRight    = null,
                      horizontal       = null;
        final double COUNTS_PER_INCH = 1714;

       // String verticalLeftEncoderName = "vle", verticalRightEncoderName = "vre", horizontalEncoderName = "he";
    //String rfName = "frontRightDrive", rbName = "backRightDrive", lfName = "frontLeftDrive", lbName = "backLeftDrive";
  //  String verticalLeftEncoderName = "frontRightDrive", verticalRightEncoderName = backRightDrive, horizontalEncoderName = "IntakeRight";
        OdometryGlobalCoordinatePosition globalPositionUpdate;
    Thread positionThread = null;

}

