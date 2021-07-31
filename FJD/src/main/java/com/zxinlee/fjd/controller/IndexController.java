package com.zxinlee.fjd.controller;

import com.zxinlee.fjd.mapper.ContentMapper;
import com.zxinlee.fjd.pojo.Content;
import com.zxinlee.fjd.utils.HtmlParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private ContentMapper contentMapper;

    @GetMapping("/getGood/{keyword}")
    public boolean getGoods(@PathVariable String keyword) throws Exception {
        List<Content> contents = new HtmlParseUtil().parseJD(keyword);
        //contents.forEach(good->contentMapper.insert(good));
        for (Content good : contents) {
            if (good.getTitle().equals("")){
                continue;
            }
            contentMapper.insert(good);
        }
        return true;
    }
}
