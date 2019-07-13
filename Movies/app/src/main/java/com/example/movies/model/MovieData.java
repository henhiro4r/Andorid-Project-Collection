package com.example.movies.model;

import java.util.ArrayList;

public class MovieData {

    private static String[][] data = new String[][]{
            {"m1","Spider-Man: Far from Home","Jon Watts","Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to \n" +
                    "rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural \n" +
                    "disasters and destruction throughout the continent."
                    ,"https://image.tmdb.org/t/p/w600_and_h900_bestv2/rjbNpRMoVvqHmhmksbokcyCr7wn.jpg","https://image.tmdb.org/t/p/original/dihW2yTsvQlust7mSuAqJDtqW7k.jpg"
                    ,"ACTION | ADVENTURE | SCIENCE FICTION","July 2, 2019","78/100"},
            {"m2","Alita: Battle Angel ","Robert Rodriguez","When Alita awakens with no memory of who she is in a future world she does not recognize, she is \n" +
                    "taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg \n" +
                    "shell is the heart and soul of a young woman with an extraordinary past."
                    ,"https://image.tmdb.org/t/p/w600_and_h900_bestv2/xRWht48C2V8XNfzvPehyClOvDni.jpg","https://image.tmdb.org/t/p/original/8RKBHHRqOMOLh5qW3sS6TSFTd8h.jpg"
                    ,"ACTION | ADVENTURE | THRILLER | SCIENCE FICTION","February 14, 2019","67/100"},
            {"m3","John Wick: Chapter 3 – Parabellum","Chad Stahelski","Super-assassin John Wick returns with a $14 million price tag on his head and an army of bounty-\n" +
                    "hunting killers on his trail. After killing a member of the shadowy international assassin’s guild, the \n" +
                    "High Table, John Wick is excommunicado, but the world’s most ruthless hit men and women await \n" +
                    "his every turn.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/ziEuG1essDuWuC5lpWUaw1uXY2O.jpg"
                    ,"https://image.tmdb.org/t/p/original/vVpEOvdxVBP2aV166j5Xlvb5Cdc.jpg","ACTION | THRILLER | CRIME","May 17, 2019","71/100"},
            {"m4","Toy Story 4","Josh Cooley","Woody has always been confident about his place in the world and that his priority is taking care of \n" +
                    "his kid, whether that's Andy or Bonnie. But when Bonnie adds a reluctant new toy called \"Forky\" to \n" +
                    "her room, a road trip adventure alongside old and new friends will show Woody how big the world \n" +
                    "can be for a toy.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/w9kR8qbmQ01HwnvK4alvnQ2ca0L.jpg"
                    ,"https://image.tmdb.org/t/p/original/m67smI1IIMmYzCl9axvKNULVKLr.jpg","ANIMATION | ADVENTURE | COMEDY | FAMILY","June 21, 2019","78/100"},
            {"m5","The Lion King","Jon Favreau","Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in \n" +
                    "the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the \n" +
                    "throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and \n" +
                    "drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, \n" +
                    "Simba will have to figure out how to grow up and take back what is rightfully his."
                    ,"https://image.tmdb.org/t/p/w600_and_h900_bestv2/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg","https://image.tmdb.org/t/p/original/1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg"
                    ,"ANIMATION | ADVENTURE | FAMILY | DRAMA","July 19, 2019","62/100"},
            {"m6","Hellboy","Neil Marshall","Hellboy comes to England, where he must defeat Nimue, Merlin's consort and the Blood Queen. But \n" +
                    "their battle will bring about the end of the world, a fate he desperately tries to turn away."
                    ,"https://image.tmdb.org/t/p/w600_and_h900_bestv2/bk8LyaMqUtaQ9hUShuvFznQYQKR.jpg","https://image.tmdb.org/t/p/original/hMbP23EkGk6tjEjRZQXhnVAl5fW.jpg"
                    ,"ACTION | ADVENTURE | FANTASY | HORROR | SCIENCE FICTION","April 12, 2019","50/100"},
            {"m7","Shazam!","David F. Sandberg","A boy is given the ability to become an adult superhero in times of need with a single magic word."
                    ,"https://image.tmdb.org/t/p/w600_and_h900_bestv2/xnopI5Xtky18MPhK40cZAGAOVeV.jpg","https://image.tmdb.org/t/p/original/bi4jh0Kt0uuZGsGJoUUfqmbrjQg.jpg"
                    ,"ACTION | COMEDY | FANTASY","March 23, 2019","71/100"},
            {"m8","Captain Marvel","Anna Boden | Ryan Fleck","The story follows Carol Danvers as she becomes one of the universe’s most powerful heroes when \n" +
                    "Earth is caught in the middle of a galactic war between two alien races. Set in the 1990s, Captain \n" +
                    "Marvel is an all-new adventure from a previously unseen period in the history of the Marvel \n" +
                    "Cinematic Universe.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/AtsgWhDnHTq68L0lLsUrCnM7TjG.jpg"
                    ,"https://image.tmdb.org/t/p/original/w2PMyoyLU22YvrGK3smVM9fW1jj.jpg","ACTION | ADVENTURE | SCIENCE FICTION","March 8, 2019","70/100"},
            {"m9","Avengers: Infinity War","Joe Russo | Anthony Russo","As the Avengers and their allies have continued to protect the world from threats too large for any \n" +
                    "one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of \n" +
                    "intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and \n" +
                    "use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led \n" +
                    "up to this moment - the fate of Earth and existence itself has never been more uncertain."
                    ,"https://image.tmdb.org/t/p/w600_and_h900_bestv2/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg","https://image.tmdb.org/t/p/original/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg"
                    ,"ACTION | ADVENTURE | FANTASY","April 27, 2018","83/100"},
            {"m10","Avengers: Endgame","Joe Russo | Anthony Russo","After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of \n" +
                    "the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in \n" +
                    "order to undo Thanos' actions and restore order to the universe once and for all, no matter what \n" +
                    "consequences may be in store.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/or06FN3Dka5tukK1e9sl16pB3iy.jpg"
                    ,"https://image.tmdb.org/t/p/original/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg","ACTION | ADVENTURE | SCIENCE FICTION","April 26, 2019","84/100"},
    };
    private static String[][] cast1 = new String[][]{
            {"Tom Holland","Peter Parker / Spider-Man","https://image.tmdb.org/t/p/w600_and_h900_bestv2/2qhIDp44cAqP2clOgt2afQI07X8.jpg"},
            {"Jake Gyllenhaal","Quentin Beck / Mysterio","https://image.tmdb.org/t/p/w600_and_h900_bestv2/92sBuFC8tWPG7IqGDJNxysT7tIF.jpg"},
            {"Samuel L. Jackson","Nick Fury","https://image.tmdb.org/t/p/w600_and_h900_bestv2/mXN4Gw9tZJVKrLJHde2IcUHmV3P.jpg"},
            {"Marisa Tomei","May Parker","https://image.tmdb.org/t/p/w600_and_h900_bestv2/w8qBpRcv04D5eSnnmvRL7PXyW36.jpg"},
            {"Jon Favreau","Harold \"Happy\" Hogan","https://image.tmdb.org/t/p/w600_and_h900_bestv2/rOVBKURoR7TrG8MYxTuNUFj3E68.jpg"},
    };
    private static String[][] cast2 = new String[][]{
            {"Rosa Salazar","Alita","https://image.tmdb.org/t/p/w600_and_h900_bestv2/rQZAi8uI74BGyi5fVN1ihOJ3qOa.jpg"},
            {"Christoph Waltz","Dr. Dyson Ido","https://image.tmdb.org/t/p/w600_and_h900_bestv2/2Hhztd4mUEV9Y25rfkXDwzL9QI9.jpg"},
            {"Mahershala Ali","Vector","https://image.tmdb.org/t/p/w600_and_h900_bestv2/y9mf12rlZBlVJS6JYuCPpjTaLT6.jpg"},
            {"Keean Johnson","Hugo","https://image.tmdb.org/t/p/w600_and_h900_bestv2/qYslz07HQUW1bAqdYSa3dUbnglb.jpg"},
            {"Jennifer Connelly","Chiren","https://image.tmdb.org/t/p/w600_and_h900_bestv2/mOVleZFCqMDugAXyyiJBq6LmSah.jpg"},
    };
    private static String[][] cast3 = new String[][]{
            {"Keanu Reeves","John Wick","https://image.tmdb.org/t/p/w600_and_h900_bestv2/bOlYWhVuOiU6azC4Bw6zlXZ5QTC.jpg"},
            {"Halle Berry","Sofia","https://image.tmdb.org/t/p/w600_and_h900_bestv2/AmCXHowNbUXpNf41dNrxNB0naM2.jpg"},
            {"Ian McShane","Winston","https://image.tmdb.org/t/p/w600_and_h900_bestv2/f6ugFQRl48BdaLNXou9LI4c8RrD.jpg"},
            {"Laurence Fishburne","The Bowery King","https://image.tmdb.org/t/p/w600_and_h900_bestv2/8suOhUmPbfKqDQ17jQ1Gy0mI3P4.jpg"},
            {"Anjelica Huston","The Director","https://image.tmdb.org/t/p/w600_and_h900_bestv2/yLB6mIudcINnbm1wcaZ2vYWZTbH.jpg"},
    };
    private static String[][] cast4 = new String[][]{
            {"Tom Hanks","Woody (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/xxPMucou2wRDxLrud8i2D4dsywh.jpg"},
            {"Tim Allen","Buzz Lightyear (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/dDbtWMGdhatUjCIYolc312R2ygu.jpg"},
            {"Annie Potts","Bo Peep (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/eryXT84RL41jHSJcMy4kS3u9y6w.jpg"},
            {"Joan Cusack","Jessie (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/3jcrXcFYoSKEUvokzqrQ2UJGtw.jpg"},
            {"Blake Clark","Slinky Dog (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/j6nhwuLKJXzHuD4zv2d3y99PcIC.jpg"},
    };
    private static String[][] cast5 = new String[][]{
            {"Donald Glover","Simba (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/36o5mpbQVdxOf9kUxWw7SllOuk.jpg"},
            {"Beyoncé Knowles","Nala (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/9MgDCYBBVBl4lM1DuxNIIbCHlKy.jpg"},
            {"James Earl Jones","Mufasa (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/oqMPIsXrl9SZkRfIKN08eFROmH6.jpg"},
            {"Chiwetel Ejiofor","Scar (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/kq5DDnqqofoRI0t6ddtRlsJnNPT.jpg"},
            {"Alfre Woodard","Sarabi (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/aCAjUOrV2WG3OaLUvQHVkyk8S2P.jpg"},
    };
    private static String[][] cast6 = new String[][]{
            {"David Harbour","Hellboy","https://image.tmdb.org/t/p/w600_and_h900_bestv2/chPekukMF5SNnW6b22NbYPqAStr.jpg"},
            {"Milla Jovovich","Nimue the Blood Queen","https://image.tmdb.org/t/p/w600_and_h900_bestv2/mcNgLarIVho7LheWcvd1oQ2tBOg.jpg"},
            {"Ian McShane","Prof. Trevor Bruttenholm","https://image.tmdb.org/t/p/w600_and_h900_bestv2/f6ugFQRl48BdaLNXou9LI4c8RrD.jpg"},
            {"Daniel Dae Kim","Major Ben Daimio","https://image.tmdb.org/t/p/w600_and_h900_bestv2/dehGaLoF7eq31pX0YXIS8KWGwv0.jpg"},
            {"Sasha Lane","Alice Monaghan","https://image.tmdb.org/t/p/w600_and_h900_bestv2/sR9vrYIAEzIaxY0LtSAyZjBSz9X.jpg"},
    };
    private static String[][] cast7 = new String[][]{
            {"Zachary Levi","Shazam","https://image.tmdb.org/t/p/w600_and_h900_bestv2/1W8L3kEMMPF9umT3ZGaNIiCYKfZ.jpg"},
            {"Asher Angel","Billy Batson","https://image.tmdb.org/t/p/w600_and_h900_bestv2/l35dA6bF2JmpDsiii8GTW8eUDLP.jpg"},
            {"Mark Strong","Doctor Thaddeus Sivana","https://image.tmdb.org/t/p/w600_and_h900_bestv2/63AwQg1hz4KQlStZDufhwJdLmT5.jpg"},
            {"Jack Dylan Grazer","Freddy Freeman","https://image.tmdb.org/t/p/w600_and_h900_bestv2/q3eF91Q7A3t5GFB5N0S7A1OSvV0.jpg"},
            {"Djimon Hounsou","The Wizard","https://image.tmdb.org/t/p/w600_and_h900_bestv2/5Nsd55XnTVAaiyeMfKh0udl8Qyu.jpg"},
    };
    private static String[][] cast8 = new String[][]{
            {"Brie Larson","Carol Danvers / Vers / Captain Marvel","https://image.tmdb.org/t/p/w600_and_h900_bestv2/4ZgxRd2ADYVm2gd5yQJa1emtMl5.jpg"},
            {"Samuel L. Jackson","Nick Fury","https://image.tmdb.org/t/p/w600_and_h900_bestv2/mXN4Gw9tZJVKrLJHde2IcUHmV3P.jpg"},
            {"Ben Mendelsohn","Talos / Keller","https://image.tmdb.org/t/p/w600_and_h900_bestv2/7wuqoqwDOMi6k1Y4zNzGPPsiQKy.jpg"},
            {"Jude Law","Yon-Rogg","https://image.tmdb.org/t/p/w600_and_h900_bestv2/4077Cyuo1mw53u1gNjLyQkqeZN0.jpg"},
            {"Annette Bening","Supreme Intelligence / Dr. Wendy Lawson / Mar-Vell","https://image.tmdb.org/t/p/w600_and_h900_bestv2/vVAvoiE6FQ4couqaB0ogaHR6Ef7.jpg"},
    };
    private static String[][] cast9 = new String[][]{
            {"Robert Downey Jr.","Tony Stark / Iron Man","https://image.tmdb.org/t/p/w600_and_h900_bestv2/1YjdSym1jTG7xjHSI0yGGWEsw5i.jpg"},
            {"Chris Hemsworth","Thor Odinson","https://image.tmdb.org/t/p/w600_and_h900_bestv2/lrhth7yK9p3vy6p7AabDUM1THKl.jpg"},
            {"Chris Evans","Steve Rogers / Captain America","https://image.tmdb.org/t/p/w600_and_h900_bestv2/7dUkkq1lK593XvOjunlUB11lKm1.jpg"},
            {"Mark Ruffalo","Bruce Banner / The Hulk","https://image.tmdb.org/t/p/w600_and_h900_bestv2/z3dvKqMNDQWk3QLxzumloQVR0pv.jpg"},
            {"Scarlett Johansson","Natasha Romanoff / Black Widow","https://image.tmdb.org/t/p/w600_and_h900_bestv2/tHMgW7Pg0Fg6HmB8Kh8Ixk6yxZw.jpg"},
    };
    private static String[][] cast10 = new String[][]{
            {"Robert Downey Jr.","Tony Stark / Iron Man","https://image.tmdb.org/t/p/w600_and_h900_bestv2/1YjdSym1jTG7xjHSI0yGGWEsw5i.jpg"},
            {"Chris Evans","Steve Rogers / Captain America","https://image.tmdb.org/t/p/w600_and_h900_bestv2/7dUkkq1lK593XvOjunlUB11lKm1.jpg"},
            {"Chris Hemsworth","Thor Odinson","https://image.tmdb.org/t/p/w600_and_h900_bestv2/lrhth7yK9p3vy6p7AabDUM1THKl.jpg"},
            {"Mark Ruffalo","Bruce Banner / The Hulk","https://image.tmdb.org/t/p/w600_and_h900_bestv2/z3dvKqMNDQWk3QLxzumloQVR0pv.jpg"},
            {"Scarlett Johansson","Natasha Romanoff / Black Widow","https://image.tmdb.org/t/p/w600_and_h900_bestv2/tHMgW7Pg0Fg6HmB8Kh8Ixk6yxZw.jpg"},
    };

    public void checkId(String id){
        ArrayList<Cast> casts = new ArrayList<>();
        switch (id){
            case "m1":
                casts.addAll(getCastData(cast1));
//                getCastData(cast1
                break;
            case "m2":
                casts.addAll(getCastData(cast2));
//                getCastData(cast2);
                break;
            case "m3":
                casts.addAll(getCastData(cast3));
//                getCastData(cast3);
                break;
            case "m4":
                casts.addAll(getCastData(cast4));
//                getCastData(cast4);
                break;
            case "m5":
                casts.addAll(getCastData(cast5));
//                getCastData(cast5);
                break;
            case "m6":
                casts.addAll(getCastData(cast6));
//                getCastData(cast6);
                break;
            case "m7":
                casts.addAll(getCastData(cast7));
//                getCastData(cast7);
                break;
            case "m8":
                casts.addAll(getCastData(cast8));
//                getCastData(cast8);
                break;
            case "m9":
                casts.addAll(getCastData(cast9));
//                getCastData(cast9);
                break;
            case "m10":
                casts.addAll(getCastData(cast10));
//                getCastData(cast10);
                break;
        }
    }

    public static ArrayList<Movie> getListData(){
        Movie movie;
        ArrayList<Movie> list = new ArrayList<>();
        for (String[] adata : data){
            movie = new Movie();
            movie.setId_movie(adata[0]);
            movie.setTitle(adata[1]);
            movie.setDirector(adata[2]);
            movie.setDescription(adata[3]);
            movie.setPoster(adata[4]);
            movie.setCover(adata[5]);
            movie.setGenres(adata[6]);
            movie.setReleaseDate(adata[7]);
            movie.setRating(adata[8]);
            list.add(movie);
        }
        return list;
    }

    private static ArrayList<Cast> getCastData(String[][] dataCast){
        Cast cast;
        ArrayList<Cast> list = new ArrayList<>();
        for (String[] acast : dataCast){
            cast = new Cast();
            cast.setName(acast[0]);
            cast.setRole(acast[1]);
            cast.setImg_url(acast[2]);
            list.add(cast);
        }
        return list;
    }
}
