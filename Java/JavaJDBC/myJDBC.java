
package jdbc;

/**
 *
 * @author Ollie Dowling
 * @version 1.0
 */
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

class myJDBC {

    public static void main(String[] args) {
        JFrame f = new JFrame("~Retrieve Data~");
        JLabel label1 = new JLabel("<html><font color=black<font size=+0.5><b><i>'First Name:</i></b></font></html>");
        JLabel label2 = new JLabel("<html><font color=black<font size=+0.5><b><i>'Last Name:</i></b></font></html>");
        JLabel label3 = new JLabel("<html><font color=black<font size=+0.5><b><i>'Gender:</i></b></font></html>");
        JLabel label4 = new JLabel("<html><font color=black<font size=+0.5><b><i>'Hire Date:</i></b></font></html>");
        JLabel label5 = new JLabel("<html><font color=black<font size=+0.5><b><i>'Birth Date:</i></b></font></html>");
        JLabel label6 = new JLabel("<html><font color=red<font size=+0.5><b><i>'Emp No:</i></b></font></html>");
        JTextField text1 = new JTextField(20);
        JTextField text2 = new JTextField(20);
        JTextField text3 = new JTextField(20);
        JTextField text4 = new JTextField(20);
        JTextField text5 = new JTextField(20);
        JTextField text6 = new JTextField(20);
        
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        text1.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        text2.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        text3.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        text4.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        text5.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		text6.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        //UI Manager used for Background Colour
	UIManager.put("OptionPane.background", Color.white);
	UIManager.put("Panel.background", Color.white);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from employees where emp_no = 123");
            String first_name = "", last_name = "";
            String gender = "", hire_date = "";
            String birth_date = "", emp_no = "";
            System.out.println("connected");
            //System.out.println(firstname);
            if (rs.next()) {
                first_name = rs.getString("first_name");
                last_name = rs.getString("last_name");
                gender = rs.getString("gender");
                hire_date = rs.getString("hire_date");
                birth_date = rs.getString("birth_date");
                emp_no = rs.getString("emp_no");
            }
            text1.setText(first_name);
            text2.setText(last_name);
            text3.setText(gender);
            text4.setText(hire_date);
            text5.setText(birth_date);
            text6.setText(emp_no);
        } catch (Exception e) {
        }
        JPanel p = new JPanel(new GridLayout(0, 2));
        p.add(label1);
        p.add(text1);
        p.add(label2);
        p.add(text2);
        p.add(label3);
        p.add(text3);
        p.add(label4);
        p.add(text4);
        p.add(label5);
        p.add(text5);
        p.add(label6);
        p.add(text6);
        f.add(p);
        f.setVisible(true);
        f.pack();
    }
}
