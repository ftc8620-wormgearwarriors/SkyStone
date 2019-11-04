
import android.widget.Switch;

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
//
        switch (StoneRember) {
            case 0: //SkyStone is off Screen
                strafe(56,-1); //Strafe over to the skystone
                //robot.ExtendMotor.setPower(1); //extend the motor
                sleep(4000); //Allows the arm to extend
                //strafe(70,1); //Strafe so all three cases meet up
                telemetry.addLine("OffScreen");
                break;
            case 1: //SkyStone is on the left
                strafe(30,-1); //Strafe over to the skystone
                //robot.ExtendMotor.setPower(1); //extend the motor
                sleep(4000); //Allows the arm to extend
                //strafe(14,1);
                telemetry.addLine(":Left");
                break;
            case 2:  //SkyStone is on the right
                strafe(14,1); //Strafe over to the skystone
                // robot.ExtendMotor.setPower(1); //extend the motor
                sleep(4000); //Allows the arm to extend
                telemetry.addLine("Right");
                break;
        }
        telemetry.update();
    }
}



