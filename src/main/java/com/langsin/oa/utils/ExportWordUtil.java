package com.langsin.oa.utils;

import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

@Slf4j
public class ExportWordUtil {
    private Configuration config;

    public ExportWordUtil() {
        config = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        config.setDefaultEncoding("utf-8");
    }

    /**
     * FreeMarker生成Word
     *
     * @param dataMap      数据
     * @param templateName 模板名
     * @param response     HttpServletResponse
     * @param fileName     导出的word文件名
     */
    public void exportWord(Map<String, Object> dataMap, String templateName, HttpServletResponse response, String fileName) {
        //加载模板(路径)数据，也可使用setServletContextForTemplateLoading()方法放入到web文件夹下
        config.setClassForTemplateLoading(this.getClass(), "/templates");
        //设置异常处理器 这样的话 即使没有属性也不会出错 如：${list.name}...不会报错
        config.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        PrintWriter out = null;
        try {
            if (templateName.endsWith(".ftl")) {
                templateName = templateName.substring(0, templateName.indexOf(".ftl"));
            }
            Template template = config.getTemplate(templateName + ".ftl", "utf-8");
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + new String("警报信息.doc".getBytes("GBK"), "iso8859-1") + "\"");
            response.setCharacterEncoding("utf-8");//此句非常关键,不然word文档全是乱码
            out = response.getWriter();

            //将模板中的预先的代码替换为数据
            template.process(dataMap, out);
            log.info("由模板文件：" + templateName + ".ftl" + " 生成Word文件 " + fileName + " 成功！！");
        } catch (TemplateNotFoundException e) {
            log.info("模板文件未找到");
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            log.info("模板类型不正确");
            e.printStackTrace();
        } catch (TemplateException e) {
            log.info("填充模板时异常");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("IO异常");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

}
