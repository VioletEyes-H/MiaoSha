package com.bula.ms.service;

import com.bula.ms.dao.UserDao;
import com.bula.ms.entity.User;
import com.bula.ms.exception.GlobalException;
import com.bula.ms.redis.RedisService;
import com.bula.ms.redis.UserKey;
import com.bula.ms.result.CodeMsg;
import com.bula.ms.util.MD5Util;
import com.bula.ms.util.UUIDUtil;
import com.bula.ms.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {
    public static final String COOKIE_NAME_TOKEN = "token";
    @Resource
    private UserDao userDao;

    @Autowired
    private RedisService redisService;

    /**
     * 根据用户id取用户数据
     *
     * @param id
     * @return
     */
    public User getById(long id) {
        //取缓存
        User user = redisService.get(UserKey.getById, "" + id, User.class);
        if (user != null) {
            return user;
        }
        //取数据库
        user = userDao.queryById(id);
        if (user != null) {
            redisService.set(UserKey.getById, "" + id, user);
        }
        return user;
    }

    /**
     * 通过Token获取缓存中的User对象
     *
     * @param response
     * @param token
     * @return
     */
    public User getUserByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        //延长token在有效期
        if (user != null) {
            addCookie(token, user, response);
        }
        return user;
    }

    /**
     * 延长token的有效期
     *
     * @param token
     * @param user
     * @param response
     */
    public void addCookie(String token, User user, HttpServletResponse response) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        cookie.setDomain("api.bula.my");
        response.addCookie(cookie);
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null)
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();//进行一次md5加密的密码
        //判断用户是否存在
        User user = getById(Long.parseLong(mobile));
        if (null == user)
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        //验证密码
        String dbPass = user.getPassword();
        String slat = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password, slat);//把客户端的密码进行二次加密
        if (!calcPass.equals(dbPass))
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        //生成Cookie
        String token = UUIDUtil.uuid();
        addCookie(token, user, response);
        return token;
    }
}
