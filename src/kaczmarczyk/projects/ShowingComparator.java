package kaczmarczyk.projects;

import java.util.Comparator;

/** Allows to compare showings while sorting */
public class ShowingComparator implements Comparator<Showing> {
    @Override
    public int compare(Showing o1, Showing o2) {
        int comparison = o1.getDayOfWeek().compareTo(o2.getDayOfWeek());
        if(comparison == 0) {
            comparison = o1.getHour().compareTo(o2.getHour());
            if(comparison == 0){
                comparison = o1.film.compareTo(o2.film);
                if(comparison == 0)
                    comparison = Integer.compare(o1.hall, o2.hall);

            }
        }

        return comparison;
    }
}
