package com.sxu.data;

import com.sxu.entity.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterpriseLocation {

    public static List<CSVRecord> readCSV(String enterpriseLocationFilePath, String[] headers) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(headers);
        FileReader fileReader = new FileReader(enterpriseLocationFilePath);
        CSVParser csvParser = new CSVParser(fileReader, csvFormat);
        List<CSVRecord> records = csvParser.getRecords();

        csvParser.close();
        fileReader.close();
        return records;
    }

    public static Map<String, Location> getEnterpriseLocationMap(String enterpriseLocationFilePath) throws Exception {
        Map<String, Location> enterpriseLocationMap = new HashMap<String, Location>();

        String[] headers = new String[]{"FID", "企业名称", "中心经度", "中心纬度"};
        List<CSVRecord> records = EnterpriseLocation.readCSV(enterpriseLocationFilePath, headers);
        if (null != records) {
            int size = records.size();
            for (int i = 1; i < size; i++) {
                enterpriseLocationMap.put(records.get(i).get("企业名称"),
                        new Location(Float.parseFloat(records.get(i).get("中心经度")), Float.parseFloat(records.get(i).get("中心纬度"))));
            }
        }

        return enterpriseLocationMap;
    }

    public static void main(String[] args) throws Exception{
        String enterpriseLocationFilePath = System.getProperty("user.dir")+"/src/main/resources/data/enterprise_location.csv";
        Map<String,Location> enterpriseLocationMap = EnterpriseLocation.getEnterpriseLocationMap(enterpriseLocationFilePath);
        for (Map.Entry<String, Location> entry : enterpriseLocationMap.entrySet()) {
            System.out.print(entry.getKey() + "\t");
            System.out.print(entry.getValue());
            System.out.println();
        }
    }
}
