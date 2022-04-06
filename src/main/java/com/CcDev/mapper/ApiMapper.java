package com.CcDev.mapper;

import com.CcDev.common.ApiListVO;
import com.CcDev.common.ApiVO;
import com.CcDev.pojo.Api;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
public interface ApiMapper extends BaseMapper<Api> {

    /**
     * 通过分类id查询对应的Api信息接口;
     *
     * @param apiClassificationId;
     * @return Api信息
     */
    @Select("select  *  from api where api_classification_id=#{apiClassificationId}")
    public List<Api> findApi(Integer apiClassificationId);

    /**
     * 查询项目下所有的Api列表接口;
     *
     * @param projectId;
     * @return Api列表
     */
    @Select("select t2.`name` classificationName,t1.*  from api t1 inner join api_classification t2 on t1.api_classification_id=t2.id where t2.project_id=#{projectId}")
    public List<ApiListVO> showApiListByProject(Integer projectId);

    /**
     * 查询指定分类下Api信息;
     *
     * @param classificationId;
     * @return 指定分类下Api信息
     */
    @Select("select t2.`name` classificationName,t2.`description` classificationDescription,t1.*  from api t1 inner join api_classification t2 on t1.api_classification_id=t2.id where t2.id=#{classificationId}")
    public List<ApiListVO> showApiListByClassificationId(Integer classificationId);

    /**
     * 查询指定Api信息
     *
     * @param apiId;
     * @return 指定Api信息
     */
    @Select("SELECT t1.*, t2.username createUsername FROM api t1, user t2 WHERE t1.id = #{apiId} AND t1.create_user = t2.id;")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "requestParams", column = "id", many = @Many(select = "com.CcDev.mapper.ApiRequestParamMapper.findApiParam"))
    })
    public ApiVO findApiViewVO(Integer apiId);
}
