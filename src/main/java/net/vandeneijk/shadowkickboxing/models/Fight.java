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

    @NotNull
    private ZonedDateTime zdtReservedUntil;

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
    @ManyToOne
    private BodyHalf bodyHalf;

    private Long fightAudioDataId;

    protected Fight() {}

    public Fight(@NotNull String randomId, @NotNull Language language, @NotNull Speed speed, @NotNull Length length, @NotNull DefensiveMode defensiveMode, @NotNull BodyHalf bodyHalf) {
        this.randomId = randomId;
        this.language = language;
        this.speed = speed;
        this.length = length;
        this.defensiveMode = defensiveMode;
        this.bodyHalf = bodyHalf;
        zdtReservedUntil = ZonedDateTime.now();
    }

    public Long getFightId() {
        return fightId;
    }

    public String getRandomId() {
        return randomId;
    }

    public ZonedDateTime getZdtReservedUntil() {
        return zdtReservedUntil;
    }

    public void setZdtReservedUntil(ZonedDateTime zdtReservedUntil) {
        this.zdtReservedUntil = zdtReservedUntil;
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

    public BodyHalf getBodyHalf() {
        return bodyHalf;
    }

    public Long getFightAudioDataId() {
        return fightAudioDataId;
    }

    public void setFightAudioDataId(Long fightAudioDataId) {
        this.fightAudioDataId = fightAudioDataId;
    }

    public String getName() {
        return "skb_" + randomId + speed.getDescriptionIn2Chars() + length.getDescriptionIn2Chars() + defensiveMode.getDescriptionIn2Chars() + bodyHalf.getDescriptionIn2Chars();
    }
}
