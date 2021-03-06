/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//make a drive BACKWARDS command (don't use absolute value cmd (Math.abs)), then upload to github
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveForwardDistance extends CommandBase {
  private final DriveSubsystem m_drive;
  private final double m_distance;
  private final double m_speed;
  /**
   * Creates a new DriveForwardDistance.
   */
  public DriveForwardDistance(double distance, double speed, DriveSubsystem drive) {
    m_drive = drive;
    m_distance = distance;
    m_speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.stopDrive();
    m_drive.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.defaultDrive(m_speed, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.stopDrive();
  }

  // Returns true when the command should end.
 @Override
  public boolean isFinished() {
    return Math.abs(m_drive.Avg_Distance()) >= m_distance; //returns a true or false statement, in a single line
  }
}


/* Give the command a distance and a speed
drive forward at that speed for that distance.

wheel diameter 6in
circumference 6pi

1 rotation = 6pi inches

DONE IN CONSTANTS

*/