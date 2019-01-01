package com.sxu.forward;

import com.sxu.timetask.ConventionalControl2CSV;
import com.sxu.timetask.ConventionalControlData2DB;
import com.sxu.util.TimeUtil;

import java.util.Date;
import java.util.Timer;

public class ForwardApplication {


    /**
     * 每隔一天，向清华接口发送post请求并将数据入库：常规管控和任务管控。目前仅需常规管控。
     * <p>
     * 从常规管控数据库中分别找到某一天的24个小时的三种管控方案数据，再按行业分。生成表格到指定路径。
     *
     * @param args
     */
    public static void main(String[] args) {

        Timer timer = new Timer();
        //timer.schedule(new ConventionalControlData2DB(), TimeUtil.get2DBDate(), 86400000);
        timer.schedule(new ConventionalControl2CSV(), TimeUtil.get2CSVDate(), 86400000);
    }

}

