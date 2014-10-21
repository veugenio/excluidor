import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.filechooser.*;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class FolderSelector extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JFileChooser fc = new JFileChooser();
	private Excluidor exc = new Excluidor();
	private JMenuBar menuBar = new JMenuBar();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JList list = new JList(exc.fileList);

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
	
	private void createMenu() {
		
		JFrame self = this;
		setJMenuBar(menuBar);
		
		JMenuItem mntmAdicionarPasta = new JMenuItem("Adicionar");
		mntmAdicionarPasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fca = fc.showOpenDialog(FolderSelector.this);
				if (fca == JFileChooser.APPROVE_OPTION) {
					String filePath = fc.getSelectedFile().getAbsolutePath();
					exc.addFile(filePath);
					Logger.log("Arquivo adicionado: ".concat(filePath));
				}
			}
		});
		menuBar.add(mntmAdicionarPasta);
		
		JMenuItem mntmRemoverSelecionado = new JMenuItem("Remover");
		mntmRemoverSelecionado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = list.getSelectedIndex();
				if (id < 0) {
					JOptionPane.showMessageDialog(self, "Selecione algum arquivo");
					return;
				}
				exc.removeFile(id);	
			}
		});
		menuBar.add(mntmRemoverSelecionado);
		
		JMenuItem mntmExcluirItens = new JMenuItem("Excluir itens");
		mntmExcluirItens.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int id = JOptionPane.showConfirmDialog(self, "Tem certeza que deseja excluir os diretórios?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
				if (id == JOptionPane.YES_OPTION) {
					exc.process();
				}
			}
		});
		menuBar.add(mntmExcluirItens);
	}

	/**
	 * Create the frame.
	 */
	public FolderSelector() {
		
		// criando o panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// configurando o filechooser
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// configurando o list
		list.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(list, BorderLayout.CENTER);
		
		// configurando o jframe
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		createMenu();
	
	}

}
