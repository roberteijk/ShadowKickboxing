/**
 * Created by Robert van den Eijk on 8-5-2020.
 */

package net.vandeneijk.shadowkickboxing;

import net.vandeneijk.shadowkickboxing.startup.SeedDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Helper {

    private static final Logger log = LoggerFactory.getLogger(SeedDatabase.class);

    public static int getAudioFileLengthMillis(File file) {
        try {
            AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(file);
            Map<String, Object> properties = baseFileFormat.properties();
            return ((Long) properties.get("duration")).intValue() / 1000;
        } catch (UnsupportedAudioFileException | IOException miscEx) {
            log.error("Error determining the length of an audio file for seeding database. Exception: " + miscEx);
        }
        return 0;
    }
}
