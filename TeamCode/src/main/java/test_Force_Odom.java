import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.RobotLog;


/**
* Created by Sarthak on 10/4/2019.
*/
@Autonomous(name = "Test Odom")
public class test_Force_Odom extends force_SkyStoneAutonomousMethods {


   @Override
   public void runOpMode() throws InterruptedException {
       //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
       Init();
       // initOdometryHardware(144,39,270);
       initOdometryHardware(0,0,0);


       telemetry.addData("Status", "Init Complete");
       telemetry.update();
       waitForStart();



       // big drive straigh
//       for (int i = 0; i < 5; i++) {
//           goToPostion(0 * robot.COUNTS_PER_INCH, 96 * robot.COUNTS_PER_INCH, 1, 0, 1 * robot.COUNTS_PER_INCH, false);
//           sendPositionTelemetry(1, 2);
//           goToPostion(0 * robot.COUNTS_PER_INCH, 0 * robot.COUNTS_PER_INCH, 1, 0, 1 * robot.COUNTS_PER_INCH, false);
//           sendPositionTelemetry(1, 2);
//       }



       // big drive straigh
       goToPostion(0*robot.COUNTS_PER_INCH,48*robot.COUNTS_PER_INCH,1,0,1*robot.COUNTS_PER_INCH,false);
       sendPositionTelemetry(5,5);
       goToPostion(0*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,1*robot.COUNTS_PER_INCH,false);
       sendPositionTelemetry(5,5 );

       // small drive straight, higher accuracy
       goToPostion(0*robot.COUNTS_PER_INCH,2*robot.COUNTS_PER_INCH,1,0,0.25*robot.COUNTS_PER_INCH,false);
       sendPositionTelemetry(5,5 );
       goToPostion(0*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,0.25*robot.COUNTS_PER_INCH,false);
       sendPositionTelemetry(5,5 );

       // big Straffe
       goToPostion(48*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,1*robot.COUNTS_PER_INCH,false);
       sendPositionTelemetry(5,5);
       goToPostion(0*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,1*robot.COUNTS_PER_INCH,false);
       sendPositionTelemetry(5,5 );

       // small Straffe, higher accuracy
       goToPostion(2*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,0.25*robot.COUNTS_PER_INCH,false);
       sendPositionTelemetry(5,5 );
       goToPostion(0*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,0.25*robot.COUNTS_PER_INCH,false);
       sendPositionTelemetry(5,5 );

       // small turn
       goToPostion(0*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,5,1000*robot.COUNTS_PER_INCH,true);
       sendPositionTelemetry(5,5);
       goToPostion(0*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,1000*robot.COUNTS_PER_INCH,true);
       sendPositionTelemetry(5,5);

       // Big turn
       goToPostion(0*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,90,1000*robot.COUNTS_PER_INCH,true);
       sendPositionTelemetry(5,5);
       goToPostion(0*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,1000*robot.COUNTS_PER_INCH,true);
       sendPositionTelemetry(5,5);
         //goToPostion(0 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //sleep(5000);
         //goToPostion( 24 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
         //goToPostion(0 *COUNTS_PER_INCH,0*COUNTS_PER_INCH, 0.5, 0, 1*COUNTS_PER_INCH);


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
    void sendPositionTelemetry (int a, int b) {
       sleep(1000 * a);
        telemetry.addData("X Position", robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH);
        telemetry.addData("Y Position", robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH);
        telemetry.addData("Orientation (Degrees)", robot.globalPositionUpdate.returnOrientation());
        telemetry.update();
        RobotLog.d(" 8620WGW sendPositionTelemetry, Current location = %.04f, %.04f,%.04f", robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH, robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH, robot.globalPositionUpdate.returnOrientation()); // ouput headings for what will be logged below
        sleep(1000 * b);
    }
}

