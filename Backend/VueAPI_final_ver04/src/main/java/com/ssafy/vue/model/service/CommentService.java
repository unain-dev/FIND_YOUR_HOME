package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.vue.model.CommentDto;

public interface CommentService {
	List<CommentDto> list(int articleno) throws Exception;
	boolean create(CommentDto commentDto) throws Exception;
	boolean modify(CommentDto commentDto) throws Exception;
	boolean delete(int memono) throws Exception;
}
