package com.springboot.chapter9.view;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * PDF导出视图类
 */
public class PdfView extends AbstractPdfView {

    // 导出服务接口
    private PdfExpertService pdfExpertService;

    // 创建对象时载入导出服务接口
    public PdfView(PdfExpertService pdfExpertService) {
        this.pdfExpertService = pdfExpertService;
    }


    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        // 调用接口实现
        pdfExpertService.make(map, document, pdfWriter, httpServletRequest, httpServletResponse);
    }
}
