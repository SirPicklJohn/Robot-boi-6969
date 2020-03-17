/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.DriveForwardDistance;
import frc.robot.commands.HalfDrive;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final XboxController m_controller = new XboxController(OIConstants.kControllerPort);
  private final DriveSubsystem m_drive = new DriveSubsystem();
  private final SendableChooser<Command> m_autonChooser = new SendableChooser<>();
  
  //auton commands
  private final Command m_simpleAuto = new DriveForwardDistance(25, 1, m_drive); //goes 25 inches at 100% in auto
  private final Command m_CHEEKIBREEKI = new DriveForwardDistance(200, 1, m_drive);

  private final ShuffleboardTab m_mainTab = Shuffleboard.getTab("-Drive Tab-");


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
                                                            //left joystick Y value (rotation)           Right Joystick X value (rotation)
    m_drive.setDefaultCommand(new ArcadeDrive(() -> -m_controller.getY(GenericHID.Hand.kLeft), () -> m_controller.getX(GenericHID.Hand.kRight), m_drive)); //makes the default cmd ArcadeDrive

    m_autonChooser.setDefaultOption("Simple Drive", m_simpleAuto);
    m_autonChooser.addOption("The Auto for Gopniks", m_CHEEKIBREEKI);
    m_mainTab.add("Autonomous Commands", m_autonChooser).withSize(2, 1).withPosition(0, 0); //use of decorators     adds the autochooser with a size of 2,1 in the upper corner

  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(m_controller, Button.kY.value).whileHeld(new HalfDrive(m_drive)); //Assigns the button Y
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autonChooser.getSelected(); //whatever we select on the chooser, return it
  }
}

