package actividadsockets;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Server_Simplex {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ServerSocket serverSocket;
		Socket socket;
		DataOutputStream outStream;
		DataInputStream dataStream;
		String nombreRecibido; String apellidoRecibido;
		String paisRecibido; String ciudadRecibida;
		int puertoUtilizar = 0;
		boolean aux;
		
		
		try {
			
			System.out.println("Bienvenido al server!");
			System.out.println("Ingrese el puerto a utilizar: ");
			
			do {
				aux=false;
				
				try {
					puertoUtilizar = Integer.parseInt(scanner.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("El puerto debe contener solo números");
					System.out.println("Reingresar: ");
					aux=true;
				}
				
			} while (aux);
			
			
			
			
			serverSocket = new ServerSocket(puertoUtilizar);
			
			System.out.println("Esperando a algún cliente en el puerto " + puertoUtilizar);
			
			socket = serverSocket.accept();
			
			System.out.println("Se conectó el cliente de dirección IP: "+socket.getInetAddress().getHostAddress());
			
			dataStream = new DataInputStream(socket.getInputStream());
			
			nombreRecibido = dataStream.readUTF();
			System.out.println("Nombre y apellido recibido: "+nombreRecibido);
			
			outStream = new DataOutputStream(socket.getOutputStream());
			outStream.writeUTF("Ingrese ciudad de residencia: ");
			ciudadRecibida = dataStream.readUTF();
			outStream.writeUTF("Ingrese pais de residencia: ");
			paisRecibido = dataStream.readUTF();
			
			outStream.writeUTF("Su lugar de residencia es "+ ciudadRecibida + ", " + paisRecibido);
			
			
			socket.close();
			serverSocket.close();
			
			System.out.println("Server cerrado");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
