package com.sunbeam;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UsersDao extends Dao {

	private PreparedStatement stmtSave;
	private PreparedStatement stmtUpdatePassword;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtFindByEmail;

	public UsersDao() throws Exception {
		String sqlSave = "INSERT INTO Users VALUES(default, ?, ?, ?, ?, ?)";
		stmtSave = con.prepareStatement(sqlSave);
		
		String sqlFindByEmail = "SELECT * FROM Users where email=?";
		stmtFindByEmail = con.prepareStatement(sqlFindByEmail);
		
		String sqlUpdatePassword = "UPDATE Users SET password = ? WHERE id = ?";
		stmtUpdatePassword = con.prepareStatement(sqlUpdatePassword);
		
		String sqlUpdate = "UPDATE Users SET first_name=?,last_name=?,email=? WHERE id = ?";
		stmtUpdate = con.prepareStatement(sqlUpdate);
	}

	public int save(Users u) throws Exception {
		stmtSave.setString(1, u.getFname());
		stmtSave.setString(2, u.getLname());
		stmtSave.setString(3, u.getEmail());
		stmtSave.setString(4, u.getPassword());
		stmtSave.setString(5, u.getMobile());
		int count = stmtSave.executeUpdate();

		return count;

	}

	public Users findByEmail(String email) throws Exception {
		stmtFindByEmail.setString(1, email);
		try (ResultSet rs = stmtFindByEmail.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("first_name");
				String lname = rs.getString("last_name");
				String password = rs.getString("password");
				String mobile = rs.getString("mobile");

				return new Users(id, fname, lname, email, password, mobile);
			}
		} // rs.close();
		return null;
	}
	
	public int updatePassword(int id,String newPassword) throws Exception{
		stmtUpdatePassword.setString(1, newPassword);
		stmtUpdatePassword.setInt(2, id);
		int count = stmtUpdatePassword.executeUpdate();
		return count;
	}
	
	public int update(int id,String newFName,String newLName,String newEmail) throws Exception{
		stmtUpdate.setString(1, newFName);
		stmtUpdate.setString(2, newLName);
		stmtUpdate.setString(3, newEmail);
		stmtUpdate.setInt(4, id);
		int count = stmtUpdate.executeUpdate();
		return count;
	}

}
