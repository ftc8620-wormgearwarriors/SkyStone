import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


/**
* Created by Sarthak on 10/4/2019.
*/
@Autonomous(name = "Force_Odometry_Blue")
public class Force_Odometry_Blue extends force_SkyStoneAutonomousMethods {


   @Override
   public void runOpMode() throws InterruptedException {
       //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
       Init();
       initOdometryHardware(0,111,270);


       telemetry.addData("Status", "Init Complete");
       telemetry.update();
       waitForStart();

//goToPostion(48*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,1*robot.COUNTS_PER_INCH,false);
         //goToPostion(0 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //sleep(5000);
         //goToPostion( 24 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //goToPostion(0 *COUNTS_PER_INCH,0*COUNTS_PER_INCH, 0.5, 0, 1*COUNTS_PER_INCH);

       goToPostion(32 * robot.COUNTS_PER_INCH, 125 * robot.COUNTS_PER_INCH,.8, 270, 1 * robot.COUNTS_PER_INCH, false);
       robot.RightWaffle.setPosition(0);
       robot.LeftWaffle.setPosition(0);
       sleep(1000);
       //goToPostion(104*robot.COUNTS_PER_INCH, 120* robot.COUNTS_PER_INCH,1,180,100* robot.COUNTS_PER_INCH,true);
       goToPostion(0 * robot.COUNTS_PER_INCH, 130 * robot.COUNTS_PER_INCH,.8, 270, 1 * robot.COUNTS_PER_INCH, false);
       sleep(1000);
       robot.RightWaffle.setPosition(1);
       robot.LeftWaffle.setPosition(1);
       goToPostion(0 * robot.COUNTS_PER_INCH, 72 * robot.COUNTS_PER_INCH,.8, 270, 1 * robot.COUNTS_PER_INCH, false);
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

