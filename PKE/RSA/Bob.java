
//Bob.java
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.math.*;
import java.security.SecureRandom;

public class Bob
{
	public DatagramSocket sock =null;
	public DatagramPacket Pin,Pout=null;
	public String s=null;
	
	Bob(){}
	
	public BigInteger encrypt(BigInteger message,BigInteger e, BigInteger n) 
	{
    	return message.modPow(e, n);
    }
	
    public void sendMessage(){
    	try
		{
			byte[] inMsg=new byte[65536];
			sock=new DatagramSocket(5000);
			Pin=new DatagramPacket(inMsg,inMsg.length); 
			sock.receive(Pin);
			InetAddress ip = Pin.getAddress(); 
			int port = Pin.getPort();
			System.out.println(Pin.getLength());
			byte[] ek=new byte[1];//encryption key from alice
			byte[] modByt=new byte[Pin.getLength()-1];//mod
			System.arraycopy(inMsg, 0, ek, 0, ek.length);
			System.arraycopy(inMsg, 1, modByt, 0,Pin.getLength()-1);
			BigInteger e=new BigInteger(ek);
			BigInteger mod=new BigInteger(modByt);
			System.out.println("Received encryption Key: "+e+" from Alice");
			byte[] msg;
			BigInteger b1,ciphertext;
			
			
			while(true)
			{	
				try{
					//sends encrypted msgs to alice
					System.out.print("To Alice: ");
					Scanner scanner = new Scanner(System.in);
					String message = scanner.nextLine();
					System.out.flush(); 
					msg=message.getBytes();
					b1 = new BigInteger(msg);
					ciphertext = encrypt(b1,e,mod);
					msg=ciphertext.toByteArray();
					Pout=new DatagramPacket(msg,msg.length,ip,port);
					sock.send(Pout);
					if(message.equalsIgnoreCase("Bye"))
						break;
				}
				catch(Exception ex)
				{
					System.out.println(ex);
					continue;
	    		}
			}
    	
	    }
	    catch(Exception ex)
	    {
	    	System.out.println(ex);
	    }
    
    }
    
    
    public static void main(String args[])
    {
    	new Bob().sendMessage();
        
    }

}