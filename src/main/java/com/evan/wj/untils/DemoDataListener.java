package com.evan.wj.untils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.evan.wj.dao.BookDAO;
import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.BookVO;
import com.evan.wj.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DemoDataListener extends AnalysisEventListener<BookVO> {


    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<BookVO> list = new ArrayList<BookVO>();

private BookService bookService;
    public DemoDataListener(BookService bookService) {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        this.bookService = bookService;
    }
    @Override
    public void invoke(BookVO data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", data.toString());
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            //入库操作
            saveData();
            // 存储完成清理 list
            list.clear();
        }

    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        // mybatis  batchinsert 批量存储入库
       bookService.batchInsert(list);
        LOGGER.info("存储数据库成功！");
    }

}
