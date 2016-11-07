package com.app.utils;


import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;

/**
 * Created by Administrator on 2016/7/20.
 */
public class HtmlParser {
    public static void main(String[] args) throws Exception{
        String sss = "<p>信息的内容<img src=\"/notify_Web/ueditor/jsp/upload/image/20160717/1468754275613050284.png\" title=\"1468754275613050284.png\" alt=\"11.png\" width=\"564\" height=\"197\"/></p><p>包含图片</p>";
        Parser parser = new Parser(sss);
        TextExtractingVisitor visitor = new TextExtractingVisitor();
        parser.visitAllNodesWith(visitor);
        System.out.println(visitor.getExtractedText());
    }
}
