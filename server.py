#---Transfer a file over socket connection---#

#using socket library for connections
import socket

#initialize socket
s = socket.socket()

#0.0.0.0 uses all available ip addresses, wifi, ethernet etc
host = '0.0.0.0'

#open port to use, *use same port for client
port = 9006

#A socket is like a gateway made up of an ip and
#port number, this statement creates the socket
#with the defined ip and port number
s.bind((host, port))

#allow up to 1 clients to connect
s.listen(1)

#flag to tell program when to end
running = True

#keep checking for a connection from a
#client
while running:

  #accept returns the client and address of client
  c, addr = s.accept()

  #display client's address
  print ('Got connection from',addr)
  #display client's message
  msg = c.recv(1024)
  pring("message: " + msg)

  #check for end
  if msg == "end":
    running = False

  #send message
  c.send("Connection established")

#end connection with client
c.close()
print('exit')
