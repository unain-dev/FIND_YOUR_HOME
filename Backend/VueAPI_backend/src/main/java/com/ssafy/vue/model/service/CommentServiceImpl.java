package com.ssafy.vue.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public List<CommentDto> list(int articleno) throws Exception {
		return commentMapper.list(articleno);
	}

	@Override
	public boolean create(CommentDto commentDto) throws Exception {
		return commentMapper.create(commentDto) == 1;
	}

	@Override
	public boolean modify(CommentDto commentDto) throws Exception {
		return commentMapper.modify(commentDto) == 1;
	}

	@Override
	public boolean delete(int memono) throws Exception {
		return commentMapper.delete(memono) == 1;
	}

}
