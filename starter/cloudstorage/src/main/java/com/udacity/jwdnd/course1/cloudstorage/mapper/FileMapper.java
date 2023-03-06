package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE USERID = #{userid}")
    List<File> getAllFiles(Integer userid);

    @Select("SELECT * FROM FILES WHERE FILEID = #{fileId}")
    File getFile(Integer fileId);
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Delete("DELETE FROM FILES WHERE FILEID = #{fileId}")
    boolean deleteFile(Integer fileId);

    @Update("Update notes set noteTitle = #{noteTitle}, noteDescription = #{noteDescription} where noteId = #{noteId}")
    public boolean editFile(File file);
}
