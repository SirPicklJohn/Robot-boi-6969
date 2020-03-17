/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;


public class DriveSubsystem extends SubsystemBase {
  private final WPI_TalonFX m_rightMaster = new WPI_TalonFX(DriveConstants.kFrontRightMotor);
  private final WPI_TalonFX m_leftMaster = new WPI_TalonFX(DriveConstants.kFrontLeftMotor);
  private final WPI_TalonFX m_rightSlave = new WPI_TalonFX(DriveConstants.kBackRightMotor);
  private final WPI_TalonFX m_leftSlave = new WPI_TalonFX(DriveConstants.kBackRightMotor);
  

  /*DIFFERENTIAL DRIVE EXPLAINED: (par wikipedia)
  A differential wheeled robot is a mobile robot whose movement is based
  on two separately driven wheels placed on either side of the robot body.*/

  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMaster, m_rightMaster);
  /**
   * Creates a new ExampleSubsystem.
   */
  public DriveSubsystem() {
    //makes sure nothing is set to any weird settings, and that they are all set to default
    m_rightMaster.configFactoryDefault();
    m_leftMaster.configFactoryDefault();
    m_rightSlave.configFactoryDefault();
    m_leftSlave.configFactoryDefault();

    //makes these motors "slaves"
    m_rightSlave.follow(m_rightMaster);
    m_leftSlave.follow(m_leftMaster);

    
    m_rightMaster.setInverted(false);
    m_leftMaster.setInverted(false);
    m_rightSlave.setInverted(InvertType.FollowMaster); //setInverted to the same as the master motor (which is false)
    m_leftSlave.setInverted(InvertType.FollowMaster); //setInverted to the same as the master motor (which is false)
  
    //When the motors aren't going, instead of being like the neutral position in a car and still movable (able to be pushed around), they are braked (like the park mode on a car).
    m_rightMaster.setNeutralMode(NeutralMode.Brake);
    m_leftMaster.setNeutralMode(NeutralMode.Brake);
    m_rightSlave.setNeutralMode(NeutralMode.Brake);
    m_leftSlave.setNeutralMode(NeutralMode.Brake);

    //The Feedback Sensor records how many times the motor moves, and gages
    m_rightMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    m_leftMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    m_drive.setRightSideInverted(true);
  }

  public void defaultDrive(double MotorSpeed, double MotorRotation) {
    m_drive.arcadeDrive(MotorSpeed, MotorRotation);
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
