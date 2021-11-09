package com.app.management.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserModel {
	private String userId;
	private String password;
	private String fullName;
	private String createdOn;
	private List<UserRoleModel> roles = new ArrayList<>();
}
