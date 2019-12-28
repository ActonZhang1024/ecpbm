package pers.zhang.dao.provider;

import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.ibatis.jdbc.SQL;
import pers.zhang.pojo.UserInfo;

import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/19 - 17:23
 */
public class UserInfoDynaSqlProvider {
    //分页动态查询
    public String selectWithParam(Map<String, Object> params) {
        String sql = new SQL(){
            {
                SELECT("*");
                FROM("user_info");
                if(params.get("userInfo") != null){
                    UserInfo userInfo = (UserInfo) params.get("userInfo");
                    if(userInfo.getUserName() != null && !"".equals(userInfo.getUserName())){
                        WHERE(" userName LIKE CONCAT('%',#{userInfo.userName},'%')");
                    }
                }
            }
        }.toString();
        if(params.get("pager") != null){
            sql += " limit #{pager.firstLimitParam}, #{pager.perPageRows}";
        }
        return sql;
    }


    //根据条件动态查询总记录数
    public String count(Map<String, Object> params){
        String sql = new SQL(){
            {
                SELECT("count(*)");
                FROM("user_info");
                if(params.get("userInfo") != null){
                    UserInfo userInfo = (UserInfo) params.get("userInfo");
                    if(userInfo.getUserName() != null && !"".equals(userInfo.getUserName())){
                        WHERE(" userName LIKE CONCAT('%',#{userInfo.userName},'%')");
                    }
                }
            }
        }.toString();
        return sql;
    }
}
