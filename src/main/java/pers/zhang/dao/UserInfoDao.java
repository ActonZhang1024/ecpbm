package pers.zhang.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import pers.zhang.pojo.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/19 - 17:19
 */
public interface UserInfoDao {
    //获取系统合法客户，即数据表user_info中status字段为1的客户列表
    @Select("select * from user_info where status = 1")
    public List<UserInfo> getValidUser();

    //根据id获取客户对象
    @Select("select * from user_info where id = #{id}")
    public UserInfo getUserInfoById(int id);

    //分页获取客户信息
    @SelectProvider(type = pers.zhang.dao.provider.UserInfoDynaSqlProvider.class, method = "selectWithParam")
    public List<UserInfo> selectByPage(Map<String, Object> params);

    //根据条件查询客户总数
    @SelectProvider(type = pers.zhang.dao.provider.UserInfoDynaSqlProvider.class, method = "count")
    public Integer count(Map<String, Object> params);

    //更新客户状态
    @Update("update user_info set status = #{flag} where id in (${ids})")
    public void updateState(@Param("ids") String ids, @Param("flag") int flag);
}
