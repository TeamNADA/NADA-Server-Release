package com.nada.server.constants;

public class AnimalYearTable {
    // 상극, 충돌, 원만, 좋음
    // 쥐, 소, 호랑이, 토끼, 용, 뱀, 말, 양, 원숭이, 닭, 개, 돼지
    private static int[][] animalYearTable = {
        {3, 4, 3, 2, 4, 3, 1, 2, 4, 2, 3, 3},
        {4, 3, 3, 3, 2, 4, 2, 1, 3, 4, 2, 3},
        {3, 3, 3, 3, 3, 2, 4, 3, 1, 2, 4, 4},
        {2, 3, 3, 3, 2, 3, 2, 4, 2, 1, 4, 4},
        {4, 2, 3, 2, 2, 3, 3, 3, 4, 4, 1, 2},
        {3, 4, 2, 3, 3, 3, 3, 3, 2, 4, 2, 1},
        {1, 2, 4, 2, 3, 3, 2, 4, 3, 3, 4, 3},
        {2, 1, 3, 4, 3, 3, 4, 3, 3, 3, 2, 4},
        {4, 3, 1, 2, 4, 2, 3, 3, 3, 3, 3, 2},
        {2, 4, 2, 1, 4, 4, 3, 3, 3, 2, 2, 3},
        {3, 2, 4, 4, 1, 2, 4, 2, 3, 2, 3, 3},
        {3, 3, 4, 4, 2, 1, 3, 4, 2, 3, 3, 2}
    };

    private static int getAnimalYearIndex(int birthYear){
        switch(birthYear % 12){
            case 0: // 원숭이
                return 8;
            case 1: // 닭
                return 9;
            case 2:  // 개
                return 10;
            case 3: // 돼지
                return 11;
            case 4: // 쥐
                return 0;
            case 5: // 소
                return 1;
            case 6: // 호랑이
                return 2;
            case 7: // 토끼
                return 3;
            case 8: // 용
                return 4;
            case 9: // 뱀
                return 5;
            case 10: // 말
                return 6;
            case 11: default: // 양
                return 7;
        }
    }

    public static int getHarmony(String myBirthDate, String yourBirthDate){

        int myBirthYear = Integer.parseInt(myBirthDate.split("\\.")[0]);
        int yourBirthYear = Integer.parseInt(yourBirthDate.split("\\.")[0]);

        int harmony;
        switch(animalYearTable[getAnimalYearIndex(myBirthYear)][getAnimalYearIndex(yourBirthYear)]){
            case 1:
                harmony = -30;
                break;
            case 2:
                harmony = -20;
                break;
            case 3:
                harmony = -10;
                break;
            case 4: default:
                harmony = 0;
                break;

        }
        return harmony;
    }

}
