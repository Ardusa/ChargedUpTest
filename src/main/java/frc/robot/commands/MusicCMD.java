package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Music;

public class MusicCMD extends CommandBase {
    private final XboxController xDrive = new XboxController(OperatorConstants.kDriverControllerPort);

    private final Music mMusic;

    public MusicCMD(Music mMusic, TalonFX ... talonFXs) {
            
//        for(int i = 0; i < talonFXs.length; i++) {
//            Music.addInstrument(talonFXs[i]);
//       }
        this.mMusic = mMusic;
        addRequirements(mMusic);
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Music Player", "Activated");
        Music.loadSong(Music.playlistOrder());
    }

//A for Play
//B for Pause
//Y for LoadSong

    @Override
    public void execute() {
        
        mMusic.setSpeed(xDrive.getLeftTriggerAxis()* 0.5);
        if (xDrive.getAButton()) {  
            Music.playSong();
            System.out.println("CMD1");
        }
        if (xDrive.getBButton()) {
            Music.pauseSong();
            System.out.println("CMD2");
        }
        if (xDrive.getYButton()) {
            Music.loadSong(Music.playlistOrder());
//            Music.loadSong(null);
            System.out.println("CMD1");
        if (xDrive.getXButton()) {
            System.out.println("CMD4");
        }
}
        Music.defaultCode();
    }

    public void end() {
        SmartDashboard.putString("Music Player", "Deactivated");
        System.out.println("Ended");
    }
}
