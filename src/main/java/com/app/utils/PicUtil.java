package com.app.utils;

import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/14.
 */
public class PicUtil {

    private Logger logger = Logger.getLogger(this.getClass());

    public static String getPicUrl(HttpServletRequest request, HttpServletResponse response, ServletConfig config) throws ServletException, IOException {

        //处理上传的图片文件
        Part part = request.getPart("file");
        PicUtil picUtil = new PicUtil();
        if (part==null){
            picUtil.logger.error("==>>>part is null <<<==");
            return null;
        }
        //获取存储图片的文件夹
        String path = PropertiesUtil.getValue("picMkdir");
        File f = new File(config.getServletContext().getRealPath(path));
        //如果文件夹不存在则创建文件夹
        if (!f.exists()){
            f.mkdir();
        }
        //为上传的图片重新命名(毫秒数+上传文件的后缀名,如.png)
        String oldFileName = part.getSubmittedFileName();
        if (StringUtil.isEmpty(oldFileName)){
            //如果用户没有选择上传的图片，则返回null
            return null;
        }
        String fileName = ""+System.currentTimeMillis()+StringUtil.getSuffix(oldFileName);
        //把图片文件按照指定的名称写入到指定的文件夹中
        part.write(config.getServletContext().getRealPath(path)+fileName);
        String test = config.getServletContext().getRealPath(path)+fileName;
        //生成图片的url
        String picUrl = GetUrlHead.getUrlHead(request,response)+path+fileName;
        return picUrl;
    }

    //如果内容里面包含有本地上传的图片，则把图片的src替换成完整的url
    public static String replaceImgSrc(HttpServletRequest request, HttpServletResponse response, String content)throws ServletException, IOException {
        if (StringUtil.isEmpty(content)){
            return null;
        }
        StringBuffer cont = new StringBuffer(content);
        for(int i=0;i<cont.length();i++){
            int tag = cont.indexOf("src=\"",i);
            if (tag==-1){
                break;
            }
            //这个判断用于区分图片是网络图片还是本地图片
            if (cont.charAt(tag+5)=='/'){
                cont.insert(tag+5,GetUrlHead.getUrlHead_noProjectName(request,response));
            }
            i=tag+6;
        }
        return cont.toString();
    }

}
