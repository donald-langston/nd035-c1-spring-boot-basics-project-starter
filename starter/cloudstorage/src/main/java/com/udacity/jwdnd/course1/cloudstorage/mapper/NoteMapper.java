package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE USERID = #{userid}")
    List<Note> getAllNotes(Integer userid);
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int addNote(Note note);

    @Delete("DELETE FROM NOTES WHERE NOTEID = #{noteid}")
    boolean deleteNote(Integer noteid);

    @Update("Update notes set noteTitle = #{noteTitle}, noteDescription = #{noteDescription} where noteId = #{noteId}")
    public boolean editNote(Note note);

}
