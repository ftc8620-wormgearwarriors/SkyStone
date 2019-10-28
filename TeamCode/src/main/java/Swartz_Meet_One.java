
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

        drive(95,100);
        SkyStoneDetection();

    }
}



