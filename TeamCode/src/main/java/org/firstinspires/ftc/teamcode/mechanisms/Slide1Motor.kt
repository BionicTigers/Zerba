package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.hardware.lynx.LynxDcMotorController
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.utils.ControlHub
import org.firstinspires.ftc.teamcode.utils.PID
import org.firstinspires.ftc.teamcode.utils.PIDTerms

class Slide1Motor(hardwareMap: HardwareMap) {
    private val right = hardwareMap.get(DcMotorEx::class.java, "SlideR")
    private val pid = PID(PIDTerms(), 0.0, 1000.0, -1.0, 1.0)
    private val hub = ControlHub(hardwareMap, "Control Hub")

    var height = 0.0
        set(value) {
            field = value.coerceIn(0.0, 1000.0)
        }

    init {
        hub.setJunkTicks()
        right.direction = DcMotorSimple.Direction.REVERSE
    }

    fun update() {
        hub.refreshBulkData()
        val encoderTicks = hub.getEncoderTicks(0).toDouble()
        val power = pid.calculate(height, encoderTicks)
        right.power = power

        println("power: $power, target: $height, current height: $encoderTicks" )
    }
}