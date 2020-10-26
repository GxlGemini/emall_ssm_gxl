package com.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author 作者      ：dty董桐月
 * @date 日期      ：Created in 2020/9/24 11:39
 * @desc 类名      ：
 */
public class PageUtil {

    /**
     * 获取分页代码
     *
     * @param request
     * @param total   总记录数
     * @param page    当前页面
     * @param size    每页参数
     * @return
     */
    public static String getPageHtml(HttpServletRequest request, long total, int page, int size) {
        if (total <= 0) {
            return null;
        }
        //计算总页数
        int pages = (int) (total % size == 0 ? total / size : total / size + 1);
        pages = pages == 0 ? 1 : pages;

        //请求地址
        String url = request.getRequestURL().toString();
        //请求参数
        StringBuilder paramBuilder = new StringBuilder();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            if (param.indexOf("page") > -1) {
                continue;
            }
            paramBuilder.append("&").append(param).append("=").append(request.getParameter(param));
        }

        //分页字符串
        StringBuilder pageBuilder = new StringBuilder();
        pageBuilder.append("<div class='holder'>");
        //上一页
        if (page <= 1) {
//            pageBuilder.append("<a title='已是首页'> 首页 </a>");
//            pageBuilder.append("<a title='已是首页'> 上页 </a>");
        } else {
            pageBuilder.append("<a href='").append(url).append("?").append("page=1")
                    .append(paramBuilder).append("'>首页</a>");
            pageBuilder.append("<a href='").append(url).append("?").append("page=").append(page - 1)
                    .append(paramBuilder).append("'>上页</a>");

        }
        //中间数字页码
        if (pages <= 7) {
            //显示全部
            for (int i = 1; i <= pages; i++) {
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, i));
            }
        } else {
            //显示部分
            if (page < 4 || page > pages - 3) {// 1 2 3 ... pages-2 pages-1 pages
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 2));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 3));
                pageBuilder.append("...");
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages - 2));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages - 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages));
            } else {// 1 2 ... page-1 page page+1 ... pages-1 pages
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, 2));
                pageBuilder.append("...");
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, page - 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, page));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, page + 1));
                pageBuilder.append("...");
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages - 1));
                pageBuilder.append(packPageItem(url, paramBuilder.toString(), page, pages));
            }
        }

        //下一页
        if (page >= pages) {
//            pageBuilder.append("<a title='已是尾页'> 下页 </a>");
//            pageBuilder.append("<a title='已是尾页'> 尾页 </a>");
        } else {
            pageBuilder.append("<a href='").append(url).append("?").append("page=").append(page+1)
                    .append(paramBuilder).append("'>下页</a>");
            pageBuilder.append("<a href='").append(url).append("?").append("page=").append(pages)
                    .append(paramBuilder).append("'>尾页</a>");

        }
        pageBuilder.append("</div>");
        return pageBuilder.toString();
    }


    /**
         * 封装分页项
         * @param url
         * @param params
         * @param page
         * @param i
         * @return
         */
        private static String packPageItem(String url, String params, int page, int i) {
            StringBuilder pageBuilder = new StringBuilder();
            if (i == page) {
                pageBuilder.append("<a class='jp-current'>").append(i).append("</a>");
            } else {
                pageBuilder.append("<a title='第").append(i).append("页' href='").append(url).append("?").append("page=").append(i)
                        .append(params).append(" '>");
                pageBuilder.append(i).append("</a>");
            }
            return pageBuilder.toString();
        }


}
