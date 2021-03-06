package com.zkd.dao.map;

import com.zkd.entity.User;

import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrqc_user
     *
     * @mbg.generated Mon Aug 20 16:02:49 SGT 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrqc_user
     *
     * @mbg.generated Mon Aug 20 16:02:49 SGT 2018
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrqc_user
     *
     * @mbg.generated Mon Aug 20 16:02:49 SGT 2018
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrqc_user
     *
     * @mbg.generated Mon Aug 20 16:02:49 SGT 2018
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrqc_user
     *
     * @mbg.generated Mon Aug 20 16:02:49 SGT 2018
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrqc_user
     *
     * @mbg.generated Mon Aug 20 16:02:49 SGT 2018
     */
    int updateByPrimaryKeyWithBLOBs(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrqc_user
     *
     * @mbg.generated Mon Aug 20 16:02:49 SGT 2018
     */
    int updateByPrimaryKey(User record);

    List<User> selectById(String userId);

}
