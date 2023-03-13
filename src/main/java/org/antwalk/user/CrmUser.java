package org.antwalk.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.antwalk.validation.FieldMatch;
import org.antwalk.validation.ValidEmail;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class CrmUser {

	/*
	 * @NotNull(message = "is required")
	 * 
	 * @Size(min = 1, message = "is required") private String userName;
	 */
	

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String fullName;
	
	@ValidEmail
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String email;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String password;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String matchingPassword;

	
	@NotNull(message = "is required")
	@Size(min = 10, message = "must be at least 10 digit")
	private String contactNo;
	
	
	
	/*
	 * @NotNull(message = "is required")
	 * 
	 * @Size(min = 1, message = "is required") private String lastName;
	 */



	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public CrmUser() {

	}

	/*
	 * public String getUserName() { return userName; }
	 * 
	 * public void setUserName(String userName) { this.userName = userName; }
	 */

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
