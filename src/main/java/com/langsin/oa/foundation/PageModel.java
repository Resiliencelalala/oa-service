package com.langsin.oa.foundation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageModel {
    @ApiModelProperty(value = "大小")
    private Integer pageSize;//大小

    @ApiModelProperty(value = "页数")
    private Integer pageNum;//页数

    public PageModel(Integer pageSize, Integer pageNum) {
        if (null == pageSize)
            pageSize = 10;
        if (null == pageNum)
            pageNum = 1;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }
}
