package app;

public class Main {
	public static int portTCP = 44444;
	public static int portUDP = 44445;
	public static void main(String[] args) {
		World world = new World();
		
		Server server = new Server();
	    server.start();
	    server.bind(portTCP, portUDP);
	    
	    /*server.addListener(new Listener() {
	        public void received (Connection connection, Object object) {
	           if (object instanceof SomeRequest) {
	              SomeRequest request = (SomeRequest)object;
	              System.out.println(request.text);
	     
	              SomeResponse response = new SomeResponse();
	              response.text = "Thanks";
	              connection.sendTCP(response);
	           }
	        }
	     });*/
	    
	}

}
