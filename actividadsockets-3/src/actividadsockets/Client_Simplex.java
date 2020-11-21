package actividadsockets;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;







public class Client_Simplex {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Socket socket = null;
		DataOutputStream dataStream;
		DataInputStream inStream;
		String datosEnviados;
		String ipServer = null;
		int puertoServer = 0;
		String nombre = null;
		String ciudad;
		String pais;
		
		boolean paraConexion;
		boolean paraNombre;
		boolean paraCiudad;
		boolean paraPais;
		
		
		try {
			
			 //ingreso de nombre
			System.out.println("Para comenzar ingrese nombre y apellido: ");
			do {
				paraNombre=false;
				
				try {
					nombre = scanner.nextLine();
					tieneNumeros(nombre);
					System.out.println("Bienvenido al cliente "+nombre+"!");
					
				} catch (tieneNumerosException e) {
					System.out.println("El nombre no puede llevar números");
					System.out.println("Reingresar: ");
					paraNombre=true;
				}	
			} while (paraNombre);
			
			
			//ingreso ip y puerto y conecto
			do {
				paraConexion=false;
				
				try {
					System.out.println("Ingrese dirección IP del server: ");
					ipServer = scanner.nextLine();

					System.out.println("Ingrese puerto del server: ");
					puertoServer = Integer.parseInt(scanner.nextLine());

					socket = new Socket(ipServer, puertoServer);	
					
				} catch (SocketException e) {
					System.out.println("No se puede conectar");
					System.out.println("Causa: IP o Puerto incorrectos");
					System.out.println("");
					paraConexion=true;
				}
				catch(UnknownHostException e) {
					System.out.println("No se puede conectar");
					System.out.println("Causa: IP o Puerto incorrectos");
					System.out.println("");
					paraConexion=true;
				}
				catch(NumberFormatException e) {
					System.out.println("No se puede conectar");
					System.out.println("Causa: IP o Puerto están vacíos o contienen letras");
					System.out.println("");
					paraConexion=true;
				}
			} while (paraConexion);
				
			
			System.out.println("Se conectó al servidor de dirección IP: "+socket.getInetAddress().getHostAddress());
				
			//envío nombre y apellido
			dataStream = new DataOutputStream(socket.getOutputStream());
			dataStream.writeUTF(nombre);
			
			//recibo pedido de ciudad
			inStream = new DataInputStream(socket.getInputStream());
			System.out.println(inStream.readUTF());
			//ingreso y mando ciudad
			do {
				paraCiudad=false;
				
				try {
					//escribo la ciudad
					
					ciudad = scanner.nextLine();
					estaVacio(ciudad);
					tieneNumeros(ciudad);
					
					//la mando
					dataStream.writeUTF(ciudad);
					paraCiudad=false;
				} 
				catch (StringVacioException e) {
					System.out.println("La ciudad está vacía");
					System.out.println("Reingrese: ");
					paraCiudad=true;
				}
				catch (tieneNumerosException e) {
					System.out.println("La ciudad no puede llevar números");
					System.out.println("Reingrese: ");
					paraCiudad=true;
				}
			} while (paraCiudad);
			
			
			//recibo pedido de pais
			inStream = new DataInputStream(socket.getInputStream());
			System.out.println(inStream.readUTF());
			
			//ingreso y mando pais
			do {
				paraPais=false;
				
				try {
					//escribo la ciudad
					
					pais = scanner.nextLine();
					estaVacio(pais);
					tieneNumeros(pais);
					
					dataStream.writeUTF(pais);
					
				} 
				catch (StringVacioException e) {
					System.out.println("El país está vacío");
					System.out.println("Reingrese: ");
					paraPais=true;
				}
				catch (tieneNumerosException e) {
					System.out.println("El país no puede llevar números");
					System.out.println("Reingrese: ");
					paraPais=true;
				}
			} while (paraPais);
			
			System.out.println(inStream.readUTF());
			
			System.out.println("Comunicación terminada");
			socket.close();
			
			System.out.println("Socket cerrado");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	
	static public void tieneNumeros(String n) throws tieneNumerosException {
		if (!Pattern.matches("[a-zA-Z ]+", n.trim())) 
			throw new tieneNumerosException();
	}
	
	static public void estaVacio(String n) throws StringVacioException {
		if (n.isEmpty())
			throw new StringVacioException();
	}
}
