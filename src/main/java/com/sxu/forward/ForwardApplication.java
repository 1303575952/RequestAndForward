package com.sxu.forward;

import com.sxu.timetask.ConventionalControl2CSV;
import com.sxu.timetask.ConventionalControlData2DB;
import com.sxu.util.TimeUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Timer;

//@SpringBootApplication
public class ForwardApplication {

	/*public static void main(String[] args) {
		SpringApplication.run(ForwardApplication.class, args);
	}*/

	/**
	 * 每隔一天，向清华接口发送post请求并将数据入库：常规管控和任务管控。目前仅需常规管控。
	 *
	 * 从常规管控数据库中分别找到某一天的24个小时的三种管控方案数据，再按行业分。生成表格到指定路径。
	 * @param args
	 */
	public static void main(String[] args) {

		Timer timer = new Timer();
		timer.schedule(new ConventionalControlData2DB(),TimeUtil.getSynDBTime());
		timer.schedule(new ConventionalControl2CSV(),TimeUtil.getSynCSVTime());
	}

}

