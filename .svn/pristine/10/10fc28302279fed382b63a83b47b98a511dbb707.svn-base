package com.e_road.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestData {
	

	public static final String[] persons = { "肖高丽", "公孙朋义", "霍雨兰", "向清俊",
			"楚嘉云", "朴正青", "李安然", "岳希慕", "晁建中", "钟晶灵", };

	public static final String[] actions = { "订购了北京团结湖路和颐酒店3207号房",
			"购买了马尔代夫卡努拉呼岛Kanuhura Maldives4晚6日自助游特价券",
			"在朝阳区三里屯酒吧街北路18号消费了铁板牛肉和泰式大虾沙拉",
			"在猎聘网发布了市场营销方向的招聘信息", "在也买酒上抢购一份轩尼诗VSOP", "在云上spa购买778元王府井店特价养生套餐",
			"在北京金宝街金宝汇三楼的质馆咖啡小憩", "获得淘宝聚划算感恩节大回馈100元优惠券",
			"关注了SCOFIELD女装韩国衣恋2014秋冬新款专柜正品修身毛呢大衣的11月27日开抢活动", };
	
	public static final String[] urls = {"1","2","3","4","5","6","7","8"};
	
	public static Map<String, String> personAction(){
		StringBuilder builder = new StringBuilder();
		Random random1 = new Random();
		builder.append(persons[random1.nextInt(10)]);
		Random random2 = new Random();
		builder.append(actions[random2.nextInt(9)]);
		Map<String, String> map = new HashMap<String, String>();
		map.put("personAction", builder.toString());
		String url = urls[random2.nextInt(8)];
		map.put("url", url);
		return map;
	}

}
