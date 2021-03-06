package com.superman.superman.controller;
import com.alibaba.fastjson.JSONObject;
import com.superman.superman.annotation.LoginRequired;
import com.superman.superman.dao.PayDao;
import com.superman.superman.dao.UserinfoMapper;
import com.superman.superman.model.Userinfo;
import com.superman.superman.redis.RedisUtil;
import com.superman.superman.service.MemberService;
import com.superman.superman.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
/**
 * Created by liujupeng on 2018/11/29.
 */
@RestController
@RequestMapping("/team")
public class TeamController {


    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private PayDao payDao;
    /**
     * 查看我的直推会员 （分页）
     * @param request
     * @param pageParam
     * @return
     */
    @LoginRequired
    @PostMapping("/myTeam")
    public WeikeResponse getMyTeam(HttpServletRequest request,PageParam pageParam) {
        String uid = (String) request.getAttribute(Constants.CURRENT_USER_ID);
        if (uid == null) {
            return WeikeResponseUtil.fail(ResponseCode.COMMON_USER_NOT_EXIST);
        }

        String key = "myTeam:"+uid+ pageParam.getPageNo();
        if (redisUtil.hasKey(key)) {
            return WeikeResponseUtil.success(JSONObject.parseObject(redisUtil.get(key)));
        }
        PageParam var1 = new PageParam(pageParam.getPageNo(), pageParam.getPageSize());
        JSONObject data = memberService.getMyTeam(Long.valueOf(uid), var1);
        redisUtil.set(key,data.toJSONString());
        redisUtil.expire(key,6, TimeUnit.SECONDS);
        return WeikeResponseUtil.success(data);
    }


    /**
     * 查看我会员下级 （分页）
     * @param request
     * @param pageParam
     * @return
     */
    @LoginRequired
    @PostMapping("/myFans")
    public WeikeResponse getMyFansNoMe(HttpServletRequest request,PageParam pageParam) {
        String uid = (String) request.getAttribute(Constants.CURRENT_USER_ID);
        if (uid == null) {
            return WeikeResponseUtil.fail(ResponseCode.COMMON_USER_NOT_EXIST);
        }
        String key = "myFans:"+uid+ pageParam.getPageNo();
        if (redisUtil.hasKey(key)) {
            return WeikeResponseUtil.success(JSONObject.parseObject(redisUtil.get(key)));
        }
        PageParam var = new PageParam(pageParam.getPageNo(), pageParam.getPageSize());
        JSONObject data = memberService.getMyNoFans(Long.valueOf(uid), var);
        redisUtil.set(key,data.toJSONString());
        redisUtil.expire(key,5, TimeUnit.SECONDS);
        return WeikeResponseUtil.success(data);
    }
    /**
     * 查看我的手机号和邀请码
     * @param request
     * @return
     */
    @LoginRequired
    @PostMapping("/message")
    public WeikeResponse message(HttpServletRequest request) {
        String uid = (String) request.getAttribute(Constants.CURRENT_USER_ID);
        if (uid == null) {
            return WeikeResponseUtil.fail(ResponseCode.COMMON_USER_NOT_EXIST);
        }
        Integer code = userinfoMapper.queryInvCodeId(Long.valueOf(uid));
        Userinfo userinfo = userinfoMapper.selectByPrimaryKey(Long.valueOf(uid));
        if (userinfo==null){
            return WeikeResponseUtil.fail(ResponseCode.COMMON_USER_NOT_EXIST);
        }
        JSONObject data=new JSONObject();
        data.put("phone",userinfo.getUserphone());
        data.put("code",code);
        return WeikeResponseUtil.success(data);
    }

    /**
     * 查看我的权限
     * @param request
     * @return
     */
    @LoginRequired
    @PostMapping("/myRole")
    public WeikeResponse queryRole(HttpServletRequest request) {
        String uid = (String) request.getAttribute(Constants.CURRENT_USER_ID);
        if (uid == null) {
            return WeikeResponseUtil.fail(ResponseCode.COMMON_USER_NOT_EXIST);
        }
        Integer myRole = userinfoMapper.queryMyRole(Integer.valueOf(uid));
        if (myRole==null){
            return WeikeResponseUtil.fail(ResponseCode.COMMON_USER_NOT_EXIST);
        }
        return WeikeResponseUtil.success(myRole);
    }
    /**
     * 查看会员支付状态
     * @param request
     * @return
     */
    @LoginRequired
    @PostMapping("/isPay")
    public WeikeResponse isPay(HttpServletRequest request) {
        String uid = (String) request.getAttribute(Constants.CURRENT_USER_ID);
        if (uid == null) {
            return WeikeResponseUtil.fail(ResponseCode.COMMON_USER_NOT_EXIST);
        }
        Integer accept = payDao.queryAccept(Integer.valueOf(uid));
        if (accept==null){
            return WeikeResponseUtil.success(0);
        }
        if (accept==0){
            return WeikeResponseUtil.success(1);
        }
        return WeikeResponseUtil.success(2);
    }


}
