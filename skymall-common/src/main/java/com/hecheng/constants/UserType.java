package com.hecheng.constants;

public enum UserType {

	MANAGER("后台用户", 1), USER("前台用户", 2);
	
    // 成员变量  
    private String name;
    
    private int type;
    
    // 构造方法  
    private UserType(String name, int type) {
        this.name = name;
        this.type = type;
    }
    // 普通方法  
    public static String getName(int type) {
        for (UserType c : UserType.values()) {
            
        	if (c.getType() == type) {
                
        		return c.name;
            }
        }
        
        return null;
    }
    
    // get set 方法  
    public String getName() {
        return name;  
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
    
}
