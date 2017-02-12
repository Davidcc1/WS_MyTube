package myRESTwsBean;

public class BeanVid {
		private int id;
		private byte[] bytes;
		private String title;
		private String idC;
		
		public BeanVid(){
			id = 0;
			bytes = null;
			title = new String();
			idC = new String();
		}
		public int getId(){
			return id;
		}
		public void setId(int id){
			this.id = id;
		}
		
		public byte[] getBytes(){
			return bytes;
		}
		
		public void setBytes(byte[] bytes){
			this.bytes = bytes;
		}
		
		public String getTitle(){
			return title;
		}
		
		public void setTitle(String title){
			this.title = title;
		}
		
		public String getClient(){
			return idC;
		}
		
		public void setClient(String idC){
			this.idC = idC;
		}

}
