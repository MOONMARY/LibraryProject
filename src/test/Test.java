package test;

import java.io.File;

public class Test {
	public static void main(String[] args) {
    	String path = "ManagerVo.java";
	File f = new File(path);	// 상대 경로
		
	System.out.println("파일 이름(상대 경로) : " + f.getName());	// 상대 경로 이름
	System.out.println("절대 경로 : " + f.getAbsolutePath());	// 절대 경로
	System.out.println("파일 존재? " + f.exists());	// 물리적으로 존재하는지 여부
    }
}
