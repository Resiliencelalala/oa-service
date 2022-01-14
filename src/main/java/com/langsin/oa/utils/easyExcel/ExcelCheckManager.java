package com.langsin.oa.utils.easyExcel;

import java.util.List;

/**
 * @Description: ${todo}
 * @author: wyy
 * @date: 2020/2/29 9:57
 */
public interface ExcelCheckManager<T> {

    /**
     * @description: 校验方法
     * @param objects
     * @throws
     * @return com.cec.moutai.common.easyexcel.ExcelImportResult
     * @author zhy
     * @date 2019/12/24 14:57
     */
    ExcelImportResult checkImportExcel(List<T> objects);
}

