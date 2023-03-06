package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {


    @Select("SELECT * FROM CREDENTIALS WHERE USERID = #{userid}")
    List<Credential> getAllCredentials(Integer userid);

    @Select("SELECT key FROM CREDENTIALS WHERE CREDENTIALID = #{credentialId}")
    String getEncryptionKey(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE CREDENTIALID = #{credentialid}")
    boolean deleteCredential(Integer credentalid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int addCredential(Credential credential);

    @Update("Update credentials set url = #{url}, username = #{username}, password = #{password} where credentialid = #{credentialId}")
    public boolean editCredential(Credential credential);

}
