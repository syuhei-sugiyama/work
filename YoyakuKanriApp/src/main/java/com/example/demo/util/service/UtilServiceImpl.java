package com.example.demo.util.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.util.serviceif.UtilService;

@Service
public class UtilServiceImpl implements UtilService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Integer> convertStrToIntArr(String conStr, String sepStr) {
		List<Integer> result = new ArrayList<Integer>();
		String[] conArr = conStr.split(sepStr);
		for (String str : conArr) {
			result.add(Integer.parseInt(str));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertStrArrToStr(String[] conStrArr, String sepStr) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < conStrArr.length; i++) {
			if (i + 1 == conStrArr.length) {
				// 最後の要素を追加するときは区切り文字無し
				sb.append(conStrArr[i]);
			} else {
				sb.append(conStrArr[i]);
				sb.append(sepStr);
			}
		}
		return sb.toString();
	}

}
