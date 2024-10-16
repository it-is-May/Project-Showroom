#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <arpa/inet.h> 

int main(int argc, char *argv[])
{
	/* check valid arguments & update variables - 1st change
	similar to Server2, we have another input for server IP, which is why
	it's 3 in the if statement */
	if(argc != 3) {
		fprints(stderr, "Usage: %s <server_ip> <port_n>\n", argv[0]);
		exit(1);
	}
    char ip[INET_ADDRSTRLEN];  //store server IP
    int port;                 //storing port# from input, it was using 34860 in Server1 
	
	//do the following system calls in the beginning of the program run
	system(" date; hostname; whoami ; who | grep `whoami` ");
	system(" netstat -aont | grep \" `hostname -i`:3486[0-9] \" ");
	
	// get ip adddress and port# - 2nd change
	if(inet_pton(AF_INET, argv[1], &ip) <=0) //get server IP address, 1st argument
	{
		fprintf(stderr, "Invalid server IP address\n");
		exit(1);
	}
	
	port = atoi(argv[2]); //get port#, 2nd argument 

    int sockfd = 0, n = 0;
    char recvBuff[1024];
    struct sockaddr_in serv_addr; 

	//socket creation
    memset(recvBuff, '0',sizeof(recvBuff));
    if((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("\n Error : Could not create socket \n");
        return 1;
    } 

    memset(&serv_addr, '0', sizeof(serv_addr)); 

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(port); 
    //argv[1] = ip; //3rd change, this line is removed because we already used argv[1] above to get IP
	
    system(" date; hostname; whoami "); //from Server1
    system(" netstat -aont | grep \" `hostname -i`:34860 \" "); //from Server1

    printf("\n timeClient2: connecting to %s Port#=%d \n", ip, port); //4th change

	//convert IP address string to binary network address
    if(inet_pton(AF_INET, argv[1], &serv_addr.sin_addr)<=0)
    {
        printf("\n inet_pton error occured\n");
        return 1;
    } 
	//connect to server
    if( connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
    {
       printf("\n Error : Connect Failed \n");
       return 1;
    } 

    printf("\n timeClient2: connected to timeServer. \n");
    system("ps");
    system(" netstat -aont | grep \":34860 \" "); //from Server1
    printf("\n\n");

    while ( (n = read(sockfd, recvBuff, sizeof(recvBuff)-1)) > 0)
    {
        recvBuff[n] = 0;
        if(fputs(recvBuff, stdout) == EOF)
        {
            printf("\n Error : Fputs error\n");
        }
    } 

    if(n < 0)
    {
        printf("\n Read error \n");
	   exit(0);
    } 

    printf("\n timeClient2: now terminated. \n");
	
	//do the following system calls at the end of the program run
	system(" date; hostname; whoami ; who | grep `whoami` ");
	system(" netstat -aont | grep \" `hostname -i`:3486[0-9] \" ");
	
    return 0;
}
