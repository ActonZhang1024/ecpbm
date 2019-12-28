package pers.zhang.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.zhang.pojo.Type;

import java.util.List;

/**
 * @author zhang
 * @date 2019/12/21 - 11:07
 */
public interface TypeDao {
    //查询所有商品类型
    @Select("select * from type")
    public List<Type> selectAll();

    //根据类型编号查询商品类型
    @Select("select * from type where id = #{id}")
    public Type selectById(int id);

    //添加商品类型
    @Insert("insert into type(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int add(Type type);

    //更新商品类型
    @Update("update type set name = #{name} where id = #{id}")
    public int update(Type type);;
}
