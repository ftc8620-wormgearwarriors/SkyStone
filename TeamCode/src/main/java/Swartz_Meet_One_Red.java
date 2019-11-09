
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Swartz_MeetOne_Red -")
public class Swartz_Meet_One_Red extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        Init();     //  init robot hardware

        VuforiaInit(); //init camera


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        drive(50,1);            // Drive to be in range to see skystone

        int StoneRember = SkyStoneDetection();  // look for skystone

        switch (StoneRember) {
            case 0: //SkyStone is off Screen
              strafe(15,1);
                telemetry.addLine("OffScreen");
                break;
            case 1: //SkyStone is on the left
                telemetry.addLine(":Left");
                strafe(8,-1);
                break;
            case 2:  //SkyStone is on the right
                //strafe(25,-0.5); //Strafe over to the skystone
                telemetry.addLine("Right");
                strafe(30,-1);
                //strafe(60,-1);
                break;
        }
        grabBlock();
        armExt(2000,1);

        telemetry.update();

        drive(16,  -1);     //Drive Back

        switch (StoneRember) {
            case 2: //SkyStone is on the right
                strafe(100, -1);  //Sideways Under Bridge

                robot.OpenServo.setPosition(0.36);  // Open Claw
                sleep(150);     //way to big was 7 00     // Sleeping to drop block
                strafe(171, 1);  // Going Back to grab another block
                drive(20, 1);   // Going forward to grab block //was 16

                grabBlock();

                drive(18, -1);
                armExt(2000, 1);
                strafe(181, -1); //was 171
                robot.OpenServo.setPosition(0.36);
                break;
            case 1: //SkyStone is on the left
                strafe(120, -1);  //Sideways Under Bridge

                robot.OpenServo.setPosition(0.36);  // Open Claw
                sleep(400);     //way to big was 7 00     // Sleeping to drop block
                strafe(190, 1);  // Going Back to grab another block
                drive(18, 1);   // Going forward to grab block

                grabBlock();

                drive(18, -1);
                armExt(2000, 1);
                strafe(200, -1);
            break;
            case 0: //SkyStone is on the offscreen
                strafe(145, -1);  //Sideways Under Bridge

                robot.OpenServo.setPosition(0.36);  // Open Claw
                sleep(400);     //way to big was 7 00     // Sleeping to drop block
                strafe(240, 1);  // Going Back to grab another block
                drive(16,    -1);   // Going forward to grab block

                grabBlock();

                drive(18, -1);
                armExt(2000, 1);
                strafe(220, -1);
            break;
        }
        }
}



