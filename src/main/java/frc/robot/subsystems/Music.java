package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

import java.io.File;
import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

public class Music extends SubsystemBase {
    private static Music mMusic;
    static Orchestra mOrchestra;
    static Orchestra Orchestra1;
    static Orchestra Orchestra2;
    static Orchestra Orchestra3;
    static Orchestra Orchestra4;

    public static final TalonFX talon1 = new TalonFX(OperatorConstants.talon1_ID);
    public static final TalonFX talon2 = new TalonFX(OperatorConstants.talon2_ID);
    public static final TalonFX talon3 = new TalonFX(OperatorConstants.talon3_ID);
    public static final TalonFX talon4 = new TalonFX(OperatorConstants.talon4_ID);

    //, new TalonFX(5), new TalonFX(6), new TalonFX(7)
    TalonFX[] motors = { new TalonFX(OperatorConstants.talon1_ID), new TalonFX(OperatorConstants.talon2_ID), new TalonFX(OperatorConstants.talon3_ID), new TalonFX(OperatorConstants.talon4_ID)};

    private static Boolean Status;
    private static String Song = "Song";

    public static String[] playlist = {
        "Crab-Rave.chrp",
        "Offender-Shuffle.chrp",
        "Chairmans-Rap.chrp"
    };

    public static int playlistLength = playlist.length;

    public static int SongNum = playlistLength - 1;
    
    public static Music getInstance() {
        if (mMusic == null) {
            mMusic = new Music();
        }    
        return mMusic;
    }    
    
    public Music() {
        ArrayList<TalonFX> instruments = new ArrayList<TalonFX>();

        for (int i = 0; i < motors.length; ++i) {
            instruments.add(motors[i]);
        }
        
        //mOrchestra = new Orchestra(instruments);
        Orchestra1.addInstrument(talon1);
        Orchestra2.addInstrument(talon2);
        Orchestra3.addInstrument(talon3);
        Orchestra4.addInstrument(talon4);
        
//        restartPlaylist();
    }    
    
    public static void defaultCode() {
        SmartDashboard.putNumber("TimeStamp", mOrchestra.getCurrentTime());
        SmartDashboard.putString("Song", Song);
//        playerStatus();

        // TalonFX talonFX = new TalonFX(0);
    }

    public void playSong() {
        mOrchestra.play();
    }
    
    public void loadSong(String filename) {
        Song = filename;
        //mOrchestra.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + playlist[1]);
        Orchestra1.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + playlist[1]);
        Orchestra2.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + playlist[1]);
        Orchestra3.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + playlist[1]);
        Orchestra4.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + playlist[1]);
        
        System.out.println("LoadSong triggered");

        //home/lvuser/deploy
    }
    
    public void pauseSong() {
        mOrchestra.pause();
        System.out.println("Pause Song triggered");
    }
    
    public static void addInstrument() {
        mOrchestra.clearInstruments();
        mOrchestra.addInstrument(talon1);
        mOrchestra.addInstrument(talon2);
        mOrchestra.addInstrument(talon3);
        mOrchestra.addInstrument(talon4);
        System.out.println("addInstrument triggered");
    }
    
    public static String playlistOrder() {
        if (SongNum == (playlistLength - 1)) {
            SongNum = 0;
        } else {
            SongNum++;
        }
        System.out.println("PlaylistOrder triggered");
        return playlist[SongNum];
    }
    
    /*

    public static void skipSong() {
//        loadSong(playlistOrder());
        loadSong(playlist[1]);

}


    public static void playerStatus() {
        Status = mOrchestra.isPlaying();
        SmartDashboard.putBoolean("Song Playing", Status);
    }



    public static void restartPlaylist() {
        SongNum = (playlistLength - 1);
//        loadSong(playlistOrder());
    }
*/
    public void setSpeed(double speed) {
        System.out.println("setSpeed triggered");

        talon1.set(ControlMode.PercentOutput, speed);
        talon2.set(ControlMode.PercentOutput, speed);
        talon3.set(ControlMode.PercentOutput, speed);
        talon4.set(ControlMode.PercentOutput, speed);
    }

}
