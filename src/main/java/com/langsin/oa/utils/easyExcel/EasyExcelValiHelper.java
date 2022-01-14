package com.langsin.oa.utils.easyExcel;
import com.alibaba.excel.annotation.ExcelProperty;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.Set;


/**
 * @Description: ${todo}
 * @author: wyy
 * @date: 2020/2/29 9:51
 */
public class EasyExcelValiHelper {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> String validateEntity(T obj,Integer line) throws NoSuchFieldException, SecurityException {
        StringBuilder result = new StringBuilder();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && set.size() != 0) {
            for (ConstraintViolation<T> cv : set) {
                Field declaredField = obj.getClass().getDeclaredField(cv.getPropertyPath().toString());
                ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
                int num = annotation.index()+1;
//                result.append("第"+line+"行第"+num+"列"+annotation.value()[0]+cv.getMessage()).append(" ");
                result.append("第"+line+"行第"+num+"列"+cv.getMessage()).append(" ");
            }
        }
        return result.toString();
    }

}
