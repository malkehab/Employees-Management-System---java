import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


//==================================================================

class data {
    public static int id;
    public static String name;
    public static String department;
    public static long phone;
    public static String password;
    public static int hours;
    public static double salary;

}

class Schema {
    private int locationX,locationY,width,height,sizeX,sizeY;
    private boolean isVisible;
    public Schema() {
        // Default values
        this.locationX = 300;
        this.locationY = 150;
        this.sizeX=1180;
        this.sizeY=650;
        this.isVisible = true;
    }
    public int getLocationY() {
        return locationY;
    }
    public int getLocationX() {
        return locationX;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public int sizeX() {
        return sizeX;
    }
    public int sizeY() {
        return sizeY;
    }
}

class ButtonCustomizer {
    public static void customizeButton(JButton button) {
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(72, 61, 139));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }
}


//==================================================================

class Log_in_frame extends Schema {
    public Log_in_frame() {
        JFrame frame = new JFrame("Log In");
        frame.getContentPane().add(new Log_in_panel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizeX(),sizeY());
        frame.setLocation(getLocationX(), getLocationY());
        frame.setVisible(isVisible());
    }


}

class Log_in_panel extends JPanel {
    private JTextField foruserid;
    private JPasswordField foruserpassword;

    public Log_in_panel() {
        setBackground(Color.WHITE);
        setLayout(null);
        setBackground(new Color(72, 61, 139));

        JLabel label = new JLabel("Please enter your data if you are an existing employee or sign-up.");
        label.setBounds(20, 40, 2000, 60);
        label.setFont(new Font("Monospaced", Font.BOLD, 30));
        label.setForeground(Color.white);

        // label for ID
        JLabel ID = new JLabel("ID:");
        ID.setBounds(150, 200, 200, 30);
        ID.setForeground(Color.white);
        // text field for ID
        foruserid = new JTextField();
        foruserid.setBounds(250, 200, 200, 30);

        // label for password textfield
        JLabel password = new JLabel("Password:");
        password.setBounds(150, 250, 200, 30);
        password.setForeground(Color.white);
        // password textfield
        foruserpassword = new JPasswordField();
        foruserpassword.setBounds(250, 250, 200, 30);

        // log in button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(200, 320, 100, 35);
        ButtonCustomizer.customizeButton(loginButton);

        // sign up button
        JButton signUpButton = new JButton("Sign up");
        signUpButton.setBounds(350, 320, 100, 35);
        ButtonCustomizer.customizeButton(signUpButton);

        JButton home_button = new JButton("Home");
        home_button.setBounds(25,20,55,25);
        home_button.setFont(new Font("Arial", Font.BOLD, 5));
        ButtonCustomizer.customizeButton(home_button);
        add(home_button);
        home_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new home_frame();
                SwingUtilities.getWindowAncestor(Log_in_panel.this).dispose();
            }
        });


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(Log_in_panel.this).dispose();
                checkLogin();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new sign_up_frame();
                SwingUtilities.getWindowAncestor(Log_in_panel.this).dispose();
            }
        });

        add(label);
        add(foruserid);
        add(ID);
        add(password);
        add(foruserpassword);
        add(loginButton);
        add(signUpButton);

    }

    private boolean validateInput(String id, String password) {
        if (id.isEmpty() || password.isEmpty()) return false;
        int u = id.length();
        for (int i = 0; i < u; i++) {
            if (id.charAt(i) < '0' || id.charAt(i) > '9') return false;
        }
        return true;
    }

    private void checkLogin() {
        String url = "jdbc:mysql://127.0.0.1:3306/employee";
        String username = "root";
        String password = "root";

        try {
            String userid = foruserid.getText();
            String userpassword = String.valueOf(foruserpassword.getPassword());
            if (!validateInput(userid, userpassword)) {
                JOptionPane.showMessageDialog(null, "Invalid format, try again");
                new Log_in_frame();
                SwingUtilities.getWindowAncestor(Log_in_panel.this).dispose();
                return;
            }

            Connection connection = DriverManager.getConnection(url, username, password);
            int ID = Integer.parseInt(userid);
            data.id=ID;
            String sql = "select * from employee_table where id= ? AND  password = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, ID);
            statement.setString(2, userpassword);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                new hours_frame();
                SwingUtilities.getWindowAncestor(Log_in_panel.this).dispose();

            } else {
                // الداتا بتاعته كده مش تمام والمفروض اظهرله ال check frame
                new Check_frame();
            }
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error connecting to the database: " + ex.getMessage());
        }
    }
}

//==================================================================

class Check_frame extends Schema {
    public Check_frame() {
        JFrame frame = new JFrame("Check");
        frame.getContentPane().add(new Check_panel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizeX(),sizeY());
        frame.setLocation(getLocationX(), getLocationY());
        frame.setVisible(isVisible());
    }
}

class Check_panel extends JPanel {
    public Check_panel() {
        setLayout(null);
        setBackground(new Color(72, 61, 139));

        JLabel label = new JLabel("Invalid username or ID. Please try again...");
        label.setBounds(120, 40, 800, 60);
        label.setFont(new Font("Monospaced", Font.BOLD, 30));
        label.setForeground(Color.white);


        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 400, 100, 35);
        ButtonCustomizer.customizeButton(loginButton);


        JButton signUpButton = new JButton("Sign up");
        signUpButton.setBounds(250, 400, 100, 35);
        ButtonCustomizer.customizeButton(signUpButton);




        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new sign_up_frame();
                SwingUtilities.getWindowAncestor(Check_panel.this).dispose();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Log_in_frame();
                SwingUtilities.getWindowAncestor(Check_panel.this).dispose();
            }
        });



        add(label);
        add(loginButton);
        add(signUpButton);

    }


}

//==================================================================

class sign_up_frame extends Schema {
    //     first name : _________
    //     last name  : _________
    //     department : _________
    //     start job (is default)
    //            sign up
    public sign_up_frame() {
        JFrame frame = new JFrame("sign up");
        frame.getContentPane().add(new sign_up_panel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizeX(),sizeY());
        frame.setLocation(getLocationX(), getLocationY());
        frame.setVisible(isVisible());
    }
}

class sign_up_panel extends JPanel  {

    //     Name : _________
    //     Department : _________
    //     Phone : _________
    //     Password : _________
    //            save

    public sign_up_panel() {
        setLayout(null);
        setBackground(new Color(72, 61, 139));

        JLabel name_label = new JLabel(" Name : ");
        name_label.setBounds(40, 20, 100, 30);
        name_label.setFont(new Font("serif", Font.PLAIN, 20));
        name_label.setForeground(Color.WHITE);


        JTextField name_field = new JTextField();
        name_field.setBounds(150, 20, 150, 30);


        JLabel department_label = new JLabel(" Department : ");
        department_label.setBounds(40, 70, 130, 30);
        department_label.setFont(new Font("serif", Font.PLAIN, 20));
        department_label.setForeground(Color.WHITE);


        JTextField department_field = new JTextField();
        department_field.setBounds(170, 70, 170, 33);


        JLabel phone_label = new JLabel(" Phone : ");
        phone_label.setBounds(40, 120, 100, 30);
        phone_label.setFont(new Font("serif", Font.PLAIN, 20));
        phone_label.setForeground(Color.WHITE);


        JTextField phone_field = new JTextField();
        phone_field.setBounds(150, 120, 150, 30);


        JLabel password_label = new JLabel(" Password : ");
        password_label.setBounds(40, 170, 100, 30);
        password_label.setFont(new Font("serif", Font.PLAIN, 20));
        password_label.setForeground(Color.WHITE);


        JTextField password_field = new JTextField();
        password_field.setBounds(150, 170, 150, 30);

        JButton save_button = new JButton(" Save ");
        save_button.setBounds(200, 100, 150, 50);
        ButtonCustomizer.customizeButton(save_button);
        save_button.setLocation(230, 250);


        JButton log_in_button = new JButton(" log in ");
        log_in_button.setBounds(200, 100, 150, 50);
        ButtonCustomizer.customizeButton(log_in_button);
        log_in_button.setLocation(40, 250);

        JButton home_button = new JButton("Home");
        home_button.setBounds(40,350,55,25);
        home_button.setFont(new Font("Arial", Font.BOLD, 5));
        ButtonCustomizer.customizeButton(home_button);
        add(home_button);
        home_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new home_frame();
                SwingUtilities.getWindowAncestor(sign_up_panel.this).dispose();
            }
        });

        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {

                if (!name_field.getText().isEmpty() && !department_field.getText().isEmpty() && !phone_field.getText().isEmpty() && !password_field.getText().isEmpty()) {

                    try {
                        data.phone = Integer.parseInt(phone_field.getText());
                    } catch (Exception e) {
                        new inttt_frame();
                        SwingUtilities.getWindowAncestor(sign_up_panel.this).dispose();
                        return;
                    }

                    data.name = name_field.getText();
                    data.department = department_field.getText();
                    data.password = password_field.getText();
                    data.id = (int) (23000 * Math.random());

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://127.0.0.1:3306/employee",
                                "root",
                                "root"
                        );
                        // id name department phone password hours salary
                        String query = "INSERT INTO employee_table values (? , ? , ? , ? , ? , 0 , 0)";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, data.id);
                        statement.setString(2, data.name);
                        statement.setString(3, data.department);
                        statement.setLong(4, data.phone);
                        statement.setString(5, data.password);
                        int rowsAffected = statement.executeUpdate();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "your id is : " + data.id);
                    new Log_in_frame();
                    SwingUtilities.getWindowAncestor(sign_up_panel.this).dispose();

                } else {
                    JOptionPane.showMessageDialog(null,"Please Enter Valid Data :)");
                    new sign_up_frame();
                    SwingUtilities.getWindowAncestor(sign_up_panel.this).dispose();
                }
            }
        });

        log_in_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Log_in_frame();
                SwingUtilities.getWindowAncestor(sign_up_panel.this).dispose();
            }
        });

        add(name_label);
        add(name_field);
        add(department_label);
        add(department_field);
        add(phone_label);
        add(phone_field);
        add(password_label);
        add(password_field);
        add(save_button);
        add(log_in_button);
    }
}

//==================================================================

class inttt_frame {

    //  please enter data :)
    //           ok

    inttt_frame() {
        JFrame frame = new JFrame("Get Salary ");
        frame.getContentPane().add(new inttt_panel());
        frame.setSize(250, 250);

        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class inttt_panel extends JPanel {
    public inttt_panel() {
        setLayout(new FlowLayout());

        JLabel id_label = new JLabel("Please Enter Valid Data :)");

        JButton save_button = new JButton(" OK ");
        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new sign_up_frame();
                SwingUtilities.getWindowAncestor(inttt_panel.this).dispose();
            }
        });

        add(id_label);
        add(save_button);
    }
}

//==================================================================

class update_frame extends Schema {
    update_frame() {
        JFrame frame = new JFrame("Update Employee");
        frame.setSize(sizeX(),sizeY());
        frame.setLocation(getLocationX(), getLocationY());
        frame.setVisible(isVisible());
        frame.getContentPane().add(new update_panel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

class update_panel extends JPanel {
    update_panel() {
        setBackground(new Color(72, 61, 139));
        setLayout(null);


        JLabel lbID = new JLabel("ID");
        lbID.setBounds(40, 20, 100, 30);
        lbID.setFont(new Font("serif", Font.PLAIN, 20));
        lbID.setForeground(Color.WHITE);
        add(lbID);

        JTextField tfID = new JTextField();
        tfID.setBounds(150, 20, 150, 30);
        add(tfID);

        JLabel lbdep = new JLabel("Departement");
        lbdep.setBounds(40, 70, 130, 30);
        lbdep.setFont(new Font("serif", Font.PLAIN, 20));
        lbdep.setForeground(Color.WHITE);
        add(lbdep);

        JTextField tfdep = new JTextField();
        tfdep.setBounds(170, 70, 170, 33);
        add(tfdep);

        JLabel lbpass = new JLabel("Password");
        lbpass.setBounds(40, 120, 100, 30);
        lbpass.setFont(new Font("serif", Font.PLAIN, 20));
        lbpass.setForeground(Color.WHITE);
        add(lbpass);

        JTextField tfpass = new JTextField();
        tfpass.setBounds(150, 120, 150, 30);
        add(tfpass);

        JLabel lbhours = new JLabel("Hours");
        lbhours.setBounds(40, 170, 100, 30);
        lbhours.setFont(new Font("serif", Font.PLAIN, 20));
        lbhours.setForeground(Color.WHITE);
        add(lbhours);

        JTextField tfhours = new JTextField();
        tfhours.setBounds(150, 170, 150, 30);
        add(tfhours);

        JButton clickhere = new JButton("OK");
        clickhere.setBounds(200, 100, 150, 50);
        ButtonCustomizer.customizeButton(clickhere);
        clickhere.setLocation(130, 250);
        add(clickhere);

        JButton home_button = new JButton("Home");
        home_button.setBounds(25,20,55,25);
        home_button.setFont(new Font("Arial", Font.BOLD, 5));
        ButtonCustomizer.customizeButton(home_button);
        add(home_button);
        home_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new home_frame();
                SwingUtilities.getWindowAncestor(update_panel.this).dispose();
            }
        });

        setSize(400, 300);

        clickhere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tfID.getText().isEmpty()&&!tfdep.getText().isEmpty()&&!tfpass.getText().isEmpty()&&!tfhours.getText().isEmpty()){
                    try {
                        String id = tfID.getText();
                        String dep = tfdep.getText();
                        String pass = tfpass.getText();
                        String hours = tfhours.getText();

                        try{
                            Integer.parseInt(id);
                            Integer.parseInt(hours);
                        }catch (Exception d){
                            JOptionPane.showMessageDialog(null,"pleas enter valid data.");
                            new update_frame();
                            SwingUtilities.getWindowAncestor(update_panel.this).dispose();
                            return;
                        }


                        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee", "root", "root");
                        PreparedStatement stmt = con.prepareStatement("UPDATE employee_table SET department = ? , password=? , hours=? WHERE id=? ");
                        stmt.setString(1, dep);
                        stmt.setString(2, pass);
                        stmt.setInt(3, Integer.parseInt(hours));
                        stmt.setInt(4, Integer.parseInt(id));

                        int rowsAffected = stmt.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Employee record updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "No records updated!");
                        }

                    } catch (Exception c) {
                        JOptionPane.showMessageDialog(null,"pleas enter valid data.");
                        new update_frame();
                        SwingUtilities.getWindowAncestor(update_panel.this).dispose();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"pleas enter data.");
                    new update_frame();
                    SwingUtilities.getWindowAncestor(update_panel.this).dispose();
                }

            }
        });

    }
}

//==================================================================

class delete_panel extends JPanel {
    delete_panel() {
        setLayout(null);
        setBackground(new Color(72, 61, 139));

        JLabel labelempId = new JLabel("Employee Id:");
        labelempId.setBounds(50, 50, 100, 30);
        add(labelempId);

        JButton d = new JButton("Delete");
        d.setBounds(150, 140, 100, 30);
        ButtonCustomizer.customizeButton(d);
        add(d);

        JTextField textFieldEmpId = new JTextField();
        textFieldEmpId.setBounds(150, 50, 150, 30);
        add(textFieldEmpId);

        setSize(400, 300);

        JButton home_button = new JButton("Home");
        home_button.setBounds(25,20,55,25);
        home_button.setFont(new Font("Arial", Font.BOLD, 5));
        ButtonCustomizer.customizeButton(home_button);
        add(home_button);
        home_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new home_frame();
                SwingUtilities.getWindowAncestor(delete_panel.this).dispose();
            }
        });

        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = textFieldEmpId.getText();
                if (!idText.isEmpty()) {
                    int id;

                    try {
                        id = Integer.parseInt(idText);
                    } catch (Exception d) {
                        JOptionPane.showMessageDialog(null, "enter valid input");
                        delete_frame frame = new delete_frame();
                        SwingUtilities.getWindowAncestor(delete_panel.this).dispose();
                        return;
                    }

                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee", "root", "root");
                        PreparedStatement stat = con.prepareStatement("SELECT * FROM employee_table WHERE id = ?");
                        stat.setInt(1, id);
                        ResultSet re = stat.executeQuery();

                        stat = con.prepareStatement("DELETE FROM employee_table WHERE id = ?");
                        stat.setInt(1, id);
                        int rowsAffected = stat.executeUpdate();


                        if (re.next()) {
                            String name = re.getString("name");
                            JOptionPane.showMessageDialog(null, "employee " + name + " has deleted");
                            SwingUtilities.getWindowAncestor(delete_panel.this).dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "employee not exist try again");
                        }


                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "enter valid input");
                    delete_frame frame = new delete_frame();
                    SwingUtilities.getWindowAncestor(delete_panel.this).dispose();
                }
            }
        });

    }
}

class delete_frame extends Schema {
    public delete_frame() {
        JFrame frame = new JFrame("Delete Users");
        frame.getContentPane().setBackground(new Color(72, 61, 139));
        frame.getContentPane().add(new delete_panel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizeX(),sizeY());
        frame.setLocation(getLocationX(), getLocationY());
        frame.setVisible(isVisible());
    }

}

//==================================================================

class control_frame extends Schema{
    public control_frame() {
        JFrame frame = new JFrame("Get Salary ");
        frame.getContentPane().add(new control_panel());
        frame.getContentPane().setBackground(new Color(72, 61, 139));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizeX(),sizeY());
        frame.setLocation(getLocationX(), getLocationY());
        frame.setVisible(isVisible());
    }
}

class control_panel extends JPanel {
    control_panel() {
        setLayout(null);
        setBackground(new Color(72, 61, 139));
        JLabel labelempId = new JLabel("Delete or Update Users:");
        labelempId.setFont(new Font("serif", Font.PLAIN, 30));
        labelempId.setBounds(40, 40, 1000, 60);
        add(labelempId);

        JButton delete = new JButton("Delete");
        delete.setBounds(80, 200, 100, 30);
        ButtonCustomizer.customizeButton(delete);
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a new frame when the "Delete" button is clicked
                delete_frame frame = new delete_frame();
                SwingUtilities.getWindowAncestor(control_panel.this).dispose();
            }
        });
        add(delete);

        JButton update = new JButton("Update");
        update.setBounds(220, 200, 100, 30);
        ButtonCustomizer.customizeButton(update);
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a new frame when the "Update" button is clicked
                update_frame frame = new update_frame();
                SwingUtilities.getWindowAncestor(control_panel.this).dispose();
            }
        });
        JButton home_button = new JButton("Home");
        home_button.setBounds(25,20,55,25);
        home_button.setFont(new Font("Arial", Font.BOLD, 5));
        ButtonCustomizer.customizeButton(home_button);
        add(home_button);
        home_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new home_frame();
                SwingUtilities.getWindowAncestor(control_panel.this).dispose();
            }
        });
        add(update);


    }
}

//==================================================================

class home_frame extends Schema{
    public home_frame() {
        JFrame frame = new JFrame("Home Frame");
        frame.getContentPane().add(new home_panel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizeX(),sizeY());
        frame.setLocation(getLocationX(), getLocationY());
        frame.setVisible(isVisible());
    }
}

class home_panel extends JPanel {
    public home_panel() {
        setLayout(null);
        setBackground(new Color(72, 61, 139));

        JLabel headingLabel = new JLabel("Welcome To Our System");
        headingLabel.setBounds(120, 40, 1000, 60);
        headingLabel.setFont(new Font("Serif", Font.ITALIC, 30));
        headingLabel.setForeground(Color.WHITE);


        JButton adminButton = new JButton("Admin");
        adminButton.setBounds(700, 400, 120, 40);
        ButtonCustomizer.customizeButton(adminButton);


        JButton employeeButton = new JButton("Employee");
        employeeButton.setBounds(900, 400, 120, 40);
        ButtonCustomizer.customizeButton(employeeButton);


        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* go to control frame */
                control_frame controlFrame = new control_frame();
                SwingUtilities.getWindowAncestor(home_panel.this).dispose();
            }
        });


        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* go to login frame */
                Log_in_frame loginFrame = new Log_in_frame();
                SwingUtilities.getWindowAncestor(home_panel.this).dispose();
            }
        });

        add(headingLabel);
        add(adminButton);
        add(employeeButton);
    }
}

//==================================================================

class hours_frame  extends Schema{
    public hours_frame() {
        JFrame frame = new JFrame("Insert Hours");
        frame.getContentPane().add(new hours_panel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizeX(),sizeY());
        frame.setLocation(getLocationX(), getLocationY());
        frame.setVisible(isVisible());
    }
}

class hours_panel extends JPanel {
    private JTextField hours_field;

    public hours_panel() {
        setLayout(null);
        setBackground(new Color(72, 61, 139));


        JLabel insert_label = new JLabel("Insert number of your hours");
        insert_label.setBounds(120, 40, 1000, 60);
        insert_label.setFont(new Font("serif", Font.BOLD, 30));
        insert_label.setForeground(Color.WHITE);


        JLabel hours_label = new JLabel("Hours :");
        hours_label.setBounds(120, 150, 200, 60);
        hours_label.setFont(new Font("serif", Font.BOLD, 30));
        hours_label.setForeground(Color.WHITE);


        hours_field = new JTextField();
        hours_field.setBounds(250, 170, 150, 30);

        JButton showDetails_button = new JButton("OK");
        showDetails_button.setBounds(500, 400, 120, 40);
        ButtonCustomizer.customizeButton(showDetails_button);
        showDetails_button.setActionCommand("OK");

        JButton home_button = new JButton("Home");
        home_button.setBounds(25,20,55,25);
        home_button.setFont(new Font("Arial", Font.BOLD, 5));
        ButtonCustomizer.customizeButton(home_button);
        add(home_button);
        home_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new home_frame();
                SwingUtilities.getWindowAncestor(hours_panel.this).dispose();
            }
        });

        showDetails_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkHours();
                /* go to details frame */
                details_frame controlFrame = new details_frame();
                SwingUtilities.getWindowAncestor(hours_panel.this).dispose();
            }
        });
        add(insert_label);
        add(hours_label);
        add(hours_field);
        add(showDetails_button);
    }

    private boolean validateInput(String hours) {
        if (hours.isEmpty()) return false;
        int u = hours.length();
        for (int i = 0; i < u; i++) {
            if (hours.charAt(i) < '0' || hours.charAt(i) > '9') return false;
        }
        return true;
    }

    private void checkHours() {
        String url = "jdbc:mysql://127.0.0.1:3306/employee";
        String username = "root";
        String password = "root";

        try {

            String hours = hours_field.getText();
            if (!validateInput(hours)) {
                JOptionPane.showMessageDialog(null, "Invalid format, try again");
                hours_frame frame = new hours_frame();
                SwingUtilities.getWindowAncestor(hours_panel.this).dispose();
                return;
            }
            data.hours = Integer.parseInt(hours);
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement stat = connection.prepareStatement("UPDATE employee_table SET hours = ?, salary = ? WHERE id = ?");
            stat.setInt(1, data.hours);
            stat.setInt(2, data.hours * 40);
            data.salary=data.hours * 40;
            stat.setInt(3, data.id);
            int rowsAffected = stat.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Employee record updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No records updated!");
            }

            connection.close();
        } catch (SQLException ex) {
            System.err.println("Error connecting to the database: " + ex.getMessage());
        }
    }
}

//==================================================================

class details_panel extends JPanel {

    public details_panel() {
        setBackground(new Color(72, 61, 139));
        setLayout(new BorderLayout(15,15));
        JPanel dataPnl=new JPanel( new GridLayout(5,2));
        dataPnl.setBackground(new Color(72, 61, 139));
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employee", "root", "root");
            PreparedStatement stat = con.prepareStatement("SELECT * FROM employee_table WHERE id = ?");
            stat.setInt(1, data.id);
            ResultSet re = stat.executeQuery();
            if (re.next()) {
                int id = re.getInt("id");
                JLabel lbID = new JLabel("ID");
                lbID.setSize(100, 30);
                lbID.setFont(new Font("serif", Font.PLAIN, 20));
                lbID.setForeground(Color.WHITE);
                dataPnl.add(lbID);
                //////////////
                String idField = String.valueOf(id);
                JLabel lbIDField = new JLabel(idField);
                lbIDField.setSize(100, 30);
                lbIDField.setFont(new Font("serif", Font.PLAIN, 20));
                lbIDField.setForeground(Color.WHITE);
                dataPnl.add(lbIDField);
                ///////////////
                JLabel name = new JLabel("Name:");
                name.setSize(100, 30);
                name.setFont(new Font("serif", Font.PLAIN, 20));
                name.setForeground(Color.WHITE);
                dataPnl.add(name);
                /////////////////
                String nameD = re.getString("name");
                JLabel nameField = new JLabel(nameD);
                nameField.setSize(100, 30);
                nameField.setFont(new Font("serif", Font.PLAIN, 20));
                nameField.setForeground(Color.WHITE);
                dataPnl.add(nameField);
                /////////////////////
                JLabel Department = new JLabel("Department:");
                Department.setSize(100, 30);
                Department.setFont(new Font("serif", Font.PLAIN, 20));
                Department.setForeground(Color.WHITE);
                dataPnl.add(Department);
                ///////////////////////
                String dep = re.getString("department");
                JLabel depF = new JLabel(dep);
                depF.setSize(100, 30);
                depF.setFont(new Font("serif", Font.PLAIN, 20));
                depF.setForeground(Color.WHITE);
                dataPnl.add(depF);
                //////////////////////////
                JLabel hrs = new JLabel("Hours:");
                hrs.setSize(100, 30);
                hrs.setFont(new Font("serif", Font.PLAIN, 20));
                hrs.setForeground(Color.WHITE);
                dataPnl.add(hrs);
                ///////////////////////////
                String hours = String.valueOf(data.hours);
                JLabel hor = new JLabel(hours);
                hor.setSize(100, 30);
                hor.setFont(new Font("serif", Font.PLAIN, 20));
                hor.setForeground(Color.WHITE);
                dataPnl.add(hor);
                ///////////////////////////////
                JLabel sal = new JLabel("Salary:");
                sal.setSize(100, 30);
                sal.setFont(new Font("serif", Font.PLAIN, 20));
                sal.setForeground(Color.WHITE);
                dataPnl.add(sal);
                ////////////////////////////////
                JLabel Salary = new JLabel(String.valueOf(data.salary));
                Salary.setSize(100, 30);
                Salary.setFont(new Font("serif", Font.PLAIN, 20));
                Salary.setForeground(Color.WHITE);
                dataPnl.add(Salary);
            } else {
                JOptionPane.showMessageDialog(null, "employee not exist try again");
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        JPanel button=new JPanel();
        button.setBackground(new Color(72, 61, 139));
        JButton btn=new JButton("EXIT");
        btn.setBounds(700, 400, 120, 60);
        ButtonCustomizer.customizeButton(btn);

        JButton login=new JButton("HOME PAGE");
        login.setBounds(700, 400, 120, 60);
        ButtonCustomizer.customizeButton(login);

        button.add(login);
        button.add(btn);

        add(dataPnl,BorderLayout.CENTER);
        add(button,BorderLayout.SOUTH);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(details_panel.this).dispose();
            }
        });
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new home_frame();
                SwingUtilities.getWindowAncestor(details_panel.this).dispose();
            }}); }}

class details_frame extends Schema{
    public details_frame(){
        JFrame frame = new JFrame("Employee Details");
        //frame.getContentPane().setBackground(new Color(72,61,139));
        frame.getContentPane().add(new details_panel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizeX(),sizeY());
        frame.setLocation(getLocationX(), getLocationY());
        frame.setVisible(isVisible());
    }
}

//==================================================================

public class Main{
    public static void main(String[] args) {
        new home_frame();
    }
}