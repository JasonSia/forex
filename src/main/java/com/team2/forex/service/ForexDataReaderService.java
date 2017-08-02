package com.team2.forex.service;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.team2.forex.entity.*;
import com.team2.forex.repository.HistoricalTradeDataRepository;
import com.team2.forex.repository.implementation.HistoricalAuditDataRepositoryImpl;
import com.team2.forex.repository.implementation.HistoricalTradeDataRepositoryImpl;
import com.team2.forex.util.DataFormatCheckUtil;
import com.team2.forex.util.DateTimeUtil;
@Service
public class ForexDataReaderService {

	@Autowired
	private HistoricalTradeDataRepository histRepo;
	
	@Autowired
	private HistoricalAuditDataRepositoryImpl auditRepo;
	
	public void parseCSV() throws ParseException {
		// TODO Auto-generated method stub
		String fileName = "/home/java/git/forex/src/main/resources/HistoricalTradeData.csv";
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int count=0;
        int countInFile = 0;
        try {

            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                // use comma as separator
            	count+=1;
            	Currency buy = Currency.SGD, sell = Currency.EUR;
                String[] data = line.split(cvsSplitBy);
                
                long startTime = System.currentTimeMillis();
                
                boolean isMalformed = DataFormatCheckUtil.checkData(data);
                
                long endTime = System.currentTimeMillis();
                
                if(isMalformed){
                	countInFile+=1;
                	System.out.println("isMalformed");
                	HistoricalAudit histAudit = auditRepo.createHistoricalAuditData(new HistoricalAudit(countInFile, count, fileName, endTime-startTime)); 
              
                } else {
                	System.out.println("isNotMalformed");
                	String[] cur = data[0].split("/");
                    for (Currency c : Currency.values()) {
                    	  if(c.name().equals(cur[0])){
                    		  sell = c;
                    	  } else if(c.name().equals(cur[1])){
                    		  buy = c;
                    	  }
                    }
                    Timestamp t = DateTimeUtil.stringToTimestamp(data[3]);
                    HistoricalTradeData histData = histRepo.createHistoricalTradeData(new HistoricalTradeData(sell, buy, Double.parseDouble(data[1]), Integer.parseInt(data[2]), t)); 
                    
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

}
