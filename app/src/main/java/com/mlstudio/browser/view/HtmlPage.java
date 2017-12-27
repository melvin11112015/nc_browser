package com.mlstudio.browser.view;

import com.mlstudio.browser.utils.LinkType;
import com.mlstudio.browser.utils.URLUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by xuming on 2015/10/27.
 */
public class HtmlPage {
    private static HashMap<String, HtmlRule> hashMap = new HashMap<>();
    private static  boolean inited=false;
    private String title = "";
    private String img = "";
    private String url = "";
    private String html = "";

    public HtmlPage(String url, String html) {
        this.url = url;
        this.html = html;
        parseHtml(url, html);
    }

    static void init() {
        if (inited) {
            return;
        }
        //Pattern p = Pattern.compile("(mouse|cat|dog|wolf|bear|human)");
        hashMap.put("view.inews.qq.com", new HtmlRule("^https?://view.inews.qq.com/a/.+", "#content p.title", "#content img",LinkType.Type_News));
        inited = true;
    }

    public static int getLinkType(String url) {
        init();
        String host = URLUtil.getHostOnly(url);
        if (hashMap.containsKey(host)) {
            HtmlRule rule = hashMap.get(host);
            Pattern pattern = Pattern.compile(rule.urlPatten);
            Matcher mc = pattern.matcher(url);
            if (mc.matches()) {
                return rule.type;
            }

        }


        return LinkType.Type_Unknown;
    }

    public String getHtml() {
        return html;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void parseHtml(String url,String html) {
        init();
        String host = URLUtil.getHostOnly(url);
        Document doc = Jsoup.parse(html);
        int type = LinkType.Type_Unknown;
        if (hashMap.containsKey(host)) {

            HtmlRule rule = hashMap.get(host);
            Pattern pattern = Pattern.compile(rule.urlPatten);
            Matcher mc = pattern.matcher(url);
            if (mc.matches()) {

                title = doc.select(rule.titleSelector).first().text();
                img = doc.select(rule.imgSelector).first().attr("src");
                type = rule.type;
                //ToastUtil.showMessage(host + ":" + title);
                return;
            }

        }

        title = doc.select("title").text();
        img = "";
        type = LinkType.Type_Unknown;
        //ToastUtil.showMessage(host + ":" + title);
    }

    public static class HtmlRule {
        public String urlPatten = "";
        public String titleSelector = "";
        public String imgSelector = "";
        public int type = LinkType.Type_Unknown;

        public HtmlRule(String urlPatten, String titleSelector, String imgSelector, int type) {
            this.titleSelector = titleSelector;
            this.urlPatten = urlPatten;
            this.imgSelector = imgSelector;
            this.type = type;
        }

    }


}
