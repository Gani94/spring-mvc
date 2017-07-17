package com.gani.services.tempChandu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gani on 7/8/17.
 */
public class SectorMapper {

    private int[][] matrix;
    private int totalCpsToBeCovered;
    private int[] sectorLimits;

    public int getTotalCpsToBeCovered() {
        return totalCpsToBeCovered;
    }

    public void setTotalCpsToBeCovered(int totalCpsToBeCovered) {
        this.totalCpsToBeCovered = totalCpsToBeCovered;
    }

    public int[] getSectorLimits() {
        return sectorLimits;
    }

    public void setSectorLimits(int[] sectorLimits) {
        this.sectorLimits = sectorLimits;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public Map<Integer,Integer> optimalSectors(){


        Map<Integer,Integer> cpSectorMap = new HashMap<>();//map of cp and the sector its assigned to
        Map<Integer,ArrayList<Integer>> tempCpSectorMap;//temp map to store best possible sector for a given cp
        int bestSector,cpsCovered=0,currentLimit=0;
        int[] sectorRankings = new int[sectorLimits.length];
        int[][] bestSectorVsCPs;
        boolean[] cpsalloted = new boolean[matrix.length];

        while(cpsCovered< totalCpsToBeCovered) {
            tempCpSectorMap = new HashMap<>();

            for (int cp = 0; cp < matrix.length; cp++) {//for each cp, find the sector best suited for it

                if(cpsalloted[cp]) {
                    continue;
                }

                bestSector = 0;
                for (int sector = 0; sector < matrix[0].length; sector++) {
                    if (matrix[cp][sector] > matrix[cp][bestSector])
                        bestSector = sector;
                }
                ArrayList<Integer> temp = tempCpSectorMap.getOrDefault(bestSector,new ArrayList<Integer>());
                temp.add(cp);

                tempCpSectorMap.put(bestSector,temp);
                sectorRankings[bestSector]++;//updating the rankings of each sector by counting how many cps it can serve best
            }

            bestSector = getMaxSector(sectorRankings);//get the best sector for this iteration
            bestSectorVsCPs = sortCpMap(tempCpSectorMap.get(bestSector),bestSector);

            currentLimit = Math.min(sectorLimits[bestSector],bestSectorVsCPs.length);

            int index=0;
            while(currentLimit>0){
                cpSectorMap.put(bestSectorVsCPs[index][1],bestSector);
                cpsalloted[bestSectorVsCPs[index][1]]=true;
                currentLimit--;
                index++;
            }


            cpsCovered += index;//update areas covered by this sector

            //clean up
            Arrays.fill(sectorRankings, 0);//reset rankings
            removeSector(bestSector);//remove the sector from further iterations

        }

        return cpSectorMap;
    }

    private int[][] sortCpMap(ArrayList<Integer> cps, int bestSector) {//create 2D map of cp index and cp value and then sort
        int[][] tempMap = new int[cps.size()][2];
        int index=0;
        for (int cp:cps) {
            tempMap[index][0]=matrix[cp][bestSector];
            tempMap[index][1]=cp;
            index++;
        }

        tempMap = mySorter(tempMap);

        return tempMap;
    }

    private void removeSector(int bestSector) {
        for (int cp = 0; cp < matrix.length; cp++) {//remove the sector from further iterations
            matrix[cp][bestSector] = -1;
        }
    }

    private int[][] mySorter(int[][] map){//to sort cps based on their values
        Arrays.sort(map, (o1, o2) -> o1[0]<o2[0]? 1:-1);

        return map;
    }

    private int getMaxSector(int[] sectorRankings) {



        int maxIndex=0;
        for (int index=0;index<sectorRankings.length;index++) {
            if(sectorRankings[maxIndex]<sectorRankings[index])
                maxIndex=index;
        }

        return maxIndex;
    }

}
