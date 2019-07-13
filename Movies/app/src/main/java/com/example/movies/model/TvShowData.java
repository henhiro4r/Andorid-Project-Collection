package com.example.movies.model;

import java.util.ArrayList;

public class TvShowData {

    private static String[][] data = new String[][]{
            {"t1","One-Punch Man","Yuusuke Murata | ONE","Saitama is a hero who only became a hero for fun. After three years of “special” training, though, \n" +
                    "he’s become so strong that he’s practically invincible. In fact, he’s too strong—even his mightiest \n" +
                    "opponents are taken out with a single punch, and it turns out that being devastatingly powerful is \n" +
                    "actually kind of a bore. With his passion for being a hero lost along with his hair, yet still faced with \n" +
                    "new enemies every day, how much longer can he keep it going?","https://image.tmdb.org/t/p/w600_and_h900_bestv2/iE3s0lG5QVdEHOEZnoAxjmMtvne.jpg"
                    ,"https://image.tmdb.org/t/p/original/s0w8JbuNNxL1YgaHeDWih12C3jG.jpg","ACTION & ADVENTURE | ANIMATION | COMEDY","October 4, 2015"},
            {"t2","Is It Wrong to Try to Pick Up Girls in a Dungeon?"," ","Bell Cranell is a rookie adventurer and the sole member of his dirt-poor guild founded by the petite \n" +
                    "goddess Hestia. Adventurers come from far and wide looking to strike it big in the massive labyrinth \n" +
                    "known as Dungeon, located beneath the city of Orario. Bell is more interested in picking up girls \n" +
                    "than Dungeon-crawling, but his reality check comes sooner than expected. Saved from a brush \n" +
                    "with death by the beautiful, renowned adventurer Aiz Wallenstein, Bell falls head-over-heels and \n" +
                    "sets his sights on winning her heart, but she's not the only woman he's caught the attention of, for \n" +
                    "better of worse.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/wuX4cucXdxad03jGWM0r3RQx1p6.jpg"
                    ,"https://image.tmdb.org/t/p/original/zQPYuhY3G8NxsKFwYtFPzC8VBCJ.jpg","ANIMATION | COMEDY | SCI-FI & FANTASY","April 4, 2015"},
            {"t3","Fire Force"," ","Year 198 of the Solar Era in Tokyo, special fire brigades are fighting against a phenomenon called \n" +
                    "spontaneous human combustion where humans beings are turned into living infernos called \n" +
                    "\"Infernals\". While the Infernals are first generation cases of spontaneous human combustion, later \n" +
                    "generations possess the ability to manipulate flames while retaining human form. Shinra \n" +
                    "Kusakabe, a youth who gained the nickname Devil's Footprints for his ability to ignite his feet at \n" +
                    "will, joins the Special Fire Force Company 8 which composes of other flames users as they work to \n" +
                    "extinguish any Infernals they encounter. As a faction that is creating Infernals appears, Shira begins \n" +
                    "to uncover the truth behind a mysterious fire that caused the death of his family twelve years ago."
                    ,"https://image.tmdb.org/t/p/w600_and_h900_bestv2/xwKGTFXL2kKz6P0WI23Q2ecaGOO.jpg","https://image.tmdb.org/t/p/original/zDXuwmqkTi2lGM4AyloAAX4v51P.jpg"
                    ,"ANIMATION | ACTION & ADVENTURE | DRAMA | SCI-FI & FANTASY","July 6, 2019"},
            {"t4","Demon Slayer: Kimetsu no Yaiba"," ","It is the Taisho Period in Japan. Tanjiro, a kindhearted boy who sells charcoal for a living, finds his \n" +
                    "family slaughtered by a demon. To make matters worse, his younger sister Nezuko, the sole \n" +
                    "survivor, has been transformed into a demon herself.\n" +
                    "\n" +
                    "Though devastated by this grim reality, Tanjiro resolves to become a “demon slayer” so that he can \n" +
                    "turn his sister back into a human, and kill the demon that massacred his family.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/taT33NroOl2Fn8bUGj8bwdmNw3G.jpg"
                    ,"https://image.tmdb.org/t/p/original/nTvM4mhqNlHIvUkI1gVnW6XP7GG.jpg","ANIMATION | ACTION & ADVENTURE | DRAMA | SCI-FI & FANTASY","April 6, 2019"},
            {"t5","Do You Love Your Mom and Her Two-Hit Multi-Target Attacks?"," ","Just when Masato thought that a random survey conducted in school was over, he got involved in a \n" +
                    "secret Government scheme. As he was carried along with the flow, he ended up in a Game world! As \n" +
                    "if that wasn't enough, shockingly, his mother was there as well! It was a little different from a \n" +
                    "typical transported to another world setting, but after some bickering,\n" +
                    "\n" +
                    "\"Mom wants go on an adventure together with Maa-kun. Can mom become Maa-kun's companion?\"\n" +
                    "\n" +
                    "With that, Masato and Mamako began their adventure as a mother and son pair. They met Porta, \n" +
                    "a cute traveling merchant, and Wise, a regrettable philosopher. Along with their new party members, \n" +
                    "Masato and co. start on their journey.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/w97kujliFaiddVCZhc2k2qJxkI0.jpg"
                    ,"https://image.tmdb.org/t/p/original/4haPENUcDjwOKKxOw6xlxkOTWPm.jpg","ACTION & ADVENTURE | COMEDY | DRAMA | SCI-FI & FANTASY","July 13, 2019"},
            {"t6","Dr. Stone"," ","The science-fiction adventure follows two boys struggle to revive humanity after a mysterious crisis \n" +
                    "has left everyone in the world turned to stone for several millennia.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/64DIVpWiGlvvJczMnyu8jojSYKm.jpg"
                    ,"https://image.tmdb.org/t/p/original/1Ep6YHL5QcrNC1JN6RYalWRPopi.jpg","ANIMATION | ACTION & ADVENTURE","July 5, 2019"},
            {"t7","Sword Art Online"," ","The players of a virtual reality MMORPG, Sword Art Online, are trapped and fighting for their very l\n" +
                    "ives. After it is announced that the only way to leave the game is by beating it, Kirito—a very \n" +
                    "powerful swordsman—and his friends take on a quest to free all the minds trapped in Aincrad.","https://image.tmdb.org/t/p/original/nc33LuqbsT0g2NYcVTVaMLsvbXu.jpg"
                    ,"https://image.tmdb.org/t/p/original/ldBFxlcow2FYGRBUSVpCYo9jEfO.jpg","ANIMATION | ACTION & ADVENTURE | SCI-FI & FANTASY","July 8, 2012"},
            {"t8","Food Wars!: Shokugeki no Soma","Yoshitomo Yonetani | Shougo Yasukawa","Yukihira Souma's dream is to become a full-time chef in his father's restaurant and surpass his \n" +
                    "father's culinary skill. But just as Yukihira graduates from middle schools his father, Yukihira \n" +
                    "Jouichirou, closes down the restaurant to cook in Europe. Although downtrodden, Souma's \n" +
                    "fighting spirit is rekindled by a challenge from Jouichirou which is to survive in an elite culinary \n" +
                    "school where only 10% of the students graduate. Can Souma survive?","https://image.tmdb.org/t/p/w600_and_h900_bestv2/oSK6MoEScP9V4XF0cPvJ0a66urY.jpg"
                    ,"https://image.tmdb.org/t/p/original/y2SLWOpRhG5wuCV770MoknlJJNp.jpg","ANIMATION | COMEDY","April 4, 2015"},
            {"t9","Re:ZERO -Starting Life in Another World-"," ","Natsuki Subaru, an ordinary high school student, is on his way home from the convenience store \n" +
                    "when he finds himself transported to another world. As he's lost and confused in a new world \n" +
                    "where he doesn't even know left from right, the only person to reach out to him was a beautiful girl \n" +
                    "with silver hair. Determined to repay her somehow for saving him from his own despair, Subaru \n" +
                    "agrees to help the girl find something she's looking for.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/rQEDHdG7PJcM9CMDyd1CwCNvkGA.jpg"
                    ,"https://image.tmdb.org/t/p/original/n6YYb2jMN0UE1YNJcPSHnwX7vMq.jpg","ANIMATION | ACTION & ADVENTURE | SCI-FI & FANTASY | DRAMA | COMEDY","April 4, 2016"},
            {"t10","Attack on Titan","Hajime Isayama","Several hundred years ago, humans were nearly exterminated by Titans. Titans are typically several \n" +
                    "stories tall, seem to have no intelligence, devour human beings and, worst of all, seem to do it for \n" +
                    "the pleasure rather than as a food source. A small percentage of humanity survived by walling \n" +
                    "themselves in a city protected by extremely high walls, even taller than the biggest Titans. Flash \n" +
                    "forward to the present and the city has not seen a Titan in over 100 years. Teenage boy Eren and his \n" +
                    "foster sister Mikasa witness something horrific as the city walls are destroyed by a Colossal Titan \n" +
                    "that appears out of thin air. As the smaller Titans flood the city, the two kids watch in horror as their \n" +
                    "mother is eaten alive. Eren vows that he will murder every single Titan and take revenge for all of \n" +
                    "mankind.","https://image.tmdb.org/t/p/w600_and_h900_bestv2/fBX1QMl4iZOKxuRuBOMnoHlmS4l.jpg"
                    ,"https://image.tmdb.org/t/p/original/jlVhGQkhOXaMyQbMoA5vae9bAmi.jpg","ANIMATION | ACTION & ADVENTURE | SCI-FI & FANTASY | DRAMA | MYSTERY | WAR & POLITICS","April 6, 2013"},
    };

    private static String[][] casts1 = new String[][]{
            {"Makoto Furukawa","Saitama (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/jH6xnU6h1lG799ZhIp1PakbrB2J.jpg"},
            {"Kaito Ishikawa","Genos (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/cJzPuk5BvTdByJY33iCMCsaHONX.jpg"},
            {"Hikaru Midorikawa","Garou (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/pvzfUDE9UruEhRBrONVFk83o6Zh.jpg"},
            {"Kazuhiro Yamaji","Silverfang (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/v44Gi3V2uBysoBdy5c8IyrgBbqM.jpg"},
            {"Yuichi Nakamura","License-less Rider (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/1DNQ7nH2IdWSz8uYeSZoFHIdQA3.jpg"},
    };

    private static String[][] casts2 = new String[][]{
            {"Risa Taneda","Riveria Ljos Alf (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/2R0SbQeHGsyAZF5LzZeN36uRL1E.jpg"},
            {"Saori Oonishi","Aiz Wallenstein (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/7mEqg8cXLioYBFKsKFBk6jOtk28.jpg"},
            {"早见纱织","Ryu Lion (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/sRwSiX28C1V15lGTDHaRQod0QUr.jpg"},
            {"Inori Minase","Hestia (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/rxEyx2LJvLeVzkyaZKkk3l8ZkDu.jpg"},
            {"Yoshitsugu Matsuoka","Bell Cranell (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/7nLyz8eeMkPSdkHZCdcCWKSgja0.jpg"},
    };

    private static String[][] casts3 = new String[][]{
            {"Gakuto Kajiwara","Shinra Kusakabe (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/gfJqbPs06e5L6S2Bn9PAoQEYqdu.jpg"},
            {"Yuusuke Kobayashi","Arthur Boyle (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/40UBOrqznnTDEI2jppnNmprCbM.jpg"},
            {"Saeko Kamijo","Maki Oze (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/9F7WSQLgOEVgjlB33ZHcfGidnO8.jpg"},
            {"Mao Ichimichi","Iris (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/2iulGWjh5lrBcQxkp0J0ZHLVscU.jpg"},
            {"Kazuya Nakai","Akitaru Obi (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/hh4yhrzkDbf3HftDrFoV52SaVil.jpg"},
    };

    private static String[][] casts4 = new String[][]{
            {"Akari Kito","Nezuko Kamado (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/2pN8lCckLZs0MC75eMW2bjDhiX8.jpg"},
            {"Natsuki Hanae","Tanjirou Kamado (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/iy3Ga7ipQ9h62f7RKa9nCAN6wdv.jpg"},
            {"Hiro Shimono","Zenitsu Agatsuma (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/hdAw02HvFHa13ckwIdK72CVtJdK.jpg"},
            {"Yoshitsugu Matsuoka","Inosuke Hashibira (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/7nLyz8eeMkPSdkHZCdcCWKSgja0.jpg"},
            {"Takahiro Sakurai","Giyuu Tomioka (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/1e96Onq8R5EDweBIuLHjZ2Rhe7K.jpg"},
    };

    private static String[][] casts5 = new String[][]{
            {"Satomi Arai","Masumi Shirase (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/5yipcDJBLT5rT3AiEEa8GexZKZl.jpg"},
            {"Sayaka Harada","Porta (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/lwc64SafX4WUs9aXp5fLzPCM3T2.jpg"},
            {"Haruki Ishiya","Masato Ōsuki (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/wvgzfZkGS2JytJ2fBpuzsFmIi2x.jpg"},
            {"Ai Kayano","Mamako Ōsuki (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/gF7FH1XYb5hIOBiVHF7Bw1e4UF4.jpg"},
            {"Lynn","Medhi (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/kxtOF7jy2hhQjWbEuYtPC2UeDTu.jpg"},
    };

    private static String[][] casts6 = new String[][]{
            {"Yuichi Nakamura","Tsukasa (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/1DNQ7nH2IdWSz8uYeSZoFHIdQA3.jpg"},
            {"Manami Numakura","Kohaku (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/qW8ff8ZoHZvp8PDCdY6P9cCAqGG.jpg"},
            {"Kana Ichinose","Yuzuriha (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/3MmHWf5pa1i4s64T2mI6npc26TY.jpg"},
            {"Yuusuke Kobayashi","Senku (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/40UBOrqznnTDEI2jppnNmprCbM.jpg"},
            {"Makoto Furukawa","Taiju (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/jH6xnU6h1lG799ZhIp1PakbrB2J.jpg"},
    };

    private static String[][] casts7 = new String[][]{
            {"Haruka Tomatsu","Asuna / Asuna Yuuki (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/2xbI1uBtj7v6R40WqmyNdbOVUBl.jpg"},
            {"Yoshitsugu Matsuoka","Kirito / Kazuto Kirigaya (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/7nLyz8eeMkPSdkHZCdcCWKSgja0.jpg"},
            {"Kanae Itou","Yui (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/lqDrFqWMLDtgPUwjqLqJ2wMzvyI.jpg"},
            {"Hiroaki Hirata","Klein (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/ynq8qnJyoivjf7qMnCVN2Z7eaKg.jpg"},
            {"Ayahi Takagaki","Lisbeth / Rika Shinozaki (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/3aE6OCzLE4c3cNAPXIAoIrWkZgA.jpg"},
    };

    private static String[][] casts8 = new String[][]{
            {"Risa Taneda","Erina Nakiri (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/2R0SbQeHGsyAZF5LzZeN36uRL1E.jpg"},
            {"Maaya Uchida","Yuki Yoshino (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/oLHM5bivNGJACA9blWf7EDETXZk.jpg"},
            {"Chinatsu Akasaki","Alice Nakiri (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/cmCV7BNdE3qZm1fQiuuGGTyzOOB.jpg"},
            {"Ai Kayano","Ryoko Sakaki (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/gF7FH1XYb5hIOBiVHF7Bw1e4UF4.jpg"},
            {"Yoshitsugu Matsuoka","Soma Yukihira (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/7nLyz8eeMkPSdkHZCdcCWKSgja0.jpg"},
    };

    private static String[][] casts9 = new String[][]{
            {"Yuusuke Kobayashi","Subaru Natsuki (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/40UBOrqznnTDEI2jppnNmprCbM.jpg"},
            {"Rie Takahashi","Emilia (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/9ltjimfvz1W5DJRY5oe6NpkG9sB.jpg"},
            {"Yumi Uchiyama","Pack (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/nyBjGjiuDgX2yLtnfv22557muyi.jpg"},
            {"Chinatsu Akasaki","Felt (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/cmCV7BNdE3qZm1fQiuuGGTyzOOB.jpg"},
            {"Yuichi Nakamura","Reinhard Van Astrea (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/1DNQ7nH2IdWSz8uYeSZoFHIdQA3.jpg"},
    };

    private static String[][] casts10 = new String[][]{
            {"Yuuki Kaji","Eren Jaeger (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/f8IGsmxY9SAWXDRYjL0NSLiiNNp.jpg"},
            {"Yui Ishikawa","Mikasa Ackerman (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/y2IcdASohr0MqVSB8Kaf3IsghOG.jpg"},
            {"Shiori Mikami","Krista Lenz (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/ipCePxoQobnN8xGDXu0wNvn9m8r.jpg"},
            {"Tomohisa Hashizume","Bertolt Hoover (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/kUl8K38qTXz0bZMuGln3JJ2BF4s.jpg"},
            {"Hiroshi Kamiya","Levi (voice)","https://image.tmdb.org/t/p/w600_and_h900_bestv2/5zUN2xtewWvnp8DIMJwCAaACfhH.jpg"},
    };

    public static String[][] checkId(String id){
        switch (id){
            case "t1":
                return casts1;
            case "t2":
                return casts2;
            case "t3":
                return casts3;
            case "t4":
                return casts4;
            case "t5":
                return casts5;
            case "t6":
                return casts6;
            case "t7":
                return casts7;
            case "t8":
                return casts8;
            case "t9":
                return casts9;
            case "t10":
                return casts10;
        }
        return null;
    }

    public static ArrayList<TvShow> getShowData(){
        TvShow tvShow;
        ArrayList<TvShow> list = new ArrayList<>();
        for (String[] adata : data){
            tvShow = new TvShow();
            tvShow.setId_show(adata[0]);
            tvShow.setTitle(adata[1]);
            tvShow.setCreator(adata[2]);
            tvShow.setDescription(adata[3]);
            tvShow.setPoster(adata[4]);
            tvShow.setCover(adata[5]);
            tvShow.setGenres(adata[6]);
            tvShow.setReleaseDate(adata[7]);
            list.add(tvShow);
        }
        return list;
    }

    public static ArrayList<Cast> getCastsData(String[][] castDatas) {
        Cast cast;
        ArrayList<Cast> list = new ArrayList<>();
        for (String[] acast : castDatas){
            cast = new Cast();
            cast.setName(acast[0]);
            cast.setRole(acast[1]);
            cast.setImg_url(acast[2]);
            list.add(cast);
        }
        return list;
    }
}
