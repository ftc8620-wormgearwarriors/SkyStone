
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

        /*armTilt(1,0.25);
        sleep(2000);
        armTilt(1.302,0.25);
        sleep(2000);
        armTilt(1.245,0.25);
        sleep(2000);
        armExt(4400,0.25);
        sleep(2000);
        armExt(1000,0.25);
        sleep(2000);

        drive(61,1);
        strafe(61,1);
        rotate(90,.5); */
        grabBlock();
    }
}



