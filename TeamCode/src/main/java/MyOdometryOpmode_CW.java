import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.RobotLog;

import javax.microedition.khronos.opengles.GL;

//import org.firstinspires.ftc.teamcode.Robot.Drivetrain.Odometry.OdometryGlobalCoordinatePosition;

/**
 * Created by Sarthak on 10/4/2019.
 */
@TeleOp(name = "CW Odometry OpMode")
public class MyOdometryOpmode_CW extends LinearOpMode {
    //Drive motors
    DcMotor right_front, right_back, left_front, left_back;
    //Odometry Wheels
    DcMotor verticalLeft, verticalRight, horizontal;

    final double COUNTS_PER_INCH = 1714;

    //Hardware Map Names for drive motors and odometry wheels. THIS WILL CHANGE ON EACH ROBOT, YOU NEED TO UPDATE THESE VALUES ACCORDINGLY
    String rfName = "rf", rbName = "rb", lfName = "lf", lbName = "lb";
    String verticalLeftEncoderName = "vle", verticalRightEncoderName = "vre", horizontalEncoderName = "he";

    OdometryGlobalCoordinatePosition globalPositionUpdate;

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        initDriveHardwareMap(rfName, rbName, lfName, lbName, verticalLeftEncoderName, verticalRightEncoderName, horizontalEncoderName);

        telemetry.addData("Status", "Init Complete");
        telemetry.update();
        waitForStart();

        //Create and start GlobalCoordinatePosition thread to constantly update the global coordinate positions
        globalPositionUpdate = new OdometryGlobalCoordinatePosition(verticalLeft, verticalRight, horizontal, COUNTS_PER_INCH, 75);
        Thread positionThread = new Thread(globalPositionUpdate);
        positionThread.start();

        globalPositionUpdate.reverseLeftEncoder();
        //globalPositionUpdate.reverseRightEncoder();
        //globalPositionUpdate.reverseNormalEncoder();

//        goToPosition(24, 24, 0.5, 0, 1);
//        sleep(2000);
//        goToPosition(0, 0, 0.5, 90, 1);
//        sleep(2000);
//        goToPosition(24, 24, 0.5, 270, 1);
//        sleep(2000);
//        goToPosition(0, 0, 0.5, 0, 1);

        // to 6 stones left position
        goToPosition(10, 43, 0.75, 270, 1);
        sleep(2000);
        RobotLog.d("8620WGW main after sleep  X="+ globalPositionUpdate.returnXCoordinate()/COUNTS_PER_INCH + "  Y="+ globalPositionUpdate.returnYCoordinate()/COUNTS_PER_INCH + "  angle=" + globalPositionUpdate.returnOrientation());

        // back tl lign up to go under sky bridge
        goToPosition(10, 22, 0.75, 270, 1);
        sleep(2000);
        RobotLog.d("8620WGW main after sleep  X="+ globalPositionUpdate.returnXCoordinate()/COUNTS_PER_INCH + "  Y="+ globalPositionUpdate.returnYCoordinate()/COUNTS_PER_INCH + "  angle=" + globalPositionUpdate.returnOrientation());

        //  drive under sky bridge
        goToPosition(50, 22, 0.75, 270, 1);
        sleep(2000);
        RobotLog.d("8620WGW main after sleep  X="+ globalPositionUpdate.returnXCoordinate()/COUNTS_PER_INCH + "  Y="+ globalPositionUpdate.returnYCoordinate()/COUNTS_PER_INCH + "  angle=" + globalPositionUpdate.returnOrientation());

        // in front of waffle
        goToPosition(52, 50, 0.75, 270, 1);
        sleep(2000);
        RobotLog.d("8620WGW main after sleep  X="+ globalPositionUpdate.returnXCoordinate()/COUNTS_PER_INCH + "  Y="+ globalPositionUpdate.returnYCoordinate()/COUNTS_PER_INCH + "  angle=" + globalPositionUpdate.returnOrientation());


        while(opModeIsActive()){
            //Display Global (x, y, theta) coordinates
            telemetry.addData("X Position", globalPositionUpdate.returnXCoordinate() / COUNTS_PER_INCH);
            telemetry.addData("Y Position", globalPositionUpdate.returnYCoordinate() / COUNTS_PER_INCH);
            telemetry.addData("Orientation (Degrees)", globalPositionUpdate.returnOrientation());

            telemetry.addData("Vertical left encoder position", verticalLeft.getCurrentPosition());
            telemetry.addData("Vertical right encoder position", verticalRight.getCurrentPosition());
            telemetry.addData("horizontal encoder position", horizontal.getCurrentPosition());

            telemetry.addData("Thread Active", positionThread.isAlive());
            telemetry.update();
        }

        //Stop the thread
        globalPositionUpdate.stop();

    }

    public void goToPosition(double targetXPosition, double targetYPosition, double robotPower, double desiredRobotOrientation, double allowableDistanceError ){

        RobotLog.d("8620WGW goToPosition begin.  TargetX="+targetXPosition+" TargetY="+targetYPosition+" rotation="+desiredRobotOrientation);
        double distanceToXTarget = (targetXPosition * COUNTS_PER_INCH) - globalPositionUpdate.returnXCoordinate();
        double distanceToYTarget = (targetYPosition * COUNTS_PER_INCH) - globalPositionUpdate.returnYCoordinate();
        double distance = Math.hypot(distanceToXTarget, distanceToYTarget);

        while (opModeIsActive() &&  (distance > (allowableDistanceError*COUNTS_PER_INCH)) ) {
            distanceToXTarget = (targetXPosition * COUNTS_PER_INCH) - globalPositionUpdate.returnXCoordinate();
            distanceToYTarget = (targetYPosition * COUNTS_PER_INCH) - globalPositionUpdate.returnYCoordinate();
            distance = Math.hypot(distanceToXTarget, distanceToYTarget);

            double robotMovementAngle = Math.toDegrees(Math.atan2(distanceToXTarget, distanceToYTarget) - (globalPositionUpdate.returnOrientation()*Math.PI/180));

            double robot_movement_x_component = calculateX(robotMovementAngle, robotPower * 1.5) ; // multiplier for slip when straffin
            double robot_movement_y_component = calculateY(robotMovementAngle, robotPower);

            double pivotCorrection = angleErrorDrive(globalPositionUpdate.returnOrientation(), desiredRobotOrientation);   //desiredRobotOrientation - globalPositionUpdate.returnOrientation();
            double pivotPower = (pivotCorrection / 10) ;  // how many degress of error can cause full power to be applied?
            double MaxPivotPower = 0.75;                     // limit the max input for turning.
            if (Math.abs(pivotPower) > MaxPivotPower) {
                pivotPower = MaxPivotPower * Math.signum(pivotPower); // Limit it, but keep the sign correct
            }

            // calculate the motor power, x component = straffing.  Y component = drive forwared.  Pivot = turn
            double frontLeftPower =     robot_movement_y_component + robot_movement_x_component + pivotPower;
            double frontRightPower =    robot_movement_y_component - robot_movement_x_component - pivotPower;
            double backLeftPower =      robot_movement_y_component - robot_movement_x_component + pivotPower;
            double backRightPower =     robot_movement_y_component + robot_movement_x_component - pivotPower;

            // limit the maximium to our power limit so we don't overdrive any motor beyond the allowed speed.
            double max = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower)),
                        Math.max(Math.abs(frontRightPower), Math.abs(backRightPower)));
                if (max > robotPower) {
                    double divider = max / robotPower;
                    frontLeftPower  /= divider;
                    frontRightPower /= divider;
                    backLeftPower   /= divider;
                    backRightPower  /= divider;
            }

            // finally set the motor powers!
            left_front.setPower (frontLeftPower);
            right_front.setPower(frontRightPower);
            left_back.setPower(backLeftPower);
            right_back.setPower(backRightPower);

            //RobotLog.d("8620WGW  X="+ globalPositionUpdate.returnXCoordinate()/COUNTS_PER_INCH + "  Y="+ globalPositionUpdate.returnYCoordinate()/COUNTS_PER_INCH + "  angle=" + globalPositionUpdate.returnOrientation() + "  X-comp=" + robot_movement_x_component + "  Y-comp=" + robot_movement_y_component);
        }
        // done so turn off the motors now!!!
        left_front.setPower (0);
        right_front.setPower(0);
        left_back.setPower(0);
        right_back.setPower(0);
        RobotLog.d("8620WGW goToPosition complete  X="+ globalPositionUpdate.returnXCoordinate()/COUNTS_PER_INCH + "  Y="+ globalPositionUpdate.returnYCoordinate()/COUNTS_PER_INCH + "  angle=" + globalPositionUpdate.returnOrientation() + "    distance error="+ distance +"  distance tolerance=" + (allowableDistanceError*COUNTS_PER_INCH));
    }

    private void initDriveHardwareMap(String rfName, String rbName, String lfName, String lbName, String vlEncoderName, String vrEncoderName, String hEncoderName){
        right_front = hardwareMap.dcMotor.get(rfName);
        right_back = hardwareMap.dcMotor.get(rbName);
        left_front = hardwareMap.dcMotor.get(lfName);
        left_back = hardwareMap.dcMotor.get(lbName);

        verticalLeft = hardwareMap.dcMotor.get(vlEncoderName);
        verticalRight = hardwareMap.dcMotor.get(vrEncoderName);
        horizontal = hardwareMap.dcMotor.get(hEncoderName);

        right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        verticalLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        verticalLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        horizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        right_front.setDirection(DcMotorSimple.Direction.REVERSE);
        right_back.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Hardware Map Init Complete");
        telemetry.update();
    }

    /**
     * Calculate the power in the x direction
     * @param desiredAngle angle on the x axis
     * @param speed robot's speed
     * @return the x vector
     */
    private double calculateX(double desiredAngle, double speed) {
        return Math.sin(Math.toRadians(desiredAngle)) * speed;
    }

    /**
     * Calculate the power in the y direction
     * @param desiredAngle angle on the y axis
     * @param speed robot's speed
     * @return the y vector
     */
    private double calculateY(double desiredAngle, double speed) {
        return Math.cos(Math.toRadians(desiredAngle)) * speed;
    }

    /**
     * Created by Worm Gear Warriors on 10/28/2018.
     * returns an angle between -180 and +180
     */
    public double angleErrorDrive(double angleTarget, double angleInitial) {
        double error = angleInitial - angleTarget;

        while (error <= -180 || error > 180) {
            if (error > 180) {
                error = error - 360;
            }
            if (error <= -180) {
                error = error + 360;
            }
        }
        return error;
    }

}
