package com.superman.superman.dao;

import com.superman.superman.dto.SysJhProblem;
import com.superman.superman.dto.SysJhVideoTutorial;
import com.superman.superman.model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysAdviceDao extends BaseDao<SysAdvice> {


    /**
     * 新增提现申请
     * @param applyCash
     * @return
     */
    @Insert("insert into jh_cash_apply (`userId`, `money`,  `audit`,  `createtime`,  `roleid` )value(#{userid},#{money}, 0,now(),#{roleid}) ")
    Integer applyCash(ApplyCash applyCash);


    /**
     * 查询提现申请记录
     *
     * @return
     */
    @Select("select *  from jh_cash_apply  where userId=#{uid} limit #{offset}, #{limit} ")
    List<ApplyCash> queryApplyCash(Map<String,Object> map);

    /**
     * 查询官方通知
     *
     * @return
     */
    @Select("select * from jh_advice_dev limit #{offset}, #{limit} ")
    List<SysJhAdviceDev> queryAdviceDev(Map<String,Object> map);

    /**
     * 轮播图
     *
     * @return
     */
    @Select("select * from jh_banner_good order by id limit 3")
    List<BannerGoods> queryBannerGoods();

    /**
     * 查询常见问题
     *
     * @return
     */
    @Select("select * from jh_problem   limit #{offset}, #{limit} ")
    List<SysJhProblem> querySysJhProblem(Map<String,Object> map);

    /**
     * 查询视频教程
     *
     * @param offset
     * @param limit
     * @return
     */
    @Select("select * from jh_video_tutorial limit #{offset}, #{limit} ")
    List<SysJhVideoTutorial> querySysJhVideoTutorial(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 统计常见问题数量
     *
     * @return
     */
    @Select("select count(id) from jh_problem ")
    Integer countProblem();

    /**
     * 统计视频教程数量
     *
     * @return
     */
    @Select("select count(id) from jh_video_tutorial ")
    Integer countTutorial();

    /**
     * 统计官方通知的数量
     *
     * @return
     */
    @Select("select count(id) from jh_advice_dev")
    Integer countAdvSum();


    /**
     * 查询订单通知（传入uid列表）分页
     *
     * @return
     */
    List<SysJhAdviceOder> querySysOderAdvice(@Param("list") List list, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 统计订单通知的数量（传入uid列表）
     *
     * @return
     */
    Integer countSysOderAdvice(@Param("list") List list);


//    /**
//     * 查询订单通知 代理或者粉丝
//     * @param uid
//     * @param offset
//     * @param limit
//     * @return
//     */
//    @Select("select * from jh_cash_apply  where userId=#{uid} limit #{offset}, #{limit} ")
//    List<ApplyCash> queryOderAdvice(@Param("uid") Integer uid, @Param("offset") Integer offset, @Param("limit") Integer limit);


}
