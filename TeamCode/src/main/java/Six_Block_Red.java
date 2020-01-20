import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


/**
* Created by Sarthak on 10/4/2019.
*/
@Autonomous(name = "Six_Block_Red")
public class Six_Block_Red extends force_SkyStoneAutonomousMethods {


   @Override
   public void runOpMode() throws InterruptedException {
       //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
       Init();
       initOdometryHardware(144,39,270);


       telemetry.addData("Status", "Init Complete");
       telemetry.update();
       waitForStart();

//goToPostion(48*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,1*robot.COUNTS_PER_INCH,false);
         //goToPostion(0 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //sleep(5000);
         //goToPostion( 24 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //goToPostion(0 *COUNTS_PER_INCH,0*COUNTS_PER_INCH, 0.5, 0, 1*COUNTS_PER_INCH);

       goToPostion(115* robot.COUNTS_PER_INCH, 49 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       sleep(1000);
       goToPostion(125 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       sleep(1000);
       goToPostion(125 * robot.COUNTS_PER_INCH, 73 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(115 * robot.COUNTS_PER_INCH, 44 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       sleep(1000);
       goToPostion(125 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       sleep(1000);
       goToPostion(125 * robot.COUNTS_PER_INCH, 73 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(115 * robot.COUNTS_PER_INCH, 35 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(125 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       sleep(1000);
       goToPostion(125 * robot.COUNTS_PER_INCH, 73 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(115 * robot.COUNTS_PER_INCH, 23 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       sleep(1000);
       goToPostion(125 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       sleep(1000);
       goToPostion(125 * robot.COUNTS_PER_INCH, 73 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       /*goToPostion(100 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(87 * robot.COUNTS_PER_INCH, 27 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(100 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(87 * robot.COUNTS_PER_INCH, 20 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(100 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(87 * robot.COUNTS_PER_INCH, 12 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(100 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
       goToPostion(87 * robot.COUNTS_PER_INCH, 3 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
*/
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

