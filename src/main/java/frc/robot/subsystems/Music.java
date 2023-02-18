package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

import java.io.File;
import java.util.ArrayList;

import com.ctre.phoenix.CANifier.PWMChannel;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

public class Music extends SubsystemBase {
    private static Music mMusic;
    static Orchestra mOrchestra;
    static Orchestra Orchestra1 = new Orchestra();
    static Orchestra Orchestra2 = new Orchestra();
    static Orchestra Orchestra3 = new Orchestra();
    static Orchestra Orchestra4 = new Orchestra();
    private static final Spark blinken = new Spark(0);

    public static final TalonFX talon1 = new TalonFX(OperatorConstants.talon1_ID);
    public static final TalonFX talon2 = new TalonFX(OperatorConstants.talon2_ID);
    public static final TalonFX talon3 = new TalonFX(OperatorConstants.talon3_ID);
    public static final TalonFX talon4 = new TalonFX(OperatorConstants.talon4_ID);

    //, new TalonFX(5), new TalonFX(6), new TalonFX(7)
    TalonFX[] motors = { new TalonFX(OperatorConstants.talon1_ID), new TalonFX(OperatorConstants.talon2_ID), new TalonFX(OperatorConstants.talon3_ID), new TalonFX(OperatorConstants.talon4_ID)};

    private static Boolean Status;
    private static String Song = "Song";

    public static String[] playlist = {
        "TwinkleStar.chrp",
        "HotCrossBuns.chrp"
    };

    public static int[] songLength = {
        29000, //Seconds
        29000
    };

    public static int x = playlist.length;

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
        
        mOrchestra = new Orchestra(instruments);
        //Orchestra1.addInstrument(talon1);
        //Orchestra2.addInstrument(talon2);
        //Orchestra3.addInstrument(talon3);
        //Orchestra4.addInstrument(talon4);
        
//        restartPlaylist();
    }    
    
    public static void defaultCode() {
        if (mOrchestra.getCurrentTime() >= (songLength[SongNum] + 200)) {
            loadSong(playlistOrder());
            playSong();
        }
        SmartDashboard.putNumber("TimeStamp", mOrchestra.getCurrentTime());
        SmartDashboard.putString("Song", Song);
        lights();
//        playerStatus();

        // TalonFX talonFX = new TalonFX(0);
    }

    public static void playSong() {
        mOrchestra.play();
        //Orchestra1.play();
        //Orchestra2.play();
        //Orchestra3.play();
        //Orchestra4.play();
    }
    
    public static void loadSong(String filename) {
        Song = filename;
        mOrchestra.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + filename);
        //Orchestra1.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + "Crab-Rave.chrp");
        //Orchestra2.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + "Crab-Rave.chrp");
        //Orchestra3.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + "Crab-Rave.chrp");
        //Orchestra4.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + "Crab-Rave.chrp");
        
        System.out.println("LoadSong triggered");

        //home/lvuser/deploy
    }
    
    public static void pauseSong() {
        mOrchestra.pause();
        //Orchestra1.pause();
        //Orchestra2.pause();
        //Orchestra3.pause();
        //Orchestra4.pause();

        System.out.println("Pause Song triggered");
    }
    
    public static void addInstrument() {
        //mOrchestra.clearInstruments();
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
    
    public static void lights() {
        if (mOrchestra.isPlaying()) {
            blinken.set(0.03);
        } else {
            blinken.set(0.53);
        }
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
        talon1.set(ControlMode.MusicTone, 261.63 * speed);
        talon2.set(ControlMode.MusicTone, 440 * speed);
        talon3.set(ControlMode.MusicTone, 329.63 * speed);
        talon4.set(ControlMode.MusicTone, 0 * speed);
    }

}
