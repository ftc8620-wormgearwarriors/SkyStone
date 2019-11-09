//SkyStoneAlignDetectorimport

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Swartz_HardwareMap
{
    /* Public Sensors */
    public WGWIMU2018 imu;
    public BNO055IMU wgwIMU2018        = null;

    /* Public Motors */
    public DcMotor  frontLeftDrive         = null;
    public DcMotor  frontRightDrive        = null;
    public DcMotor  backLeftDrive          = null;
    public DcMotor  backRightDrive         = null;
    public DcMotor  LiftMotor              = null;
    public DcMotor  ExtendMotor            = null;

    /* Public Servos */
    public Servo OpenServo  = null;
    public Servo TwistServo = null;
    public Servo DragServo = null;

    //public sensors
    public AnalogInput armPosInput=null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Swartz_HardwareMap(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        wgwIMU2018 = hwMap.get(BNO055IMU.class, "imu");
        imu = new WGWIMU2018(wgwIMU2018);

        // Define and Initialize Motors
        frontLeftDrive    = hwMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive   = hwMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive     = hwMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive    = hwMap.get(DcMotor.class, "backRightDrive");
        LiftMotor         = hwMap.get(DcMotor.class, "LiftMotor");
        ExtendMotor       = hwMap.get(DcMotor.class, "ExtendMotor");

        OpenServo         = hwMap.get(Servo.class, "OpenServo");
        TwistServo        = hwMap.get(Servo.class, "TwistServo");
        DragServo         = hwMap.get(Servo.class, "DragServo");

        armPosInput       = hwMap.analogInput.get("armPos");

        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to FORWARD if using AndyMark motors
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE if using AndyMark motors
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD); //  Set to FORWARD if using AndyMark motors
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);//  Set to FORWARD if using AndyMark motors

        frontLeftDrive.setZeroPowerBehavior (DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior (DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior (DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior (DcMotor.ZeroPowerBehavior.BRAKE);


        ExtendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        // Set all motors to zero power
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        ExtendMotor.setPower(0);
        LiftMotor.setPower(0);
        TwistServo.setPosition(0.5);
        OpenServo.setPosition(0.6);
        DragServo.setPosition (0);


        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ExtendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}

