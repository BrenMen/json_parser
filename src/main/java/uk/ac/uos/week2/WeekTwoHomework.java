package uk.ac.uos.week2;

public class WeekTwoHomework {
	
	 public static void main(String[] str) {
		 
	 }
	 
	public String camelCase(String str)
	{
		StringBuilder builder = new StringBuilder(str);
		boolean isLastSpace = true;
		
		for(int i = 0; i < builder.length(); i++)
		{
			char ch = builder.charAt(i);
			
			if(isLastSpace && ch >= 'a' && ch <='z')
			{
				builder.setCharAt(i, (char)(ch + ('A' - 'a') ));
				isLastSpace = false;
			}
			else isLastSpace = ch != ' ';
		}
	
		return builder.toString();
	}
		
	public String fullName(String foreName, String middleName, String surName) {
		middleName = (middleName == null) ? "" : middleName;
		String letterFormat = foreName + " " + middleName.charAt(0) + " " + surName;
			
		return camelCase(letterFormat);
	}
}