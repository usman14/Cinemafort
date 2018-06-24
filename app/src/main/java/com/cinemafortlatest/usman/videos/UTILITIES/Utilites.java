package com.cinemafortlatest.usman.videos.UTILITIES;

import java.text.SimpleDateFormat;

/**
 * Created by usman on 4/18/2017.
 */

public class Utilites {

    public String Genre_name(int a)
    {
        String name = null;
        if(a==28)
        {
         name="Action";
    }
        if(a==12)
        {
            name="Adventure";
        }
        if(a==28)
        {
            name="Action";
        }
        if(a==16)
        {
            name="Animation";
        }
        if(a==35)
        {
            name="Comedy";
        }
        if(a==80)
        {
            name="Crime";
        }
        if(a==28)
        {
            name="Action";
        }
        if(a==99)
        {
            name="Documentary";
        }
        if(a==18)
        {
            name="Drama";
        }
    /**{
        "id": 35,
            "name": "Comedy"
    },
    {
        "id": 80,
            "name": "Crime"
    },
    {
        "id": 99,
            "name": "Documentary"
    },
    {
        "id": 18,
            "name": "Drama"
    },
    {
        "id": 10751,
            "name": "Family"
    },
    {
        "id": 14,
            "name": "Fantasy"
    },
    {
        "id": 36,
            "name": "History"
    },
    {
        "id": 27,
            "name": "Horror"
    },
    {
        "id": 10402,
            "name": "Music"
    },
    {
        "id": 9648,
            "name": "Mystery"
    },
    {
        "id": 10749,
            "name": "Romance"
    },
    {
        "id": 878,
            "name": "Science Fiction"
    },
    {
        "id": 10770,
            "name": "TV Movie"
    },
    {
        "id": 53,
            "name": "Thriller"
    },
    {
        "id": 10752,
            "name": "War"
    },
    {
        "id": 37,
            "name": "Western"
    }*/

        return name;}

        public static String year(String date)
        {
            StringBuilder builder=new StringBuilder();
            builder.append(date.charAt(0)).append(date.charAt(1)).append(date.charAt(2)).append(date.charAt(3));
            return builder.toString();}

    public static String Sorting_Order(int item)
    {
        String value = null;
        if(item==0)
        {
            value="release_date.desc";
        }
        if(item==1)
        {
            value="popularity.desc";
        }
        if(item==2)
        {
            value="revenue.desc";
        }


    return value; }
}
