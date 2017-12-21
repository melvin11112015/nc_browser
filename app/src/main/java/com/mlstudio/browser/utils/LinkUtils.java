
package com.mlstudio.browser.utils;

public final class LinkUtils {

	private static String[] goodsHttpStart = {

			"m.taobao.com",
			"m.tmall.com",
			"m.jd.com",//"item.m.jd.com",
			"www.amazon.cn",
			"m.vip.com",
			"m.dangdang.com",
			"m.suning.com",
			"m.yhd.com",
			"m.gome.com.cn",
			"m.jumei.com",
			"m.yixun.com",
			"m.lefeng.com",
			"www.womai.com",
			"m.newegg.cn",

	};


	private static String[] newsReg = {

			"m.taobao.com",


	};


	public static int getLinkType(String url) {
		for (String s : goodsHttpStart) {
			if (url.indexOf(s) > 0) {
				return LinkType.Type_Goods;
			}
		}


		return LinkType.Type_Unknown;
	}


}

