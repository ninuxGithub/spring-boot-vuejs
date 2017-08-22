package com.example.demo.bean;

import java.util.List;

import lombok.Data;

@Data
public class JqFilter {
	// {"groupOp":"AND","rules":[{"field":"productDate","op":"bw","data":"2011-1-1"},{"field":"stockAmount","op":"gt","data":"100"}]}
	private String groupOp;

	private List<Rule> rules;

}


