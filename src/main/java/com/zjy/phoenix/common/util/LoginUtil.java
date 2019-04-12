package com.zjy.phoenix.common.util;


import com.jfinal.kit.HashKit;
import com.zjy.phoenix.module.admin.model.User;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class LoginUtil {
    private LoginUtil() {}

    private static final String JWT_TOKEN = Const.JWT_TOKEN;


    /**
     * 密码:md5hex
     */
    public static String pwdEncode(String password) {
        return HashKit.md5(password);
    }


    /**
     * 返回当前用户
     */
    public static Map<String, Object> currentUser(HttpServletRequest request, HttpServletResponse response) {
        // 获取cookie信息
        String userCookie = CookieUtil.getCookie(request, JWT_TOKEN);
        // cookie为空，直接清除
        if (StrKit.isBlank(userCookie)) {
            CookieUtil.removeCookie(response, JWT_TOKEN);
            return null;
        }
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(userCookie);
        } catch (RuntimeException e) {
            CookieUtil.removeCookie(response, JWT_TOKEN);
            return null;
        }
        Object uid = claims.get("uid");
        if (uid == null) {
            CookieUtil.removeCookie(response, JWT_TOKEN);
            return null;
        }
        Map<String, Object> currUser = new HashMap();
        currUser.put("uid", uid);
        currUser.put("realName", claims.get("realName"));
        return currUser;
    }

    /**
     * 用户登陆状态维持
     */
    public static void loginUser(HttpServletRequest request, HttpServletResponse response, User user, boolean... remember) {
        Map<String, Object> data = new HashMap();
        data.put("uid", user.getId());
        data.put("realName", user.getRealName());
        // 超时时间
        int maxAge = -1;
        if (remember.length > 0 && remember[0]) {
            maxAge = 60 * 60 * 24 * 30; // 30天
        }
        String jwt = JwtUtil.createJWT(data, maxAge);

        CookieUtil.setCookie(response, JWT_TOKEN, jwt, maxAge);
        CookieUtil.setCookie(response, "uid", String.valueOf(user.getId()), maxAge);
        CookieUtil.setCookie(response, "username", String.valueOf(user.getUserName()), maxAge);
        CookieUtil.setCookie(response, "realname", String.valueOf(user.getRealName()), maxAge);

    }

    /**
     * 退出即删除用户信息
     */
    public static void logoutUser(HttpServletResponse response) {
        CookieUtil.removeCookie(response, JWT_TOKEN);
    }
}
