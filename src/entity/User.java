/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.controlsfx.control.Notifications;
import services.Mailling;
import services.UserService;

/**
 *
 * @author mega pc
 */
public class User  {
    
   private int id;
   private boolean isBlocked;
   private String email;
   private String firstname ;
   private String lastname;
   private String phone;
   private String roles;
   private String password;

    


   
    public static String encrypMD5 (String data) {
try{
MessageDigest md =MessageDigest.getInstance ("MD5");
byte[] messageDigst = md.digest (data.getBytes () );
BigInteger num = new BigInteger (1, messageDigst);
String hashtext = num.toString (16);
while (hashtext.length () < 32) {
hashtext = "0" + hashtext;
}
return hashtext;} 
catch (NoSuchAlgorithmException e) {
throw new RuntimeException (e);
}

}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.phone != other.phone) {
            return false;
        }
        return true;
    }

    

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    

    
    
    
    






  



   



 

  
    



   public String verifRole()
   {

       if(getEmail().contains("admin.tn"))
       
         this.roles="[\"ROLE_ADMIN\"]";
       
       else
         this.roles="[\"ROLE_CLIENT\"]";
       
       
       return roles;    
   }

 
        
    public Integer rondomcode()
    {
Random rand=new Random();
int randomcode=rand.nextInt(999999);
 return randomcode; 
    }
  


    public void forgetpass(String email)
{
UserService sp = new UserService();
Mailling m=new Mailling();
int code=rondomcode();

if (sp.checkEmail(email).contains(email))
{
    Scanner s = new Scanner(System.in);
  m.sendemail(email,code);
  System.out.println("Enter code");
  int verifcode = s.nextInt();
  if (code == verifcode)
  {
      System.out.println("Enter new passwoed");
      String newpassword = s.next();
      if(!sp.Updatepassword(newpassword,email))
     {
         System.out.println("passwoed updated");
     }
     else
     {
         System.out.println("passwoed wasn't updated");
     }
      
  }
  else
  {
        System.out.println("Rong code");
  }

}
else
System.out.println(email +" was not found in our database\ntry again");

}
   
       public boolean signup(String mailorusername,String password) {
           boolean check;
        UserService sp = new UserService();
        List<String> list;
        list = new ArrayList<>();
        list.add(mailorusername);list.add(encrypMD5(password));
       if (sp.checksignin(mailorusername,encrypMD5(password)).equals(list))
{
      check=true;
    System.out.println("correct credentials");
   
}    
       else
       {
           check=false;
           System.out.println("correct credentials needed");

       }
       return check;
    }

    
    
    
    
    
    
    
}
