import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.RobotLog;


/**
* Created by Sarthak on 10/4/2019.
*/
@TeleOp(name = "Force_Odometry")
public class Force_Odometry extends force_SkyStoneAutonomousMethods {


   @Override
   public void runOpMode() throws InterruptedException {
       //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
       Init();
       initOdometryHardware();


       telemetry.addData("Status", "Init Complete");
       telemetry.update();
       waitForStart();

//goToPostion(48*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,1*robot.COUNTS_PER_INCH,false);
         //goToPostion(0 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //sleep(5000);
         //goToPostion( 24 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //goToPostion(0 *COUNTS_PER_INCH,0*COUNTS_PER_INCH, 0.5, 0, 1*COUNTS_PER_INCH);

       goToPostion(104 * robot.COUNTS_PER_INCH, 125 * robot.COUNTS_PER_INCH,.8, 90, 1 * robot.COUNTS_PER_INCH, false);
       robot.RightWaffle.setPosition(0);
       robot.LeftWaffle.setPosition(0);
       sleep(1000);
       goToPostion(104*robot.COUNTS_PER_INCH, 120* robot.COUNTS_PER_INCH,1,180,100* robot.COUNTS_PER_INCH,true);
       goToPostion(135 * robot.COUNTS_PER_INCH, 111 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       robot.RightWaffle.setPosition(0);
       robot.LeftWaffle.setPosition(0);
       sleep(1000);
       goToPostion(135 * robot.COUNTS_PER_INCH, 72 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       //goToPostion(24 * COUNTS_PER_INCH, 0 * COUNTS_PER_INCH,.8, 0, 1 * COUNTS_PER_INCH, false);
            telemetry.addData("x Position", robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH);
            telemetry.update();


       while(opModeIsActive()){
           //Display Global (x, y, theta) coordinates
           telemetry.addData("X Position", robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH);
           telemetry.addData("Y Position", robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH);
           telemetry.addData("Orientation (Degrees)", robot.globalPositionUpdate.returnOrientation());

           telemetry.addData("Vertical left encoder position", robot.verticalLeft.getCurrentPosition());
           telemetry.addData("Vertical right encoder position", robot.verticalRight.getCurrentPosition());
           telemetry.addData("horizontal encoder position", robot.horizontal.getCurrentPosition());

           telemetry.addData("Thread Active", robot.positionThread.isAlive());
           telemetry.update();
       }

       //Stop the thread
       robot.globalPositionUpdate.stop();

   }

}

