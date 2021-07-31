package com.zxinlee.fjd.utils;

import com.zxinlee.fjd.pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.Encoder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class HtmlParseUtil {
//    public static void main(String[] args) throws Exception {
//        new HtmlParseUtil().parseJD("daier").forEach(System.out::println);
//    }

    public List<Content> parseJD(String keyword) throws Exception {

        //https://search.jd.com/Search?keyword=java
        //https://search.jd.com/search?keyword=huawei&wq=huawei//华为的电脑

        String encode = URLEncoder.encode(keyword, "UTF-8");//转义编码
        String url = "https://search.jd.com/Search?keyword=" + encode;
        System.out.println(url);
        Document document = Jsoup.parse(new URL(url), 300000);
//        Elements elements = document.getElementsByClass("gl-warp clearfix");
//        System.out.println(elements.html());
        Elements lis = document.getElementsByTag("li");

        ArrayList<Content> goodsList = new ArrayList<>();


        for (Element li : lis) {
            String img = li.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price1 = li.getElementsByClass("p-price").eq(0).text();
            if (price1.equals("") || price1.equals(null)) {
                continue;
            }
            Integer price = Integer.valueOf(price1.substring(1).split("￥")[0].replace(".", "").trim());
            String title = li.getElementsByClass("p-name").eq(0).text();
//            if (title == content.getTitle()) continue;
            String icons = li.getElementsByClass("p-icons").eq(0).text();

//            System.out.println("====================");
//            System.out.println(img);
//           System.out.println(price);
//            System.out.println(title);
//            System.out.println(icons);

            Content content = new Content();
            content.setKeyword(keyword);
            content.setImg(img);
            content.setPrice(price);
            content.setTitle(title);
            content.setIcons(icons);

            goodsList.add(content);

            // goodsList.add(img);
        }
        return goodsList;
    }
}
