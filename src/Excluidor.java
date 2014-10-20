import javax.swing.DefaultListModel;



public class Excluidor {

	public DefaultListModel fileList;
	public Excluidor() {
		// TODO Auto-generated constructor stub
		fileList = new DefaultListModel();
	}
	
	public void addFile(String address) {
		fileList.addElement(address);
	}
	
	public boolean removeFile(int index) {
		if (fileList.elementAt(index) == null) {
			return false;
		}
		fileList.remove(index);
		return true;
	}
	public boolean removeFile(String address) {
		int id = fileList.indexOf(address);
		if (id >= 0) {
			fileList.remove(id);
		}
		return true;
	}
	
	

}
