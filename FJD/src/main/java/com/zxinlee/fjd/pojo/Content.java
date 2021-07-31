package com.zxinlee.fjd.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("goods")
@Component
public class Content {
    private String keyword;
    private String title;
    private String img;
    private Integer price;
    private String icons;
}
