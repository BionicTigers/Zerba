package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.utils.Robot
import org.firstinspires.ftc.teamcode.utils.input.GamepadEx
import org.firstinspires.ftc.teamcode.utils.vision.AprilTags
import java.util.Arrays

@TeleOp(name = "AprilTagOp")
class AprilTagOp : LinearOpMode() {
    override fun runOpMode() {
        val robot = Robot(this)
        val (gamepad1, _) = robot.getGamepads()
        val aprilTags = AprilTags(robot, hardwareMap)

        gamepad1.getButton(GamepadEx.Buttons.A).onStart{
            aprilTags.aprilTagLog(aprilTags.calculateRobotPos(), telemetry)
            println(aprilTags.aprilTagLog(aprilTags.calculateRobotPos(), telemetry))
        }

        gamepad1.getButton(GamepadEx.Buttons.B).onStart {
            telemetry.addLine("Apriltags in view: ${Arrays.toString(aprilTags.getAprilTagDetections())}")
            println("Apriltags in view: ${Arrays.toString(aprilTags.getAprilTagDetections())}")
        }

        gamepad1.getButton(GamepadEx.Buttons.Y).onStart {
            aprilTags.toggleLiveView()
            telemetry.addLine("Toggled Live View")
            println("Toggled Live View")
        }

        gamepad1.getButton(GamepadEx.Buttons.X).onStart {
            aprilTags.toggleATProcessor()
            telemetry.addLine("Toggled ATProcessor")
            println("Toggled ATProcessor")
        }


        waitForStart()

        while (opModeIsActive()) {
            robot.update()
            aprilTags.calculateRobotPos()
            aprilTags.printEverything()

        }
    }
}