import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.filechooser.*;


public class FolderSelector extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FolderSelector frame = new FolderSelector();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FolderSelector() {
		
		Excluidor exc = new Excluidor();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		@SuppressWarnings("rawtypes")
		JList list = new JList();
		list.setLayoutOrientation(JList.VERTICAL);
		// list.setSelectionMode(JList.)
		contentPane.add(list, BorderLayout.CENTER);
		
		JButton btnAdicionarArquivo = new JButton("Adicionar pasta");
		btnAdicionarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fca = fc.showOpenDialog(FolderSelector.this);
				if (fca == JFileChooser.APPROVE_OPTION) {
					String filePath = fc.getSelectedFile().getAbsolutePath();
					exc.addFile(filePath);
					Logger.log("Arquivo adicionado: ".concat(filePath));
				}
			}
		});
		contentPane.add(btnAdicionarArquivo, BorderLayout.NORTH);
		
		
	
	}

}
