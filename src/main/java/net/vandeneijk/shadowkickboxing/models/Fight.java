/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
public class Fight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fightId;

    @Column(unique = true)
    @NotNull
    private String randomId;

    private ZonedDateTime zdtFirstDownload;

    @NotNull
    @ManyToOne
    private Language language;

    @NotNull
    @ManyToOne
    private Speed speed;

    @NotNull
    @ManyToOne
    private Length length;

    @NotNull
    @Column(columnDefinition = "longblob")
    private byte[] audioFragment;

    protected Fight() {}

    public Fight(@NotNull String randomId, @NotNull Language language, @NotNull Speed speed, @NotNull Length length, @NotNull byte[] audioFragment) {
        this.randomId = randomId;
        this.language = language;
        this.speed = speed;
        this.length = length;
        this.audioFragment = audioFragment;
    }

    public Long getFightId() {
        return fightId;
    }

    public String getRandomId() {
        return randomId;
    }

    public ZonedDateTime getZdtFirstDownload() {
        return zdtFirstDownload;
    }

    public void setZdtFirstDownload(ZonedDateTime zdtFirstDownload) {
        this.zdtFirstDownload = zdtFirstDownload;
    }

    public Language getLanguage() {
        return language;
    }

    public Speed getSpeed() {
        return speed;
    }

    public Length getLength() {
        return length;
    }

    public byte[] getAudioFragment() {
        return audioFragment;
    }

    public String getName() {
        return "skb_" + randomId + speed.getDescriptionIn2Chars() + length.getDescriptionIn2Chars();
    }
}
