package hang.pulllog.data.impl;

import com.hang.pulllog.data.IDataWrite;
import com.hang.pulllog.http.HttpUtils;
import com.hang.pulllog.log.impl.ILogMerge;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ZhangHang
 * @create 2018-05-09 10:37
 **/
public class DataWriteImpl implements IDataWrite {
    private final static Integer SIZE = 500;
    private static int i = 0;

    private Map<String, Workbook> workbooks;
    private Map<String, CellStyle> cellStyles;
    private List<String> cellNameList;
    private Map<String, String> filePaths;

    private CountDownLatch countDownLatchEnd;
    public LinkedBlockingQueue<Map<String, String>> linkedQueue = new LinkedBlockingQueue<Map<String, String>>();

    private ILogMerge logMerge;

    public DataWriteImpl(Map<String, Workbook> workbooks, List<String> cellNameList, CountDownLatch countDownLatchEnd, Map<String, String> filePaths, ILogMerge logMerge) {
        this.workbooks = workbooks;
        this.cellNameList = cellNameList;
        this.countDownLatchEnd = countDownLatchEnd;
        this.filePaths = filePaths;
        this.logMerge = logMerge;
        generateStyle();
    }

    @Override
    public void offerData(List<Map<String, String>> mapList, String serverId) {
        for (Map<String, String> dataMap : mapList) {
            dataMap.put("serverId", serverId);
            if (logMerge != null) {
                logMerge.merge(dataMap, linkedQueue);
            } else {
                linkedQueue.offer(dataMap);
            }

            if (linkedQueue.size() >= SIZE) {
                generateExcelData();
            }
        }
    }

    /**
     * 当日志数据全部取回来后，将剩下的所有数据全部转换Excel数据
     */
    @Override
    public void ultimateData() throws InterruptedException, IOException {
        while (linkedQueue.peek() != null) {
            transformExcelData();
        }

        //将Excel数据全部写入Excel文件
        extractWorkbook();
        //关闭线程池
        HttpUtils.closeThreadPool();
        //关闭文件读写
        for (Map.Entry<String, Workbook> entry : workbooks.entrySet()) {
            entry.getValue().close();
        }
        System.out.println("生成成功！！");
    }

    /**
     * 将缓存数据生成Excel数据
     */
    private void generateExcelData() {
        for (int i = 0; i < SIZE; i++) {
            transformExcelData();
        }
    }

    /**
     * 生成Workbook数据源
     */
    private void transformExcelData() {
        Workbook workbook = null;
        String filePath = null;
        CellStyle cellStyle = null;
        Map<String, String> filteredResult = linkedQueue.poll();
        if (workbooks.containsKey("-1")) {
            workbook = workbooks.get("-1");
            filePath = filePaths.get("-1");
            cellStyle = cellStyles.get("-1");
        } else {
            workbook = workbooks.get(filteredResult.get("serverId"));
            filePath = filePaths.get(filteredResult.get("serverId"));
            cellStyle = cellStyles.get(filteredResult.get("serverId"));
        }
        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        //设置服务器序列号
        row.createCell(0).setCellStyle(cellStyle);
        row.createCell(0).setCellValue(filteredResult.get("serverId"));
        //设置其它获取数据
        for (int cell = 1; cell < cellNameList.size(); cell++) {
            row.createCell(cell).setCellStyle(cellStyle);
            row.createCell(cell).setCellValue(filteredResult.get(cellNameList.get(cell)));
        }
    }


    /**
     * 将Workbook数据提取出来
     */
    private void extractWorkbook() {
        Workbook workbook = null;
        String filePath = null;
        if (workbooks.containsKey("-1")) {
            workbook = workbooks.get("-1");
            filePath = filePaths.get("-1");
            feedExcel(workbook, filePath);
        } else {
            for (Map.Entry<String, Workbook> workbookEntry : workbooks.entrySet()) {
                workbook = workbookEntry.getValue();
                filePath = filePaths.get(workbookEntry.getKey());
                feedExcel(workbook, filePath);
            }
        }

    }

    /**
     * 将Excel数据写入Excel文件中
     */
    private void feedExcel(Workbook workbook, String filePath) {
        try {
            File file = new File(filePath + ".xlsx");
            FileOutputStream fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            fileoutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Excel生成样式
     */
    private void generateStyle() {
        if (cellStyles == null) {
            cellStyles = new HashMap<String, CellStyle>();
        }

        for (Map.Entry<String, Workbook> workbookEntry : workbooks.entrySet()) {
            Workbook workbook = workbookEntry.getValue();
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyles.put(workbookEntry.getKey(), cellStyle);
        }
    }
}
