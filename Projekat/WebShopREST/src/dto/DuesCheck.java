package dto;

import dao.DuesDAO;
import dao.KorisnikDAO;

public class DuesCheck extends Thread{
	
	private static int sleep_time = 10 * 1000;	//10 sekundi
	
	 public void run(){
	       while(true) {
	    	   try {
				Thread.sleep(sleep_time);
				
				
				DuesDAO.getInstance().checkDues();
				KorisnikDAO.getInstance().setBuyerTypeNames();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	       }
	 }

}