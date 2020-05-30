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

    private ZonedDateTime zdtReservation;

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
    @ManyToOne
    private DefensiveMode defensiveMode;

    @NotNull
    private Long fightAudioDataId;

    protected Fight() {}

    public Fight(@NotNull String randomId, @NotNull Language language, @NotNull Speed speed, @NotNull Length length, @NotNull DefensiveMode defensiveMode, @NotNull Long fightAudioDataId) {
        this.randomId = randomId;
        this.language = language;
        this.speed = speed;
        this.length = length;
        this.defensiveMode = defensiveMode;
        this.fightAudioDataId = fightAudioDataId;
    }

    public Long getFightId() {
        return fightId;
    }

    public String getRandomId() {
        return randomId;
    }

    public ZonedDateTime getZdtReservation() {
        return zdtReservation;
    }

    public void setZdtReservation(ZonedDateTime zdtReservation) {
        this.zdtReservation = zdtReservation;
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

    public DefensiveMode getDefensiveMode() {
        return defensiveMode;
    }

    public Long getFightAudioDataId() {
        return fightAudioDataId;
    }

    public String getName() {
        return "skb_" + randomId + speed.getDescriptionIn2Chars() + length.getDescriptionIn2Chars() + defensiveMode.getDescriptionIn2Chars();
    }
}
