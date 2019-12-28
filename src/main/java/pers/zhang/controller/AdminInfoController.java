package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pers.zhang.pojo.AdminInfo;
import pers.zhang.pojo.Functions;
import pers.zhang.pojo.TreeNode;
import pers.zhang.service.AdminInfoService;
import pers.zhang.util.JsonFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhang
 * @date 2019/12/22 - 18:23
 */
@Controller
@RequestMapping("/admininfo")
@SessionAttributes(value = {"admin"})
public class AdminInfoController {

    @Autowired
    private AdminInfoService adminInfoService;

    @ResponseBody
    @RequestMapping(value = "/login", produces = "text/html;charset=utf-8")
    public String login(AdminInfo ai, ModelMap model){
        //后台登陆验证
        AdminInfo adminInfo = adminInfoService.login(ai);
        if(adminInfo != null && adminInfo.getName() != null){
            //通过验证后，再判断是否已为该管理员分配功能权限
            if(adminInfoService.getAdmininfoAndFunctions(adminInfo.getId()).getFs().size() > 0){
                //验证通过且已分配功能权限，则将admininfo对象存入model中
                model.put("admin", adminInfo);
                //已JSON格式向页面发送成功信息
                return "{\"success\":\"true\",\"message\":\"登录成功\"}";
            }else{
                return "{\"success\":\"false\",\"message\":\"您没有权限，请联系超级管理员设置权限\"}";
            }
        }else {
            return "{\"success\":\"false\",\"message\":\"登录失败\"}";
        }
    }

    //获取功能菜单数据
    @RequestMapping("/getTree")
    @ResponseBody
    public List<TreeNode> getTree(@RequestParam("adminid") String adminid){
        // 根据管理员编号，获取AdminInfo对象
        AdminInfo admininfo = adminInfoService.getAdmininfoAndFunctions(Integer.parseInt(adminid));
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        // 获取关联的Functions对象集合
        List<Functions> functionsList = admininfo.getFs();
        // 对List<Functions>类型的Functions对象集合排序
        Collections.sort(functionsList);
        // 将排序后的Functions对象集合转换到List<TreeNode>类型的列表nodes
        for (Functions functions : functionsList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(functions.getId());
            treeNode.setFid(functions.getParentid());
            treeNode.setText(functions.getName());
            System.out.println(treeNode.getText());
            nodes.add(treeNode);
        }
        // 调用自定义的工具类JsonFactory的buildtree方法，为nodes列表中的各个TreeNode元素中的
        // children属性赋值(该节点包含的子节点)
        List<TreeNode> treeNodes = JsonFactory.buildtree(nodes, 0);
        return treeNodes;
    }

    // 退出
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public String logout(SessionStatus status) {
        // @SessionAttributes清除
        status.setComplete();
        return "{\"success\":\"true\",\"message\":\"注销成功\"}";
    }
}
