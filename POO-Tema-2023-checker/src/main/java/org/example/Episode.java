package org.example;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Episode extends Production {
    private final String episodeName;
    private final String duration;
    public Episode() {
        super();
        this.episodeName = null;
        this.duration = null;
    }

    @Override
    public void displayInfo() {
        System.out.println("    Episode Name: "+episodeName);
        System.out.println("    Duration: " + duration);
    }
}
