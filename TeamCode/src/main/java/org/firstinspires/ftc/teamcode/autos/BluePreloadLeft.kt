package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Chainbar
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Output
import org.firstinspires.ftc.teamcode.mechanisms.Slide
import org.firstinspires.ftc.teamcode.utils.Robot
import org.firstinspires.ftc.teamcode.utils.vision.OpenCv
import org.firstinspires.ftc.teamcode.utils.Pose
import org.firstinspires.ftc.teamcode.utils.Time
import org.firstinspires.ftc.teamcode.utils.command.Command
import org.firstinspires.ftc.teamcode.utils.command.CommandGroup
import org.firstinspires.ftc.teamcode.utils.command.Scheduler
import org.firstinspires.ftc.teamcode.utils.command.continuousCommand
import org.firstinspires.ftc.teamcode.utils.command.timedCommand
import org.firstinspires.ftc.teamcode.utils.vision.VisionConstants

@Autonomous(name = "BluePreloadLeft")
class BluePreloadLeft : LinearOpMode() {
    override fun runOpMode() {
        Scheduler.clear() //Clears all commands from the scheduler to allow a new OpMode to run
        //Object declarations
        val robot = Robot(this)
        val drivetrain = Drivetrain(hardwareMap, robot)
        val intake = Intake(hardwareMap)
        val output = Output(hardwareMap)
        val slides = Slide(hardwareMap)
        val chainbar = Chainbar(hardwareMap)
        val arm = Arm(hardwareMap)
        val openCv = OpenCv(hardwareMap.get(WebcamName::class.java, "Webcam 1"),
            hashMapOf("Blue" to VisionConstants.BLUE))

        //Sets the robot's starting position
        robot.pose = Pose(2121.0, 286.0, 180.0)

        //Creates potential scoring positions for the purple pixel on the spike marks
        val leftSpikeScore = Pose(2121.8, 700.0, 180.0)
        val middleSpikeScore = Pose(2121.8, 1000.0, 180.0)
        val rightSpikeScore = Pose(2121.8, 1300.0, 180.0)

        val intermediate = Pose(2650.0, 286.0, 90.0)
        val turn = Pose(2650.0, 200.0, 180.0)
       //Creates potential scoring positions for the yellow pixel on the backdrop
        val leftBackdropScore = Pose(2600.0, 1276.0, 0.0)
        val middleBackdropScore = Pose(2000.0, 900.0, 0.0)
        val rightBackdropScore = Pose(2700.0, 1109.0, 0.0)

        //Positions between backdrop scoring and parking
        val prePark = Pose(3060.0, 249.0, 90.0)
        val park = Pose(3352.0, 249.0, 0.0)

        val autoTime = ElapsedTime()
        var detection: Detection? = null

        val preParkCommand = drivetrain.moveToPosition(prePark)
        val parkCommand = drivetrain.moveToPosition(park)

        val group1 = CommandGroup()
            .add(Command({
                val result = openCv.getDetection()
                detection = when (result?.position?.x?.toInt()) {
                    in 0..300 -> Detection.Left
                    in 300..(1280 / 3 * 2) -> Detection.Center
                    in (1280 / 3 * 2)..1280 -> Detection.Right
                    else -> null
                }
            }) {detection == null}) //Gets camera detection
            .add {
                return@add when (detection) {
                    Detection.Left -> drivetrain.moveToPosition(leftSpikeScore)
                    Detection.Center -> drivetrain.moveToPosition(middleSpikeScore)
                    Detection.Right -> drivetrain.moveToPosition(rightSpikeScore)
                    else -> drivetrain.moveToPosition(middleSpikeScore)
                }
            } //Moves to correct spike scoring position
            .add(timedCommand({ drivetrain.stop() }, Time.fromSeconds(1.0)))
            .add(drivetrain.moveToPosition(intermediate))
            .add(timedCommand({ drivetrain.stop() }, Time.fromSeconds(1.0)))
            .add(drivetrain.moveToPosition(turn))
            /*.add {
                return@add when (detection) {
                    Detection.Left -> drivetrain.moveToPosition(leftBackdropScore)
                    Detection.Center -> drivetrain.moveToPosition(middleBackdropScore)
                    Detection.Right -> drivetrain.moveToPosition(rightBackdropScore)
                    else -> drivetrain.moveToPosition(middleBackdropScore)
                }
            }*/
            .build() //Builds all commands

        Scheduler.add(continuousCommand { slides.update() })
        Scheduler.add(group1)
        waitForStart()
        autoTime.reset()
        while(opModeIsActive()) {
            robot.update()
        }
    }
}