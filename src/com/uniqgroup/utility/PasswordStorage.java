package com.uniqgroup.utility;

import com.uniqgroup.pojo.PasswordHash;



public class PasswordStorage {
	
	
	public static String[] pass_list = { "No Password","RRRY","BGRG","YBGR" };
	
	/**
	 * 1. magenta 2.green 3.Blue 4.yellow
	 **/
	public static int getCurrentPasswordIndex(String curr_pass){
		int curr_indx_pass = 0;
		
		for(int i=0;i<PasswordStorage.pass_list.length;i++)
		{
			try {
				if(PasswordHash.validatePassword(PasswordStorage.pass_list[i], 
						 curr_pass)){
					curr_indx_pass = i;
					
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			
		}
		return curr_indx_pass;
	}	
	
	public static String getPassByIndex(int index){
		
		return pass_list[index];
	}
	
	
}
