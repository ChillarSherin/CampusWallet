package com.chillarcards.campuswallet.NotificationCenter;

public class Contact {
	
	//private variables
	String _id;
	String datetime;
	String Datas;
	String Clicked;
	String SchoolID;
	String StudentID;
	String StdName;
	int messageID;

	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(String id, String datetime, String Datas, String Clicked, String SchoolID, String StudentID, String StdName,int messageID){
		this._id = id;
		this.datetime = datetime;
		this.Datas = Datas;
		this.Clicked = Clicked;
		this.SchoolID = SchoolID;
		this.StudentID = StudentID;
		this.StdName = StdName;
		this.messageID = messageID;

	}


	
	// constructor
	public Contact(String datetime, String Datas){
		this.datetime = datetime;
		this.Datas = Datas;
	}

	public Contact(String s, String s1, String clicked) {
		this.datetime = s;
		this.Datas = s1;
		this.Clicked = clicked;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	// getting ID
	public String getID(){
		return this._id;
	}
	
	// setting id
	public void setID(String id){
		this._id = id;
	}
	
	// getting datetime
	public String getdatetime(){
		return this.datetime;
	}
	
	// setting datetime
	public void setdatetime(String datetime){
		this.datetime = datetime;
	}
	
	// getting phone number
	public String getDatas(){
		return this.Datas;
	}
	
	// setting phone number
	public void setDatas(String phone_number){
		this.Datas = phone_number;
	}

	public String getClicked() {
		return Clicked;
	}

	public void setClicked(String clicked) {
		Clicked = clicked;
	}


	public String getSchoolID() {
		return SchoolID;
	}

	public void setSchoolID(String schoolID) {
		SchoolID = schoolID;
	}

	public String getStudentID() {
		return StudentID;
	}

	public void setStudentID(String studentID) {
		StudentID = studentID;
	}

	public String getStdName() {
		return StdName;
	}

	public void setStdName(String stdName) {
		StdName = stdName;
	}
}
