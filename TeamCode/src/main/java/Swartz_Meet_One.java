
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Swartz_MeetOne -")
public class Swartz_Meet_One extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        Init();     //  init robot hardware

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        drive(50,1);
        int StoneRember = SkyStoneDetection();

        switch (StoneRember) {
            case 0: //SkyStone is off Screen
                rotate(270,-1);  // figure out the straffe
                drive(20,1);
                rotate(360,-1);
                telemetry.addLine("OffScreen");
                break;
            case 1: //SkyStone is on the left
                //strafe(30,1); //Strafe over to the skystone
                telemetry.addLine(":Left");
                break;
            case 2:  //SkyStone is on the right
                //strafe(25,-0.5); //Strafe over to the skystone
                rotate(90,1);
                drive(20,1);
                rotate(0,-1);
                telemetry.addLine("Right");
                break;
        }
        telemetry.update();
    }
}



