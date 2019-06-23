package com.example.movie.Model;

import java.util.ArrayList;

public class MovieData {
    public static String[][] data = new String[][]{
            {"Avengers: Infinity War","April 27, 2018","https://image.tmdb.org/t/p/w600_and_h900_bestv2/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg","As the Avengers and their allies have continued to protect the world from threats too large for any \n" +
                    "one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of \n" +
                    "intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and \n" +
                    "use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led \n" +
                    "up to this moment - the fate of Earth and existence itself has never been more uncertain.","Joe Russo | Anthony Russo", "ADVENTURE | ACTION | FANTASY"},
            {"Avengers: Endgame", "April 26, 2019","https://image.tmdb.org/t/p/w600_and_h900_bestv2/or06FN3Dka5tukK1e9sl16pB3iy.jpg","After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of \n" +
                    "the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in \n" +
                    "order to undo Thanos' actions and restore order to the universe once and for all, no matter what \n" +
                    "consequences may be in store.","Joe Russo | Anthony Russo","ADVENTURE | SCIENCE FICTION | ACTION"},
            {"Toy Story 4","June 21, 2019","https://image.tmdb.org/t/p/w600_and_h900_bestv2/crvO4xm2gs5W0joyKmJf1l1ThZV.jpg","Woody has always been confident about his place in the world and that his priority is taking care of \n" +
                    "his kid, whether that's Andy or Bonnie. But when Bonnie adds a reluctant new toy called \"Forky\" to \n" +
                    "her room, a road trip adventure alongside old and new friends will show Woody how big the world \n" +
                    "can be for a toy.","Josh Cooley","ADVENTURE | ANIMATION | COMEDY | FAMILY"},
            {"Venom","October 5, 2018","https://image.tmdb.org/t/p/w600_and_h900_bestv2/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg","Investigative journalist Eddie Brock attempts a comeback following a scandal, but accidentally \n" +
                    "becomes the host of Venom, a violent, super powerful alien symbiote. Soon, he must rely on his \n" +
                    "newfound powers to protect the world from a shadowy organization looking for a symbiote of their \n" +
                    "own.","Ruben Fleischer","SCIENCE FICTION | ACTION"},
            {"Robin Hood","November 21, 2018","https://image.tmdb.org/t/p/w600_and_h900_bestv2/AiRfixFcfTkNbn2A73qVJPlpkUo.jpg","A war-hardened Crusader and his Moorish commander mount an audacious revolt against the \n" +
                    "corrupt English crown.","Otto Bathurst","ADVENTURE | ACTION | THRILLER"},
            {"Black Panther","February 16, 2018","https://image.tmdb.org/t/p/w600_and_h900_bestv2/uxzzxijgPIY7slzFvMotPv8wjKA.jpg","King T'Challa returns home from America to the reclusive, technologically advanced African nation \n" +
                    "of Wakanda to serve as his country's new leader. However, T'Challa soon finds that he is challenged \n" +
                    "for the throne by factions within his own country as well as without. Using powers reserved to \n" +
                    "Wakandan kings, T'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the \n" +
                    "queen-mother, his princess-kid sister, members of the Dora Milaje (the Wakandan 'special forces') \n" +
                    "and an American secret agent, to prevent Wakanda from being dragged into a world war.","Ryan Coogler","ADVENTURE | ACTION | FANTASY | SCIENCE FICTION"},
            {"Mortal Engines","December 14, 2018","https://image.tmdb.org/t/p/w600_and_h900_bestv2/iteUvQKCW0EqNQrIVzZGJntYq9s.jpg","Many thousands of years in the future, Earth’s cities roam the globe on huge wheels, devouring \n" +
                    "each other in a struggle for ever diminishing resources. On one of these massive traction cities, the \n" +
                    "old London, Tom Natsworthy has an unexpected encounter with a mysterious young woman from \n" +
                    "the wastelands who will change the course of his life forever.","Christian Rivers","ADVENTURE | FANTASY"},
    };

    public static ArrayList<Movie> getListData(){
        Movie movie = null;
        ArrayList<Movie> list = new ArrayList<>();
        for (String[] adata : data){
            movie = new Movie();
            movie.setTitle(adata[0]);
            movie.setReleaseDate(adata[1]);
            movie.setPhoto(adata[2]);
            movie.setDescription(adata[3]);
            movie.setDirector(adata[4]);
            movie.setGenres(adata[5]);
            list.add(movie);
        }
        return list;
    }
}
