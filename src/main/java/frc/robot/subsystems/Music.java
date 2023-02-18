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

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

public class Music extends SubsystemBase {
    private static Music mMusic;
    private static Orchestra mOrchestra = new Orchestra();

    private static final Spark blinken = new Spark(0);
    
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

    private static ArrayList<TalonFX> instruments = new ArrayList<TalonFX>();

    public static Music getInstance() {
        if (mMusic == null) {
            mMusic = new Music();
        }    
        return mMusic;
    }    
    
    public Music() {     
        //Orchestra1.addInstrument(talon1);
        //Orchestra2.addInstrument(talon2);
        //Orchestra3.addInstrument(talon3);
        //Orchestra4.addInstrument(talon4);
    }    
    
    public static void init(TalonFX[] talons) {
        TalonFX e;
        for (int x = 0; x < talons.length; x++) {
            instruments.add(talons[x]);
            e = talons[x];
            mOrchestra.addInstrument(e);
        }
    }
    public void defaultCode() {
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

    public void playSong() {
        mOrchestra.play();
        //Orchestra1.play();
        //Orchestra2.play();
        //Orchestra3.play();
        //Orchestra4.play();
    }
    
    public void loadSong(String filename) {
        Song = filename;
        mOrchestra.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + filename);
        //Orchestra1.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + "Crab-Rave.chrp");
        //Orchestra2.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + "Crab-Rave.chrp");
        //Orchestra3.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + "Crab-Rave.chrp");
        //Orchestra4.loadMusic(File.separator + "home" + File.separator + "lvuser" + File.separator + "deploy" + File.separator + "Crab-Rave.chrp");
    
        //home/lvuser/deploy
    }
    
    public void pauseSong() {
        mOrchestra.pause();
        //Orchestra1.pause();
        //Orchestra2.pause();
        //Orchestra3.pause();
        //Orchestra4.pause();

    }
    
    public static void addInstrument() {
        //mOrchestra.clearInstruments();
    }
    
    public static String playlistOrder() {
        if (SongNum == (playlistLength - 1)) {
            SongNum = 0;
        } else {
            SongNum++;
        }
        return playlist[SongNum];
    }
    
    public static void lights() {
        if (mOrchestra.isPlaying()) {
            blinken.set(0.65);
        } else {
            blinken.set(0.03);
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

}
