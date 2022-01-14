package com.langsin.oa.utils.easyExcel;

import lombok.Data;

/**
 * @Description: ${todo}
 * @author: wyy
 * @date: 2020/2/29 9:55
 */
@Data
public class ExcelImportErrObjectDto {

    private Object object;

    private String errMsg;

    public ExcelImportErrObjectDto(){}

    public ExcelImportErrObjectDto(Object object,String errMsg){
        this.object = object;
        this.errMsg = errMsg;
    }
}

