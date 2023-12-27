package org.constants;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GuiConstants {
    public static List<String> contributor = List.of("home ", "View productions details", "View actors details", "View notifications", "Search for actors/movies/series", "Add/Delete actors/movies/series to/from favorites", "Create/DeleteRequest", "Add/Delete actor/movie/series/ from system", "Update movie details", "Update actor details", "Solve requests", "Logout", "Exit");
    public static  List<String> admin = List.of("home ", "View productions details", "View actors details", "View notifications", "Search for actors/movies/series", "Add/Delete actors/movies/series to/from favorites", "Add/Delete user", "Add/Delete actor/movie/series/ from system", "Update movie details", "Update actor details", "Solve requests", "Logout", "Exit");
    public static  List<String> regular = List.of("home ", "View productions details", "View actors details", "View notifications", "Search for actors/movies/series", "Add/Delete actors/movies/series to/from favorites","Create/DeleteRequest", "Add / Delete Review", "Logout", "Exit");

    // GUI frequently used functions
    public static ImageIcon getIcon(String name, int width, int height){
        ImageIcon originalIcon = new ImageIcon(Constants.path + name);
        return new ImageIcon(getScaledImage(originalIcon.getImage(), width, height));
    }
   public static Image getScaledImage(Image srcImg, int width, int height) {
        return srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
