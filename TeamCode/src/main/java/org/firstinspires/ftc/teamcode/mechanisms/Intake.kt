package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo

/**
 *  Brings pixels into the robot
 */
class Intake(hardwareMap: HardwareMap) {
//    private val colorSensor = hardwareMap.get(ColorSensor::class.java, "sensor")
    private val intakeMotor = hardwareMap.get(DcMotorEx::class.java, "intake")
    val leftServo = hardwareMap.get(Servo::class.java, "intakeLeft")
    val rightServo = hardwareMap.get(Servo::class.java, "intakeRight")

    /**
     * Runs the intake
     */
    fun start() {
        intakeMotor.power = 0.8
    }

    /**
     * Stops the intake
     */
    fun stop() {
        intakeMotor.power = 0.0
    }

    /**
     * Raises the intake to its storage position
     */
    fun up() {
        leftServo.position = 1.0
        rightServo.position = 0.0
    }

    /**
     * Lowers the intake into intaking position
     */
    fun down() {
        leftServo.position = 0.0
        rightServo.position = 1.0
    }
}