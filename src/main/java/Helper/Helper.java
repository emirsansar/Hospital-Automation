package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {
	
	public static void showMsg(String str) {
		String msg;
		optionPaneChangeButtonText();
		
		switch(str) {
		case "fill": 
			msg = "Please fill out all fields!";
			break;
		case "success":
			msg = "Operation successfulL!";
			break;
		case "error":
			msg = "An error has occurred!";
			break;
		default:
			msg = str;
			break;
			
		}
		
		JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean confirm(String str) {
		String msg;
		optionPaneChangeButtonText();
		
		switch(str) {
		
		case "sure":
			msg = "Are you sure you want to perform this operation?";
			break;
		default:
			msg=str;
			break;
		}
		
		int res = JOptionPane.showConfirmDialog(null, msg, "Dikkat!", JOptionPane.YES_NO_OPTION);
		
		if (res == 0 ) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void optionPaneChangeButtonText() {
		UIManager.put("OptionPane.cancelButtonText", "Cancel");
		UIManager.put("OptionPane.noButtonText", "No");
		UIManager.put("OptionPane.oklButtonText", "Okay");
		UIManager.put("OptionPane.yesButtonText", "Yes");
	}
	
	public static String getPass(char[] arr) {
		return String.valueOf(arr);
	}

}
