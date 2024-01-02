package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.mechanisms.Slide
import org.firstinspires.ftc.teamcode.mechanisms.Slide1Motor
import org.firstinspires.ftc.teamcode.utils.Robot
import org.firstinspires.ftc.teamcode.utils.command.ContinuousCommand
import org.firstinspires.ftc.teamcode.utils.command.Scheduler
import org.firstinspires.ftc.teamcode.utils.input.GamepadEx

@TeleOp(name="SlideOp")

class SlideOp : LinearOpMode() {
    override fun runOpMode() {
        val robot = Robot(this)
        val (gamepad1, _) = robot.getGamepads()
        val slide = Slide(hardwareMap)
        val telemetry: Telemetry = robot.telemetry

        gamepad1.getButton(GamepadEx.Buttons.Y).onStart {
            slide.height += 500 * Scheduler.deltaTime
        }

        gamepad1.getButton(GamepadEx.Buttons.X).onStart {
            slide.height -= 500 * Scheduler.deltaTime
        }

        Scheduler.add(ContinuousCommand { slide.update() })

        telemetry.clearAll()

        waitForStart()

        while (opModeIsActive()) {
            telemetry.addData("Position: ", slide.right.currentPosition)
            robot.update()
            telemetry.update()
        }

        Scheduler.clear()
    }
}