
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Straight_Swartz")
public class Swartz_Test_auto extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        Init();     //  init robot hardware

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        drive(61,1);
        strafe(61,1);
        rotate(90,.5);
    }
}



