package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.util.StringUtil;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

@Controller
public class CkeditorUpload extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private File upload;
    private String uploadContentType;
    private String uploadFileName;

    public File getUpload() {
        return this.upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadContentType() {
        return this.uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
        return this.uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    @Override
    public String execute() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String callback = ServletActionContext.getRequest().getParameter("CKEditorFuncNum");

        String expandedName = "";
        if ((this.uploadContentType.equals("image/pjpeg")) || (this.uploadContentType.equals("image/jpeg"))) {
            expandedName = ".jpg";
        } else if ((this.uploadContentType.equals("image/png")) || (this.uploadContentType.equals("image/x-png"))) {
            expandedName = ".png";
        } else if (this.uploadContentType.equals("image/gif")) {
            expandedName = ".gif";
        } else if (this.uploadContentType.equals("image/bmp")) {
            expandedName = ".bmp";
        } else {
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
                    + ",'','文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");
            out.println("</script>");
            return null;
        }

        if (this.upload.length() > 614400L) {
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件大小不得大于600k');");
            out.println("</script>");
            return null;
        }

        InputStream is = new FileInputStream(this.upload);
		/*String uploadPath = ServletActionContext.getServletContext().getRealPath(File.separator)
							+ File.separator + "images" + File.separator + "ck_images";*/
        String uploadPath = Constant.IMG_PATH + "ck_images";
        String fileName = StringUtil.getRandomString();
        fileName = fileName + expandedName;
        File toFile = new File(uploadPath, fileName);
        OutputStream os = new FileOutputStream(toFile);

        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        is.close();
        os.close();

        out.println("<script type=\"text/javascript\">");
        String previewPath = "/iborrow/images/ck_images/" + fileName;
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + previewPath + "','')");
        out.println("</script>");
        return null;
    }
}