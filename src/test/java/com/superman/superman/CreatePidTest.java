//package com.superman.superman;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.pdd.pop.sdk.http.PopClient;
//import com.pdd.pop.sdk.http.PopHttpClient;
//import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPidGenerateRequest;
//import com.pdd.pop.sdk.http.api.request.PddDdkOauthGoodsPidGenerateRequest;
//import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPidGenerateResponse;
//import com.superman.superman.dao.SysPidjdDao;
//import com.superman.superman.service.ScoreService;
//import com.superman.superman.utils.SmsSendDemo;
//import com.superman.superman.utils.sign.RandomUtils;
//import com.superman.superman.utils.sms.SmsSendResponse;
//import lombok.extern.java.Log;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Log
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CreatePidTest {
//    @Value("${pdd_pro.pdd-key}")
//    private String PDD_KEY;
//    @Value("${pdd_pro.pdd-secret}")
//    private String PDD_SECRET;
//    @Autowired
//    ScoreService scoreService;
//    @Autowired
//    SysPidjdDao sysPidjdDao;
//
//    /**
//     * 生成拼多多推广位
//     */
//    @Test
//    public void pddPid() {
//        for (int i = 3; i < 47; i++) {
//
//            List<String> pid = new ArrayList<>();
//            for (int j = i * 100; j < (i + 1) * 100; j++) {
//                pid.add("" + RandomUtils.randomSixNum());
//            }
//            PopClient client = new PopHttpClient(PDD_KEY, PDD_SECRET);
//
//            PddDdkGoodsPidGenerateRequest request = new PddDdkGoodsPidGenerateRequest();
//            request.setNumber(Long.valueOf(pid.size()));
//            request.setPIdNameList(pid);
//
//            PddDdkGoodsPidGenerateResponse response = null;
//            try {
//                response = client.syncInvoke(request);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            List<PddDdkGoodsPidGenerateResponse.PIdListItem> pIdList = response.getPIdGenerateResponse().getPIdList();
//            pIdList.forEach(item -> {
//                Integer var = sysPidjdDao.addPidPdd(item.getPId());
//                if (var == 0) {
//                    log.warning("pid插入到数据库失败 PID===" + item.getPId());
//                }
//            });
//        }
//    }
//
//    @Test
//    public void taobaoPid() throws IOException {
//        File file = new File("D:/pid.txt");//目标文件地址
//        InputStreamReader isr = null;
//        try {
//            isr = new InputStreamReader(new FileInputStream(file), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        BufferedReader br = new BufferedReader(isr);
//        String content = br.readLine();
//        br.close();
//        isr.close();
//        JSONObject m = JSONObject.parseObject(content);
//        JSONObject dd = JSONObject.parseObject(m.toJSONString());
//        JSONArray jsonArray = dd.getJSONObject("data").getJSONArray("pagelist");
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject da = (JSONObject) jsonArray.get(i);
//            if (da.getString("siteid").equals("253650051")) {
//                String adzoneid = da.getString("adzoneid");
//                try {
//                    sysPidjdDao.addPidTb(Long.valueOf(adzoneid));
//                } catch (Exception e) {
//                    continue;
//                }
//            }
//
//        }
//        log.warning(dd.toJSONString());
//    }
//}
