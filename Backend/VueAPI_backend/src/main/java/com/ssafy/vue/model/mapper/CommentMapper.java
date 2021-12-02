package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.CommentDto;

public interface CommentMapper {
	List<CommentDto> list(int articleno) throws SQLException;
	int create(CommentDto commentDto) throws SQLException;
	int modify(CommentDto commentDto) throws SQLException;
	int delete(int memono) throws SQLException;
}
