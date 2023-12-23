package org.example;

public enum Genre {
    Action, Adventure, Comedy, Drama, Horror, SF,
    Fantasy, Romance, Mystery, Thriller, Crime, Biography, History, War, Western, Musical, Sport, Documentary, Animation, Family, Music, Short, Adult, News, Reality_TV, Talk_Show, Game_Show, Film_Noir , Cooking;

    public static Genre getGenre(int index) {
        return valueOf(Genre.values()[index].name());
    }

}
