package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.utils.Pose
import org.firstinspires.ftc.teamcode.utils.Robot

@Autonomous(name = "BlueParkLeft")
class BlueParkLeft : LinearOpMode() {
    override fun runOpMode() {
        val robot = Robot(this)
        robot.pose = Pose(1499.0, 310.0, 0.0)
        BluePark(robot, hardwareMap)
    }
}