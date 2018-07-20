# java_telnet_tms
Telnet text messaging server with embeded apache derby database

The message server creates a connection to the database running in the same environment. This means that a java application being run in another environment while this one is active cannot connect to the database:
When an application accesses a Derby database using the Embedded Derby JDBC driver, the Derby engine does not run in a separate process, and there are no separate database processes to start up and shut down. Instead, the Derby database engine runs inside the same Java Virtual Machine (JVM) as the application. So, Derby becomes part of the application just like any other jar file that the application uses. 

Figure 1 depicts this embedded architecture.
Figure 1: Derby Embedded Architecture
The Apache DB Project (2017) i

User authentication was already implemented for the TMS system where the user must provide a valid name and password. User authentication is stored in the userAuth.properties file is for use with the MsgSvrConnection class.
Future development could include providing an improved security policy with the system and the database. Derby has it’s own authentication and authorization options where it grants can grant user access to the Derby system but not necessarily access to the database made in the connection request (e.g. for Create, Read, Update and Delete (CRUD) operations). This could be implemented by creating the jdbc connection object in each instance of the MsgSvrConnection class where the relevant authenticated properties object can be passed into the constructor for authorization.
Currently the password is also stored in the database for demonstration purposes only.
