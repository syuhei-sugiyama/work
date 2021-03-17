package com.example.demo;

import java.io.Serializable;
import java.util.List;

public interface MsgDataDao<T> extends Serializable {
	public List<MsgData> getAll();
	public MsgData findById(long id);
}
