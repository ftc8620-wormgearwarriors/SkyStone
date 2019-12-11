
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.DbgLog;

@Autonomous (name = "Test_Auto blue")
public class Test_Auto_Blue extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        //  init robot hardware
        Init();

        //init camera
        init_vuforia_2();
        armTilt(0.997,0.8);         //tilt up to level so the claw clears the base

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //Identifies placement of skystone with vision code
        double frontDistance = 15.0;
        double frontGap = 0.0;
        double frontVelocity = 1.0;


        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(true, false);  // look for skystone
        switch (StoneRember) {
            case LEFT: //SkyStone is left
                frontGap = 79.0;
                telemetry.addLine("LEFT");
                break;
            case CENTER: //SkyStone is on center
                frontGap = 59.0;
                telemetry.addLine(":CENTER");
                break;
            case RIGHT:  //SkyStone is on the right
                frontGap = 41.0;
                telemetry.addLine("Right");
                break;
        }
        telemetry.update();


        //Drives forward to skystone
        frontgap(frontDistance, frontVelocity, frontGap, sensorSide.RIGHT);


        //Extend arm and grab stone and put arm in position to go under bridge
        grabBlock();
        armExt(2000, 1);

        // Turning to drive under bridge
        rotate(275, -1);

        // Driving under bridge depending on skystone position
        switch (StoneRember) {
            //KEY:  1: drive under bridge with gap
            case LEFT:// Left position
                gap(110, 1, 53, sensorSide.LEFT); //1
                break;
            case CENTER:// center position
                gap(130, 1, 53, sensorSide.LEFT); //1
                 break;
            case RIGHT:// right position
                gap(150, 1, 53, sensorSide.LEFT); //1
                break;
        }

        sleep(1000); //test-remove later
        //drive to waffle
        frontgap(53, 1, 69, sensorSide.LEFT);
        sleep(1000); //test-remove later

        // lines up on block
        armTilt(1.12, 1);
        strafe(70, -1); // lines up on block
        // drive to place block

        // extending arm and dropping block
        armExt(2800, 1);
        robot.OpenServo.setPosition(0);
        // tilt to clear skystone
        armTilt(.98, 0.8);

        // backs off waffle
        drive(10, -1);
        //rotates to align the waffle
        rotate(90, 1);
        //grabbers on waffle
        robot.RightWaffle.setPosition(0.5);
        robot.LeftWaffle.setPosition(0.5);
        //strafes onto waffle
        strafe(15, 1);
        //grabs waffle
        robot.RightWaffle.setPosition(0);
        robot.LeftWaffle.setPosition(1);
        sleep(400);
        //strafe waffle into build site
        strafe(92, -1);
        telemetry.update();
    }
}



