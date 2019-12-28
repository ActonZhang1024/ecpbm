package pers.zhang.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zhang.pojo.OrderDetail;
import pers.zhang.pojo.OrderInfo;
import pers.zhang.pojo.Pager;
import pers.zhang.service.OrderInfoService;
import pers.zhang.service.ProductInfoService;
import pers.zhang.service.UserInfoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/23 - 19:19
 */
@Controller
@RequestMapping("/orderinfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProductInfoService productInfoService;

    //保存订单
    @ResponseBody
    @RequestMapping("/commitOrder")
    public String commitOrder(String inserted, String orderinfo) throws JsonParseException, JsonMappingException, IOException{
        try{
            //创建ObejectMapper对象，实现JavaBean和JSON转换
            ObjectMapper mapper = new ObjectMapper();
            //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
            mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            //将json字符串orderinfo转换成JavaBean对象（订单信息）
            OrderInfo oi = mapper.readValue(orderinfo, OrderInfo[].class)[0];
            //保存订单信息
            orderInfoService.addOrderInfo(oi);
            //将JSON字符串转换重List<OrderDetail>集合（订单明细信息）
            List<OrderDetail> odList =  mapper.readValue(inserted, new TypeReference<ArrayList<OrderDetail>>(){});
            //给订单明细对象的其他属性赋值
            for(OrderDetail od : odList){
                od.setOid(oi.getId());
                //保存订单明细
                orderInfoService.addOrderDetail(od);
            }
            return "success";
        }catch (Exception e){
            return "failure";
        }
    }

    //分页显示
    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(Integer page, Integer rows, OrderInfo orderInfo){
        //初始化一个分页类对象pager
        Pager pager = new Pager();
        pager.setCurPage(page);
        pager.setPerPageRows(rows);
        //创建对象params，用于封装查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderInfo", orderInfo);
        //获取满足条件的订单总数
        Integer totalCount = orderInfoService.count(params);
        //获取满足条件的订单列表
        List<OrderInfo> orderinfos = orderInfoService.findOrderInfo(orderInfo, pager);
        //创建result对象，保存查询结果数据
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("total", totalCount);
        result.put("rows", orderinfos);
        return result;
    }

    //删除订单
    @ResponseBody
    @RequestMapping(value = "/deleteOrder", produces = "text/html;charset=UTF-8")
    public String deleteOrder(String oids){
        String str = "";
        try{
            oids = oids.substring(0, oids.length() - 1);
            String[] ids = oids.split(",");
            for(String id : ids){
                orderInfoService.deleteOrder(Integer.parseInt(id));
            }
            str = "{\"success\":\"true\",\"message\":\"删除成功！\"}";
        }catch (Exception e){
            str = "{\"success\":\"false\",\"message\":\"删除失败！\"}";
        }
        return str;
    }

    //根据订单id获取要查看的订单对象，再返回订单明细也
    @RequestMapping("/getOrderInfo")
    public String getOrderInfo(String oid, Model model){
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(Integer.parseInt(oid));
        model.addAttribute("oi", orderInfo);
        return "orderdetail";
    }

    //根据订单id号获取订单明细列表
    @ResponseBody
    @RequestMapping("/getOrderDetails")
    public List<OrderDetail> getOrderDetails(String oid){
        List<OrderDetail> ods = orderInfoService.getOrderDetailById(Integer.parseInt(oid));
        for(OrderDetail od : ods){
            od.setPrice(od.getPi().getPrice());
            od.setTotalprice(od.getPi().getPrice() * od.getNum());
        }
        return ods;
    }
}
