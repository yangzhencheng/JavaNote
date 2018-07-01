/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javanote;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.applet.*;
import java.util.Date;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JOptionPane;
import javax.swing.JEditorPane;
import javax.swing.border.*;
/**
 *
 * @author yangzhencheng
 */
public class JavaNote {
    static int FileBool = 2;			//当FileBool为2时，表现文本没有内容改变。当大于10时，说明被其它附加功能占有。
    static String FilePath = "";		//收到文件的地址
    static String FileName = "";		//收到的文件名
    static String NetArea = "";                 //字体
    static String Font_Word = "";		//字体
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainWindow Win = new MainWindow( "Java Note");
    }
    
}





//打开文件类
class OpenWindow
{
	int i;
	FileDialog OpenFile;
	String OneLine;
	BufferedReader in;
	
	OpenWindow( MainWindow WindowArea )
	{
		if( JavaNote.FileBool == 1 )
		{
			i = JOptionPane.showConfirmDialog( WindowArea, "是否保存现有内容？", "文件保存", JOptionPane.YES_NO_CANCEL_OPTION );
			switch( i )
			{
				case 0: try
						  {
						  	SaveWindow OpenSave = new SaveWindow( WindowArea, "保存" );
							OpenFile = new FileDialog( WindowArea, "打开文件", FileDialog.LOAD );
							OpenFile.setVisible( true );
							JavaNote.FilePath = OpenFile.getDirectory( );
							JavaNote.FileName = OpenFile.getFile( );
							WindowArea.MainTextArea.setText( "" );
							File usefile = new File( JavaNote.FilePath, JavaNote.FileName );
							FileReader fileuser=new FileReader(usefile);
							in = new BufferedReader( fileuser );
							while( ( OneLine = in.readLine( ) ) != null )
							{
								WindowArea.MainTextArea.append( OneLine + '\n' );
								JavaNote.FileBool = 2;
							}
							in.close( );
							fileuser.close( );
						}
						catch( IOException io ){ }
						catch( NullPointerException ec ){ }
						break;
				case 1: try{	
						OpenFile =new FileDialog( WindowArea, "打开文件", FileDialog.LOAD );
						OpenFile.setVisible( true );
						JavaNote.FilePath = OpenFile.getDirectory( );
						JavaNote.FileName = OpenFile.getFile();
						WindowArea.MainTextArea.setText( "" );
						File usefile = new File( JavaNote.FilePath, JavaNote.FileName );
						FileReader fileuser = new FileReader( usefile );
						in = new BufferedReader( fileuser );
						
						while( ( OneLine=in.readLine( ) ) != null )
						{
							WindowArea.MainTextArea.append( OneLine+'\n' );
							JavaNote.FileBool=2;
						}
						
						in.close( );
						fileuser.close( );
					}
					catch( IOException io ){ }
					catch( NullPointerException ec ){ }
					break;
				case 2: break;
				default: System.out.println( "程序出错！" );
			}
		}
		else
		{
			try
			{
				OpenFile = new FileDialog( WindowArea, "打开文件", FileDialog.LOAD );
				OpenFile.setVisible( true );
				JavaNote.FilePath = OpenFile.getDirectory( );
				JavaNote.FileName = OpenFile.getFile( );
				WindowArea.MainTextArea.setText("");
				File usefile = new File( JavaNote.FilePath, JavaNote.FileName );
				FileReader fileuser = new FileReader( usefile );
				in = new BufferedReader(fileuser);
				
				while( ( OneLine=in.readLine( ) ) != null )
				{
					WindowArea.MainTextArea.append( OneLine + '\n' );
					JavaNote.FileBool = 2;
				}
				
				in.close( );
				fileuser.close( );
			}
			catch( IOException io ){ }
			catch( NullPointerException ec ){ }
		}
	}
}

//上网类
class UpNet extends JDialog implements ActionListener
{
	JTextField textnet;
	JLabel labaddress;
	JButton Bu_Yes, Bu_No;
	Container con = getContentPane( );
	Panel con1 = new Panel( );
	Panel con2 = new Panel( );
	MainWindow wins;
	
	UpNet( MainWindow WindowArea, String TitleName )
	{
		wins = WindowArea;
		setTitle( TitleName );
		setBounds( 100, 100, 360, 150 );
		textnet = new JTextField( 20 );
		labaddress = new JLabel( "地址：" );
		con1.setLayout(new FlowLayout( ) );
		con1.add( labaddress );
		con1.add( textnet );
		textnet.requestFocus( );
		Bu_Yes =new JButton( "确定" );
		Bu_No =new JButton( "取消" );
		con2.setLayout( new FlowLayout( ) );
		con2.add( Bu_Yes );
		con2.add( Bu_No );
		con.add( con1, BorderLayout.NORTH );
		con.add( con2, BorderLayout.CENTER );
		con.validate( );
		Bu_Yes.addActionListener( this );
		Bu_No.addActionListener( this );
		setVisible( true );
		validate( );
		setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource( ) == Bu_Yes )
		{	
			System.out.println( "上网" );
			if( textnet.getText( ) != "" ){
				JavaNote.NetArea = textnet.getText( );
				NetWindow NetWin = new NetWindow( wins );
			}
			dispose( );
		}
		else if( e.getSource( ) == Bu_No )
		{
			dispose( );
		}
	}
}

//保存和另存为类
class SaveWindow
{
	FileDialog SaveFile;
	BufferedWriter out;
	
	SaveWindow( MainWindow WindowArea, String TitleName )
	{
		if( !( JavaNote.FileName.equals( "" ) ) && !( TitleName.equals( "另存为……" ) ) )
		{
			try
			{
				File usefile = new File( JavaNote.FilePath, JavaNote.FileName );
				FileWriter fileuser = new FileWriter( usefile );
				out = new BufferedWriter( fileuser );
				out.write( WindowArea.MainTextArea.getText( ),0,(WindowArea.MainTextArea.getText( ) ).length( ) );
				JavaNote.FileBool = 2;
				out.close( );
				fileuser.close( );
			}
			catch( IOException io ){}
			catch( NullPointerException ec )
			{
				JavaNote.FilePath = "";
				JavaNote.FileName = "";
			}
		}
		else
		{
			boolean FileCreateSuccessful = false;
			SaveFile = new FileDialog( WindowArea,TitleName, FileDialog.SAVE );
			SaveFile.setVisible(true);
			JavaNote.FilePath = SaveFile.getDirectory( );
			JavaNote.FileName = SaveFile.getFile( );
			try
			{
				File usefile = new File( JavaNote.FilePath, JavaNote.FileName );
				if( FileCreateSuccessful == false )
				{
					FileWriter fileuser = new FileWriter( usefile );
					out=new BufferedWriter( fileuser );
					out.write( WindowArea.MainTextArea.getText( ), 0, ( WindowArea.MainTextArea.getText( ) ).length( ) );
					JavaNote.FileBool = 2;
					out.close( );
					fileuser.close( );
				}
			}
			catch( IOException io ){ }
			catch( NullPointerException ec )
			{
				JavaNote.FilePath = "";
				JavaNote.FileName = "";
			}
		}
	}
}

//字体类
class WordStyle extends Dialog implements ActionListener, ItemListener
{
	String Word_Name;
	Button YES,NO;
	Choice Font_List;
	Panel Button_Area;
	Font Font_Means;
	JTextArea Analyze_Text;
	Box boxVS;
	MainWindow wins;
	
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
	String fontName[ ] = ge.getAvailableFontFamilyNames( );
	
	WordStyle( MainWindow WindowArea )
	{
		super( WindowArea, "字体", true );
		wins = WindowArea;
		boxVS = Box.createVerticalBox( );
		Font_List = new Choice( );
		Button_Area = new Panel( );
		Analyze_Text = new JTextArea( );
		Font_Means = new Font( "宋体", Font.PLAIN, 32 );
		Analyze_Text.setFont( Font_Means );
		Analyze_Text.setText( "小明XiaoMing！" );
		
		for( int i = 0; i < fontName.length; i++ )
		{
			Font_List.add( fontName[ i ] );
		}
		
		setLayout( new FlowLayout( ) );
		YES = new Button( "确定" );
		NO = new Button( "取消" );
		YES.addActionListener( this );
		NO.addActionListener( this );
		Font_List.addItemListener( this );
		
		addWindowListener( new WindowAdapter( )
			{
				public void windowClosing( WindowEvent e )
				{
					setVisible( false );
				}
			}
		);
		
		setBounds( 100, 100, 300, 200 );
		Button_Area.add( YES );
		Button_Area.add( NO );
		boxVS.add( Font_List );
		boxVS.add( Box.createVerticalStrut( 20 ) );
		boxVS.add( Analyze_Text );
		boxVS.add( Box.createVerticalStrut( 20 ) );
		boxVS.add( Button_Area );
		add( boxVS );
		setVisible( true );
		validate( );
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource( ) == YES )			//YES
		{
			dispose( );
		}
		else if( e.getSource( ) == NO )		//NO
		{
			JavaNote.Font_Word = "";
			dispose( );
		}
		else
		{
			System.out.println( "程序出错！" );
		}
	}
	
	public void itemStateChanged( ItemEvent e )
	{
		String Word_Name=Font_List.getSelectedItem( );
		JavaNote.Font_Word = Word_Name;
		Font f = new Font( Word_Name, Font.PLAIN, 32 );
		Analyze_Text.setFont( f );

	}
}

//段落类
class ParaGraph extends Dialog implements ActionListener
{
	Button YES,NO;
	
	ParaGraph( MainWindow WindowArea )
	{
		super( WindowArea, "段落", true );
		setLayout( new FlowLayout( ) );
		YES = new Button( "确定" );
		NO = new Button( "取消" );
		YES.addActionListener( this );
		NO.addActionListener( this );
		
		addWindowListener( new WindowAdapter( )
		{
			public void windowClosing( WindowEvent e )
			{
				setVisible( false );
			}
		}
		);
		
		setBounds( 100, 100, 60, 60 );
		add( YES );
		add( NO );
		setVisible( true );
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource( ) == YES )			//YES
		{
			JavaNote.FileBool=0;
		}
		else if( e.getSource( ) == NO )		//NO
		{
			dispose( );
		}
		else
		{
			System.out.println( "程序出错！" );
		}
	}
}

//设置类
class SetThing extends Dialog implements ActionListener
{
	Button YES,NO;
	SetThing( MainWindow WindowArea ){
		super( WindowArea, "设置", true );
		setLayout( new FlowLayout( ) );
		YES = new Button( "确定" );
		NO = new Button( "取消" );
		YES.addActionListener( this );
		NO.addActionListener( this );
		addWindowListener( new WindowAdapter( )
		{
			public void windowClosing( WindowEvent e )
			{
				dispose( );
			}
		}
		);
		setBounds( 100, 100, 60, 60 );
		add( YES );
		add( NO );
		setVisible( true );
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource( ) == YES )			//YES
		{
			JavaNote.FileBool=0;
		}
		else if( e.getSource( ) == NO )		//NO
		{
			dispose( );
		}
		else
		{
			System.out.println( "程序出错！" );
		}
	}
}

//文档类
class Helps extends JFrame implements ActionListener
{
	Box box1;
	Label HelpText[] = new Label[ 4 ];
	Button YES;

	Helps( ){
		setTitle( "声明一下！" );
		
		setLayout( new FlowLayout( ) );
		
		box1 = Box.createVerticalBox( );
		
		HelpText[ 0 ] = new Label( "本程序只是个人兴趣，" );
		HelpText[ 1 ] = new Label( "因而不涉及任何商业利益，" );
		HelpText[ 2 ] = new Label( "再且还不涉及任何义务和责任，" );
		HelpText[ 3 ] = new Label( "如有不服，请你自杀！" );

		
		YES = new Button( "确定" );
		YES.addActionListener( this );
		
		addWindowListener( new WindowAdapter( )
		{
			public void windowClosing( WindowEvent e )
			{
				dispose( );
			}
		}
		);
		setBounds( 100, 100, 300, 200 );
		
		for( int i = 0; i < 4; i++ )
			box1.add( HelpText[ i ] );
		
		box1.add( Box.createVerticalStrut( 8 ) );
		box1.add( YES );		
		add( box1 );
		setVisible( true );
	}
	
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource( ) == YES )			//YES
		{
			dispose( );
		}
		else
		{
			System.out.println( "程序出错！" );
		}
	}
}


class NetWindow extends JFrame	//JDialog
{
	JEditorPane editPane;					//上网所用的浏览区
	Thread thread;
	URL url;
	ScrollPane scroll = new ScrollPane( );
	MainWindow wins;
  
	NetWindow( MainWindow MainWin )
	{
		wins = MainWin;
		MainWin.setVisible( false );
		editPane = new JEditorPane( );
		editPane.setEditable( false );
		scroll.add( editPane );
		add( scroll );
		setBounds(100,100,700,400);
		setVisible( true );
		editPane.addHyperlinkListener( new HyperlinkListener( )
													{
														public void hyperlinkUpdate( HyperlinkEvent e )
														{
															if( e.getEventType( ) == HyperlinkEvent.EventType.ACTIVATED )
															{
																try
																{
																	editPane.setPage( e.getURL( ) );
																}
																catch( IOException e2 )
																{
																	editPane.setText( " " + e2 );
																}
															}
														}
													} );
																	
		validate( );
		try
		{
			editPane.setText( null );
			url = new URL( JavaNote.NetArea );
			editPane.setPage( url );
		}
		catch( Exception e1 )
		{
			return;
		}

		addWindowListener( new WindowAdapter( )
									{
										public void windowClosing( WindowEvent e )
								     	{
									     wins.setVisible( true );
									     dispose( );
									   }
									} );
	}
}


class MainWindow extends JFrame implements WindowListener, ActionListener, DocumentListener
{
	Image icons;
	Toolkit tool;
	
	MenuBar Mainmenubar;					//菜单条
	Menu mFile, mEdit, mForm, mAdvanced, mHelp;			//菜单栏
	MenuItem mF_N, mF_O, mF_S, mF_S_A, mF_P, mF_Q, mF_net;		//菜单项——文件
	MenuItem mE_Return, mE_X, mE_C, mE_V, mE_D, mE_Cls, mE_Find, mE_FindContinue, mE_FindAndChange, mE_Goto, mE_SelAll, mE_Time;	//菜单项——编辑
	MenuItem mO_Word, mO_Paragraph;				//菜单项——格式
	MenuItem mA_Compile, mA_SetValue;			//编译
	MenuItem mH_F1, mH_Public;				//菜单项——帮助
	JTextArea MainTextArea;					//文本区
	UpNet usenet;								//上网对话框
	
	//打印设置声明
	PrintJob printwork = null;
	Graphics g = null;
	
	MainWindow( String s )
	{
		super( s );
		/**setLayout( new FlowLayout( ) );
		  不添加可以出现文本框顶满窗口
		*/
		
		//设置图标
		tool = getToolkit( );
		icons = tool.getImage( "texts.png" );
	
		setIconImage( icons );
	
		Mainmenubar = new MenuBar( );
		
		//菜单栏
		mFile = new Menu( "文件" );
		mEdit = new Menu( "编辑" );
		mForm = new Menu( "格式" );
		mAdvanced = new Menu( "高级" );
		mHelp = new Menu( "帮助" );
		
		//菜单项
		//文件菜单项
		mF_N = new MenuItem( "新建" );
		mF_O = new MenuItem( "打开" );
		mF_net = new MenuItem( "上网" );
		mF_S = new MenuItem( "保存" );
		mF_S_A = new MenuItem( "另存为" );
		mF_P = new MenuItem( "打印" );
		mF_Q = new MenuItem( "退出" );
		
		//编辑菜单项
		mE_Return = new MenuItem( "澈消" );
		mE_X = new MenuItem( "剪切" );
		mE_C = new MenuItem( "复制" );
		mE_V = new MenuItem( "粘贴" );
		mE_D = new MenuItem( "删除" );
		mE_Cls = new MenuItem( "清空" );
		mE_Find = new MenuItem( "查找" );
		mE_FindContinue = new MenuItem( "查找下一个" );
		mE_FindAndChange = new MenuItem( "替换" );
		mE_Goto = new MenuItem( "转到" );
		mE_SelAll = new MenuItem( "全选" );
		mE_Time = new MenuItem( "时期/时间" );
		
		//格式菜单项
		mO_Word = new MenuItem( "字体" );
		mO_Paragraph = new MenuItem( "段落" );
		
		//高级
		mA_Compile = new MenuItem( "编译" );
		mA_SetValue = new MenuItem( "设置" );
		
		//帮助菜单项
		mH_F1 = new MenuItem( "文档" );
		mH_Public = new MenuItem( "版本信息" );
		
		//快捷键
		//文件区
		mF_N.setShortcut( new MenuShortcut( KeyEvent.VK_N ) );
		mF_O.setShortcut( new MenuShortcut( KeyEvent.VK_O ) );
		mF_net.setShortcut( new MenuShortcut( KeyEvent.VK_U ) );
		mF_S.setShortcut( new MenuShortcut( KeyEvent.VK_S ) );
		mF_P.setShortcut( new MenuShortcut( KeyEvent.VK_P ) );
		mF_Q.setShortcut( new MenuShortcut( KeyEvent.VK_Q ) );
		
		//编辑区
		mE_Return.setShortcut( new MenuShortcut( KeyEvent.VK_Z ) );
		mE_X.setShortcut( new MenuShortcut( KeyEvent.VK_X ) );
		mE_C.setShortcut( new MenuShortcut( KeyEvent.VK_C ) );
		mE_V.setShortcut( new MenuShortcut( KeyEvent.VK_V ) );
		mE_D.setShortcut( new MenuShortcut( KeyEvent.VK_DELETE ) );
		mE_Cls.setShortcut( new MenuShortcut( KeyEvent.VK_F7 ) );
		mE_Find.setShortcut( new MenuShortcut( KeyEvent.VK_F ) );
		mE_FindContinue.setShortcut(new MenuShortcut( KeyEvent.VK_F3 ) );
		mE_FindAndChange.setShortcut( new MenuShortcut(KeyEvent.VK_H ) );
		mE_Goto.setShortcut( new MenuShortcut( KeyEvent.VK_G ) );
		mE_SelAll.setShortcut( new MenuShortcut( KeyEvent.VK_A ) );
		mE_Time.setShortcut( new MenuShortcut( KeyEvent.VK_F3 ) );
		
		//格式区
		mO_Word.setShortcut(new MenuShortcut( KeyEvent.VK_B ) );
		mO_Paragraph.setShortcut(new MenuShortcut( KeyEvent.VK_G ) );
		
		//高级
		mA_Compile.setShortcut(new MenuShortcut( KeyEvent.VK_L ) );
		
		//帮助区
		mH_F1.setShortcut( new MenuShortcut( KeyEvent.VK_F1 ) );
		mH_Public.setShortcut( new MenuShortcut( KeyEvent.VK_I ) );
		
		//界面菜单
		mFile.add( mF_N );
		mFile.add( mF_O );
		mFile.add( mF_net );
		mFile.add( mF_S );
		mFile.add( mF_S_A );
		mFile.addSeparator( );
		mFile.add( mF_P );
		mFile.addSeparator( );
		mFile.add( mF_Q );
		mEdit.add( mE_Return );
		mEdit.addSeparator( );
		mEdit.add( mE_X );
		mEdit.add( mE_C );
		mEdit.add( mE_V );
		mEdit.add( mE_D );
		mEdit.add( mE_Cls );
		mEdit.add( mE_Find );
		mEdit.add( mE_FindContinue );
		mEdit.add( mE_FindAndChange );
		mEdit.add( mE_Goto );
		mEdit.addSeparator( );
		mEdit.add( mE_SelAll );
		mEdit.add( mE_Time);
		
		//格式区
		mForm.add( mO_Word );
		mForm.add( mO_Paragraph );
		
		//高级
		mAdvanced.add( mA_Compile );
		mAdvanced.add( mA_SetValue );
		
		//帮助区
		mHelp.add( mH_F1 );
		mHelp.add( mH_Public );
		
		//控制监视器
		mF_N.addActionListener( this );
		mF_O.addActionListener( this );
		mF_net.addActionListener( this );
		mF_S.addActionListener( this );
		mF_S_A.addActionListener( this );
		mF_P.addActionListener( this );
		mF_Q.addActionListener( this );
		mE_Return.addActionListener( this );
		mE_X.addActionListener( this );
		mE_C.addActionListener( this );
		mE_V.addActionListener( this );
		mE_D.addActionListener( this );
		mE_Cls.addActionListener( this );
		mE_Find.addActionListener( this );
		mE_FindContinue.addActionListener(this);
		mE_FindAndChange.addActionListener( this );
		mE_Goto.addActionListener( this );
		mE_SelAll.addActionListener( this );
		mE_Time.addActionListener( this );
		mO_Word.addActionListener( this );
		mO_Paragraph.addActionListener( this );
		mA_Compile.addActionListener( this );
		mA_SetValue.addActionListener( this );
		mH_F1.addActionListener( this );
		mH_Public.addActionListener( this );
		
		Mainmenubar.add( mFile );
		Mainmenubar.add( mEdit );
		Mainmenubar.add( mForm );
		Mainmenubar.add( mAdvanced );
		Mainmenubar.add( mHelp );
		
		//界面文本框
		MainTextArea = new JTextArea( );
		( MainTextArea.getDocument( ) ).addDocumentListener( this );
		setMenuBar( Mainmenubar );
		add( new JScrollPane( MainTextArea ) );		//添加文本框
		setBounds( 100, 100, 700, 400 );
		addWindowListener( this );
		setVisible( true );
		validate( );
	}	
	
	void ExitProblem( )
	{
		if( JavaNote.FileBool == 1 )
		{
			int n = JOptionPane.showConfirmDialog( this, "文件已经被改动，是否？", "保存问题", JOptionPane.YES_NO_CANCEL_OPTION );
			switch( n )
			{
				case 0: try{
						SaveWindow savethis = new SaveWindow( this, "保存" );
						if( !( JavaNote.FilePath.equals( null ) ) ) System.exit( 0 );
					}
					catch( NullPointerException e ){ }
				case 1: System.exit(0);
				case 2: break;
				default: System.out.println("程序出错！");
			}
		}
		else System.exit(0);

	}
	
	//窗口基本控制
	public void windowActivated( WindowEvent e ){ }
	public void windowDeactivated( WindowEvent e ){ }
	public void windowClosing( WindowEvent e )
	{
		ExitProblem( );
	}
	public void windowClosed( WindowEvent e ){ }
	public void windowIconified( WindowEvent e ){ }
	public void windowDeiconified( WindowEvent e ){ }
	public void windowOpened( WindowEvent e ){ }
	
	//菜单处理
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource( ) == mF_N )						//新建
		{
			if ( !( MainTextArea.getText( ).equals( "" ) ) )
			{
				int n=JOptionPane.showConfirmDialog( this,"是否保存原文件？", "新建问题", JOptionPane.YES_NO_CANCEL_OPTION );
				
				switch( n )
				{
					case 0: SaveWindow savethis = new SaveWindow( this, "保存" );
						try
						{
							if( !( JavaNote.FilePath.equals( null ) ) )
							{
								MainTextArea.setText("");
							}
						}
						catch(NullPointerException c){}
						
						//设置文本参数为初始状态
						JavaNote.FilePath = "";
						JavaNote.FileName = "";
						JavaNote.FileBool = 2;
						this.setTitle( "文本编辑器" );
						break;
					case 1: MainTextArea.setText( "" );
						JavaNote.FilePath="";
						JavaNote.FileName="";
						JavaNote.FileBool=2;
						this.setTitle( "文本编辑器" );
						break;
					case 2: break;
					default: System.out.println( "程序出错！" );
				}
			}
			else MainTextArea.setText( "" );
		}
		else if( e.getSource( ) == mF_O )
		{
			OpenWindow openthis = new OpenWindow( this );
			if( JavaNote.FileName != null )
				this.setTitle( "文本编辑器-" + JavaNote.FilePath + JavaNote.FileName );
		}
		else if( e.getSource( ) == mF_net )
		{
			usenet = new UpNet( this, "上网地址" );
		}
		else if( e.getSource( ) == mF_S )
		{
			SaveWindow savethis = new SaveWindow( this, "保存" );
			if( JavaNote.FileName != "" )
				this.setTitle("文本编辑器-"+JavaNote.FilePath + JavaNote.FileName);
		}
		else if(e.getSource()==mF_S_A)
		{
			SaveWindow saveAsthis = new SaveWindow( this, "另存为……" );
			if( JavaNote.FileName != "" )
				this.setTitle( "文本编辑器-" + JavaNote.FilePath + JavaNote.FileName );
		}
		else if( e.getSource() == mF_P )
		{
			System.out.println( "打印" );
		}
		else if( e.getSource() == mF_Q )					//退出
		{
			ExitProblem( );
		}
		else if(e.getSource( ) == mE_Return )
		{
			System.out.println( "澈消" );
		}
		else if( e.getSource( ) == mE_X )
		{
			MainTextArea.cut( );
		}
		else if(e.getSource()==mE_C)
		{
			MainTextArea.copy( );
		}
		else if( e.getSource( ) == mE_V )
		{
			MainTextArea.paste( );
		}
		else if( e.getSource( ) == mE_D )
		{
			System.out.println( "删除" );
		}
		else if( e.getSource( ) == mE_Cls )				//清空
		{
			MainTextArea.setText( "" );
		}
		else if( e.getSource( ) == mE_Find )
		{
			System.out.println( "查找" );
		}
		else if( e.getSource( ) == mE_FindContinue )
		{
			System.out.println( "查找下一个" );
		}
		else if( e.getSource( ) == mE_FindAndChange )
		{
			System.out.println( "替换" );
		}
		else if( e.getSource() == mE_Goto )
		{
			System.out.println( "转到" );
		}
		else if( e.getSource( ) == mE_SelAll )
		{
			MainTextArea.selectAll( );
		}
		else if( e.getSource( ) == mO_Word )				//字体
		{
			WordStyle WStyle = new WordStyle( this );
			if( JavaNote.Font_Word != "" )
			{
				Font s = new Font( JavaNote.Font_Word, Font.PLAIN, 12 );
				MainTextArea.setFont( s );
				JavaNote.Font_Word = "";
			}
		}
		else if( e.getSource() == mO_Paragraph )		//段落
		{
			ParaGraph PStyle = new ParaGraph( this );
		}
		else if( e.getSource( ) == mA_Compile )			//编译
		{
			do{
			
				//如果没有保存
				if( JavaNote.FileName == "" ){
					SaveWindow savethis = new SaveWindow( this, "保存" );
					this.setTitle("文本编辑器-"+JavaNote.FilePath+JavaNote.FileName);
				}
				else
				{
					try
					{
						byte content1[ ] = new byte[ 200 ];
						int i;
						String InputThing;
						Runtime ce = Runtime.getRuntime( );
						InputStream ins;
						ins = ce.exec( "javac " + JavaNote.FilePath + JavaNote.FileName ).getInputStream( );
						BufferedInputStream bin = new BufferedInputStream( ins );
						while( ( i = bin.read( content1,0,200 ) ) != -1 ) System.out.println( content1 );
					}
					catch( IOException ec ){ }
					break;
				}
			}while( true );
		}
		else if( e.getSource( ) == mA_SetValue )		//设置
		{
			SetThing SetProgram=new SetThing( this );
		}
		else if( e.getSource( ) == mH_F1 ){
			Helps hp = new Helps( );
			System.out.println( "文档" );
		}
		else if( e.getSource( ) == mH_Public )		//版本信息
		{
			try
			{
				File warn = new File( "ding.wav" );
				AudioClip clip;
				URL urlfile = warn.toURL( );
				clip=Applet.newAudioClip(urlfile);
				clip.play( );
				JOptionPane.showMessageDialog( this, "版权归杨振成所有！", "版权信息", JOptionPane.WARNING_MESSAGE );
				clip.stop( );
			}
			catch( Exception ed )
			{
				System.out.println( ed );
			}
		}
		else if( e.getSource( ) == mE_Time )		//日期/时间
		{
			Date nowTime = new Date( );
			String DateTime = String.valueOf( nowTime );
			MainTextArea.append( "\n" + DateTime );
		}
		else
		{
			System.out.println( "程序错误！" );
		}
	}
	
	//文本框监视器
	public void changedUpdate( DocumentEvent e )
	{
		JavaNote.FileBool = 1;
	}
	public void removeUpdate( DocumentEvent e )
	{
		JavaNote.FileBool = 1;
	}
	public void insertUpdate( DocumentEvent e )
	{
		JavaNote.FileBool = 1;
	}
}
