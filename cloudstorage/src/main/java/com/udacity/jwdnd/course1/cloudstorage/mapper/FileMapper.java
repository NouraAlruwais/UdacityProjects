package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename=#{fileName}")
    File getFile(String fileName);

    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    List<File> getAllFiles(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid,filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Update("UPDATE FILES SET filename=#{fileName}, contenttype=#{contentType}, filesize=#{fileSize}, userid=#{userId} WHERE fileid=#{fileId}")
    void update(File file);

    @Delete("DELETE FROM FILES WHERE fileid=#{fileId}")
    void delete(Integer fileId);
}
