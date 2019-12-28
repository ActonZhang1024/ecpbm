package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zhang.pojo.Pager;
import pers.zhang.pojo.UserInfo;
import pers.zhang.service.UserInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/23 - 18:32
 */
@Controller
@RequestMapping("/userinfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    //获取合法用户
    @RequestMapping("/getValidUser")
    @ResponseBody
    public List<UserInfo> getValidUser(){
        List<UserInfo> uiList = userInfoService.getValidUser();
        UserInfo ui = new UserInfo();
        ui.setId(0);
        ui.setUserName("请选择...");
        uiList.add(0, ui);
        return uiList;
    }

    //分页获取客户列表
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> userlist(Integer page, Integer rows, UserInfo userInfo) {
        // 创建分页类对象
        Pager pager = new Pager();
        pager.setCurPage(page);
        pager.setPerPageRows(rows);
        // 创建对象params，封装查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userInfo", userInfo);
        // 根据查询条件，获取客户记录数
        int totalCount = userInfoService.count(params);
        // 根据查询条件，分页获取客户列表
        List<UserInfo> userinfos = userInfoService.findUserInfo(userInfo, pager);
        // 创建对象result，保存查询结果数据
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("total", totalCount);
        result.put("rows", userinfos);
        return result;
    }

    //更新客户状态
    @ResponseBody
    @RequestMapping(value = "/setIsEnableUser", produces = "text/heml;charset=UTF-8")
    public String setIsEnableUser(@RequestParam("uids") String uids, @RequestParam("flag") String flag){
        try{
            userInfoService.modifyStatus(uids.substring(0, uids.length() - 1), Integer.parseInt(flag));
            return "{\"success\":\"true\",\"message\":\"更改成功\"}";
        }catch (Exception e){
            return "{\"success\":\"false\",\"message\":\"更改失败\"}";
        }
    }
}
