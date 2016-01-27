package rmicalculator;

import java.rmi.Naming;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelloWorldClientCalcu extends JFrame implements ActionListener {

	/**
	 * @author Ollie Dowling
	 * @version 1.0
	 */
	private static final long serialVersionUID = 1L;

	static String message = "blank";

	// The HelloWorld object "obj" is the identifier that is
	// used to refer to the remote object that implements
	// the HelloWorld interface.

	public double n1 = 0.0;
	public double  d1;
	public double n2 = 0.0;
	private JButton jbutton[] = new JButton[21];
	private JTextField tfield;
	Container contain;
	int button,i;
	public String str;
	public String number="";
	private JPanel top;
	private JPanel bot;
	public HelloWorldClientCalcu()
	{
		setTitle("calculator");
		top = new JPanel();
		bot = new JPanel();
		tfield = new JTextField(22);
		tfield.setEditable(false);
		contain = getContentPane();
		bot.setLayout(new GridLayout(5,4));
		top.add(tfield);
		contain.add(top,"North");
		for(int i=0;i<10;i++)
		{
			jbutton[i] = new JButton(""+i);
		}
		jbutton[10] = new JButton("+");
		jbutton[11] = new JButton("-");
		jbutton[12] = new JButton("*");
		jbutton[13] = new JButton("/");
		jbutton[14] = new JButton("clear");
		jbutton[15] = new JButton(".");
		jbutton[16] = new JButton("=");
		for(int i = 0;i<17;i++)
		{
			jbutton[i].addActionListener(this);
			bot.add(jbutton[i]);
		}
		contain.add(bot,"Center"); //center panel with buttons
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}     
	public void actionPerformed(ActionEvent ae)
	{  
		str = ae.getActionCommand(); //get text
		System.out.println(str);
		for(int i=0;i<10;i++)
		{
			if(ae.getSource()==jbutton[i])
			{
				number = number+str;     
				tfield.setText(number);//concatenation
			}
		}
		if((ae.getSource())==jbutton[15])               
		{
			number = number+str;                       
			tfield.setText(number);
		}
		for(int i=10;i<14;i++)
		{
			if(ae.getSource()==jbutton[i])           
			{
				button = i-9; 
				if(number!="")    
				{
					tfield.setText("");
					n1 = Double.parseDouble(number);//convert string in to double
					number="";                           
				}
				else
				{
					tfield.setText("");
				}
			}
		}
		if(ae.getSource()==jbutton[16]) 
		{
			if(!(tfield.getText().equals("")))
			{
				tfield.setText("");
				n2 = Double.parseDouble(number);
				number = "";
				try
				{
					String url = "rmi://localhost/server";
					HelloWorldCalcuInterface intf =(HelloWorldCalcuInterface) Naming.lookup(url);
					switch(button)
					{
					case 1:
						d1 = intf.addition(n1,n2);
						break;  
					case 2:
						d1 = intf.subtraction(n1,n2);
						break;
					case 3:
						d1 = intf.multiplication(n1,n2);
						break;
					case 4:
						d1 = intf.division(n1,n2);
						break;
					default:
						d1 = 0.0;
					}
					str = String.valueOf(d1); 
					tfield.setText(str);    
				}
				catch(Exception e){}
			}
			else
			{
				tfield.setText("");
			}
		}
		if(ae.getSource()==jbutton[14])
		{
			tfield.setText("");
			number = "";
			n1=0.0;
			n2=0.0;
			button=0;
		}
	}
	public static void main(String args[])
	{
		JFrame frame = new HelloWorldClientCalcu();
		frame.setSize(300,300);
		frame.setVisible(true);
	}
}