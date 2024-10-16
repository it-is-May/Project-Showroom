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
    char ip[] = "127.0.0.1";  // default IP of the server
    int port = 34860;         // default port# of the server was 23450, change it to one of the assigned port#, which is 34860
	
	//do the following system calls in the beginning of the program run
	system(" date; hostname; whoami ; who | grep `whoami` ");
	system(" netstat -aont | grep \" `hostname -i`:3486[0-9] \" ");

    int sockfd = 0, n = 0;
    char recvBuff[1024];
    struct sockaddr_in serv_addr; 


    memset(recvBuff, '0',sizeof(recvBuff));
    if((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        printf("\n Error : Could not create socket \n");
        return 1;
    } 

    memset(&serv_addr, '0', sizeof(serv_addr)); 

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(port); 
    argv[1] = ip;

    system(" date; hostname; whoami ");
    system(" netstat -aont | grep \" `hostname -i`:34860 \" "); //change 23450 to one of the assigned port#, which is 34860

    printf("\n timeClient1: connecting to 127.0.0.1 Port#=34860 \n"); //change 23450 to one of the assigned port#, which is 34860


    if(inet_pton(AF_INET, argv[1], &serv_addr.sin_addr)<=0)
    {
        printf("\n inet_pton error occured\n");
        return 1;
    } 

    if( connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
    {
       printf("\n Error : Connect Failed \n");
       return 1;
    } 

    printf("\n timeClient1: connected to timeServer. \n");
    system("ps");
    system(" netstat -aont | grep \":34860 \" "); //change 23450 to one of the assigned port#, which is 34860
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

    printf("\n timeClient1: now terminated. \n");
	
	//do the following system calls at the end of the program run
	system(" date; hostname; whoami ; who | grep `whoami` ");
	system(" netstat -aont | grep \" `hostname -i`:3486[0-9] \" ");
	
    return 0;
}
