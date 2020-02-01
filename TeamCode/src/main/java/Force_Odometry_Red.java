import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.RobotLog;


/**
* Created by Sarthak on 10/4/2019.
*/
@Autonomous(name = "Force_Odometry_Red")
public class Force_Odometry_Red extends force_SkyStoneAutonomousMethods {


   @Override
   public void runOpMode() throws InterruptedException {
       /*
       *Initialize hardware map values and where the claw.
       */
       Init();
       //this is where on the field the robot starts
       initOdometryHardware(135,111,90);
       robot.rightStoneGrabberUpDown.setPosition(0);
       robot.rightStoneGrabberOpenClose.setPosition(0);

        // tells us when the robot is done initializing
       telemetry.addData("Status", "Init Complete");
       telemetry.update();
       waitForStart();

       robot.rightStoneGrabberOpenClose.setPosition(1.0);
       goToPostion(104 * robot.COUNTS_PER_INCH,
                   125 * robot.COUNTS_PER_INCH,
                   .8, 90,
                   1 * robot.COUNTS_PER_INCH, false);
       robot.RightWaffle.setPosition(0); //closes the right waffle servor to grab the platform
       robot.LeftWaffle.setPosition(0);  //closes the left waffle servor to grab the platform
       sleep(1000);          //gives both of the waffle
       goToPostion(135 * robot.COUNTS_PER_INCH,
                   130 * robot.COUNTS_PER_INCH,.8, 90,
                   1 * robot.COUNTS_PER_INCH, false);
       sleep(1000);
       robot.RightWaffle.setPosition(1); //opens the right waffle servor to grab the platform
       robot.LeftWaffle.setPosition(1); //opens the left waffle servor to grab the platform
       goToPostion(135 * robot.COUNTS_PER_INCH,
                   72 * robot.COUNTS_PER_INCH,.8, 90,
                   1 * robot.COUNTS_PER_INCH, false);
       //displays the robots x position
       telemetry.addData("x Position",
                                robot.globalPositionUpdate.returnXCoordinate() /
                                        robot.COUNTS_PER_INCH);
       telemetry.update();


       while(opModeIsActive()){
           //Display Global (x, y, theta) coordinates
           telemetry.addData("X Position",
                   robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH);
           telemetry.addData("Y Position",
                   robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH);
           telemetry.addData("Orientation (Degrees)",
                         robot.globalPositionUpdate.returnOrientation());

           telemetry.addData("Vertical left encoder position",
                   robot.verticalLeft.getCurrentPosition());
           telemetry.addData("Vertical right encoder position",
                   robot.verticalRight.getCurrentPosition());
           telemetry.addData("horizontal encoder position",
                   robot.horizontal.getCurrentPosition());

           telemetry.addData("Thread Active", robot.positionThread.isAlive());
           telemetry.update();
       }

       //Stop the thread
       robot.globalPositionUpdate.stop();

   }

}

