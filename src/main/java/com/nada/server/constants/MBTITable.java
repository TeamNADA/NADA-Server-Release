package com.nada.server.constants;

import java.util.HashMap;
import java.util.Map;

public class MBTITable {

    private static int[][] mbtiTable = {
        {4, 4, 4, 5, 4, 5, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1},
        {4, 4, 5, 4, 5, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1},
        {4, 5, 4, 4, 4, 4, 4, 5, 1, 1, 1, 1, 1, 1, 1, 1},
        {5, 4, 4, 4, 4, 4, 4, 4, 5, 1, 1, 1, 1, 1, 1, 1},
        {4, 5, 4, 4, 4, 4, 4, 5, 3, 3, 3, 3, 2, 2, 2, 2},
        {5, 4, 4, 4, 4, 4, 5, 4, 3, 3, 3, 3, 3, 3, 3, 3},
        {4, 4, 4, 4, 4, 5, 4, 4, 3, 3, 3, 3, 2, 2, 2, 5},
        {4, 4, 5, 4, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 2},
        {1, 1, 1, 5, 3, 3, 3, 3, 2, 2, 2, 2, 4, 5, 4, 5},
        {1, 1, 1, 1, 3, 3, 3, 3, 2, 2, 2, 2, 5, 3, 5, 3},
        {1, 1, 1, 1, 3, 3, 3, 3, 2, 2, 2, 2, 3, 5, 3, 5},
        {1, 1, 1, 1, 3, 3, 3, 3, 2, 2, 2, 2, 5, 3, 5, 3},
        {1, 1, 1, 1, 2, 3, 2, 2, 3, 5, 3, 5, 4, 4, 4, 4},
        {1, 1, 1, 1, 2, 3, 2, 2, 5, 3, 5, 3, 4, 4, 4, 4},
        {1, 1, 1, 1, 2, 3, 2, 2, 3, 5, 3, 5, 4, 4, 4, 4},
        {1, 1, 1, 1, 2, 3, 5, 2, 5, 3, 5, 3, 4, 4, 4, 4}
    };

    private static Map<String, Integer> mbtiMap = new HashMap<String, Integer>(){{
        put("INFP", 0); put("ENFP", 1); put("INFJ", 2); put("ENFJ", 3);
        put("INTJ", 4); put("ENTJ", 5); put("INTP", 6); put("ENTP", 7);
        put("ISFP", 8); put("ESFP", 9); put("ISTP", 10); put("ESTP", 11);
        put("ISFJ", 12); put("ESFJ", 13); put("ISTJ", 14); put("ESTJ", 15);
    }};

    public static int getHarmony(String mine, String yours){
        Integer myIndex = mbtiMap.get(mine);
        Integer yourIndex = mbtiMap.get(yours);

        int harmony;
        switch (mbtiTable[myIndex][yourIndex]){
            case 1:
                harmony = -40;
                break;
            case 2:
                harmony = -30;
                break;
            case 3:
                harmony = -20;
                break;
            case 4:
                harmony = -10;
                break;
            case 5: default:
                harmony = 0;
                break;
        }
        return harmony;
    }
}
