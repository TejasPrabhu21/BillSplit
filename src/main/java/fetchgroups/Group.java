package fetchgroups;

import java.util.HashMap;

public class Group {
	private int groupId;
	private String groupName;
	private int admin;
	private String link;
	private String createdOn;
	private int memCount;
	private int totalAmt;

	public Group() {
		// Default constructor
	}

	public Group(int groupId, String groupName, int admin, String link, String createdOn) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.admin = admin;
		this.link = link;
		this.createdOn = createdOn;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

//----------------------------------
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

//---------------------------------------------
	public int getCreatedBy() {
		return admin;
	}

	public void setCreatedBy(int admin) {
		this.admin = admin;
	}

// ------------------------------
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

// ------------------------------
	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

//-----------------------------
	public int getMemCount() {
		return memCount;
	}

	public void setMemCount(int memCount) {
		this.memCount = memCount;
	}

//-----------------------------
	public int getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}

}
