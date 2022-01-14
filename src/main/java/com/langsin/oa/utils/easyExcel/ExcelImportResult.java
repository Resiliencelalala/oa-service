package com.langsin.oa.utils.easyExcel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: ${todo}
 * @author: wyy
 * @date: 2020/2/29 9:58
 */
@Data
public class ExcelImportResult {
    private List<ExcelImportSucObjectDto> successDtos;

    private List<ExcelImportErrObjectDto> errDtos;

    public ExcelImportResult(List<ExcelImportSucObjectDto> successDtos,List<ExcelImportErrObjectDto> errDtos){
        this.successDtos =successDtos;
        this.errDtos = errDtos;
    }

    public ExcelImportResult(List<ExcelImportErrObjectDto> errDtos){
        this.successDtos =new ArrayList<>();
        this.errDtos = errDtos;
    }
}
