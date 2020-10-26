package com.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

public class PageHtmlUtils {
    public static String getPageHtml(HttpServletRequest request, long total, int page, int size) {
        if (total <= 0) {
            return null;
        }
        int pages = (int) ((total % size) == 0 ? (total / size) : (total / size) + 1);
        pages = pages == 0 ? 1 : pages;
        String url = request.getRequestURL().toString();
        StringBuilder paramBuilder = new StringBuilder();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            if (param.indexOf("page") > -1) {
                continue;
            }
            paramBuilder.append("&").append(param).append("=").append(request.getParameter(param));
        }
        StringBuilder pageBuilder = new StringBuilder();
        pageBuilder.append("<div class='holder'>");
        if (page <= 1) {

        } else {
            pageBuilder.append("<a href='").append(url).append("?").append("page=1").append(paramBuilder).append("'>首页</a>");
            pageBuilder.append("<a href='").append(url).append("?").append("page=").append(page - 1).append(paramBuilder).append("'>上页</a>");
        }
        if (page <= 7) {
            for (int i = 1; i <= pages; i++) {
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, i));
            }
        } else {
            if (page < 4 || page > pages - 3) {
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 2));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 3));
                pageBuilder.append(" ... ");
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages - 2));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages - 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages));
            } else {
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 2));
                pageBuilder.append(" ... ");
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, page - 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, page));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, page + 1));
                pageBuilder.append(" ... ");
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages - 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages));
            }
        }
        if (page > pages) {

        } else {
            pageBuilder.append("<a href='").append(url).append("?").append("page=").append(page + 1).append(paramBuilder).append("'>下页</a>");
            pageBuilder.append("<a href='").append(url).append("?").append("page=").append(pages).append(paramBuilder).append("'>尾页</a>");
        }
        pageBuilder.append("</div>");
        return pageBuilder.toString();
    }

    private static String packPageItem(String url, String params, int page, int i) {
        StringBuilder pageBuilder = new StringBuilder();
        if (i == page) {
            pageBuilder.append("<a class='jp-current'>").append(i).append("</a>");
        } else {
            pageBuilder.append("<a title='第").append(i).append("页' href='").append(url).append("?").append("page=").append(i)
                    .append(params).append("'>");
            pageBuilder.append(i).append("</a>");
        }
        return pageBuilder.toString();
    }

    public static String getPageTool(HttpServletRequest request, long total, int page, int size) {
        long pages = total % size == 0 ? total / size : total / size + 1;
        pages = pages == 0 ? 1 : pages;
        String url = request.getRequestURL().toString();
        StringBuilder queryString = new StringBuilder();
        Enumeration<String> enumeration = request.getParameterNames();
        try {
            while (enumeration.hasMoreElements()) {
                String element = (String) enumeration.nextElement();
                if (!element.contains("page")) {
                    queryString.append("&").append(element).append("=").append(java.net.URLEncoder.encode(request.getParameter(element), "UTF-8"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder buf = new StringBuilder();
        buf.append("<div style='text-align:center;'>\n");
        if (page <= 1) {
            buf.append("<a class='btn btn-info' disabled >首页</a>\n");
        } else {
            buf.append("<a class='btn btn-info' href='").append(url).append("?page=").append(1).append(queryString).append("'>首页</a>\n");
        }
        if (page <= 1) {
            buf.append("<a class='btn btn-info' disabled >上一页</a>\n");
        } else {
            buf.append("<a class='btn btn-info' href='").append(url).append("?page=").append(page > 1 ? page - 1 : 1).append(queryString).append("'>上一页</a>\n");
        }
        buf.append("<h2 style='display:inline;'>[").append(page).append("/").append(pages).append("]</h2>\n");
        buf.append("<h2 style='display:inline;'>[").append(total).append("]</h2>\n");
        if (page >= pages) {
            buf.append("<a class='btn btn-info' disabled >下一页</a>\n");
        } else {
            buf.append("<a class='btn btn-info' href='").append(url).append("?page=").append(page < pages ? page + 1 : pages).append(queryString).append("'>下一页</a>\n");
        }
        if (page >= pages) {
            buf.append("<a class='btn btn-info' disabled >尾页</a>\n");
        } else {
            buf.append("<a class='btn btn-info' href='").append(url).append("?page=").append(pages).append(queryString).append("'>尾页</a>\n");
        }
        buf.append("<input type='text' class='form-control' style='display:inline;width:60px;' value=''/>");
        buf.append("<a class='btn btn-info' href='javascript:void(0);' onclick='location.href=\"").append(url).append("?page=").append("\"+(this.previousSibling.value)+\"").append(queryString).append("\"'>GO</a>\n");
        buf.append("</div>\n");
        return buf.toString();
    }
}
