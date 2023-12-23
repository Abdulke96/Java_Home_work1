package org.example;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @Override
    public void displayInfo() {
        System.out.println("    Episode Name: "+episodeName);
        System.out.println("    Duration: " + duration);
    }

    @Override
    public String guiDisplay() {
        return "    Episode Name: "+episodeName + "\n" +
                "   Duration: " + duration + "\n";
    }

}
