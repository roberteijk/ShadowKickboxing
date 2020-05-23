/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
public class ConnectionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long connectionLogId;

    @NotNull
    private String requestedItem;

    @NotNull
    private String requestedItemAs;

    private Boolean available;

    @NotNull
    private ZonedDateTime zdtRequest;

    @NotNull
    private String requestIpAddress;

    protected ConnectionLog() {}

    public ConnectionLog(@NotNull String requestedItem, @NotNull String requestedItemAs, @NotNull ZonedDateTime zdtRequest, @NotNull String requestIpAddress) {
        this.requestedItem = requestedItem;
        this.requestedItemAs = requestedItemAs;
        this.available = null;
        this.zdtRequest = zdtRequest;
        this.requestIpAddress = requestIpAddress;
    }

    public Long getConnectionLogId() {
        return connectionLogId;
    }

    public String getRequestedItem() {
        return requestedItem;
    }

    public String getRequestedItemAs() {
        return requestedItemAs;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public ZonedDateTime getZdtRequest() {
        return zdtRequest;
    }

    public String getRequestIpAddress() {
        return requestIpAddress;
    }
}
