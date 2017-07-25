package com.gani.services.tempChandu;

import java.util.*;


public class baseStations1 {

    private int[][] matrix;
    private int totalCpesToBeCovered;
    private int sectorLimit;
    private int cpes;

    public int getTotalCpesToBeCovered() {
        return totalCpesToBeCovered;
    }

    public void setTotalCpesToBeCovered(int totalCpesToBeCovered) {
        this.totalCpesToBeCovered = totalCpesToBeCovered;
    }

    public int getSectorLimit() {
        return sectorLimit;
    }

    public void setSectorLimit(int sectorLimit) {
        this.sectorLimit = sectorLimit;
    }

    public int getCpes() {
        return cpes;
    }

    public void setCpes(int cpes) {
        this.cpes = cpes;
    }

    public int getSectors() {
        return sectors;
    }

    public void setSectors(int sectors) {
        this.sectors = sectors;
    }

    private int sectors;

    public int getTotalCpsToBeCovered() {
        return totalCpesToBeCovered;
    }

    public void setTotalCpsToBeCovered(int totalCpesToBeCovered) {
        this.totalCpesToBeCovered = totalCpesToBeCovered;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public Map<Integer,Integer> optimalSectors(){

        Map<Integer,Integer> cpeSectorMap = new HashMap<>();//map of cpe and the sector it is assigned to
        Map<Integer,ArrayList<Integer>> tempCpeSectorMap;//temp map to store best possible sector for a given cpe
        int bestSector,cpesCovered=0,currentLimit=0;
        int[] sectorRankings = new int[matrix[0].length];
        int[][] bestSectorVsCPEs;
        boolean[] cpesalloted = new boolean[matrix.length];

        while(cpesCovered< totalCpesToBeCovered) {
            tempCpeSectorMap = new HashMap<>();

            for (int cpe = 0; cpe < matrix.length; cpe++) {//for each cp, find the sector best suited for it
                if(cpesalloted[cpe]) {
                    continue;
                }

                bestSector = 0;
                for (int sector = 0; sector < matrix[0].length; sector++) {
                    if (matrix[cpe][sector] > matrix[cpe][bestSector])
                        bestSector = sector;
                }
                ArrayList<Integer> temp = tempCpeSectorMap.getOrDefault(bestSector,new ArrayList<Integer>());
                temp.add(cpe);

                tempCpeSectorMap.put(bestSector,temp);
                sectorRankings[bestSector]++;//updating the rankings of each sector by counting how many cpes it can serve the best
            }

            bestSector = getMaxSector(sectorRankings);//get the best sector for this iteration
            bestSectorVsCPEs = sortCpeMap(tempCpeSectorMap.get(bestSector),bestSector);

            currentLimit = Math.min(sectorLimit,bestSectorVsCPEs.length);

            int index=0;
            while(currentLimit>0 && cpesCovered++<totalCpesToBeCovered){
                cpeSectorMap.put(bestSectorVsCPEs[index][1],bestSector);
                cpesalloted[bestSectorVsCPEs[index][1]]=true;
                currentLimit--;
                index++;
            }


            //clean up
            Arrays.fill(sectorRankings, 0);//reset rankings
            removeSector(bestSector);//remove the sector from further iterations

        }

        return cpeSectorMap;
    }

    private int[][] sortCpeMap(ArrayList<Integer> cps, int bestSector) {//create 2D map of cpe index and cpe value and then sort
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
            matrix[cp][bestSector] = Integer.MIN_VALUE;
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

    public static void main(String[] args)
    {


        baseStations1 bs = new baseStations1();
        System.out.println("Enter the number of CPEs and Sectors");
        Scanner scan = new Scanner(System.in);
        bs.setCpes(scan.nextInt());
        bs.setSectors(scan.nextInt());
        System.out.println("Enter the path loss values");

        int[][] tempMatrix = new int[bs.getCpes()][bs.getSectors()];

        for(int i = 0; i< bs.getCpes(); i++){
            for(int j = 0 ;j< bs.getSectors(); j++){
                int value = scan.nextInt();
                tempMatrix[i][j] = value;
            }
        }
        bs.setMatrix(tempMatrix);

        System.out.println("Enter the coverage percent");
        int cov = scan.nextInt();
        bs.totalCpesToBeCovered = (int) ( Math.ceil((double)cov*bs.getCpes()/100));
        System.out.println(bs.totalCpesToBeCovered);
        System.out.println("Enter the threshold limit for sector coverage");
        bs.sectorLimit = scan.nextInt();
        Map<Integer,Integer> myMap= bs.optimalSectors();

        for (Map.Entry entry:myMap.entrySet()) {

            System.out.println("CP "+(int)entry.getKey()+1+" is assigned to sector "+(int)entry.getValue()+1);

        }
        scan.close();
    }
}
