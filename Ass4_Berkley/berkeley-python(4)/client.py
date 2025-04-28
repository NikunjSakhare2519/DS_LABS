#1.python server.py
#2. python client.py
# Python3 program imitating a client process
from timeit import default_timer as timer
from dateutil import parser
import threading
import datetime
import socket
import time
'''
# client thread function used to send time at client side
def startSendingTime(slave_client):
    while True:
        # provide server with clock time at the client
        slave_client.send(str(datetime.datetime.now()).encode())
        print("Recent time sent successfully")
        time.sleep(5)

# client thread function used to receive synchronized time
def startReceivingTime(slave_client):
    while True:
        # receive data from the server
        synchronized_time = parser.parse(slave_client.recv(1024).decode())
        print("Synchronized time at the client is: " + str(synchronized_time))

# function used to Synchronize client process time
def initiateSlaveClient(port=8080):
    slave_client = socket.socket()
    # connect to the clock server on local computer
    slave_client.connect(('127.0.0.1', port))
    # start sending time to server
    print("Starting to receive time from server\n")
    send_time_thread = threading.Thread(
        target=startSendingTime,
        args=(slave_client,)
    )
    send_time_thread.start()
    # start receiving synchronized time from server
    print("Starting to receive synchronized time from server\n")
    receive_time_thread = threading.Thread(
        target=startReceivingTime,
        args=(slave_client,)
    )
    receive_time_thread.start()

# Driver function
if __name__ == '__main__':
    # initialize the Slave / Client
    initiateSlaveClient(port=8080)
    
    '''
    
from dateutil import parser
import threading
import datetime
import socket
import time

# client thread function used to send time at client side
def startSendingTime(slave_client):
    while True:
        try:
            # provide server with clock time at the client
            slave_client.send(str(datetime.datetime.now()).encode())
            print("Recent time sent successfully")
            time.sleep(5)
        except Exception as e:
            print(f"Error sending time: {e}")
            break  # Exit the loop on error (you can retry logic here)

# client thread function used to receive synchronized time
def startReceivingTime(slave_client):
    while True:
        try:
            # receive data from the server
            synchronized_time = parser.parse(slave_client.recv(1024).decode())
            print("Synchronized time at the client is: " + str(synchronized_time))
        except Exception as e:
            print(f"Error receiving synchronized time: {e}")
            break  # Exit the loop on error (you can retry logic here)

# function used to Synchronize client process time
def initiateSlaveClient(port=8080):
    slave_client = socket.socket()
    slave_client.settimeout(10)  # Timeout after 10 seconds if connection fails
    
    try:
        # Attempt to connect to the clock server on local computer
        slave_client.connect(('127.0.0.1', port))
        print(f"Connected to server on port {port}")
    except socket.timeout:
        print(f"Connection timed out after 10 seconds. Server not reachable.")
        return
    except ConnectionRefusedError:
        print("Connection refused. Is the server running?")
        return
    except Exception as e:
        print(f"Failed to connect to server: {e}")
        return
    
    # Start sending time to server
    print("Starting to receive time from server\n")
    send_time_thread = threading.Thread(
        target=startSendingTime,
        args=(slave_client,)
    )
    send_time_thread.daemon = True  # Ensure thread exits when the main program exits
    send_time_thread.start()
    
    # Start receiving synchronized time from server
    print("Starting to receive synchronized time from server\n")
    receive_time_thread = threading.Thread(
        target=startReceivingTime,
        args=(slave_client,)
    )
    receive_time_thread.daemon = True  # Ensure thread exits when the main program exits
    receive_time_thread.start()
    
    # Keep the main thread alive while the background threads handle communication
    try:
        while True:
            time.sleep(1)  # Keep the program running
    except KeyboardInterrupt:
        print("Client shutting down...")
        slave_client.close()

# Driver function
if __name__ == '__main__':
    # Initialize the Slave / Client
    initiateSlaveClient(port=8080)

