package kaczmarczyk.projects;

import java.io.Serializable;

/** Includes basic film parameters */
public class Film implements Serializable, Comparable<Film> {
    String title;
    String director;
    int minutes; //indicates the length of the film

    public Film(){

    }

    public Film(String title, String director, int minutes){
        this.title = title;
        this.director = director;
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "\"" + title + "\", reż.: " + director;
    }

    public int compareTo(Film f){
        if(f == null)
            return -1;

        if(title.equals(f.title))
            return director.compareTo(f.director);

        return title.compareTo(f.title);
    }

    @Override
    public boolean equals(Object f){
        if(!(f instanceof Film))
            return false;

        Film film = (Film) f;
        return this.compareTo(film) == 0;
    }
}
