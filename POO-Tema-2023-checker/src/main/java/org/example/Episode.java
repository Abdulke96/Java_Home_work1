package org.example;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.constants.WriteOutput;

@EqualsAndHashCode(callSuper = true)
@Data
public class Episode extends Production {
    private String episodeName;
    private  String duration;
    public Episode() {
        super();
        this.episodeName = "null";
        this.duration = "null";
    }
    public Episode(String episodeName, String duration) {
        super();
        this.episodeName = episodeName;
        this.duration = duration;
    }
    @Override
    public void displayInfo() {
        WriteOutput.printGreen("    Episode Name: "+episodeName);
        WriteOutput.printGreen("    Duration: " + duration);
    }

    @Override
    public String guiDisplay() {
        return "    Episode Name: "+episodeName + "\n" +
                "   Duration: " + duration + "\n";
    }

}
