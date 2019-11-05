
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Swartz_MeetOne -")
public class Swartz_Meet_One extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        Init();     //  init robot hardware

        VuforiaInit(); //init camera
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        drive(50,1);
        int StoneRember = SkyStoneDetection();

        switch (StoneRember) {
            case 0: //SkyStone is off Screen
              strafe(15,1);
                telemetry.addLine("OffScreen");
                break;
            case 1: //SkyStone is on the left
                telemetry.addLine(":Left");
                strafe(10,-1);
                break;
            case 2:  //SkyStone is on the right
                //strafe(25,-0.5); //Strafe over to the skystone
                telemetry.addLine("Right");
                strafe(30,-1);
                //strafe(60,-1);
                break;
        }
        grabBlock();
        armTilt(1.24,1);
        armExt(2000,1);

        telemetry.update();
    }
}



