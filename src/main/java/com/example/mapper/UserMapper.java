package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.User;
import com.example.example.UserExample;

@Mapper
public interface UserMapper {

	/** 有効なユーザー情報を全取得 */
	List<User> selectAllValidUsers();

	/** user・skillsに合致するユーザー情報を取得 */
	List<User> selectUsersWithTechnicalSkills(@Param("user") User user, @Param("skills") List<Integer> skills);

	/** メールアドレス・ステージに合致するユーザー情報を取得 */
	List<User> selectByEmailAndStage(@Param("email") String email, @Param("stage") String stage);

	/** Exampleに合致するユーザー情報を取得 */
	List<User> selectByExample(UserExample example);

	/**
	 * 
	 * ユーザーidで検索
	 * 
	 * @param example ユーザーid
	 * @return ユーザー情報
	 */
	User selectByUserId(UserExample example);

	/** 主キーに合致するユーザー情報を取得 */
	User selectByPrimaryKey(Integer userId);

	/** 新規ユーザー情報追加 */
	int insertSelective(User record);

	/** Exampleに合致するユーザー情報を一部更新 */
	boolean updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);
	
	/** recordに合致する仮ユーザー情報を更新し有効ユーザーとする */
	int updateNewestTemporaryUser(@Param("record") User record);
	
	/** Exampleに合致するユーザー情報を削除 */
	int deleteByExample(UserExample example);

}