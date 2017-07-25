package com.gani.services.tempChandu;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Gani on 7/19/17.
 */
public class ExcelWork {
    private static final String Epro = "/Users/Gani/Desktop/Epro.xlsx";




    public static void main(String[] args) {

        try{

            long currentTime = System.currentTimeMillis();
            FileInputStream excelFile = new FileInputStream(new File(Epro));
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet preDB = workbook.getSheet("Pre DB changes");
            Sheet postDB = workbook.getSheet("Post DB changes");
            Sheet changes;
            if(workbook.getSheet("Changes")!=null)
              workbook.removeSheetAt(workbook.getSheetIndex("Changes"));

            changes = workbook.createSheet("Changes");

//          Used linkedHashMap to preserve order of insertion
            Map<String,Integer> preDBResponseMap = new LinkedHashMap<>();//store http responses for pre DB key: response name
            Map<String,Integer> postDBResponseMap = new LinkedHashMap<>();//store http responses for post DB  value: row number
            Map<String,Integer> preDBTransactionMap = new LinkedHashMap<>();//store transaction responses for pre DB key: transaction name
            Map<String,Integer> postDBTransactionMap = new LinkedHashMap<>();//store transaction responses for post DB value: row number


            getMaps(preDB,preDBResponseMap,preDBTransactionMap);
            getMaps(postDB,postDBResponseMap,postDBTransactionMap);


            int rowNumber=0;

//Objective

            Row currentRow = changes.createRow(rowNumber);
            Cell cell = currentRow.createCell(0);
            cell.setCellValue("Test Objective: 100 users Baseline Test -pre and post whitelist DB changes comparision ");

            changes.addMergedRegion(new CellRangeAddress(rowNumber,rowNumber,0,10));
            rowNumber++;

            currentRow = changes.createRow(rowNumber++);
            currentRow.createCell(0).setCellValue("RunId");
            currentRow.createCell(1).setCellValue("Pre DB Changes");
            currentRow.createCell(3).setCellValue("Post DB Changes");
            currentRow.createCell(5).setCellValue("Difference");

            currentRow = changes.createRow(rowNumber++);
            currentRow.createCell(0).setCellValue("No of users");
            currentRow.createCell(1).setCellFormula("'Pre DB changes'!B6");
            currentRow.createCell(3).setCellFormula("'Post DB changes'!B6");
            currentRow.createCell(5).setCellFormula("D3-B3");



// Metrics data
            currentRow = changes.createRow(rowNumber);
            currentRow.createCell(0).setCellValue("Metrics");

            changes.addMergedRegion(new CellRangeAddress(rowNumber,rowNumber,0,10));
            rowNumber++;

            fillMetricData(changes,rowNumber++,"Total throughput(bytes)","B8",5);
            fillMetricData(changes,rowNumber++,"Average throughput(bytes)","B9",6);
            fillMetricData(changes,rowNumber++,"Total hits","B10",7);
            fillMetricData(changes,rowNumber++,"Average hits","B11",8);
            fillMetricData(changes,rowNumber++,"Total errors","B12",9);
            fillMetricData(changes,rowNumber++,"Total Transactions passed","B13",10);
            fillMetricData(changes,rowNumber++,"Total Transactions failed","B14",11);
            fillMetricData(changes,rowNumber++,"Total Transactions stopped","B15",12);


//Http responses
            currentRow = changes.createRow(rowNumber++);
            currentRow.createCell(0).setCellValue("Http responses");
            currentRow.createCell(1).setCellValue("Total");
            currentRow.createCell(2).setCellValue("per second");
            currentRow.createCell(3).setCellValue("Total");
            currentRow.createCell(4).setCellValue("per second");

            for(String responseName:preDBResponseMap.keySet()){
                currentRow = changes.createRow(rowNumber++);
                currentRow.createCell(0).setCellValue(responseName);
                currentRow.createCell(1).setCellFormula("'Pre DB changes'!B"+(preDBResponseMap.get(responseName)+1));
                currentRow.createCell(2).setCellFormula("'Pre DB changes'!C"+(preDBResponseMap.get(responseName)+1));

                if(postDBResponseMap.containsKey(responseName)){
                    currentRow.createCell(3).setCellFormula("'Post DB changes'!B"+(postDBResponseMap.get(responseName)+1));
                    currentRow.createCell(4).setCellFormula("'Post DB changes'!C"+(postDBResponseMap.get(responseName)+1));
                    postDBResponseMap.remove(responseName);
                }
                else{
                    currentRow.createCell(3).setCellValue(0);
                    currentRow.createCell(4).setCellValue(0);
                }

            }

            for(String responseName:postDBResponseMap.keySet()){
                currentRow = changes.createRow(rowNumber++);
                currentRow.createCell(0).setCellValue(responseName);
                currentRow.createCell(1).setCellValue(0);
                currentRow.createCell(2).setCellValue(0);
                currentRow.createCell(3).setCellFormula("'Post DB changes'!B"+(postDBResponseMap.get(responseName)+1));
                currentRow.createCell(4).setCellFormula("'Post DB changes'!C"+(postDBResponseMap.get(responseName)+1));

            }

//90% response time
            currentRow = changes.createRow(rowNumber++);
            currentRow.createCell(0).setCellValue("90 percent response time");

            currentRow = changes.createRow(rowNumber++);
            currentRow.createCell(0).setCellValue("Transaction Name");
            currentRow.createCell(1).setCellValue("Pre change");
            currentRow.createCell(2).setCellValue("Post change");
            currentRow.createCell(3).setCellValue("Difference");
            currentRow.createCell(4).setCellValue("Percent");


            CellStyle percentStyle = workbook.createCellStyle();
            percentStyle.setDataFormat(workbook.createDataFormat().getFormat("0.000%"));

            for(Map.Entry<String,Integer> e:preDBTransactionMap.entrySet()){
                currentRow = changes.createRow(rowNumber++);
                currentRow.createCell(0).setCellValue(e.getKey());
                currentRow.createCell(1).setCellFormula("'Pre DB changes'!F"+(preDBTransactionMap.get(e.getKey())+1));
                currentRow.createCell(2).setCellFormula("'Post DB changes'!F"+(postDBTransactionMap.get(e.getKey())+1));
                currentRow.createCell(3).setCellFormula("B"+rowNumber+"-C"+rowNumber);
                currentRow.createCell(4).setCellStyle(percentStyle);
                currentRow.getCell(4).setCellFormula("D"+rowNumber+"/B"+rowNumber);

            }


            FileOutputStream outputStream = new FileOutputStream(Epro);
            workbook.write(outputStream);
            workbook.close();

            System.out.println(System.currentTimeMillis()-currentTime);

        }

        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void getMaps(Sheet db, Map<String, Integer> ResponseMap, Map<String, Integer> TransactionMap) {
        String key="";
        boolean flag=false;
        for(Row row:db){
            key=row.getCell(0).getStringCellValue();
            if(key.contains("Http status codes"))
                ResponseMap.put(row.getCell(0).getStringCellValue(),row.getRowNum());
            if(flag){
                TransactionMap.put(row.getCell(0).getStringCellValue(),row.getRowNum());
            }
            if(key.contains("Transaction Name"))
                flag=true;
        }

    }

    private static void fillMetricData(Sheet sheet,int currentRowNumber, String rowName, String cellName, int formula){

        Row row;

        row = sheet.createRow(currentRowNumber);
        row.createCell(0).setCellValue(rowName);
        row.createCell(1).setCellFormula("'Pre DB changes'!"+cellName);
        row.createCell(3).setCellFormula("'Post DB changes'!"+cellName);
        row.createCell(5).setCellFormula("D"+formula+"-B"+formula);
    }
}
