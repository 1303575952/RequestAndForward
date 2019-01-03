package com.sxu.data;

import com.sxu.entity.EnterpriseProperty;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterpriseInfoData {

    //public static Map<String, EnterpriseProperty> enterprisePropertyMap = new HashMap<String, EnterpriseProperty>();


    public static List<CSVRecord> readCSV(String filePath, String[] headers) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(headers);
        FileReader fileReader = new FileReader(filePath);
        CSVParser csvParser = new CSVParser(fileReader, csvFormat);
        List<CSVRecord> records = csvParser.getRecords();

        csvParser.close();
        fileReader.close();
        return records;
    }

    public static void writeCsv(String[] headers, String[][] data, String filePath) throws IOException {
        final String NEW_LINE_SEPARATOR = "\n";
        //初始化csvformat
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        //创建FileWriter对象
        FileWriter fileWriter = new FileWriter(filePath);
        //创建CSVPrinter对象
        CSVPrinter printer = new CSVPrinter(fileWriter, formator);
        //写入列头数据
        printer.printRecord(headers);
        if (null != data) {
            //循环写入数据
            for (String[] lineData : data) {
                printer.printRecord(lineData);
            }
        }
        printer.flush();
        printer.close();
        fileWriter.close();
        System.out.println("CSV文件创建成功,文件路径:" + filePath);
    }

    /**
     * 拿到企业与对应位置，格子
     *
     * @param
     * @return
     * @throws Exception
     */
    public static Map<String, EnterpriseProperty> getEnterprisePropertyMap(String enterpriseFilePath) throws Exception {
        Map<String, EnterpriseProperty> enterprisePropertyMap = new HashMap<String, EnterpriseProperty>();
        String[] headers = new String[]{"企业编号", "企业名称", "企业分类", "企业经度", "企业纬度", "格子0", "排口名称"};
        List<CSVRecord> records = EnterpriseInfoData.readCSV(enterpriseFilePath, headers);
        if (null != records) {
            int size = records.size();
            for (int i = 1; i < size; i++) {
                enterprisePropertyMap.put(records.get(i).get("企业名称"),
                        new EnterpriseProperty(records.get(i).get("企业分类"), Integer.valueOf(records.get(i).get("格子0"))));
            }
        }
        return enterprisePropertyMap;
    }

    public static void main(String[] args) throws Exception {
        String enterpriseLocationFilePath = System.getProperty("user.dir") + "/src/main/resources/data/enterprise_location.csv";
        Map<String, EnterpriseProperty> enterprisePropertyMap = EnterpriseInfoData.getEnterprisePropertyMap(enterpriseLocationFilePath);
        for (Map.Entry<String, EnterpriseProperty> entry : enterprisePropertyMap.entrySet()) {
            System.out.print(entry.getKey() + "\t");
            System.out.print(entry.getValue());
            System.out.println();
        }
    }
}
