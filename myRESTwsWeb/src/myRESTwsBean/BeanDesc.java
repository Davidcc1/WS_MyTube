package myRESTwsBean;

public class BeanDesc {
	private int idDesc;
	private String desc;
	private String id;
	
	public BeanDesc(){
		idDesc = 0;
		desc = new String();
		id = new String();
	}
	
	public int getId(){
		return idDesc;
	}
	
	public void setId(int idDesc){
		this.idDesc = idDesc;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public void setDesc(String desc){
		this.desc = desc;
	}
	
	public String getClient(){
		return id;
	}
	
	public void setClient(String id){
		this.id = id;
	}

}