package com.superman.superman.service;

import com.alibaba.fastjson.JSONObject;
import com.superman.superman.model.ScoreBean;

/**
 * Created by liujupeng on 2018/11/14.
 */
public interface ScoreService {
    /**
     * 查询当天积分
     * @param scoreBean
     * @return
     */
    Boolean isExitSign(ScoreBean scoreBean);
    /**
     * 查询当天积分
     * @param
     * @return
     */
    JSONObject myScore(Integer  uid);

    /**
     * 查询浏览商品次数
     * @param uid
     * @return
     */
    Long countLooks(Long uid);

    /**
     *查询浏览分享次数
     * @param uid
     * @return
     */
    Boolean isShare(Long uid);

    /**
     * 记录浏览商品次数
     * @param uid
     * @param goodId
     * @return
     */
    String recordBrowse(String uid,Long goodId);

    /**
     * 增加积分
     * @param scoreBean
     * @return
     */
     Boolean addScore(ScoreBean scoreBean);

    /**
     * 每日签到
     * @param id
     * @return
     */
     Boolean isSign(Long id);
    /**
     * 积分提现
     * @param id
     * @return
     */
     Boolean scoreToCash(Long id);

}
