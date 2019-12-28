package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zhang.pojo.Type;
import pers.zhang.service.TypeService;

import java.util.List;

/**
 * @author zhang
 * @date 2019/12/23 - 0:00
 */
@Controller
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @ResponseBody
    @RequestMapping("/getType/{flag}")
    public List<Type> getType(@PathVariable("flag") Integer flag){
        List<Type> typeList = typeService.getAll();
        if(flag == 1){
            Type type = new Type();
            type.setId(0);
            type.setName("请选择...");
            typeList.add(0, type);
        }
        return typeList;
    }

    //添加商品类型
    @RequestMapping(value = "/addType", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addType(Type type) {
        try {
            typeService.addType(type);
            return "{\"success\":\"true\",\"message\":\"添加成功\"}";
        } catch (Exception e) {
            return "{\"success\":\"false\",\"message\":\"添加失败\"}";
        }
    }

    //修改商品类型
    @RequestMapping(value = "/updateType", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateType(Type type) {
        try {
            typeService.updateType(type);
            return "{\"success\":\"true\",\"message\":\"修改成功\"}";
        } catch (Exception e) {
            return "{\"success\":\"false\",\"message\":\"修改失败\"}";
        }
    }
}
