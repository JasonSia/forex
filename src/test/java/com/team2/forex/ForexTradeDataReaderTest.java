package com.team2.forex;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.entity.HistoricalAudit;
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.repository.HistoricalAuditDataRepository;
import com.team2.forex.repository.HistoricalTradeDataRepository;
import com.team2.forex.service.ForexDataReaderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ForexApplication.class})
public class ForexTradeDataReaderTest {
	@Autowired
	private ForexDataReaderService fdrs;
	
	@Autowired
	private HistoricalAuditDataRepository auditRepo;
	
	@Autowired
	private HistoricalTradeDataRepository tradeDataRepo;
		
	@Test
	public void historicalAuditDataInsertedInDatabase() throws ParseException{
		fdrs.parseCSV();
		String fileName = "/home/java/git/forex/src/main/resources/HistoricalTradeData.csv";
		HistoricalAudit histAuditData = auditRepo.getHistoricalAuditData(fileName);
		
		assertEquals("checking row num", 7, histAuditData.getFileRowNum());
	}
	
}
