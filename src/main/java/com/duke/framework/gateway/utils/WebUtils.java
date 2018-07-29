package com.duke.framework.gateway.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created duke on 2018/7/29
 */
public class WebUtils {

    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue) {
        addCookie(response, cookieName, cookieValue, 0, "", "", -1, "/", (Boolean) null, (Boolean) null);
    }

    private static void addCookie(HttpServletResponse response,
                                  String cookieName,
                                  String cookieValue,
                                  Integer version,
                                  String comment,
                                  String domain,
                                  Integer maxAge,
                                  String path,
                                  Boolean secure,
                                  Boolean httpOnly) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        if (domain != null) {
            cookie.setDomain(domain);
        }

        if (secure != null) {
            cookie.setSecure(secure);
        }

        if (httpOnly != null) {
            cookie.setHttpOnly(httpOnly);
        }

        if (version != null) {
            cookie.setVersion(version);
        }

        if (comment != null) {
            cookie.setComment(comment);
        }

        response.addCookie(cookie);
    }

    /**
     * 从cookies中提取名称为parameter的cookies值
     *
     * @param request   请求
     * @param parameter cookies名称
     * @return String
     */
    public static String extract(HttpServletRequest request, String parameter) {
        if (request != null && parameter != null) {
            Cookie parameterCookie = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Cookie[] var4 = cookies;
                int var5 = cookies.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    Cookie cookie = var4[var6];
                    if (parameter.equalsIgnoreCase(cookie.getName())) {
                        parameterCookie = cookie;
                        break;
                    }
                }
            }

            if (parameterCookie != null) {
                return parameterCookie.getValue();
            } else {
                String parameterHeaderValue = request.getHeader(parameter);
                if (parameterHeaderValue != null) {
                    return parameterHeaderValue;
                } else {
                    String parameterValue = request.getParameter(parameter);
                    return parameterValue;
                }
            }
        } else {
            return null;
        }
    }

}
