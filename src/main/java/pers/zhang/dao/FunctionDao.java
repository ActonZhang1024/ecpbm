package pers.zhang.dao;

import org.apache.ibatis.annotations.Select;
import pers.zhang.pojo.Functions;

import java.util.List;

/**
 * @author zhang
 * @date 2019/12/21 - 12:28
 */
public interface FunctionDao {
    //根据管理员id获取功能权限
    @Select("select * from functions where id in (select fid from powers where aid = #{aid})")
    public List<Functions> selectByAdminId(Integer aid);
}
