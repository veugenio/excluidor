import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.DefaultListModel;



public class Excluidor {
	
	public static int MAX_ATTEMPTS = 3;
	public static String TMP_BASE = "tmp";

	private Path tmp;
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
	public boolean removeFile(Object obj) {
		int id = fileList.indexOf(obj);
		if (id >= 0) {
			fileList.remove(id);
		}
		return true;
	}
	private boolean checkTmp() {
		if (tmp != null) return true;
		// TODO usar a constante tmp base aqui
		String tmp = "c:\tmp";
		Path path = Paths.get(tmp);

		for (int i=0; Files.exists(path); i++) {
			path = Paths.get(tmp.concat(Integer.toString(i)));
		}
		
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			Logger.log("Falha ao criar o diretório tmp: ".concat(e.getMessage()));
			return false;
		}
		this.tmp = path;
		return true;
	}
	
	/**
	 * Move o arquivo para pasta temporaria
	 * @param file
	 * @return Path do novo arquivo 
	 */
	private Path moveToTmp(Path file) {
		return this.moveToTmp(file, 1);
	}
	private Path moveToTmp(Path file, int attempt) {
		// check tmp folder
		if (!checkTmp()) {
			Logger.log("Falha ao tentar criar a pasta temporária.");
			return null;
		}
		Path target = this.tmp.resolveSibling(file.getFileName());
		try {
			Files.move(file, target, StandardCopyOption.REPLACE_EXISTING);
		} catch(UnsupportedOperationException e) {
			//if the array contains a copy option that is not supported
			Logger.log("Operação não suportada: ".concat(e.getMessage()));
			return null;
		} catch(FileAlreadyExistsException e) {
			Logger.log("O arquivo já existe: ".concat(e.getMessage()));
			if (attempt < MAX_ATTEMPTS) {
				Logger.log("Apagando o temporário: ".concat(target.toString()));
				deleteFile(target);
				Logger.log("Tentando mover novamente");
				moveToTmp(file, attempt++);
			}
		} catch(DirectoryNotEmptyException  e) {
			
		} catch(IOException e) {
			
		}catch(SecurityException e) {
			
		}





		return file;
	}
	private int deleteFile(Path file) {
		
		try	{
			Files.delete(file);
		} catch (NoSuchFileException x) {
			Logger.log("O arquivo não existe: ".concat(file.toString()));
		} catch (DirectoryNotEmptyException x) {
			Logger.log("O arquivo não está vazio: ".concat(file.toString()));
		} catch (IOException x) {
			Logger.log("Movendo o arquivo para diretorio temporário: ".concat(file.toString()));
			moveToTmp(file);
		} catch (Exception x) {
			Logger.log("Falha ao tentar excluir o arquivo: ".concat(x.getMessage()));
			return 1;
		}
		return 0;
	}
	private int processFiles() {
		int total = fileList.size();
		for (int i=0; i < total; i++) {
			String file = (String) fileList.get(i);
			int err = deleteFile(Paths.get(file));
			if (err > 0)
				return err;
		}
		return 0;
	}

	public void process() {
		Logger.log("Iniciando exclusão dos arquivos");
		int err = processFiles();
		if (err > 0)
			Logger.log("Erro no processamento dos arquivos");
		else
			Logger.log("Processo finalizado");
	}
	
	

}
