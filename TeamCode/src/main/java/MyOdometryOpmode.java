import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.RobotLog;


/**
* Created by Sarthak on 10/4/2019.
*/
@TeleOp(name = "My Odometry OpMode")
public class MyOdometryOpmode extends LinearOpMode {
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

       //globalPositionUpdate.reverseRightEncoder();
       //globalPositionUpdate.reverseNormalEncoder();
         globalPositionUpdate.reverseLeftEncoder();

         //goToPostion(0 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //sleep(5000);
         //goToPostion( 24 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //goToPostion(0 *COUNTS_PER_INCH,0*COUNTS_PER_INCH, 0.5, 0, 1*COUNTS_PER_INCH);
         goToPostion(0 * COUNTS_PER_INCH, 0 * COUNTS_PER_INCH,.4, 90, 1 * COUNTS_PER_INCH, true);

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

   public void goToPostion(double targetXPostion, double targetYPosition, double robotPower, double desiredRobotOrientation, double allowableDistanceError, boolean pivot) {

       double distanceToXTarget = targetXPostion - globalPositionUpdate.returnXCoordinate();
       double distanceToYTarget = targetYPosition - globalPositionUpdate.returnYCoordinate();

       double distance = Math.hypot(distanceToXTarget, distanceToYTarget);

       double angleError = angleError180(globalPositionUpdate.returnOrientation(),desiredRobotOrientation);
       PIDController           pidRotate;
       // Set PID proportional value to start reducing power at about 50 degrees of rotation.
       // P by itself may stall before turn completed so we add a bit of I (integral) which
       // causes the PID controller to gently increase power if the turn is not completed.
       pidRotate = new PIDController(.003, .00003, 0);
       // start pid controller. PID controller will monitor the turn angle with respect to the
       // target angle and reduce power as we approach the target angle. This is to prevent the
       // robots momentum from overshooting the turn after we turn off the power. The PID controller
       // reports onTarget() = true when the difference between turn angle and target angle is within
       // 1% of target (tolerance) which is about 1 degree. This helps prevent overshoot. Overshoot is
       // dependant on the motor and gearing configuration, starting power, weight of the robot and the
       // on target tolerance. If the controller overshoots, it will reverse the sign of the output
       // turning the robot back toward the setpoint value.

       pidRotate.reset();
       pidRotate.setSetpoint(desiredRobotOrientation);
       pidRotate.setInputRange(globalPositionUpdate.returnOrientation(), desiredRobotOrientation);
       pidRotate.setOutputRange(0, robotPower);
       pidRotate.setTolerance(1);
       pidRotate.enable();

       double pivotCorrection = pidRotate.performPID(globalPositionUpdate.returnOrientation()); // power will be - on right turn.

       while (opModeIsActive()&& (distance > allowableDistanceError || !pidRotate.onTarget())) {

           angleError = angleError180(globalPositionUpdate.returnOrientation(),desiredRobotOrientation);

           distanceToXTarget = targetXPostion - globalPositionUpdate.returnXCoordinate();  // distance to X target
           distanceToYTarget = targetYPosition - globalPositionUpdate.returnYCoordinate(); // distance to Y target

           distance = Math.hypot(distanceToXTarget,distanceToYTarget); // calculate offset distance

           double robotMovementAngle = Math.toDegrees(Math.atan2(distanceToXTarget, distanceToYTarget)) - globalPositionUpdate.returnOrientation(); // angle robot is moving

           double robot_movement_x_component = calculateX(robotMovementAngle, robotPower * 1.5); // calcuate how much strafe and drive needed to get to target
           double robot_movement_y_component = calculateY(robotMovementAngle, robotPower);
           //double pivotCorrection = (desiredRobotOrientation - globalPositionUpdate.returnOrientation()) / 20; // keep robot facing right way

           if (pivot) {
               robot_movement_x_component = 0;
               robot_movement_y_component = 0;
           }

           pivotCorrection = pidRotate.performPID(globalPositionUpdate.returnOrientation()); // power will be - on right turn.

           double frontLeftPower =  robot_movement_y_component + robot_movement_x_component + pivotCorrection;
           double frontRightPower = robot_movement_y_component - robot_movement_x_component - pivotCorrection;
           double backLeftPower =   robot_movement_y_component - robot_movement_x_component + pivotCorrection;
           double backRightPower =  robot_movement_y_component + robot_movement_x_component - pivotCorrection;

           double max = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower)),
                   Math.max(Math.abs(frontRightPower), Math.abs(backRightPower)));
           if (max > robotPower) {
               double divider = max / robotPower;
               frontLeftPower /= divider;
               frontRightPower /= divider;
               backLeftPower /= divider;
               backRightPower /= divider;
           }
           right_front.setPower(frontRightPower);
           right_back.setPower(backRightPower);
           left_front.setPower(frontLeftPower);
           left_back.setPower(backLeftPower);
           RobotLog.d("8620WGW goToPosition x ="+globalPositionUpdate.returnXCoordinate ()+"  y =" + globalPositionUpdate.returnYCoordinate()+ "  angle ="+ globalPositionUpdate.returnOrientation());
       }
       right_front.setPower(0);
       left_front.setPower(0);
       right_back.setPower(0);
       left_back.setPower(0);
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
    public double angleError180(double angleTarget, double angleInitial) {
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

