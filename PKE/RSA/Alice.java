
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.math.*;
import java.security.SecureRandom;

public class Alice
{
	public BigInteger mod,d;
    public int bitLength;
	public DatagramSocket sock =null;
	public DatagramPacket Pin,Pout=null;
	public int port;
	
	Alice(int bitLength, int port){
	  this.bitLength = bitLength;
	  this.port = port;
	}
	
   public byte[] generateKeys()
   {
		SecureRandom r = new SecureRandom();
		BigInteger p = new BigInteger(bitLength/2, 100, r);
		BigInteger q = new BigInteger(bitLength/2, 100, r);
		mod = p.multiply(q);
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		BigInteger e = new BigInteger("3");
		while (phi.gcd(e).intValue()>1)
		{
		  e = e.add(new BigInteger("1"));
		}
		d = e.modInverse(phi);
		byte[] ek = e.toByteArray();
		byte[] mod_byt = mod.toByteArray();
		byte[] senKeys=new byte[ek.length+mod_byt.length];
		System.out.println("Sending the encryption key " + e + " to Bob");
		System.arraycopy(ek, 0, senKeys, 0, ek.length);
		System.arraycopy(mod_byt, 0, senKeys, ek.length, mod_byt.length);
		return senKeys;
   }
   
   public BigInteger decrypt(BigInteger message, BigInteger d, BigInteger mod) 
   {
   	   return message.modPow(d, mod);
   }
   
   
   public void control(){
   	   try
		{
			sock=new DatagramSocket();
			InetAddress ip = InetAddress.getByName("localhost");
			byte[] msg,sendKey,decrypt;
			//send the encryption key to Bob
			sendKey=generateKeys();
			Pout=new DatagramPacket(sendKey,sendKey.length,ip,port);
			sock.send(Pout);
			byte[] inMsg=new byte[65536];
			byte[] getMsg;
			int packetSize;
			String message = null;
			BigInteger b1, plaintext;
			while(true)
			{
				Pin=new DatagramPacket(inMsg,inMsg.length);
				sock.receive(Pin);
				getMsg=new byte[Pin.getLength()];
				System.arraycopy(inMsg, 0,getMsg , 0,Pin.getLength());
				b1=new BigInteger(getMsg);
				plaintext = decrypt(b1,d,mod);
				decrypt=plaintext.toByteArray();
				message = new String(decrypt);
				System.out.println("From Bob: "+message);
				if(message.equalsIgnoreCase("Bye"))
					break;
			}		
		}
		catch(IOException ex)
		{
			System.out.println(ex);
		}
   }
   
   public static void main(String args[])
   {
   	   
   	   new Alice(1024, 5000).control();
		
    }
}
